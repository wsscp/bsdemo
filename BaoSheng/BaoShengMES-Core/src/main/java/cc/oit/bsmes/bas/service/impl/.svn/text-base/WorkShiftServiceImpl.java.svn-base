package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.MonthCalendarDAO;
import cc.oit.bsmes.bas.dao.WeekCalendarDAO;
import cc.oit.bsmes.bas.dao.WeekCalendarShiftDAO;
import cc.oit.bsmes.bas.dao.WorkShiftDAO;
import cc.oit.bsmes.bas.model.MonthCalendar;
import cc.oit.bsmes.bas.model.WeekCalendar;
import cc.oit.bsmes.bas.model.WeekCalendarShift;
import cc.oit.bsmes.bas.model.WorkShift;
import cc.oit.bsmes.bas.service.WorkShiftService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;

@Service
public class WorkShiftServiceImpl extends BaseServiceImpl<WorkShift> implements WorkShiftService {

	
	@Resource private WorkShiftDAO workShiftDAO;
	@Resource private MonthCalendarDAO monthCalendarDAO;
	@Resource private WeekCalendarDAO weekCalendarDAO;
	@Resource private WeekCalendarShiftDAO weekCalendarShiftDAO;
	@Override
	public List<WorkShift> getWorkShiftsByWeekNo(int weekNo) {
		List<WorkShift> list = workShiftDAO.getByWeekNo(weekNo);
		for(WorkShift ws:list){
			updateShiftTime(ws);
		} 
		return list;
	}
	
	private static void updateShiftTime(WorkShift ws){
		String shiftStartTime = ws.getShiftStartTime();
		if(StringUtils.isNotBlank(shiftStartTime)){
			ws.setShiftStartTime(shiftStartTime.substring(0, 2)+":"+shiftStartTime.substring(2, 4));
		}
		String shiftEndTime  = ws.getShiftEndTime();
		if(StringUtils.isNotBlank(shiftEndTime)){
			ws.setShiftEndTime(shiftEndTime.substring(0, 2)+":"+shiftEndTime.substring(2, 4));
		}
	}

	@Override
	public WorkShift getWorkShiftByShiftName(String shiftName) {
		return workShiftDAO.getWorkShiftByShiftName(shiftName);
	}

	@Override
	public List<WorkShift> getWorkShiftName() {
		return workShiftDAO.getWorkShiftName();
	}

	@Override
	public List<WorkShift> getWorkShiftInfo() {
		return workShiftDAO.getWorkShiftInfo();
	}

	@Override
	public List<WorkShift> getWorkShifByStartAndEnd(Date startDate,Date endDate,String orgCode) {
		List<WorkShift> list=new ArrayList<WorkShift>();
		String startTime=DateUtils.getDay(startDate);
		String endTime=DateUtils.getDay(endDate);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("start", startTime.substring(0, 6));
		map.put("end", endTime.substring(0, 6));
		Calendar cal=Calendar.getInstance();
		cal.setTime(startDate);
		Calendar calTemp=Calendar.getInstance();
		List<MonthCalendar> monthCalendar=monthCalendarDAO.getByStartAndEnd(map);
		for(MonthCalendar month:monthCalendar){
			 long totalDays = DateUtils.getTimeDiff(startDate, endDate);
			 Date temp=startDate;
			 for(int i=0;i<totalDays;i++){
				 if(i>0){
            		 temp = DateUtils.addDayToDate(temp, 1);
				 }
				 String workDay = DateUtils.getDay(temp); //20131203
				 if(StringUtils.equals(month.getWorkMonth(), workDay.substring(0, 6))){
					 String field=("0".equals(workDay.substring(6, 7))) ? "Day" + workDay.substring(7) : "Day" + workDay.substring(6);
					 String result=invokeMethod(month,field).toString();
					 if(StringUtils.equals(result, "1")){
						 cal.setTime(temp);
						  int week = EquipCalendarServiceImpl.getWeek(cal.get(Calendar.DAY_OF_WEEK));
						  WeekCalendar weekCalendar = weekCalendarDAO.getWeekCalendarByWeekNo(week + "",orgCode);
						  if (weekCalendar != null && weekCalendar.isIsworkday()){
							  List<WeekCalendarShift> weekShift = weekCalendarShiftDAO.getByWeekCalendarId(weekCalendar.getId());
							  for (WeekCalendarShift we : weekShift) {
								  WorkShift workShift=new WorkShift();
								  workShift.setShiftName(we.getShiftName());
								  Date resStart=DateUtils.convert(workDay+we.getShiftStartTime(),DateUtils.DATE_TIME_SHORT_FORMAT);
								  workShift.setShiftStartTime(DateUtils.convert(resStart));
								  resStart=DateUtils.convert(workDay+we.getShiftEndTime(),DateUtils.DATE_TIME_SHORT_FORMAT);
								  workShift.setShiftEndTime(DateUtils.convert(resStart));
	                              list.add(workShift);
							  }
						  }
					 }
				 }else{
					  cal.add(Calendar.MONTH, 1);
					  calTemp.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),1);
					  startDate=calTemp.getTime();
					  break;
				 }
			 }
		}
		return list;
	}
	
	 private  Object invokeMethod(MonthCalendar monthCal, String methodName){
	        Class<? extends Object> monthClass = monthCal.getClass();
	        Method method = null;
	        try {
				method = monthClass.getMethod("get" + methodName);
				return method.invoke(monthCal);
			} catch (Exception e){
				e.printStackTrace();
				 return "";
			}
	    }
}

