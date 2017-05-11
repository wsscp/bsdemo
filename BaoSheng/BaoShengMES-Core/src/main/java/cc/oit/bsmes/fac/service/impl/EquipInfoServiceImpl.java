package cc.oit.bsmes.fac.service.impl;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.bas.service.SysMessageService;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.constants.MaintainTemplateType;
import cc.oit.bsmes.common.constants.MaintainTriggerType;
import cc.oit.bsmes.common.constants.MesStatus;
import cc.oit.bsmes.common.constants.MesType;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.ReceiptStatus;
import cc.oit.bsmes.common.constants.ReceiptType;
import cc.oit.bsmes.common.constants.WorkOrderOperateType;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.complexQuery.CustomQueryParam;
import cc.oit.bsmes.common.mybatis.complexQuery.WithValueQueryParam;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.EquipLoadCache;
import cc.oit.bsmes.common.util.JSONUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.common.util.WebContextUtils;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.eve.service.MaintainCache;
import cc.oit.bsmes.fac.dao.EquipInfoDAO;
import cc.oit.bsmes.fac.dao.EquipRepairRecordDAO;
import cc.oit.bsmes.fac.dao.SparePartDAO;
import cc.oit.bsmes.fac.dao.StatusHistoryDAO;
//import cc.oit.bsmes.bas.model.EquipEnergyMonitor;
import cc.oit.bsmes.fac.model.EquipEnergyMonitor1;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.EquipMaintainState;
import cc.oit.bsmes.fac.model.EquipRepairRecord;
import cc.oit.bsmes.fac.model.MaintainRecord;
import cc.oit.bsmes.fac.model.MaintainTemplate;
import cc.oit.bsmes.fac.model.SparePart;
import cc.oit.bsmes.fac.model.StatusHistory;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.EquipMaintainStateService;
import cc.oit.bsmes.fac.service.MaintainRecordService;
import cc.oit.bsmes.fac.service.MaintainTemplateService;
import cc.oit.bsmes.fac.service.StatusHistoryService;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamHistoryAcquisition;
import cc.oit.bsmes.interfaceWWIs.service.DataIssuedService;
import cc.oit.bsmes.interfacemessage.model.Message;
import cc.oit.bsmes.interfacemessage.service.MessageService;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ReceiptService;
import cc.oit.bsmes.wip.service.WorkOrderOperateLogService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.thinkgem.jeesite.api.common.EnergyMonitor;

@Service
public class EquipInfoServiceImpl extends BaseServiceImpl<EquipInfo> implements EquipInfoService {

	@Resource
	private EquipInfoDAO equipInfoDAO;
	@Resource
	private StatusHistoryDAO statusHistoryDAO;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private ReceiptService receiptService;
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private ProcessReceiptService processReceiptService;
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	private SysMessageService sysMessageService;
	@Resource
	private EmployeeService employeeService;
	@Resource
	private MaintainTemplateService maintainTemplateService;
	@Resource
	private MaintainRecordService maintainRecordService;
	@Resource
	private DataIssuedService dataIssuedService;
	@Resource
	private StatusHistoryService statusHistoryService;
	@Resource
	private EquipMaintainStateService equipMaintainStateService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private MessageService messageService;
	@Resource
	private EquipMESWWMappingService equipMESWWMappingService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private EquipRepairRecordDAO equipRepairRecordDAO;
	@Resource
	private SparePartDAO sparePartDAO;
	@Resource
	private WorkOrderOperateLogService workOrderOperateLogService;
	
	@Override
	public EquipInfo getByCode(String code, String orgCode) {
		EquipInfo findParams = new EquipInfo();
		findParams.setCode(code);
		findParams.setOrgCode(orgCode);
		return equipInfoDAO.getOne(findParams);
	}

	@Override
	public List<EquipInfo> getByWipProcessId(String processId) {
		return equipInfoDAO.getEquipInfosByWipProcessId(processId);
	}

	@Override
	public List<EquipInfo> getByOrgCode(String orgCode, EquipType type) {
		EquipInfo findParams = new EquipInfo();
		findParams.setOrgCode(orgCode);
		findParams.setType(type);
		return equipInfoDAO.get(findParams);
	}

	@Override
	public List<EquipInfo> getByProcessSection(String orgCode, EquipType type, String processCode) {
		return equipInfoDAO.getEquipBySection(processCode, orgCode);
	}

	@Override
	public void initEquipLoad(String orgCode) {
		// 调整设备加载剩余
		EquipLoadCache.init(orgCode);

		// 删除
		equipInfoDAO.initEquipLoad(orgCode);
	}

	/**
	 * 计算OA前调用存储过程：删除组织下设备产能
	 * 
	 * @author DingXintao
	 * @date 2015-08-28 上午9:19:27
	 * @param orgCode 组织编码
	 */
	@Override
	public void callInitOrderTask(String orgCode) {
		equipInfoDAO.initEquipLoad(orgCode);
	}

