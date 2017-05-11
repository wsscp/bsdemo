package cc.oit.bsmes.wip.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.wip.model.WorkOrder;

/**
 * WorkOrderDAO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2014年1月6日 下午1:43:26
 * @since
 * @version
 */
public interface WorkOrderDAO extends BaseDAO<WorkOrder> {

	public List<WorkOrder> findByRequestMap(Map<String, Object> requestMap);

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-26 下午4:01:32
	 * @param requestMap
	 * @return
	 * @see
	 */
	public Integer countByRequestMap(Map<String, Object> requestMap);

	public List<WorkOrder> getByIds(List<String> ids);

	public List<WorkOrder> getForAudit(String orgCode);

	public void cancelByWorkOrderNo(String workOrderNo, String updator);

	/**
	 * 
	 * <p>
	 * 根据预计开始时间,预计结束时间和设备编号查询生产单列表
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-11 上午10:54:11
	 * @param startTime
	 * @param finishedTime
	 * @param equipCode
	 * @return
	 * @see
	 */
	public List<WorkOrder> getByPreTimeAndEquipCode(Date startTime, Date finishedTime, String equipCode, String orgCode);

	public WorkOrder getCurrentByEquipCode(String equipCode, String orgCode);

	public List<WorkOrder> getRecentByEquipCode(String equipCode, String orgCode);

	public List<WorkOrder> getFinishWorkOrderForTerminal(String equipCode, String orgCode);

	/**
	 * 
	 * <p>
	 * 根据设备号,批次号,加工时段追溯设备加工
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-25 上午11:44:51
	 * @param findParam
	 * @return
	 * @see
	 */
	public List<Map<String, Object>> findForEquipProcessTrace(Map<String, Object> findParam);

	/**
	 * 
	 * <p>
	 * 根据设备号,批次号,加工时段 统计设备加工
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-25 上午11:44:51
	 * @param findParam
	 * @return
	 * @see
	 */
	public int countForEquipProcessTrace(Map<String, Object> findParam);

	/**
	 * 
	 * <p>
	 * 接受任务
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-2-25 下午5:52:41
	 * @param workOrderNo
	 * @see
	 */
	public void updateStatusByNo(String workOrderNo);

	/**
	 * 
	 * <p>
	 * 接受任务
	 * </p>
	 * 
	 * @date 2014-2-25 下午5:52:41
	 * @param workOrderNo
	 * @see
	 */
	public void acceptWorkOrder(String workOrderNo, String updator);

	/**
	 * 
	 * <p>
	 * 结束任务
	 * </p>
	 * 
	 * @date 2014-2-25 下午5:52:41
	 * @param workOrderNo
	 * @see
	 */
	public void finishWorkOrder(String workOrderNo, String updator);

	public void setRelationSeq(String workOrderNo);

	/**
	 * <p>
	 * 根据设备代码和状态获取生产单
	 * </p>
	 * 
	 * @author workOrderNo
	 * @date 2014-5-15 上午11:43:26
	 * @param findParams
	 * @return
	 * @see
	 */
	public List<WorkOrder> getByEquipCodeAndStatus(WorkOrder findParams);

	/**
	 * <p>
	 * 根据设备code获取未排序的生产单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午3:33:26
	 * @param findParams
	 * @return
	 * @see
	 */
	public List<WorkOrder> getDisorderWorkOrderByEquipCode(WorkOrder findParams);

	/**
	 * <p>
	 * 根据设备code 统计未排序的生产单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午3:39:29
	 * @param findParams
	 * @return
	 * @see
	 */
	public int countDisorderWorkOrderByEquipCode(WorkOrder findParams);
	
	// 更细生产单长度百分比
	public int updatePercent(WorkOrder workOrder);

	/**
	 * <p>
	 * 根据设备code 获取已经排序的生产单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午4:06:31
	 * @param findParams
	 * @return
	 * @see
	 */
	public List<WorkOrder> getSeqWorkOrderByEquipCode(WorkOrder findParams);

