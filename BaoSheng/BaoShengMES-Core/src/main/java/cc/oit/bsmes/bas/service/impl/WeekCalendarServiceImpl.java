package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.WeekCalendarDAO;
import cc.oit.bsmes.bas.model.WeekCalendar;
import cc.oit.bsmes.bas.service.WeekCalendarService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WeekCalendarServiceImpl extends BaseServiceImpl<WeekCalendar> implements WeekCalendarService {

	@Resource
	private WeekCalendarDAO weekCalendarDAO;

	@Override
	public WeekCalendar getWeekCalendarByWeekNo(String weekNo, String orgCode) {
		return weekCalendarDAO.getWeekCalendarByWeekNo(weekNo, orgCode);
	}
	
	

}

