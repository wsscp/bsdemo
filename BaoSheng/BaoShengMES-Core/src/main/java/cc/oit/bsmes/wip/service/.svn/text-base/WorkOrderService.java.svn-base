package cc.oit.bsmes.wip.service;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import jxl.write.WriteException;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.wip.dto.MethodReturnDto;
import cc.oit.bsmes.wip.model.WorkOrder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface WorkOrderService extends BaseService<WorkOrder> {

	/**
	 * 
	 * <p>
	 * 根据页面查询条件查找数据
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-17 下午2:17:04
	 * @param requestMap
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see
	 */
	public List<WorkOrder> findByRequestMap(Map<String, Object> requestMap, int start, int limit, List<Sort> sortList);

	/**
	 * <p>
	 * 根据页面查询条件查找数据
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2014-1-26 下午4:00:06
	 * @param requestMap
	 * @return
	 * @see
	 */
	public Integer countByRequestMap(Map<String, Object> requestMap);

	/**
	 * <p>
	 * 字典查询下拉框
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-7-15 11:12:48
	 * @return JSON
	 * @see
	 */
	public JSON getWorkOrderStatusCombo();

	/**
	 * 
	 * <p>
	 * 根据生产单号查询订单任务信息
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-20 下午6:05:01
	 * @param workOrderId
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see
	 */
	public List<OrderTask> getSubListWorkOrderId(String workOrderId, int start, int limit, List<Sort> sortList);

	/**
	 * 
	 * <p>
	 * 审核生产单
	 * </p>
	 * <p>
	 * 生成物料需求清单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-23 下午12:02:18
	 * @return
	 * @see
	 */
	public List<WorkOrder> auditWorkOrder();

	/**
	 * 
	 * <p>
	 * 取消生产单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-23 下午12:02:18
	 * @param workOrderList
	 * @return
	 * @see
	 */
	public void cancelWorkOrder(List<WorkOrder> workOrderList);

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-26 下午3:58:30
	 * @param workOrderId
	 * @return
	 * @see
	 */
	int countSubListWorkOrderId(String workOrderId);

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-11 下午5:50:31
	 * @param equipCode
	 * @param status
	 * @return
	 * @see
	 */
	List<WorkOrder> getByEquipCodeAndStatus(String equipCode, WorkOrderStatus status);

	/**
	 * 获取设备上的当前生产单。
	 * 
	 * @author chanedi
	 * @date 2014年2月14日 下午2:26:53
	 * @param equipCode
	 * @return
	 * @see
	 */
	public WorkOrder getCurrentByEquipCode(String equipCode);

	/**
	 * 获取最近的生产单。
	 * 
	 * @author chanedi
	 * @date 2014年2月14日 下午6:05:40
	 * @param equipCode
	 * @see
	 */
	public List<WorkOrder> getRecentByEquipCode(String equipCode, String status);

	/**
	 * <p>
	 * 向上调整生产单顺序
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-19 下午4:20:10
	 * @param equipCode
	 * @param workOrderId
	 * @throws ParseException
	 * @see
	 */
	public void upWorkOrder(String equipCode, String workOrderId) throws ParseException;

	/**
	 * <p>
	 * 向下调整生产单顺序
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-19 下午4:20:10
	 * @param equipCode
	 * @param workOrderId
	 * @throws ParseException
	 * @see
	 */
	public void downWorkOrder(String equipCode, String workOrderId) throws ParseException;

	/**
	 * 
	 * <p>
	 * 成品/半成品称重
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-19 上午10:22:46
	 * @param workOrderNO
	 * @param userCode
	 * @param weight
	 * @see
	 */
	public void productWeightSave(String workOrderNO, String userCode, String weight);

	/**
	 * 
	 * <p>
	 * 废料称重
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-19 上午10:22:46
	 * @param workOrderNO
	 * @param userCode
	 * @param weight
	 * @see
	 */
	public void scrapWeightSave(String matCode, String workOrderNO, String userCode, String weight);

	/**
	 * <p>
	 * 单位称重
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-19 下午5:06:32
	 * @param workOrderNO
	 * @param userCode
	 * @param weight
	 * @see
	 */
	public void unitWeightSave(String workOrderNO, String userCode, String weight);

	/**
	 * <p>
	 * 实际投放物料
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-21 下午4:45:52
	 * @param workOrderNO
	 * @param batchNo
	 * @return TODO
	 * @see
	 */
	public JSONObject feedMaterial(String workOrderNO, String batchNo, String operator, String orderTaskId, String color);

	/**
	 * <p>
	 * 根据设备号,批次号,加工时段 统计设备加工
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-25 下午12:04:16
	 * @param findParam
	 * @return
	 * @see
	 */
	public int countForEquipProcessTrace(Map<String, Object> findParam);

	/**
	 * <p>
	 * 根据设备号,批次号,加工时段追溯设备加工
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-25 下午12:06:26
	 * @param findParam
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see
	 */
	public List findForEquipProcessTrace(Map<String, Object> findParam, int start, int limit, List<Sort> sortList);

	/**
	 * 
	 * <p>
	 * 通过工单号查询工单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-26 上午11:55:23
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public WorkOrder getByWorkOrderNO(String workOrderNo);

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

	// /**
	// * <p>加工工单</p>
	// * @author QiuYangjun
	// * @date 2014-3-5 上午11:30:41
	// * @param workOrderNo
	// * @param receiptArray
	// * @see
	// */
	// public void processWorkOrder(String workOrderNo, List<Receipt>
	// receiptArray);
	/**
	 * <p>
	 * 根据工单号修改设备状态
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-4-23 下午3:18:25
	 * @param workOrderNo
	 * @see
	 */
	void changeEquipStatus(String equipCode, String operator);

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
	 * <p>
	 * 根据设备code获取未排序的生产单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午3:35:12
	 * @param equipCode
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see
	 */
	public List<WorkOrder> getDisorderWorkOrderByEquipCode(String equipCode, int start, int limit, List<Sort> sortList);

	/**
	 * <p>
	 * 根据设备code 统计未排序的生产单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午3:39:55
	 * @param equipCode
	 * @return
	 * @see
	 */
	public int countDisorderWorkOrderByEquipCode(String equipCode);
	
	// 更细生产单长度百分比
	public int updatePercent(WorkOrder workOrder);
	
	/**
	 * <p>
	 * 根据设备code 获取已经排序的生产单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午4:03:11
	 * @param equipCode
	 * @return
	 * @see
	 */
	public List<WorkOrder> getSeqWorkOrderByEquipCode(String equipCode);

	/**
	 * <p>
	 * 将生产单加入排序表中
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午5:13:25
	 * @param workOrderNos
	 * @see
	 */
	public void insertToPriorityByWorkOrders(List<String> workOrderNos);

	/**
	 * <p>
	 * 将生产单从排序表移除
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午6:05:38
	 * @param workOrderNos
	 * @see
	 */
	public void removeFromPriorityByWorkOrders(List<String> workOrderNos);

	/**
	 * <p>
	 * 更新设备上生产单顺序
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-20 下午1:55:26
	 * @param updateSeq
	 * @see
	 */
	public void updateSeq(String updateSeq);

	/**
	 * <p>
	 * 生产单设置固定设备
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-21 下午6:14:55
	 * @param workOrderNo
	 * @param fixedEquipCode
	 * @return
	 * @see
	 */
	public WorkOrder updateFixedEquipByWorkOrderNo(String workOrderNo, String fixedEquipCode);

	public WorkOrder getLastByEquipCode(String equipCode);

	public void export(OutputStream os, String sheetName, JSONArray columns, Map<String, Object> findParams)
			throws IOException, WriteException, InvocationTargetException, IllegalAccessException;

	/**
	 * @Title:       checkWorkOrderIsFinished
	 * @Description: TODO((报工界面: 完成生产单按钮:校验生产单是否全部完成)
	 * @param:       workOrderNo 生产单号
	 * @param:       equipCode 设备编码
	 * @param:       operator 操作人
	 * @return:      MethodReturnDto   
	 * @throws
	 */
	public MethodReturnDto checkWorkOrderIsFinished(String workOrderNo, String equipCode, String operator);
	
	/**
	 * @Title:       finishWorkOrder
	 * @Description: TODO((报工界面: 完成生产单按钮:完成生产单)
	 * @param:       workOrderNo 生产单号
	 * @param:       equipCode 设备编码
	 * @param:       operator 操作人
	 * @return:      MethodReturnDto   
	 * @throws
	 */
	public MethodReturnDto finishWorkOrder(String workOrderNo, String equipCode, String operator);

	public List<WorkOrder> getPausedWorkOrders(String orgCode);

	/**
	 * 
	 * @param barCode
	 * @return
	 */
	public WorkOrder getByBarCode(String barCode, String orgCode);

	public int countToday(String equipCode, String orgCode);

	public List<WorkOrder> getByEquipCode(String equipCode);

	/**
	 * 根据设备编码获取当前加工的产品信息
	 * 
	 * @param equipCode 设备编码
	 * @return List<Map<String,String>>
	 * */
	public List<Map<String, String>> getProductByEquipCode(String equipCode);

	public Map<String, Object> getProductsCoordinate(WorkOrder workOrder);

	public List<WorkOrder> getWorkOrderAndProduct(WorkOrder workOrder);

	public List<WorkOrder> getWorkOrderByEquipList(Map<String, Object> map);

	/**
	 * 手动排程 查看生产单/成缆护套下单界面
	 * 
	 * @author DingXintao
	 * @return List<WorkOrder>
	 */
	public List<WorkOrder> findForHandSchedule(Map<String, Object> param);

	/**
	 * 手动排程 查看生产单
	 * 
	 * @author DingXintao
	 * @return List<WorkOrder>
	 */
	public int findForHandScheduleCount(Map<String, Object> param);

	/**
	 * 手动排程 查看生产单 - 根据设备编码
	 * 
	 * @author DingXintao
	 * @param equipCode 设备编码
	 * @return List<WorkOrder>
	 */
	public List<WorkOrder> findForHandScheduleByEquipCode(String equipCode);

	/**
	 * @Title getWOByEquipCodeAndStatus
	 * @Description TODO(根据设备编号 + 订单状态 查询生产单 先按创建时间排序)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年3月18日 下午2:15:26
	 * @param @param equipCode 设备编码
	 * @param @param status 状态
	 * @param @param start 开始数
	 * @param @param limit 每页数限制
	 * @param @param sortList 排序
	 * @return List<WorkOrder>
	 * @throws
	 */
	public List<WorkOrder> getWOByEquipCodeAndStatus(String equipCode, String status, int start, int limit,
			List<Sort> sortList);

	/**
	 * @Title countGetWOByEquipCodeAndStatus
	 * @Description TODO(根据设备编号 + 订单状态 查询生产单 计数)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年3月18日 下午2:16:16
	 * @param @param equipCode 设备编码
	 * @param @param status 状态
	 * @return int
	 * @throws
	 */
	public int countGetWOByEquipCodeAndStatus(String equipCode, String status);

	// /**
	// * 绝缘查看生产单grid业务
	// * @param equipCode
	// * @param status
	// * @return
	// */
	// public List<WorkOrder> getJYWOByEquipCodeAndStatus(String
	// equipCode,String status,int start, int limit, List<Sort> sortList);

	/**
	 * 根据生产单号 查询订单信息 返回 （订单Item ID,合同号，型号规格，投产长度，界面，厚度）
	 * 
	 * @param workOrderNo
	 * @return
	 */
	public List<Map<String, Object>> getWOSalesOrderInfo(String workOrderNo, String section);

	/**
	 * 
	 * @param workOrderNum
	 * @return
	 */
	public List<String> getWorkOrderEquipByWoNum(String workOrderNum);

	/**
	 * 更新生产单状态：1、workOrder 2、orderTask 3、proDec
	 * 
	 * @param workOrderNo 生产单号
	 * @param status 状态
	 * */
	public MethodReturnDto updateWorkerOrderStatus(String workOrderNo, WorkOrderStatus status);

	public void updateWorkerOrderIsDispatchByNo(String workOrderNo); // 生产单设置为急件

	public List<WorkOrder> getWorkOrderBaseInfo(String orderItemId, String section);

	public List<WorkOrder> getWorkOrderFinishDetail(String orderItemId, String workOrderNo);

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
	 * @param orderItemIdArray 查询参数
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	public List<Map<String, Object>> getAllWorkOrderByOIID(String[] orderItemIdArray);

	/**
	 * 
	 * @param workOrderNo
	 * @return
	 */
	public List<Map<String, String>> getPrintMatList(String workOrderNo);

	public void saveChangeWorkOrderNoReason(Map<String, Object> param);

	public void saveTimeAndTempValue(Map<String, String> param);

	public String getContractLengthByWorkOrderNo(String workOrderNo);

	public String getContractLengthByProDecId(String parentId);
	
	//根据生产单号获取该生产单的生产进度
	public String getFinishedPercent(String woOrderNo,String preWorkOrderNo);
	
	public Map<String, List<Object>> refreshSingle(String equipCode,String workOrderNo);
	
	/**
	 * @Title:       getLatestWorkOrderNo
	 * @Description: TODO(获取最新的订单的生产单号)
	 * @param:       orderItemIdArray 订单id
	 * @return:      String   
	 * @throws
	 */
	public WorkOrder getLatestWorkOrder(List<String> orderItemIdArray);
	
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
	
	public List<WorkOrder> changeWorkOrderSeq(String equipCode);
}
