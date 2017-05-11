package cc.oit.bsmes.eve.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.eve.model.EventProcess;

/**
 * 
 * 事件处理流程定义
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-10 下午2:52:00
 * @since
 * @version
 */
public interface EventProcessService extends BaseService<EventProcess> {
	/**
	 * 
	 * <p>根据事件类型id获取事件处理步骤</p> 
	 * @author leiwei
	 * @date 2014-2-12 下午2:12:49
	 * @param eventTypeId
	 * @return
	 * @see
	 */
	List<EventProcess> getByEventTypeId(String eventTypeId);
	/**
	 * 
	 * <p>删除某条事件流程记录，并更改事件流程顺序</p>  
	 * @author leiwei
	 * @date 2014-2-17 下午3:10:17
	 * @param id 事件流程id
	 * @param eventTypeId 事件类型
	 * @see
	 */
	void deleteEventProcessById(String id,String eventTypeId);
	
	Integer  getProcesSeqByEventTypeId(String eventTypeId);
	Integer getProcessSeqById(String id);

}
