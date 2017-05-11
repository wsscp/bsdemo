package cc.oit.bsmes.pla.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.oit.bsmes.pla.service.DataEditService;

/**
 * 数据测试
 * 
 * @author DingXintao
 */
@Controller
@RequestMapping("/pla/dataEdit")
public class DataEditController {
	@Resource
	private DataEditService dataEditService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "pla");
		model.addAttribute("submoduleName", "dataEdit");
		return "pla.dataEdit";
	}

	

}
