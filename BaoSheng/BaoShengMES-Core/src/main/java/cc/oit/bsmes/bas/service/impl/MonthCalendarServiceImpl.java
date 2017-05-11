package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.MonthCalendarDAO;
import cc.oit.bsmes.bas.dao.WeekCalendarDAO;
import cc.oit.bsmes.bas.model.MonthCalendar;
import cc.oit.bsmes.bas.model.WeekCalendar;
import cc.oit.bsmes.bas.service.MonthCalendarService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-3-7 下午1:10:23
 * @since
 * @version
 */
@Service
public class MonthCalendarServiceImpl extends BaseServiceImpl<MonthCalendar>
		implements MonthCalendarService {
	@Resource
	private MonthCalendarDAO monthCalendarDAO;

	@Resource
	private WeekCalendarDAO weekCalendarDAO;

	@Override
	public void generateWorkDate(Date date, String orgCode) {
		int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		int preYear = calendar.get(Calendar.YEAR);
		int preMonth = calendar.get(Calendar.MONTH) + 1;

		MonthCalendar monthCalendar = monthCalendarDAO.getLatestDate();
		int baseYear = 0;
		int baseMonth = 0;
		if(monthCalendar!=null){
			String workMonth = monthCalendar.getWorkMonth();
			baseYear = Integer.valueOf(workMonth.substring(0, 4));
			baseMonth = Integer.valueOf(workMonth.substring(4));
		}else{
			Calendar calendarPre = Calendar.getInstance();
			baseYear = calendarPre.get(Calendar.YEAR);
            baseMonth = calendarPre.get(Calendar.MONTH) + 1;
		}

		if (preYear > baseYear) {
			int timeDistance = preMonth
					+ (12 * (preYear - baseYear) - baseMonth);
			for (int i = 0; i < timeDistance; i++) {
				MonthCalendar monthCalendarInsert = new MonthCalendar();
				if (baseMonth == 12) {
					int day = 1;
					baseYear++;
					baseMonth = 1;
					monthCalendarInsert.setWorkMonth(String.valueOf(baseYear)+"01");

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay1("1");
					} else {
						monthCalendarInsert.setDay1("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay2("1");
					} else {
						monthCalendarInsert.setDay2("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay3("1");
					} else {
						monthCalendarInsert.setDay3("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay4("1");
					} else {
						monthCalendarInsert.setDay4("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay5("1");
					} else {
						monthCalendarInsert.setDay5("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay6("1");
					} else {
						monthCalendarInsert.setDay6("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay7("1");
					} else {
						monthCalendarInsert.setDay7("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay8("1");
					} else {
						monthCalendarInsert.setDay8("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay9("1");
					} else {
						monthCalendarInsert.setDay9("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay10("1");
					} else {
						monthCalendarInsert.setDay10("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay11("1");
					} else {
						monthCalendarInsert.setDay11("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay12("1");
					} else {
						monthCalendarInsert.setDay12("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay13("1");
					} else {
						monthCalendarInsert.setDay13("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay14("1");
					} else {
						monthCalendarInsert.setDay14("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay15("1");
					} else {
						monthCalendarInsert.setDay15("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay16("1");
					} else {
						monthCalendarInsert.setDay16("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay17("1");
					} else {
						monthCalendarInsert.setDay17("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay18("1");
					} else {
						monthCalendarInsert.setDay18("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay19("1");
					} else {
						monthCalendarInsert.setDay19("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay20("1");
					} else {
						monthCalendarInsert.setDay20("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay21("1");
					} else {
						monthCalendarInsert.setDay21("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay22("1");
					} else {
						monthCalendarInsert.setDay22("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay23("1");
					} else {
						monthCalendarInsert.setDay23("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay24("1");
					} else {
						monthCalendarInsert.setDay24("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay25("1");
					} else {
						monthCalendarInsert.setDay25("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay26("1");
					} else {
						monthCalendarInsert.setDay26("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay27("1");
					} else {
						monthCalendarInsert.setDay27("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay28("1");
					} else {
						monthCalendarInsert.setDay28("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay29("1");
					} else {
						monthCalendarInsert.setDay29("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay30("1");
					} else {
						monthCalendarInsert.setDay30("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay31("1");
					} else {
						monthCalendarInsert.setDay31("0");
					}
				} else {
					int day = 1;
					baseMonth++;
					if(String.valueOf(baseMonth).length()==1){
						monthCalendarInsert.setWorkMonth(String.valueOf(baseYear)+"0"+baseMonth);
					}else{
						monthCalendarInsert.setWorkMonth(String.valueOf(baseYear)+baseMonth);
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay1("1");
					} else {
						monthCalendarInsert.setDay1("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay2("1");
					} else {
						monthCalendarInsert.setDay2("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay3("1");
					} else {
						monthCalendarInsert.setDay3("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay4("1");
					} else {
						monthCalendarInsert.setDay4("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay5("1");
					} else {
						monthCalendarInsert.setDay5("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay6("1");
					} else {
						monthCalendarInsert.setDay6("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay7("1");
					} else {
						monthCalendarInsert.setDay7("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay8("1");
					} else {
						monthCalendarInsert.setDay8("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay9("1");
					} else {
						monthCalendarInsert.setDay9("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay10("1");
					} else {
						monthCalendarInsert.setDay10("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay11("1");
					} else {
						monthCalendarInsert.setDay11("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay12("1");
					} else {
						monthCalendarInsert.setDay12("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay13("1");
					} else {
						monthCalendarInsert.setDay13("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay14("1");
					} else {
						monthCalendarInsert.setDay14("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay15("1");
					} else {
						monthCalendarInsert.setDay15("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay16("1");
					} else {
						monthCalendarInsert.setDay16("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay17("1");
					} else {
						monthCalendarInsert.setDay17("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay18("1");
					} else {
						monthCalendarInsert.setDay18("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay19("1");
					} else {
						monthCalendarInsert.setDay19("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay20("1");
					} else {
						monthCalendarInsert.setDay20("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay21("1");
					} else {
						monthCalendarInsert.setDay21("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay22("1");
					} else {
						monthCalendarInsert.setDay22("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay23("1");
					} else {
						monthCalendarInsert.setDay23("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay24("1");
					} else {
						monthCalendarInsert.setDay24("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay25("1");
					} else {
						monthCalendarInsert.setDay25("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay26("1");
					} else {
						monthCalendarInsert.setDay26("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay27("1");
					} else {
						monthCalendarInsert.setDay27("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay28("1");
					} else {
						monthCalendarInsert.setDay28("0");
					}
						
					if (isLeapYear(baseYear) && baseMonth == 2) {
						if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
							monthCalendarInsert.setDay29("1");
						} else {
							monthCalendarInsert.setDay29("0");
						}
					} else if(baseMonth != 2){
						if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
							monthCalendarInsert.setDay29("1");
						} else {
							monthCalendarInsert.setDay29("0");
						}
						if(daysInMonth(daysInMonth,baseMonth-1)==30){
							if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
								monthCalendarInsert.setDay30("1");
							} else {
								monthCalendarInsert.setDay30("0");
							}
						}else{
							if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
								monthCalendarInsert.setDay30("1");
							} else {
								monthCalendarInsert.setDay30("0");
							}
							
							if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
								monthCalendarInsert.setDay31("1");
							} else {
								monthCalendarInsert.setDay31("0");
							}
						}
					}
				}
				monthCalendarDAO.insert(monthCalendarInsert);
			}
		} else if (preYear == baseYear && preMonth > baseMonth) {
			int timeDistance = preMonth - baseMonth;
			for (int i = 0; i < timeDistance; i++) {
				MonthCalendar monthCalendarInsert = new MonthCalendar();
				int day = 1;
				baseMonth++;
				if(String.valueOf(baseMonth).length()==1){
					monthCalendarInsert.setWorkMonth(String.valueOf(baseYear)+"0"+baseMonth);
				}else{
					monthCalendarInsert.setWorkMonth(String.valueOf(baseYear)+baseMonth);
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay1("1");
				} else {
					monthCalendarInsert.setDay1("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay2("1");
				} else {
					monthCalendarInsert.setDay2("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay3("1");
				} else {
					monthCalendarInsert.setDay3("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay4("1");
				} else {
					monthCalendarInsert.setDay4("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay5("1");
				} else {
					monthCalendarInsert.setDay5("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay6("1");
				} else {
					monthCalendarInsert.setDay6("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay7("1");
				} else {
					monthCalendarInsert.setDay7("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay8("1");
				} else {
					monthCalendarInsert.setDay8("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay9("1");
				} else {
					monthCalendarInsert.setDay9("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay10("1");
				} else {
					monthCalendarInsert.setDay10("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay11("1");
				} else {
					monthCalendarInsert.setDay11("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay12("1");
				} else {
					monthCalendarInsert.setDay12("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay13("1");
				} else {
					monthCalendarInsert.setDay13("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay14("1");
				} else {
					monthCalendarInsert.setDay14("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay15("1");
				} else {
					monthCalendarInsert.setDay15("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay16("1");
				} else {
					monthCalendarInsert.setDay16("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay17("1");
				} else {
					monthCalendarInsert.setDay17("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay18("1");
				} else {
					monthCalendarInsert.setDay18("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay19("1");
				} else {
					monthCalendarInsert.setDay19("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay20("1");
				} else {
					monthCalendarInsert.setDay20("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay21("1");
				} else {
					monthCalendarInsert.setDay21("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay22("1");
				} else {
					monthCalendarInsert.setDay22("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay23("1");
				} else {
					monthCalendarInsert.setDay23("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay24("1");
				} else {
					monthCalendarInsert.setDay24("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay25("1");
				} else {
					monthCalendarInsert.setDay25("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay26("1");
				} else {
					monthCalendarInsert.setDay26("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay27("1");
				} else {
					monthCalendarInsert.setDay27("0");
				}

				if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
					monthCalendarInsert.setDay28("1");
				} else {
					monthCalendarInsert.setDay28("0");
				}

				if (isLeapYear(baseYear) && baseMonth == 2) {
					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay29("1");
					} else {
						monthCalendarInsert.setDay29("0");
					}
				} else {
					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay29("1");
					} else {
						monthCalendarInsert.setDay29("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay30("1");
					} else {
						monthCalendarInsert.setDay30("0");
					}

					if (isWorkDay(baseYear, baseMonth, day++, orgCode)) {
						monthCalendarInsert.setDay31("1");
					} else {
						monthCalendarInsert.setDay31("0");
					}
				}
				monthCalendarDAO.insert(monthCalendar);
			}
		}
	}

	public boolean isWorkDay(int year, int month, int day, String orgCode) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		int preDay = calendar.get(Calendar.DAY_OF_WEEK);
		boolean workDay ;
		if(preDay==1){
			WeekCalendar weekCalendar = weekCalendarDAO
					.getWeekCalendarByWeekNo("7",orgCode);
			workDay = weekCalendar.isIsworkday();
		}else{
			WeekCalendar weekCalendar = weekCalendarDAO
					.getWeekCalendarByWeekNo(String.valueOf(preDay - 1),orgCode);
			workDay = weekCalendar.isIsworkday();
		}
		return workDay;
	}

	public boolean isLeapYear(int year) {
		return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? true
				: false;
	}

	public int daysInMonth(int[] daysInMonth, int month) {
		return daysInMonth[month];
	}

	@Override
	public List<MonthCalendar> queryWorkDay(String workMonth,String orgCode) {
		return monthCalendarDAO.queryWorkDay(workMonth,orgCode);
	}

	@Override
	public void updateDate(MonthCalendar monthCalendar) {
		monthCalendarDAO.updateDate(monthCalendar);
	}
	
	private String[] getDayInfo(MonthCalendar monthCalendar){
		String[]  dayList = new String[31];
		dayList[0] = monthCalendar.getDay1();
		dayList[1] = monthCalendar.getDay2();
		dayList[2] = monthCalendar.getDay3();
		dayList[3] = monthCalendar.getDay4();
		dayList[4] = monthCalendar.getDay5();
		dayList[5] = monthCalendar.getDay6();
		dayList[6] = monthCalendar.getDay7();
		dayList[7] = monthCalendar.getDay8();
		dayList[8] = monthCalendar.getDay9();
		dayList[9] = monthCalendar.getDay10();
		dayList[10] = monthCalendar.getDay11();
		dayList[11] = monthCalendar.getDay12();
		dayList[12] = monthCalendar.getDay13();
		dayList[13] = monthCalendar.getDay14();
		dayList[14] = monthCalendar.getDay15();
		dayList[15] = monthCalendar.getDay16();
		dayList[16] = monthCalendar.getDay17();
		dayList[17] = monthCalendar.getDay18();
		dayList[18] = monthCalendar.getDay19();
		dayList[19] = monthCalendar.getDay20();
		dayList[20] = monthCalendar.getDay21();
		dayList[21] = monthCalendar.getDay22();
		dayList[22] = monthCalendar.getDay23();
		dayList[23] = monthCalendar.getDay24();
		dayList[24] = monthCalendar.getDay25();
		dayList[25] = monthCalendar.getDay26();
		dayList[26] = monthCalendar.getDay27();
		dayList[27] = monthCalendar.getDay28();
		dayList[28] = monthCalendar.getDay29();
		dayList[29] = monthCalendar.getDay30();
		dayList[30] = monthCalendar.getDay31();
		return dayList;
	}

	@Override
	public List<Date> queryNotWorkDay(Date startDate,Date endDate,String orgCode) {
		long diff = endDate.getTime() - startDate.getTime();
	    long days = diff / (1000 * 60 * 60 * 24);            //相隔天数
	    
		List<Date> dateList = new ArrayList<Date>();
		Calendar calendar = Calendar.getInstance();
	    
	    for(int day=0;day<=days;day++){
	    	calendar.setTimeInMillis(startDate.getTime());
			calendar.add(Calendar.DATE,day);
			int year = calendar.get(Calendar.YEAR)-1900;
			int month = calendar.get(Calendar.MONTH)+1;
			String monthStr = String.valueOf(month).length()==1?("0"+month):(""+month);
			String begin_date = year + monthStr;
			List<MonthCalendar> monthCalendarList = monthCalendarDAO.queryWorkDay(begin_date,orgCode);
			String[] dayInfo = getDayInfo(monthCalendarList.get(0));
			if(("0").equals(dayInfo[calendar.get(Calendar.DAY_OF_MONTH)-1])){
				Date date = new Date(calendar.getTimeInMillis());
				dateList.add(date);
			}
	    }
	    
		return dateList;
	}
}
