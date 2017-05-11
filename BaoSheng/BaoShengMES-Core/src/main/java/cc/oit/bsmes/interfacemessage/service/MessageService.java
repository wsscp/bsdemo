package cc.oit.bsmes.interfacemessage.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacemessage.model.Message;

public interface MessageService extends BaseService<Message>{
	
	List<Message> findMessageSMS();
	
	List<Message> findMessageEmail();
	
	public void sendMessagebyMail(String pro); 
	
	public boolean checkExistPrcv(String prcvs,JSONObject result);
	
	public void saveTemp(String orderItemIds,String pro);
}
