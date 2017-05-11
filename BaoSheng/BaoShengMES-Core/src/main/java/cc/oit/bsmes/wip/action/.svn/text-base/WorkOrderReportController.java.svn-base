package cc.oit.bsmes.wip.action;

import cc.oit.bsmes.bas.model.WorkShift;
import cc.oit.bsmes.bas.service.WorkShiftService;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.WorkOrderReport;
import cc.oit.bsmes.wip.service.WorkOrderReportService;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TODO(描述类的职责)
 * <p>WorkOrderReportController</p>
 * @author DingXintao
 * @date 2014-7-1 11:20:48
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/workOrderReport")
public class WorkOrderReportController {
	
	@Resource private WorkOrderReportService workOrderReportService;
	
	@Resource private WorkShiftService workShiftService;
	
	@RequestMapping(value = "/{path}", produces="text/html")
    public String index(Model model, @PathVariable String path) {
        model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "workOrderReport");
        return "wip.workOrderReport." + path; 
    }
	
	/**
     * <p>编织屏蔽生产记录 显示列表</p> 
     * @author DingXintao
     * @date 2014-8-1 10:22:48
     * @return
     * @see
     */
    @ResponseBody
    @RequestMapping(value = "/{path}")
    public TableView indexList(HttpServletRequest request, @PathVariable String path, @ModelAttribute WorkOrderReport findParams, 
    		@RequestParam String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit)  {
    	if(StringUtils.isBlank(findParams.getReportDate())){
    		findParams.setReportDate(null);
    	}
    	if(StringUtils.isBlank(findParams.getShiftId())){
    		findParams.setShiftId(null);
    	}
    	findParams.setReportType(path);
    	findParams.setOrgCode(SessionUtils.getUser().getOrgCode());
    	List<WorkOrderReport> list = workOrderReportService.find(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
    	Integer count = workOrderReportService.count(findParams);
    	TableView tableView = new TableView();
    	tableView.setRows(list);
        tableView.setTotal(count);
    	return tableView;
    }
    
    /**
     * <p>导出报表</p> 
     * @author DingXintao
     * @date 2014-8-4 16:15:48
     * @param request 请求
     * @param response 响应
     * @param path 报表类型/路径
     * @param queryDate 生产日期
     * @param shiftId 班次
     * @throws Exception 
     * @see
     */
	@RequestMapping(value="/workOrderReportExport/{path}/{queryDate}/{fileName}/{shiftId}", method = RequestMethod.GET)
	@ResponseBody
    public void reportExport(HttpServletRequest request, HttpServletResponse response,
    		@PathVariable String path, @PathVariable String queryDate, @PathVariable String fileName, @PathVariable String shiftId) throws Exception {
		
		workOrderReportService.reportExport(request, response, path, queryDate, fileName, shiftId);

    }
	
	/**
     * <p>列表下拉框：班次</p> 
     * @author DingXintao
     * @date 2014-8-11 14:15:48
     * @param request 请求
     * @return List<WorkShift> 
     * @see
     */
	@RequestMapping(value="/getWorkShiftCombo", method = RequestMethod.GET)
	@ResponseBody
    public List<WorkShift> getWorkShiftCombo(HttpServletRequest request) {
		
		return workShiftService.findByObj(new WorkShift());

    }
	
	
    
}
