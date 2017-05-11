package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.WeekCalendarShift;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;
import java.util.Map;

public interface WeekCalendarShiftDAO extends BaseDAO<WeekCalendarShift> {
	List<WeekCalendarShift> getByWeekCalendarId(String weekCalendarId);
	
	public List<WeekCalendarShift> findByRequestMap(Map<String, Object> requestMap);
    
    public Integer countByRequestMap(Map<String, Object> requestMap);
    
    public WeekCalendarShift getByWeekCalendarIdAndWorkShiftId(String weekCalendarId,String workShiftId,String orgCode);
    
}
