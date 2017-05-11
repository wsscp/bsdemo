package cc.oit.bsmes.interfaceVF.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.bas.service.RoleService;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.constants.CustomerOrderStatus;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.MD5Utils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.eve.service.MaintainCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.EquipMaintainState;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.EquipMaintainStateService;
import cc.oit.bsmes.fac.service.StatusHistoryService;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.wip.model.OnoffRecord;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.OnoffRecordService;
import cc.oit.bsmes.wip.service.ReceiptService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by jijy on 2014/10/26.
 */
@Controller
@RequestMapping("/interfaceVF")
public class VirtualFactoryController {

    @Resource
    private EquipInfoService equipInfoService;
    @Resource
    private EquipMaintainStateService equipMaintainStateService;
    @Resource
    private EventInformationService eventInformationService;
    @Resource
    private StatusHistoryService statusHistoryService;
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private WorkOrderService workOrderService;
    @Resource
    private ReceiptService receiptService;
    @Resource
    private DataAcquisitionService dataAcquisitionService;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private OnoffRecordService onoffRecordService;
    @Resource
    private OrderTaskService orderTaskService;


    //success
    @ResponseBody
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public JSONObject login(@RequestParam String loginId, @RequestParam String password) {
        User user = userService.checkUserCodeUnique(loginId);
        int loginStatus = Constants.LOGIN_SUCCESS;
        if (user == null)
            loginStatus = Constants.LOGIN_USER_NOT_EXISTS;
        if (user.getStatus().equals(WebConstants.NO))
            loginStatus = Constants.LOGIN_USER_NOT_EXISTS;
        if (!user.getPassword().equals(MD5Utils.string2MD5(password)))
            loginStatus = Constants.LOGIN_PASSWORD_WRONG;
        List<Role> roles = roleService.getByUserCode(loginId);
        String[] roleNames = new String[roles.size()];
        for (int i = 0; i < roles.size(); i++) {
            roleNames[i] = roles.get(i).getName();
        }

        JSONObject result = new JSONObject();
        result.put("LoginState", loginStatus);
        result.put("Roles", roleNames);
        result.put("Supplementary", user.getSupplementary());
        result.put("orgCode", user.getOrgCode());
        return result;
    }

    //success
    @ResponseBody
    @RequestMapping(value = "saveSupplementary", method = RequestMethod.POST)
    public int saveSupplementary(@RequestParam String loginId, @RequestParam String supplementary) {
        return userService.updateByUserCode(loginId, supplementary) > 0 ? Constants.YES : Constants.NO;
    }

    //success
    @ResponseBody
    @RequestMapping(value = "getEquipmentState", method = RequestMethod.POST)
    public JSONObject getEquipmentState(@RequestParam String eqipCode, @RequestParam String orgCode) {
        EquipInfo equipInfo = equipInfoService.getByCode(eqipCode, orgCode);
        JSONObject result = new JSONObject();
        result.put("EqipName", equipInfo == null ? "" : equipInfo.getName());
        result.put("StateList", new ArrayList<Receipt>());
        WorkOrder workOrder = workOrderService.getCurrentByEquipCode(eqipCode);
        if (workOrder == null)
            return result;

        EquipInfo mainEquip = StaticDataCache.getMainEquipInfo(eqipCode);
        if (mainEquip == null)
            return result;

        List<Receipt> receipts = receiptService.getByEquipCode(mainEquip.getCode(), workOrder.getProcessId(), orgCode);
        if (receipts.size() == 0)
            return result;

        //得到实时采集数据
        List<Receipt> queryLiveList = dataAcquisitionService.queryLiveReceiptByCodes(receipts);
        JSONArray array = new JSONArray();
        for(Receipt receipt:receipts){
            JSONObject subResult = new JSONObject();
            subResult.put("StateName",receipt.getReceiptName());
            subResult.put("StateValue","");
            for(Receipt oReceipt:queryLiveList){
                if(receipt.getReceiptCode().equals(oReceipt.getReceiptCode())){
                    subResult.put("StateValue",oReceipt.getDaValue());
                }
            }
            subResult.put("StateUnit", StringUtils.isBlank(receipt.getDataUnit())?"":receipt.getDataUnit());
            array.add(subResult);
        }
        result.put("StateList", array);
        return result;
    }

