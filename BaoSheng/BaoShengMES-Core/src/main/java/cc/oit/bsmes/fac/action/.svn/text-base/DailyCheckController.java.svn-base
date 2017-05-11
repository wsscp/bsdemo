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

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.MaintainRecord;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.MaintainRecordService;

@Controller
@RequestMapping("/fac/dailyCheck")
public class DailyCheckController {

	@Resource
	private MaintainRecordService maintainRecordService;
	@Resource
	private EquipInfoService equipInfoService;

	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "fac");
        model.addAttribute("submoduleName", "dailyCheck");
        return "fac.dailyCheck";
    }
	
    @RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(@ModelAttribute MaintainRecord  param, @RequestParam(required=false) Integer start, @RequestParam(required=false) Integer limit, @RequestParam(required = false) String sort) {
        List list = maintainRecordService.getDailyCheck(param, start, limit);
        TableView tableView = new TableView();
        tableView.setRows(list);
        tableView.setTotal(maintainRecordService.countDailyChecks(param));
        return tableView;
    }
    @ResponseBody
	@RequestMapping(value="equip",method=RequestMethod.GET)		    
	public List<EquipInfo> equip(){
	    EquipInfo info= new EquipInfo();
		info.setOrgCode(info.getOrgCode());
		info.setType(EquipType.MAIN_EQUIPMENT);
		info.setOrgCode(equipInfoService.getOrgCode());
		List<EquipInfo> result=equipInfoService.findByObj(info);
		for(int i=0;i<result.size();i++){
    		EquipInfo infos=result.get(i);
    		result.get(i).setName("["+infos.getCode().replace("_EQUIP", "")+"]  "+infos.getName().replace("_设备", ""));
    	}
	    return result;
	}
}
