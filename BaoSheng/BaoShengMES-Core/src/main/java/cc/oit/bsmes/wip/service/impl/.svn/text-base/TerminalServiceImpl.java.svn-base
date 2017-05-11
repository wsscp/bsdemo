package cc.oit.bsmes.wip.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.dao.EmployeeDAO;
import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.RoleEquip;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.bas.service.RoleEquipService;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.constants.WorkOrderOperateType;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.eve.dao.EventTypeDAO;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.model.EventProcess;
import cc.oit.bsmes.eve.model.EventProcessLog;
import cc.oit.bsmes.eve.model.EventType;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.eve.service.EventProcessLogService;
import cc.oit.bsmes.eve.service.EventProcessService;
import cc.oit.bsmes.eve.service.EventProcesserService;
import cc.oit.bsmes.eve.service.EventTypeService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.inv.dao.MatDAO;
import cc.oit.bsmes.inv.model.AssistOption;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.pla.dao.BorrowMatDAO;
import cc.oit.bsmes.pla.dao.OrderTaskDAO;
import cc.oit.bsmes.pla.dao.MaterialRequirementPlanDAO;
import cc.oit.bsmes.pla.dao.SupplementMaterialDAO;
import cc.oit.bsmes.pla.model.BorrowMat;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.model.SupplementMaterial;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.dao.ProductProcessDAO;
import cc.oit.bsmes.pro.service.ProcessInformationService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.dao.WorkOrderDAO;
import cc.oit.bsmes.wip.dao.WorkOrderEquipRelationDAO;
import cc.oit.bsmes.wip.dto.MethodReturnDto;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.TerminalService;
import cc.oit.bsmes.wip.service.WorkOrderOperateLogService;
import cc.oit.bsmes.wip.service.WorkOrderService;

@Service
public class TerminalServiceImpl implements TerminalService {
	@Resource
	private WorkOrderDAO workOrderDAO;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	private EventProcessLogService eventProcessLogService;
	@Resource
	private EventTypeDAO eventTypeDAO;
	@Resource
	private DataDicService dataDicService;
	@Resource
	private EventTypeService eventTypeService;
	@Resource
	private UserService userService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private WorkOrderOperateLogService workOrderOperateLogService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private RoleEquipService roleEquipService;
	@Resource
	private EventProcesserService eventProcesserService;
	@Resource
	private EventProcessService eventProcessService;
	@Resource
	private ProcessInformationService processInformationService;
	@Resource
	private EmployeeDAO employeeDAO;
	@Resource
	private EmployeeService employeeService;
	@Resource
	private WorkOrderEquipRelationDAO workOrderEquipRelationDAO;
	@Resource
	private SupplementMaterialDAO supplementMaterialDAO;
	@Resource
	private BorrowMatDAO borrowMatDAO;
	@Resource
	private MatDAO matDAO;
	@Resource
	private MaterialRequirementPlanDAO materialRequirementPlanDAO;
	@Resource
	private ProductProcessDAO productProcessDAO;
	@Resource
	private OrderTaskDAO orderTaskDAO;
	

	public static String VALID_NO_USER = "员工号不存在！";
	public static String VALID_ERROR_USER = "只允许该设备的维护员和生产线操作员执行该操作！";

	/**
	 * <p>
	 * 暂停原因
	 * </p>
	 * 
	 * @return List<DataDic>
	 * @author DingXintao
	 * @date 2014-7-15 11:12:48
	 * @see
	 */
	public List<DataDic> queryPauseReasonDic() {
		return dataDicService.getCodeByTermsCode(TermsCodeType.WORKORDER_PAUSE_REASON_DETAIL);
	}

