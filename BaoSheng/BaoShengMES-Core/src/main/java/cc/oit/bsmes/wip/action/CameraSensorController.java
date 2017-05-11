package cc.oit.bsmes.wip.action;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;


@Controller
@RequestMapping("/wip/cameraSensor")
public class CameraSensorController {
	@Resource
	private EquipInfoService equipInfoService;
	
	@RequestMapping(produces="text/html")
    public String index(HttpServletRequest request,Model model) {
		String equipCode = request.getParameter("equipCode");
		EquipInfo equipInfo = equipInfoService.getByCode(equipCode, SessionUtils.getUser().getOrgCode());
		String mrl = equipInfo.getMrl();
        model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "cameraSensor");

        if("null".equals(mrl)){
        	mrl = "172.17.6.131";
        }

        model.addAttribute("mrl", mrl);
        return "html_wip.cameraSensor"; 
    }
}
