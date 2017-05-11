package cc.oit.bsmes.wip.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.dao.ParmsMappingDAO;
import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.common.constants.ReceiptStatus;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamHistoryAcquisition;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.interfaceWWIs.service.DataIssuedService;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;
import cc.oit.bsmes.wip.dao.ReceiptDAO;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ReceiptService;
import cc.oit.bsmes.wip.service.WorkOrderService;

@Service
public class ReceiptServiceImpl extends BaseServiceImpl<Receipt> implements ReceiptService {

	@Resource
	private ReceiptDAO receiptDAO;
	@Resource
	private DataIssuedService dataIssuedService;
	@Resource
	private ParmsMappingDAO parmsMappingDAO;
	@Resource
	private ProcessReceiptService processReceiptService;
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private DataAcquisitionService dataAcquisitionService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private EquipInfoService equipInfoService;

	/**
	 * <p>
	 * 根据工单号查询下发参数(不包括归档的数据)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-3 下午5:09:51
	 * @param woNo
	 * @return
	 * @see cc.oit.bsmes.wip.service.ReceiptService#getByWorkOrderNo(java.lang.String)
	 */
	@Override
	public List<Receipt> getByWorkOrderNo(String woNo) {
		return receiptDAO.getByWorkOrderNo(woNo, getOrgCode());
	}

