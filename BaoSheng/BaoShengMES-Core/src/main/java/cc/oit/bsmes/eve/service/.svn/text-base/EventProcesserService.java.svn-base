package cc.oit.bsmes.eve.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.eve.model.EventProcesser;
/**
 * 
 * 事件关联处理人
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-13 下午3:12:42
 * @since
 * @version
 */
public interface EventProcesserService extends BaseService<EventProcesser> {
	/**
	 * 
	 * <p>根据事件处理流程ID获取 事件关联处理人</p> 
	 * @author leiwei
	 * @date 2014-2-13 下午3:13:07
	 * @param eventProcessId 事件处理流程ID
	 * @return
	 * @see
	 */
	public List<EventProcesser> getByEventProcessId(String eventProcessId);
	
	/**
	 * 
	 * <p>根据事件ID获取 事件关联处理人员工信息</p> 
	 * @author zhangdongping
	 * @date 2014-2-13 下午3:13:07
	 * @param eventProcessId 事件处理流程ID
	 * @return
	 * @see
	 */
	public List<Employee> getEmployeeByEventId(String evenId,String eventProcessId);
	
	
	/**
	 * <p>根据处理流程ID获取所有相关人员工号</p> 
	 * @author zhangdongping
	 * @date 2014-6-24 上午11:26:43
	 * @param eventProcessId
	 * @return
	 * @see
	 */
	public Map<String,String > getUserCodeByEventProcessId(String eventProcessId) ;
}
