package cc.oit.bsmes.common.constants;

import cc.oit.bsmes.common.util.DateUtils;

import java.util.Date;

public class BusinessConstants {

	public static final String PROCESS_PATH_SPLITS = ";";

	public static final String CAPACITY_KEY_SEPARATOR = ";";

	public static final Date MAX_DATE = DateUtils.convert("2999-12-31");

	/**
	 * ORACLE最大IN数量
	 */
	public static final int ORACLE_MAX_IN_SIZE = 1000;
	/**
	 * 设备日历最大查询时间(三个月)
	 */
	public static final int MAX_CALENDER_QUERY = 3;
	/**
	 * 设备负载的同一台设备的的结束时间和它的下一个开始时间的间隔小于MAX_INTERVAL可以合并(分钟)
	 */
	public static final long MAX_INTERVAL = 5;

	/**
	 * 第一次生产提前两星期
	 */
	public static final int FIRST_TIME_EARLY_DATE = -14;

	/**
	 * 最大采集周期
	 */
	public static final int MAX_CYCLE_COUNT = 100;

	/**
	 * 以下为计算OA更新版本
	 * 
	 * @author DingXintao
	 * @date 2015-09-07
	 * */
	public static final String DAYS_TO_SCHEDULE = "plan.schedule.daysToSchedule"; // 业务查询系统参数KEY：精细排程工作日数
	public static final String All_DAYS = "plan.schedule.alldays"; // 业务查询系统参数KEY：精细排程订单天数
	public static final Integer ORDER_NUM = 450; // 计算OA一次查询的订单产品数量，为零/空则查询所有
	public static final boolean USE_CACHE = true; // 是否使用缓存
	public static Integer ECALENDAR_DAY_NUM = 15; // 设备负载查询占的天数
	public static Integer PRIORITY_USED_ECALENDAR_DAY_NUM = 3; // 优先使用设备负载的天数
	public static final boolean EQUIP_LOAD_BALANCE = false; // 设备是否使用负载均衡模式
	
	
}
