package cc.oit.bsmes.fac.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.fac.model.MaintainTemplate;
import cc.oit.bsmes.fac.service.MaintainTemplateService;

/**
 * @author chanedi
 */
@Controller
@RequestMapping("/fac/maintainTemplate")
public class MaintainTemplateController {

    @Resource
    private MaintainTemplateService maintainTemplateService;

    @RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "fac");
        model.addAttribute("submoduleName", "maintainTemplate");
        return "fac.maintainTemplate";
    }
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(@ModelAttribute MaintainTemplate param, @RequestParam(required=false) Integer start, @RequestParam(required=false) Integer limit, @RequestParam(required = false) String sort) {
    	param.setOrgCode(maintainTemplateService.getOrgCode());
        List list = maintainTemplateService.find(param, start, limit, null);
        TableView tableView = new TableView();
        tableView.setRows(list);
        tableView.setTotal(maintainTemplateService.count(param));
        return tableView;
    }

	/**
	 * 设备维护模版 新增
	 * 
	 * */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public UpdateResult save(MaintainTemplate maintainTemplate) {
		UpdateResult updateResult = new UpdateResult();
		maintainTemplateService.insert(maintainTemplate);
		updateResult.addResult(maintainTemplate);
		return updateResult;
	}

}