package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 设备日历
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-1-13 下午12:00:09
 * @since
 * @version
 */
public interface EquipCalendarDAO extends BaseDAO<EquipCalendar> {
	/**
	 * <p>
	 * TODO(根据日期获取设备班次)
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-3-7 上午11:29:22
	 * @return
	 * @see
	 */
	public EquipCalendar getByEquipAndDateOfWork(EquipCalendar cal);

	public List<EquipCalendar> getByLimitTime(Map<String, Object> map);

	/**
	 * <!-- 获取设备工作日历： 直接带回班次及开始时间结束时间等等 -->
	 * 
	 * @author DingXintao
	 * @date 2015-09-08
	 * 
	 * @param map : start\end\equipCode
	 * 
	 * */
	public List<EquipCalendar> getEquipCalendarByCode(Map<String, Object> map);

}