    //success
    @ResponseBody
    @RequestMapping(value = "getEquipmentWorkState", method = RequestMethod.POST)
    public JSONArray getEquipmentWorkState(@RequestParam String eqipCodes, @RequestParam String orgCode) {
        JSONArray array = new JSONArray();
        if(StringUtils.isBlank(eqipCodes)){
            return array;
        }
        String[] equipCodeArray =  eqipCodes.split("\\|");
        List<EquipInfo> equipInfos = equipInfoService.findForVF(equipCodeArray, orgCode);
        for(EquipInfo equipInfo:equipInfos){
            JSONObject result = new JSONObject();
            result.put("Code",equipInfo.getCode());
            result.put("State",equipInfo.getStatus());
            result.put("Name", equipInfo.getName());
            array.add(result);
        }
        return array;
    }

    //success
    @ResponseBody
    @RequestMapping(value = "getEquipmentWorkStateDetail", method = RequestMethod.POST)
    public JSONObject getEquipmentWorkStateDetail(@RequestParam String eqipCode, @RequestParam String orgCode) {
        JSONObject jsonObject = new JSONObject();
        EventInformation information = new EventInformation();
        information.setEquipCode(eqipCode);
        List<EventInformation> eventInformations = eventInformationService.getEventInfoList(information);
        if (eventInformations != null && eventInformations.size() != 0) {
            EventInformation eventInformation = eventInformations.get(0);
            jsonObject.put("ProcessingState", eventInformation.getEventStatus().toString());
            jsonObject.put("ProcessingEmployee", eventInformation.getEventProcessor());
            jsonObject.put("Reason", eventInformation.getEventReason());
        }

        List<EquipInfo> equipInfos = equipInfoService.getByNameOrCode(eqipCode, orgCode, EquipType.PRODUCT_LINE);
        if (equipInfos != null && equipInfos.size() > 0) {
            jsonObject.put("EqipName", equipInfos.get(0).getName());
        }
        return jsonObject;
    }

    @ResponseBody
    @RequestMapping(value = "getEquipmentMaintenanceState", method = RequestMethod.POST)
    public JSONArray getEquipmentMaintenanceState(@RequestParam String eqipCodes, @RequestParam String orgCode,@RequestParam String userCode) {

    	UserService userService = (UserService) ContextUtils.getBean(UserService.class);
    	User user = userService.checkUserCodeUnique(userCode);
    	 if (user != null) {
            user.setOrgCode(orgCode);
            SessionUtils.setUser(user);
         }    	
        Map<String, EquipMaintainState> equipMaintainStates = MaintainCache.getEquipMaintainStates(orgCode);

        JSONArray jsonArray = new JSONArray();
        if(StringUtils.isBlank(eqipCodes)){
            return jsonArray;
        }
        for (String eqipCode : eqipCodes.split("\\|")) {
            if(StringUtils.isBlank(eqipCode)){
                continue;
            }
            if(equipMaintainStates==null)
            {
            	return jsonArray;
            }
            EquipInfo equip = StaticDataCache.getMainEquipInfo(eqipCode);
            EquipMaintainState uncompletedMaintainSate = equipMaintainStates.get(equip.getCode());
            
            if (uncompletedMaintainSate == null) {
                continue;
            }

            JSONObject object = new JSONObject();
            object.put("Code", eqipCode);
            object.put("Name", uncompletedMaintainSate.getEquipName());
            object.put("State", getMaintainState(uncompletedMaintainSate));
            jsonArray.add(object);
        }

        return jsonArray;
    }