	/**
	 * 在调试 加工按钮时 会下发数据 如果前台有修改动作 则该记录直接新增状态为下发， 若不变则修改对应记录状态为下发若状态已经为下发状态则修改下发时间
	 * <p>
	 * 下发工艺参数
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-19 下午2:16:26
	 * @param receiptArray
	 * @param isDebug 是否调试
	 * @see cc.oit.bsmes.wip.service.ReceiptService#issuedReceipt(java.util.List,
	 *      String,java.lang.Boolean)
	 */
	@Override
	public void issuedReceipt(List<Receipt> receiptArray, String woNo, Boolean isDebug, String operator) {
		List<Receipt> list = getLastReceiptList(woNo);
		Map<String, Receipt> updateReceiptMap = new HashMap<String, Receipt>();
		for (Receipt receipt : receiptArray) {
			updateReceiptMap.put(receipt.getReceiptCode(), receipt);
		}
		List<Receipt> issuedList = new ArrayList<Receipt>();
		for (Receipt issuedReceipt : list) {
			Receipt re = updateReceiptMap.get(issuedReceipt.getReceiptCode());
			Receipt newReceipt = new Receipt();
			try {
				ConvertUtils.register(new DateConverter(null), java.util.Date.class);
				BeanUtils.copyProperties(newReceipt, issuedReceipt);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			if (re != null) {
				newReceipt.setReceiptTargetValue(re.getReceiptTargetValue());
			}
			newReceipt.setIssuedTime(new Date());
			newReceipt.setStatus(ReceiptStatus.ISSUED);
			newReceipt.setId(null);
			newReceipt.setCreateTime(null);
			newReceipt.setModifyTime(null);
			newReceipt.setCreateUserCode(operator);
			newReceipt.setModifyUserCode(operator);
			newReceipt.setWorkOrderNo(woNo);
			insert(newReceipt);
			issuedList.add(newReceipt);
		}
		dataIssuedService.IssuedParms(issuedList, isDebug);
	}

	@Override
	public Receipt getByReceiptCode(String receiptCode, String equipCode) {
		return receiptDAO.getByEquipCodeAndParamsCode(receiptCode, equipCode, getOrgCode());
	}

	public Receipt getReceiptName(Map<String, Object> map) {
		return receiptDAO.getReceiptName(map);
	}

	@Override
	public Receipt getByTagName(String tagName) {
		return receiptDAO.getByTagName(tagName, getOrgCode());
	}

	@Override
	public List<Receipt> getByEquipCode(String equipCode, String processId, String orgCode) {
		return receiptDAO.getByEquipCode(equipCode, processId, orgCode);
	}

	@Override
	public Receipt getByProcessReceiptAndQA(String receiptCode, String equipCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("receiptCode", receiptCode);
		map.put("equipCode", equipCode);
		return receiptDAO.getByProcessReceiptAndQA(map);
	}

	@Override
	public Map<String, List<Object[]>> getHistoryTrace(String equipCode, String receiptCode, String startTime,
			String endTime) {
		Map<String, List<Object[]>> result = new HashMap<String, List<Object[]>>();
		long time = (DateUtils.convert(endTime).getTime() - DateUtils.convert(startTime).getTime()) / 1000;
		int cycleCount = 0;
		BigDecimal frequence = BigDecimal.ZERO;
		// if(StringUtils.equalsIgnoreCase(type, "receipt")){
		// ProcessReceipt receipt =
		// processReceiptService.getByEquipCodeAndProcessCodeAndReceiptCode(equipCode,
		// processCode,receiptCode);
		// if(receipt!=null){
		// frequence = new BigDecimal(receipt.getFrequence());
		// }
		// }else if(StringUtils.equalsIgnoreCase(type, "qc")){
		// ProcessQc qc =
		// processQcService.getByEquipCodeAndProcessCodeAndReceiptCode(equipCode,
		// processCode,receiptCode);
		// if(qc!=null){
		// frequence = new BigDecimal(qc.getFrequence());
		// }
		// }
		BigDecimal timeFrequence = BigDecimal.ZERO;
		if (time > 0 && time < 60) {
			// 一分钟之内
			timeFrequence = new BigDecimal(60);
		} else if (time >= 60 && time < 3600) {
			// 一小时之内
			timeFrequence = new BigDecimal(3600);
		} else if (time >= 3600 && time < 86400) {
			// 一天之内
			timeFrequence = new BigDecimal(86400);
		} else if (time >= 86400 && time < 604800) {
			// 一星期之内
			timeFrequence = new BigDecimal(604800);
		} else if (time >= 604800 && time < 2678400) {
			// 一个月之内
			timeFrequence = new BigDecimal(2678400);
		} else if (time >= 2678400 && time < 32140800) {
			// 一年之内
			timeFrequence = new BigDecimal(32140800);
		} else {
			// 超过一年
			timeFrequence = frequence.multiply(new BigDecimal(BusinessConstants.MAX_CYCLE_COUNT));
		}
		if (frequence.compareTo(BigDecimal.ZERO) != 0
				&& (timeFrequence.divide(frequence).intValue() < BusinessConstants.MAX_CYCLE_COUNT)) {
			cycleCount = timeFrequence.divide(frequence).intValue();
		} else {
			cycleCount = BusinessConstants.MAX_CYCLE_COUNT;
		}

		// 组装历史数据
		List<EquipParamHistoryAcquisition> list = dataAcquisitionService.findParamHistory(equipCode, receiptCode,
				DateUtils.convert(startTime), DateUtils.convert(endTime), cycleCount);
		if (CollectionUtils.isEmpty(list)) {
			return result;
		}
		List<Object[]> historyData = new ArrayList<Object[]>();
		for (EquipParamHistoryAcquisition paramHis : list) {
			Object[] array = new Object[2];
			array[0] = paramHis.getDatetime();
			array[1] = paramHis.getValue() == null ? 0 : paramHis.getValue();
			historyData.add(array);
		}
		result.put("historyData", historyData);

		/*-------------------------------------------------------------*/
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("receiptCode", receiptCode.replaceFirst("R_", "W_"));
		findParams.put("orgCode", SessionUtils.getUser().getOrgCode());
		findParams.put("startTime", DateUtils.convert(startTime));
		findParams.put("endTime", DateUtils.convert(endTime));
		findParams.put("equipCode", equipCode);

		List<Receipt> receiptList = receiptDAO.getByReceiptCodeAndTime(findParams);
		if (!CollectionUtils.isEmpty(receiptList)) {
			// if(receiptList.get(0).getIssuedTime().after(DateUtils.convert(startTime))){
			// Receipt receipt = receiptDAO.getLastReceipt(findParams);
			// if(receipt != null){
			// receiptList.add(0,receipt);
			// }else{
			// receipt = new Receipt();
			// Receipt lastReceipt = receiptList.get(0);
			// receipt.setIssuedTime(DateUtils.convert(endTime));
			// receipt.setReceiptMaxValue(lastReceipt.getReceiptMaxValue());
			// receipt.setReceiptMinValue(lastReceipt.getReceiptMinValue());
			// receiptList.add(0,receipt);
			// }
			// }

			Receipt firstReceipt = receiptList.get(0);
			if (firstReceipt.getIssuedTime().before(DateUtils.convert(startTime))) {

				firstReceipt.setIssuedTime(list.get(0).getDatetime());

			}
			Receipt lastReceipt = receiptList.get(receiptList.size() - 1);
			Receipt receipt = new Receipt();
			receipt.setIssuedTime(list.get(list.size() - 1).getDatetime());
			receipt.setReceiptMaxValue(lastReceipt.getReceiptMaxValue());
			receipt.setReceiptMinValue(lastReceipt.getReceiptMinValue());
			receiptList.add(receipt);

		}

		List<Object[]> maxDataList = new ArrayList<Object[]>();
		List<Object[]> minDataList = new ArrayList<Object[]>();
		for (Receipt receipt : receiptList) {
			Object[] maxArray = new Object[2];
			Object[] minArray = new Object[2];

			maxArray[0] = receipt.getIssuedTime();
			maxArray[1] = StringUtils.isNotBlank(receipt.getReceiptMaxValue()) ? Double.parseDouble(receipt
					.getReceiptMaxValue()) : 0;

			minArray[0] = receipt.getIssuedTime();
			minArray[1] = StringUtils.isNotBlank(receipt.getReceiptMinValue()) ? Double.parseDouble(receipt
					.getReceiptMinValue()) : 0;

			maxDataList.add(maxArray);
			minDataList.add(minArray);
		}

		result.put("maxList", maxDataList);
		result.put("minList", minDataList);
		return result;
	}

	@Override
	public void exportToXls(OutputStream os, String sheetName, Map<String, Object> map) throws RowsExceededException,
			WriteException, IOException {

		List<EquipParamHistoryAcquisition> list = this.getRealData(map);
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet sheet = wwb.createSheet(sheetName, 0);
		sheet.setColumnView(0, 30);
		sheet.setColumnView(1, 30);
		sheet.setColumnView(2, 30);
		sheet.setColumnView(3, 30);

		sheet.addCell(new Label(0, 0, "生产产品"));
		sheet.addCell(new Label(1, 0, "采集参数"));
		sheet.addCell(new Label(2, 0, "采集时间"));
		sheet.addCell(new Label(3, 0, "采集值"));
		if (list.size() == 0) {
			wwb.write();
			wwb.close();
			return;
		}
		int k = 1;
		for (int i = 0; i < list.size(); i++) {
			EquipParamHistoryAcquisition equipParamHistoryAcquisition = list.get(i);
			if (StringUtils.isNotBlank(equipParamHistoryAcquisition.getProductCode())) {
				sheet.addCell(new Label(0, k, equipParamHistoryAcquisition.getProductCode()));
				sheet.addCell(new Label(1, k, sheetName));
				sheet.addCell(new Label(2, k, DateUtils.convert(equipParamHistoryAcquisition.getDatetime(),
						DateUtils.DATE_TIME_FORMAT)));
				Double paramData = equipParamHistoryAcquisition.getValue();
				if (paramData != null) {
					sheet.addCell(new Label(3, k, paramData + ""));
				}
				k++;
			}
		}
		wwb.write();
		wwb.close();
	}

	public List<EquipParamHistoryAcquisition> getRealData(Map<String, Object> map) {
		String equipCode = map.get("equipCode").toString();
		String processCode = map.get("processCode").toString();
		String type = map.get("type").toString();
		String receiptCode = map.get("receiptCode").toString();
		Date startTime = (Date) map.get("startTime");
		Date endTime = (Date) map.get("endTime");
		long time = (endTime.getTime() - startTime.getTime()) / 1000;
		int cycleCount = 0;
		BigDecimal frequence = BigDecimal.ZERO;
		if (StringUtils.equalsIgnoreCase(type, "receipt")) {
			ProcessReceipt receipt = processReceiptService.getByEquipCodeAndProcessCodeAndReceiptCode(equipCode,
					processCode, receiptCode);
			if (receipt != null) {
				frequence = new BigDecimal(receipt.getFrequence());
			}
		} else if (StringUtils.equalsIgnoreCase(type, "qc")) {
			ProcessQc qc = processQcService.getByEquipCodeAndProcessCodeAndReceiptCode(equipCode, processCode,
					receiptCode);
			if (qc != null) {
				frequence = new BigDecimal(qc.getFrequence());
			}
		}
		BigDecimal timeFrequence = BigDecimal.ZERO;
		if (time > 0 && time < 60) {
			// 一分钟之内
			timeFrequence = new BigDecimal(60);
		} else if (time >= 60 && time < 3600) {
			// //一小时之内
			timeFrequence = new BigDecimal(3600);
		} else if (time >= 3600 && time < 86400) {
			// 一天之内
			timeFrequence = new BigDecimal(86400);
		} else if (time >= 86400 && time < 604800) {
			// 一星期之内
			timeFrequence = new BigDecimal(604800);
		} else if (time >= 604800 && time < 2678400) {
			// 一个月之内
			timeFrequence = new BigDecimal(2678400);
		} else if (time >= 2678400 && time < 32140800) {
			// 一年之内
			timeFrequence = new BigDecimal(32140800);
		} else {
			// 超过一年
			timeFrequence = frequence.multiply(new BigDecimal(BusinessConstants.MAX_CYCLE_COUNT));
		}
		if (frequence.compareTo(BigDecimal.ZERO) != 0
				&& (timeFrequence.divide(frequence).intValue() < BusinessConstants.MAX_CYCLE_COUNT)) {
			cycleCount = timeFrequence.divide(frequence).intValue();
		} else {
			cycleCount = BusinessConstants.MAX_CYCLE_COUNT;
		}

		// 组装历史数据
		List<EquipParamHistoryAcquisition> list = dataAcquisitionService.findParamHistory(equipCode, receiptCode,
				startTime, endTime, cycleCount);
		equipCode = equipInfoService.getEquipLineByEquip(equipCode).getCode();
		WorkOrder workOrder = new WorkOrder();
		workOrder.setEquipCode(equipCode);
		workOrder.setProcessCode(processCode);
		// workOrder.setRealStartTime(startTime);
		// workOrder.setRealEndTime(endTime);
		List<WorkOrder> workOrderList = workOrderService.getWorkOrderAndProduct(workOrder);
		for (int i = 0; i < workOrderList.size(); i++) {
			WorkOrder work = workOrderList.get(i);
			if (work.getRealEndTime() == null) {
				work.setRealEndTime(new Date());
			}
			for (int j = 0; j < list.size(); j++) {
				EquipParamHistoryAcquisition equipParamHistoryAcquisition = list.get(j);
				if (equipParamHistoryAcquisition.getDatetime().getTime() >= work.getRealStartTime().getTime()
						&& equipParamHistoryAcquisition.getDatetime().getTime() <= work.getRealEndTime().getTime()) {
					if (StringUtils.isNotBlank(work.getProductCode())) {
						list.get(j).setProductCode(work.getProductName());
					}
				}
			}
		}
		return list;
	}

	@Override
	public Map<String, Object> realReceiptChart(String equipCode, String processCode, String receiptCode, String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		Date endTime = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endTime);
		calendar.add(calendar.MINUTE, -5);
		Date startTime = calendar.getTime();
		// 组装历史数据
		List<EquipParamHistoryAcquisition> list = dataAcquisitionService.findSubParamHistory(equipCode, receiptCode,
				startTime, endTime, 50);
		if (CollectionUtils.isEmpty(list)) {
			return result;
		}
		List<Object[]> historyData = new ArrayList<Object[]>();
		for (EquipParamHistoryAcquisition paramHis : list) {
			Object[] array = new Object[2];
			array[0] = paramHis.getDatetime();
			array[1] = paramHis.getValue() == null ? 0 : paramHis.getValue();
			historyData.add(array);
		}
		result.put("realData", historyData);

		// Y坐标的警戒线设置
		String targetValue = parmsMappingDAO.getEquipWWProductValue(receiptCode, equipCode.replace("_EQUIP", ""),
				this.getOrgCode());
		if (StringUtils.isNotEmpty(targetValue)) {
			// List<String> yPlotLines = new ArrayList<String>();
			// yPlotLines.add(760);
			// yPlotLines.add(820);
			result.put("yPlotLines", targetValue.split("-"));
		}

		return result;
	}

	/**
	 * 获取下发参数的最新的一组数据
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单号
	 * */
	public List<Receipt> getLastReceiptList(String workOrderNo) {
		return receiptDAO.getLastReceiptList(workOrderNo);
	}
}