	/**
	 * <p>
	 * 切换生产单
	 * </p>
	 * 
	 * @param userCode 工号
	 * @param password 密码
	 * @param equipCode 设备号
	 * @param pauseReason 暂停原因
	 * @return boolean
	 * @author DingXintao
	 * @date 2014-7-1 11:20:48
	 */
	@Transactional(readOnly = false)
	public MethodReturnDto changeOrderSeq(String userCode, String password, String equipCode, String oldWorkOrderNo, String newWorkOrderNo,
			String pauseReason) {
		WorkOrder workOrder = workOrderService.getByWorkOrderNO(newWorkOrderNo);
		if(workOrder.getStatus() == WorkOrderStatus.CANCELED){
			return new MethodReturnDto(false);
		}
		Employee employee = employeeService.validUserEquipPermissions(userCode, equipCode);
		MethodReturnDto dto = new MethodReturnDto(employee != null);
		if (employee == null) {
			dto.setMessage("您没有该设备的权限");
		} else {
			if (!password.equals(employee.getPassword())) {
				dto.setSuccess(false);
				dto.setMessage("您的密码错误，请重新输入!");
			}else if(!("BZZ".equals(employee.getCertificate()) || "DD".equals(employee.getCertificate()))){
				dto.setSuccess(false);
				dto.setMessage("请班组长或调度切换生产单!");
			} else {
				// 0、判断旧生产单是否在其他设备上有正在加工的任务，有不能更新生产单状态。
            	OrderTask findParams = new OrderTask();
            	findParams.setWorkOrderNo(oldWorkOrderNo);
            	List<OrderTask> orderTaskArray = orderTaskService.findByObj(findParams);
            	Boolean otherEquip = false;
            	for(OrderTask o : orderTaskArray){
            		if(StringUtils.isNotEmpty(o.getEquipCode()) && !o.getEquipCode().equals(equipCode)){
            			otherEquip = true;
            			break;
            		}
            	}
            	// 1、调整生产单在设备上加工的顺序
                workOrderEquipRelationDAO.updateSeqByWorkOrderNoAndEquipCode(oldWorkOrderNo, newWorkOrderNo, equipCode, userCode);
                // 2、将原来的生产单状态修改成暂停了(T_WIP_WORK_ORDER_OPERATE_LOG)
                workOrderOperateLogService.changeWorkOrderStatus(oldWorkOrderNo, equipCode, (otherEquip ? WorkOrderStatus.IN_PROGRESS : WorkOrderStatus.TO_DO), WorkOrderOperateType.SWITCH, pauseReason, userCode);
                // 3、将当前生产单状态改成暂停(T_WIP_WORK_ORDER)
                if(!otherEquip){
                	workOrderService.updateWorkerOrderStatusNotFinished(oldWorkOrderNo, WorkOrderStatus.TO_DO);
                }
                // 4、更新orderTask和prodec
                for(OrderTask o : orderTaskArray){ // 此处修复任务单完成了，状态被更新回去了的bug，出现一些已经完成了的生产单，状态依然是待加工
                	if(o.getStatus() != WorkOrderStatus.FINISHED  && StringUtils.isNotEmpty(o.getEquipCode()) && o.getEquipCode().equals(equipCode)){
            			o.setStatus(WorkOrderStatus.TO_DO);
            			orderTaskService.update(o);
            			CustomerOrderItemProDec pd = customerOrderItemProDecService.getById(o.getOrderItemProDecId());
            			pd.setStatus(ProductOrderStatus.TO_DO);
            			customerOrderItemProDecService.update(pd);
            		}
                }
			}
		}
		// 调整优先级
		return dto;
	}

	public List<MaterialRequirementPlan> getMaterialInfo(Map<String, Object> params) {
		return materialRequirementPlanDAO.getMaterialInfo(params);
	}

	public void insertMoreMaterial(Map<String, Object> params) {
		materialRequirementPlanDAO.insertMoreMaterial(params);
	}

	/**
	 * <p>
	 * 获取设备报警类型
	 * </p>
	 * 
	 * @return
	 * @author DingXintao
	 * @date 2014-6-10 14:16:48
	 * @see
	 */
	public List<EventType> equipAlarmType() {
		EventType findParams = new EventType();
		findParams.setNeedShow("1");
		List<EventType> alist = eventTypeService.findByObj(findParams);
		return alist;
	}

	/**
	 * <p>
	 * 获取设备报警类型 明细
	 * </p>
	 * 
	 * @param query 级联参数
	 * @return
	 * @author DingXintao
	 * @date 2014-6-10 14:16:48
	 * @see
	 */
	public List<DataDic> equipAlarmTypeDesc(String query) {
		return dataDicService.getCodeByTermsCodeAndExtatt(TermsCodeType.EVENT_TYPE_DETAIL, query);
	}

