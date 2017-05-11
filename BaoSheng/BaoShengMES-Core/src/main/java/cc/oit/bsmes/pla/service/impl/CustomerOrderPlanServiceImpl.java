/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */
package cc.oit.bsmes.pla.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.WorkTask;
import cc.oit.bsmes.fac.service.WorkTaskService;
import cc.oit.bsmes.pla.dao.MaterialRequirementPlanDAO;
import cc.oit.bsmes.pla.dto.CraftDto;
import cc.oit.bsmes.pla.dto.ProcessDto;
import cc.oit.bsmes.pla.handler.ForwardRecursionCraftHandler;
import cc.oit.bsmes.pla.handler.ReverseRecursionCraftHandler;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.model.OaMrp;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderPlanService;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.OaMrpService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.wip.dao.WorkOrderDAO;
import cc.oit.bsmes.wip.model.WorkOrder;

/**
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-1-2 上午9:40:26
 * @since
 * @version
 */
@Service
public class CustomerOrderPlanServiceImpl implements CustomerOrderPlanService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private CustomerOrderService customerOrderService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private WorkTaskService workTaskService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private EquipCalendarService equipCalendarService;
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	private OaMrpService oaMrpService;
	@Resource
	private MaterialRequirementPlanDAO materialRequirementPlanDAO;
	@Resource
	private WorkOrderDAO workOrderDAO;

	private ResourceCache resourceCache;
	private static Calendar now;
	/**
	 * 缓存客户生产订单 key:CustomerOrderId value:CustomerOrder
	 */
	private CacheMapThreadLocal<CustomerOrder> customerOrderCacheMap = new CacheMapThreadLocal<CustomerOrder>();
	private CacheMapThreadLocal<Date> orderPlanFinishedTimeCacheMap = new CacheMapThreadLocal<Date>();

	private ForwardRecursionCraftHandler forwardRecursionCraftHandler;
	private ReverseRecursionCraftHandler reverseRecursionCraftHandler;

	private List<String> firstTimeOrderItemIdList;

	private class CacheMapThreadLocal<T> extends ThreadLocal<Map<String, T>> {
		public CacheMapThreadLocal() {
			super();
		}

		@Override
		protected Map<String, T> initialValue() {
			return new HashMap<String, T>();
		}

		public T put(String key, T value) {
			Map<String, T> map = this.get();
			return map.put(key, value);
		}

		public T get(String key) {
			Map<String, T> map = this.get();
			return map.get(key);
		}
	}

	public void initOA(ResourceCache resourceCache, String orgCode) {
		now = Calendar.getInstance();
		// TODO 从第二天开始计算
		now.add(Calendar.DATE, 1);
		this.resourceCache = resourceCache;
		// 更新设备负载
		updateEquipWorkTask(orgCode);
		forwardRecursionCraftHandler = new ForwardRecursionCraftHandler(resourceCache, equipCalendarService, now);
		reverseRecursionCraftHandler = new ReverseRecursionCraftHandler(resourceCache, equipCalendarService, now);

		List<CustomerOrderItem> fitstTimeOrderItem = customerOrderItemService.getFirstTime(orgCode);
		firstTimeOrderItemIdList = new ArrayList<String>(fitstTimeOrderItem.size());
		for (CustomerOrderItem item : fitstTimeOrderItem) {
			if (orderTaskService.checkFirstTime(item.getProductCode(), item.getContractNo())) {
				firstTimeOrderItemIdList.add(item.getId());
			}
		}
	}

	private void updateEquipWorkTask(String orgCode) {
		workTaskService.deleteByOrgCodeForOA(orgCode);

		List<EquipInfo> equipList = resourceCache.getEquipInfoByOrgCode(orgCode);
		Iterator<EquipInfo> equipListIt = equipList.iterator();
		while (equipListIt.hasNext()) {
			EquipInfo equip = equipListIt.next();
			List<WorkTask> workTaskList = new ArrayList<WorkTask>();
			List<OrderTask> orderTaskList = equip.getOrderTasks();
			if (orderTaskList != null) {
				Iterator<OrderTask> orderTaskIt = orderTaskList.iterator();
				while (orderTaskIt.hasNext()) {
					OrderTask orderTask = orderTaskIt.next();
					WorkTask wt = new WorkTask();
					wt.setId(UUID.randomUUID().toString());
					wt.setEquipCode(equip.getCode());
					wt.setWorkStartTime(orderTask.getPlanStartDate());
					wt.setWorkEndTime(orderTask.getPlanFinishDate());
					wt.setOrderItemProDecId(orderTask.getOrderItemProDecId());
					workTaskList.add(wt);
				}
			}
			workTaskService.insert(workTaskList);
			equip.setWorkTasks(workTaskList);
		}

	}

	/**
	 * 客户订单OA推算
	 *
	 * @author QiuYangjun
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2014-1-2 上午9:40:27
	 * @see cc.oit.bsmes.pla.service.CustomerOrderPlanService#calculatorOA(ResourceCache,
	 *      String)
	 */
	@Override
	@Transactional(readOnly = false)
	public void calculatorOA(ResourceCache resourceCache, String orgCode) throws IllegalAccessException,
			InvocationTargetException {
		initOA(resourceCache, orgCode);
		// 已经占用设备产能的订单明细工序用时分解ID(卷)列表
		List<String> lockedOrderItemProDecIds = orderTaskService.getForOALocked(orgCode);
		// 缓存销售订单
		// Map<String,SalesOrderItem> salesOrderItemCacheMap=new
		// HashMap<String,SalesOrderItem>();
		// 缓存产品工艺结构
		// Map<String,CraftDto> craftsCacheMap=new HashMap<String,CraftDto>();
		// 缓存客户生产订单
		// Map<String,CustomerOrder> customerOrderCacheMap=new
		// HashMap<String,CustomerOrder>();

		List<CustomerOrderItem> customerOrderItemList = customerOrderItemService.getUncompleted(orgCode, null);
		int i = 0;
		logger.debug("********* 总共{}个任务*************", customerOrderItemList.size());
		for (CustomerOrderItem orderItem : customerOrderItemList) {
			logger.debug("========ORDER ITEM ID{}开始第{}个============", orderItem.getId(), i);

			CustomerOrder order = getCustomerOrder(orderItem);

			// 判断是否是第一次生产
			if (firstTimeOrderItemIdList.contains(orderItem.getId())) {
				orderItem.setIsFirstTime(true);
			}
			Date orderFinishedDate = orderPlanFinishedTimeCacheMap.get(order.getId());
			// 判断是否固定OA
			if (orderItem.getSubOaDate() != null) {
				// 如果是固定OA,逆向工序计算
				Date orderItemFinishedDate = calculatorFixedOA(order, orderItem, lockedOrderItemProDecIds);
				// 将item的最后完成时间与订单的完成时间作对比,取最晚的那个时间
				if (orderFinishedDate == null || orderFinishedDate.before(orderItemFinishedDate)) {
					orderPlanFinishedTimeCacheMap.put(order.getId(), orderItemFinishedDate);
				}
			} else {
				// 如果不是固定OA,正向工序计算
				Date orderItemFinishedDate = orderFinishedDate = calculatorUnFixedOA(order, orderItem,
						lockedOrderItemProDecIds);
				// 将item的最后完成时间与订单的完成时间作对比,取最晚的那个时间
				if (orderFinishedDate == null || orderFinishedDate.before(orderItemFinishedDate)) {
					orderPlanFinishedTimeCacheMap.put(order.getId(), orderItemFinishedDate);
				}
			}
			logger.debug("========ORDER ITEM ID{} 结束第{}个============", orderItem.getId(), i);
			i++;
		}
		finishedCalculatorOA();
	}

	private CustomerOrder getCustomerOrder(CustomerOrderItem orderItem) {
		CustomerOrder order = customerOrderCacheMap.get(orderItem.getCustomerOrderId());
		if (order == null) {
            order = customerOrderService.getById(orderItem.getCustomerOrderId());
            customerOrderCacheMap.put(orderItem.getCustomerOrderId(), order);
        }
		return order;
	}

	private void finishedCalculatorOA() {
		Map<String, CustomerOrder> orderMap = customerOrderCacheMap.get();
		Set<Entry<String, CustomerOrder>> entrySet = orderMap.entrySet();
		for (Entry<String, CustomerOrder> entry : entrySet) {
			CustomerOrder order = entry.getValue();
			if (order.getPlanFinishDate() != null) {
				order.setLastOa(order.getPlanFinishDate());
			}
			order.setPlanFinishDate(orderPlanFinishedTimeCacheMap.get(order.getId()));
			// 超交期预警
			if (order.getCustomerOaDate() != null && order.getPlanFinishDate() != null
					&& order.getCustomerOaDate().before(order.getPlanFinishDate())) {
				EventInformation eventInformation = new EventInformation();
				eventInformation.setCode(EventTypeContent.OT.name());
				eventInformation.setEventTitle("合同超交期");
				eventInformation.setEventContent("合同号:" + order.getContractNo() + ",超指定交期");
				eventInformation.setEventStatus(EventStatus.UNCOMPLETED);
				eventInformationService.insertInfo(eventInformation);
			}
			customerOrderService.update(order);
		}
	}

	/**
	 * 计算有固定OA的订单交期
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-3 下午5:42:08
	 * @param order
	 * @param orderItem
	 * @param lockedOrderItemProDecIds
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @see
	 */
	private Date calculatorFixedOA(CustomerOrder order, CustomerOrderItem orderItem,
			List<String> lockedOrderItemProDecIds) throws IllegalAccessException, InvocationTargetException {
		List<CustomerOrderItemDec> cusOrderItemDescList = orderItem.getCusOrderItemDesc();
		// 工序和订单明细工序用时分解(卷)的缓存,避免多次嵌套循环
		Map<String, List<CustomerOrderItemProDec>> processCustomerOrderItemProDecCache = new HashMap<String, List<CustomerOrderItemProDec>>();
		if (cusOrderItemDescList != null && cusOrderItemDescList.size() != 0) {
			for (CustomerOrderItemDec itemDesc : cusOrderItemDescList) {
				// 将每一卷和工序绑定,用于计算每一步工序上的设备使用情况
				List<CustomerOrderItemProDec> customerOrderItemProDecList = itemDesc.getCusOrderItemProDesList();
				if (customerOrderItemProDecList != null) {
					for (CustomerOrderItemProDec proDec : customerOrderItemProDecList) {
						String processId = proDec.getProcessId();
						List<CustomerOrderItemProDec> processProDecList = processCustomerOrderItemProDecCache
								.get(processId);
						if (processProDecList == null) {
							processProDecList = new ArrayList<CustomerOrderItemProDec>();
						}
						processProDecList.add(proDec);
						processCustomerOrderItemProDecCache.put(processId, processProDecList);
					}
				}
			}
		}
		//得到工艺
		ProductCrafts productCrafts = StaticDataCache.getCrafts(orderItem.getCraftsId());
		CraftDto craft = new CraftDto();

		//把工艺流程改造成工艺树，并把任务和设备负载挂上去
		convertCraft(craft, productCrafts, resourceCache.getProductProcessByCraftId(productCrafts.getId()),
				processCustomerOrderItemProDecCache);

		//设置指定交期
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(orderItem.getSubOaDate());

		// TODO 指定交期暂时以指定日期的16点为结束时间
		endTime.set(endTime.get(Calendar.YEAR), endTime.get(Calendar.MONTH), endTime.get(Calendar.DATE), 16, 0, 0);

		// TODO 确认指定交期是生产完成时间还是客户交货时间后再做修改
		// endTime.add(Calendar.DATE, -3);
		List<String> lockedtmp = new ArrayList<String>();
		lockedtmp.addAll(lockedOrderItemProDecIds);

		//复杂产品第一次生产该产品提前2个星期
		Product product = resourceCache.getProductByCode(orderItem.getProductCode());
		if (product != null) {
			// 复杂产品提早生产
			if (product.getComplex() || orderItem.getIsFirstTime()) {
				endTime.add(Calendar.DATE, BusinessConstants.FIRST_TIME_EARLY_DATE);
			}
		} else {
			// 产品查询出错
			throw new MESException();
		}

		Map<String, List<WorkTask>> equipListWorkTaskCache = new HashMap<String, List<WorkTask>>();
		Date finishedDate = reverseRecursionCraftHandler.process(resourceCache, craft.getProcess(), lockedtmp,
				endTime.getTime(), equipListWorkTaskCache);
		// 若果返回值为null 说明原始OA时间内无法完成任务,从第一个工序从新开始计算OA时间
		if (finishedDate == null) {

			equipListWorkTaskCache.clear();

			finishedDate = forwardRecursionCraftHandler.process(resourceCache, craft.getProcess(),
					lockedOrderItemProDecIds, now.getTime(), equipListWorkTaskCache);
		} else {
			lockedOrderItemProDecIds.clear();
			lockedOrderItemProDecIds.addAll(lockedtmp);
		}

		finishedCalculator(order, equipListWorkTaskCache, processCustomerOrderItemProDecCache, craft, orderItem,
				finishedDate);

		return finishedDate;
	}

	private void finishedCalculator(CustomerOrder order, Map<String, List<WorkTask>> equipListWorkTaskCache,
			Map<String, List<CustomerOrderItemProDec>> processCustomerOrderItemProDecCache, CraftDto craft,
			CustomerOrderItem orderItem, Date finishedDate) {
		logger.debug("========WORK TASK 信息============");
		// logger.debug("设备,卷ID,开始时间,结束时间,用时");
		Iterator<String> equipListWorkTaskKeyIterator = equipListWorkTaskCache.keySet().iterator();

		/**
		 * [计划开始时间的更新]
		 * 问题：因为原计划开始时间初始设置为原来的开始时间，但计算后的开始时间后延了，对于都未开始的订单应更新之，后面逻辑实未更新。
		 * 修改：orderItem中有已经开始或者完成的proDec
		 * ，即是否状态为生产中，计划开始时间初始设置成原来的开始时间；若没有，则设置为null重新计算更新。 注：①
		 * 最终若还为null，则置为原来的计划开始时间
		 * 
		 * @Author DingXintao
		 * @Time 2014-09-17 10:30
		 * */
		Date orderItemStartDate = null;
		if (ProductOrderStatus.IN_PROGRESS.equals(orderItem.getStatus())) {
			orderItemStartDate = orderItem.getPlanStartDate();
		}

		while (equipListWorkTaskKeyIterator.hasNext()) {
			List<WorkTask> wtList = equipListWorkTaskCache.get(equipListWorkTaskKeyIterator.next());
			Iterator<WorkTask> workTaskIterator = wtList.iterator();
			while (workTaskIterator.hasNext()) {
				WorkTask wt = workTaskIterator.next();
				if (orderItemStartDate == null) {
					orderItemStartDate = wt.getWorkStartTime();
				} else {
					if (orderItemStartDate.after(wt.getWorkStartTime())) {
						orderItemStartDate = wt.getWorkStartTime();
					}
				}
				if (StringUtils.isBlank(wt.getId())) {
					wt.setDescription(order.getProductCode());
					workTaskService.insert(wt);
				}
				Iterator<String> processCustomerOrderItemProDecKeyIterator = processCustomerOrderItemProDecCache
						.keySet().iterator();
				while (processCustomerOrderItemProDecKeyIterator.hasNext()) {
					List<CustomerOrderItemProDec> customerOrderItemProDecList = processCustomerOrderItemProDecCache
							.get(processCustomerOrderItemProDecKeyIterator.next());
					Iterator<CustomerOrderItemProDec> customerOrderItemProDecIterator = customerOrderItemProDecList
							.iterator();
					while (customerOrderItemProDecIterator.hasNext()) {
						CustomerOrderItemProDec dec = customerOrderItemProDecIterator.next();
						if (StringUtils.equals(dec.getId(), wt.getOrderItemProDecId())) {
							dec.setLatestStartDate(wt.getWorkStartTime());
							dec.setLatestFinishDate(wt.getWorkEndTime());
							dec.setEquipCode(wt.getEquipCode());
							customerOrderItemProDecService.update(dec);

							if (orderItemStartDate == null) {
								orderItemStartDate = dec.getLatestStartDate();
							} else {
								if (orderItemStartDate.after(dec.getLatestStartDate())) {
									orderItemStartDate = dec.getLatestStartDate();
								}
							}

							// logger.debug("{},{},{},{},{},{},{}",wt.getEquipCode(),
							// dec.getId(),
							// DateUtils.convert(dec.getLatestStartDate(),
							// DateUtils.DATE_TIME_FORMAT),
							// DateUtils.convert(dec.getLatestFinishDate(),
							// DateUtils.DATE_TIME_FORMAT)
							// ,dec.getLatestFinishDate().getTime()-dec.getLatestStartDate().getTime(),
							// dec.getLatestStartDate().getTime(),
							// dec.getLatestFinishDate().getTime());
						}

					}

				}
			}
		}

		/**
		 * 见：[计划开始时间的更新]/①
		 * */
		if (orderItemStartDate == null) {
			orderItemStartDate = orderItem.getPlanStartDate();
		}

		if (orderItem.getPlanFinishDate() != null) {
			orderItem.setLastOa(orderItem.getPlanFinishDate());
		}
		orderItem.setPlanStartDate(orderItemStartDate);
		orderItem.setPlanFinishDate(finishedDate);
		customerOrderItemService.update(orderItem);

		if (order.getPlanStartDate() == null) {
			order.setPlanStartDate(orderItem.getPlanStartDate());
		} else {
			if (order.getPlanStartDate().after(orderItem.getPlanStartDate())) {
				order.setPlanStartDate(orderItem.getPlanStartDate());
			}
		}
		oaMrpService.deleteByContractNoOrderItemIdOrgCode(orderItem.getCustomerOrder().getContractNo(),
				orderItem.getId(), orderItem.getCustomerOrder().getOrgCode());
		calculatorMRP(orderItem, craft.getProcess(), equipListWorkTaskCache);

		mergerWorkTask(craft.getProcess(), equipListWorkTaskCache);
	}

	/**
	 * 
	 * <p>
	 * 计算物料需求
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-4-15 上午11:48:59
	 * @see
	 */
	private void calculatorMRP(CustomerOrderItem orderItem, ProcessDto process,
			Map<String, List<WorkTask>> equipListWorkTaskCache) {

		List<ProcessDto> preProcessList = process.getPreProcesses();
		if (!CollectionUtils.isEmpty(preProcessList)) {
			for (ProcessDto pro : preProcessList) {
				calculatorMRP(orderItem, pro, equipListWorkTaskCache);
			}
		}

		// 计算物料需求
		// 计算工序所有未生产长度之和
		List<CustomerOrderItemProDec> prodecList = process.getOrderItemProDecList();
		Iterator<CustomerOrderItemProDec> proDecIt = prodecList.iterator();

		Set<Entry<String, List<WorkTask>>> entrySet = equipListWorkTaskCache.entrySet();
		BigDecimal length = BigDecimal.ZERO;
		Date planDate = null;
		String equipCode = null;
		while (proDecIt.hasNext()) {
			CustomerOrderItemProDec dec = proDecIt.next();
			Iterator<Entry<String, List<WorkTask>>> entryIter = entrySet.iterator();
			while (entryIter.hasNext()) {
				Entry<String, List<WorkTask>> entry = entryIter.next();
				if (entry != null && entry.getValue() != null && !entry.getValue().isEmpty()) {
					Iterator<WorkTask> workTaskIt = entry.getValue().iterator();
					while (workTaskIt.hasNext()) {
						WorkTask workTask = workTaskIt.next();
						if (StringUtils.equalsIgnoreCase(dec.getId(), workTask.getOrderItemProDecId())) {
							length = length.add(new BigDecimal(dec.getUnFinishedLength()));
							if (planDate == null || planDate.after(workTask.getWorkStartTime())) {
								planDate = workTask.getWorkStartTime();
							}
							equipCode = dec.getEquipCode();
						}
					}
				}
			}
		}
		if (length.intValue() > 0) {
			// 根据工序ID查询出工序所有投入的物料
			List<ProcessInOut> inList = StaticDataCache.getByProcessId(process.getId());
			inLoop: for (ProcessInOut in : inList) {
				if (in.getInOrOut() == InOrOut.OUT) {
					continue inLoop;
				}
				if (!MatType.MATERIALS.equals(in.getMat().getMatType())) {
					continue inLoop;
				}
				BigDecimal totalCount = length.multiply(new BigDecimal(in.getQuantity())).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
				OaMrp mrp = new OaMrp();
				mrp.setContractNo(orderItem.getCustomerOrder().getContractNo());
				mrp.setOrderItemId(orderItem.getId());
				mrp.setMatCode(in.getMatCode());
				mrp.setProcessCode(process.getProcessCode());
				mrp.setPlanDate(planDate);
				mrp.setStatus(MaterialStatus.UNAUDITED);
				mrp.setQuantity(totalCount.doubleValue());
				mrp.setUnit(in.getUnit());
				mrp.setOrgCode(orderItem.getCustomerOrder().getOrgCode());
				mrp.setProductCode(orderItem.getProductCode());
				mrp.setEquipCode(equipCode);
				oaMrpService.insert(mrp);
			}

			// System.out.println("-------------我来更新了----------------");
			/**
			 * [物料需求计划更新] 问题：OA重新计算没有更新T_PLA_MRP_OA物料需求。
			 * 修改：OA重新计算时，根据WORK_ORDER_ID和PROCESS_CODE更新PLAN_DATE(需求日期)。
			 * 注：为了性能直接单独使用新的DAO接口，不使用查询再更新了。
			 * 
			 * @Author DingXintao
			 * @Time 2014-10-27 10:30
			 * */
			WorkOrder findParam = new WorkOrder();
			findParam.setId(orderItem.getId());
			findParam.setProcessCode(process.getProcessCode());
			findParam.setEquipCode(equipCode);
			findParam.setOrgCode(SessionUtils.getUser() == null ? "" : SessionUtils.getUser().getOrgCode());
			// 1、获取WORK_ORDER_ID *难
			List<WorkOrder> mrpOrderList = workOrderDAO.getListForUpMrpOfCalculatorOA(findParam);
			if (!CollectionUtils.isEmpty(mrpOrderList)) {
				MaterialRequirementPlan mrp = null;
				for (WorkOrder workOrder : mrpOrderList) {
					mrp = new MaterialRequirementPlan();
					mrp.setWorkOrderId(workOrder.getWorkOrderId());
					mrp.setEquipCode(equipCode);
					mrp.setProcessCode(process.getProcessCode());
					mrp.setPlanDate(planDate);
					materialRequirementPlanDAO.updateForCalculatorOA(mrp);
				}
			}

		}

	}

	private void mergerWorkTask(ProcessDto process, Map<String, List<WorkTask>> equipListWorkTaskCache) {

		List<ProcessDto> preProcessList = process.getPreProcesses();
		if (!CollectionUtils.isEmpty(preProcessList)) {
			for (ProcessDto pro : preProcessList) {
				mergerWorkTask(pro, equipListWorkTaskCache);
			}

			List<EquipInfo> equipInfoList = process.getAvailableEquips();
			if (equipInfoList != null) {
				Iterator<EquipInfo> equipInfoIterator = equipInfoList.iterator();
				while (equipInfoIterator.hasNext()) {
					EquipInfo equip = equipInfoIterator.next();
					List<WorkTask> newWorkTaskList = equipListWorkTaskCache.get(equip.getCode());
					if (newWorkTaskList != null) {
						List<WorkTask> equipExistsWorkTaskList = equip.getWorkTasks();
						if (equipExistsWorkTaskList != null) {
							Iterator<WorkTask> it = newWorkTaskList.iterator();
							while (it.hasNext()) {
								WorkTask newWorkTask = it.next();
								boolean flg = true;
								Iterator<WorkTask> existsIt = equipExistsWorkTaskList.iterator();
								while (existsIt.hasNext()) {
									WorkTask exists = existsIt.next();
									if (StringUtils.equalsIgnoreCase(newWorkTask.getOrderItemProDecId(),
											exists.getOrderItemProDecId())) {
										flg = false;
									}
								}
								if (flg) {
									equip.getWorkTasks().add(newWorkTask);
								}
							}
						} else {
							if (newWorkTaskList != null) {
								equip.setWorkTasks(newWorkTaskList);
								equipListWorkTaskCache.remove(equip.getCode());
							}
						}
					}
				}
			}

		} else {
			List<EquipInfo> equipInfoList = process.getAvailableEquips();
			if (equipInfoList != null) {
				Iterator<EquipInfo> equipInfoIterator = equipInfoList.iterator();
				while (equipInfoIterator.hasNext()) {
					EquipInfo equip = equipInfoIterator.next();
					List<WorkTask> newWorkTaskList = equipListWorkTaskCache.get(equip.getCode());
					if (newWorkTaskList != null) {
						List<WorkTask> equipExistsWorkTaskList = equip.getWorkTasks();
						if (equipExistsWorkTaskList != null) {
							Iterator<WorkTask> it = newWorkTaskList.iterator();
							while (it.hasNext()) {
								WorkTask newWorkTask = it.next();
								boolean flg = true;
								Iterator<WorkTask> existsIt = equipExistsWorkTaskList.iterator();
								while (existsIt.hasNext()) {
									WorkTask exists = existsIt.next();
									if (StringUtils.equalsIgnoreCase(newWorkTask.getOrderItemProDecId(),
											exists.getOrderItemProDecId())) {
										flg = false;
									}
								}
								if (flg) {
									equip.getWorkTasks().add(newWorkTask);
								}
							}
						} else {
							if (newWorkTaskList != null) {
								equip.setWorkTasks(newWorkTaskList);
								equipListWorkTaskCache.remove(equip.getCode());
							}
						}
					}
				}
			}

		}
	}

	/**
	 * 计算有无固定OA的订单交期
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-3 下午5:44:51
	 * @param order
	 * @param orderItem
	 * @param lockedOrderItemProDecIds
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @see
	 */
	private Date calculatorUnFixedOA(CustomerOrder order, CustomerOrderItem orderItem,
			List<String> lockedOrderItemProDecIds) throws IllegalAccessException, InvocationTargetException {
		List<CustomerOrderItemDec> cusOrderItemDescList = orderItem.getCusOrderItemDesc();
		// 工序和订单明细工序用时分解(卷)的缓存,避免多次嵌套循环
		Map<String, List<CustomerOrderItemProDec>> processCustomerOrderItemProDecCache = new HashMap<String, List<CustomerOrderItemProDec>>();

		if (cusOrderItemDescList != null && cusOrderItemDescList.size() != 0) {
			for (CustomerOrderItemDec itemDesc : cusOrderItemDescList) {
				// 将每一卷和工序绑定,用于计算每一步工序上的设备使用情况
				List<CustomerOrderItemProDec> customerOrderItemProDecList = itemDesc.getCusOrderItemProDesList();
				if (customerOrderItemProDecList != null) {
					for (CustomerOrderItemProDec proDec : customerOrderItemProDecList) {
						String processId = proDec.getProcessId();
						List<CustomerOrderItemProDec> processProDecList = processCustomerOrderItemProDecCache
								.get(processId);
						if (processProDecList == null) {
							processProDecList = new ArrayList<CustomerOrderItemProDec>();
						}
						processProDecList.add(proDec);
						processCustomerOrderItemProDecCache.put(processId, processProDecList);
					}
				}
			}
		}
		ProductCrafts productCrafts = StaticDataCache.getCrafts(orderItem.getCraftsId());
		CraftDto craft = new CraftDto();
		convertCraft(craft, productCrafts, resourceCache.getProductProcessByCraftId(productCrafts.getId()),
				processCustomerOrderItemProDecCache);

		Map<String, List<WorkTask>> equipListWorkTaskCache = new HashMap<String, List<WorkTask>>();

		Date finishedDate = forwardRecursionCraftHandler.process(resourceCache, craft.getProcess(),
				lockedOrderItemProDecIds, now.getTime(), equipListWorkTaskCache);

		finishedCalculator(order, equipListWorkTaskCache, processCustomerOrderItemProDecCache, craft, orderItem,
				finishedDate);

		// if(finishedDate!=null){
		// order.setOaDate(finishedDate);
		// }
		// if(order.getOaDate() == null){
		// order.setOaDate(finishedDate);
		// } else if (order.getOaDate() != null &&
		// order.getOaDate().before(finishedDate)) {
		// order.setOaDate(finishedDate);
		// }

		return finishedDate;
	}

	/**
	 * 将工艺结构转换成树形结构对象
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-2 下午3:22:22
	 * @param craftDto
	 * @param productCrafts 产品工艺
	 * @param productProcessList 按工艺倒序排列的工艺流程
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @see
	 *
	 *
	 */
	@Override
	public void convertCraft(CraftDto craftDto, ProductCrafts productCrafts, List<ProductProcess> productProcessList,
			Map<String, List<CustomerOrderItemProDec>> processCustomerOrderItemProDecCache)
			throws IllegalAccessException, InvocationTargetException {
		ProcessDto process = new ProcessDto();
		//最后一道工序
		BeanUtils.copyProperties(productProcessList.get(0), process);
		craftDto.setCraftCode(productCrafts.getCraftsCode());
		if (processCustomerOrderItemProDecCache != null
				&& processCustomerOrderItemProDecCache.get(process.getId()) != null) {
			//设置该工序的ProDec 和 设备
			process.setOrderItemProDecList(processCustomerOrderItemProDecCache.get(process.getId()));
			process.setAvailableEquips(this.resourceCache.getDefaultEquips(process.getId(), 0));
		}
		craftDto.setProcess(process);
		for (int i = 1; i < productProcessList.size(); i++) {
			ProcessDto processDto = new ProcessDto();
			BeanUtils.copyProperties(productProcessList.get(i), processDto);

			if (processCustomerOrderItemProDecCache != null
					&& processCustomerOrderItemProDecCache.get(processDto.getId()) != null) {
				processDto.setOrderItemProDecList(processCustomerOrderItemProDecCache.get(processDto.getId()));
				processDto.setAvailableEquips(this.resourceCache.getDefaultEquips(processDto.getId(), 0));
			}

			if (!craftDto.getProcess().addPreProcess(processDto)) {
				craftDto.getProcess().addNextProcess(processDto);
			}
		}
	}
}
