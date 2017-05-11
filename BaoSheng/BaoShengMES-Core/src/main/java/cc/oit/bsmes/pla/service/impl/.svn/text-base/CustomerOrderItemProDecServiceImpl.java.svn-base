package cc.oit.bsmes.pla.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.InventoryDetailStatus;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.constants.ProcessSection;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.exception.InconsistentException;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.inv.model.InventoryDetail;
import cc.oit.bsmes.inv.service.InventoryDetailService;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.dao.CustomerOrderItemProDecDAO;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.InvOaUseLog;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.InvOaUseLogService;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.dto.SectionLength;
import cc.oit.bsmes.wip.service.SectionService;

/**
 * 
 * @author JinHanyun
 * @date 2013-12-25 下午1:38:02
 * @since
 * @version
 */
@Service
public class CustomerOrderItemProDecServiceImpl extends BaseServiceImpl<CustomerOrderItemProDec> implements
		CustomerOrderItemProDecService {
	public static final Pattern pattern = Pattern.compile("^(\\d)#-(\\d+)#/([\\u4e00-\\u9fa5]++)");
	@Resource
	private CustomerOrderItemProDecDAO customerOrderItemProDecDAO;

	@Resource
	private CustomerOrderItemService customerOrderItemService;

	@Resource
	private CustomerOrderItemDecService customerOrderItemDecService;

	@Resource
	private SalesOrderItemService salesOrderItemService;

	@Resource
	private InventoryService inventoryService;

	@Resource
	private SectionService sectionService;

	@Resource
	private InvOaUseLogService invOaUseLogService;

	@Resource
	private InventoryDetailService inventoryDetailService;

	@Resource
	private SalesOrderService salesOrderService;

	@Resource
	private ProductProcessService productProcessService;

	@Override
	@Transactional(readOnly = false)
	public void analysisOrderToProcess(ResourceCache resourceCache, String orgCode) {
		// 缓存产品销售订单信息
		Map<String, SalesOrderItem> soiMap = new HashMap<String, SalesOrderItem>();
		Map<String, SalesOrder> salesOrderMap = new HashMap<String, SalesOrder>();
		// 查询出所有未开始的订单列表
		List<CustomerOrderItem> cusOrderItemList = customerOrderItemService.getUnLocked(orgCode);
		int i = 0;
		logger.info("总共需要分解{}个订单!", cusOrderItemList.size());
		for (CustomerOrderItem coi : cusOrderItemList) {
			logger.info("正在分解第{}个订单,长度为:{}", i, coi.getOrderLength());
			i++;
			SalesOrderItem salesOrderItem = salesOrderItemCache(coi, soiMap);
			String productCode = salesOrderItem.getProductCode();
			String contractNo = salesOrderCache(coi, salesOrderMap);
			// 订单上的工艺ID
			String craftsId = coi.getCraftsId();

			if (StringUtils.isBlank(productCode))
				throw new InconsistentException();
			List<ProductProcess> proProcessesList = resourceCache.getProductProcessByCraftId(craftsId);

			// 根据工艺代码 查询出该产品工艺流程工序按加工顺序倒序排列？最好这里直接加载每道工序的投入和产出的半成品和成品 TODO
			// 注意查询结果排序问题
			// 比较工艺是否发生变化,若发生变化则删除上次分解数据记录
			List<CustomerOrderItemDec> coids = coi.getCusOrderItemDesc();
			if (!CollectionUtils.isEmpty(coids)) {
				CustomerOrderItemDec itemDec = coids.get(0);
				List<CustomerOrderItemProDec> proDecList = itemDec.getCusOrderItemProDesList();
				if (!CollectionUtils.isEmpty(proDecList)) {
					CustomerOrderItemProDec itemProDec = proDecList.get(0);
					if (!craftsId.equals(itemProDec.getCraftsId())) {
						customerOrderItemProDecDAO.deleteByOrderItemId(coi.getId(), WebConstants.ROOT_ID);
					}
				}
			}

			for (CustomerOrderItemDec coid : coids) {
				// 如果 客户订单明细分解 状态为已完成 则不分解
				if (coid.getStatus() == ProductOrderStatus.FINISHED)
					continue;
				// 判断是否已锁定 如果已锁定则不分解
				if (checkProDecIsLocked(coid.getId(), true) > 0) {
					continue;
				}
				// 查询当前item是否已经分解过
				List<CustomerOrderItemProDec> coipdList = coid.getCusOrderItemProDesList();

				Map<String, List<CustomerOrderItemProDec>> updateOffsetLen = new HashMap<String, List<CustomerOrderItemProDec>>();

				if (coipdList != null && coipdList.size() > 0) {
					// 如果使用库存则重新进行分解
					if (checkProDecUseStock(coid.getId()) > 0) {
						customerOrderItemDecService.deleteCusOrderItemDecById(coid.getId(), WebConstants.ROOT_ID, "0");
					} else {
						// 如果已分解 未使用库存则不分解直接进行库存冲抵
						if (coipdList != null) {
							for (CustomerOrderItemProDec coipd : coipdList) {
								if (coipd.getIsLocked()) {
									break;
								}
								List<CustomerOrderItemProDec> proDecList = updateOffsetLen.get(coipd.getProcessId());
								if (proDecList == null) {
									proDecList = new ArrayList<CustomerOrderItemProDec>();
								}
								proDecList.add(coipd);
								updateOffsetLen.put(coipd.getProcessId(), proDecList);
							}
						}
					}
				}

				CustomerOrderItemProDec coip = new CustomerOrderItemProDec();
				coip.setCraftsId(craftsId);
				coip.setUnFinishedLength(coid.getLength());
				coip.setHalfProductCode(productCode);
				coip.setUseStock(false);
				coip.setOrderItemDecId(coid.getId());
				coip.setProductCode(productCode);
				coip.setContractNo(contractNo);
				coip.setProductSpec(salesOrderItem.getProductSpec());
				coip.setOrgCode(orgCode);
				coip.setParrelCount(1);
				iteratorProcess(proProcessesList, coip, updateOffsetLen);
			}
		}
	}

	private SalesOrderItem salesOrderItemCache(CustomerOrderItem coi, Map<String, SalesOrderItem> soiMap) {
		// 根据客户生产订单中的销售订单明细ID 查询出销售订单明细对象 该对象有工艺代码 产品代码
		SalesOrderItem salesOrderItem = soiMap.get(coi.getSalesOrderItemId());
		if (salesOrderItem == null) {
			salesOrderItem = salesOrderItemService.getById(coi.getSalesOrderItemId());
			if (salesOrderItem != null)
				soiMap.put(salesOrderItem.getId(), salesOrderItem);
		}
		if (salesOrderItem == null)
			throw new InconsistentException();
		return salesOrderItem;
	}

	private String salesOrderCache(CustomerOrderItem coi, Map<String, SalesOrder> salesOrderMap) {
		String salesOrderId = "";
		CustomerOrder customerOrder = coi.getCustomerOrder();
		if (customerOrder != null) {
			salesOrderId = customerOrder.getSalesOrderId();
			if (StringUtils.isBlank(salesOrderId)) {
				SalesOrderItem salesOrderItem = coi.getSalesOrderItem();
				if (salesOrderItem != null) {
					salesOrderId = salesOrderItem.getSalesOrderId();
				}
			}
		} else {
			SalesOrderItem salesOrderItem = coi.getSalesOrderItem();
			if (salesOrderItem != null) {
				salesOrderId = salesOrderItem.getSalesOrderId();
			}
		}
		SalesOrder salesOrder = salesOrderMap.get(salesOrderId);
		if (salesOrder == null) {
			salesOrder = salesOrderService.getById(salesOrderId);
			if (salesOrder != null) {
				salesOrderMap.put(salesOrderId, salesOrder);
			}
		}
		if (salesOrder == null) {
			return "";
		} else {
			return salesOrder.getContractNo();
		}
	}

	/**
	 * 
	 * 遍历整个流程，计算每道工序需投入的半成品长度 计算需要的半成品长度默认为 当前工序的生产长度*单位投入用量
	 * 
	 * @author JinHanyun
	 * @date 2013-12-30 下午3:18:16
	 * @param proProcessesList 工序列表
	 * @see
	 */
	private void iteratorProcess(List<ProductProcess> proProcessesList, CustomerOrderItemProDec coip,
			Map<String, List<CustomerOrderItemProDec>> updateOffsetLen) {

		// 最后需要更新的数据集
		List<CustomerOrderItemProDec> coipResult = new ArrayList<CustomerOrderItemProDec>();

		// 每道工序都需要根据其产出算投入 key 工序ID value map key 产品代码 val 需要投入的原料长度集合
		Map<String, Map<String, List<CustomerOrderItemProDec>>> processProLenMap = new HashMap<String, Map<String, List<CustomerOrderItemProDec>>>();

		// 为每道工序绑定分解对象
		Map<String, List<CustomerOrderItemProDec>> everyProcessCoipd = new HashMap<String, List<CustomerOrderItemProDec>>();

		// 缓存该明细已冲抵的信息 key 工序全路径 该工序已冲抵长度集合
		// 前面已判断过是否使用库存，使用库存则已删除库存使用数据重新分解 若未使用查询也是空不用查询
		Map<String, List<Double>> inventoryOffsetCache = new HashMap<String, List<Double>>();

		// 已使用的库存对象
		Map<String, InventoryDetail> useStockCache = new HashMap<String, InventoryDetail>();

		// 如果updateOffsetLen 不为空，表示已分解过 则进行库存冲抵验证
		if (updateOffsetLen.size() > 0) {
			// key nextOrderId, value 冲抵量
			Map<String, Double> offSetMap = new HashMap<String, Double>();
			for (ProductProcess productProcess : proProcessesList) {
				List<CustomerOrderItemProDec> nextCoipdList = updateOffsetLen.get(productProcess.getId());
				if (nextCoipdList == null) {
					continue;
				}
				for (CustomerOrderItemProDec coipNext : nextCoipdList) {
					Double offSet = offSetMap.get(coipNext.getNextOrderId());
					if (offSet != null) {
						coipNext.setUnFinishedLength(offSet);
					}
					stockOffset(coipNext, useStockCache, inventoryOffsetCache);
					offSetMap.put(coipNext.getId(), coipNext.getUnFinishedLength());
					coipResult.add(coipNext);
				}
			}
		} else {
			for (ProductProcess productProcess : proProcessesList) {
				// 得到该工序投入产出信息 查询的时候最好加个排序 产出在前投入在后
				List<ProcessInOut> proInOutList = StaticDataCache.getByProcessId(productProcess.getId());
				for (ProcessInOut proInOut : proInOutList) {
					// true 为投入 false 为产出
					if (proInOut.getInOrOut().chackInOrOut()) {
						if (proInOut.getMat().getMatType() != MatType.MATERIALS) {
							cacaluteInByOut(coip, processProLenMap, everyProcessCoipd, productProcess, proInOut);
						}
					} else {
						List<Double> useLogs = inventoryOffsetCache.get(productProcess.getFullPath());
						if (useLogs == null)
							useLogs = new ArrayList<Double>();
						inventoryOffsetCache.put(productProcess.getFullPath(), useLogs);

						// 如果为最后一道工序则产出的为成品不需要冲抵
						if (WebConstants.ROOT_ID.equals(productProcess.getNextProcessId())) {
							CustomerOrderItemProDec nextCoipd = coip;
							nextCoipd.setProcessId(productProcess.getId());
							nextCoipd.setProcessName(productProcess.getProcessName());
							nextCoipd.setProcessCode(productProcess.getProcessCode());
							nextCoipd.setStatus(ProductOrderStatus.TO_DO);
							nextCoipd.setOrgCode(coip.getOrgCode());
							nextCoipd.setProcessPath(productProcess.getFullPath());
							nextCoipd.setProductCode(coip.getProductCode());
							nextCoipd.setContractNo(coip.getContractNo());
							nextCoipd.setProductSpec(coip.getProductSpec());
							coipResult.add(nextCoipd);
							List<CustomerOrderItemProDec> list = new ArrayList<CustomerOrderItemProDec>();
							list.add(nextCoipd);
							everyProcessCoipd.put(productProcess.getId(), list);
							continue;
						} else {
							Map<String, List<CustomerOrderItemProDec>> coipsMap = processProLenMap.get(productProcess
									.getNextProcessId());
							// 如果没查询到当前工序应该生产的长度则表示该工序已经完全冲抵
							if (coipsMap == null)
								continue;
							List<CustomerOrderItemProDec> list = coipsMap.get(proInOut.getMatCode());
							if (list == null || list.size() == 0)
								continue;
							List<CustomerOrderItemProDec> coipdList = everyProcessCoipd.get(productProcess.getId());
							if (coipdList == null) {
								coipdList = new ArrayList<CustomerOrderItemProDec>();
							}
							for (CustomerOrderItemProDec coipNext : list) {

								CustomerOrderItemProDec newCoipNext = new CustomerOrderItemProDec();

								newCoipNext.setCraftsId(coipNext.getCraftsId());
								newCoipNext.setHalfProductCode(coipNext.getHalfProductCode());
								newCoipNext.setOrderItemDecId(coipNext.getOrderItemDecId());
								newCoipNext.setUnFinishedLength(coipNext.getUnFinishedLength());
								newCoipNext.setParrelCount(coipNext.getParrelCount());

								newCoipNext.setProcessId(productProcess.getId());
								newCoipNext.setProcessPath(productProcess.getFullPath());
								newCoipNext.setProcessName(productProcess.getProcessName());
								newCoipNext.setProcessCode(productProcess.getProcessCode());
								newCoipNext.setOrgCode(coip.getOrgCode());
								newCoipNext.setProductCode(coip.getProductCode());
								newCoipNext.setContractNo(coip.getContractNo());
								newCoipNext.setStatus(ProductOrderStatus.TO_DO);
								newCoipNext.setProductSpec(coip.getProductSpec());
								stockOffset(newCoipNext, useStockCache, inventoryOffsetCache);
								coipResult.add(newCoipNext);
								coipdList.add(newCoipNext);
							}
							everyProcessCoipd.put(productProcess.getId(), coipdList);
						}
					}
				}
			}
		}
		executeUpdate(coipResult);

		// 清理缓存
		coipResult.clear();
		processProLenMap.clear();
		everyProcessCoipd.clear();
		inventoryOffsetCache.clear();
		useStockCache.clear();

		coipResult = null;
		processProLenMap = null;
		everyProcessCoipd = null;
		inventoryOffsetCache = null;
		useStockCache = null;
	}

	private void cacaluteInByOut(CustomerOrderItemProDec coip,
			Map<String, Map<String, List<CustomerOrderItemProDec>>> processProLenMap,
			Map<String, List<CustomerOrderItemProDec>> everyProcessCoipd, ProductProcess productProcess,
			ProcessInOut proInOut) {
		List<CustomerOrderItemProDec> nextCoipdList = everyProcessCoipd.get(productProcess.getId());
		if (nextCoipdList == null) {
			return;
		}

		// 当前工序产出对象
		// 产出会在上一工序计算出来，当前工序只需要计算投入就可以了，根据产出进行计算其实际需要多长，然后验证是否可以进行冲抵

		// 判断该产出是否可以冲抵
		// TODO 计算proLen
		CustomerOrderItemProDec coipd = null;
		// 把该半成品缓存到map中
		Map<String, List<CustomerOrderItemProDec>> lenMap = processProLenMap.get(productProcess.getId());
		if (lenMap == null)
			lenMap = new HashMap<String, List<CustomerOrderItemProDec>>();

		List<CustomerOrderItemProDec> lenList = lenMap.get(proInOut.getMatCode());
		if (lenList == null)
			lenList = new ArrayList<CustomerOrderItemProDec>();

		for (int i = 0; i < proInOut.getQuantity(); i++) {
			double proLen = 0;
			for (CustomerOrderItemProDec nextCoipd : nextCoipdList) {
				proLen += nextCoipd.getUnFinishedLength() == null ? 0 : nextCoipd.getUnFinishedLength().doubleValue();
			}
			if (proLen == 0)
				continue;
			coipd = new CustomerOrderItemProDec();
			coipd.setCraftsId(coip.getCraftsId());
			coipd.setHalfProductCode(proInOut.getMatCode());
			coipd.setOrderItemDecId(coip.getOrderItemDecId());
			coipd.setUnFinishedLength(proLen);
			coipd.setParrelCount((proInOut.getQuantity().intValue()));
			lenList.add(coipd);
		}
		lenMap.put(proInOut.getMatCode(), lenList);
		processProLenMap.put(productProcess.getId(), lenMap);
	}

	/**
	 * 库存冲抵
	 * 
	 * @param coipNext
	 * @param useStockCache
	 * @param inventoryOffsetCache
	 * @return
	 */
	private CustomerOrderItemProDec stockOffset(CustomerOrderItemProDec coipNext,
			Map<String, InventoryDetail> useStockCache, Map<String, List<Double>> inventoryOffsetCache) {
		Double halfFishProLen = coipNext.getUnFinishedLength(); // 需要生产的半成品的长度

		// 该工序库存使用日志
		List<InvOaUseLog> useLogList = new ArrayList<InvOaUseLog>();
		coipNext.setUseStock(false);
		if (halfFishProLen != null) {
			// 进行半成品冲抵
			// 设置冲抵的信息，并更新对应的库存日志表
			// 查询出当前半成品符合需要长度的库存，TODO 需要加上一个常量值
			List<InventoryDetail> inventoryDetailList = inventoryDetailService.findByMatCodeAndLen(
					coipNext.getHalfProductCode(), halfFishProLen);
			// 遍历这个结果集把已冲抵的对象去掉
			if (inventoryDetailList.size() > 0) {
				Iterator<InventoryDetail> it = inventoryDetailList.iterator();
				while (it.hasNext()) {
					InventoryDetail inventoryDetail = it.next();
					if (useStockCache.get(inventoryDetail.getId()) != null) {
						it.remove();
					}
				}

				if (inventoryDetailList.size() > 0) {
					// 符合条件可以冲抵的记录 TODO
					List<? extends SectionLength> inventoryDetailOffsetList = sectionService.getMatchedSections(
							inventoryDetailList, inventoryOffsetCache, coipNext, true);
					// 进行冲抵 并计算还需要生产的长度
					for (SectionLength sectionLength : inventoryDetailOffsetList) {
						InventoryDetail inventoryDetail = (InventoryDetail) sectionLength;
						Inventory inventory = inventoryDetail.getInventory();
						InvOaUseLog useLog = new InvOaUseLog();
						useLog.setMatCode(inventory.getMaterialCode());
						useLog.setInventoryDetailId(inventoryDetail.getId());
						useLog.setMatBatchNo(inventory.getBarCode());
						useLog.setUsedStockLength(inventoryDetail.getLength());

						// 更新detail状态为冻结
						inventoryDetail.setStatus(InventoryDetailStatus.FREEZE);
						useLog.setInventoryDetail(inventoryDetail);

						// 重新计算库存锁定长度，并set
						double invLockedQuantity = inventory.getLockedQuantity();
						inventory.setLockedQuantity(invLockedQuantity + inventoryDetail.getLength());

						useLogList.add(useLog);

						// TODO 更新当前inventoryDetail状态
						// 把已冲抵过的库存对象进行缓存，避免下次继续使用
						useStockCache.put(inventoryDetail.getId(), inventoryDetail);
						coipNext.setUseStock(true);
					}
				}
			}
		}
		// 设置库存使用量和未生产量
		if (useLogList.size() > 0) {
			coipNext.setInventoryUseLogs(useLogList);
		}
		return coipNext;
	}

	/**
	 * 
	 * 订单分解到工序，进行数据更新
	 * 
	 * @author JinHanyun
	 * @date 2014-1-3 上午11:51:13
	 * @param coipResult
	 * @see
	 */
	private void executeUpdate(List<CustomerOrderItemProDec> coipResult) {
		Map<String, List<String>> cacheMap = new HashMap<String, List<String>>();
		for (CustomerOrderItemProDec coipd : coipResult) {
			String nextProcessPath = coipd.getProcessPath();
			nextProcessPath = nextProcessPath.substring(0, nextProcessPath.indexOf(coipd.getProcessId()));
			if (StringUtils.isNotBlank(nextProcessPath)) {
				List<String> list = cacheMap.get(nextProcessPath);
				coipd.setNextOrderId(list.get(0));
				if (list.size() > 1) {
					list.remove(0);
				}
			} else {
				coipd.setNextOrderId(WebConstants.ROOT_ID);
			}
			if (StringUtils.isBlank(coipd.getId())) {
				insert(coipd);
				if (coipd.getInventoryUseLogs() != null) {
					for (InvOaUseLog useLog : coipd.getInventoryUseLogs()) {
						useLog.setRefId(coipd.getId());
						invOaUseLogService.insert(useLog);
						InventoryDetail detail = useLog.getInventoryDetail();
						inventoryDetailService.update(detail);
						inventoryService.update(detail.getInventory());
					}
				}
			} else {
				update(coipd);
				if (coipd.getInventoryUseLogs() != null) {
					for (InvOaUseLog useLog : coipd.getInventoryUseLogs()) {
						// 如果useLog的ID 不为空表示是上次分解时添加的这次不用处理
						if (StringUtils.isBlank(useLog.getId())) {
							useLog.setRefId(coipd.getId());
							invOaUseLogService.insert(useLog);
							InventoryDetail detail = useLog.getInventoryDetail();
							inventoryDetailService.update(detail);
							inventoryService.update(detail.getInventory());
						}
					}
				}
			}
			List<String> list = cacheMap.get(coipd.getProcessPath());
			if (list == null) {
				list = new ArrayList<String>();
			}
			list.add(coipd.getId());
			cacheMap.put(coipd.getProcessPath(), list);
		}
		cacheMap.clear();
		cacheMap = null;
	}

	@Override
	public List<CustomerOrderItemProDec> getByCusOrderItemDecId(String cusOrderItemDecId) {
		CustomerOrderItemProDec findParams = new CustomerOrderItemProDec();
		findParams.setOrderItemDecId(cusOrderItemDecId);
		return customerOrderItemProDecDAO.get(findParams);
	}

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = false) public void
	 * deleteByCusOrderItemDescId(String cusOrderItemDescId) { if
	 * (customerOrderItemProDecDAO
	 * .deleteByCusOrderItemDescId(cusOrderItemDescId) <= 0 ) { throw new
	 * DataCommitException(); } }
	 */

	@Override
	public List<CustomerOrderItemProDec> getUnaudited(String orgCode, Date endDate) {
		return customerOrderItemProDecDAO.getUnaudited(orgCode, endDate);
	}

	@Override
	public CustomerOrderItemProDec getCurrentByWoNo(String workOrderNo) {
		return customerOrderItemProDecDAO.getCurrentByWoNo(workOrderNo);
	}

	@Override
	public void cancelOffset(String proDecId, String updator) {
		customerOrderItemProDecDAO.cancelOffset(proDecId, updator);
	}

	@Override
	public int updateOptionalEquipCode(String equipCodes, String orderItemId, String processId) {
		return customerOrderItemProDecDAO.updateOptionalEquipCode(equipCodes, orderItemId, processId);
	}

	@Override
	public List<CustomerOrderItemProDec> getItemDecInProgress(String orgCode, Date startDate) {
		return customerOrderItemProDecDAO.getItemDecInProgress(orgCode, startDate);
	}

	/**
	 * 
	 * <p>
	 * 生产单排序后更新固定设备
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-20 下午4:16:04
	 * @param equipCode
	 * @param workOrderNo
	 * @return
	 * @see CustomerOrderItemProDecService#updateFixEquipForWorkOrderSeq(String,
	 *      String)
	 */
	@Override
	public int updateFixEquipForWorkOrderSeq(String equipCode, String workOrderNo) {
		return customerOrderItemProDecDAO.updateFixEquipForWorkOrderSeq(equipCode, workOrderNo, SessionUtils.getUser()
				.getUserCode());
	}

	@Override
	public List<CustomerOrderItemProDec> getUnFinishedLength(String contractNo, String processId, String orgCode) {
		return customerOrderItemProDecDAO.getUnFinishedLength(contractNo, processId, orgCode);
	}

	@Override
	public List<CustomerOrderItemProDec> getByWoNo(String woNo) {
		return customerOrderItemProDecDAO.getByWoNo(woNo);
	}

	@Override
	public List<CustomerOrderItemProDec> getLastOrders(String orderId) {
		CustomerOrderItemProDec findParams = new CustomerOrderItemProDec();
		findParams.setNextOrderId(orderId);
		return customerOrderItemProDecDAO.get(findParams);
	}

	private int checkProDecUseStock(String itemDecId) {
		return customerOrderItemProDecDAO.checkProDecUseStock(itemDecId);
	}

	private int checkProDecIsLocked(String itemDecId, boolean isLocked) {
		CustomerOrderItemProDec findParams = new CustomerOrderItemProDec();
		findParams.setOrderItemDecId(itemDecId);
		findParams.setIsLocked(isLocked);
		return customerOrderItemProDecDAO.count(findParams);
	}

	/**
	 * <p>
	 * 获取上层orderTaskId关联的所有的ProDec
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-7-2 9:20:48
	 * @param orderTaskId 订单任务ID
	 * @return List<CustomerOrderItemProDec>
	 */
	public List<CustomerOrderItemProDec> getProDecByOrderTaskId(String orderTaskId) {
		return customerOrderItemProDecDAO.getProDecByOrderTaskId(orderTaskId);
	}

	/**
	 * 获取手动排程工序下的明细分解
	 */
	@Override
	public List<CustomerOrderItemProDec> getHandScheduleOrderItemProDec(String orderItemId, String processId) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("orderItemId", orderItemId);
		param.put("processId", processId);
		return customerOrderItemProDecDAO.getHandScheduleOrderItemProDec(param);
	}

	/**
	 * 分解产品明细到工序
	 */
	@Override
	@Transactional(readOnly = false)
	public void splitOrderByOrderItemId(String orderItemId, String orgCode) {
		// 缓存产品销售订单信息
		Map<String, SalesOrderItem> soiMap = new HashMap<String, SalesOrderItem>();
		Map<String, SalesOrder> salesOrderMap = new HashMap<String, SalesOrder>();
		// 查询出所有未开始的订单列表
		CustomerOrderItem coi = customerOrderItemService.getById(orderItemId);
		logger.info("正在分解订单,长度为:{}", coi.getOrderLength());
		SalesOrderItem salesOrderItem = salesOrderItemCache(coi, soiMap);
		String productCode = salesOrderItem.getProductCode();
		String contractNo = salesOrderCache(coi, salesOrderMap);
		// 订单上的工艺ID
		String craftsId = coi.getCraftsId();

		if (StringUtils.isBlank(productCode))
			throw new InconsistentException();
		List<ProductProcess> proProcessesList = productProcessService.getByProductCraftsId(craftsId);

		// 根据工艺代码 查询出该产品工艺流程工序按加工顺序倒序排列？最好这里直接加载每道工序的投入和产出的半成品和成品 TODO
		// 注意查询结果排序问题
		// 比较工艺是否发生变化,若发生变化则删除上次分解数据记录
		List<CustomerOrderItemDec> coids = coi.getCusOrderItemDesc();
		if (!CollectionUtils.isEmpty(coids)) {
			CustomerOrderItemDec itemDec = coids.get(0);
			List<CustomerOrderItemProDec> proDecList = itemDec.getCusOrderItemProDesList();
			if (!CollectionUtils.isEmpty(proDecList)) {
				CustomerOrderItemProDec itemProDec = proDecList.get(0);
				if (!craftsId.equals(itemProDec.getCraftsId())) {
					customerOrderItemProDecDAO.deleteByOrderItemId(coi.getId(), WebConstants.ROOT_ID);
				}
			}
		}

		for (CustomerOrderItemDec coid : coids) {
			// 如果 客户订单明细分解 状态为已完成 则不分解
			if (coid.getStatus() == ProductOrderStatus.FINISHED)
				continue;
			// 判断是否已锁定 如果已锁定则不分解
			if (checkProDecIsLocked(coid.getId(), true) > 0) {
				continue;
			}
			// 查询当前item是否已经分解过
			List<CustomerOrderItemProDec> coipdList = coid.getCusOrderItemProDesList();

			Map<String, List<CustomerOrderItemProDec>> updateOffsetLen = new HashMap<String, List<CustomerOrderItemProDec>>();

			if (!CollectionUtils.isEmpty(coipdList)) {
				// 如果使用库存则重新进行分解
				if (checkProDecUseStock(coid.getId()) > 0) {
					customerOrderItemDecService.deleteCusOrderItemDecById(coid.getId(), WebConstants.ROOT_ID, "0");
				} else {
					// 如果已分解 未使用库存则不分解直接进行库存冲抵
					for (CustomerOrderItemProDec coipd : coipdList) {
						if (coipd.getIsLocked()) {
							break;
						}
						List<CustomerOrderItemProDec> proDecList = updateOffsetLen.get(coipd.getProcessId());
						if (proDecList == null) {
							proDecList = new ArrayList<CustomerOrderItemProDec>();
						}
						proDecList.add(coipd);
						updateOffsetLen.put(coipd.getProcessId(), proDecList);
					}
				}
			}

			CustomerOrderItemProDec coip = new CustomerOrderItemProDec();
			coip.setCraftsId(craftsId);
			coip.setUnFinishedLength(coid.getLength());
			coip.setHalfProductCode(productCode);
			coip.setUseStock(false);
			coip.setOrderItemDecId(coid.getId());
			coip.setProductCode(productCode);
			coip.setContractNo(contractNo);
			coip.setProductSpec(salesOrderItem.getProductSpec());
			coip.setOrgCode(orgCode);
			coip.setParrelCount(1);
			iteratorProcess(proProcessesList, coip, updateOffsetLen);
		}
	}

	/**
	 * <p>
	 * 根据主键更新固定设备equipCode
	 * </p>
	 * 
	 * @author DingXintao
	 * @param id 主键
	 * @param equipCode 设备编码
	 */
	public int updateFixEquipById(String id, String equipCode) {
		return customerOrderItemProDecDAO.updateFixEquipById(id, equipCode);
	}

	/**
	 * 获取分卷下未完成明细：根据分卷ID
	 * 
	 * @author DingXintao
	 * @param customerOrderItemDecId 分卷ID
	 */
	@Override
	public List<CustomerOrderItemProDec> getUncompleteOrderItemProDecByDecId(String customerOrderItemDecId) {
		return customerOrderItemProDecDAO.getUncompleteOrderItemProDecByDecId(customerOrderItemDecId);
	}

	/**
	 * 
	 * <p>
	 * 排生产单 : 根据工序、客户订单ID获取工序列表 *****成缆工段*****
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processSection 工段
	 * @param orderItemIdArray 客户订单ID数组
	 * @param processType 工序类型：成缆/护套
	 * 
	 * @return List<CustomerOrderItemProDec>
	 */
	public List<CustomerOrderItemProDec> getOrderProcessByCodeCLHT(String processSection, String[] orderItemIdArray,
			String preWorkOrderNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("processSection", processSection);
		param.put("orderItemIdArray", orderItemIdArray);
		param.put("workOrderNo", preWorkOrderNo);
		if (ProcessInformation.CL.equals(processSection)) {
			return customerOrderItemProDecDAO.getOrderProcessByCodeCL(param);
		} else if (ProcessInformation.HT.equals(processSection)) {
			return customerOrderItemProDecDAO.getOrderProcessByCodeHT(param);
		} else {
			return null;
		}
	}

	@Override
	public List<Map<String, String>> getFirstProcessByCode(String[] orderItemIdArray) {
		return customerOrderItemProDecDAO.getFirstProcessByCode(orderItemIdArray[0]);
	}

	/**
	 * 
	 * <p>
	 * 排生产单 : 根据工序、客户订单ID获取工序列表
	 * 1、根据工段查询不同的SQL；
	 * 2、
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processSection 工段
	 * @param orderItemIdArray 客户订单ID数组
	 * 
	 * @return List<CustomerOrderItemProDec>
	 */
	@Override
	public List<CustomerOrderItemProDec> getOrderProcessByCode(ProcessSection section, String[] orderItemIdArray, String preWorkOrderNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("section", section.getOrder());
		param.put("orderItemIdArray", orderItemIdArray);
		param.put("workOrderNo", preWorkOrderNo);
		if (ProcessSection.CL == section) {
			return customerOrderItemProDecDAO.getOrderProcessByCodeCL(param);
		} else if (ProcessSection.HT == section) {
			return customerOrderItemProDecDAO.getOrderProcessByCodeHT(param);
		}
		
		List<CustomerOrderItemProDec> tempProdDecList = new ArrayList<CustomerOrderItemProDec>();
		List<CustomerOrderItemProDec> prodDecList = customerOrderItemProDecDAO.getOrderProcessByCodeJY(param);
		Map<String, Integer> tempProcessMap = new HashMap<String, Integer>();
		Map<String, String> discrColorMap = new HashMap<String, String>(); // 保存辨别色，输出与IN
																			// 对应，才能找到对应的输入
		// 按照1#，2#线进行拆分
		for (CustomerOrderItemProDec customerOrderItemProDec : prodDecList) {
			if (InOrOut.OUT == customerOrderItemProDec.getInOrOut()) {
				customerOrderItemProDec.setDiscrColor(customerOrderItemProDec.getColor());
				discrColorMap.put(customerOrderItemProDec.getProcessId(), customerOrderItemProDec.getColor());
				// 云母绕包直接返回
				if (StringUtils.isNotBlank(customerOrderItemProDec.getColor())
						&& !"wrapping_ymd".equals(customerOrderItemProDec.getProcessCode())) {
					Matcher matcher = pattern.matcher(customerOrderItemProDec.getColor());
					if (matcher.find()) {
						if (matcher.groupCount() >= 3) {
							int loopCount = Integer.valueOf(matcher.group(2));
							String color = matcher.group(3);
							// 保存每道工序的芯数
							tempProcessMap.put(customerOrderItemProDec.getProcessId(), loopCount);
							discrColorMap.put(customerOrderItemProDec.getProcessId(), color);
							for (int i = 1; i <= loopCount; i++) {
								try {
									CustomerOrderItemProDec temp = (CustomerOrderItemProDec) BeanUtils
											.cloneBean(customerOrderItemProDec);
									temp.setColor(i + "#" + color);
									temp.setDiscrColor(i + "#" + color);
									tempProdDecList.add(temp);
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InstantiationException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								} catch (NoSuchMethodException e) {
									e.printStackTrace();
								}
							}
						} else { // 匹配到了，但是不守规则
							tempProdDecList.add(customerOrderItemProDec);
						}
					} else { // 没有匹配到颜色
						tempProdDecList.add(customerOrderItemProDec);
					}
				} else { // 没有颜色
					tempProdDecList.add(customerOrderItemProDec);
				}
			}
		}
		// 生成投入物料需求
		// if (tempProcessMap.keySet().size() > 0)
		getAllProcessInMat(prodDecList, tempProdDecList, tempProcessMap, discrColorMap);

		if (prodDecList.size() == 0)
			return prodDecList;
		else
			return tempProdDecList;
	}

	private List<CustomerOrderItemProDec> getAllProcessInMat(List<CustomerOrderItemProDec> origList,
			List<CustomerOrderItemProDec> destList, Map<String, Integer> processIdSplitCountMap,
			Map<String, String> discrColorMap) {
		for (CustomerOrderItemProDec customerOrderItemProDec : origList) {
			if (InOrOut.IN == customerOrderItemProDec.getInOrOut()) {
				if (null != processIdSplitCountMap.get(customerOrderItemProDec.getProcessId())) {
					int loopCount = processIdSplitCountMap.get(customerOrderItemProDec.getProcessId());
					for (int i = 1; i <= loopCount; i++) {
						try {
							CustomerOrderItemProDec temp = (CustomerOrderItemProDec) BeanUtils
									.cloneBean(customerOrderItemProDec);
							temp.setDiscrColor(i + "#" + discrColorMap.get(customerOrderItemProDec.getProcessId()));
							destList.add(temp);
						} catch (IllegalAccessException e) {
							logger.error(e.getMessage());
						} catch (InstantiationException e) {
							logger.error(e.getMessage());
						} catch (InvocationTargetException e) {
							logger.error(e.getMessage());
						} catch (NoSuchMethodException e) {
							logger.error(e.getMessage());
						}
					}
				} else { // 说明没有颜色分解，直接放进投入列表
					customerOrderItemProDec.setDiscrColor(discrColorMap.get(customerOrderItemProDec.getProcessId()));
					destList.add(customerOrderItemProDec);
				}
			}
		}
		return destList;
	}

	@Override
	public void insertOrderItemProDec(String orderItemId, String processId, String userCode) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("orderItemId", orderItemId);
		param.put("processId", processId);
		param.put("userCode", userCode);

		customerOrderItemProDecDAO.insertOrderItemProDec(param);
	}

	@Override
	public int changeTask(Map<String, Object> params) {
		return customerOrderItemProDecDAO.changeTask(params);
	}

	/**
	 * PRO_DEC的数据补充，查询产出的质量参数等
	 * 
	 * @param processCode 工序编码，判断工段
	 * @param id PRO_DEC主键
	 * @param processId 工序ID
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getPatchOutAttrDesc(String processCode, String id, String processId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("processId", processId);
		if (processCode.equals("wrapping_ymd") || processCode.equals("Extrusion-Single")
				|| processCode.equals("Respool")) {
			return customerOrderItemProDecDAO.getPatchOutAttrDescJY(params);
		} else if (processCode.equals("Braiding") || processCode.equals("wrapping") || processCode.equals("Cabling")
				|| processCode.equals("Twisting")) {
			return customerOrderItemProDecDAO.getPatchOutAttrDescCL(params);
		} else if (processCode.equals("shield") || processCode.equals("Jacket-Extrusion")) {
			return customerOrderItemProDecDAO.getPatchOutAttrDescHT(params);
		}
		return null;
	}

	/**
	 * 对prodec的关联ID：RelateOrderIds做补充
	 * 
	 * @param workOrderNo 生产单号
	 * @return List<Map<String, String>>
	 */
	@Override
	public List<Map<String, String>> patchProdecRelateOrderIds(String workOrderNo) {
		return customerOrderItemProDecDAO.patchProdecRelateOrderIds(workOrderNo);
	}

	/**
	 * 更新生产单状态：1、workOrder 2、orderTask 3、proDec
	 * 
	 * @param param workOrderNo:生产单号;status:状态
	 * */
	@Override
	public void updateWorkerOrderStatus(Map<String, Object> param) {
		customerOrderItemProDecDAO.updateWorkerOrderStatus(param);
	}

	@Override
	public List<CustomerOrderItemProDec> getByTodoCusOrderItemId(
			String cusOrderItemId) {		
		return customerOrderItemProDecDAO.getByTodoCusOrderItemId(cusOrderItemId);
	}
}