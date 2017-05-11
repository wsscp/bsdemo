package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.WorkShift;
import cc.oit.bsmes.common.service.BaseService;

import java.util.Date;
import java.util.List;

public interface WorkShiftService extends BaseService<WorkShift> {

	/**
	 * 
	 * <p>查询当天的班次信息</p> 
	 * @author JinHanyun
	 * @date 2014-3-7 下午4:35:41
	 * @return
	 * @see
	 */
	public List<WorkShift> getWorkShiftsByWeekNo(int weekNo);
	
	public WorkShift getWorkShiftByShiftName(String shiftName);
	
	public List<WorkShift> getWorkShiftName();
	
	public List<WorkShift> getWorkShiftInfo();
	/**
	 * 
	 * <p>根据开始日期，结束日期获取班次信息</p> 
	 * @author leiwei
	 * @date 2014-4-24 下午3:27:26
	 * @return
	 * @see
	 */
	public List<WorkShift> getWorkShifByStartAndEnd(Date startDate,Date endDate,String orgCode);
}
