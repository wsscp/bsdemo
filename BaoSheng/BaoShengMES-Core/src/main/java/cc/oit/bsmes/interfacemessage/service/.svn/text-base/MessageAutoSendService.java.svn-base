package cc.oit.bsmes.interfacemessage.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.MesStatus;
import cc.oit.bsmes.common.service.impl.MailEngine;
import cc.oit.bsmes.interfacemessage.model.Message;

@Service
public class MessageAutoSendService {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private static HttpURLConnection connection;
	private static URL urlObj;
	private static Properties props;
	private static String url;
	private static String username;
	private static String pwd;
	private static String type;
	private static String sign;
	private static Object synObject= new Object();

	@Resource
	private MessageService messageService;
	@Resource
	private MailEngine mailEngine;

	private void init(){
		props = new Properties();
		InputStream in = MessageAutoSendService.class
				.getResourceAsStream("Message.properties");
		try {
			props.load(in);
			url = props.getProperty("url");
			username = props.getProperty("username");
			pwd = props.getProperty("pwd");
			type = props.getProperty("type");
			sign = props.getProperty("sign");

			urlObj = new URL(url);
			connection=null;
			connection = (HttpURLConnection) urlObj.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void autoSendMessage() throws IOException {
		synchronized (synObject){
			
		
		init();
		List<Message> messageSMSList = messageService.findMessageSMS();
		List<Message> messageEmailList = messageService.findMessageEmail();
		connection.connect();
		
		for (Message message : messageSMSList) {
			String mobile = message.getConsignee();
			if(StringUtils.isEmpty(mobile))
			{
				updateMessageSuccess(message);
				continue;
			}
			 OutputStream outsreM = connection.getOutputStream();
			DataOutputStream out = new DataOutputStream(outsreM
					); 
			String mesContent = message.getMesContent();
			String content = "name=" + username + "&pwd=" + pwd + "&content="
					+ URLEncoder.encode(mesContent, "utf-8") + "&mobile="
					+ mobile + "&type=" + type+"&sign="+URLEncoder.encode(sign, "utf-8");
			out.writeBytes(content);
			InputStream instream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream
					));
			String line;
			String lineStr = "";// 短信发送响应返回字符串
			while ((line = reader.readLine()) != null) {
				lineStr = lineStr + line;
			}
			reader.close();
			
			if (lineStr.startsWith("0")) {// 响应值开头code为0就为提交成功
				updateMessageSuccess(message);
			} else {
				updateMessageError(message, lineStr);
			}
			instream.close();
			outsreM.close();
			out.flush();
			out.close();
		}

	
		connection.disconnect();

		for (Message message : messageEmailList) {
			try {
				String emailAdd = message.getConsignee();
				if(StringUtils.isEmpty(emailAdd))
				{
					updateMessageSuccess(message);
					continue;
				}
				mailEngine.sendMessage(message.getConsignee().split(","),
						message.getMesTitle(), message.getMesContent(), null);
				updateMessageSuccess(message);
			} catch (MessagingException e) {
				log.error(e.getMessage(), e);
				String lineStr = e.getMessage();
				updateMessageError(message, lineStr);
			}
		}
		}

	}
	
	public void autoSendEmail() throws IOException{
		synchronized (synObject){
		List<Message> messageEmailList = messageService.findMessageEmail();
		for (Message message : messageEmailList) {
			try {
				String emailAdd = message.getConsignee();
				if(StringUtils.isEmpty(emailAdd))
				{
					updateMessageSuccess(message);
					continue;
				}
				mailEngine.sendMessage(message.getConsignee().split(","),message.getMesTitle(), message.getMesContent(), null);
				updateMessageSuccess(message);
			} catch (MessagingException e) {
				log.error(e.getMessage(), e);
				String lineStr = e.getMessage();
				updateMessageError(message, lineStr);
			}
		}
		}
	
	}

	public void updateMessageSuccess(Message message) {
		Message updateMessage = new Message();
		updateMessage.setId(message.getId());
		updateMessage.setStatus(MesStatus.SUCCESS);		 
		updateMessage.setSendDate(new Date());
		messageService.update(updateMessage);
	}

	public void updateMessageError(Message message, String exceptionDes) {
		Message updateMessage = new Message();
		updateMessage.setId(message.getId());
		updateMessage.setStatus(MesStatus.SENDED);
		updateMessage.setSendTimes(message.getSendTimes() + 1);
		updateMessage.setSendDate(new Date());
		updateMessage.setExceptionDescription(exceptionDes);
		messageService.update(updateMessage);
	}

}