	@Override
	@Transactional(readOnly = false)
	public void changeEquipStatus(String equipLineCode, EquipStatus status, String operator, boolean needChanegeAlarm) {
		equipInfoDAO.updateEquipStatus(equipLineCode, status.name(), operator);
		EquipInfo mainequip = getMainEquipByEquipLine(equipLineCode);
		// 更改设备的报警状态
		if (needChanegeAlarm) {
			dataIssuedService.UpdateEquipAlarmState(mainequip.getCode(), status);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void changeEquipStatusBetweenDebugAndInProgress(String equipCode, String operator) {
		EquipInfo equip = getByCode(equipCode, SessionUtils.getUser().getOrgCode());
		if (equip.getStatus().equals(EquipStatus.IN_DEBUG)) {
			changeEquipStatus(equipCode, EquipStatus.IN_PROGRESS, operator, true);
		} else {
			changeEquipStatus(equipCode, EquipStatus.IN_DEBUG, operator, true);
		}
	}

	/**
	 * 终端接受任务的操作<br/>
	 * 1、验证;<br/>
	 * 2、调用过程更新订单状态;<br/>
	 * 3、更新设备状态;<br/>
	 * 4、新增下发参数表.<br/>
	 * 
	 * @author DingXintao
	 * @param equipCode 设备编码
	 * @param workOrderNo 生产单号
	 * @param operator 经办人
	 * */
	@Override
	public JSONObject mesClientAcceptTask(String equipCode, String workOrderNo, String operator) {
		JSONObject result = new JSONObject();
		WorkOrder workOrder = workOrderService.getByWorkOrderNO(workOrderNo);
		String userCode = SessionUtils.getUser().getUserCode();
		if (workOrder == null) {
			result.put("success", false);
			result.put("msg", "生产单不存在");
		} if (workOrder.getStatus() == WorkOrderStatus.CANCELED) {
			result.put("success", false);
			result.put("msg", "生产单已取消！");
		} else {
			// 1、验证是否选择了任务单
			OrderTask findParams = new OrderTask();
			findParams.setWorkOrderNo(workOrderNo);
			findParams.setStatus(WorkOrderStatus.IN_PROGRESS);
			List<OrderTask> orderTaskArray = orderTaskService.findByObj(findParams);
			if (orderTaskArray == null || orderTaskArray.size() == 0) {
				result.put("success", false);
				result.put("msg", "请选择一个生产任务");
			}
			// 2、调用过程接受任务
			workOrderService.acceptWorkOrder(workOrder.getWorkOrderNo(), SessionUtils.getUser().getUserCode());
			// 2.1、生产单状态变更记录
			workOrderOperateLogService.changeWorkOrderStatus(workOrderNo, equipCode, WorkOrderStatus.IN_PROGRESS, WorkOrderOperateType.ACCEPT, WorkOrderOperateType.ACCEPT.toString(), userCode);
			// 3、修改设备状态
			changeEquipStatus(equipCode, EquipStatus.IN_DEBUG, operator, true);
			// 4、下发表数据获取：从MES_WWW表获取
			List<Receipt> receiptList = new ArrayList<Receipt>(); // 接受需要下发的参数
			EquipInfo mainEquipInfo = StaticDataCache.getMainEquipInfo(equipCode); // 获取主设备
			EquipMESWWMapping findParams1 = new EquipMESWWMapping();
			findParams1.setEquipCode(mainEquipInfo.getCode());
			findParams1.setNeedIs(true);
			List<EquipMESWWMapping> mappingArray = equipMESWWMappingService.findByObj(findParams1);
			for (EquipMESWWMapping mapping : mappingArray) {
				Receipt receipt = new Receipt();
				receipt.setWorkOrderId(workOrder.getId());

				receipt.setReceiptCode(mapping.getParmCode());
				receipt.setReceiptName(mapping.getParmName());
				receipt.setEquipCode(mapping.getEquipCode());
				receipt.setStatus(ReceiptStatus.INITIAL);
				// 此处需要重新设计思考
				// receipt.setReceiptId(qc.getId());
				// receipt.setReceiptTargetValue(qc.getItemTargetValue());
				// receipt.setReceiptMaxValue(qc.getItemMaxValue());
				// receipt.setReceiptMinValue(qc.getItemMinValue());
				// receipt.setNeedAlarm(StringUtils.isBlank(qc.getNeedAlarm()) ?
				// null : WebConstants.YES.equals(qc
				// .getNeedAlarm()));
				// receipt.setFrequence(qc.getFrequence());
				receipt.setType(ReceiptType.PROCESS_RECEIPT);
				initWorkderInfo(workOrder, receipt);
				receiptList.add(receipt);
			}
			receiptService.insert(receiptList);
			// 创建报工单
			// reportService.insert(wo.getWorkOrderNo());
			result.put("success", true);
		}
		return result;
	}

	/**
	 * 将一些基本信息set进去
	 * */
	private void initWorkderInfo(WorkOrder workOrder, Receipt issedParm) {
		// 计米器设定 计米器设定 W_OrderL
		if ("W_OrderL".equalsIgnoreCase(issedParm.getReceiptCode())) {
			issedParm.setReceiptTargetValue(workOrder.getOrderLength().toString());
			issedParm.setReceiptMaxValue("");
			issedParm.setReceiptMinValue("");
			issedParm.setNeedAlarm(false);
		}
		// 订单编号 M_OrderN
		if ("M_OrderN".equalsIgnoreCase(issedParm.getReceiptCode())) {
			issedParm.setReceiptMaxValue("");
			issedParm.setReceiptMinValue("");
			issedParm.setReceiptTargetValue(workOrder.getWorkOrderNo());
			issedParm.setNeedAlarm(false);
		}

		CustomerOrderItemProDec prodec = null;
		List<CustomerOrderItemProDec> prodecArray = customerOrderItemProDecService
				.getByWoNo(workOrder.getWorkOrderNo());
		for (CustomerOrderItemProDec p : prodecArray) {
			if (p.getStatus() == ProductOrderStatus.IN_PROGRESS) {
				prodec = p;
			}
		}
		if ("M_OrderM".equalsIgnoreCase(issedParm.getReceiptCode())) {
			// 半成品 产品型号 产品型号 M_OrderM 无型号，直接写入半成品代码
			issedParm.setReceiptMaxValue("");
			issedParm.setReceiptMinValue("");
			issedParm.setReceiptTargetValue(prodec == null ? "" : prodec.getCustProductType());
			issedParm.setNeedAlarm(false);
		}
		if ("M_OrderS".equalsIgnoreCase(issedParm.getReceiptCode())) {
			// 半成品 产品规格 产品规格 M_OrderS
			issedParm.setReceiptMaxValue("");
			issedParm.setReceiptMinValue("");
			issedParm.setReceiptTargetValue(prodec == null ? "" : prodec.getCustProductSpec());
			issedParm.setNeedAlarm(false);
		}
		if ("M_ContractN".equalsIgnoreCase(issedParm.getReceiptCode())) {
			// 合同号
			issedParm.setReceiptMaxValue("");
			issedParm.setReceiptMinValue("");
			issedParm.setReceiptTargetValue(prodec == null ? "" : prodec.getContractNo());
			issedParm.setNeedAlarm(false);
		}
		if ("M_Worker".equalsIgnoreCase(issedParm.getReceiptCode())) {
			// 制单人
			issedParm.setReceiptMaxValue("");
			issedParm.setReceiptMinValue("");
			issedParm.setReceiptTargetValue(workOrder.getDocMakerUserCode());
			issedParm.setNeedAlarm(false);
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EquipInfo> getAllProductLine() {
		User user = SessionUtils.getUser();
		if (user == null) {
			return null;
		}

		Map parm = new HashMap();
		parm.put("type", EquipType.PRODUCT_LINE);
		parm.put("orgCode", user.getOrgCode());
		return equipInfoDAO.getEquipLine(parm);
	}

	@Override
	public List<EquipInfo> findByName(String name) {
		return equipInfoDAO.findByName(name);
	}

	@Override
	public List<EquipInfo> findByProcessId(String processId) {
		return equipInfoDAO.findByProcessId(processId);
	}

	@Override
	public String getEquipNameByProcessIdAndOrderItemProDecId(String processId, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderItemProDecId", id);
		map.put("processId", processId);
		return equipInfoDAO.getEquipNameByProcessIdAndOrderItemProDecId(map);
	}

	@Override
	public EquipInfo getMainEquipByEquipLine(String equipLineCode) {
		return equipInfoDAO.getMainEquipByEquipLine(equipLineCode);
	}

	@Override
	public List<EquipInfo> getTotalByStatus(String orgCode, String[] equipStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgCode", SessionUtils.getUser().getOrgCode());
		map.put("equipStatus", equipStatus);
		return equipInfoDAO.getTotalByStatus(map);
	}

	/**
	 * 根据设备编码和组织机构获取生产线
	 * 
	 * @param equipCode 生产线编码
	 * @param orgCode 组织机构
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public EquipInfo getEquipLineByEquip(String equipCode) {
		Map map = new HashMap();
		map.put("equipCode", equipCode);
		map.put("orgCode", this.getOrgCode());
		return equipInfoDAO.getEquipLineByEquip(map);
	}

	@Override
	public List<EquipInfo> getEquipInfoByProcessId(String processId) {
		return equipInfoDAO.getEquipInfoByProcessId(processId);
	}

	/**
	 * <p>
	 * Job启动设备维修检测
	 * </p>
	 * 1、查询出所有的设备<br/>
	 * 2、遍历设备获取该设备下的所有维修模版<br/>
	 * 3、
	 * 
	 * */
	@Override
	public void createMaintainEvent(String orgCode) {
		EquipInfo findParams = new EquipInfo();
		findParams.setOrgCode(orgCode);
		findParams.setType(EquipType.MAIN_EQUIPMENT);
		List<EquipInfo> equips = equipInfoDAO.get(findParams);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date dateToMaintain = calendar.getTime();

		int timeAhead = WebContextUtils.getSysParamIntValue("fac.alarm.timeAhead", orgCode);
		int runtimeAhead = WebContextUtils.getSysParamIntValue("fac.alarm.runtimeAhead", orgCode);// 单位：小时
		calendar.add(Calendar.DATE, timeAhead);
		Date dateToAlarm = calendar.getTime();

		// 获取当前时间和昨天的时间
		calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		calendar.add(Calendar.DATE, -1);
		Date lastDate = calendar.getTime();

		for (EquipInfo equip : equips) {
			MaintainTemplate tmplParams = new MaintainTemplate();
			tmplParams.setModel(equip.getModel());
			List<MaintainTemplate> maintainTemplates = maintainTemplateService.getByObj(tmplParams);
			Multimap<MaintainTemplateType, MaintainTemplate> tmplMap = ArrayListMultimap.create();
			// Map<MaintainTemplateType, MaintainTemplate> tmplMap = new
			// HashMap<MaintainTemplateType, MaintainTemplate>();
			for (MaintainTemplate maintainTemplate : maintainTemplates) {
				tmplMap.put(maintainTemplate.getType(), maintainTemplate);
			}

			// 点检
			this.dailyMaintain(equip, tmplMap.get(MaintainTemplateType.DAILY));

			// 月检
			this.maintain(equip, tmplMap.get(MaintainTemplateType.MONTHLY), dateToMaintain, dateToAlarm, now,
					runtimeAhead);
			// 一级保养
			this.maintain(equip, tmplMap.get(MaintainTemplateType.FIRST_CLASS), dateToMaintain, dateToAlarm, now,
					runtimeAhead);
			// 二级保养
			this.maintain(equip, tmplMap.get(MaintainTemplateType.SECOND_CLASS), dateToMaintain, dateToAlarm, now,
					runtimeAhead);
			// 大修
			this.maintain(equip, tmplMap.get(MaintainTemplateType.OVERHAUL), dateToMaintain, dateToAlarm, now,
					runtimeAhead);
		}

		List<EquipMaintainState> equipMaintainStates = equipMaintainStateService.getUncompletedMaintainSates(orgCode);
		MaintainCache.setEquipMaintainStates(equipMaintainStates, orgCode);
	}

	/**
	 * <p>
	 * Job 点检
	 * </p>
	 * 
	 * @param EquipInfo equip 设备信息
	 * @param Collection <MaintainTemplate> maintainTemplate 设备点检的维修模版集合
	 * 
	 * */
	private void dailyMaintain(EquipInfo equip, Collection<MaintainTemplate> maintainTemplates) {
		// 获取当前时间和昨天的时间
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		calendar.add(Calendar.DATE, -1);
		Date lastDate = calendar.getTime();

		if (maintainTemplates == null || maintainTemplates.size() == 0) {
			return;
		}

		// 获取模版，第一个
		MaintainTemplate maintainTemplate = maintainTemplates.iterator().next();
		if (maintainTemplate.getCreateTime().compareTo(lastDate) > 0) { // 今天刚建立
			return;
		}

		List<CustomQueryParam> customQueryParams = new ArrayList<CustomQueryParam>();
		customQueryParams.add(new WithValueQueryParam("equipCode", "=", equip.getCode()));
		customQueryParams.add(new WithValueQueryParam("tmplId", "=", maintainTemplate.getId()));
		customQueryParams.add(new WithValueQueryParam("finishTime", ">", lastDate));
		customQueryParams.add(new WithValueQueryParam("finishTime", "<", now));
		List<MaintainRecord> records = maintainRecordService.query(customQueryParams);

		if (records.size() == 0) {
			EventInformation eventInfo = new EventInformation();
			eventInfo.setEventTitle("点检未做提醒");
			eventInfo.setEventContent(equip.getCode() + "点检未做");
			eventInfo.setEventStatus(EventStatus.UNCOMPLETED);
			eventInfo.setProcessTriggerTime(new Date());
			eventInfo.setBatchNo(maintainTemplate.getId());
			EquipInfo object = StaticDataCache.getLineEquipInfo(equip.getCode());
			if (object != null) {
				eventInfo.setEquipCode(object.getCode());
			} else {
				eventInfo.setEquipCode(equip.getCode());
			}
			eventInfo.setCode(EventTypeContent.MAINTAIN_DAILY.name());
			eventInformationService.insertInfo(eventInfo);
		}
	}

	/**
	 * <p>
	 * Job 设备维修
	 * </p>
	 * 
	 * @param EquipInfo equip 设备信息
	 * @param Collection <MaintainTemplate> maintainTemplate 设备点检的维修模版集合
	 * 
	 * */
	private void maintain(EquipInfo equip, Collection<MaintainTemplate> maintainTemplates, Date dateToMaintain,
			Date dateToAlarm, Date now, int runtimeAhead) {

		if (maintainTemplates == null || maintainTemplates.size() == 0) {
			return;
		}

		// 遍历检测类型的模版
		MaintainTemplate maintainTemplate = null;
		Iterator<MaintainTemplate> it = maintainTemplates.iterator();
		while (it.hasNext()) {
			maintainTemplate = it.next();
			boolean isNotice = false; // 提醒了就不要再提醒了
			if (MaintainTriggerType.NATURE.equals(maintainTemplate.getTriggerType())) { // 自然时间提醒
				isNotice = this.natureMaintain(equip, maintainTemplate, dateToMaintain, dateToAlarm);
			} else if (MaintainTriggerType.RUNTIME.equals(maintainTemplate.getTriggerType())) { // 设备运行时间提醒
				isNotice = this
						.runtimeMaintain(equip, maintainTemplate, dateToMaintain, dateToAlarm, now, runtimeAhead);
			}
			if (isNotice) {
				break;
			}
		}
	}

	/**
	 * <p>
	 * Job 按自然时间检测
	 * </p>
	 * 
	 * @param EquipInfo equip 设备信息
	 * @param Collection <MaintainTemplate> maintainTemplate 设备点检的维修模版集合
	 * 
	 * */
	private boolean natureMaintain(EquipInfo equip, MaintainTemplate maintainTemplate, Date dateToMaintain,
			Date dateToAlarm) {
		boolean isNotice = false;
		if (maintainTemplate == null) {
			return isNotice;
		}

		MaintainTemplateType maintainTemplateType = maintainTemplate.getType();
		Date date = null;
		switch (maintainTemplateType) {
		case MONTHLY:
			date = equip.getNextMaintainDate();
			break;
		case FIRST_CLASS:
			date = equip.getNextMaintainDateFirst();
			break;
		case SECOND_CLASS:
			date = equip.getNextMaintainDateSecond();
			break;
		case OVERHAUL:
			date = equip.getNextMaintainDateOverhaul();
			break;
		default:
			break;
		}
		String maintainers = getMaintainers(equip);

		if (DateUtils.isSameDay(dateToAlarm, date)) {
			if (maintainTemplateType == MaintainTemplateType.FIRST_CLASS
					|| maintainTemplateType == MaintainTemplateType.SECOND_CLASS) {
				List<String> userCodes = equip.getMaintainers();
				List<String> telephoneList = new ArrayList<String>();
				for (String userCode : userCodes) {
					Employee employee = employeeService.getByUserCode(userCode);
					telephoneList.add(employee.getTelephone());
				}
				String telephoneListStr = telephoneList.toString();
				Message sms = new Message();
				sms.setConsignee(telephoneListStr.subSequence(1, telephoneListStr.indexOf(']')).toString());
				sms.setMesTitle("设备维护");
				sms.setMesContent("设备" + equip.getCode() + "将进行" + maintainTemplateType.toString() + "，请做好准备工作。");
				sms.setSendTimes(0);
				sms.setMesType(MesType.SMS);
				sms.setStatus(MesStatus.NEW);
				messageService.insert(sms);
			}

			for (String maintainer : equip.getMaintainers()) {
				sysMessageService.sendMessage(maintainer, "设备" + maintainTemplateType.toString(),
						"设备" + equip.getCode() + "将进行" + maintainTemplateType.toString() + "，请做好准备工作。");
			}

			// for统计
			EquipMaintainState maintainState = new EquipMaintainState();
			maintainState.setEquipCode(equip.getCode());
			maintainState.setEquipName(equip.getName());
			maintainState.setStartDate(date);
			maintainState.setTimeNeeded(maintainTemplate.getTime());
			maintainState.setMaintainer(maintainers);
			maintainState.setMaintainType(maintainTemplate.getType());
			equipMaintainStateService.insert(maintainState);
			isNotice = true;
		}

		// 月检提醒
		if (DateUtils.isSameDay(dateToMaintain, date)) {
			EventInformation eventInfo = new EventInformation();
			eventInfo.setEventContent("设备" + equip.getCode() + "[" + equip.getName() + "]"
					+ maintainTemplate.getType().toString() + "提醒");
			eventInfo.setEventTitle("设备" + maintainTemplate.getType().toString() + "提醒");
			eventInfo.setEquipCode(equip.getCode());
			eventInfo.setEventStatus(EventStatus.UNCOMPLETED);
			eventInfo.setProcessTriggerTime(new Date());
			eventInfo.setBatchNo(maintainTemplate.getId());
			eventInfo.setCode(EventTypeContent.MAINTAIN.name());
			eventInformationService.insertInfo(eventInfo, equip.getMaintainers());

			// for统计
			EquipMaintainState maintainState = equipMaintainStateService.getUncompletedMaintainSate(equip.getCode(),
					equip.getOrgCode(), maintainTemplateType);
			maintainState.setStartDate(date);
			maintainState.setEventInfoId(eventInfo.getId());
			maintainState.setTimeNeeded(maintainTemplate.getTime());
			maintainState.setMaintainer(maintainers);
			equipMaintainStateService.update(maintainState);
			isNotice = true;
		}

		return isNotice;
	}

	/**
	 * <p>
	 * Job 按设备运行时间检测
	 * </p>
	 * 
	 * @param EquipInfo equip 设备信息
	 * @param Collection <MaintainTemplate> maintainTemplate 设备点检的维修模版集合
	 * 
	 * */
	private boolean runtimeMaintain(EquipInfo equip, MaintainTemplate maintainTemplate, Date dateToMaintain,
			Date dateToAlarm, Date now, int runtimeAhead) {
		boolean isNotice = false;
		if (maintainTemplate == null) {
			return isNotice;
		}

		if (maintainTemplate.getTriggerType() == MaintainTriggerType.NATURE) {
			return natureMaintain(equip, maintainTemplate, dateToMaintain, dateToAlarm);
		}

		MaintainRecord latestRecord = maintainRecordService.getLatest(equip.getCode(), maintainTemplate.getType());
		Date lastMaintainDate = latestRecord.getFinishTime();
		// 如果最近做过更大级别的保养则上次保养日期修正
		lastMaintainDate = fixLastMaintainDate(equip, maintainTemplate, lastMaintainDate);

		double equipTotalWorkHour = statusHistoryService.getEquipTotalWorkHour(equip.getCode(), lastMaintainDate, now);

		MaintainTemplateType maintainTemplateType = maintainTemplate.getType();
		String maintainers = getMaintainers(equip);

		// 月检准备提醒
		if (equipTotalWorkHour + runtimeAhead >= maintainTemplate.getTriggerCycle()) {
			for (String maintainer : equip.getMaintainers()) {
				sysMessageService.sendMessage(maintainer, "设备" + maintainTemplateType.toString(),
						"设备" + equip.getCode() + "将进行" + maintainTemplateType.toString() + "，请做好准备工作。");
			}

			// for统计
			EquipMaintainState maintainState = new EquipMaintainState();
			maintainState.setEquipCode(equip.getCode());
			maintainState.setEquipName(equip.getName());
			maintainState.setTimeNeeded(maintainTemplate.getTime());
			maintainState.setMaintainType(maintainTemplate.getType());
			// maintainState.setMaintainer(maintainers);
			maintainState.setLastMaintainDate(lastMaintainDate);
			maintainState.setTriggerCycle(maintainTemplate.getTriggerCycle());
			equipMaintainStateService.insert(maintainState);
			isNotice = true;
		}

		if (equipTotalWorkHour >= maintainTemplate.getTriggerCycle()) {
			EventInformation eventInfo = new EventInformation();
			eventInfo.setEventContent("设备" + equip.getCode() + "[" + equip.getName() + "]"
					+ maintainTemplate.getType().toString() + "提醒");
			eventInfo.setEventTitle("设备" + maintainTemplate.getType().toString() + "提醒");
			eventInfo.setEventStatus(EventStatus.UNCOMPLETED);
			eventInfo.setProcessTriggerTime(new Date());
			eventInfo.setBatchNo(maintainTemplate.getId());
			eventInfo.setEquipCode(equip.getCode());
			eventInfo.setCode(EventTypeContent.MAINTAIN.name());
			eventInformationService.insertInfo(eventInfo, equip.getMaintainers());

			// for统计
			EquipMaintainState maintainState = equipMaintainStateService.getUncompletedMaintainSate(equip.getCode(),
					equip.getOrgCode(), maintainTemplateType);
			maintainState.setTimeNeeded(maintainTemplate.getTime());
			maintainState.setMaintainer(maintainers);
			maintainState.setEventInfoId(eventInfo.getId());
			maintainState.setLastMaintainDate(lastMaintainDate);
			maintainState.setTriggerCycle(maintainTemplate.getTriggerCycle());
			equipMaintainStateService.update(maintainState);
			isNotice = true;
		}

		return isNotice;
	}

	private String getMaintainers(EquipInfo equip) {
		StringBuilder maintainers = new StringBuilder();
		for (String maintainer : equip.getMaintainers()) {
			Employee employee = employeeService.getByUserCode(maintainer);
			maintainers.append(employee.getName());
			maintainers.append(" ");
		}
		return maintainers.toString();
	}

	@Override
	public Date fixLastMaintainDate(EquipInfo equip, MaintainTemplate maintainTemplate, Date lastMaintainDate) {
		lastMaintainDate = fixLastMaintainDate0(equip, maintainTemplate, lastMaintainDate,
				MaintainTemplateType.SECOND_CLASS);
		lastMaintainDate = fixLastMaintainDate0(equip, maintainTemplate, lastMaintainDate,
				MaintainTemplateType.OVERHAUL);
		return lastMaintainDate;
	}

	private void setNextMaintainDateStr(Map<MaintainTemplateType, MaintainTemplate> tmplMap, EquipInfo equipInfo,
			MaintainTemplateType type, Date now) {
		MaintainTemplate template = tmplMap.get(type);
		if (template == null) {
			return;
		}
		if (template.getTriggerType() != MaintainTriggerType.RUNTIME) {
			return;
		}
		MaintainRecord latestRecord = maintainRecordService.getLatest(equipInfo.getCode(), type);
		int equipTotalWorkHour = 0;
		if (latestRecord != null) {
			Date lastMaintainDate = latestRecord.getFinishTime();
			// 如果最近做过更大级别的保养则上次保养日期修正
			lastMaintainDate = fixLastMaintainDate(equipInfo, template, lastMaintainDate);
			equipTotalWorkHour = (int) statusHistoryService.getEquipTotalWorkHour(equipInfo.getCode(),
					lastMaintainDate, now);
		}
		String nextMaintainDateStr = equipTotalWorkHour + "/" + template.getTriggerCycle() + "小时";
		switch (type) {
		case FIRST_CLASS:
			equipInfo.setNextMaintainDateFirstStr(nextMaintainDateStr);
			break;
		case SECOND_CLASS:
			equipInfo.setNextMaintainDateSecondStr(nextMaintainDateStr);
			break;
		case OVERHAUL:
			equipInfo.setNextMaintainDateOverhaulStr(nextMaintainDateStr);
			break;
		}
	}

	private Date fixLastMaintainDate0(EquipInfo equip, MaintainTemplate maintainTemplate, Date lastMaintainDate,
			MaintainTemplateType largerType) {
		if (maintainTemplate.getType() != largerType) {
			MaintainRecord _latestRecord = maintainRecordService.getLatest(equip.getCode(), largerType);
			if (_latestRecord == null || _latestRecord.getFinishTime() == null) {
				return null;
			}
			Date _lastMaintainDate = _latestRecord.getFinishTime();
			if (_lastMaintainDate.getTime() > lastMaintainDate.getTime()) {
				lastMaintainDate = _lastMaintainDate;
			}
		}
		return lastMaintainDate;
	}

	@Override
	public List<Date> getMaintainDates(EquipInfo equipInfo, Date endDate) {
		List<Date> maintainDates = new ArrayList<Date>();
		Date nextMaintainDate = equipInfo.getNextMaintainDate();
		if (nextMaintainDate == null) {
			return maintainDates;
		}
		maintainDates.add(nextMaintainDate);

		MaintainTemplate findParams = new MaintainTemplate();
		findParams.setModel(equipInfo.getModel());
		findParams.setType(MaintainTemplateType.MONTHLY);
		List<MaintainTemplate> maintainTemplates = maintainTemplateService.getByObj(findParams);
		if (maintainTemplates.size() == 0) {
			return maintainDates;
		}

		MaintainTemplate maintainTemplate = maintainTemplates.get(0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nextMaintainDate);

		Date maintainDate = nextMaintainDate;
		while (true) {
			calendar.add(Calendar.MONTH, maintainTemplate.getTriggerCycle());
			maintainDate = calendar.getTime();
			if (maintainDate.compareTo(endDate) < 0) {
				maintainDates.add(maintainDate);
			} else {
				break;
			}
		}

		return maintainDates;
	}

	@Override
	public void reverseEquipStatus(String equipCode) {
		EquipInfo equipInfo = reverseEquipStatus0(equipCode);

		// 更新设备状态
		EquipInfo mainequip = getMainEquipByEquipLine(equipCode);
		if (mainequip != null) {
			reverseEquipStatus0(mainequip.getCode());
		}

		// 更改设备的报警状态
		dataIssuedService.UpdateEquipAlarmState(mainequip.getCode(), equipInfo.getStatus());
	}

	@Override
	public void fixNextMaintainDate(Date now, EquipInfo equipInfo) {
		// 修正下次维修时间
		MaintainTemplate tmplParams = new MaintainTemplate();
		tmplParams.setModel(equipInfo.getModel());
		List<MaintainTemplate> maintainTemplates = maintainTemplateService.getByObj(tmplParams);
		Map<MaintainTemplateType, MaintainTemplate> tmplMap = new HashMap<MaintainTemplateType, MaintainTemplate>();
		for (MaintainTemplate maintainTemplate : maintainTemplates) {
			tmplMap.put(maintainTemplate.getType(), maintainTemplate);
		}

		setNextMaintainDateStr(tmplMap, equipInfo, MaintainTemplateType.FIRST_CLASS, now);
		setNextMaintainDateStr(tmplMap, equipInfo, MaintainTemplateType.SECOND_CLASS, now);
		setNextMaintainDateStr(tmplMap, equipInfo, MaintainTemplateType.OVERHAUL, now);
	}

	private EquipInfo reverseEquipStatus0(String equipCode) {
		// 获取状态记录
		EquipInfo findParams = new EquipInfo();
		findParams.setCode(equipCode);
		EquipInfo equipInfo = equipInfoDAO.getOne(findParams);
		StatusHistory oldHistory = statusHistoryDAO.getLatestEndByEquipId(equipInfo.getId());

		// 更新生产线状态并恢复历史记录
		if (oldHistory != null) {
			equipInfo.setStatus(oldHistory.getStatus());
			oldHistory.setEndTime(null);
			statusHistoryDAO.update(oldHistory);
		} else {
			equipInfo.setStatus(EquipStatus.IDLE);
		}
		equipInfoDAO.update(equipInfo);

		StatusHistory nowHistory = statusHistoryDAO.getByEquipIdAndNullEndTime(equipInfo.getId());
		statusHistoryDAO.delete(nowHistory.getId());
		return equipInfo;
	}

	@Override
	public void insert(EquipInfo equipInfo) throws DataCommitException {
		checkMaintainersExists(equipInfo);
		setNextMaintainDate(equipInfo);
		super.insert(equipInfo);
	}

	@Override
	public void update(EquipInfo equipInfo) throws DataCommitException {
		checkMaintainersExists(equipInfo);
		setNextMaintainDate(equipInfo);
		super.update(equipInfo);
	}

	private void checkMaintainersExists(EquipInfo equipInfo) {
		String maintainerStr = equipInfo.getMaintainer();
		if (StringUtils.isEmpty(maintainerStr)) {
			return;
		}
		String[] maintainers = maintainerStr.split(",");
		for (String maintainer : maintainers) {
			Employee employee = employeeService.getByUserCode(maintainer);
			if (employee == null) {
				throw new MESException("fac.notExistUserCode", maintainer);
			}
		}
	}

	private void setNextMaintainDate(EquipInfo equipInfo) {
		if (!equipInfo.getIsNeedMaintain()) {
			return;
		}

		try {
			setNextMaintainDate(equipInfo, "maintainDate", "nextMaintainDate");
			setNextMaintainDate(equipInfo, "maintainDateFirst", "nextMaintainDateFirst");
			setNextMaintainDate(equipInfo, "maintainDateSecond", "nextMaintainDateSecond");
			setNextMaintainDate(equipInfo, "maintainDateOverhaul", "nextMaintainDateOverhaul");
		} catch (IntrospectionException e) {
			// 不该抛出的异常
			e.printStackTrace();
			throw new MESException();
		} catch (IllegalAccessException e) {
			// 不该抛出的异常
			e.printStackTrace();
			throw new MESException();
		} catch (InvocationTargetException e) {
			// 不该抛出的异常
			e.printStackTrace();
			throw new MESException();
		}
	}

	private void setNextMaintainDate(EquipInfo equipInfo, String maintainDatePropName, String nextMaintainDatePropName)
			throws InvocationTargetException, IllegalAccessException, IntrospectionException {
		PropertyDescriptor maintainDateDescriptor = new PropertyDescriptor(maintainDatePropName, EquipInfo.class);
		PropertyDescriptor nextMaintainDateDescriptor = new PropertyDescriptor(nextMaintainDatePropName,
				EquipInfo.class);

		Integer maintainDate = (Integer) maintainDateDescriptor.getReadMethod().invoke(equipInfo);
		if (maintainDate == null) {
			return;
		}

		Date now = new Date();
		Date nextMaintainDate = (Date) nextMaintainDateDescriptor.getReadMethod().invoke(equipInfo);
		if (nextMaintainDate == null) {
			nextMaintainDate = now;
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nextMaintainDate);
		calendar.set(Calendar.DATE, maintainDate);
		if (calendar.getTime().compareTo(now) < 0) {
			// 到下个月触发
			calendar.add(Calendar.MONTH, 1);
		}
		nextMaintainDateDescriptor.getWriteMethod().invoke(equipInfo, calendar.getTime());
	}

	@Override
	public List<EquipInfo> findForExport(JSONObject queryFilter) {
		EquipInfo findParams = (EquipInfo) JSONUtils.jsonToBean(queryFilter, EquipInfo.class);
		findParams.setOrgCode(SessionUtils.getUser().getOrgCode());
		return equipInfoDAO.find(findParams);
	}

	@Override
	public List<EquipInfo> getByNameOrCode(String name, String orgCode, EquipType type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", name);
		map.put("orgCode", orgCode);
		map.put("type", type);
		return equipInfoDAO.getByNameOrCode(map);
	}

	@Override
	public List<EquipInfo> getByRoleId(EquipInfo equipInfo, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return equipInfoDAO.getByRoleId(equipInfo);
	}

	@Override
	public int countByRoleId(EquipInfo equipInfo) {
		return equipInfoDAO.countByRoleId(equipInfo);
	}

	@Override
	public EquipInfo findForDataUpdate(String code, String type) {
		return equipInfoDAO.findForDataUpdate(code, type);
	}

	@Override
	public List<EquipInfo> findForVF(String[] equipCodes, String orgCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("equipCodes", equipCodes);
		map.put("orgCode", orgCode);
		return equipInfoDAO.findForVF(map);
	}

	@Override
	public List<JSONObject> findEquipByUser(String userCode) {
		List<EquipInfo> list = equipInfoDAO.findEquipByUser(userCode);
		List<JSONObject> result = new ArrayList<JSONObject>();
		for (EquipInfo equipInfo : list) {
			JSONObject object = new JSONObject();
			object.put("EqipName", equipInfo.getName());
			object.put("EqipCode", equipInfo.getCode());
			result.add(object);
		}
		return result;
	}

	@Override
	public List<EquipInfo> getMergeEquipByProcessCode(String processCode, String orgCode) {
		return equipInfoDAO.getMergeEquipByProcessCode(processCode, orgCode);
	}

	/**
	 * 
	 * 获取工段可选设备 : 排生产单(包含工序)
	 * 
	 * @author DingXintao
	 * @param section 工段
	 * @return List<EquipInfo>
	 */
	@Override
	public List<EquipInfo> getEquipByProcessSection(String section) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("section", section);
		param.put("orgCode", this.getOrgCode());
		return equipInfoDAO.getEquipByProcessSection(param);
	}
	
	/**
	 * 
	 * 获取工段可选设备 : 排生产单(不包含工序)
	 * 
	 * @author 宋前克
	 * @param section 工段
	 * @return List<EquipInfo>
	 */
	@Override
	public List<EquipInfo> getEquipByProcessSectionN(String section) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("section", section);
		param.put("orgCode", this.getOrgCode());
		return equipInfoDAO.getEquipByProcessSectionN(param);
	}

	/**
	 * 
	 * 获取工段可选设备 : 生产单切换设备
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单号
	 * 
	 * @return List<Map<String, String>>
	 */
	@Override
	public List<Map<String, String>> getEquipForChangeOrderEquip(String workOrderNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orgCode", this.getOrgCode());
		param.put("workOrderNo", workOrderNo);
		return equipInfoDAO.getEquipForChangeOrderEquip(param);
	}

	@Override
	public List<EquipInfo> getEquipLine(Map<String, Object> param) {
		return equipInfoDAO.getEquipLine(param);
	}

	@Override
	public void insetRecord(EquipRepairRecord r) {
		// TODO Auto-generated method stub
		equipRepairRecordDAO.insert(r);
	}

	@Override
	public void insetSparePart(SparePart s) {
		// TODO Auto-generated method stub
		sparePartDAO.insert(s);
	}

	@Override
	public List<SparePart> querySpareParts(String recordId) {
		// TODO Auto-generated method stub
		return sparePartDAO.getSparePartsByRecordId(recordId);
	}

	@Override
	public void deleteSparePart(String id) {
		// TODO Auto-generated method stub
		sparePartDAO.delete(id);
	}

	@Override
	public List<EquipRepairRecord> getEventInfo(String id) {
		// TODO Auto-generated method stub
		return equipRepairRecordDAO.getEventInfo(id);
	}

	@Override
	public List<EquipInfo> findInfo(EquipInfo param, Integer start, Integer limit,
			List<Sort> parseArray) {
		// TODO Auto-generated method stub
		if (start != null && limit != null) {
            SqlInterceptor.setRowBounds(new RowBounds(start, limit));
        }
		return equipInfoDAO.findInfo(param.getSection());
	}

	@Override
	public int countInfo(EquipInfo param) {
		// TODO Auto-generated method stub
		return equipInfoDAO.countInfo(param.getSection());
	}

	@Override
	public int getRecordsByEventInfoId(String eventInfoId) {
		// TODO Auto-generated method stub
		return equipRepairRecordDAO.getByEventInfoId(eventInfoId);
	}

	@Override
	public List<EquipInfo> getSpecificEquip(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return equipInfoDAO.getSpecificEquip(params);
	}

	@Override
	public List<Map<String,String>> getByWorkOrder(Map<String, Object> param1) {
		// TODO Auto-generated method stub
		return equipInfoDAO.getByWorkOrder(param1);
	}

	@Override
	public String getEquipParamByCode(String code) {
		// TODO Auto-generated method stub
		return equipInfoDAO.getEquipParamByCode(code);
	}

	@Override
	public List<Map<String, String>> getEquipParams() {
		// TODO Auto-generated method stub
		return equipInfoDAO.getEquipParams();
	}

	/*@Override
	public List<EquipEnergyMonitor1> getEquipEnergyMonitor(Map<String, Object> findParams) {
		return equipInfoDAO.getEquipEnergyMonitor(findParams);
	}*/

//	@Override
//	public int countEquipEnergyMonitor(String name) {
//		return equipInfoDAO.countEquipEnergyMonitor(name);
//	}

	/*@Override
	public Map<String, Object> energyReceiptChart(String equipName) {
		Map<String, Object> result = new HashMap<String, Object>();
		EquipEnergyMonitor findparams = new EquipEnergyMonitor();
		findparams.setEquipName(equipName);
		List<EquipEnergyMonitor> list = equipInfoDAO.findEnergyHistory(findparams);
		if (CollectionUtils.isEmpty(list)) {
			return result;
		}
		List<Object[]> historyData = new ArrayList<Object[]>();
		for (EquipEnergyMonitor paramHis : list) {
			double powat = Double.parseDouble(StringUtils.isNotEmpty(paramHis.getPow_at())?paramHis.getPow_at():"0");
			Object[] array = new Object[2];
			array[1] = powat;
			array[0] = paramHis.getCreateTime();
			historyData.add(array);
		}
			result.put("realdata",historyData);
		return result;
		}
*/
}
