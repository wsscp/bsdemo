package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.WorkShift;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;

public interface WorkShiftDAO extends BaseDAO<WorkShift>{

	/**
	 * 
	 * <p>查询当天班次信息</p> 
	 * @author JinHanyun
	 * @date 2014-3-7 下午4:19:57
	 * @param weekNo
	 * @return
	 * @see
	 */
	public List<WorkShift> getByWeekNo(int weekNo);
	
	public WorkShift getWorkShiftByShiftName(String shiftName);

	public List<WorkShift> getWorkShiftName();
	
	public List<WorkShift> getWorkShiftInfo();
}
