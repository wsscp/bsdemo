package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.WeekCalendar;
import cc.oit.bsmes.common.service.BaseService;

public interface WeekCalendarService extends BaseService<WeekCalendar> {
	
	WeekCalendar getWeekCalendarByWeekNo(String weekNo,String orgCode);    
}
