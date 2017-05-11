package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.WeekCalendarShift;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;

import java.util.List;
import java.util.Map;

public interface WeekCalendarShiftService extends BaseService<WeekCalendarShift> {
	
	public List<WeekCalendarShift> findByRequestMap(Map<String, Object> requestMap, int start,int limit, List<Sort> sortList);
    
    public Integer countByRequestMap(Map<String, Object> requestMap);
    
    public WeekCalendarShift getByWeekCalendarIdAndWorkShiftId(String weekCalendarId,String workShiftId,String orgCode);
    
    public List<WeekCalendarShift> getByWeekCalendarId(String weekCalendarId);
    
    /**
     * 某一工作日的夜班开始时间与前一天工作日的中班结束时间比较
     * @param shiftEndTime
     * @param shiftStartTime
     * @return
     */
    public Boolean validTime(String shiftEndTime,String shiftStartTime);
    
    /**
     * 某一工作日的白班开始时间与当天工作日的夜班结束时间比较、
     * 某一工作日的中班开始时间与当天工作日的白班结束时间比较
     * @param shiftEndTime
     * @param shiftStartTime
     * @return
     */
    public Boolean validTimeTop(String shiftEndTime,String shiftStartTime);
    
}
