package cc.oit.bsmes.job.tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.SysMessage;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.bas.service.SysMessageService;
import cc.oit.bsmes.common.constants.MesStatus;
import cc.oit.bsmes.common.constants.MesType;
import cc.oit.bsmes.common.constants.ProcessType;
import cc.oit.bsmes.common.service.impl.MailEngine;
import cc.oit.bsmes.eve.dao.EventOwnerDAO;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.model.EventOwner;
import cc.oit.bsmes.eve.model.EventProcess;
import cc.oit.bsmes.eve.model.EventTriggerLog;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.eve.service.EventProcessService;
import cc.oit.bsmes.eve.service.EventProcesserService;
import cc.oit.bsmes.eve.service.EventTriggerLogService;
import cc.oit.bsmes.interfacemessage.model.Message;
import cc.oit.bsmes.interfacemessage.service.MessageService;

/**
 * 处理单个事件<br/>
 * 发送[SMS("短信"),MESSAGE("系统消息"),EMAIL("邮件"),ALL("所有")]<br/>
 * 
 * */
@Service
public class EventScheduleProcess {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	private EventProcessService eventProcessService;
	@Resource
	private EventTriggerLogService eventTriggerLogService;
	@Resource
	private MessageService messageService;
	@Resource
	private SysMessageService sysMessageService;
	@Resource
	private EventProcesserService eventProcesserService;
	@Resource
	private MailEngine mailEngine;
	@Resource
	EventOwnerDAO eventOwnerDAO;
	@Resource
	private EmployeeService employeeService; 

