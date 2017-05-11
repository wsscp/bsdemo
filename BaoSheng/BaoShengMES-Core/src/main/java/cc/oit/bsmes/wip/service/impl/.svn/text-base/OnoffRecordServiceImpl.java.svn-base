package cc.oit.bsmes.wip.service.impl;

import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.model.WorkShift;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.bas.service.MesClientService;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.bas.service.WorkShiftService;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.constants.UserOnOffType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.wip.dao.OnoffRecordDAO;
import cc.oit.bsmes.wip.model.OnoffRecord;
import cc.oit.bsmes.wip.service.OnoffRecordService;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OnoffRecordServiceImpl extends BaseServiceImpl<OnoffRecord> implements OnoffRecordService {

	@Resource
	private OnoffRecordDAO onoffRecordDAO;
	@Resource
	private UserService userService;
	@Resource
	private EmployeeService employeeService;
	@Resource
	private WorkShiftService workShiftService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private MesClientService mesClientService;

	@Override
	public List<OnoffRecord> getHavePermissionRecord(String clientMac, String equipCode) {
		return onoffRecordDAO.getHavePermissionRecord(clientMac, equipCode);
	}

	@Override
	public OnoffRecord findbyUserCodeAndMac(String userCode, String mac) {
		return onoffRecordDAO.findbyUserCodeAndMac(userCode, mac);
	}

	@Override
	public String assOnWorkEmployeeNames(String mac) {
		List<OnoffRecord> workUsers = onoffRecordDAO.findByMesClientMac(mac);
		StringBuffer buffer = new StringBuffer();
		for (OnoffRecord record : workUsers) {
			buffer.append(record.getUserName() + ",");
		}
		if (buffer.length() > 0) {
			return buffer.substring(0, buffer.length() - 1);
		} else {
			return "";
		}
	}

	@Override
	public String employeeCreditCard(String mac, OnoffRecord newRecord, User user,Map map) {
		OnoffRecord oldRecord = findbyUserCodeAndMac(newRecord.getUserCode(), mac);
		if (oldRecord != null && !oldRecord.getExceptionType().equals(newRecord.getExceptionType())) {
			oldRecord.setExceptionType(newRecord.getExceptionType());
			oldRecord.setOffTime(new Date());
			map.put("OnTime",oldRecord.getOnTime());
			map.put("OffTime", oldRecord.getOffTime());
			map.put("equipeCodes", oldRecord.getEquipCodes());
			map.put("userCode", oldRecord.getUserCode());
			map.put("exceptionType", oldRecord.getExceptionType());
			onoffRecordDAO.update(oldRecord);
			return assOnWorkEmployeeNames(mac);
		} else {
			if (!"0".equals(user.getStatus())) {
				newRecord.setOnTime(new Date());
				newRecord.setClientName(mac);
				newRecord.setUserName(user.getName());
				newRecord.setExceptionType(UserOnOffType.ON_WORK.name());
				onoffRecordDAO.insert(newRecord);
			}
			return assOnWorkEmployeeNames(mac);
		}
	}

	@Override
	public ModelAndView loadUserCreditCardTypeAndWorkShift(String mac, String userCode) {
		ModelAndView modelAndView = new ModelAndView(new FastJsonJsonView());
		User user = userService.checkUserCodeUnique(userCode);
		if (user == null || StringUtils.equals(user.getStatus(), WebConstants.NO)) {
			modelAndView.addObject("success", false);
		} else {
			// 验证用户是否有权限S
			int roleEquips = mesClientService.checkUserLoginMesClient(userCode, mac);
			if (roleEquips == 0) {
				modelAndView.addObject("success", false);
				modelAndView.addObject("msg", "您没有当前终端设备的权限!");
			} else {
				OnoffRecord record = findbyUserCodeAndMac(userCode, mac);
				if (record != null) {
					WorkShift workShift = workShiftService.getById(record.getShiftId());
					updateShiftTime(workShift);
					modelAndView.addObject("workShift", workShift);
				}
				modelAndView.addObject("recordExists", record != null);
				
				modelAndView.addObject("creditCardTypes",
						StaticDataCache.getByTermsCode(TermsCodeType.DATA_WORK_ONOFF_TYPE.name()));
				modelAndView.addObject("success", true);
			}
		}
		return modelAndView;
	}

	private static void updateShiftTime(WorkShift ws) {
		String shiftStartTime = ws.getShiftStartTime();
		if (StringUtils.isNotBlank(shiftStartTime)) {
			ws.setShiftStartTime(shiftStartTime.substring(0, 2) + ":" + shiftStartTime.substring(2, 4));
		}
		String shiftEndTime = ws.getShiftEndTime();
		if (StringUtils.isNotBlank(shiftEndTime)) {
			ws.setShiftEndTime(shiftEndTime.substring(0, 2) + ":" + shiftEndTime.substring(2, 4));
		}
	}

	/**
	 * <p>
	 * 检验上班时间，若上班时间小于日历工作时间 则 return false 大于或等于 工作日历时间则return true
	 * </p>
	 * 
	 * @param onTime
	 * @param workShift
	 * @return
	 * @author JinHanyun
	 * @date 2014-3-10 上午11:26:52
	 * @see
	 */
	private static Date getOffTime(Date onTime, WorkShift workShift) {
		String shiftStartTime = workShift.getShiftStartTime();
		String shiftEndTime = workShift.getShiftEndTime();
		int startHour = Integer.parseInt(shiftStartTime.substring(0, 2));
		int endHour = Integer.parseInt(shiftEndTime.substring(0, 2));

		int startMinute = Integer.parseInt(shiftStartTime.substring(2, 4));
		int endMinute = Integer.parseInt(shiftEndTime.substring(2, 4));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(onTime);

		int workMinute = endMinute - startMinute;
		int workHour = endHour - startHour;
		if (workMinute < 0) {
			workMinute = 60 + workMinute;
			workHour = workHour - 1;
		}

		calendar.add(Calendar.HOUR_OF_DAY, endHour - startHour);
		calendar.add(Calendar.MINUTE, endMinute - startMinute);
		return calendar.getTime();
	}

	@Override
	public int validUserPermission(String userCode, String mac, String equipCode) {
		return onoffRecordDAO.validUserPermission(userCode, mac, equipCode);
	}

	@Override
	public JSONObject getOnWorkUserDetailByEquipCode(String emplCode, String orgCode) {
		List<OnoffRecord> list = onoffRecordDAO.getOnWorkUserByUserCode(emplCode);
		JSONObject result = new JSONObject();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (!CollectionUtils.isEmpty(list)) {
			OnoffRecord record = list.get(0);
			result.put("Code", record.getUserCode());
			result.put("Certificate", record.getCertificate());
			result.put("BeginWorkTime", format.format(record.getOnTime()));
			// WorkShift workShift =
			// workShiftService.getById(String.valueOf(record.getShiftId()));
			// subResult.put("EndWorkTime",format.format(workShift.getShiftEndTime()));
			result.put("EndWorkTime", "");
			result.put("Equipment", equipInfoService.findEquipByUser(String.valueOf(record.getUserCode())));
		}
		return result;
	}

	@Override
	public List<OnoffRecord> getOnWorkUserRecord(String equipCode, String orgCode) {
		return onoffRecordDAO.getOnWorkUserRecord(equipCode, orgCode);
	}

	/**
	 * @Title:       getUsers
	 * @Description: TODO(终端获取在线工人信息)
	 * @param:       request 请求
	 * @param:       response 响应
	 * @param:       role 角色:挡班/副挡板/辅助工
	 * @return:      List<OnoffRecord>   
	 * @throws
	 */
	@Override
	public List<OnoffRecord> getByMesClientMacAndRole(String mac, String role) {
		// TODO Auto-generated method stub
		List<OnoffRecord> list = onoffRecordDAO.findByMesClientMac(mac);
		if (StringUtils.isNotEmpty(role)) {
			List<OnoffRecord> onoffRecord = new ArrayList<OnoffRecord>();
			for (OnoffRecord o : list) {
				if (role.equals(o.getCertificate())) {
					onoffRecord.add(o);
				}
			}
			return onoffRecord;
		}
		return list;
	}

	@Override
	public List<OnoffRecord> getByMesClientUsers(String mac) {
		List<OnoffRecord> list = onoffRecordDAO.findByMesClientMac(mac);
		return list;
	}

	@Override
	public int checkIFCreditCard(String userCode, String equipCode) {
		return onoffRecordDAO.checkIFCreditCard(userCode, equipCode);
	}
	
	@Override
	public String checkIfDB(String userCode){
		return onoffRecordDAO.checkIfDB(userCode);
	}
	
	@Override
	public String checkIfUsed(String equipCode){
		return onoffRecordDAO.checkIfUsed(equipCode);
	}
}
