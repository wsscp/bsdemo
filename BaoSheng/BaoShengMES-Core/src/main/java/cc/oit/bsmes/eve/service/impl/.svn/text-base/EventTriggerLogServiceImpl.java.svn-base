package cc.oit.bsmes.eve.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.eve.dao.EventTriggerLogDAO;
import cc.oit.bsmes.eve.model.EventTriggerLog;
import cc.oit.bsmes.eve.service.EventTriggerLogService;
/**
 * 
 *  事件触发日志
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2014-2-10 下午1:56:10
 * @since
 * @version
 */

@Service
public class EventTriggerLogServiceImpl extends BaseServiceImpl<EventTriggerLog> implements EventTriggerLogService {
	@Resource private EventTriggerLogDAO eventTriggerLogDAO;

	/**
	 * 记录一个异常日志，不回滚事务<br/>
	 * 
	 * @param String eventId 事件ID
	 * @param String processId 流程ID
	 * @param String errorMessage 错误内容
	 * @param String orgCode 组织编码
	 * 
	 * @throws DataCommitException 异常
	 * */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void insertErrorEventTriggerLog(String eventId, String processId, String errorMessage, String orgCode)
			throws DataCommitException {
		EventTriggerLog eventLog = new EventTriggerLog();
		eventLog.setProcessContent(errorMessage);
		eventLog.setCreateTime(new Date());
		eventLog.setCreateUserCode("Event Process Job");
		eventLog.setEventId(eventId);
		eventLog.setModifyTime(new Date());
		eventLog.setModifyUserCode("Event Process Job");
		eventLog.setOrgCode(orgCode);
		eventLog.setProcessId(processId);
		eventTriggerLogDAO.insert(eventLog);
	}
}
