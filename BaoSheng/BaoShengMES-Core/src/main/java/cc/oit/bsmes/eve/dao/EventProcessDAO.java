package cc.oit.bsmes.eve.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.eve.model.EventProcess;
/**
 * 
 * 事件处理流程定义
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-10 下午2:54:29
 * @since
 * @version
 */
public interface EventProcessDAO extends BaseDAO<EventProcess> {
	/**
	 * 
	 * <p>TODO(根据事件类型id获取事件处理步骤)</p> 
	 * @author leiwei
	 * @date 2014-2-12 下午2:14:29
	 * @param eventTypeId
	 * @return
	 * @see
	 */
	List<EventProcess> getByEventTypeId(String eventTypeId);
	/**
	 * 
	 * <p>TODO(删除某条事件流程记录，并更改事件流程顺序)</p> 
	 * @author leiwei
	 * @date 2014-2-21 上午9:58:19
	 * @param id
	 * @param eventTypeId
	 * @see
	 */
	void deleteEventProcessById(String id,String eventTypeId);
	
	Integer getProcesSeqByEventTypeId(String eventTypeId);
	Integer getProcessSeqById(String id);


}