	/**
	 * 处理单个事件
	 * 
	 * @param Map<String, List<EventProcess>> eventProcessMap 事件处理流程集合
	 * @param EventInformation eve 事件对象
	 * @throws Exception 异常
	 * */
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class }, readOnly = false)
	public void processOne(Map<String, List<EventProcess>> eventProcessMap, EventInformation eve) throws Exception {
		Date triggerTime = eve.getProcessTriggerTime();
		if (triggerTime != null && triggerTime.before(new Date())) {
			try {
				List<EventProcess> alist = eventProcessMap.get(eve.getEventTypeId());
				if (alist == null) {
//					EventProcess findProcessParams = new EventProcess();
//					findProcessParams.setEventTypeId(eve.getEventTypeId());
//					findProcessParams.setStatus(Boolean.TRUE);
					alist = eventProcessService.getByEventTypeId(eve.getEventTypeId());
					eventProcessMap.put(eve.getEventTypeId(), alist);
				}
				if(alist==null||alist.size()==0)
				{
					return;
				}
				EventProcess needDoProcess = null;// 马上需要处理的流程
				EventProcess fuchuerDoProcess = null;// 未来需要处理的
				if("-1".equalsIgnoreCase(eve.getEventProcessId()))
				{
				 //如果是最-1，说明是最后一步，直接拿最后一步的处理步骤
					needDoProcess=alist.get(alist.size()-1);
					
				}else
				{
					for (EventProcess eventProcess : alist) {
						if (eventProcess.getId().equalsIgnoreCase(eve.getEventProcessId())) {
							// 按照eventProcess的定义处理时间
							needDoProcess = eventProcess;
							continue;
						}
						if (needDoProcess != null && needDoProcess.getProcessSeq() < eventProcess.getProcessSeq()
								&& fuchuerDoProcess == null) {
							// 为升级事件做好准备
							fuchuerDoProcess = eventProcess;
							break;
						}
					}
				}
				

				// 开始处理
				if (needDoProcess != null) {
					// 根据流程中的处理方式，处理，
					executeEventProcess(eve, needDoProcess);
				}

				if (fuchuerDoProcess != null) {
					double intervalMinutes = fuchuerDoProcess.getStepInterval();
					eve.setProcessTriggerTime(DateUtils.addSeconds(new Date(), (int) intervalMinutes * 60));
					eve.setEventProcessId(fuchuerDoProcess.getId());
					processOwner(eve, fuchuerDoProcess);
				} else {
				 
					//按照最后一个流程的间隔重复处理
					double intervalMinutes = needDoProcess.getStepInterval();
					eve.setProcessTriggerTime(DateUtils.addSeconds(new Date(), (int) intervalMinutes * 60));
					//eve.setProcessTriggerTime(BusinessConstants.MAX_DATE);
					eve.setEventProcessId("-1");
					// eventOwnerDAO.deleteByEventId(eve.getId());
				}
				eventInformationService.update(eve);
			} catch (Exception e) {
				// 触发日志
				String errorMessage = e.getMessage();
				if (errorMessage.length() > 3000)
					errorMessage = errorMessage.substring(0, 3000);
				eventTriggerLogService.insertErrorEventTriggerLog(eve.getId(), eve.getEventProcessId(), errorMessage,
						eve.getOrgCode());
				throw e;
			}
		}
	}

	/**
	 * 根据下一节点事件处理流程添加到表<EventOwner>正真处理人的信息<br/>
	 * 1、根据流程Id获得流程处理人信息：处理人角色/处理人code<br/>
	 * 2、根据[处理人角色/处理人code]获得正真的处理人的code集合<br/>
	 * 
	 * @param EventInformation eve 事件对象
	 * @param EventProcess fuchuerDoProcess 事件处理流程
	 * */
	private void processOwner(EventInformation eve, EventProcess fuchuerDoProcess) {
		// eventOwnerDAO.deleteByEventId(eve.getId());
		// 获得该流程节点处理人code集合
		Map<String, String> map = eventProcesserService.getUserCodeByEventProcessId(fuchuerDoProcess.getId());
		if (map == null) {
			logger.warn("下一流程节点处理人集合为空！");
			return;
		}
		// 添加处理人
		Iterator<String> it = map.values().iterator();
		while (it.hasNext()) {
			String owner = it.next();
			EventOwner t = new EventOwner();
			t.setEventId(eve.getId());
			t.setUserCode(owner);
			t.setCreateUserCode("job");
			t.setModifyUserCode("job");
			t.setEventProcessId(fuchuerDoProcess.getId());
			eventOwnerDAO.insert(t);
		}
		return;
	}

	/**
	 * 处理此次Job事件流程<br/>
	 * 1、联合查询[<Employee>和<EventOwner>]获取处理人员工集合List<Employee><br/>
	 * 2、根据事件流程处理方式[SMS("短信"),MESSAGE("系统消息"),EMAIL("邮件"),ALL("所有")]发送事件内容<br/>
	 * 
	 * @param EventInformation eve 事件对象
	 * @param EventProcess eventProcess 事件处理流程
	 * */
	public void executeEventProcess(EventInformation eve, EventProcess eventProcess) throws Exception {
		// 获取处理人员信息集合
		List<Employee> alist = eventProcesserService.getEmployeeByEventId(eve.getId(), eventProcess.getId());
		if (null == alist || alist.size() == 0) {
			logger.warn("当前流程节点处理人集合为空！");
			return;
		}

		// 根据处理方式发送消息
		String processType = eventProcess.getProcessType().name();
		if (ProcessType.SMS.name().equalsIgnoreCase(processType)) {
			this.sendSMS(eve, alist, eventProcess);
		} else if (ProcessType.EMAIL.name().equalsIgnoreCase(processType)) {
			this.sendMail(eve, alist, eventProcess);
		} else if (ProcessType.MESSAGE.name().equalsIgnoreCase(processType)) {
			this.sendMessage(eve, alist, eventProcess);
		}
		else if (ProcessType.SMSPLUSMESSAGE.name().equalsIgnoreCase(processType)) {
			this.sendSMS(eve, alist, eventProcess);
			this.sendMessage(eve, alist, eventProcess);
		}
		else if (ProcessType.EMAILPLUSMESSAGE.name().equalsIgnoreCase(processType)) {
			this.sendMail(eve, alist, eventProcess);
			this.sendMessage(eve, alist, eventProcess);
		}
		else if (ProcessType.SMSPLUSEMAIL.name().equalsIgnoreCase(processType)) {
			this.sendSMS(eve, alist, eventProcess);
			this.sendMail(eve, alist, eventProcess);
		}else if (ProcessType.ALL.name().equalsIgnoreCase(processType)) {
			this.sendSMS(eve, alist, eventProcess);
			this.sendMail(eve, alist, eventProcess);
			this.sendMessage(eve, alist, eventProcess);
		} else {
			throw new Exception("wrong process type");
		}
	}

	/**
	 * 发送系统消息
	 * 
	 * @param EventInformation eve 事件对象
	 * @param List<Employee> alist 事件处理人集合
	 * @param EventProcess eventProcess 事件处理流程
	 * */
	private void sendMessage(EventInformation eve, List<Employee> alist, EventProcess eventProcess) {
		String messageContent = this.addMessageLevelFlag(eve.getEventContent(), eventProcess.getProcessSeq());
		StringBuffer processContent = new StringBuffer();
		processContent.append("发送站内消息给以下人员:");
		List<SysMessage> messageList = new ArrayList<SysMessage>();
		for (Employee employee : alist) { // 系统内部消息
			SysMessage message = new SysMessage();
			message.setCreateTime(new Date());
			message.setCreateUserCode("Event Process Job");
			message.setModifyTime(new Date());
			message.setModifyUserCode("Event Process Job");
			message.setHasread(false);
			message.setMessageContent(messageContent);
			String messageReceiver = employee.getUserCode();
			String messageReceiverName = employee.getName();
			processContent.append(messageReceiverName).append("[");
			processContent.append(messageReceiver).append("],");
			message.setMessageReceiver(messageReceiver);
			message.setMessageTitle(eve.getEventTitle());
			message.setOrgCode(eve.getOrgCode());
			message.setReadTime(null);
			message.setReceiveTime(new Date());
			messageList.add(message);
		}
		sysMessageService.insert(messageList);

		// 事件处理日志
		this.triggerLog(eve, eventProcess, processContent.toString());
	}

	/**
	 * 发送邮件
	 * 
	 * @param EventInformation eve 事件对象
	 * @param List<Employee> alist 事件人集合
	 * @param EventProcess eventProcess 事件处理流程
	 * */
	private void sendMail(EventInformation eve, List<Employee> alist, EventProcess eventProcess) {
		StringBuffer processContent = new StringBuffer();
		Message mesEmail = new Message();
		// 电子邮件
		String bodyText = this.addMessageLevelFlag(eve.getEventContent(), eventProcess.getProcessSeq());
		List<String> emailAddressesList = new ArrayList<String>();
		String subject = eve.getEventTitle();
		processContent.append("发送邮件给以下人员:");
		for (Employee employee : alist) {
			if (!StringUtils.isEmpty(employee.getEmail())) {
				String messageReceiver = employee.getUserCode();
				String messageReceiverName = employee.getName();
				processContent.append(messageReceiverName).append("[");
				processContent.append(messageReceiver).append("],");
				emailAddressesList.add(employee.getEmail());
			}
		}
		String emailAddressesListStr = emailAddressesList.toString();
		mesEmail.setConsignee(emailAddressesListStr.subSequence(1, emailAddressesListStr.indexOf(']')).toString());
		if (StringUtils.isEmpty(mesEmail.getConsignee())) {
			return;
		}
		mesEmail.setMesTitle(subject);
		mesEmail.setMesContent(bodyText);
		mesEmail.setSendTimes(0);
		mesEmail.setMesType(MesType.EMAIL);
		mesEmail.setStatus(MesStatus.NEW);
		messageService.insert(mesEmail);

		// 事件处理日志
		this.triggerLog(eve, eventProcess, processContent.toString());
	}

	/**
	 * 发送短信
	 * 
	 * @param EventInformation eve 事件对象
	 * @param List<Employee> alist 事件人集合
	 * @param EventProcess eventProcess 事件处理流程
	 * */
	private void sendSMS(EventInformation eve, List<Employee> alist, EventProcess eventProcess) {
		StringBuffer processContent = new StringBuffer();
		Message mesEmail = new Message();
		String bodyText = "";
		// 短信
		if(eventProcess==null){
			bodyText = eve.getEventContent()+"[已解除.]";
		}else{
			bodyText = this.addMessageLevelFlag(eve.getEventContent(), eventProcess.getProcessSeq());
		}
		
		
		List<String> telephoneList = new ArrayList<String>();
		String subject = eve.getEventTitle();
		processContent.append("发送短信给以下人员:");
		for (Employee employee : alist) {
			if (!StringUtils.isEmpty(employee.getTelephone())) {
				String messageReceiver = employee.getUserCode();
				String messageReceiverName = employee.getName();
				processContent.append(messageReceiverName).append("[");
				processContent.append(messageReceiver).append("],");
				telephoneList.add(employee.getTelephone());
			}
		}
		String telephoneListStr = telephoneList.toString();
		// 存储号码时用","隔开
		mesEmail.setConsignee(telephoneListStr.subSequence(1, telephoneListStr.indexOf(']')).toString());
		if (StringUtils.isEmpty(mesEmail.getConsignee())) {
			return;
		}
		mesEmail.setMesTitle(subject);
		mesEmail.setMesContent(bodyText);
		mesEmail.setSendTimes(0);
		mesEmail.setMesType(MesType.SMS);
		mesEmail.setStatus(MesStatus.NEW);
		messageService.insert(mesEmail);// 将需要发送的短信存储

		// 事件处理日志
		this.triggerLog(eve, eventProcess, processContent.toString());
	}

	/**
	 * 事件处理日志
	 * 
	 * @param EventInformation eve 事件对象
	 * @param EventProcess eventProcess 事件处理流程
	 * @param StringBuffer processContentBuffer 事件处理记录日志内容
	 * */
	private void triggerLog(EventInformation eve, EventProcess eventProcess, String processContentTxt) {
		// 触发日志
		if (processContentTxt.length() > 4000) {
			processContentTxt = processContentTxt.substring(0, 4000);
		}
		EventTriggerLog eventLog = new EventTriggerLog();
		eventLog.setCreateTime(new Date());
		eventLog.setCreateUserCode("Event Process Job");
		eventLog.setEventId(eve.getId());
		eventLog.setModifyTime(new Date());
		eventLog.setModifyUserCode("Event Process Job");
		eventLog.setOrgCode(eve.getOrgCode());
		eventLog.setProcessId(eventProcess.getId());
		eventLog.setProcessContent(processContentTxt);
		eventTriggerLogService.insert(eventLog);
	}

	/**
	 * 消息内容添加紧急程度
	 * 
	 * @param String content 事件内容
	 * @param int seq 事件紧急程度
	 * */
	private String addMessageLevelFlag(String content, int seq) {
		StringBuffer contentBuf = new StringBuffer();
		contentBuf.append(content).append("[紧急程度：").append(seq).append("]");
		return contentBuf.toString();
	}
	
	public void processOne(EventInformation eve) throws Exception{
		try {
			String equipCode = eve.getEquipCode();
			List<Employee> employeeList = employeeService.getEventAccepter(equipCode);
			eve.setStatus("0");
			eventInformationService.update(eve);
			
			sendSMS(eve, employeeList,null);
		}catch(Exception e){
			// 触发日志
			String errorMessage = e.getMessage();
			if (errorMessage.length() > 3000)
				errorMessage = errorMessage.substring(0, 3000);
			eventTriggerLogService.insertErrorEventTriggerLog(eve.getId(), eve.getEventProcessId(), errorMessage,
					eve.getOrgCode());
			throw e;
		}
	}
}
