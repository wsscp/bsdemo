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
package cc.oit.bsmes.pla.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.OrderTask;

/**
 * OrderTaskDAO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-1-3 下午2:55:19
 * @since
 * @version
 */
public interface OrderTaskDAO extends BaseDAO<OrderTask> {

	public List<String> getAllLockedOrderItemProDecIds(OrderTask orderTask);

	public List<OrderTask> getOrderTasksLimitByTime(OrderTask orderTask);

	/**
	 * 根据生产单号获取所有未完成任务，按计划开始生产时间排序。
	 * 
	 * @author chanedi
	 * @date 2014年1月6日 下午2:00:34
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<OrderTask> getUncompletedByWoNo(String workOrderNo);

	/**
	 * 获取剩余任务量
	 * 
	 * @author chanedi
	 * @param id
	 * @date 2014年1月13日 下午4:48:58
	 * @return
	 * @see
	 */
	public double getTaskLeft(String id);

	public int deleteByOrgCode(String orgCode);

	/**
	 * 
	 * <p>
	 * 根据生产单ID查询订单任务
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-21 下午5:36:58
	 * @param workOrderId
	 * @return
	 * @see
	 */
	public List<OrderTask> getByWorkOrderId(String workOrderId);

	/**
	 * 
	 * <p>
	 * 根据生产单ID查询订单任务
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-21 下午5:36:58
	 * @param workOrderId
	 * @return
	 * @see
	 */
	public int countByWorkOrderId(String workOrderId);

	/**
	 * 
	 * <p>
	 * 根据生产单号查询最近需要生产的任务
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-2-25 下午5:40:25
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<OrderTask> getRecentByWorkOrderNo(String workOrderNo);

	public List<String> getForOALocked(String orgCode);

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-13 下午4:28:03
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<OrderTask> getProduceByWorkOrderNo(String workOrderNo);

	public List<OrderTask> getByWorkOrderNo(String workOrderNo, String orgCode);

	@MapKey("ORDER_ID")
	public Map<String, Map<String, String>> getOrdersTodayByEquipcodes(String[] equipCodes, String orgCode);

	public OrderTask getCurrentOrder(String equipCode, String orgCode);

	public Double getSumFinishTaskLength(String workOrderNo);

	public void insertOrderTask(@Param("userCode") String userCode, @Param("workOrderNo") String workOrderNo,
			@Param("proDecIdList") List<String> proDecIdList);

	/**
	 * 条件： 颜色 + 生产单号 查询订单任务
	 * 
	 * @param woNo
	 * @param color
	 * @return
	 */
	public List<OrderTask> findByWoNoAndColor(String woNo, String color);

	/**
	 * 
	 * @param params
	 * @return
	 */
	public int changeTask(Map<String, Object> params);

	public List<String> getOrderItemDecIds(Map<String, String> params);

	/**
	 * 
	 * @param params
	 * @return
	 */
	public List<OrderTask> getOrderTasks(Map<String, String> params);

	/**
	 * 取消生产的:ORDER_TASK
	 * */
	public void updateOrderTaskCANCELEDStatusByNo(Map<String, Object> param);

	/**
	 * 更新生产单状态：1、workOrder 2、orderTask 3、proDec
	 * 
	 * @param param workOrderNo:生产单号;status:状态
	 * */
	public void updateWorkerOrderStatus(Map<String, Object> param);

	public String getWorkOrderColors(String workOrderNo);

	public String getContractNo(String workOrderNo);

	public List<OrderTask> getInProgressTask(String workOrderNo, String equipCode);
	
	public List<OrderTask> getFinishedTask(String workOrderNo, String equipCode);

	/**
	 * 查询生产单是否有未完成的任务
	 * 
	 * @param workOrderNo
	 * @return
	 */
	public int countUnFinishTask(String workOrderNo);
	
	public Map<String,String> getOrderTaskId(Map<String,String> param);
	
	public OrderTask getTaskIdByCustOrderItemId(String custOrderItemId);

}