    @ResponseBody
    @RequestMapping(value = "getEquipmentMaintenanceStateDetail", method = RequestMethod.POST)
    public JSONObject getEquipmentMaintenanceStateDetail(@RequestParam(required=false) String eqipCode, @RequestParam String orgCode,@RequestParam String userCode) {
       
    	JSONObject object = new JSONObject();
    	if(eqipCode==null)
    	{
    		return object;
    	}
        UserService userService = (UserService) ContextUtils.getBean(UserService.class);
    	User user = userService.checkUserCodeUnique(userCode);
    	 if (user != null) {
            user.setOrgCode(orgCode);
            SessionUtils.setUser(user);
         }    
        Map<String, EquipMaintainState> equipMaintainStates = MaintainCache.getEquipMaintainStates(orgCode);
        EquipInfo equip = StaticDataCache.getMainEquipInfo(eqipCode);        
        EquipMaintainState uncompletedMaintainSate = equipMaintainStates.get(equip.getCode());
        if (uncompletedMaintainSate == null) {
            return object;
        }
        MaintainState maintainState = getMaintainState(uncompletedMaintainSate);
        object.put("EqipName", uncompletedMaintainSate.getEquipName());
        object.put("MatcType", uncompletedMaintainSate.getMaintainType());
        
        object.put("State", maintainState);
        if (maintainState == null) {
            return object;
        }

        Date now = new Date();
       
		switch (maintainState) {
            case COMING:
                Date startDate = uncompletedMaintainSate.getStartDate();
                if (startDate != null) {
                    object.put("PlanTime", DateUtils.convert(startDate, DateUtils.DATE_FORMAT));
                } else {
                    Date lastMaintainDate = uncompletedMaintainSate.getLastMaintainDate();
                    Integer triggerCycle = uncompletedMaintainSate.getTriggerCycle();
                    if(lastMaintainDate==null||triggerCycle==null)
                    {
                    	break;
                    }
                    int equipTotalWorkHour = (int) statusHistoryService.getEquipTotalWorkHour(eqipCode, lastMaintainDate, now);
                  int remainTime = triggerCycle - equipTotalWorkHour;

                    object.put("PlanTime", remainTime + "小时");
                }
                break;
            case IN_PROGRESS:
                String eventInfoId = uncompletedMaintainSate.getEventInfoId();
                EventInformation param =new EventInformation();
                param.setId(eventInfoId);
               List<EventInformation> alist = eventInformationService.getInfo(param,0,1,null);
                
                if(CollectionUtils.isNotEmpty(alist))
                {
                	param=alist.get(0);
                }                
                Date beginTime = param.getResponseTime();
                Double timeNeeded = uncompletedMaintainSate.getTimeNeeded();
                Date endTime=null;
                if(beginTime!=null){
                	if(timeNeeded==null)
                	{
                		timeNeeded=new Double(1);
                	}
                    endTime = new Date(beginTime.getTime() + (long) (timeNeeded * 60 * 60 * 1000));
                }
            
                object.put("BeginTime", DateUtils.convert(beginTime, DateUtils.DATE_FORMAT));
                object.put("EndTime",  DateUtils.convert(endTime, DateUtils.DATE_FORMAT) );
                object.put("Employee", uncompletedMaintainSate.getMaintainer());
                break;
            case ABNORMAL:
                eventInfoId = uncompletedMaintainSate.getEventInfoId();
                EventInformation eventInformation = eventInformationService.getById(eventInfoId);
                Date createTime = eventInformation.getCreateTime();
                long delayTime = now.getTime() - createTime.getTime();
                int delayDay = (int) (delayTime / (24 * 60 * 60 * 1000));

                object.put("OutTime", delayDay);
                break;
        }

        return object;
    }

    @ResponseBody
    @RequestMapping(value = "getEquipmentEmployee", method = RequestMethod.POST)
    public JSONArray getEquipmentEmployee(@RequestParam String eqipCodes, @RequestParam String orgCode) {
        if(StringUtils.isBlank(eqipCodes)){
            return new JSONArray();
        }
        return employeeService.getOnWorkUserByEquip(eqipCodes.split("\\|"), orgCode);
    }

    @ResponseBody
    @RequestMapping(value = "getEmployeeDetail", method = RequestMethod.POST)
    public JSONObject getEmployeeDetail(@RequestParam String emplCode, @RequestParam String orgCode) {
        return onoffRecordService.getOnWorkUserDetailByEquipCode(emplCode, orgCode);
    }

    /**
     * success
     * @param eqipCodes
     * @param orgCode
     * @return
     */
    // 返回SALEITEMID
    @ResponseBody
    @RequestMapping(value = "getEquipmentOrders", method = RequestMethod.POST)
    public JSONArray getEquipmentOrders(@RequestParam String eqipCodes, @RequestParam String orgCode) {
        JSONArray jsonArray = new JSONArray();
        if(StringUtils.isBlank(eqipCodes)){
            return jsonArray;
        }

        Map<String, Map<String, String>> ordersTodayByEquipcodes = orderTaskService.getOrdersTodayByEquipcodes(eqipCodes.split("\\|"), orgCode);
        List<String> uncompletedOrderLateEvents = eventInformationService.getUncompletedOrderLateEvents(eqipCodes.split("\\|"), orgCode);
        for (String saleItemId : uncompletedOrderLateEvents) {
            Map<String, String> order = ordersTodayByEquipcodes.get(saleItemId);
            if (order != null && order.get("STATUS").equals(CustomerOrderStatus.IN_PROGRESS)) {
                order.put("STATUS", "LATE");
            }
        }
        for (Map<String, String> orders : ordersTodayByEquipcodes.values()) {
            JSONObject object = new JSONObject();
            object.put("OrderCode", orders.get("CONTRACT_NO"));
            object.put("ProductCode", orders.get("PRODUCT_CODE"));
            Object progress = orders.get("PROGRESS");
            Double progressd= Double.parseDouble(progress.toString())>1?1d:Double.parseDouble(progress.toString());
            object.put("Progress", WebConstants.INT_DF.format(progressd*100));

            String status = orders.get("STATUS");
            OrderState state = OrderState.NORMAL;
            if (status.equals("LATE")) {
                state = OrderState.ABNORMAL;
            } else if (status.equals("TO_DO")) {
                state = OrderState.NOTHING;
            }
            if (Double.parseDouble(progress.toString())==1) {
                state = OrderState.COMPLETED;
            }
            object.put("State", state);
            jsonArray.add(object);
        }

        return jsonArray;
    }

