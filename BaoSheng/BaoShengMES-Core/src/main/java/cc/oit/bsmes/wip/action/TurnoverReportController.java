package cc.oit.bsmes.wip.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.wip.model.TurnOverReport;
import cc.oit.bsmes.wip.service.TurnOverReportService;

import com.alibaba.fastjson.JSONArray;

/**
 * 
 * @author rongyd
 *
 */
 
@Controller
@RequestMapping("/wip/turnoverReport")
public class TurnoverReportController {

	@Resource
	private TurnOverReportService turnOverReportService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "turnoverReport");
		return "wip.turnoverReport";
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public TableView list(@RequestParam(required = false) String sort,
			@RequestParam(required = false) Integer start,
			@RequestParam(required = false) String processCode,
			@RequestParam(required = false) Integer limit,
			@RequestParam(required = false) String shiftDate,
			@RequestParam int page,
			@RequestParam(required = false) String shiftName,
			@RequestParam(required = false) String equipCode,
			@RequestParam(required = false) String userCode) {
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		TableView tableView = new TableView();
		List<TurnOverReport> lists = turnOverReportService.getTurnoverReport(
				processCode, shiftDate, shiftName, equipCode, userCode,start,limit,sortArray);
		tableView.setRows(lists);
		tableView.setTotal(turnOverReportService.getCountTurnoverReport(processCode, shiftDate, 
				shiftName, equipCode, userCode));
		return tableView;
	}
	
	
	@ResponseBody
	@RequestMapping(value="getProcessName",method = RequestMethod.GET)
	public List<TurnOverReport> getProcessName(){
	    List<TurnOverReport> processNames=turnOverReportService.getProcessName();
		return processNames;
	}
	
	@ResponseBody
	@RequestMapping(value="getEquipCode",method = RequestMethod.GET)
	public List<TurnOverReport> getEquipCode(){
	    List<TurnOverReport> equipLists=turnOverReportService.getEquipCode();
		return equipLists;
	}	
	
	@ResponseBody
	@RequestMapping(value="getUserName/{query}",method = RequestMethod.GET)
	public List<TurnOverReport> getUserName(@PathVariable String query){
		try {
			query=new String(query.getBytes("ISO8859_1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	    List<TurnOverReport> userNames=turnOverReportService.getUserName(query);
		return userNames;
	}
}
