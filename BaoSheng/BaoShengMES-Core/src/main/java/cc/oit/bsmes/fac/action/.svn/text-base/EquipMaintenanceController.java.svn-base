package cc.oit.bsmes.fac.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.EquipMaintenance;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.EquipMaintenanceService;
import cc.oit.bsmes.wip.model.Report;

/**
 * 
 * 设备维修统计
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-6-23 下午4:44:04
 * @since
 * @version
 */
@Controller
@RequestMapping("/fac/equipMaintenance")
public class EquipMaintenanceController {

	@Resource private EquipMaintenanceService equipMaintenanceService;
	@Resource private EquipInfoService equipInfoService;
	
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "fac");
        model.addAttribute("submoduleName", "equipMaintenance");
        return "fac.equipMaintenance"; 
    }
	@RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(HttpServletRequest request,@ModelAttribute EquipMaintenance param, @RequestParam String sort, 
    		@RequestParam int page, @RequestParam int start, @RequestParam int limit)  {
		List list = equipMaintenanceService.find(param, start, limit, null);
    	TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(equipMaintenanceService.count(param));
    	return tableView;
    }
	
	 @ResponseBody
	 @RequestMapping(value="equipment",method=RequestMethod.GET)
	 public List<EquipInfo> equipment(){
	    	List<EquipInfo> result = equipInfoService.getByOrgCode(SessionUtils.getUser().getOrgCode(),EquipType.PRODUCT_LINE);
	    	for(int i=0;i<result.size();i++){
	    		EquipInfo info=result.get(i);
	    		result.get(i).setName("["+info.getCode().replace("_EQUIP", "")+"]  "+info.getEquipAlias());
	    	}
	    	EquipInfo equip=new EquipInfo();
	    	equip.setName("全部");
	    	result.add(0, equip);
	    	return result;
	  }
	 
	 	@ResponseBody
		@RequestMapping(value = "queryEquipEvent/{code}", method = RequestMethod.GET)
		public List<EquipMaintenance> queryEquipEvent(@PathVariable String code) {
	 		List<EquipMaintenance> list = equipMaintenanceService.queryEquipEvent(code);
			return list;
		}
}
