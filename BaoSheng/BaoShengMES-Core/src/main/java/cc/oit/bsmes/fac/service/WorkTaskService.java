package cc.oit.bsmes.fac.service;

import java.text.ParseException;
import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.fac.model.WorkTask;

/**
 * 
 * 设备加工负载
 * @author leiwei
 * @date  2014-1-3 下午4:24:44
 * @since
 * @version
 */
public interface WorkTaskService extends BaseService<WorkTask> {

	/**
	 * 
	 * 根据工序id获取设备计划加工任务负载,开始时间大于当前时间，按照任务负载由大到小排列
	 * @author leiwei
	 * @date 2014-1-6 下午3:51:55
	 * @param processId  工序id
	 * @return
	 * @see
	 */
	List<WorkTask> getByProcessId(String processId);
	
	/**
	 * 
	 * <p>TODO(根据机构编码删除设备计划加工任务负载)</p> 
	 * @author leiwei
	 * @date 2014-1-6 下午3:51:33
	 * @param orgCode 机构编码
	 * @see
	 */
	void deleteByOrgCode(String orgCode);

	/**
	 * <p>根据设备编号和订单分解工序ID获取设备负载记录</p> 
	 * @author QiuYangjun
	 * @date 2014-2-14 下午5:07:52
	 * @param equipCode
	 * @param orderItemProDecId
	 * @return
	 * @see
	 */
	public WorkTask getByEquipCodeAndOrderItemProDecId(String equipCode,
			String orderItemProDecId);

	/**
	 * <p>OA计算初始化删除未锁定数据</p> 
	 * @author QiuYangjun
	 * @date 2014-3-18 下午4:18:56
	 * @param orgCode
	 * @see
	 */
	void deleteByOrgCodeForOA(String orgCode);
	/**
	 * 
	 * <p>TODO(获取三天内设备负载)</p> 
	 * @author leiwei
	 * @date 2014-3-24 下午1:28:12
	 * @param processId
	 * @return
	 * @see
	 */
	List<WorkTask> getByProcessIdAndDate(String processId);
	/**
	 * 
	 * <p>根据设备编码获取设备负载</p> 
	 * @author leiwei
	 * @date 2014-3-24 下午2:32:05
	 * @param equipCode
	 * @return
	 * @see
	 */
	List<WorkTask> getByCode(String equipCode);
	/**
	 * 
	 * <p>获取从当前日期起的设备负载</p> 
	 * @author leiwei
	 * @date 2014-4-2 下午12:56:39
	 * @return
	 * @see
	 */
	List<WorkTask> getWorkTasks(String fromDate, String toDate) throws ParseException;
	
	/**
	 * 根据设备编码获取设备加工任务负载，条件为 没有加工完成
	 * 
	 * @author DingXintao
	 * @date 2015-08-28
	 * @param equipCode 设备编码
	 * */
	public List<WorkTask> getByEquipCode(String equipCode);
}
