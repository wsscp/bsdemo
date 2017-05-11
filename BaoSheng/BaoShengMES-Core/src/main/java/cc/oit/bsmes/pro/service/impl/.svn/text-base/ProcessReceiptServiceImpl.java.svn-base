package cc.oit.bsmes.pro.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import jxl.Cell;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.common.constants.QADetectType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JxlUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.interfacePLM.dao.CanShuKuDAO;
import cc.oit.bsmes.interfacePLM.model.CanShuKu;
import cc.oit.bsmes.interfacePLM.service.impl.PrcvServiceImpl;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.pro.dao.EquipListDAO;
import cc.oit.bsmes.pro.dao.ProcessReceiptDAO;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProcessQcValue;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import cc.oit.bsmes.pro.service.ProcessQcValueService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ReceiptService;
import cc.oit.bsmes.wip.service.WorkOrderService;

@Service
public class ProcessReceiptServiceImpl extends BaseServiceImpl<ProcessReceipt> implements ProcessReceiptService {

	@Resource
	private ProcessReceiptDAO processReceiptDAO;
	@Resource
	private DataAcquisitionService dataAcquisitionService;
	@Resource
	private EquipMESWWMappingService equipMESWWMappingService;
	@Resource
	private EquipListDAO equipListDAO;
	@Resource
	private CanShuKuDAO canShuKuDAO;
	@Resource
	private ReceiptService receiptService;
	@Resource
	private ProcessQcValueService processQcValueService;
	@Resource
	private WorkOrderService workOrderService;

	@Override
	public List<ProcessReceipt> getByEqipListId(String eqipListId) {
		ProcessReceipt findParams = new ProcessReceipt();
		findParams.setEqipListId(eqipListId);
		return processReceiptDAO.get(findParams);
	}

	/**
	 * 终端页面获取操作参数（难） <br/>
	 * 1、获取设备的所有下发参数和采集参数；<br/>
	 * 2、获取采集参数的所有实时采集数据； ；<br/>
	 * 3、获取设备的下发参数的下发值；；<br/>
	 * 4、返回设备的所有下发参数，将对应采集值/下发值set进去。<br/>
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单号
	 * @param equipCode 设备编码
	 * 
	 * */
	@Override
	public List<ProcessReceipt> getByWorkOrderNo(String workOrderNo, String equipCode) {
		// 1、查询MES_www设备映射参数
		EquipInfo mainEquipInfo = StaticDataCache.getMainEquipInfo(equipCode); // 获取主设备
		EquipMESWWMapping findParams = new EquipMESWWMapping();
		findParams.setEquipCode(mainEquipInfo.getCode());
		List<EquipMESWWMapping> mappingArray = equipMESWWMappingService.findByObj(findParams);
		// 2、定义显示下发数组isArray/数采参数数组daArray
		List<ProcessReceipt> isArray = new ArrayList<ProcessReceipt>(); // 下发的参数
		List<Receipt> daArray = new ArrayList<Receipt>(); // 采集参数
		// 3、将参数集mappingArray封装到isArray、daArray
		for (EquipMESWWMapping mapping : mappingArray) {
			if (mapping.getNeedIs() && mapping.getNeedShow()) { // 下发的参数
				ProcessReceipt receipt = new ProcessReceipt();
				receipt.setReceiptCode(mapping.getParmCode());
				receipt.setReceiptName(mapping.getParmName());
				isArray.add(receipt);
			} else if (mapping.getNeedDa()) { // 采集的
				Receipt receipt = new Receipt();
				receipt.setReceiptCode(mapping.getParmCode());
				receipt.setEquipCode(mapping.getEquipCode());
				daArray.add(receipt);
			}
		}
		// 4、接入外部接口获取质量参数实时数据，封装成map
		List<Receipt> receiptsResutl = dataAcquisitionService.queryLiveReceiptByCodes(daArray);
		Map<String, String> liveMap = new HashMap<String, String>();
		for (Receipt receipt : receiptsResutl) {
			liveMap.put(receipt.getReceiptCode().replace("R_", ""), receipt.getDaValue());
		}
		// 5、查询最新的下发参数数据，封装成map
		Map<String, String> lastIsMap = new HashMap<String, String>();
		List<Receipt> lastIsArray = receiptService.getLastReceiptList(workOrderNo);
		for (Receipt receipt : lastIsArray) {
			lastIsMap.put(receipt.getReceiptCode(), receipt.getReceiptTargetValue());
		}
		// 6、实时数据/最新下发参数:set到isArray里面
		for (ProcessReceipt receipt : isArray) {
			receipt.setDaValue(liveMap.get(receipt.getReceiptCode().replace("M_", "").replace("W_", "")));
			receipt.setSetValue(lastIsMap.get(receipt.getReceiptCode()));

			/**
			 * 为了后面数据报表
			 */
			WorkOrder workOrder = workOrderService.getByWorkOrderNO(workOrderNo);
			if (null != workOrder && workOrder.getStatus() == WorkOrderStatus.IN_PROGRESS
					&& StringUtils.isNotBlank(WebConstants.receiptCodes.get(receipt.getReceiptCode()))) {
				ProcessQcValue processQcValue = new ProcessQcValue();
				processQcValue.setEqipCode(receipt.getEquipCode());
				processQcValue.setWorkOrderNo(workOrderNo);
				processQcValue.setCheckItemCode(receipt.getReceiptCode());
				processQcValue.setQcValue(receipt.getDaValue());
				processQcValue.setCreateUserCode("system");
				processQcValue.setModifyUserCode("system");
				processQcValue.setType(QADetectType.DA);
				processQcValueService.insert(processQcValue);
			}
		}
		// 7、要求值范围：难，缺少PLM对应关系，无法绑定
		// TO_DO
		return isArray;
	}

