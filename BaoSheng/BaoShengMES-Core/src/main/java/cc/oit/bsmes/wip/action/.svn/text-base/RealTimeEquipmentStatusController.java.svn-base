package cc.oit.bsmes.wip.action;

import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wip/realTimeEquipmentStatus")
public class RealTimeEquipmentStatusController {
  
  @Resource private EquipInfoService equipInfoService;
 
  private static String[] equipStatus={"IN_PROGRESS","IN_DEBUG","CLOSED","IDLE","ERROR","IN_MAINTAIN"};
  private static int total=0;
    @RequestMapping(produces = "text/html")
      public String index(Model model) {
          model.addAttribute("moduleName", "wip");
          model.addAttribute("submoduleName", "realTimeEquipmentStatus"); 
          return "html_wip.realTimeEquipmentStatus";
      }
    
    
	@RequestMapping(value="realSearch",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> realSearch(@RequestParam(required=false) String status){
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(status)) {
			param.put("equipStatus", status.trim().split(","));
		}
		param.put("orgCode",equipInfoService.getOrgCode());
		List<EquipInfo> list=equipInfoService.getEquipLine(param);
		if(equipStatus.length==6){
       	  	total=list.size();
        }
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("equipInfos",list);
		map.put("totals", total);
		return map;
	}
}