	/**
	 * @Title: triggerEquipAlarm
	 * @Description: TODO(手动触发报警提交)
	 * @param: equipCode 设备编码
	 * @param: eventTypeCode 事件类型
	 * @param: eventTypeCodeDesc 事件类型明细
	 * @param: operator 操作工
	 * @param: director 维修班负责人
	 * @return: MethodReturnDto
	 * @throws
	 */
	@Override
	@Transactional(readOnly = false)
	public MethodReturnDto triggerEquipAlarm(String equipCode, String eventTypeCode, String eventTypeCodeDesc, String operator, String director) {
		DataDic dataDic = dataDicService.getByDicCode("EVENT_TYPE_DETAIL", eventTypeCodeDesc);
		List<EventInformation> list = eventInformationService.getByEquipCode(equipCode,eventTypeCodeDesc);
		for (EventInformation eventInformation : list) {
			if (null != dataDic) {
				if (StringUtils.equalsIgnoreCase(eventInformation.getEqipEventCode(), eventTypeCodeDesc)) {
					if(EventStatus.PENDING.equals(eventInformation.getEventStatus())){//报修时候发现事件待确认
						return new MethodReturnDto(false, dataDic.getName()+"处理结束,请处理警报!");
					}else{
						return new MethodReturnDto(false, "设备故障已响应,维修工正在处理!");
					}
				}
			}
		}
		// 1.1、获取设备信息
		EquipInfo equip = equipInfoService.getMainEquipByEquipLine(equipCode);
		// 1.1.1、检验设备信息的完整性：设备维修负责人
		// if(null == equip.getMaintainers() ||
		// equip.getMaintainers().size()==0){
		// return new MethodReturnDto(false, "请先添加该设备的维修负责人！");
		// }
		// 1.2、获取workOrder信息
		WorkOrder workOrder = workOrderService.getCurrentByEquipCode(equipCode);
		// 1.3、获取事件类型信息
		EventType eventType = new EventType();
		eventType.setCode(eventTypeCode);
		eventType = eventTypeDAO.getOne(eventType);
		// 1.3.1、获取事件类型明细信息
		StringBuffer eventContent = new StringBuffer();
		eventContent.append("设备：").append(equip.getEquipAlias()).append("发生[").append(eventType.getName()).append("]：");
		boolean equipError = false;
		if (null != dataDic) {
			eventContent.append(dataDic.getName());
			if ("Y".equals(dataDic.getMarks())) {
				equipError = true;
			}
		} else {
			eventContent.append(eventTypeCodeDesc);
		}
		eventContent.append("。");
		// 1.4、获取当前用户信息
		User user = SessionUtils.getUser();

		// 2、封装并新增设备信息
		EventInformation t = new EventInformation();
		t.setId(UUID.randomUUID().toString());
		t.setEventTitle(eventType.getName());
		t.setEventContent(eventContent.toString());
		t.setCode(eventTypeCode);
		t.setEventStatus(EventStatus.UNCOMPLETED);
		t.setProcessTriggerTime(new Date());
		t.setProcessId(workOrder == null ? "" : workOrder.getProcessId());
		t.setEquipCode(equipCode);
		t.setCreateUserCode(StringUtils.isEmpty(operator) ? SessionUtils.getUser().getUserCode() : operator);
		t.setModifyUserCode(StringUtils.isEmpty(operator) ? SessionUtils.getUser().getUserCode() : operator);
		t.setOrgCode(user.getOrgCode());
		t.setMaintainPeople(director);
		t.setEqipEventCode(eventTypeCodeDesc);

		// 添加一级事件处理人
		// List<String> maintainers = equip.getMaintainers();
		List<String> processerList = new ArrayList<String>();
		// processerList.addAll(maintainers);
		EventProcess eventProcess = new EventProcess();
		eventProcess.setEventTypeId(eventType.getId());
		eventProcess.setProcessSeq(1);
		eventProcess.setStatus(true);
		List<EventProcess> eventProcessList = eventProcessService.getByObj(eventProcess);
		if (!CollectionUtils.isEmpty(eventProcessList)) {
			eventProcess = eventProcessList.get(0);
			// 获取事件处理人
			Map<String, String> map = eventProcesserService.getUserCodeByEventProcessId(eventProcess.getId());
			processerList.addAll(map.values());
		}
		// 判断当前生产单所属工序，由工序确认事件处理人(绝缘,成缆,护套,编织)
		if (null != workOrder && StringUtils.isNotBlank(workOrder.getProcessId())) {
			// ProductProcess productProcess =
			// productProcessService.getById(workOrder.getProcessId());
			// String processCode = productProcess.getProcessCode();
			// ProcessInformation processInfo =
			// processInformationService.getByCode(processCode);
			// String name = processInfo.getName();
			// String section = processInfo.getSection();

			String name = "编织";
			String section = "成缆";

			List<Employee> employeeList = null;
			if (name.equals("编织")) {
				employeeList = employeeDAO.getByNameSMS(name);
			} else {
				employeeList = employeeDAO.getByNameSMS(section);
			}
			List<String> tempList = new ArrayList<String>();
			for (Employee employee : employeeList) {
				tempList.add(employee.getUserCode());
			}
			processerList.addAll(tempList);
		}

		eventInformationService.insertInfo(t, processerList);

		// 3、设备故障更新设备状态: 设备故障中严重的才变更状态
		if (eventTypeCode.equals(EventTypeContent.EQIP.name()) && equipError) {
			// 更改设备状态，并更新设备状态历史
			equipInfoService.changeEquipStatus(equipCode, EquipStatus.ERROR, "alarm", true);
		}

		return new MethodReturnDto(true);
	}

