/*
 * Copyright by Orientech and the original author or authors
 * 
 * This document only allow internal use.Any of your behaviors using the
 * file not internal will pay the legal responsibility.
 * 
 * You can learn more information about Orientech from 
 * 
 *       http://www.orientech.cc/
 */
package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.MonthCalendar;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.MonthCalendarService;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * Month Calendar
 * @author ChenXiang
 * @date 2014-3-25 下午5:25:15
 * @since
 * @version
 */
@Controller
@RequestMapping("/bas/monthCalendar")
public class MonthCalendarController {
	
	@Resource
	private MonthCalendarService monthCalendarService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "monthCalendar");
        return "bas.monthCalendar";
    }
	
	@RequestMapping(value="queryWorkDay",method = RequestMethod.GET)
	@ResponseBody
	@SuppressWarnings("all")
	public TableView queryWorkDay(@RequestParam String workday){
		User user = SessionUtils.getUser();
		String orgCode = user.getOrgCode();
		
		String yearStr = workday.substring(0,4);
		String monthStr = workday.substring(4,6);
		Integer year = Integer.valueOf(yearStr) -1900;
		Integer month = Integer.valueOf(monthStr)-1;
		Date date = new Date(year,month,1);
		monthCalendarService.generateWorkDate(date, orgCode);
		
		List<MonthCalendar> monthCalendarList = monthCalendarService.queryWorkDay(workday,orgCode);
		TableView tableView = new TableView();
		tableView.setRows(monthCalendarList);
		tableView.setTotal(monthCalendarList.size());
		return tableView;
	}
	
	@RequestMapping(value="updateDate", method = RequestMethod.GET)
	@ResponseBody
	public void updateDate(@RequestParam String workMonth,@RequestParam String day) throws ClassNotFoundException {
		User user = SessionUtils.getUser();
		String orgCode = user.getOrgCode();
		
		List<MonthCalendar> monthCalendarList = monthCalendarService.queryWorkDay(workMonth,orgCode);
		MonthCalendar monthCalendar = monthCalendarList.get(0);
		
		List<String> list = new ArrayList<String>();
		list.add(monthCalendar.getDay1());
		list.add(monthCalendar.getDay2());
		list.add(monthCalendar.getDay3());
		list.add(monthCalendar.getDay4());
		list.add(monthCalendar.getDay5());
		list.add(monthCalendar.getDay6());
		list.add(monthCalendar.getDay7());
		list.add(monthCalendar.getDay8());
		list.add(monthCalendar.getDay9());
		list.add(monthCalendar.getDay10());
		list.add(monthCalendar.getDay11());
		list.add(monthCalendar.getDay12());
		list.add(monthCalendar.getDay13());
		list.add(monthCalendar.getDay14());
		list.add(monthCalendar.getDay15());
		list.add(monthCalendar.getDay16());
		list.add(monthCalendar.getDay17());
		list.add(monthCalendar.getDay18());
		list.add(monthCalendar.getDay19());
		list.add(monthCalendar.getDay20());
		list.add(monthCalendar.getDay21());
		list.add(monthCalendar.getDay22());
		list.add(monthCalendar.getDay23());
		list.add(monthCalendar.getDay24());
		list.add(monthCalendar.getDay25());
		list.add(monthCalendar.getDay26());
		list.add(monthCalendar.getDay27());
		list.add(monthCalendar.getDay28());
		list.add(monthCalendar.getDay29());
		list.add(monthCalendar.getDay30());
		list.add(monthCalendar.getDay31());
		
		for(int i=0;i<list.size();i++){
			if((i+1)==Integer.valueOf(day)&&"1".equals(list.get(i))){
				list.set(i, "0");
			}else if((i+1)==Integer.valueOf(day)&&"0".equals(list.get(i))){
				list.set(i, "1");
			}
		}
		
		monthCalendar.setDay1(list.get(0));
		monthCalendar.setDay2(list.get(1));
		monthCalendar.setDay3(list.get(2));
		monthCalendar.setDay4(list.get(3));
		monthCalendar.setDay5(list.get(4));
		monthCalendar.setDay6(list.get(5));
		monthCalendar.setDay7(list.get(6));
		monthCalendar.setDay8(list.get(7));
		monthCalendar.setDay9(list.get(8));
		monthCalendar.setDay10(list.get(9));
		monthCalendar.setDay11(list.get(10));
		monthCalendar.setDay12(list.get(11));
		monthCalendar.setDay13(list.get(12));
		monthCalendar.setDay14(list.get(13));
		monthCalendar.setDay15(list.get(14));
		monthCalendar.setDay16(list.get(15));
		monthCalendar.setDay17(list.get(16));
		monthCalendar.setDay18(list.get(17));
		monthCalendar.setDay19(list.get(18));
		monthCalendar.setDay20(list.get(19));
		monthCalendar.setDay21(list.get(20));
		monthCalendar.setDay22(list.get(21));
		monthCalendar.setDay23(list.get(22));
		monthCalendar.setDay24(list.get(23));
		monthCalendar.setDay25(list.get(24));
		monthCalendar.setDay26(list.get(25));
		monthCalendar.setDay27(list.get(26));
		monthCalendar.setDay28(list.get(27));
		monthCalendar.setDay29(list.get(28));
		monthCalendar.setDay30(list.get(29));
		monthCalendar.setDay31(list.get(30));
		monthCalendarService.updateDate(monthCalendar);
	}
}
