package cc.oit.bsmes.bas.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.dao.EquipCalendarDAO;
import cc.oit.bsmes.bas.dao.MonthCalendarDAO;
import cc.oit.bsmes.bas.dao.WeekCalendarDAO;
import cc.oit.bsmes.bas.dao.WeekCalendarShiftDAO;
import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.bas.model.MonthCalendar;
import cc.oit.bsmes.bas.model.WeekCalendar;
import cc.oit.bsmes.bas.model.WeekCalendarShift;
import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.fac.model.EquipInfo;

/**
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-3-11 下午2:06:24
 */
@Service
public class EquipCalendarServiceImpl extends BaseServiceImpl<EquipCalendar> implements EquipCalendarService {
	@Resource
	private EquipCalendarDAO equipCalendarDAO;
	@Resource
	private MonthCalendarDAO monthCalendarDAO;
	@Resource
	private WeekCalendarDAO weekCalendarDAO;
	@Resource
	private WeekCalendarShiftDAO weekCalendarShiftDAO;

	/**
	 * 获取设备工作日历
	 * 
	 * @author DingXintao
	 * @date 2015-09-07
	 * @param equipCode 设备编码
	 * @param startLoadTime 查询设备日历的开始加载时间，默认当前时间
	 * @param endLoadTime 查询设备日历的结束加载时间，默认当前时间延后15天
	 * @return
	 * @see
	 */
	@Override
	public List<EquipCalendar> getEquipCalendarByCode(String equipCode, Date startLoadTime, Date endLoadTime) {
		if (null == startLoadTime) {
			startLoadTime = new Date();
		}
		if (null == endLoadTime) {
			endLoadTime = new Date(new Date().getTime() + BusinessConstants.ECALENDAR_DAY_NUM * 24 * 60 * 60 * 1000); // 延后15天
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", DateUtils.getDay(startLoadTime));
		params.put("end", DateUtils.getDay(endLoadTime));
		params.put("equipCode", equipCode);
		return equipCalendarDAO.getEquipCalendarByCode(params);
	}

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @param equipInfo
	 * @param startLoadTime
	 * @param endLoadTime @return
	 * @author leiwei
	 * @date 2014-3-11 下午2:06:32
	 * @see cc.oit.bsmes.bas.service.EquipCalendarService#getByEquipInfo(cc.oit.bsmes.fac.model.EquipInfo,
	 *      Date, Date)
	 */
	@Override
	public List<EquipCalendar> getByEquipInfo(EquipInfo equipInfo, Date startLoadTime, Date endLoadTime) {
		List<EquipCalendar> equipCal = new ArrayList<EquipCalendar>();
		if (StringUtils.isNotBlank(equipInfo.getCode())) {
			Calendar cal = Calendar.getInstance();
			Date startTime = new Date();
			Date endTime = null;
			if (startLoadTime != null) {
				startTime = startLoadTime;
			}
			if (endLoadTime != null) {
				endTime = endLoadTime;
			} else {
				if (startLoadTime != null) {
					endTime = new Date();
				} else {
					cal.setTime(startTime);
					cal.add(Calendar.MONTH, BusinessConstants.MAX_CALENDER_QUERY);
					endTime = cal.getTime();
				}
			}
			long totalDays = DateUtils.getTimeDiff(startTime, endTime);
			if (totalDays < 0) {
				return null;
			}
			Date temp = startTime;
			// 月工作日历
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("start", DateUtils.getDay(startTime).substring(0, 6));
			map.put("end", DateUtils.getDay(endTime).substring(0, 6));
			List<MonthCalendar> monthCalendar = monthCalendarDAO.getByStartAndEnd(map);
			// 设备工作日历
			map = new HashMap<String, Object>();
			map.put("start", DateUtils.getDay(startTime));
			map.put("end", DateUtils.getDay(endTime));
			map.put("equipCode", equipInfo.getCode());
			List<EquipCalendar> equipCalendars = equipCalendarDAO.getByLimitTime(map);
			// 工厂周日历
			List<WeekCalendar> weekCalendars = weekCalendarDAO.getByOrgCode(equipInfo.getOrgCode());
			for (int j = 0; j < totalDays; j++) {
				if (j > 0) {
					temp = DateUtils.addDayToDate(temp, 1);
				}
				String workDay = DateUtils.getDay(temp); // 20131203
				// 从设备日历上取设备工作日历,如果没取到数据 则去工厂日历中取
				EquipCalendar equipCalendar = getEquipCalendar(equipCalendars, workDay);
				if (equipCalendar == null) {
					// 获取月工作日历,判断该天是否为工作日
					if (getMonthCalendar(monthCalendar, workDay)) {
						EquipCalendar eqcl = new EquipCalendar();
						eqcl.setDateOfWork(workDay);
						eqcl.setEquipCode(equipInfo.getCode());
						eqcl.setOrgCode(equipInfo.getOrgCode());
						cal.setTime(temp);
						int week = getWeek(cal.get(Calendar.DAY_OF_WEEK));
						WeekCalendar weekCalendar = getWeekCalendar(weekCalendars, week);
						if (weekCalendar != null) {
							// 获取工作日班次和班次信息
							List<WeekCalendarShift> weekShift = weekCalendarShiftDAO.getByWeekCalendarId(weekCalendar.getId());
							List<EquipCalShift> equipCals = new ArrayList<EquipCalShift>();
							for (WeekCalendarShift we : weekShift) {
								EquipCalShift equipCalShift = new EquipCalShift();
								equipCalShift.setShiftName(we.getShiftName());
								equipCalShift.setShiftStartTime(we.getShiftStartTime());
								equipCalShift.setShiftEndTime(we.getShiftEndTime());
								equipCals.add(equipCalShift);
							}
							eqcl.setEquipCalShift(equipCals);
							equipCal.add(eqcl);
						}
					}
				} else {
					equipCal.add(equipCalendar);
				}

			}
		}
		return equipCal;
	}