	/**
	 * 终端手动处理警报
	 * 
	 * @param userCode 工号
	 * @param 事件ID
	 * @param equipCode 设备编码
	 * @return MethodReturnDto 返回信息
	 * @author DingXintao
	 * @date 2014年6月10日 下午11:44:33
	 * @see
	 */
	@Transactional(readOnly = false)
	public MethodReturnDto handleEquipAlarm(String userCode, String eventId, String equipCode) {
		// 1.1、验证权限
		if (!userService.isGroupUser(userCode)) {
			return new MethodReturnDto(false, VALID_NO_USER);
		}

		// 1.2、判断当前处理人是否为设备维护人员 || 1.3、判断当前处理人的角色是否为该生产线处理人
		// if (!this.isEquipMaintainer(equipCode, userCode) &&
		// !this.isRoleToEquipLine(equipCode, userCode)) {
		// return new MethodReturnDto(false, VALID_ERROR_USER);
		// }

		// 查询事件信息并更新状态
		EventInformation eventInformation = eventInformationService.getById(eventId);
		if (eventInformation.getEventStatus() == EventStatus.RESPONDED) {
			eventInformation.setStatus("1");
		} else {
			eventInformation.setEventStatus(EventStatus.RESPONDED);
		}
		eventInformationService.update(eventInformation);

		// 保存事件流程处理日志
		this.addeventProcessLogAfterHandlerEvent(eventId, userCode);

		// 更改设备状态，并更新设备状态历史
		this.updateEquipStatusAfterHandlerEvent(eventInformation.getEquipCode());

		return new MethodReturnDto(true);
	}

	/**
	 * 处理报设备故障 警告
	 * 
	 * @param userCode 工号
	 * @return MethodReturnDto 返回信息
	 * @author DingXintao
	 * @date 2014年6月11日 下午1:44:33
	 * @see
	 */
	@Transactional(readOnly = false)
	public MethodReturnDto handleEquipError(String userCode, String equipCode) {
		// 1.1、验证权限
		if (!userService.isGroupUser(userCode)) {
			return new MethodReturnDto(false, VALID_NO_USER);
		}

		// 1.2、判断当前处理人是否为设备维护人员 || 1.3、判断当前处理人的角色是否为该生产线处理人
		// if (!this.isEquipMaintainer(equipCode, userCode) &&
		// !this.isRoleToEquipLine(equipCode, userCode)) {
		// return new MethodReturnDto(false, VALID_ERROR_USER);
		// }

		// 根据设备号查询未完成的设备故障事件列表
		List<EventInformation> eventInfoList = this.getUncompleteEventInfoList(equipCode);

		// 更新事件状态：已完成
		for (EventInformation eventInfo : eventInfoList) {
			eventInfo.setEventStatus(EventStatus.RESPONDED);
			eventInformationService.update(eventInfo);

			// 保存警告流程处理日志
			this.addeventProcessLogAfterHandlerEvent(eventInfo.getId(), userCode);
		}

		// 更改设备状态，并更新设备状态历史
		this.updateEquipStatusAfterHandlerEvent(equipCode);

		return new MethodReturnDto(true);
	}

