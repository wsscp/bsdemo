package cc.oit.bsmes.wip.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.eve.model.EventType;
import cc.oit.bsmes.eve.service.EventTypeService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.wip.model.EventTrace;
import cc.oit.bsmes.wip.service.EventTraceService;

@Controller
@RequestMapping("/wip/eventTrace")
public class EventTraceController {
	@Resource private EventTraceService eventTraceService;
	@Resource private EventTypeService eventTypeService;
	@Resource private EquipInfoService equipInfoService;
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "eventTrace");
        return "wip.eventTrace"; 
    }
	
    @ResponseBody
    @RequestMapping
    public TableView list(HttpServletRequest request, @RequestParam String sort, 
			@RequestParam int page, @RequestParam int start,
			@RequestParam int limit) throws ParseException {
       	TableView tableView = new TableView();
       	Map<String,Object> findParams=new HashMap<String,Object>();
       	if(StringUtils.isNotEmpty(request.getParameter("eventTitle"))){
       		findParams.put("eventTitle",request.getParameter("eventTitle"));
       	}
       	if(StringUtils.isNotEmpty(request.getParameter("processCode"))){
       		findParams.put("processCode",request.getParameter("processCode"));
       	}
       	if(StringUtils.isNotEmpty(request.getParameter("equipCode"))){
       		findParams.put("equipCode",request.getParameter("equipCode"));
       	}
       	
       	/*while(request.getParameterNames().hasMoreElements()){
       		String name=(String) request.getParameterNames().nextElement();
       		if(name.equals("start")||name.equals("limit")){
				continue;
			}
       		if(StringUtils.isNotEmpty(request.getParameter(name))){
       			findParams.put(name, request.getParameter(name));
       		}
       	}*/
       	findParams.put("start",start);
		findParams.put("end",start+limit);
    	List<EventTrace> lists =  eventTraceService.findAllEventTrace(findParams);
    	int count=eventTraceService.countEventTrace(findParams);
    	tableView.setRows(lists);
    	tableView.setTotal(count);
    	return tableView;
    }
    
	@RequestMapping(value="getEventType",method=RequestMethod.GET)	
	@ResponseBody
	public List<EventType> getEventType(){
		List<EventType> eventList=eventTypeService.getAll();
		EventType event=new EventType();
		event.setCode("");
		event.setName("所有");
		eventList.add(0, event);
		return eventList;
	}
	
	
	@RequestMapping(value="getProcess")	
	@ResponseBody
	public List<EventTrace> getProcess(){
		List<EventTrace> processLists=eventTraceService.getProcess();
		return processLists;
	}
	
	 @ResponseBody
	 @RequestMapping(value="equip/{path}",method=RequestMethod.GET)
	 public List<EquipInfo> equip(@PathVariable("path") String processCode){
		  Map<String,Object> params=new HashMap<String,Object>();
		  if(!processCode.equals("1")){
			  params.put("processCode", processCode);
		  }
	      return equipInfoService.getSpecificEquip(params);
	  }
}
