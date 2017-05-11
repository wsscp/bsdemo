package cc.oit.bsmes.job.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.model.EventProcess;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;

@Service
public class EventScheduleTask extends BaseSimpleTask {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	EventScheduleProcess eventScheduleProcess;

	/**
	 * <p>
	 * Job执行事件消息发送
	 * </p>
	 * 1、 获取未处理的事件信息列表<br/>
	 * 2、 循环处理单个消息<EventInformation><br/>
	 * --2.1、根据消息类型获取消息处理流程List<EventProcess>[有序的]<br/>
	 * --2.2、循环消息处理流程获取当前即将处理的流程<EventProcess>和下一节点要处理的流程<EventProcess><br/>
	 * ----2.2.1、根据事件流程的ID和事件对象的[eventProcessId]相等关系获得即将处理<EventProcess><br/>
	 * ----2.2.2、根据事件流程的ID和事件对象的[eventProcessId]大小关系获得下一节点要处理<EventProcess><br/>
	 * ----2.2.3、更新事件信息<EventInformation>的[eventProcessId]，以便下一次Job执行获取当前要处理的流程<br/>
	 * ----2.2.4、根据下一节点要处理的流程获取相关处理人信息，并 添加到[T_EVE_EVENT_OWNER]表中<EventOwner><br/>
	 * 3、根据当前流程处理事件信息<br/>
	 * --3.1、从<EventOwner>中获取事件流程要处理的相关人员信息<br/>
	 * --3.2、根据事件处理流程的处理方式发送消息SMS("短信"),MESSAGE("系统消息"),EMAIL("邮件"),ALL("所有")<br/>
	 * */
	@Override
	public void process(JobParams parms) {
		String orgCode = parms.getOrgCode();
		Integer size = parms.getBatchSize();
		if (size == null || size.intValue() == 0) {
			size = Integer.valueOf(100);
		}
		EventInformation findParams = new EventInformation();
		findParams.setOrgCode(orgCode);
		findParams.setEventStatus(EventStatus.UNCOMPLETED);
		// 获取未处理事件列表
		List<EventInformation> eventTodoList = eventInformationService.findNeedToProcess(findParams);

		Map<String, List<EventProcess>> eventProcessMap = new HashMap<String, List<EventProcess>>();
		for (EventInformation eve : eventTodoList) {
			try {
				// 处理单个事件
				eventScheduleProcess.processOne(eventProcessMap, eve);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e);
			}
		}
		
		EventInformation findParamsComp = new EventInformation();
		findParamsComp.setOrgCode(orgCode);
		findParamsComp.setStatus("1");
		findParamsComp.setEventTypeId("914e3d9a-022e-41a7-a1bf-3aae0a029a4e");
		//获取设备故障已完成的事件列表
		List<EventInformation> eventTodoListFinished = eventInformationService.findByObj(findParamsComp);
		
		for (EventInformation eventInformation : eventTodoListFinished) {
			try{
				eventScheduleProcess.processOne(eventInformation);
			}catch(Exception e){
				logger.error(e.getLocalizedMessage(), e);
			}
		}
	}

}