	/**
	 * 
	 * <p>
	 * 利用反射获取月工作日历对象
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-3-21 下午5:14:59
	 * @param date
	 * @return
	 * @see
	 */
	public static MonthCalendar getMonthCalendar(String date) {
		String month = date.substring(0, 6);
		String day = ("0".equals(date.substring(6, 7))) ? "DAY" + date.substring(7) : "DAY" + date.substring(6);
		MonthCalendar findParams = null;
		try {
			Class classType = MonthCalendar.class;
			Method[] methodArray = classType.getDeclaredMethods();
			findParams = (MonthCalendar) classType.newInstance();
			for (Method method : methodArray) {
				if (StringUtils.contains(method.getName(), "setDay")) {
					if (StringUtils.equalsIgnoreCase(method.getName().substring(3), day)) {
						method.invoke(findParams, "1");
					}
				}
				if (StringUtils.containsIgnoreCase(method.getName(), "setWorkMonth")) {
					method.invoke(findParams, month);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return findParams;
	}

	@Override
	public Date getNextDays(Date start, int days, String orgCode) {
		Date temp = start;
		Calendar cal = Calendar.getInstance();
		// 工厂周日历
		List<WeekCalendar> weekCalendars = weekCalendarDAO.getByOrgCode(orgCode);
		while (days > 0) {
			String startDate = DateUtils.getDay(temp);
			List<MonthCalendar> monthCalendar = monthCalendarDAO.find(getMonthCalendar(startDate));
			if (monthCalendar.size() > 0) {
				cal.setTime(temp);
				int week = getWeek(cal.get(Calendar.DAY_OF_WEEK));
				WeekCalendar weekCalendar = getWeekCalendar(weekCalendars, week);
				if (weekCalendar != null) {
					days--;
				}
			}
			temp = DateUtils.addDayToDate(temp, 1);
		}
		return temp;
	}

	/**
	 * <p>
	 * x星期之间的转换，星期一为1，星期天为7
	 * </p>
	 * 
	 * @param weekNo
	 * @return
	 * @author leiwei
	 * @date 2014-3-7 下午5:37:37
	 * @see
	 */
	public static int getWeek(int weekNo) {
		switch (weekNo) {
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		case 5:
			return 4;
		case 6:
			return 5;
		case 7:
			return 6;
		default:
			return 7;
		}
	}

	@Override
	public EquipCalendar getByEquipCodeAndDateOfWork(EquipCalendar cal) {
		return equipCalendarDAO.getByEquipAndDateOfWork(cal);
	}

	private WeekCalendar getWeekCalendar(List<WeekCalendar> weekCalendar, int workNo) {
		if (weekCalendar.size() > 0) {
			WeekCalendar weekCal = null;
			for (WeekCalendar week : weekCalendar) {
				if (week.getWeekNo() == workNo && week.isIsworkday()) {
					weekCal = week;
					break;
				}
			}
			return weekCal;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * <p>
	 * 设备日历
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-5-5 下午4:49:39
	 * @param equipCalendar
	 * @param workDay
	 * @return
	 * @see
	 */
	private EquipCalendar getEquipCalendar(List<EquipCalendar> equipCalendar, String workDay) {
		if (equipCalendar.size() > 0) {
			EquipCalendar equipCal = null;
			for (EquipCalendar eql : equipCalendar) {
				if (StringUtils.equals(eql.getDateOfWork(), workDay)) {
					equipCal = eql;
					break;
				}
			}
			return equipCal;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * <p>
	 * 判断该天是否为工作日
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-5-5 下午3:42:14
	 * @param monthCalendar
	 * @param workDay
	 * @return
	 * @see
	 */
	private boolean getMonthCalendar(List<MonthCalendar> monthCalendar, String workDay) {
		String month = workDay.substring(0, 6);
		boolean res = false;
		for (MonthCalendar mon : monthCalendar) {
			if (StringUtils.equals(month, mon.getWorkMonth())) {
				String field = ("0".equals(workDay.substring(6, 7))) ? "Day" + workDay.substring(7) : "Day" + workDay.substring(6);
				String result = invokeMethod(mon, field).toString();
				if (StringUtils.equals(result, "1")) {
					res = true;
					break;
				}
			}
		}
		return res;
	}

	private Object invokeMethod(MonthCalendar monthCal, String methodName) {
		Class<? extends Object> monthClass = monthCal.getClass();
		Method method = null;
		try {
			method = monthClass.getMethod("get" + methodName);
			return method.invoke(monthCal);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
