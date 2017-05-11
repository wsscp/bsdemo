package cc.oit.bsmes.eve.service;

import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.eve.model.EventTriggerLog;
/**
 * 
 * 异常事件信息
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2014-2-10 下午1:47:48
 * @since
 * @version
 */
public interface EventTriggerLogService extends BaseService<EventTriggerLog> {

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
	public void insertErrorEventTriggerLog(String eventId, String processId, String errorMessage, String orgCode)
			throws DataCommitException;
}
