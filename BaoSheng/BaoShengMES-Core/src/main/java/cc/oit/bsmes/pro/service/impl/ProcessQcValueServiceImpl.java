package cc.oit.bsmes.pro.service.impl;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.constants.QADetectType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JSONUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.pro.dao.ProcessQcValueDAO;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProcessQcValue;
import cc.oit.bsmes.pro.service.ProcessQCEqipService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessQcValueService;
import cc.oit.bsmes.pro.service.ProcessQcWipService;
import cc.oit.bsmes.wip.dto.MesClientEqipInfo;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSONObject;

@Service
public class ProcessQcValueServiceImpl extends BaseServiceImpl<ProcessQcValue> implements ProcessQcValueService {

	@Resource
	private ProcessQcValueDAO processQcValueDAO;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private ProcessQcWipService processQcWipService;
	@Resource
	private ProcessQCEqipService processQCEqipService;
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	private DataAcquisitionService dataAcquisitionService;

	@Override
	public List<ProcessQcValue> findDistinctByWorkOrderNo(String workOrderNo) {
		return processQcValueDAO.findDistinctByWorkOrderNo(workOrderNo);
	}

	@Override
	public List<ProcessQcValue> findByWorkOrderNoAndCheckItemCode(String workOrderNo, String checkItemCode) {
		ProcessQcValue findParams = new ProcessQcValue();
		findParams.setCheckItemCode(checkItemCode);
		findParams.setWorkOrderNo(workOrderNo);
		return processQcValueDAO.find(findParams);
	}

	@Override
	public List<ProcessQcValue> queryQACheckItems(String workOrderNo, String type, String equipCode) {
		List<ProcessQcValue> rows = new ArrayList<ProcessQcValue>();
		if (StringUtils.isBlank(type)) {
			return rows;
		}
		List<ProcessQc> list = processQcService.getByWorkOrderNoAndDistinctEqipCode(workOrderNo, equipCode, type);
		// 采集数据，并判断是否通过
		// 未测试
		// WorkOrder workOrder = workOrderService.getByWorkOrderNO(workOrderNo);
		EquipInfo mainEquip = StaticDataCache.getMainEquipInfo(equipCode);
		Receipt receipt = new Receipt();

		// TODO 现在是循环加载参数实时值 可以考虑使用批量加载实时值
		ProcessQcValue qcValue = null;
		for (ProcessQc qc : list) {
			qcValue = new ProcessQcValue();
			if (qc.getNeedDa() && mainEquip != null) {
				receipt.setReceiptCode(qc.getCheckItemCode());
				receipt.setEquipCode(mainEquip.getCode());
				dataAcquisitionService.queryLiveReceiptByCodes(receipt);
				qcValue.setQcValue(receipt.getDaValue());
				if (StringUtils.isNotBlank(qcValue.getQcValue()) && StringUtils.isNotBlank(qc.getItemTargetValue())) {
					qcValue.setQcResult(qc.getItemTargetValue().equals(qcValue.getQcValue()) ? Constants.PASS
							: Constants.NO_PASS);
				}
			}
			qcValue.setId(qc.getId());
			qcValue.setCheckItemCode(qc.getCheckItemCode());
			qcValue.setCheckItemName(qc.getCheckItemName());
			qcValue.setWorkOrderNo(workOrderNo);
			qcValue.setItemTargetValue(qc.getItemTargetValue());
			qcValue.setDataUnit(qc.getDataUnit());
			String checkItemRange = "~";
			if (StringUtils.isNotBlank(qc.getItemMinValue())) {
				checkItemRange = qc.getItemMinValue() + checkItemRange;
			}
			if (StringUtils.isNotBlank(qc.getItemMaxValue())) {
				checkItemRange = checkItemRange + qc.getItemMaxValue();
			}
			if (!StringUtils.equalsIgnoreCase(checkItemRange, "~")) {
				qcValue.setCheckItemRange(checkItemRange);
			}
			rows.add(qcValue);
		}
		return rows;
	}

	@Override
	public void entryProcessQAValue(List<ProcessQcValue> list, Integer coilNum, String equipCode) {
		String userCode = SessionUtils.getUser().getUserCode();
		for (ProcessQcValue qc : list) {
			// 根据质量参数CODE,生产单号 查询交集设备集合
			// List<String> equipCodes =
			// processQCEqipService.getEquipCodeByWorkOrderNo(qc.getCheckItemCode(),
			// qc.getWorkOrderNo());
			// for(String eqipCode:equipCodes){
			processQcValueDAO.saveProcessQcValue(qc.getCheckItemCode(), qc.getQcValue(), qc.getQcResult(),
					qc.getCheckEqipCode(), equipCode, qc.getWorkOrderNo(), userCode, qc.getType().name(), coilNum);
			// }
		}
	}

	@Override
	public void generateQAAlarm(ProcessQcValue qcValue) {
		// ProcessQc processQc =
		// processQcService.getByCheckItemCode(qcValue.getCheckItemCode());
		User user = SessionUtils.getUser();
		EventInformation t = new EventInformation();
		t.setId(UUID.randomUUID().toString());
		t.setEventTitle("QA报警");
		t.setEventContent("警报内容");
		t.setEventReason("产品代码：" + qcValue.getProductCode() + ",检测参数：" + qcValue.getCheckItemName() + ",检测值："
				+ qcValue.getQcValue() + ",检测设备：" + qcValue.getCheckEqipCode() + ",检测结果：" + qcValue.getQcResult());
		t.setCode(EventTypeContent.QA.name());
		t.setEventStatus(EventStatus.UNCOMPLETED);
		t.setProcessTriggerTime(new Date());
		t.setProcessId(qcValue.getProcessId());
		t.setEquipCode(qcValue.getEqipCode());
		t.setCreateUserCode(user.getUserCode());
		t.setOrgCode(user.getOrgCode());
		eventInformationService.insertInfo(t);
	}