	/**
	 * <p>
	 * 根据设备号查询未完成的设备故障事件列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014年7月3日 17:5:33
	 */
	private List<EventInformation> getUncompleteEventInfoList(String equipCode) {
		// 查询设备故障的事件类型明细
		EventType eventType = this.getEventTypeByCode(EventTypeContent.EQIP);

		EventInformation eventInformation = new EventInformation();
		eventInformation.setEquipCode(equipCode);
		eventInformation.setEventTypeId(eventType.getId());
		eventInformation.setEventStatus(EventStatus.UNCOMPLETED);
		List<EventInformation> eventInfoList = eventInformationService.findByObj(eventInformation);
		if (null == eventInfoList) {
			return new ArrayList<EventInformation>();
		}
		return eventInfoList;
	}

	/**
	 * <p>
	 * 根据类型Code获取完整事件信息
	 * </p>
	 * 
	 * @param type 事件类型
	 * @author DingXintao
	 * @date 2014年7月3日 17:5:33
	 */
	private EventType getEventTypeByCode(EventTypeContent type) {
		EventType eventType = new EventType();
		eventType.setCode(type.name());
		eventType = eventTypeDAO.getOne(eventType);
		return eventType;
	}

	/**
	 * <p>
	 * 保存警告流程处理日志
	 * </p>
	 * 
	 * @param eventInfoId 事件信息ID
	 * @author DingXintao
	 * @date 2014年7月3日 17:5:33
	 */
	private void addeventProcessLogAfterHandlerEvent(String eventInfoId, String userCode) {
		EventProcessLog eventProcessLog = new EventProcessLog();
		eventProcessLog.setEventInfoId(eventInfoId);
		eventProcessLog.setCreateUserCode(userCode);
		eventProcessLog.setModifyUserCode(userCode);
		eventProcessLog.setType(EventStatus.RESPONDED);
		List<EventProcessLog> tmp = eventProcessLogService.findByObj(eventProcessLog);
		if (tmp != null && tmp.size() > 1) {
			return; // 若已经处理直接返回
		}
		eventProcessLog.setOrgCode(SessionUtils.getUser().getOrgCode());
		eventProcessLogService.insert(eventProcessLog);
	}

	/**
	 * <p>
	 * 处理完设备警报后更新设备状态
	 * </p>
	 * 
	 * @param equipCode 设备代码
	 * @author DingXintao
	 * @date 2014年7月3日 17:5:33
	 */
	private void updateEquipStatusAfterHandlerEvent(String equipCode) {
		// 根据设备号查询未完成的设备故障事件列表
		List<EventInformation> eventInfoList = this.getUncompleteEventInfoList(equipCode);
		if (!CollectionUtils.isEmpty(eventInfoList)) { // 设备存在故障不更新状态
			return;
		}

		// 判断设备上的生产单是否有生产中的，有改为调试中[IN_DEBUG]，否则改为空闲[IDLE]
		WorkOrder findParams = new WorkOrder();
		findParams.setEquipCode(equipCode);
		findParams.setStatus(WorkOrderStatus.IN_PROGRESS);
		if (!CollectionUtils.isEmpty(workOrderDAO.find(findParams))) {
			equipInfoService.changeEquipStatus(equipCode, EquipStatus.IN_DEBUG, "event", true);
		} else {
			equipInfoService.changeEquipStatus(equipCode, EquipStatus.IDLE, "event", true);
		}
	}

	/**
	 * <p>
	 * 库存管理块：获取当前工序的物料位置信息
	 * </p>
	 * 
	 * @param params 物料号和工序ID
	 * @return List<Inventory>
	 * @author DingXintao
	 * @date 2014-9-15 11:16:48
	 * @see
	 */
	public List<Inventory> invLocationList(Inventory params) {

		List<Inventory> list = inventoryService.getLocationByWorkOrderNo(params);

		return list;
	}

