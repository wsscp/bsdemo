package cc.oit.bsmes.wip.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.DailyReport;
import cc.oit.bsmes.wip.model.MonthReport;
import cc.oit.bsmes.wip.service.DailyReportService;
import cc.oit.bsmes.wip.service.MonthReportService;

/**
 * TODO(描述类的职责)
 * <p>
 * PersonalMonitorController
 * </p>
 * 
 * @author yezhiqiang
 * @date 2015-08-25 11:20:48
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/personalMonitor")
public class PersonalMonitorController {

	@Resource
	private DailyReportService dailyReportService;

	@Resource
	private MonthReportService monthReportService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "personalMonitor");
		return "wip.personalMonitor";
	}

	@ResponseBody
	@RequestMapping
	public TableView indexList(HttpServletRequest request,
			@RequestParam(required = false) String yearMonthDay,
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String userCode,
			@RequestParam(required = false) String shiftId,
			@RequestParam String sort, @RequestParam int page,
			@RequestParam int start, @RequestParam int limit) {
		Map<String, String> param = new HashMap<String, String>();
		// 查询
		if (!yearMonthDay.isEmpty()) {
			param.put("yearMonthDay", yearMonthDay);
		}
		if (!code.isEmpty()) {
			param.put("code", code);
		}
		if (!userCode.isEmpty()) {
			param.put("userCode", userCode);
		}
		if (!shiftId.isEmpty()) {
			param.put("shiftId", shiftId);
		}
		List<DailyReport> list = dailyReportService.getDailyReportByDate(param,start,limit);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(dailyReportService.countDailyReportByDate(param));
		return tableView;
	}

	@ResponseBody
	@RequestMapping(value = "monthReport", method = RequestMethod.GET)
	public TableView monthReport(HttpServletRequest request,
			@RequestParam(required = false) String yearMonth,
			@RequestParam(required = false) String code,
			@RequestParam(required = false) String userCode,
			@RequestParam String sort, @RequestParam int page,
			@RequestParam int start, @RequestParam int limit) {
		Map<String, String> param = new HashMap<String, String>();
		// 查询
		if (!yearMonth.isEmpty()) {
			param.put("yearMonth", yearMonth);
		}
		if (!code.isEmpty()) {
			param.put("code", code);
		}
		if (!userCode.isEmpty()) {
			param.put("userCode", userCode);
		}
		List<MonthReport> list = monthReportService.getMonthReportByDate(param);
		TableView tableView = new TableView();
		tableView.setRows(list);
		return tableView;
	}

	@ResponseBody
	@RequestMapping(value = "getMonthEquipCode", method = RequestMethod.GET)
	public List<MonthReport> findMonthEquips(HttpServletRequest request,
			@RequestParam String yearMonth, @RequestParam String sort,
			@RequestParam int page, @RequestParam int start,
			@RequestParam int limit) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("yearMonth", yearMonth);
		return monthReportService.getMonthReportByDate(param);
	}

	@ResponseBody
	@RequestMapping(value = "getMonthUserCode", method = RequestMethod.GET)
	public List<MonthReport> findMonthUsers(HttpServletRequest request,
			@RequestParam String yearMonth, @RequestParam String sort,
			@RequestParam int page, @RequestParam int start,
			@RequestParam int limit) {
		return monthReportService.getMonthUsersByDate(yearMonth);
	}
	
	@ResponseBody
	@RequestMapping(value = "getDailyEquipCode", method = RequestMethod.GET)
	public List<DailyReport> findDailyEquips(HttpServletRequest request,
			@RequestParam String yearMonthDay, @RequestParam String sort,
			@RequestParam int page, @RequestParam int start,
			@RequestParam int limit) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("yearMonthDay", yearMonthDay);
		return dailyReportService.getDailyEquipCodeByDate(param);
	}
	
	@ResponseBody
	@RequestMapping(value = "getDailyUserCode", method = RequestMethod.GET)
	public List<DailyReport> findDailyUsers(HttpServletRequest request,
			@RequestParam(required=false) String yearMonthDay, @RequestParam(required=false) String startDate,
			@RequestParam(required=false) String endDate,
			@RequestParam String sort,
			@RequestParam int page, @RequestParam int start,
			@RequestParam int limit) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (startDate != null) {
			param.put("startDate", DateUtils.convert(startDate));
		}
		if (endDate != null) {
			param.put("endDate", DateUtils.convert(endDate));
		}
		if(yearMonthDay != null){
			param.put("yearMonthDay", yearMonthDay);
		}
		return dailyReportService.getDailyReportByDate(param);
	}
	
	@ResponseBody
	@RequestMapping(value = "searchCreditCardList", method = RequestMethod.GET)
	public TableView searchCreditCardList(HttpServletRequest request,
			@RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate,
			@RequestParam(required = false) String queryType,
			@RequestParam(required = false) String shiftId,
			@RequestParam(required = false) String userCode,
			@RequestParam(required = false) String sort, @RequestParam int page,
			@RequestParam int start, @RequestParam int limit) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 查询
		if (!startDate.isEmpty()) {
			param.put("startDate", DateUtils.convert(startDate));
		}
		if (!endDate.isEmpty()) {
			param.put("endDate", DateUtils.convert(endDate));
		}
		if (!queryType.isEmpty()) {
			param.put("queryType", queryType);
		}
		if (!shiftId.isEmpty()) {
			param.put("shiftId", shiftId);
		}
		if (!userCode.isEmpty()) {
			param.put("userCode", userCode);
		}
		List<DailyReport> list = dailyReportService.searchCreditCardList(param,start,limit);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(dailyReportService.countCreditCardList(param));
		return tableView;
	}

}