	@Override
	public JSONObject checkExistsInputQcValue(String woNo, String equipCode, int coilNum) {
		WorkOrder currentWorkOrder = workOrderService.getCurrentByEquipCode(equipCode);
		MesClientEqipInfo equipInfo = new MesClientEqipInfo();
		processQcWipService.getCheckTypeByProcessId(currentWorkOrder.getProcessId(), equipInfo);
		boolean isOutCheck = WebConstants.YES.equals(equipInfo.getNeedOutCheck());
		JSONObject result = new JSONObject();
		// 在线盘号为1的时候进行验证是否已经录入上车检 下车检 首检
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("woNo", woNo);
		paramMap.put("coilNum", coilNum);
		if (WebConstants.YES.equals(String.valueOf(coilNum))) {
			// 不做首检
			// WorkOrder lastWorkOrder =
			// workOrderService.getLastByEquipCode(equipCode);
			// boolean isFirstCheck = (lastWorkOrder == null ||
			// !currentWorkOrder.getHalfProductCode().equals(lastWorkOrder.getHalfProductCode()))
			// &&
			// WebConstants.YES.equals(equipInfo.getNeedFirstCheck());
			/*
			 * if(isFirstCheck){ //表示首检已做 paramMap.put("type",
			 * QADetectType.FIRST_CHECK.name());
			 * if(processQcValueDAO.checkExistsInputQcValueByWoNo(paramMap) >
			 * 0){ isFirstCheck = false; } }
			 */
			if (isOutCheck) {
				// 表示下车检已做
				paramMap.put("type", QADetectType.OUT_CHECK.name());
				if (processQcValueDAO.checkExistsInputQcValueByWoNo(paramMap) > 0) {
					isOutCheck = false;
				}
			}

			// if(isFirstCheck && isOutCheck){
			// result.put("msg","请录首检、下车检质量数据!");
			// }else if(isFirstCheck){
			// result.put("msg","请录首检质量数据!");
			// }else if(isOutCheck){
			// result.put("msg","请录下车检质量数据!");
			// }

			if (isOutCheck) {
				result.put("msg", "下车检质量数据!");
			} else if (isOutCheck) {
				result.put("msg", "请录下车检质量数据!");
			}
		} else {
			paramMap.put("type", QADetectType.OUT_CHECK.name());
			if (isOutCheck && processQcValueDAO.checkExistsInputQcValueByWoNo(paramMap) == 0) {
				result.put("msg", "请录下车检质量数据!");
			}
		}
		result.put("success", true);
		return result;
	}

	@Override
	public boolean inCheck(String woNo) {
		WorkOrder currentWorkOrder = workOrderService.getByWorkOrderNO(woNo);
		MesClientEqipInfo equipInfo = new MesClientEqipInfo();
		processQcWipService.getCheckTypeByProcessId(currentWorkOrder.getProcessId(), equipInfo);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("woNo", woNo);
		boolean isInCheck = WebConstants.YES.equals(equipInfo.getNeedInCheck());
		if (isInCheck) {
			// 表示上车检 已做
			paramMap.put("type", QADetectType.IN_CHECK.name());
			if (processQcValueDAO.checkExistsInputQcValueByWoNo(paramMap) > 0) {
				isInCheck = false;
			}
		}
		return isInCheck;
	}

	@Override
	public List<ProcessQcValue> findForExport(JSONObject queryFilter) throws InvocationTargetException,
			IllegalAccessException, NoSuchMethodException {
		// ProcessQcValue findParams =
		// (ProcessQcValue)JSONUtils.jsonToBean(queryFilter,ProcessQcValue.class);
		ProcessQcValue findParams = JSONObject.toJavaObject(queryFilter, ProcessQcValue.class);
		// addLike(findParams,ProcessQcValue.class);
		return processQcValueDAO.find(findParams);
	}

	@Override
	public int countForExport(JSONObject queryParams) throws InvocationTargetException, IllegalAccessException,
			NoSuchMethodException {
		ProcessQcValue findParams = (ProcessQcValue) JSONUtils.jsonToBean(queryParams, ProcessQcValue.class);
		// addLike(findParams,ProcessQcValue.class);
		return processQcValueDAO.count(findParams);
	}

	@Override
	public List<ProcessQcValue> getLastByWorkOrderNoAndType(String workOrderNo) {
		return processQcValueDAO.getLastByWorkOrderNoAndType(workOrderNo);
	}

	private static class Constants {
		private static final String PASS = "通过";
		private static final String NO_PASS = "不通过";
	}

	@Override
	public int updateDA(String serialNum, String workOrderNo) {
		return processQcValueDAO.updateDA(serialNum, workOrderNo);
	}

	@Override
	public List<ProcessQcValue> getQaList(ProcessQcValue findParams, int start,
			int limit,List<Sort> list) {
		SqlInterceptor.setRowBounds(new RowBounds(start,limit));
		return processQcValueDAO.getQaList(findParams);
	}

	@Override
	public int countQaList(ProcessQcValue findParams) {
		return processQcValueDAO.countQaList(findParams);
	}

	@Override
	public String queryProcessQcValueCoilNum(String workOrderNo,String type,String equipCode) {
		return processQcValueDAO.queryProcessQcValueCoilNum(workOrderNo, type, equipCode);
	}

	
	
}
