package cc.oit.bsmes.wip.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.ApplyMat;
import cc.oit.bsmes.wip.service.ApplyMatService;

@Controller
@RequestMapping("/wip/applyMaterials")
public class ApplyMaterialsController {

	@Resource
	private ApplyMatService applyMatService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "applyMaterials");
		return "wip.applyMaterials";
    }
	
	@RequestMapping
    @ResponseBody
    public TableView list(HttpServletRequest request, ApplyMat findParams,
			@RequestParam String sort,
    		@RequestParam int page, @RequestParam int start, @RequestParam int limit){
		TableView tableView = new TableView();
		tableView.setRows(applyMatService.findApplyMat(findParams,start,limit));
		tableView.setTotal(applyMatService.countApplyMat(findParams));
		return tableView;
	}
	@ResponseBody
	@RequestMapping(value = "update",method=RequestMethod.POST)
	public JSON update(@RequestParam String id,@RequestParam String issueQuntity){
		ApplyMat mat = applyMatService.getById(id);
		mat.setIssueQuntity(Double.valueOf(issueQuntity));
		mat.setStatus(MaterialStatus.MAT_GETED);
		applyMatService.update(mat);
		return JSONArrayUtils.ajaxJsonResponse(true, "更新成功!!");
	}
	
	@ResponseBody
	@RequestMapping(value="getMatName",method=RequestMethod.GET)
	public List<ApplyMat> getMatName(){
		return applyMatService.getMatName();
	}
	
	
}
