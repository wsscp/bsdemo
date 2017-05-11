/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */ 
package cc.oit.bsmes.fac.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.fac.model.WorkTask;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月31日 下午3:44:59
 * @since
 * @version
 */
public interface WorkTaskDAO extends BaseDAO<WorkTask> {

	List<WorkTask> getByProcessId(String processId);

	void deleteByOrgCode(String orgCode);
	
	/**
	 * 
	 * <p>OA计算初始化数据</p> 
	 * @author QiuYangjun
	 * @date 2014-3-18 下午4:13:33
	 * @param orgCode
	 * @see
	 */
	void deleteByOrgCodeForOA(String orgCode);
	
	/**
	 * 根据设备编码获取设备加工任务负载，条件为 没有加工完成
	 * 
	 * @author DingXintao
	 * @date 2015-08-28
	 * @param equipCode 设备编码
	 * */
	public List<WorkTask> getByEquipCode(String equipCode);

	/**
	 * <p>OA计算初始化删除未锁定并且不存在orderTask表中的数据</p> 
	 * @author QiuYangjun
	 * @date 2014-3-21 下午1:45:07
	 * @param orgCode
	 * @see
	 */
	void deleteByOrgCodeForOANotExistsOrderTask(String orgCode);
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author leiwei
	 * @date 2014-3-24 下午1:29:51
	 * @param processId
	 * @return
	 * @see
	 */
	List<WorkTask> getByProcessIdAndDate(String processId);

	List<WorkTask> getByCode(String code);

	List<WorkTask> getWorkTasks(Map<String, Object> param);

}
