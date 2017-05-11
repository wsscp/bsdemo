package cc.oit.bsmes.common.util;


public class DayOfWeekUtils {

	/**
	 * 
	 * <p>Calendar中的DAY_OF_WEEK转成中国周中的星期几</p> 
	 * @author JinHanyun
	 * @date 2014-3-10 上午9:21:34
	 * @param calendarDay
	 * @return
	 * @see
	 */
	public static int getChinaDayOfWeek(int calendarDay){
		switch (calendarDay) {
		case 1:
			return 7;
		case 2:
			return 1;
		case 3:
			return 2;
		case 4:
			return 3;
		case 5:
			return 4;
		case 6:
			return 5;
		case 7:
			return 6;
		default:
			return 0;
		}
	}
	
	/**
	 * 
	 * <p>中国周中的星期几转成Calendar中的DAY_OF_WEEK</p> 
	 * @author JinHanyun
	 * @date 2014-3-10 上午9:23:24
	 * @param day
	 * @return
	 * @see
	 */
	public static int getCalendarDayOfWeek(int day){
		switch (day) {
		case 1:
			return 2;
		case 2:
			return 3;
		case 3:
			return 4;
		case 4:
			return 5;
		case 5:
			return 6;
		case 6:
			return 7;
		case 7:
			return 1;
		default:
			return 0;
		}
	}
}