	/**
	 * 工艺参数报表： 获取设备的工艺参数实时数据
	 * 
	 * @param equipCode 设备编码
	 * @param processCode 工序编码
	 * */
	@Override
	public List<ProcessReceipt> getByEquipCodeAndProcessCode(String equipCode, String processCode) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("equipCode", equipCode);
		List<ProcessReceipt> processReceiptList = processReceiptDAO.getByEquipCodeAndProcessCode(findParams);
		if (processReceiptList != null && !processReceiptList.isEmpty()) {
			List<Receipt> receipts = new ArrayList<Receipt>();
			for (ProcessReceipt processReceipt : processReceiptList) {
				Receipt receipt = new Receipt();
				receipt.setReceiptCode(processReceipt.getReceiptCode());
				receipt.setEquipCode(equipCode);
				receipts.add(receipt);
			}
			// 接入外部接口获取质量参数实时数据
			List<Receipt> receiptsResutl = dataAcquisitionService.queryLiveReceiptByCodes(receipts);
			for (Receipt receipt : receiptsResutl) {
				for (ProcessReceipt processReceipt : processReceiptList) {
					if (StringUtils.equalsIgnoreCase(receipt.getEquipCode(), processReceipt.getEquipCode())
							&& StringUtils.equalsIgnoreCase(receipt.getReceiptCode(), processReceipt.getReceiptCode())) {
						processReceipt.setDaValue(receipt.getDaValue());
					}
				}
			}
		}

