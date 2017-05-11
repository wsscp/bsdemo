package cc.oit.bsmes.eve.action;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.EventProcesserType;
import cc.oit.bsmes.eve.model.EventProcesser;
import cc.oit.bsmes.eve.service.EventProcesserService;

/**
 * 
 * 事件关联处理人
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-18 下午5:35:41
 * @since
 * @version
 */
@Controller
@RequestMapping("/eve/eventProcesser")
public class EventProcesserController {
	@Resource private EventProcesserService eventProcesserService;
	
	/**
	 * 
	 * 删除事件关联处理人
	 * @author leiwei
	 * @date 2014-2-18 下午5:36:40
	 * @param id
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value="deleteEventProcesser",method = RequestMethod.GET)
	public void deleteEventProcess(@RequestParam String id){
		eventProcesserService.deleteById(id);
	}
	/**
	 * 
	 * <p>TODO(获取事件处理人)</p> 
	 * @author leiwei
	 * @date 2014-2-18 下午5:45:40
	 * @param eventProcessId
	 * @return
	 * @see
	 */
	@RequestMapping(value="getPerson/{eventProcessId}",method=RequestMethod.GET)	
	@ResponseBody
	public List<EventProcesser> getPerson(@PathVariable String eventProcessId){
		List<EventProcesser> eventProcesser=eventProcesserService.getByEventProcessId(eventProcessId);
		return eventProcesser;
	}
	/**
	 * 	
	 * <p>添加</p> 
	 * @author leiwei
	 * @date 2014-2-20 下午2:48:50
	 * @param id
	 * @param processer
	 * @param type
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value="insert",method = RequestMethod.POST)
	public String insert(@RequestParam String status,@RequestParam String processer,@RequestParam String eventProcessId ,@RequestParam String eventTypeId){
		EventProcesser event=new EventProcesser();
		event.setId(UUID.randomUUID().toString());
		event.setProcesser(processer);
		event.setEventProcessId(eventProcessId);
		event.setEventTypeId(eventTypeId);
		if("0".equals(status)){
			event.setType(EventProcesserType.USER);
		}else{
			event.setType(EventProcesserType.ROLE);
		}
		List<EventProcesser>eventProcesser=eventProcesserService.findByObj(event);
		if(eventProcesser.size()>0){
			return "processerExist";
		}else{
			eventProcesserService.insert(event);
			return "insert";
		}
	}
}