	/**
	 * <p>
	 * 根据生产单号列表更新固定设备代码
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-21 下午12:04:40
	 * @param param
	 * @see
	 */
	public void updateWorkOrderFixEquip(Map<String, Object> param);

	/**
	 * <p>
	 * 跳过订单操作之更新PRO_DEC表的计算长度
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 14:04:40
	 * @param param workOrderNo
	 * @see
	 */
	public void updateOrderLengthForSkip(String workOrderNo);

	/**
	 * 查询刚刚完成的生产单
	 * 
	 * @param equipCode
	 * @return
	 */
	public List<WorkOrder> getLastByEquipCode(String equipCode, String orgCode);

	/**
	 * 处理一个workOrder下的task proDec
	 * 
	 * @param woId
	 * @param updator
	 * @param isLocked
	 */
	public void auditWorkOrder(String woId, String updator, String isLocked, Date reqDate, String processCode,
			String orgCode, String processId);

	/**
	 * 
	 * @param barCode
	 * @return
	 */
	public WorkOrder getByBarCode(String barCode, String orgCode);

	/**
	 * 重新计算更新物料需求计划时查询生产单
	 * 
	 * @author DingXintao
	 * @date 2014-10-27 15:06:21
	 * @param findParam
	 * @return List<WorkOrder>
	 */
	public List<WorkOrder> getListForUpMrpOfCalculatorOA(WorkOrder findParam);

	public int countToday(String equipCode, String orgCode);

	/**
	 * 查询设备下正在加工和待加工的生产单
	 * 
	 * @param equipCode
	 * @return
	 */
	public List<WorkOrder> getByEquipCode(String equipCode, String orgCode);

	/**
	 * 根据设备编码获取当前加工的产品信息
	 * 
	 * @param params 参数:equipCode 设备编码/equipCode 组织机构
	 * */
	public List<Map<String, String>> getProductByEquipCode(Map<String, String> params);

	public List<WorkOrder> getWorkOrderAndLimitTime(Map map);

	public List<WorkOrder> getFinishedWorkOrder(WorkOrder findParam);

	public List<WorkOrder> getWorkOrderAndProduct(WorkOrder workOrder);

	public List<WorkOrder> getWorkOrderByEquipList(Map<String, Object> map);

	public List<WorkOrder> getProductNameByEquipCode(String equipCode);

	/**
	 * 手动排程 查看生产单
	 * 
	 * @author DingXintao
	 * @param param 参数
	 * @return List<WorkOrder>
	 */
	public List<WorkOrder> findForHandSchedule(Map<String, Object> param);
	

	/**
	 * 手动排程 查看生产单的合同内容
	 * 
	 * @author 王国华
	 * @date 2017-01-18 09:10:45
	 * @param list 参数
	 * @return List<WorkOrder>
	 */
	public List<WorkOrder> findForHandScheduleContract(List<WorkOrder> list);

	/**
	 * 手动排程 查看生产单
	 * 
	 * @author DingXintao
	 * @return List<WorkOrder>
	 */
	public int findForHandScheduleCount(Map<String, Object> param);

	/**
	 * 手动排程 查看生产单 - 排序
	 * 
	 * @author DingXintao
	 * @param equipCode 参数
	 * @return List<WorkOrder>
	 */
	public List<WorkOrder> findForHandScheduleByEquipCode(String equipCode);

	/**
	 * @Title getWOByEquipCodeAndStatus
	 * @Description TODO(根据设备编号 + 订单状态 查询生产单 先按创建时间排序)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年3月18日 下午2:18:00
	 * @param @param findParams 查询参数{equipCode:设备编码/status:状态}
	 * @param @return
	 * @return List<WorkOrder>
	 * @throws
	 */
	public List<WorkOrder> getWOByEquipCodeAndStatus(Map<String, String> findParams);

	/**
	 * @Title countGetWOByEquipCodeAndStatus
	 * @Description TODO(根据设备编号 + 订单状态 查询生产单 计数)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年3月18日 下午2:16:16
	 * @param @param findParams 查询参数{equipCode:设备编码/status:状态}
	 * @return int
	 * @throws
	 */
	public int countWOByEquipCodeAndStatus(Map<String, String> findParams);