	/**
	 * <p>
	 * 判断操作用户是否为设备维护员
	 * </p>
	 * 
	 * @param equipCode 设备代码
	 * @param userCode 员工号
	 * @author DingXintao
	 * @date 2014年12月17日 17:5:33
	 */
	private boolean isEquipMaintainer(String equipCode, String userCode) {
		boolean isMaintainer = false;
		// EquipInfo equipInfo = StaticDataCache.getMainEquipInfo(equipCode);
		EquipInfo equipInfo = equipInfoService.getMainEquipByEquipLine(equipCode);

		List<String> maintainers = equipInfo.getMaintainers();
		if (null != maintainers) {
			for (String maintainer : maintainers) {
				if (maintainer.equals(userCode)) {
					isMaintainer = true;
					break;
				}
			}
		}
		return isMaintainer;
	}

	/**
	 * <p>
	 * 判断操作用户角色是否为生产线操作员
	 * </p>
	 * 
	 * @param equipCode 设备代码
	 * @param userCode 员工号
	 * @author DingXintao
	 * @date 2014年12月24日 17:5:33
	 */
	private boolean isRoleToEquipLine(String equipCode, String userCode) {
		boolean isPass = false;
		List<RoleEquip> roleEquips = roleEquipService.getRoleEquipByUserCode(equipCode, userCode);
		if (!CollectionUtils.isEmpty(roleEquips)) {
			isPass = true;
		}
		return isPass;
	}

	@Override
	public void saveBorrowMat(SupplementMaterial supplementMaterial) {
		supplementMaterialDAO.insert(supplementMaterial);
	}

	@Override
	public List<Mat> getMatbyWorkOrderNo(String workOrderNo, String processCode) {
		return matDAO.getMatName(workOrderNo, processCode);
	}

	@Override
	public List<AssistOption> getAssistOp(String processCode) {
		return matDAO.getAssistOp(processCode);
	}

	@Override
	public void insertBorrowMat(BorrowMat boMat) {
		// TODO Auto-generated method stub
		borrowMatDAO.insert(boMat);
	}

	@Override
	public void updateBorrowMat(BorrowMat b) {
		// TODO Auto-generated method stub
		borrowMatDAO.update(b);
	}

	@Override
	public void saveSupplementMaterial(SupplementMaterial b) {
		// TODO Auto-generated method stub
		supplementMaterialDAO.insert(b);
	}

	@Override
	public List<Map<String, Object>> getBZSemiProducts(String taskId) {
		return matDAO.getBZSemiProducts(taskId);
	}

	@Override
	public List<Map<String, Object>> getBZMaterialProps(String matCode) {
		return matDAO.getBZMaterialProps(matCode);
	}

	@Override
	public void fillPropInMap(List<Map<String, Object>> propMaps, Map<String, String> paramMapping, Map<String, String> outMap) {
		String propName = "PROP_NAME";
		String propValue = "PROP_TARGET_VALUE";
		for (Map<String, Object> propMap : propMaps) {
			if (propMap.get("PROP_NAME") == null) {
				propName = "CHECK_ITEM_NAME";
				propValue = "ITEM_TARGET_VALUE";
			}
			for (Map.Entry<String, String> entry : paramMapping.entrySet()) {
				if (propMap.get(propName).toString().contains("单位")) {
					continue;
				}
				if (propMap.get(propName).toString().contains(entry.getKey())) {
					outMap.put(entry.getValue(), propMap.get(propValue).toString());
					break;
				}
			}
		}

	}

	@Override
	public List<Map<String, Object>> getBZOutSemiProducts(String taskId) {
		return matDAO.getBZOutSemiProducts(taskId);
	}

	@Override
	public List<Map<String, String>> isJYProcess(String custOrderItemId) {
		return productProcessDAO.isJYProcess(custOrderItemId);
	}

	@Override
	public List<Map<String, String>> getRBMatPropsByProcessId(String processId) {
		return productProcessDAO.getRBMatPropsByProcessId(processId);
	}

	@Override
	public Map<String, String> getSemiOutColors(String getSemiOutColors) {
		return matDAO.getSemiOutColors(getSemiOutColors);
	}

	@Override
	public Map<String, String> getOrderTaskId(Map<String, String> param) {
		return orderTaskDAO.getOrderTaskId(param);
	}

}
