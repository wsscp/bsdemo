package cc.oit.bsmes.interfacemessage.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacemessage.model.Message;

public interface MessageDAO extends BaseDAO<Message>{
	
	List<Message> findMessageSMS();
	
	List<Message> findMessageEmail();
}
