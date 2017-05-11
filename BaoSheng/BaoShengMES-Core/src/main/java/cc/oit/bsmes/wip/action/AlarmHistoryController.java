/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */ 
package cc.oit.bsmes.wip.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wwalmdb.model.AlarmHistory;
import cc.oit.bsmes.wwalmdb.service.AlarmHistoryService;

import com.alibaba.fastjson.JSONArray;

/**
 * TODO(描述类的职责)
 * <p>
 * AlarmHistoryController
 * </p>
 * 
 * @author DingXintao
 * @date 2014-12-12 11:20:48
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/alarmHistory")
public class AlarmHistoryController {
	
	@Resource
	private AlarmHistoryService alarmHistoryService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "alarmHistory");
		return "wip.alarmHistory";
    }
	
	@RequestMapping
    @ResponseBody
	public TableView list(HttpServletRequest request, AlarmHistory findParams,
			@RequestParam String sort,
    		@RequestParam int page, @RequestParam int start, @RequestParam int limit)  {
		findParams.setAlarmState("UNACK_ALM");
		List<AlarmHistory> list = alarmHistoryService.
    			find(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
    	TableView tableView = new TableView();
    	tableView.setRows(list);
		tableView.setTotal(alarmHistoryService.count(findParams));
    	return tableView;
    }
	
    
}
