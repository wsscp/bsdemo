package cc.oit.bsmes.job.controller;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.job.base.model.SchedulerLog;
import cc.oit.bsmes.job.service.SchedulerLogService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/job/schedulerLog")
public class SchedulerLogController {
	@Resource private SchedulerLogService schedulerLogService;
	
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "job");
		model.addAttribute("submoduleName", "schedulerLog");
		return "job.schedulerLog";
    }
	
	@RequestMapping
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public TableView list(@ModelAttribute SchedulerLog param, @RequestParam(required = false) String sort, @RequestParam(required=false) Integer start, @RequestParam(required=false) Integer limit) {
	    param.setOrgCode(SessionUtils.getUser().getOrgCode());
	    List list = schedulerLogService.find(param, start, limit, JSONArray.parseArray(sort, Sort.class));
	    TableView tableView = new TableView();
	    tableView.setRows(list);
	    tableView.setTotal(schedulerLogService.count(param));
	    return tableView;
	 }
}
