package cc.oit.bsmes.eve.action;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import cc.oit.bsmes.common.constants.ProcessType;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.eve.model.EventProcess;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.eve.service.EventProcessService;

/**
 * 
 * 事件处理流程
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-17 下午1:41:46
 * @since
 * @version
 */
@Controller
@RequestMapping("/eve/eventProcess")
public class EventProcessController {
	@Resource private EventProcessService eventProcessService;
	@Resource private EventInformationService eventInformationService;
	/**
	 * 
	 * <p>删除某条流程记录</p> 
	 * @author leiwei
	 * @date 2014-2-17 下午1:42:11
	 * @param jsonText 事件流程id
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value="deleteEventProcess",method = RequestMethod.GET)
	public String deleteEventProcess(@RequestParam String id,@RequestParam String eventTypeId){
		int count=eventInformationService.getEventStatusByEventProcessId(id);
		if(count>0){
			return "unComplete";
		}else{
			eventProcessService.deleteEventProcessById(id,eventTypeId);
			return "success";
		}
		
	}
	
	
	/**
	 * 
	 * <p>TODO(事件处理流程)</p> 
	 * @author leiwei
	 * @date 2014-2-17 下午1:30:11
	 * @param eventTypeId
	 * @return
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value="getProcess/{eventTypeId}",method=RequestMethod.GET)	
	public List<EventProcess> getProcess(@PathVariable String eventTypeId){
		List<EventProcess> eventProcess=eventProcessService.getByEventTypeId(eventTypeId);
		return eventProcess;
	}
	/**
	 * 
	 * <p>添加或修改事件处理流程</p> 
	 * @author leiwei
	 * @date 2014-2-19 下午6:18:46
	 * @param id
	 * @param eventTypeId
	 * @param processType
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value="insertOrUpdate",method = RequestMethod.POST)
	public void insertOrUpdate(@RequestParam String id,@RequestParam String eventTypeId,@RequestParam String processType,@RequestParam String stepInterval){
		EventProcess event=getEventProcess(id,eventTypeId,processType,stepInterval);
		if(StringUtils.isBlank(id)){
			Integer processSeq= eventProcessService.getProcesSeqByEventTypeId(eventTypeId);
			event.setId(UUID.randomUUID().toString());
			event.setStatus(true);
			event.setProcessSeq(processSeq==null?1:processSeq+1);
			eventProcessService.insert(event);
		}else{
			Integer processSeq=eventProcessService.getProcessSeqById(id);
			event.setProcessSeq(processSeq);
			eventProcessService.update(event);
		}
	}
	
	/**
	 * 
	 * <p>TODO(事件处理类型)</p> 
	 * @author leiwei
	 * @date 2014-2-21 上午10:50:11
	 * @param eventTypeId
	 * @return
	 * @see
	 */
	@RequestMapping(value="processType",method=RequestMethod.GET)	
	@ResponseBody
	public JSONArray processType(){
		JSONArray array = JSONArrayUtils.enumToJSON(ProcessType.class);
		return array;
	}
	private EventProcess getEventProcess(String id,String eventTypeId,String processType,String stepInterval){
		EventProcess event=new EventProcess();
		if(StringUtils.isNotBlank(id)){
			event.setId(id);
		}
		if(StringUtils.isNotBlank(eventTypeId)){
			event.setEventTypeId(eventTypeId);
		}
		if(StringUtils.isNotBlank(stepInterval)){
			event.setStepInterval(Double.parseDouble(stepInterval));
		}
		if(StringUtils.isNotBlank(processType)){
			if(processType.equals(ProcessType.SMS.name())){
				event.setProcessType(ProcessType.SMS);
			}else if(processType.equals(ProcessType.EMAIL.name())){
				event.setProcessType(ProcessType.EMAIL);
			}else if(processType.equals(ProcessType.ALL.name())){
				event.setProcessType(ProcessType.ALL);
			}else if(processType.equals(ProcessType.SMSPLUSMESSAGE.name())){
				event.setProcessType(ProcessType.SMSPLUSMESSAGE);
			}else if(processType.equals(ProcessType.EMAILPLUSMESSAGE.name())){
				event.setProcessType(ProcessType.EMAILPLUSMESSAGE);
			}else if(processType.equals(ProcessType.SMSPLUSEMAIL.name())){
				event.setProcessType(ProcessType.SMSPLUSEMAIL);
			}else{
				event.setProcessType(ProcessType.MESSAGE);
			}
			
		}
		return event;
	}
}
