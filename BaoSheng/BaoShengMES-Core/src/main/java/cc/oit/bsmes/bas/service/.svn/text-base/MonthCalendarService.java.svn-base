package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.MonthCalendar;
import cc.oit.bsmes.common.service.BaseService;

import java.util.Date;
import java.util.List;
/**
 * 
 * 月工作日历
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-7 下午1:07:36
 * @since
 * @version
 */
public interface MonthCalendarService extends BaseService<MonthCalendar> {
	
	public void generateWorkDate(Date date, String orgCode);
	
	public List<MonthCalendar> queryWorkDay(String workMonth,String orgCode);
	
	public void updateDate(MonthCalendar monthCalendar);
	
	public List<Date> queryNotWorkDay(Date startDate,Date endDate,String orgCode);
}
