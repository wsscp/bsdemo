package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.model.VProcessEquip;
import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.bas.service.VProcessEquipService;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.eve.model.EventType;
import cc.oit.bsmes.eve.service.EventTypeService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bas/equipMESWWMapping")
public class EquipMESWWMappingController {

	@Resource
	private EquipMESWWMappingService equipMESWWMappingService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private VProcessEquipService vProcessEquipService; 
	@Resource private EventTypeService eventTypeService;
	
	@RequestMapping(produces = "text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "equipMESWWMapping");
        return "bas.equipMESWWMapping";
    }
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public TableView list(HttpServletRequest request, 
    						@RequestParam String sort,
    						@RequestParam int page, 
    						@RequestParam int start, 
    						@RequestParam int limit,
    						@RequestParam(required = false) String tagName,
    			    		@RequestParam(required = false) String equipCode) {

    	Map<String,Object> requestMap = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(tagName)){
			requestMap.put("tagName","%"+tagName+"%");
		}
		if(StringUtils.isNotBlank(equipCode)){
			requestMap.put("equipCode","%"+equipCode+"%");
		}

        //查询
        List<EquipMESWWMapping> list = equipMESWWMappingService.findByRequestMap(requestMap, start, limit, JSONArray.parseArray(sort, Sort.class));
        TableView tableView = new TableView();
        tableView.setRows(list);
        tableView.setTotal(equipMESWWMappingService.countByRequestMap(requestMap));
        return tableView;
    }
	
	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		EquipMESWWMapping equipMESWWMapping =  JSON.parseObject(jsonText, EquipMESWWMapping.class);
		equipMESWWMappingService.insert(equipMESWWMapping);
		equipMESWWMapping = equipMESWWMappingService.getById(equipMESWWMapping.getId());
		updateResult.addResult(equipMESWWMapping);
		return updateResult;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		EquipMESWWMapping equipMESWWMapping =  JSON.parseObject(jsonText, EquipMESWWMapping.class);
		equipMESWWMappingService.update(equipMESWWMapping);
		equipMESWWMapping = equipMESWWMappingService.getById(equipMESWWMapping.getId());
		updateResult.addResult(equipMESWWMapping);
		return updateResult;
	}
	
	/**
	 * 设备编码下拉款选项
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getEquipCode")		    
	public List<EquipInfo> getEquipCode(){
		User user = SessionUtils.getUser();
	    List<EquipInfo> result = equipInfoService.getByOrgCode(user.getOrgCode(),EquipType.MAIN_EQUIPMENT);
	    return result;
	}
	
	@ResponseBody
	@RequestMapping(value="getParmCode/{equipCode}")		    
	public List<VProcessEquip> getParmCode(@PathVariable String equipCode){
	    List<VProcessEquip> result = vProcessEquipService.getItemCode(equipCode);
	    return result;
	}
	
	@RequestMapping(value="checkExist/{tagName}",method = RequestMethod.GET)
	@ResponseBody
	public boolean checkUnique(@PathVariable String tagName){
		boolean result = false;
		EquipMESWWMapping equipMESWWMapping = equipMESWWMappingService.getByTagName(tagName);
		if(equipMESWWMapping!=null){
			result = true;
		}
		return result;
	}
	
	@RequestMapping(value="checkExist/{equipCode}/{parmCode}",method = RequestMethod.GET)
	@ResponseBody
	public boolean checkUnique(@PathVariable String equipCode,@PathVariable String parmCode){
		boolean result = false;
		EquipMESWWMapping equipMESWWMapping = equipMESWWMappingService.getByEquipCodeAndParmCode(equipCode,parmCode);
		if(equipMESWWMapping!=null){
			result = true;
		}
		return result;
	}
	
	@RequestMapping(value = "getEventType", method = RequestMethod.GET)
	@ResponseBody
	public List<EventType> getEventType() {
		List<EventType> eventList = eventTypeService.getAll();
		return eventList;
	}
	
}
