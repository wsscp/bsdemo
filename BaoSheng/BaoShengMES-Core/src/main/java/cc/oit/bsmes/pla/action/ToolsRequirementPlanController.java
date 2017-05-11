package cc.oit.bsmes.pla.action;

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.pla.model.ToolsRequirementPlan;
import cc.oit.bsmes.pla.service.ToolsRequirementPlanService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * 辅助工具需求清单
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-1-22 下午6:02:34
 * @since
 * @version
 */
@Controller
@RequestMapping("/pla/toolsRequirementPlan")
public class ToolsRequirementPlanController {
	
	@Resource 
	private ToolsRequirementPlanService toolsRequirementPlanService;
	@Resource
	private EquipInfoService equipInfoService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "pla");
        model.addAttribute("submoduleName", "toolsRequirementPlan");
        return "pla.toolsRequirementPlan"; 
    }
	
	@RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(HttpServletRequest request,@ModelAttribute ToolsRequirementPlan param,
    		@RequestParam int page, @RequestParam int start, @RequestParam int limit, @RequestParam(required = false) String sort)  {
		List list = toolsRequirementPlanService.find(param, start, limit, JSONArray.parseArray(sort, Sort.class));
    	TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(toolsRequirementPlanService.count(param));
    	return tableView;
    }
	 @ResponseBody
	 @RequestMapping(value="process",method=RequestMethod.GET)
	 public List<ToolsRequirementPlan> findEquipListByCombobox(){
		 String orgCode=SessionUtils.getUser().getOrgCode();
	      List<ToolsRequirementPlan> result = toolsRequirementPlanService.findByOrgCode(orgCode);
	      return result;
	 }
	 @ResponseBody
	 @RequestMapping(value="equipment",method=RequestMethod.GET)
	 public List<EquipInfo> findEquipsByOrgCode(){
		  String orgCode=SessionUtils.getUser().getOrgCode();
	      List<EquipInfo> result = equipInfoService.getByOrgCode(orgCode,EquipType.TOOLS);
	      return result;
	  }
}
