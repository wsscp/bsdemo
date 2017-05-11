package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.fac.model.EquipInfo;

import java.util.Date;
import java.util.List;

/**
 * 
 * 设备工作日历
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-3-11 下午1:55:32
 * @since
 * @version
 */
public interface EquipCalendarService extends BaseService<EquipCalendar> {

	/**
	 * 获取设备工作日历
	 * 
	 * @author DingXintao
	 * @date 2015-09-07
	 * @param equipCode 设备编码
	 * @param startLoadTime 查询设备日历的开始加载时间，默认当前时间
	 * @param endLoadTime 查询设备日历的结束加载时间，默认当前时间的三个月之后
	 * @return
	 * @see
	 */
	public List<EquipCalendar> getEquipCalendarByCode(String equipCode, Date startLoadTime, Date endLoadTime);

	/**
	 * 获取设备工作日历,默认三个月，参数需包含设备code,起始加载时间(没有赋值默认当前时间),结束加载时间(无参默认当前时间的三个月以后的那天)
	 * 
	 * @author leiwei
	 * @date 2014-3-11 下午1:59:53
	 * @param equipInfo
	 * @param startLoadTime 查询设备日历的开始加载时间，默认当前时间
	 * @param endLoadTime 查询设备日历的结束加载时间，默认当前时间的三个月之后
	 * @return
	 * @see
	 */
	public List<EquipCalendar> getByEquipInfo(EquipInfo equipInfo, Date startLoadTime, Date endLoadTime);

	/**
	 * 
	 * <p>
	 * 返回某天的几个工作日后的日期
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-3-21 下午5:02:30
	 * @param start从哪天开始
	 * @param days 多少天
	 * @return
	 * @see
	 */
	public Date getNextDays(Date start, int days, String orgCode);

	/**
	 * 由设备编码和日期获得设备日历信息，可作为设备日历班次的唯一性判断
	 * 
	 * @param cal
	 * @return
	 */
	public EquipCalendar getByEquipCodeAndDateOfWork(EquipCalendar cal);

}
