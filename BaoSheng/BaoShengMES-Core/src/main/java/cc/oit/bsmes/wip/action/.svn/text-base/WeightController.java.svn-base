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

import cc.oit.bsmes.wip.service.WorkOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-2-18 下午3:08:15
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/weight")
public class WeightController {
	@Resource
	private WorkOrderService workOrderService;
	
	@RequestMapping(produces = "text/html", method = RequestMethod.GET)
	public String index(@RequestParam("pageName") String pageName, Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "weight");
		return "wip.weight."+pageName; 
	}
	
	@ResponseBody
	@RequestMapping(value="/productWeightSave", method = RequestMethod.POST)
	public void productWeightSave(@RequestParam String workOrderNO,@RequestParam String userCode,@RequestParam String weight, Model model) {
		workOrderService.productWeightSave(workOrderNO, userCode, weight);
	}
	@ResponseBody
	@RequestMapping(value="/scrapWeightSave", method = RequestMethod.POST)
	public void scrapWeightSave(@RequestParam String matCode,@RequestParam String workOrderNO,@RequestParam String userCode,@RequestParam String weight, Model model) {
		workOrderService.scrapWeightSave(matCode, workOrderNO, userCode, weight);
	}
	@ResponseBody
	@RequestMapping(value="/unitWeightSave", method = RequestMethod.POST)
	public void unitWeightSave(@RequestParam String matCode,@RequestParam String workOrderNO,@RequestParam String userCode,@RequestParam String weight, Model model) {
		workOrderService.unitWeightSave(workOrderNO, userCode, weight);
	}
}
