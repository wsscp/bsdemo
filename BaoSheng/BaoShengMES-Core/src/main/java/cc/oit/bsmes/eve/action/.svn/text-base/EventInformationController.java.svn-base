package cc.oit.bsmes.eve.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cc.oit.bsmes.common.mybatis.Sort;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.model.EventProcessLog;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.eve.service.EventProcessLogService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;


/**
 * 
 * 异常事件信息
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-21 下午5:47:09
 * @since
 * @version
 */
@Controller
@RequestMapping("/eve/eventInformation")
public class EventInformationController {
	@Resource private EventInformationService eventInformationService;
	@Resource private EventProcessLogService eventProcessLogService;
	@Resource private EquipInfoService equipInfoService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "eve");
        model.addAttribute("submoduleName", "eventInformation");
        return "eve.eventInformation"; 
    }
	@RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(HttpServletRequest request,@ModelAttribute EventInformation param, @RequestParam String sort, 
    		@RequestParam int page, @RequestParam int start, @RequestParam int limit)  {
//		param.setOrgCode(SessionUtils.getUser().getOrgCode());
		param.setOrgCode(eventInformationService.getOrgCode());
		param.setUserCode(SessionUtils.getUser().getUserCode());
		List<EventInformation> list = eventInformationService.getInfo(param, start, limit, JSONArray.parseArray(sort, Sort.class));
    	for(EventInformation e:list){
    		EquipInfo info = equipInfoService.getByCode(e.getEquipCode(), equipInfoService.getOrgCode());
    		if(info != null){
    			e.setEquipCode(info.getEquipAlias()+"("+info.getCode()+")");
    		}
    	}
		TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(eventInformationService.totalCount(param));
    	return tableView;
    }
	/**
	 * 
	 * @author leiwei
	 * @date 2014-2-25 下午2:41:58
	 * @return
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value="eventStatus",method=RequestMethod.GET)		    
	public JSONArray eventStatus(){
		JSONArray array = JSONArrayUtils.enumToJSON(EventStatus.class);
		return array;
	}
	@ResponseBody
	@RequestMapping(value="eventTitle",method=RequestMethod.GET)		    
	public List<EventInformation> eventTitle(){
		List<EventInformation> infos=eventInformationService.getDistinctTileByOrgCode(eventInformationService.getOrgCode());
	    return infos;
	}
	
	@ResponseBody
	@RequestMapping(value="equip",method=RequestMethod.GET)		    
	public List<Map> equip(){
//		EquipInfo info= new EquipInfo();
//		info.setOrgCode(info.getOrgCode());
//		info.setType(EquipType.MAIN_EQUIPMENT);
//		info.setOrgCode(equipInfoService.getOrgCode());
//		List<EquipInfo> result=equipInfoService.findByObj(info);
//		for(int i=0;i<result.size();i++){
//			System.out.println(i);
//    		EquipInfo infos=result.get(i);
//    		result.get(i).setName("["+infos.getCode().replace("_EQUIP", "")+"]  "+infos.getName().replace("_设备", ""));
//    		result.get(i).setCode(infos.getCode().replace("_EQUIP", ""));
//    	}
		List<Map> result = new ArrayList<Map>();
		List<String> codes = eventInformationService.getEquipCodes();
		for(String code : codes){
			if(code != null){
				Map<String,String> map = new HashMap<String,String>();
				EquipInfo info = equipInfoService.getByCode(code, equipInfoService.getOrgCode());
				if(info != null){
					map.put("code", code);
					map.put("name", "["+code+"]  "+info.getEquipAlias());
					result.add(map);
				}
			}
		}
	    return result;
	}
	
	@ResponseBody
	@RequestMapping(value="update",method = RequestMethod.GET)
	public void update(@RequestParam String jsonText){
		EventInformation eventInfo=JSON.parseObject(jsonText, EventInformation.class);
		eventInfo.setModifyUserCode(SessionUtils.getUser().getUserCode());
		eventInfo.setEventStatus(EventStatus.COMPLETED);
		EventProcessLog eventLog=new EventProcessLog();
		eventLog.setEventInfoId(eventInfo.getId());
		eventLog.setType(EventStatus.COMPLETED);
		List<EventProcessLog> tmp = eventProcessLogService.findByObj(eventLog);
		if(tmp != null && tmp.size() > 1){
			return; // 若已经处理直接返回
		}
		eventLog.setOrgCode(SessionUtils.getUser().getOrgCode());
		eventInformationService.update(eventInfo);
		eventProcessLogService.insert(eventLog);
	}
	
	@ResponseBody
	@RequestMapping(value="getWarnShow",method=RequestMethod.GET)		    
	public List<Map> getWarnShow(){
		List<Map> result = new ArrayList<Map>();
		List<EventInformation> list = eventInformationService.getWarnShow();
		for(EventInformation e : list){
			Map<String,String> map = new HashMap<String,String>();
			map.put("eventTitle", e.getEventTitle());
			map.put("eventContent", e.getEventContent());
			map.put("id", e.getId());
			map.put("director", e.getMaintainPeople());
			result.add(map);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="updateWarnShow",method=RequestMethod.POST)		    
	public JSONObject updateWarnShow(@RequestParam String jsonText){
		JSONArray array = JSONArray.parseArray(jsonText);
		JSONObject result = new JSONObject();
		for(int i=0;i<array.size();i++){
			JSONObject jo = array.getJSONObject(i);
			EventInformation e = eventInformationService.getById(jo.getString("id"));
			e.setEventStatus(EventStatus.RESPONDED);
			eventInformationService.update(e);
		}
		result.put("succcess", true);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="save",method = RequestMethod.GET)
	public void save(@RequestParam String jsonText){
		EventInformation eventInfo=JSON.parseObject(jsonText, EventInformation.class);
		eventInfo.setEventStatus(EventStatus.PENDING);
		EventProcessLog eventLog=new EventProcessLog();
		eventLog.setEventInfoId(eventInfo.getId());
		eventLog.setType(EventStatus.PENDING);
		List<EventProcessLog> tmp = eventProcessLogService.findByObj(eventLog);
		if(tmp != null && tmp.size() > 1){
			return; // 若已经处理直接返回
		}
		eventLog.setOrgCode(SessionUtils.getUser().getOrgCode());
		eventInformationService.update(eventInfo);
		eventProcessLogService.insert(eventLog);
	}
	@ResponseBody
	@RequestMapping(value="getEquipEventPending")
	public JSON getEquipEventPending(){
		Map<String,Object> map = new HashMap<String, Object>();
		List<EventInformation> list = eventInformationService.getEquipEventPending();
		map.put("list", list);
		return JSONArrayUtils.ajaxJsonResponse(true, "", map);
	}
}
