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

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.WorkOrderOperateLog;
import cc.oit.bsmes.wip.service.WorkOrderOperateLogService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * TODO(描述类的职责)
 * <p>WorkOrderOperateLogController</p>
 * @author DingXintao
 * @date 2014-7-1 11:20:48
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/workOrderOperateLog")
public class WorkOrderOperateLogController {
	
	@Resource
	private WorkOrderOperateLogService workOrderOperateLogService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "workOrderOperateLog");
        return "wip.workOrderOperateLog"; 
    }
	
	@RequestMapping
    @ResponseBody
    public TableView list(HttpServletRequest request, WorkOrderOperateLog findParams, @RequestParam String sort, 
    		@RequestParam int page, @RequestParam int start, @RequestParam int limit)  {
		if(null != findParams.getWorkOrderNo())
			findParams.setWorkOrderNo("%"+findParams.getWorkOrderNo()+"%");
		if(null != findParams.getEquipCode())
			findParams.setEquipCode("%"+findParams.getEquipCode()+"%");
        findParams.setOrgCode(workOrderOperateLogService.getOrgCode());
		
    	List<WorkOrderOperateLog> list = workOrderOperateLogService.
    			find(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
    	TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(workOrderOperateLogService.count(findParams));
    	return tableView;
    }
	
    
}
