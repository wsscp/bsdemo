package cc.oit.bsmes.fac.action;


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
@RequestMapping("/fac/equipFaultManage")
public class EquipFaultManageController {
	@Resource private EventInformationService eventInformationService;
	@Resource private EventProcessLogService eventProcessLogService;
	@Resource private EquipInfoService equipInfoService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "fac");
        model.addAttribute("submoduleName", "equipFaultManage");
        return "fac.equipFaultManage"; 
    }
	@RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(HttpServletRequest request,@ModelAttribute EventInformation param, @RequestParam String sort, 
    		@RequestParam int page, @RequestParam int start, @RequestParam int limit)  {
		param.setOrgCode(SessionUtils.getUser().getOrgCode());
		param.setEventTitle("设备异常");
		List<EventInformation> list = eventInformationService.getEquipEventInfo(param, start, limit, JSONArray.parseArray(sort, Sort.class));
		TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(eventInformationService.totalEquipEventInfo(param));
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
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("eventTitle", e.getEventTitle());
			map.put("eventContent", e.getEventContent());
			map.put("id", e.getId());
			String director;
			if("dg".equals(e.getMaintainPeople())){
				director = "电工";
			}else if("qg".equals(e.getMaintainPeople())){
				director = "钳工";
			}else{
				director = "其他";
			}
			map.put("director", director);
			map.put("eventStatus", e.getEventStatus());
			result.add(map);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="updateWarnShow",method=RequestMethod.POST)		    
	public JSONObject updateWarnShow(@RequestParam String jsonText){
		List<EventInformation> list = JSONArray.parseArray(jsonText, EventInformation.class);
		JSONObject result = new JSONObject();
		for(EventInformation eventInformation : list){
			eventInformation.setEventStatus(EventStatus.RESPONDED);
			EventProcessLog eventLog=new EventProcessLog();
			eventLog.setEventInfoId(eventInformation.getId());
			eventLog.setType(EventStatus.RESPONDED);
			eventLog.setOrgCode(SessionUtils.getUser().getOrgCode());
			eventProcessLogService.insert(eventLog);
			eventInformationService.update(eventInformation);
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
	@RequestMapping(value="getRegisterData" ,method = RequestMethod.POST)
	public JSON getRegisterData(@RequestParam String id){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("eve", eventInformationService.getRegisterData(id));
		return JSONArrayUtils.ajaxJsonResponse(true, "成功", map);
	}
	
	@ResponseBody
	@RequestMapping(value="getCheckData" ,method = RequestMethod.POST)
	public JSON getCheckData(@RequestParam String id){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("eve", eventInformationService.getCheckData(id));
		return JSONArrayUtils.ajaxJsonResponse(true, "成功", map);
	}
}
