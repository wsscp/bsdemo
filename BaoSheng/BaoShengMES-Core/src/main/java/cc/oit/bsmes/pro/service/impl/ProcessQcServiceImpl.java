package cc.oit.bsmes.pro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import jxl.Cell;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.common.constants.QCDataType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JxlUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.pro.dao.ProcessQcDAO;
import cc.oit.bsmes.pro.model.ProcessQCEqip;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProcessQCEqipService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessQcWipService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.dto.MesClientEqipInfo;
import cc.oit.bsmes.wip.model.Receipt;

@Service
public class ProcessQcServiceImpl extends BaseServiceImpl<ProcessQc> implements ProcessQcService {

	@Resource
	private ProcessQcDAO processQcDAO;

	@Resource
	private ProductProcessService productProcessService;

	@Resource
	private EquipInfoService equipInfoService;

	@Resource
	private ProcessQCEqipService processQCEqipService;

	@Resource
	private DataAcquisitionService dataAcquisitionService;
	
	private static final int PROCESS_QC_INDEX = 1;

	@Override
	public List<ProcessQc> getByProcessId(String processId) {
		ProcessQc findParams = new ProcessQc();
		findParams.setProcessId(processId);
		return processQcDAO.find(findParams);
	}

	/**
	 * <p>
	 * TODO产品/半成品称重,根据生产单号插入数据
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-19 下午3:43:43
	 * @param workOrderNO
	 * @param weight
	 * @param checkItemCode
	 * @param userCode
	 * @see cc.oit.bsmes.pro.service.ProcessQcService#insertByWorkOrderNO(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void insertByWorkOrderNO(String workOrderNO, String weight, String checkItemCode, String userCode) {
		processQcDAO.insertByWorkOrderNO(workOrderNO, weight, checkItemCode, userCode);
	}

	@Override
	public List<ProcessQc> getByWorkOrderNo(String workOrderNo) {
		return processQcDAO.getByWorkOrderNo(workOrderNo);
	}

	/**
	 * <p>
	 * 查询工序的质量检测参数
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-10 上午11:28:58
	 * @param processCode
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProcessQcService#getByProcessCode(java.lang.String)
	 */

