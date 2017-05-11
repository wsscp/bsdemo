package cc.oit.bsmes.wip.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.service.ReceiptService;

/**
 * 参数下发报表
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-24 下午5:48:15
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/receipt")
public class ReceiptController {

	@Resource
	private ReceiptService receiptService;
	@Resource
	private EquipInfoService equipInfoService;

	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "receipt");
        return "wip.receipt";
    }
	
    @RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(@ModelAttribute Receipt param, @RequestParam(required=false) Integer start, @RequestParam(required=false) Integer limit, @RequestParam(required = false) String sort) {
    	param.setOrgCode(receiptService.getOrgCode());
        List list = receiptService.find(param, start, limit, null);
        TableView tableView = new TableView();
        tableView.setRows(list);
        tableView.setTotal(receiptService.count(param));
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
