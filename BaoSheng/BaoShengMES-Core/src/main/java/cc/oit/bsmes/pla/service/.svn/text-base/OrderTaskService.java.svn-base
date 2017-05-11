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
package cc.oit.bsmes.pla.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.common.util.EquipLoadCache;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.wip.dto.MethodReturnDto;

/**
 * OrderTaskService
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-1-3 下午3:18:03
 * @since
 * @version
 */
public interface OrderTaskService extends BaseService<OrderTask> {

	/**
	 * 获取未来三天的订单任务，用于生成订单任务资源甘特图。
	 * 
	 * @author leiwei
	 * @date 2014-1-7 上午10:57:03
	 * @return
	 * @throws Exception
	 * @see
	 */
	public List<OrderTask> getLimitByTime(String orgCode, String fromDate, String toDate) throws Exception;

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
	 * 删除组织下所有。
	 * 
	 * @author chanedi
	 * @date 2014年1月8日 下午7:43:12
	 * @param orgCode
	 * @see
	 */
	public void deleteByOrgCode(String orgCode);

	/**
	 * 判断指定产品是否第一次生产
	 * 
	 * @param productCode 要判断的产品
	 * @param contractNo 要判断的合同号，如果已生产的任务的合同号为contractNo则不算生产过
	 */
	public boolean checkFirstTime(String productCode, String contractNo);

	/**
	 * 生成未来N天的生产单。
	 * 
	 * @author chanedi
	 * @date 2014年1月8日 上午11:13:53
	 * @param resourceCache TODO
	 * @param orgCode
	 * @see
	 */
	public void generate(ResourceCache resourceCache, String orgCode);

	/**
	 * <p>
	 * 根据生产单号获取所有订单任务数据
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-20 下午3:14:27
	 * @param workOrderId
	 * @param sortList
	 * @return
	 * @see
	 */
	public List<OrderTask> getByWorkOrderId(String workOrderId, int start, int limit, List<Sort> sortList);

	/**
	 * <p>
	 * 根据生产单号获取所有订单任务数据
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-23 下午12:58:21
	 * @param workOrderId
	 * @return
	 * @see
	 */
	public List<OrderTask> getByWorkOrderId(String workOrderId);

	/**
	 * <p>
	 * 根据生产单号统计所有订单任务数据
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-23 下午12:58:21
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

	/**
	 * getForOALocked oa计算用，用于排除这些产能已占用的订单
	 * 
	 * @author chanedi
	 * @date 2014-2-28 上午11:28:16
	 * @param orgCode
	 * @return
	 * @see
	 */
	public List<String> getForOALocked(String orgCode);

	/**
	 * 
	 * <p>
	 * 查询当前生产单的已生成 生产中 待生产的任务
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-13 下午4:30:20
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<OrderTask> getProduceByWorkOrderNo(String workOrderNo);

	/**
	 * <p>
	 * 根据生产单号获取所有订单任务数据
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午5:38:29
	 * @param workOrderNo
	 * @see
	 */
	public List<OrderTask> getByWorkOrderNo(String workOrderNo);

	/**
	 * <p>
	 * 根据生产单号和所属组织获得合同号、产品代码和工序ID
	 * </p>
	 * 
	 * @param workOrderNo
	 * @param orgCode
	 * @return
	 */
	public List<OrderTask> getByWorkOrderNo(String workOrderNo, String orgCode);

	/**
	 * @param equipInfo
	 * @param counter 记录上次线程中断时的执行位置，初始为new Counter()
	 * @throws EquipLoadCache.TimeOutException
	 */
	public void fixEquipLoad(EquipInfo equipInfo, EquipLoadCache.Counter counter)
			throws EquipLoadCache.TimeOutException;

	/**
	 * 获取指定设备当前的生产任务，返回原始订单号
	 * 
	 * @param equipCodes
	 * @param orgCode
	 * @return 返回SALEITEMID为key的map,value包括SALEITEMID和PROGRESS两列
	 */
	public Map<String, Map<String, String>> getOrdersTodayByEquipcodes(String[] equipCodes, String orgCode);

	public OrderTask getCurrentOrder(String equipCode, String orgCode);

	/**
	 * 计算生产单已完成的长度
	 * 
	 * @param workOrderNo
	 * @return
	 */
	public Double getSumFinishTaskLength(String workOrderNo);

	public void insertOrderTask(String orderItemId, String userCode, String workOrderNo);

	/**
	 * 新增orderTask与prodec关系表
	 * 
	 * @param userCode 用户编码
	 * @param workOrderNo 生产单号
	 * @param proDecIdList TODO
	 * 
	 * @return
	 */
	public void insertOrderTask(String userCode, String workOrderNo, List<String> proDecIdList);

	/**
	 * 新增orderTask与prodec关系表
	 * 
	 * @param customerOrderItemProDec 参数对象
	 * @param userCode 用户编码
	 * @param workOrderNo 生产单号
	 * @return
	 */
	public void insertOrderTask(CustomerOrderItemProDec customerOrderItemProDec, String orgCode, String userCode,
			String workOrderNo);

	/**
	 * 条件： 颜色 + 生产单号 查询订单任务
	 * 
	 * @param woNo
	 * @param color
	 * @return
	 */
	public List<OrderTask> findByWoNoAndColor(String woNo, String color);

	/**
	 * 切换任务
	 * 
	 * @return
	 */
	public MethodReturnDto changeTask(String[] ids, String status, String equipCode);

	/**
	 * 根据生产单号查询相关的订单明细ID ADD BY JINHANYUN
	 * 
	 * @return
	 */
	public List<String> getOrderItemDecIds(String orderTaskId, String workOrderNo, String color);

	/**
	 * 
	 * @param orderTaskId
	 * @param workOrderNo
	 * @param color
	 * @return
	 */
	public List<OrderTask> getOrderTasks(String orderTaskId, String workOrderNo, String color);

	/**
	 * 更新生产单状态：1、workOrder 2、orderTask 3、proDec
	 * 
	 * @param param workOrderNo:生产单号;status:状态
	 * */
	public void updateWorkerOrderStatus(Map<String, Object> param);

	/**
	 * 取消生产的:ORDER_TASK
	 * */
	public void updateOrderTaskCANCELEDStatusByNo(Map<String, Object> param);

	/**
	 * 查询当前生产单 正在生产中任务的颜色
	 * 
	 * @param workOrderNo
	 * @return
	 */
	public String getWorkOrderColors(String workOrderNo);

	public String getContractNo(String workOrderNo);

	public List<OrderTask> getInProgressTask(String workOrderNo, String equipCode);
	
	public List<OrderTask> getFinishedTask(String workOrderNo, String equipCode);

	public int countUnFinishTask(String workOrderNo);
	
	public OrderTask getTaskIdByCustOrderItemId(String custOrderItemId);
}
