package cc.oit.bsmes.job.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.eve.service.LastExecuteTimeRecordService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamHistoryAcquisition;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.interfaceWWIs.model.SparkHistory;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.model.SparkRepair;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ReceiptService;
import cc.oit.bsmes.wip.service.SectionService;
import cc.oit.bsmes.wip.service.SparkRepairService;
import cc.oit.bsmes.wip.service.WorkOrderService;
import cc.oit.bsmes.wwalmdb.model.AlarmHistory;
import cc.oit.bsmes.wwalmdb.service.AlarmHistoryService;
import cc.oit.bsmes.wwalmdb.service.SparkHistoryService;

@Service
public class AlarmProcessTaskBack extends BaseSimpleTask {

	@Resource
	private AlarmHistoryService alarmHistoryService;
	
	@Resource
	private SparkHistoryService SparkHistoryService; 

	@Resource
	private EventInformationService eventInformationService;

	@Resource
	private ReceiptService receiptService;

	@Resource
	private LastExecuteTimeRecordService lastExecuteTimeRecordService;

	@Resource
	private DataAcquisitionService dataAcquisitionService;

	@Resource
	private WorkOrderService workOrderService;

	@Resource
	private SectionService sectionService;

	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private EquipInfoService equipInfoService;
	
	@Resource
	private SparkRepairService sparkRepairService;

	private static String UNACK_ALM = "UNACK_ALM";
	private static String UNACK_RTN = "UNACK_RTN";
	private static char SPLIT_CHAR = '.';
	private static String HI_ALARM = "Hi";
	private static String R_FAULTC = "R_FAULTC"; // 设备状态报警【int型报警】

