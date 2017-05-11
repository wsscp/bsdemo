package cc.oit.bsmes.wip.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;

@Controller
@RequestMapping("/wip/statusChart")
public class StatusChartController {

	@Resource
	private EquipInfoService equipInfoService;
	@RequestMapping(value = "{equipCode}", produces = "text/html", method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, @PathVariable String equipCode, 
    		@RequestParam String title, Model model) {
        model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "statusChart");
        model.addAttribute("equipCode", equipCode);
        EquipInfo equipInfo =equipInfoService.getByCode(equipCode, equipInfoService.getOrgCode());
        model.addAttribute("equipName", equipInfo.getName());
        if(StringUtils.equals(title, "加工时间统计")){
        	return "wip.statusChart.scheduler";
        }else if(StringUtils.equals(title, "加工时间分析")){
        	return "wip.statusChart.chartLine";
        }else{
        	return "wip.statusChart.chartOEE";
        }
    }
}
