package cc.oit.bsmes.wip.action;

import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.MaterialTrace;
import cc.oit.bsmes.wip.service.MaterialTraceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * TODO(原材物料料追溯)
 * <p style="display:none">modifyRecord</p>
 * @author Administrator
 * @date 2014-3-5 下午2:36:11
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/materialTrace")
public class MaterialTraceController {
	@Resource private MaterialTraceService materialTraceService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "materialTrace");
        return "wip.materialTrace"; 
    }
	
    @ResponseBody
    @RequestMapping
    public TableView list(HttpServletRequest request,@ModelAttribute MaterialTrace param, @RequestParam String sort, 
    		@RequestParam int page, @RequestParam int start, @RequestParam int limit)  {
       	TableView tableView = new TableView();
        param.setOrgCode(materialTraceService.getOrgCode());
    	List<MaterialTrace> list =materialTraceService.find(param, start, limit, null);
    	tableView.setRows(list);
    	tableView.setTotal(materialTraceService.count(param));
    	return tableView;
    }
}