	/**
	 * 根据生产单号 查询订单信息
	 * 
	 * @param workOrderNo
	 * @return
	 */
	public List<Map<String, Object>> getWOSalesOrderInfo(String workOrderNo);

	public List<Map<String, Object>> getWOSalesOrderInfoCL(String workOrderNo);

	public List<String> getWorkOrderEquip(String workOrderNo);

	public void updateWorkerOrderIsDispatchByNo(Map<String, Object> param);

	public List<WorkOrder> getWorkOrderBaseInfo(@Param("orderItemId") String orderItemId,
			@Param("section") String section);

	public List<WorkOrder> getWorkOrderFinishDetail(@Param("orderItemId") String orderItemId,
			@Param("workOrderNo") String workOrderNo);

	/**
	 * 上一道工序工序合成JSON字段 [成缆专用]
	 * 
	 * @param workOrderNo 生产单号
	 * @return String
	 */
	public String getProcessesMergedArrayByWorkNo(String workOrderNo);

	/**
	 * 根据其中一个生产单获取整个生产单流程
	 * 
	 * @param workOrderNo 生产单号
	 * @return List<WorkOrder>
	 */
	public List<WorkOrder> getWorkOrderFlowArray(String workOrderNo);

	/**
	 * @Title getAllWorkOrderByOIID
	 * @Description TODO(获取这批所有的相关的生产单信息)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年3月31日 下午1:39:41
	 * @param param:orderItemIdArray 查询参数
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> getAllWorkOrderByOIID(Map<String, Object> param);

	public List<Map<String, String>> getPrintMatList(String workOrderNo);

	public List<WorkOrder> getReceiver(Map<String, Object> param);

	public void saveChangeWorkOrderNoReason(Map<String, Object> param);

	/**
	 * 获取查询下拉框：合同号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getContractNo(Map<String, Object> param);

	/**
	 * 获取查询下拉框：单位
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getCustomerCompany(Map<String, Object> param);

	/**
	 * 获取查询下拉框：经办人
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getOperator(Map<String, Object> param);

	/**
	 * 获取查询下拉框：客户型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getCustproductType(Map<String, Object> param);

	/**
	 * 获取查询下拉框：产品型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getProductType(Map<String, Object> param);

	/**
	 * 获取查询下拉框：产品规格
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getProductSpec(Map<String, Object> param);

	public List<WorkOrder> getWorkOrderProcessName();

	/**
	 * 获取查询下拉框：线芯结构
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<WorkOrder> getWiresStructure(Map<String, Object> param);

	public void insertNewColorData(Map<String, Object> param);

	public void saveTimeAndTempValue(Map<String, String> param);

	public String getContractLengthByWorkOrderNo(String workOrderNo);

	public String getContractLengthByProDecId(String parentId);
	
	//根据生产单号获取与生产单的生产进度
	public String getFinishedPercent(String WoOrderNo,String preWorkOrderNo);

	public List<Map<String, String>> getJJXX(String woNo);
	
	/**
	 * @Title:       getLatestWorkOrderNo
	 * @Description: TODO(获取最新的订单的生产单号)
	 * @param:       orderItemIdArray 订单id
	 * @return:      String   
	 * @throws
	 */
	public WorkOrder getLatestWorkOrder(@SuppressWarnings("rawtypes") Map map);
	
	/**
	 * 更新生产单状态: 排除状态finished的bug
	 * 
	 * @param workOrderNo 生产单号
	 * @param status 状态
	 * */
	public void updateWorkerOrderStatusNotFinished(String workOrderNo, WorkOrderStatus status);

	/**
	 * 根据其中一个生产单(护套）获取编织的任务
	 * 
	 * @param workOrderNo 生产单号
	 * @author 王国华
	 * @date 2017-01-17 09:56:39
	 * @return List<WorkOrder>
	 */
	public List<WorkOrder> getWorkOrderBraidingArray(String workOrderNo);
	
	public List<WorkOrder> changeWorkOrderSeq(List<WorkOrder> list);
	
}