    /**
     * 计划开始时间没有
     * @param eqipCode
     * @param orgCode
     * @return
     */
    // 返回prodec
    @ResponseBody
    @RequestMapping(value = "getEquipmentTask", method = RequestMethod.POST)
    public JSONObject getEquipmentTask(@RequestParam String eqipCode, @RequestParam String orgCode) {
        JSONObject object = new JSONObject();
        object.put("EqipCode", eqipCode);
        OrderState state = OrderState.NORMAL;
        int uncompletedOrderLateEvents = eventInformationService.countUncompletedByType(EventTypeContent.WT, eqipCode, orgCode);
        if (uncompletedOrderLateEvents > 0) {
            state = OrderState.ABNORMAL;
        }
        object.put("State", state);
        object.put("OrderNum", workOrderService.countToday(eqipCode, orgCode));
        List<OnoffRecord> onWorkUserRecord = onoffRecordService.getOnWorkUserRecord(eqipCode, orgCode);
        StringBuilder employees = new StringBuilder();
        for (OnoffRecord onoffRecord : onWorkUserRecord) {
            employees.append(onoffRecord.getUserName());
            employees.append(" ");
        }
        object.put("Employee", employees);

        //TODO 计划开始时间没有值
        OrderTask currentOrder = orderTaskService.getCurrentOrder(eqipCode, orgCode);
        if (currentOrder == null) {
            return object;
        }
        Double progress = currentOrder.getProgress()>1?1: currentOrder.getProgress();        
        object.put("Progress", WebConstants.INT_DF.format(progress*100));
        object.put("OrderCode", currentOrder.getWorkOrderNo()); 
        object.put("BeginTime",  DateUtils.convert(currentOrder.getPlanStartDate(), DateUtils.DATE_FORMAT) );

       
        return object;
    }

    // 返回prodec
    @ResponseBody
    @RequestMapping(value = "getEquipmentTasks", method = RequestMethod.POST)
    public JSONArray getEquipmentTasks(@RequestParam String eqipCodes, @RequestParam String orgCode) {
        JSONArray jsonArray = new JSONArray();

        String[] equipCodeArray = eqipCodes.split("\\|");
        for (String eqipCode : equipCodeArray) {
            if(StringUtils.isBlank(eqipCode)){
                continue;
            }
            jsonArray.add(getEquipmentTask(eqipCode, orgCode));
        }

        return jsonArray;
    }

    private MaintainState getMaintainState(EquipMaintainState equipMaintainState) {
        if (equipMaintainState.getEventInfoId() == null) {
            return MaintainState.COMING;
        }
        EventInformation eventInformation = eventInformationService.getById(equipMaintainState.getEventInfoId());
        if (eventInformation.getEventStatus() == EventStatus.COMPLETED) {
            equipMaintainState.setCompleted(true);
            equipMaintainStateService.update(equipMaintainState);
            return null;
        }
        if ( new Date().getTime() - eventInformation.getCreateTime().getTime()  > 24 * 60 * 60 * 1000) {
            return MaintainState.ABNORMAL;
        } else {
            return MaintainState.IN_PROGRESS;
        }
    }

    private static enum MaintainState {
        ABNORMAL, COMING, IN_PROGRESS;
    }

    private static enum OrderState {
        ABNORMAL, NORMAL, NOTHING, COMPLETED;
    }

    private static class Constants {
        private static final int LOGIN_SUCCESS = 0;
        private static final int LOGIN_PASSWORD_WRONG = 1;
        private static final int LOGIN_USER_NOT_EXISTS = 2;
        private static final int YES = 0;
        private static final int NO = 1;
    }
  

}