	private List<ProcessQc> getByProcessCode(String equipCode, String processCode) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("equipCode", equipCode);
		findParams.put("processCode", processCode);
		return processQcDAO.getByProcessCode(findParams);
	}

	/**
	 * <p>
	 * 根据设备code和工序code追溯产品质量检测参数
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-10 上午11:28:58
	 * @param equipCode
	 * @param processCode
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProcessQcService#traceByEquipCodeAndProcessCode(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<ProcessQc> traceByEquipCodeAndProcessCode(String equipCode, String processCode) {
		List<ProcessQc> processQcList = getByProcessCode(equipCode, processCode);
		// TODO 接入外部接口获取质量参数实时数据

		if (processQcList != null && !processQcList.isEmpty()) {
			List<Receipt> receipts = new ArrayList<Receipt>();
			for (ProcessQc processQc : processQcList) {
				Receipt receipt = new Receipt();
				receipt.setReceiptCode(processQc.getCheckItemCode());
				receipt.setEquipCode(equipCode);
				receipts.add(receipt);
			}
			// 接入外部接口获取质量参数实时数据
			List<Receipt> receiptsResutl = dataAcquisitionService.queryLiveReceiptByCodes(receipts);
			for (Receipt receipt : receiptsResutl) {
				for (ProcessQc processQc : processQcList) {
					if (StringUtils.equalsIgnoreCase(receipt.getEquipCode(), equipCode)
							&& StringUtils.equalsIgnoreCase(receipt.getReceiptCode(), processQc.getCheckItemCode())) {
						processQc.setDaValue(receipt.getDaValue());
					}
				}
			}
		}

		return processQcList;
	}

	@Override
	public List<ProcessQc> getByEquipLineAndProcessId(String equipLineCode, String processId) {
		return processQcDAO.getByEquipLineAndProcessId(equipLineCode, processId);
	}

	@Override
	public List<ProcessQc> getByWorkOrderNoAndDistinctEqipCode(String workOrderNo, String equipCode, String type) {
		return processQcDAO.getByWorkOrderNoAndDistinctEqipCode(workOrderNo, equipCode, type);
	}

	@Override
	public void importProcessQc(List<Cell[]> rows, String equipCode, String acEquipCode, String orgCode) {
		// List<ProductProcess> processList =
		// productProcessService.getByProcessCode(processCode);
		/**
		 * 修改为根据equipCode查找processList
		 * 
		 * @author DingXintao
		 * @time 2014-11-17 10:00:00
		 * */
		List<ProductProcess> processList = productProcessService.getByEquipCode(equipCode);

		for (ProductProcess productProcess : processList) {
			// List<EquipInfo> equipInfos =
			// equipInfoService.getEquipInfoByProcessId(productProcess.getId());
			EquipInfo equipInfo = StaticDataCache.getMainEquipInfo(equipCode);
			if (null == equipInfo) {
				continue;
			}

			ProcessQc findPram = new ProcessQc();
			for (Cell[] cells : rows) {
				ProcessQc processQc = new ProcessQc();
				processQc.setProcessId(productProcess.getId());
				processQc.setId(UUID.randomUUID().toString());
				processQc.setFrequence(10.0);
				processQc.setNeedShow("1");
				setProperty(cells, processQc);

				findPram.setProcessId(processQc.getProcessId());
				findPram.setCheckItemCode(processQc.getCheckItemCode());
				// 判断是否存在了，是就update，否就insert
				List<ProcessQc> processQcList = this.getByObj(processQc);
				if (null == processQcList || processQcList.size() == 0) {
					insert(processQc);
				} else {
					findPram = processQcList.get(0);
					processQc.setId(findPram.getId());
					update(processQc);
				}

				ProcessQCEqip processQCEqip = new ProcessQCEqip();
				processQCEqip.setQcId(processQc.getId());
				processQCEqip.setEquipCode(equipInfo.getCode());
				processQCEqip.setEquipId(equipInfo.getId());
				// 判断是否存在了，是就update，否就insert
				List<ProcessQCEqip> processQcEqipList = processQCEqipService.getByObj(processQCEqip);
				if (null == processQcEqipList || processQcEqipList.size() == 0) {
					processQCEqip.setAcEquipCode(acEquipCode);
					processQCEqipService.insert(processQCEqip);
				} else {
					// processQCEqip = processQcEqipList.get(0);
					// processQCEqipService.update(processQCEqip);
				}

			}
		}
	}

	private void setProperty(Cell[] cells, ProcessQc processQc) {
		processQc.setCheckItemCode(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX]));
		processQc.setCheckItemName(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 1]));
		processQc.setItemTargetValue(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 2]));
		processQc.setItemMaxValue(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 3]));
		processQc.setItemMinValue(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 4]));
		boolean flag = "是".equals(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 5]));
		processQc.setNeedDa(flag);
		processQc.setNeedAlarm(flag ? "1" : "0");
		processQc.setNeedIs("是".equals(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 6])) ? true : false);
		processQc.setDataType(QCDataType.getByDesc(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 7])));
		processQc.setDataUnit(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 8]));
		processQc.setNeedFirstCheck("是".equals(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 9])) ? "1" : "0");
		processQc.setNeedMiddleCheck("是".equals(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 10])) ? "1" : "0");
		processQc.setNeedInCheck("是".equals(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 11])) ? "1" : "0");
		processQc.setNeedOutCheck("是".equals(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 12])) ? "1" : "0");
		processQc.setNeedShow("是".equals(JxlUtils.getRealContents(cells[PROCESS_QC_INDEX + 13])) ? "1" : "0");
		processQc.setNeedShow(StringUtils.isNotBlank(processQc.getItemTargetValue()) ? "1" : "0");
	}

	@Override
	public ProcessQc getByEquipCodeAndProcessCodeAndReceiptCode(String equipCode, String processCode, String receiptCode) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("equipCode", equipCode);
		findParams.put("processCode", processCode);
		findParams.put("receiptCode", receiptCode);
		List<ProcessQc> alist = processQcDAO.getByEquipCodeAndProcessCodeAndReceiptCode(findParams);
		if (CollectionUtils.isEmpty(alist)) {
			return null;
		} else {
			return alist.get(0);
		}
	}

	@Override
	public List<Map<String, String>> getEmphShow(String processId, String productLineCode) {
		return processQcDAO.getEmphShow(processId, productLineCode);
	}

	@Override
	@Transactional(readOnly = false)
	public void insertBatch(List<ProcessQc> list) {
		processQcDAO.insertBatch(list);
	}

	public void insertBackGround(String newProcessId, List<String> processIds) {
		processQcDAO.insertBackGround(newProcessId, processIds);
	}

	@Override
	public List<Map<String, Object>> getQcInfoByTaskId(String taskId) {
		return processQcDAO.getQcInfoByTaskId(taskId);
	}
}
