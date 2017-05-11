package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.WeekCalendarShiftDAO;
import cc.oit.bsmes.bas.model.WeekCalendarShift;
import cc.oit.bsmes.bas.service.WeekCalendarShiftService;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class WeekCalendarShiftServiceImpl extends BaseServiceImpl<WeekCalendarShift> implements WeekCalendarShiftService {

	@Resource
	private WeekCalendarShiftDAO weekCalendarShiftDAO;
	
	@Override
	public List<WeekCalendarShift> findByRequestMap(
			Map<String, Object> requestMap, int start, int limit, List<Sort> sortList) {
		return weekCalendarShiftDAO.findByRequestMap(requestMap);
	}

	@Override
	public Integer countByRequestMap(Map<String, Object> requestMap) {
		return weekCalendarShiftDAO.countByRequestMap(requestMap);
	}

	@Override
	public WeekCalendarShift getByWeekCalendarIdAndWorkShiftId(
			String weekCalendarId, String workShiftId,String orgCode) {
		return weekCalendarShiftDAO.getByWeekCalendarIdAndWorkShiftId(weekCalendarId,workShiftId,orgCode);
	}

	@Override
	public List<WeekCalendarShift> getByWeekCalendarId(String weekCalendarId) {
		return weekCalendarShiftDAO.getByWeekCalendarId(weekCalendarId);
	}

	@Override
	public Boolean validTime(String shiftEndTime,String shiftStartTime) {
		Boolean bool = false;
		if(shiftStartTime.length()==4&&shiftEndTime.length()==4&&Integer.valueOf(shiftEndTime)>Integer.valueOf(shiftStartTime)){
			bool = true;
		}else if(shiftStartTime.length()==4&&shiftEndTime.length()<4){
			bool = true;
		}else if(shiftStartTime.length()<4&&shiftEndTime.length()<4&&Integer.valueOf(shiftEndTime)>Integer.valueOf(shiftStartTime)){
			bool = true;
		}
		return bool;
	}

	@Override
	public Boolean validTimeTop(String shiftEndTime, String shiftStartTime) {
		Boolean bool = false;
		if(Integer.valueOf(shiftEndTime)>Integer.valueOf(shiftStartTime)){
			bool = true;
		}
		return bool;
	}

}