		return processReceiptList;
	}

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-21 下午6:19:27
	 * @param eqipListId
	 * @param start
	 * @param limit
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProcessReceiptService#getByEqipListId(java.lang.String,
	 *      int, int)
	 */
	@Override
	public List<ProcessReceipt> getByEqipListId(String eqipListId, int start, int limit) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return getByEqipListId(eqipListId);
	}

	/**
	 * <p>
	 * 根据设备code和工艺code 工艺参数code 查找工艺参数信息
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-4-28 下午5:14:03
	 * @param equipCode
	 * @param processCode
	 * @param receiptCode
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProcessReceiptService#getByEquipCodeAndProcessCodeAndReceiptCode(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public ProcessReceipt getByEquipCodeAndProcessCodeAndReceiptCode(String equipCode, String processCode,
			String receiptCode) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("equipCode", equipCode);
		findParams.put("processCode", processCode);
		findParams.put("receiptCode", receiptCode);
		List<ProcessReceipt> alist = processReceiptDAO.getByEquipCodeAndProcessCodeAndReceiptCode(findParams);
		if (CollectionUtils.isEmpty(alist)) {
			return null;
		} else {
			return alist.get(0);
		}

	}

	@Override
	public void importProcessQc(List<Cell[]> qcList, String equipCode, String acEquipCode, String orgCode) {
		// 是否直接插入数据，一般用于测试或者第一次导入，后期可改为fase
		boolean isImmediateInsert = false;
		if (isImmediateInsert) {
			this.deleteAll();
		}
		/**
		 * 修改为根据equipCode查找EquipList
		 * 
		 * @author DingXintao
		 * @time 2014-11-17 10:47:00
		 * */
		List<EquipList> eqplist = equipListDAO.getByEquipCodeReal(equipCode); // 获取工序生产线列表
		// 循环excel行封装工艺对象
		for (int loop = 0; loop < qcList.size(); loop++) {
			Cell[] cells = qcList.get(loop);

			ProcessReceipt processReceipt = new ProcessReceipt();
			processReceipt.setAcEquipCode(acEquipCode);
			processReceipt.setReceiptCode(JxlUtils.getRealContents(cells[1]));
			processReceipt.setReceiptName(JxlUtils.getRealContents(cells[2]));
			processReceipt.setSubReceiptCode(JxlUtils.getRealContents(cells[1]));
			processReceipt.setSubReceiptName(JxlUtils.getRealContents(cells[2]));
			processReceipt.setReceiptTargetValue(JxlUtils.getRealContents(cells[3]));
			processReceipt.setReceiptMaxValue(JxlUtils.getRealContents(cells[4]));
			processReceipt.setReceiptMinValue(JxlUtils.getRealContents(cells[5]));
			String needDA = JxlUtils.getRealContents(cells[6]);
			if (!StringUtils.isEmpty(needDA) && "是".equalsIgnoreCase(needDA)) {
				processReceipt.setNeedDa(true);
				processReceipt.setNeedAlarm(true);
			} else {
				processReceipt.setNeedDa(false);
				processReceipt.setNeedAlarm(false);
			}

			String needIS = JxlUtils.getRealContents(cells[7]);
			if (!StringUtils.isEmpty(needIS) && "是".equalsIgnoreCase(needIS)) {
				processReceipt.setNeedIs(true);
			} else {
				processReceipt.setNeedIs(false);
			}

			String dataType = JxlUtils.getRealContents(cells[8]);
			if ("布尔型".equalsIgnoreCase(dataType)) {
				processReceipt.setDataType("BOOLEAN");

			} else if ("数字型".equalsIgnoreCase(dataType)) {
				processReceipt.setDataType("DOUBLE");
			} else {
				processReceipt.setDataType("STRING");
			}

			processReceipt.setDataUnit(JxlUtils.getRealContents(cells[9]));
			processReceipt.setCreateTime(new Date());
			processReceipt.setModifyTime(new Date());
			processReceipt.setCreateUserCode("admin");
			processReceipt.setModifyUserCode("admin");
			processReceipt.setHasPic(false);
			processReceipt.setNeedShow("是".equals(JxlUtils.getRealContents(cells[14])));
			processReceipt.setFrequence(10.0);

			if (!CollectionUtils.isEmpty(eqplist)) { // 更新工序生产线的工艺参数
				for (int k = 0; k < eqplist.size(); k++) {
					EquipList eqp = eqplist.get(k);
					processReceipt.setEqipListId(eqp.getId());
					processReceipt.setEquipCode(eqp.getEquipCode());
					processReceipt.setNeedShow(StringUtils.isNotBlank(processReceipt.getReceiptTargetValue()));

					// 直接插入，若判断更新将此处注释
					if (isImmediateInsert) {
						processReceipt.setId(null);
						this.insert(processReceipt);
						continue;
					}

					ProcessReceipt findParam = new ProcessReceipt();
					findParam.setReceiptCode(processReceipt.getReceiptCode());
					findParam.setEquipCode(processReceipt.getEquipCode());
					findParam.setEqipListId(processReceipt.getEqipListId()); // 工序使用设备清单ID
					// 判断是否存在了，是就update，否就insert
					List<ProcessReceipt> processReceiptList = this.getByObj(findParam);
					if (null == processReceiptList || processReceiptList.size() == 0) {
						processReceipt.setId(null);
						this.insert(processReceipt);
					} else {
						findParam = processReceiptList.get(0);
						processReceipt.setId(findParam.getId());
						this.update(processReceipt);
					}

				}
			}
		}
	}

	@Override
	public List<ProcessReceipt> getByProcessId(String processId) {
		return processReceiptDAO.getByProcessId(processId);
	}

	@Override
	public void updateEquipQcByHand(String craftsCode) {
		processReceiptDAO.updateReceiptQc(craftsCode);
		System.out.println("开始处理。。。");
		List<Map<String, String>> receipt = processReceiptDAO.findReceiptMap();
		for (Map<String, String> m : receipt) {
			String mesId = m.get("MES_ID");
			String plmId = m.get("PLM_ID");
			String equipNo = m.get("EQUIP_CODE");
			processReceiptDAO.insertBatch(updateEquipQc(mesId, plmId, equipNo));
		}
	}

	@Override
	public List<ProcessReceipt> updateEquipQc(String mesId, String plmId, String equipNo) {
		System.out.println("mes:" + mesId + " --------plm:" + plmId);
		List<CanShuKu> canshukuArray = canShuKuDAO.getParamArrayByScxId(plmId);
		List<ProcessReceipt> ProcessReceipts = new ArrayList<ProcessReceipt>();
		for (CanShuKu canshuku : canshukuArray) {

			String flag = PrcvServiceImpl.receiptMap.get(mesId + canshuku.getNo());
			if (flag != null) {
				continue;
			} else {
				PrcvServiceImpl.receiptMap.put(mesId + canshuku.getNo(), mesId + canshuku.getNo());
			}
			ProcessReceipt processReceipt = new ProcessReceipt();
			processReceipt.setId(UUID.randomUUID().toString());
			processReceipt.setFrequence(10.00); // 检测频率????
			// processReceipt.setCreateTime(new Date());
			// processReceipt.setModifyTime(new Date());
			// processReceipt.setCreateUserCode("admin");
			// processReceipt.setModifyUserCode("admin");
			processReceipt.setDataType("STRING"); // 参数数据类型????
			processReceipt.setDataUnit(null); // 参数单位????
			processReceipt.setHasPic(false); // 是否有附件????
			processReceipt.setNeedAlarm(false); // 超差是否报警????
			processReceipt.setNeedShow(StringUtils.isNotBlank(canshuku.getValue()) ? true : false); // 是否需要在终端显示????
			processReceipt.setValueDomain(null); // 值域????

			processReceipt.setEqipListId(mesId);
			processReceipt.setEquipCode(equipNo + "_EQUIP");
			processReceipt.setReceiptCode(canshuku.getNo());
			processReceipt.setSubReceiptCode(canshuku.getNo());
			processReceipt.setReceiptName(canshuku.getName());
			processReceipt.setSubReceiptName(canshuku.getName());
			processReceipt.setReceiptTargetValue(canshuku.getValue() == null ? "" : canshuku.getValue());
			processReceipt.setReceiptMaxValue(null); // 参数上限????
			processReceipt.setReceiptMinValue(null); // 参数下限????
			// 如果数采的maping有则从数采maping中取值
			// todo
			EquipMESWWMapping equipMap = StaticDataCache.getEquipMESWWMapping(equipNo + "_EQUIP", canshuku.getNo());
			if (equipMap != null) {
				processReceipt.setNeedDa(equipMap.getNeedDa()); // 是否需要数采????
				processReceipt.setNeedIs(equipMap.getNeedIs()); // 是否需要下发????
			} else {
				processReceipt.setNeedDa(false); // 是否需要数采????
				processReceipt.setNeedIs(false); // 是否需要下发????
			}
			ProcessReceipts.add(processReceipt);
		}
		return ProcessReceipts;
	}

	@Override
	public void insertBatchInterface(List<ProcessReceipt> receiptListResult) {

		processReceiptDAO.insertBatchInterface(receiptListResult);
	}

	@Override
	public Map<String, String> getEquipQcByItemId(String[] orderItemIdArray) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderItemIdArray", orderItemIdArray);
		List<ProcessReceipt> l = processReceiptDAO.getEquipQcByItemId(param);
		Map<String, String> receipt = new HashMap<String, String>();
		for (ProcessReceipt r : l) {
			if (r.getReceiptTargetValue() != null) {
				receipt.put(r.getEquipCode().replaceAll("_EQUIP", ""), r.getReceiptTargetValue());
			}
		}
		return receipt;
	}
}