	/**
	 * <p>
	 * 警报处理
	 * </p>
	 * ①、查询AlarmHistory列表遍历； ②、根据TagName获取MES映射对象EquipMESWWMapping； ③、
	 */
	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void process(JobParams parms) {		
		
		processSaprk(parms);		
		List<EventInformation> result = new ArrayList<EventInformation>(); // 警报事件列表
		// TODO 查询上次处理时间
		LastExecuteTimeRecord letRecord = lastExecuteTimeRecordService.getOne(InterfaceDataType.ALARM);
		Date lastExecuteDate = letRecord.getLastExecuteTime();
		Integer millisec = letRecord.getMillisec();
        if(millisec == null) millisec = 0;

		try {
			// 从本地数据库查询最后维护时间
			List<AlarmHistory> alarms = alarmHistoryService.findByEventStamp(lastExecuteDate, millisec,
					parms.getBatchSize());
			alarms = this.purgeAlarms(alarms);

			for (int i = 0; i < alarms.size() - 1; i++) {
				AlarmHistory alarmHistory = alarms.get(i);
				// 根据tagName获取映射表对象明细信息
				String tagName = alarmHistory.getTagName();
				EquipMESWWMapping equipMESWWMapping = this.getMappingObjByTagName(tagName);
				if (equipMESWWMapping == null) {
					continue;
				}
				tagName = tagName.substring(0, tagName.lastIndexOf(SPLIT_CHAR));
				if ("R_BreakDPos".equalsIgnoreCase(tagName.substring(tagName.lastIndexOf(SPLIT_CHAR) + 1)))
				{
					//火花处理
					//this.qpAlarmPart(tagName, alarmHistory, equipMESWWMapping, parms);
					continue;
				}

//				if (equipMESWWMapping.getEventType()!=null&&equipMESWWMapping.getEventType().equals(EventTypeContent.QP.name())) {
//					// QP质量问题报警
//					this.qpAlarmPart(tagName, alarmHistory, equipMESWWMapping, parms);
//				} else
				if (equipMESWWMapping.getEventType()!=null&&equipMESWWMapping.getEventType().equals(EventTypeContent.EQIP.name())) {
					// 设备状态报警[收线、放线、无料等关于设备自身状态]
					this.equipStatusAlarmPart(tagName, alarmHistory, equipMESWWMapping, parms, result);
				} else if(equipMESWWMapping.getEventType()!=null&& equipMESWWMapping.getEventType() == EventTypeContent.QC){
					// QC报警处理[温度、速度、厚度等关于工艺质量参数]
					this.qcAlarmPart(tagName, alarmHistory, equipMESWWMapping, parms, result);
				}
				else{
					//did nothing
				}
			}

			// 保存时间记录
			if (alarms.size() > 0) {
				AlarmHistory lastAlarmHistory = alarms.get(alarms.size() - 1);
				letRecord.setLastExecuteTime(lastAlarmHistory.getEventStampUTC());
				letRecord.setMillisec(lastAlarmHistory.getMilliSec());
				lastExecuteTimeRecordService.saveRecord(letRecord);
			}
			// 保存事件信息
			eventInformationService.insertInfo(result);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	}
	
	
	private void processSaprk(JobParams parms)
	{
	 
		LastExecuteTimeRecord letRecord = lastExecuteTimeRecordService.getOne(InterfaceDataType.SPARK);
		Date lastExecuteDate = letRecord.getLastExecuteTime();
		try {
			// 从本地数据库查询最后维护时间
			List<SparkHistory> alarms = SparkHistoryService.findByEventStamp(lastExecuteDate,
					parms.getBatchSize());
			for (int i = 0; i < alarms.size() ; i++) {
				SparkHistory sparkHistory = alarms.get(i);
				// 根据tagName获取映射表对象明细信息
				String tagName = sparkHistory.getTagName()+".H";
				EquipMESWWMapping equipMESWWMapping = this.getMappingObjByTagName(tagName);
				if (equipMESWWMapping == null) {
					continue;
				}
				tagName = tagName.substring(0, tagName.lastIndexOf(SPLIT_CHAR));
				if ("R_BreakDPos".equalsIgnoreCase(tagName.substring(tagName.lastIndexOf(SPLIT_CHAR) + 1)))
				{
					//火花处理
					this.qpAlarmPart(tagName, sparkHistory, equipMESWWMapping, parms);
					continue;
				} 
  
			}

			// 保存时间记录
			if (alarms.size() > 0) {
				SparkHistory lastAlarmHistory = alarms.get(alarms.size() - 1);
				letRecord.setLastExecuteTime(lastAlarmHistory.getCreateTime());
				letRecord.setMillisec(0);				
				lastExecuteTimeRecordService.saveRecord(letRecord);
			}
			// 保存事件信息
			 
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
	
		
	}

	/**
	 * <p>
	 * 获取映射表对象:EquipMESWWMapping
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-11-10 10:05:16
	 * @param tagName 标签名
	 * @return String
	 * */
	private EquipMESWWMapping getMappingObjByTagName(String tagName) {
		// 验证tagName的有效性：【TagName】的组成为：设备号+参数类型+报警限类别
		if (!this.validateTagName(tagName)) {
			logger.error("tagName:" + tagName + "无效，TagName的组成规则为：设备号+参数类型+报警限类别!");
			return null;
		}
		tagName = tagName.substring(0, tagName.lastIndexOf(SPLIT_CHAR));

		EquipMESWWMapping equipMESWWMapping = StaticDataCache.getEquipMESWWMapping(tagName);
		if (equipMESWWMapping == null) {
			logger.error("tagName:" + tagName + "在T_INT_EQUIP_MES_WW_MAPPING表中未进行映射!");
		}
		return equipMESWWMapping;
	}

	/**
	 * <p>
	 * 验证tagName的有效性:设备号+参数类型+报警限类别
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-11-10 09:47:16
	 * @param tagName 标签名
	 * @return boolean
	 * */
	private boolean validateTagName(String tagName) {
		boolean res = true;
		if (StringUtils.isBlank(tagName)) {
			res = false;
		} else {
			if (tagName.split("\\.").length != 3) {
				res = false;
			}
		}
		return res;
	}

	/**
	 * <p>
	 * QP警报处理
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-11-10 09:47:16
	 * @param tagName 标签名
	 * @param alarmHistory 警报历史记录[OPC数据库]
	 * @param equipMESWWMapping 映射对象[MES数据库]
	 * @param parms 任务参数
	 * */
	private void qpAlarmPart(String tagName, SparkHistory sparkHistory, EquipMESWWMapping equipMESWWMapping,
			JobParams parms) {
 
		EquipParamHistoryAcquisition currentAcqu = null; 
		currentAcqu = new EquipParamHistoryAcquisition();
		currentAcqu.setValue(new Double(sparkHistory.getLength()));	 
		String equipCode = equipMESWWMapping.getEquipCode();		 
		EquipInfo equipLineInfo = StaticDataCache.getLineEquipInfo(equipCode);
		if (equipLineInfo == null) {
			logger.error("设备：" + equipCode + "未找到对应的生产线!");
			return;
		}
		// 计算当前分段长度
		WorkOrder workOrder = workOrderService.getCurrentByEquipCode(equipLineInfo.getCode());
		if(workOrder==null)
		{
			return;
		}
		if(workOrder.getStatus().equals(WorkOrderStatus.TO_DO))
		{
			return;
		}
		OrderTask findParams=new OrderTask();
		findParams.setWorkOrderNo(workOrder.getWorkOrderNo());
		List<OrderTask> orderTasks = orderTaskService.findByObj(findParams);		
		OrderTask orderTask = orderTasks.size() > 0 ? orderTasks.get(0) : new OrderTask();
		//Section lastSection = sectionService.getLastSection(workOrder.getWorkOrderNo());
		//double lastSectionLocal = lastSection == null ? 0 : lastSection.getSectionLocal();			
//		Section section = new Section();
//		section.setWorkOrderNo(workOrder.getWorkOrderNo());
//		section.setSectionLocal(currentAcqu.getValue());
//		section.setOrgCode(parms.getOrgCode());
//		section.setProductLength(currentAcqu.getValue());
//		section.setSectionLength(currentAcqu.getValue() - lastSectionLocal);
//		section.setGoodLength(currentAcqu.getValue() - lastSectionLocal);
//		section.setSectionType(SectionType.UNNORMAL);
//		if (orderTask != null) {
//			section.setProcessPath(orderTask.getProcessPath());
//		}
//		section.setCreateUserCode("job");
//		section.setModifyUserCode("job");
		//sectionService.insert(section);
		
		SparkRepair sparkRepair=new SparkRepair();
		sparkRepair.setWorkOrderNo(workOrder.getWorkOrderNo());
		sparkRepair.setContractNo(orderTask.getContractNo());
		sparkRepair.setProductCode(orderTask.getProductCode());
		sparkRepair.setEquipCode(workOrder.getEquipCode());
		sparkRepair.setSparkPosition(sparkHistory.getLength());
		sparkRepair.setCreateTime(sparkHistory.getCreateTime());
		sparkRepair.setStatus("UNCOMPLETED");		
		sparkRepair.setCreateUserCode("job");
		sparkRepairService.insert(sparkRepair);
		
		
	}

	/**
	 * <p>
	 * 设备状态警报处理
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-11-10 09:47:16
	 * @param tagName 标签名
	 * @param alarmHistory 警报历史记录[OPC数据库]
	 * @param equipMESWWMapping 映射对象[MES数据库]
	 * @param parms 任务参数
	 * @param result 事件列表
	 *            <p>
	 *            BOOL型报警。 41 火花报警 R_ASpark Bool 42 凹凸报警 R_AConx Bool 43 外径报警
	 *            R_AOD Bool 45 异常报警 R_AMFault Bool 46 计米到达报警 R_AFinish Bool 47
	 *            无料报警 R_AHopper Bool。
	 * 
	 *            INT型报警，通过【R_FaultC】的当前值体现出来。 1 报警代码 R_FaultC 2 放线报警 R_FaultC=1
	 *            3 收线报警 R_FaultC=2 4 主机报警 R_FaultC=3 5 收线盘报警 R_FaultC=4
	 *            </p>
	 * */
	private void equipStatusAlarmPart(String tagName, AlarmHistory alarmHistory, EquipMESWWMapping equipMESWWMapping,
			JobParams parms, List<EventInformation> result) {
		String equipCode = equipMESWWMapping.getEquipCode();
		String paramCode = equipMESWWMapping.getParmCode();
		if (paramCode.equals(R_FAULTC)) { // int型警报
			paramCode = R_FAULTC + "_" + alarmHistory.getValue();
		}
		// 根据paramCode获取警报内容
		String alarmMsg = (String) StaticDataCache.getResultMap().get(paramCode);
		if (StringUtils.isEmpty(alarmMsg)) {
			logger.error("未找到[" + R_FAULTC + "_" + alarmHistory.getValue() + "]对应的报警具体内容");
			alarmMsg = paramCode;
		}
		StringBuffer desc = new StringBuffer("设备：[");
		desc.append(equipCode).append("]发生").append(alarmMsg).append("，请处理。");

		//EventInformation t = this.initEventInfo("设备状态报警", desc.toString(), EventTypeContent.EQIP.name(),
		//		alarmHistory.getEventStampUTC(), null, equipCode, parms.getOrgCode());
		//result.add(t);
	}

	/**
	 * <p>
	 * QC警报处理
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-11-10 09:47:16
	 * @param tagName 标签名
	 * @param alarmHistory 警报历史记录[OPC数据库]
	 * @param equipMESWWMapping 映射对象[MES数据库]
	 * @param parms 任务参数
	 * @param result 事件列表
	 * */
	private void qcAlarmPart(String tagName, AlarmHistory alarmHistory, EquipMESWWMapping equipMESWWMapping,
			JobParams parms, List<EventInformation> result) {
		String equipCode = equipMESWWMapping.getEquipCode();
		 
		EquipInfo equipline = equipInfoService.getEquipLineByEquip(equipCode);
		if(equipline!=null)
		{
			equipCode=equipline.getCode();
		}
		Receipt receiptRead = receiptService.getByTagName(tagName);
		Receipt receiptWrite =receiptService.getByTagName(tagName.replaceFirst("R_", "W_"));
	 
		if (receiptWrite!=null) {
			String upOrDown = null;
			if (HI_ALARM.equalsIgnoreCase(alarmHistory.getType() == null ? "" : alarmHistory.getType().trim())) {
				upOrDown = "设定上限为：";
			} else {
				upOrDown = "设定下限为：";
			}
			StringBuffer desc = new StringBuffer("设备：");
			desc.append(equipCode).append("，参数：").append(receiptWrite.getReceiptName()).append("[")
					.append(receiptWrite.getReceiptCode()).append("]").append("发生报警，请处理。");
			desc.append("设定值为：").append(receiptWrite.getReceiptTargetValue()).append(",采集值为：")
					.append(alarmHistory.getValue()).append(",").append(upOrDown).append(alarmHistory.getCheckValue());
			EventInformation t = this.initEventInfo("质量采集数据报警", desc.toString(), EventTypeContent.QA.name(),
					alarmHistory.getEventStampUTC(), receiptWrite.getProcessId(), equipCode, parms.getOrgCode());
			result.add(t);
		} else if(receiptRead!=null&&receiptWrite==null)
		{
			String upOrDown = null;
			if (HI_ALARM.equalsIgnoreCase(alarmHistory.getType() == null ? "" : alarmHistory.getType().trim())) {
				upOrDown = "设定上限为：";
			} else {
				upOrDown = "设定下限为：";
			}
			StringBuffer desc = new StringBuffer("设备：");
			desc.append(equipCode).append("，参数：").append(receiptRead.getReceiptName()).append("[")
					.append(receiptRead.getReceiptCode()).append("]").append("发生报警，请处理。");
			desc.append(",采集值为：")
					.append(alarmHistory.getValue()).append(",").append(upOrDown).append(alarmHistory.getCheckValue());
			EventInformation t = this.initEventInfo("质量采集数据报警", desc.toString(), EventTypeContent.QA.name(),
					alarmHistory.getEventStampUTC(), receiptRead.getProcessId(), equipCode, parms.getOrgCode());
			result.add(t);
		}else{
			logger.error(tagName + "is wrong not found in mes database");
		}
		 
	}

	/**
	 * <p>
	 * 封装事件对象
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-11-10 09:47:16
	 * @param title 事件标题
	 * @param eventContent 事件内容
	 * @param code 类型代码
	 * @param eventStamp 事件升级触发时间
	 * @param processId 工序ID
	 * @param equipCode 设备CODE
	 * @param orgCode 数据所属组织
	 * */
	private EventInformation initEventInfo(String title, String eventContent, String code, Date eventStamp,
			String processId, String equipCode, String orgCode) {
		EventInformation t = new EventInformation();
		t.setEventTitle(title);
		t.setEventContent(eventContent);
		t.setCode(code); // 设备异常
		t.setEventStatus(EventStatus.UNCOMPLETED);
		t.setProcessTriggerTime(eventStamp);
		t.setProcessId(processId);
		t.setEquipCode(equipCode);
		t.setOrgCode(orgCode);
		return t;
	}

	private List<AlarmHistory> purgeAlarms(List<AlarmHistory> alarms) {
		Map<String, AlarmHistory> alarmMap = new HashMap<String, AlarmHistory>();
		if (CollectionUtils.isEmpty(alarms)) {
			return new ArrayList<AlarmHistory>();
		}
		for (int i = 0; i < alarms.size(); i++) {
			AlarmHistory alarm = alarms.get(i);
			String tagName = alarm.getTagName();
			if (!this.validateTagName(tagName)) {
				continue;
			}
			tagName = tagName.substring(0, tagName.lastIndexOf(SPLIT_CHAR));
			if("R_BreakDPos".equalsIgnoreCase(tagName.substring(tagName.lastIndexOf(SPLIT_CHAR)+1)))
			{
				if (UNACK_RTN.equalsIgnoreCase(alarm.getAlarmState())) {
					//忽略此类警报
					continue;
				} 
			
			}
		
			if (UNACK_ALM.equalsIgnoreCase(alarm.getAlarmState())) {
				// 报警信息
				alarmMap.put(alarm.getTagName(), alarm);

			} else if (UNACK_RTN.equalsIgnoreCase(alarm.getAlarmState())) {
				// 报警恢复信息
				alarmMap.remove(alarm.getTagName());
			}

		}
		List<AlarmHistory> alst = new ArrayList<AlarmHistory>();
		alst.addAll(alarmMap.values());
		// 加入最后一条数据
		alst.add(alarms.get(alarms.size() - 1));
		return alst;
	}

	public static void main(String ares[]) {

		// String test="BD001.W_OrderL.Hi";
		// String tagName=test.substring(0, test.lastIndexOf(SPLIT_CHAR));

		System.out.print("");
	}
}
