package cc.oit.bsmes.wip.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.WorkOrderOperateLog;

/**
 * WorkOrderOperateLogDAO
 * 
 * @author DingXintao
 * @date 2014-7-1 11:20:48
 * @since
 * @version
 */
public interface WorkOrderOperateLogDAO extends BaseDAO<WorkOrderOperateLog> {

	
	/**
	 * @Title:       getLastOperateLog
	 * @Description: TODO(获取最近的一条操作记录：状态!=FINISHED && endTime == null)
	 * @param:       @param workOrderNo 生产单号
	 * @return:      List<WorkOrderOperateLog>   
	 * @throws
	 */
	public WorkOrderOperateLog getLastOperateLog(String workOrderNo);
}
