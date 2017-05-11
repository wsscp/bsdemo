package cc.oit.bsmes.interfaceMessage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.MesStatus;
import cc.oit.bsmes.common.constants.MesType;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.model.EventProcess;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.eve.service.EventProcessService;
import cc.oit.bsmes.interfacemessage.model.Message;
import cc.oit.bsmes.interfacemessage.service.MessageAutoSendService;
import cc.oit.bsmes.interfacemessage.service.MessageService;
import cc.oit.bsmes.job.tasks.EventScheduleProcess;
import cc.oit.bsmes.junit.BaseTest;

public class MessageTest extends BaseTest {
	@Resource
	private EventScheduleProcess eventScheduleProcess;
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	private EventProcessService eventProcessService;
	@Resource
	private DataDicService dataDicService;
	@Resource
	private MessageService messageService;

	@Test
	public void test() throws Exception {
		EventInformation findParams = new EventInformation();
		findParams.setOrgCode("1");
		findParams.setEventStatus(EventStatus.UNCOMPLETED);
		List<EventInformation> eventTodoList = eventInformationService.findNeedToProcess(findParams);
		List<EventProcess> alist = eventProcessService.getByEventTypeId(eventTodoList.get(0).getEventTypeId());
		eventScheduleProcess.executeEventProcess(eventTodoList.get(0), alist.get(0));

	}

	@Test
	public void test2() {
		DataDic dic = new DataDic();
		dic.setTermsCode("xchen");
		dic.setName("xchen");
		dataDicService.insert(dic);
	}

	@Test
	public void test3() {
		Message mesEmail = new Message();
		mesEmail.setMesContent("xchen");
		mesEmail.setSendTimes(0);
		mesEmail.setMesType(MesType.SMS);
		mesEmail.setStatus(MesStatus.NEW);
		mesEmail.setSendDate(new Date());
		messageService.insert(mesEmail);
	}

	@Test
	public void test4() {
		HttpURLConnection connection;
		URL url;
		try {
			url = new URL("http://sms.1xinxi.cn/asmx/smsservice.aspx");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			String content = "name=" + URLEncoder.encode("13917727619", "utf-8") + "&pwd="
					+ URLEncoder.encode("29F32C1162A27BFBC39BEC74587B", "utf-8") + "&content="
					+ URLEncoder.encode("收到吗？", "utf-8") + "&mobile=" + URLEncoder.encode(" 15901803446", "utf-8")
					+ "&type=" + URLEncoder.encode("pt", "utf-8");
			out.writeBytes(content);
			out.flush();
			out.close(); // flush and close
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			String lineStr = "";
			while ((line = reader.readLine()) != null) {
				lineStr = lineStr + line;
			}
			System.out.println(lineStr);
			reader.close();
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test5() {
		MessageAutoSendService mesAutoSendService = (MessageAutoSendService) ContextUtils
				.getBean(MessageAutoSendService.class);
		try {
			mesAutoSendService.autoSendMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test6() {
		List<String> listStr = new ArrayList<String>();
		listStr.add("1");
		listStr.add("2");
		listStr.add("3");
		System.out.println(listStr.toString());
	}

	@Test
	public void test7() {
		List<Message> messageList = messageService.findMessageSMS();
		System.out.println(messageList);
	}

	@Test
	public void test8() {
		MessageAutoSendService mesAutoSendService = (MessageAutoSendService) ContextUtils
				.getBean(MessageAutoSendService.class);
		try {
			mesAutoSendService.autoSendMessage();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
