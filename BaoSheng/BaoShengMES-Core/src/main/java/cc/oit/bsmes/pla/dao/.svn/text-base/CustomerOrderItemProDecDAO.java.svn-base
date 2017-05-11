package cc.oit.bsmes.pla.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;

/**
 * CustomerOrderItemProDecDAO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2013年12月31日 下午2:10:16
 * @since
 * @version
 */
public interface CustomerOrderItemProDecDAO extends BaseDAO<CustomerOrderItemProDec> {

	/**
	 * 
	 * deleteByOrderItemId
	 * 
	 * @author JinHanyun
	 * @date 2014-1-9 上午11:28:03
	 * @param orderItemId 订单明细ID
	 * @param updateUser 更新用户编号
	 * @see
	 */
	public void deleteByOrderItemId(String orderItemId, String updateUser);

	public List<CustomerOrderItemProDec> getByCusOrderItemDecId(String cusOrderItemDecId);

	// public int deleteByCusOrderItemDescId(String cusOrderItemDescId);

	public List<CustomerOrderItemProDec> getByStatus(String orgCode, List<ProductOrderStatus> statuses);

	public List<CustomerOrderItemProDec> getUnaudited(String orgCode, Date endDate);

	public CustomerOrderItemProDec getCurrentByWoNo(String workOrderNo);

	/**
	 * 取消冲抵
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-31 下午4:46:04
	 * @param proDecId
	 * @param updator
	 * @see
	 */
	public void cancelOffset(String proDecId, String updator);

	/**
	 * 启用可选设备
	 * 
	 * @author JinHanyun
	 * @param equipCodes
	 * @param orderItemId
	 * @param processId
	 * @return
	 */
	public int updateOptionalEquipCode(String equipCodes, String orderItemId, String processId);

	public List<CustomerOrderItemProDec> getItemDecInProgress(String orgCode, Date startDate);

	/**
	 * 
	 * <p>
	 * 生产单排序后更新固定设备
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-20 下午4:16:04
	 * @param equipCode
	 * @param workOrderNo
	 * @param userCode
	 * @return
	 * @see
	 */
	public int updateFixEquipForWorkOrderSeq(String equipCode, String workOrderNo, String userCode);

	public List<CustomerOrderItemProDec> getUnFinishedLength(String contractNo, String processId, String orgCode);

	public List<CustomerOrderItemProDec> getByWoNo(String woNo);

	public int checkProDecUseStock(String itemDecId);

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
	public List<CustomerOrderItemProDec> getHandScheduleOrderItemProDec(Map<String, String> param);

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
	 * @param param : processCode 工序; orderItemIdArray 客户订单ID数组
	 * 
	 * @return List<CustomerOrderItemProDec>
	 */
	public List<CustomerOrderItemProDec> getOrderProcessByCodeJY(Map<String, Object> param);

	/**
	 * 
	 * <p>
	 * 排生产单 : 根据工序、客户订单ID获取工序列表 *****成缆工段*****
	 * </p>
	 * 
	 * @author DingXintao
	 * @param param : processCode 工序; orderItemIdArray 客户订单ID数组
	 * 
	 * @return List<CustomerOrderItemProDec>
	 */
	public List<CustomerOrderItemProDec> getOrderProcessByCodeCL(Map<String, Object> param);
	
	/**
	 * 
	 * <p>
	 * 排生产单 : 根据工序、客户订单ID获取工序列表 *****成缆工段*****
	 * </p>
	 * 
	 * @author DingXintao
	 * @param param : processCode 工序; orderItemIdArray 客户订单ID数组
	 * 
	 * @return List<CustomerOrderItemProDec>
	 */
	public List<CustomerOrderItemProDec> getOrderProcessByCodeHT(Map<String, Object> param);

	/**
	 * 插入分解工序信息
	 * 
	 * @param param
	 */
	public void insertOrderItemProDec(Map<String, String> param);

	/**
	 * 根据生产单ID查看生产单明细 - 关于工序明细的
	 * 
	 * @param workOrderNo 生产单编号
	 * @return List<CustomerOrderItemProDec>
	 */
	public List<CustomerOrderItemProDec> showWorkOrderDetail(String workOrderNo);
	
	/**
	 * 根据生产单ID查看生产单明细 - 关于工序明细的
	 * 
	 * @param workOrderNo 生产单编号
	 * @return List<CustomerOrderItemProDec>
	 */
	public List<CustomerOrderItemProDec> showWorkOrderCLDetail(String workOrderNo);
	
	/**
	 * 根据生产单ID查看生产单明细 - 关于工序明细的
	 * 
	 * @param workOrderNo 生产单编号
	 * @return List<CustomerOrderItemProDec>
	 */
	public List<CustomerOrderItemProDec> showWorkOrderHTDetail(String workOrderNo);

	/**
	 * 切换任务时同时修改PRODEC记录状态
	 * @param params
	 * @return
	 */
	public int changeTask(Map<String,Object> params);
	
	public List<Map<String, String>> getFirstProcessByCode(String orderItemIdArray);
	
	public List<CustomerOrderItemProDec> getSwitchHistory(Map<String, Object> param);
	

	/**
	 * PRO_DEC的数据补充，查询产出的质量参数等
	 * @param params 参数: {id PRO_DEC主键, processId 工序ID}
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getPatchOutAttrDescJY(Map<String, String> params);
	/**
	 * PRO_DEC的数据补充，查询产出的质量参数等
	 * @param params 参数: {id PRO_DEC主键, processId 工序ID}
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getPatchOutAttrDescCL(Map<String, String> params);
	/**
	 * PRO_DEC的数据补充，查询产出的质量参数等
	 * @param params 参数: {id PRO_DEC主键, processId 工序ID}
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getPatchOutAttrDescHT(Map<String, String> params);
	
	
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
	
	public List<CustomerOrderItemProDec> getByTodoCusOrderItemId(String cusOrderItemId);
}
