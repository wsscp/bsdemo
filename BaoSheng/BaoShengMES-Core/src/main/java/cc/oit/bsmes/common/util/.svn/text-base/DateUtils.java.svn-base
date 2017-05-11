package cc.oit.bsmes.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * 日期工具类
 * 
 */
public class DateUtils {

	private static final Log logger = LogFactory.getLog(DateUtils.class);

	public static final String DATE_SHORT_FORMAT = "yyyyMMdd";
	public static final String MONTH_SHORT_FORMAT = "yyyyMM";
	public static final String DATE_TIME_SHORT_FORMAT = "yyyyMMddHHmm";
	public static final String DATE_TIMESTAMP_SHORT_FORMAT = "yyyyMMddHHmmss";
	public static final String DATE_TIMESTAMP_LONG_FORMAT = "yyyyMMddHHmmssS";
	public static final String DATE_CH_FORMAT = "yyyy年MM月dd日";

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_SHORT_FORMAT2 = "yyyy-MM-dd HH:mm";
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String TIME_SHORT_FORMAT = "HHmmss";

	public static final String DAYTIME_START = "00:00:00";
	public static final String DAYTIME_END = "23:59:59";

	private DateUtils() {
	}

	private static final String[] FORMATS = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss", "HH:mm",
			"HH:mm:ss", "yyyy-MM", "yyyy-MM-dd HH:mm:ss.S" };

	public static Date convert(String str) {
		if (str != null && str.length() > 0) {
			if (str.length() > 10 && str.charAt(10) == 'T') {
				str = str.replace('T', ' '); // 去掉json-lib加的T字母
			}
			for (String format : FORMATS) {
				if (str.length() == format.length()) {
					try {
						Date date = new SimpleDateFormat(format).parse(str);

						return date;
					} catch (ParseException e) {
						if (logger.isWarnEnabled()) {
							logger.warn(e.getMessage());
						}
						// logger.warn(e.getMessage());
					}
				}
			}
		}
		return null;
	}

	public static Date convert(String str, String format) {
		if (!StringUtils.isEmpty(str)) {
			try {
				Date date = new SimpleDateFormat(format).parse(str);
				return date;
			} catch (ParseException e) {
				if (logger.isWarnEnabled()) {
					logger.warn(e.getMessage());
				}
				// logger.warn(e.getMessage());
			}
		}
		return null;
	}

	/**
	 * 时间拼接 将日期和实现拼接 ymd 如2012-05-15 hm 如0812
	 * 
	 * 
	 * @date 2012-11-22 下午4:48:43
	 */
	public static Date concat(String ymd, String hm) {
		if (!StringUtils.isEmpty(ymd) && !StringUtils.isEmpty(hm)) {
			try {
				String dateString = ymd.concat(" ").concat(
						hm.substring(0, 2).concat(":").concat(hm.substring(2, 4)).concat(":00"));
				Date date = DateUtils.convert(dateString, DateUtils.DATE_TIME_FORMAT);
				return date;
			} catch (NullPointerException e) {
				if (logger.isWarnEnabled()) {
					logger.warn(e.getMessage());
				}
			}
		}
		return null;
	}

	/**
	 * 根据传入的日期返回年月日的6位字符串，例：20101203
	 * 
	 * @date 2012-11-28 下午8:35:55
	 */
	public static String getDay(Date date) {
		return convert(date, DATE_SHORT_FORMAT);
	}

	/**
	 * 根据传入的日期返回中文年月日字符串，例：2010年12月03日
	 * 
	 * @date 2012-11-28 下午8:35:55
	 */
	public static String getChDate(Date date) {
		return convert(date, DATE_CH_FORMAT);
	}

	/**
	 * 将传入的时间格式的字符串转成时间对象
	 * 
	 * 例：传入2012-12-03 23:21:24
	 * 
	 * @date 2012-11-29 上午11:29:31
	 */
	public static Date strToDate(String dateStr) {
		SimpleDateFormat formatDate = new SimpleDateFormat(DATE_TIME_FORMAT);
		Date date = null;
		try {
			date = formatDate.parse(dateStr);
		} catch (Exception e) {

		}
		return date;
	}

	public static String convert(Date date) {
		return convert(date, DATE_TIME_FORMAT);
	}

	public static String convert(Date date, String dateFormat) {
		if (date == null) {
			return null;
		}

		if (null == dateFormat) {
			dateFormat = DATE_TIME_FORMAT;
		}

		return new SimpleDateFormat(dateFormat).format(date);
	}

	/**
	 * 返回该日期的最后一刻，精确到纳秒
	 * 
	 * @return
	 */
	public static Timestamp getLastEndDatetime(Date endTime) {
		Timestamp ts = new Timestamp(endTime.getTime());
		ts.setNanos(999999999);
		return ts;
	}

	/**
	 * 返回该日期加1秒
	 * 
	 * @return
	 */
	public static Timestamp getEndTimeAdd(Date endTime) {
		Timestamp ts = new Timestamp(endTime.getTime());
		Calendar c = Calendar.getInstance();
		c.setTime(ts);
		c.add(Calendar.MILLISECOND, 1000);
		c.set(Calendar.MILLISECOND, 0);
		return new Timestamp(c.getTimeInMillis());
	}

	/**
	 * 相对当前日期，增加或减少天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static String addDay(Date date, int day) {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);

		return df.format(new Date(date.getTime() + 1000l * 24 * 60 * 60 * day));
	}
	
	/**
	 * 相对当前日期，增加或减少天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addHours(Date date, int hour) {
		return new Date(date.getTime() + 1000l * 60 * 60 * hour);
	}

	/**
	 * 相对当前日期，增加或减少天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDayToDate(Date date, long day) {
		return new Date(date.getTime() + 1000 * 24 * 60 * 60 * day);
	}

	/**
	 * 返回两个时间的相差天数
	 * 
	 * @param startTime 对比的开始时间
	 * @param endTime 对比的结束时间
	 * @return 相差天数
	 */

	public static Long getTimeDiff(String startTime, String endTime) {
		Long days = null;
		Date startDate = null;
		Date endDate = null;
		try {
			if (startTime.length() == 10 && endTime.length() == 10) {
				startDate = new SimpleDateFormat(DATE_FORMAT).parse(startTime);
				endDate = new SimpleDateFormat(DATE_FORMAT).parse(endTime);
			} else {
				startDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(startTime);
				endDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(endTime);
			}

			days = getTimeDiff(startDate, endDate);
		} catch (ParseException e) {
			if (logger.isWarnEnabled()) {
				logger.warn(e.getMessage());
			}
			days = null;
		}
		return days;
	}

	/**
	 * 返回两个时间的相差天数
	 * 
	 * @param startTime 对比的开始时间
	 * @param endTime 对比的结束时间
	 * @return 相差天数
	 */
	public static Long getTimeDiff(Date startTime, Date endTime) {
		Long days = null;

		Calendar c = Calendar.getInstance();
		c.setTime(startTime);
		long l_s = c.getTimeInMillis();
		c.setTime(endTime);
		long l_e = c.getTimeInMillis();
		days = (l_e - l_s) / 86400000;
		return days;
	}

	/**
	 * 返回两个时间的相差分钟数
	 * 
	 * @param startTime 对比的开始时间
	 * @param endTime 对比的结束时间
	 * @return 相差分钟数
	 */
	public static Long getMinuteDiff(Date startTime, Date endTime) {
		Long minutes = null;

		Calendar c = Calendar.getInstance();
		c.setTime(startTime);
		long l_s = c.getTimeInMillis();
		c.setTime(endTime);
		long l_e = c.getTimeInMillis();
		minutes = (l_e - l_s) / (1000l * 60);
		return minutes;
	}

	/**
	 * 返回两个时间的相差分钟数
	 * 
	 * @param startTime 对比的开始时间
	 * @param endTime 对比的结束时间
	 * @return 相差分钟数
	 */
	public static Long getSecondsDiff(Date startTime, Date endTime) {
		Long minutes = null;

		Calendar c = Calendar.getInstance();
		c.setTime(startTime);
		long l_s = c.getTimeInMillis();
		c.setTime(endTime);
		long l_e = c.getTimeInMillis();
		minutes = (l_e - l_s) / (1000l);
		return minutes;
	}

	public static String getPidFromDate(Date date) {
		if (date == null) {
			return "";
		}

		String m = convert(date, "yyyyMM");
		String d = convert(date, "dd");

		if (Integer.valueOf(d) <= 10) {
			d = "01";
		} else if (Integer.valueOf(d) <= 20) {
			d = "02";
		} else {
			d = "03";
		}
		return m.concat(d);
	}

	// 获得n天后00:00:00点时间
	public static Date getStartDatetime(Date date, Integer diffDays) {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String thisdate = df.format(date.getTime() + 1000l * 24 * 60 * 60 * diffDays);
		return convert(thisdate + " " + DAYTIME_START);
	}

	// 获得n天后23:59:59点时间
	public static Date getEndDatetime(Date date, Integer diffDays) {
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String thisdate = df.format(date.getTime() + 1000l * 24 * 60 * 60 * diffDays);
		return convert(thisdate + " " + DAYTIME_END);

	}

	// 获得当天00:00:00点时间
	public static Date getStartDatetime(Date date) {
		String thisdate = convert(date, DATE_FORMAT);
		return convert(thisdate + " " + DAYTIME_START);
	}

	// 获得当天23:59:59点时间
	public static Date getEndDatetime(Date date) {
		String thisdate = convert(date, DATE_FORMAT);
		return convert(thisdate + " " + DAYTIME_END);

	}

	// 获得本周一00:00:00点时间
	public static Date getStartWeektime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String thisdate = convert(cal.getTime(), DATE_FORMAT);
		return convert(thisdate + " " + DAYTIME_START);
	}

	// 获得本周日23:59:59点时间
	public static Date getEndWeektime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		String thisdate = convert(cal.getTime(), DATE_FORMAT);
		return convert(thisdate + " " + DAYTIME_END);
	}

	// 获得本月第一天00:00:00点时间
	public static Date getStartMonthtime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		String thisdate = convert(cal.getTime(), DATE_FORMAT);
		return convert(thisdate + " " + DAYTIME_START);
	}

	// 获得本月最后一天23:59:59点时间
	public static Date getEndMonthtime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cal.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		String thisdate = convert(cal.getTime(), DATE_FORMAT);
		return convert(thisdate + " " + DAYTIME_END);
	}

	// 获得本月天数
	public static int getMonthDayCount(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cal.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		return cal.get(Calendar.DATE);
	}

	public static Date getLastMonthStartMorning(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setTime(getStartMonthtime(date));
		cal.add(Calendar.MONTH, -1);
		return cal.getTime();
	}

	public static Date getCurrentQuarterStartTime(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int currentMonth = c.get(Calendar.MONTH) + 1;
		SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3)
				c.set(Calendar.MONTH, 0);
			else if (currentMonth >= 4 && currentMonth <= 6)
				c.set(Calendar.MONTH, 3);
			else if (currentMonth >= 7 && currentMonth <= 9)
				c.set(Calendar.MONTH, 4);
			else if (currentMonth >= 10 && currentMonth <= 12)
				c.set(Calendar.MONTH, 9);
			c.set(Calendar.DATE, 1);
			now = longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 当前季度的结束时间，即2012-03-31 23:59:59
	 * 
	 * @return
	 */
	public static Date getCurrentQuarterEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setTime(getCurrentQuarterStartTime(date));
		cal.add(Calendar.MONTH, 3);
		return cal.getTime();
	}

	public static void main(String[] args) {

		System.out.println("当前时间：" + new Date().toLocaleString());
		System.out.println("本周周一0点时间：" + getStartWeektime(new Date()).toLocaleString());
		System.out.println("本周周日24点时间：" + getEndWeektime(new Date()).toLocaleString());
		System.out.println("本月初0点时间：" + getStartMonthtime(new Date()).toLocaleString());
		System.out.println("本月未24点时间：" + getEndMonthtime(new Date()).toLocaleString());
		System.out.println("上月初0点时间：" + getLastMonthStartMorning(new Date()).toLocaleString());
		System.out.println("本季度开始点时间：" + getCurrentQuarterStartTime(new Date()).toLocaleString());
		System.out.println("本季度结束点时间：" + getCurrentQuarterEndTime(new Date()).toLocaleString());
		//
	}
}
