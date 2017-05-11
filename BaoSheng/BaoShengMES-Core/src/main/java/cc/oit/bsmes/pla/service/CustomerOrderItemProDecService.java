package cc.oit.bsmes.pla.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.constants.ProcessSection;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;

/**
 * CustomerOrderItemProDecService
 * 
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author JinHanyun
 * @date 2013-12-25 上午10:03:29
 * @since
 * @version
 */
public interface CustomerOrderItemProDecService extends BaseService<CustomerOrderItemProDec> {

	/**
	 * 分解订单到工序
	 * 
	 * @author JinHanyun
	 * @date 2013-12-24 下午5:37:35
	 * @see
	 */
	public void analysisOrderToProcess(ResourceCache resourceCache, String orgCode);

	/**
	 * findByCusOrderItemDescId
	 * 
	 * @author JinHanyun
	 * @date 2013-12-31 下午3:39:27
	 * @param cusOrderItemDecId
	 * @return
	 * @see
	 */
	public List<CustomerOrderItemProDec> getByCusOrderItemDecId(String cusOrderItemDecId);

	/**
	 * 通过分解明细Id 删除订单分解到工序表数据
	 * 
	 * @author JinHanyun
	 * @date 2013-12-31 下午2:55:05
	 * @param cusOrderItemDescId
	 * @see
	 * 
	 *      public void deleteByCusOrderItemDescId(String cusOrderItemDescId);
	 */

	/**
	 * endDate前orgCode组织下所有的未完成任务，并按最晚开始时间 排序，不包括已审核和未确认。
	 * 
	 * @author chanedi
	 * @date 2014年1月8日 上午11:45:36
	 * @param orgCode
	 * @param endDate
	 * @return
	 * @see
	 */
	public List<CustomerOrderItemProDec> getUnaudited(String orgCode, Date endDate);

	/**
	 * 获取当前生产订单。
	 * 
	 * @author chanedi
	 * @param workOrderNo
	 * @return
	 */
	public CustomerOrderItemProDec getCurrentByWoNo(String workOrderNo);

	/**
	 * 
	 * <p>
	 * 取消冲抵
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-31 下午4:50:54
	 * @param proDecId
	 * @param updator
	 * @see
	 */
	public void cancelOffset(String proDecId, String updator);

	/**
	 * 
	 * <p>
	 * 启用可选设备
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-31 下午4:50:54
	 * @see
	 */
	public int updateOptionalEquipCode(String equipCodes, String orderItemId, String processId);

	/**
	 * 获取itemdec状态为inprogress且最晚开始时间在startDate后的
	 */
	public List<CustomerOrderItemProDec> getItemDecInProgress(String orgCode, Date startDate);

	/**
	 * <p>
	 * 生产单排序后更新固定设备
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-20 下午4:16:04
	 * @param equipCode
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public int updateFixEquipForWorkOrderSeq(String equipCode, String workOrderNo);

	/**
	 * <p>
	 * 根据主键更新固定设备equipCode
	 * </p>
	 * 
	 * @author DingXintao
	 * @param id 主键
	 * @param equipCode 设备编码
	 */
	public int updateFixEquipById(String id, String equipCode);

	public List<CustomerOrderItemProDec> getUnFinishedLength(String contractNo, String processId, String orgCode);

	public List<CustomerOrderItemProDec> getByWoNo(String woNo);

	public List<CustomerOrderItemProDec> getLastOrders(String orderId);

	/**
	 * <p>
	 * 获取上层orderTaskId关联的所有的ProDec
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-7-2 9:20:48
	 * @param orderTaskId 订单任务ID
	 * @return List<CustomerOrderItemProDec>
	 */
	public List<CustomerOrderItemProDec> getProDecByOrderTaskId(String orderTaskId);

	/**
	 * 获取手动排程工序下的明细分解
	 */
	public List<CustomerOrderItemProDec> getHandScheduleOrderItemProDec(String orderItemId, String processId);

	/**
	 * 分解产品明细到工序
	 */
	public void splitOrderByOrderItemId(String orderItemId, String orgCode);

	/**
	 * 获取分卷下未完成明细：根据分卷ID
	 * 
	 * @author DingXintao
	 * @param customerOrderItemDecId 分卷ID
	 */
	public List<CustomerOrderItemProDec> getUncompleteOrderItemProDecByDecId(String customerOrderItemDecId);

	/**
	 * 
	 * <p>
	 * 排生产单 : 根据工序、客户订单ID获取工序列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @param section 工段
	 * @param orderItemIdArray 客户订单ID数组
	 * @param preWorkOrderNo 上道生产单号
	 * 
	 * @return List<CustomerOrderItemProDec>
	 */
	public List<CustomerOrderItemProDec> getOrderProcessByCode(ProcessSection section, String[] orderItemIdArray, String preWorkOrderNo);

	public List<Map<String, String>> getFirstProcessByCode(String[] orderItemIdArray);

	/**
	 * 插入分解工序信息
	 * 
	 * @param orderItemId
	 * @param processId
	 * @param userCode
	 */
	public void insertOrderItemProDec(String orderItemId, String processId, String userCode);

	public int changeTask(Map<String, Object> params);

	/**
	 * PRO_DEC的数据补充，查询产出的质量参数等
	 * 
	 * @param processCode 工序编码，判断工段
	 * @param id PRO_DEC主键
	 * @param processId 工序ID
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getPatchOutAttrDesc(String processCode, String id, String processId);

	/**
	 * 对prodec的关联ID：RelateOrderIds做补充
	 * 
	 * @param workOrderNo 生产单号
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> patchProdecRelateOrderIds(String workOrderNo);

	/**
	 * 更新生产单状态：1、workOrder 2、orderTask 3、proDec
	 * 
	 * @param param workOrderNo:生产单号;status:状态
	 * */
	public void updateWorkerOrderStatus(Map<String, Object> param);
	
	/**
	 * 获取to_do状态的CustomerOrderItemProDec
	 * @param cusOrderItemDecId
	 * @return
	 */
	
	public List<CustomerOrderItemProDec> getByTodoCusOrderItemId(String cusOrderItemId);

}
