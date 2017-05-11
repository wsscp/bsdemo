package cc.oit.bsmes.bas.dao;


import cc.oit.bsmes.bas.model.MonthCalendar;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;
import java.util.Map;

public interface MonthCalendarDAO extends BaseDAO<MonthCalendar> {

	public MonthCalendar getLatestDate();
	
	public List<MonthCalendar> queryWorkDay(String workMonth,String orgCode);
 
	public void updateDate(MonthCalendar monthCalendar);

	public List<MonthCalendar> getByStartAndEnd(Map map);
}
