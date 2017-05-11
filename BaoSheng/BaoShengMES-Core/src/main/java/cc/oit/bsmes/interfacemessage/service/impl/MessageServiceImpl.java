package cc.oit.bsmes.interfacemessage.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.MesStatus;
import cc.oit.bsmes.common.constants.MesType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.interfacemessage.dao.MessageDAO;
import cc.oit.bsmes.interfacemessage.model.MailSenderInfo;
import cc.oit.bsmes.interfacemessage.model.Message;
import cc.oit.bsmes.interfacemessage.model.SimpleMailSender;
import cc.oit.bsmes.interfacemessage.service.MessageService;
import cc.oit.bsmes.pla.dao.CustomerOrderItemDAO;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pro.dao.ProductCraftsDAO;
import cc.oit.bsmes.pro.model.ProductCrafts;

import com.alibaba.fastjson.JSONObject;

@Service
public class MessageServiceImpl extends BaseServiceImpl<Message> implements
		MessageService {

	@Resource
	private MessageDAO messageDAO;

	@Resource
	private ProductCraftsDAO productCraftsDAO;
	
	@Resource
	private CustomerOrderItemDAO customerOrderItemDAO;
	

	@Override
	public List<Message> findMessageSMS() {
		return messageDAO.findMessageSMS();
	}

	@Override
	public List<Message> findMessageEmail() {
		return messageDAO.findMessageEmail();
	}

	@Override
	public void sendMessagebyMail(String pro) {

		InputStream in = MessageServiceImpl.class
				.getResourceAsStream("MessageInfo.properties");
		Properties p = new Properties();
		String[] tos = null;
		try {
			p.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration en = p.propertyNames();
		while (en.hasMoreElements()) {
			String name = (String) en.nextElement();
			tos = p.getProperty(name).split(",");
		}

		for (String to : tos) {
			MailSenderInfo mailInfo = new MailSenderInfo();
			mailInfo.setMailServerHost("smtp.126.com");
			mailInfo.setMailServerPort("25");
			mailInfo.setValidate(true);
			// 邮箱用户名
			mailInfo.setUserName("CManu7@126.com");
			// 邮箱密码
			mailInfo.setPassword("dsmtkqrgoqpmzzhy");
			// 发件人邮箱
			mailInfo.setFromAddress("CManu7@126.com");
			// 收件人邮箱
			mailInfo.setToAddress(to);
			// 邮件标题
			mailInfo.setSubject("请添加以下产品的工艺路线");

			StringBuffer buffer = new StringBuffer();

			String[] contents = pro.split(",");

			for (String content : contents) {
				buffer.append(content + "\n");
			}
			mailInfo.setContent(buffer.toString());
			// 发送邮件
			SimpleMailSender sms = new SimpleMailSender();
			// 发送文体格式
			sms.sendTextMail(mailInfo);
		}

	}

	@Override
	public boolean checkExistPrcv(String prcvs, JSONObject result) {

		String[] prcv = prcvs.split(",");
		for (String prcvNo : prcv) {
			prcvNo = prcvNo + "-001";
			List<ProductCrafts> prcvLists = productCraftsDAO
					.getByPrcvByNo(prcvNo);
			if (prcvLists.size() > 0) {
				result.put("message", "工艺路线" + prcvNo + "在系统中已存在！");
				return true;
			}
		}
		return false;
	}

	@Override
	public void saveTemp(String orderItemIds,String pro) {
		String[] orderItemId=orderItemIds.split(",");
		for(String custId:orderItemId){
			List <CustomerOrderItem> lists=customerOrderItemDAO.checkExistsCustId(custId);
			if(lists.size()==0){
				String creatorUserCode=SessionUtils.getUser().getCreateUserCode();
				customerOrderItemDAO.insertCustId(custId,creatorUserCode);
			}
		}
		
		InputStream in = MessageServiceImpl.class
				.getResourceAsStream("MessageInfo.properties");
		Properties p = new Properties();
		String tos = null;
		StringBuffer buffer = new StringBuffer();
		try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Enumeration en = p.propertyNames();
		while (en.hasMoreElements()) {
			String name = (String) en.nextElement();
			tos = p.getProperty(name);
		}
		

		String[] contents = pro.split(",");
		for (String content : contents) {
			buffer.append(content + "\n");
		}
		Message mesEmail = new Message();
		mesEmail.setConsignee(tos);
		mesEmail.setMesTitle("请添加以下产品的工艺路线");
		mesEmail.setMesContent(buffer.toString());
		mesEmail.setSendTimes(1);
		mesEmail.setMesType(MesType.EMAIL);
		mesEmail.setStatus(MesStatus.NEW);
		messageDAO.insert(mesEmail);
		
	}

}
