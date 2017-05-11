package cc.oit.bsmes.pla.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemInfo;

import com.alibaba.fastjson.JSONArray;

/**
 * CustomerOrderItemService
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
public interface CustomerOrderItemService extends BaseService<CustomerOrderItem> {

	/**
	 * 
	 * 查询订单明细
	 * 
	 * @author JinHanyun
	 * @date 2013-12-24 下午4:43:10
	 * @param cusOrderId 客户生产订单Id
	 * @return
	 * @see
	 */
	public List<CustomerOrderItem> getByCusOrderId(String cusOrderId);

	/**
	 * 获取没有完成的订单产品：默认最新导入的300个有工序的订单
	 * 
	 * @param orgCode 组织编码
	 * @param limit 数量限制：默认300
	 * @return List<CustomerOrderItem>
	 */
	public List<CustomerOrderItem> getUncompleted(String orgCode, Integer limit);

	/**
	 * 获取所有未开始订单，并按 顺序、创建时间 排序。
	 * 
	 * @author chanedi
	 * @date 2013年12月25日 下午2:43:42
	 * @param orgCode
	 * @return
	 * @see
	 */
	public List<CustomerOrderItem> getUnLocked(String orgCode);

	/**
	 * 获取第一次生产的订单项（待生产）
	 * 
	 * @author chanedi
	 */
	public List<CustomerOrderItem> getFirstTime(String orgCode);

	/**
	 * 根据订单明细id取消订单明细。详见《订单OA计算程序逻辑设计-取消订单》活动图。
	 * 
	 * @author chanedi
	 * @date 2013年12月24日 下午2:48:57
	 * @param id
	 * @see
	 */
	public void cancel(String id);

	/**
	 * 
	 * 订单管理前台界面数据查询 特殊查询
	 * 
	 * @author JinHanyun
	 * @date 2014-1-14 下午4:56:06
	 * @param findParams
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 * 
	 *      public List<CustomerOrderItemInfo>
	 *      findByOrderInfo(CustomerOrderItemInfo findParams,int start, int
	 *      limit);
	 */

	/**
	 * 
	 * 订单管理前面界记录总数查询
	 * 
	 * @author JinHanyun
	 * @date 2014-1-14 下午5:00:19
	 * @param findParams
	 * @return
	 * @see
	 */
	public int countByOrderInfo(CustomerOrderItemInfo findParams);

	/**
	 * 
	 * 通过orderItemId 查询订单管理修改当前修改记录
	 * 
	 * @author JinHanyun
	 * @date 2014-1-22 上午9:50:58
	 * @param orderItemId
	 * @return
	 * @see
	 */
	public CustomerOrderItem getByOrderItemId(String orderItemId);

	/**
	 * 
	 * 根据工艺ID 查询出工艺流程组装成JSON对象
	 * 
	 * @author JinHanyun
	 * @date 2014-1-22 下午2:24:20
	 * @see
	 */
	public JSONArray craftProcessHandle(String productCode) throws IllegalAccessException, InvocationTargetException;

	public List<CustomerOrderItem> findByOrderIdAndSalesOrderItemInfo(CustomerOrderItem findParams);

	public void itemSplit(String itemId, String subOrderLengths);

	public CustomerOrderItem getByWorkOrderNoAndContractNo(String workOrderNo, String contractNo);
	
	public CustomerOrderItem getBySalesOrderItemId(String salesOrderItemId);

	/**
	 * 下发生产单主列表
	 * 
	 * */
	public List<Map<String, String>> getHandScheduleOrder(Map<String, Object> param);
	
	/**
	 * 
	 * 临时生产单列表
	 */
	public List<Map<String, String>> getHandScheduleOrderTemp(Map<String, Object> param,Integer start,Integer limit);

	/**
	 * 下发生产单主列表:计数
	 * 
	 * */
	public Integer countHandScheduleOrder(Map<String, Object> param);

	/**
	 * 获取未完成的订单明细：根据订单ID
	 * 
	 * @author DingXintao
	 * @param customerOrderId 订单ID
	 */
	public List<CustomerOrderItem> getUncompleteOrderItemByOrderId(String customerOrderId);

	/**
	 * 根据生产单ID查看生产单明细 - 关于工序明细的 - 新通用方法
	 * 
	 * @param workOrderNo 生产单编号
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> showWorkOrderDetailCommon(String workOrderNo);
	
	/**
	 * 获取可以手动排程的订单: 成缆的主列表 - 点击排生产单弹出编辑框时所需要的订单数据
	 * 
	 * @param param 生产单ID数组
	 * @return List<Map<String, String>>
	 * */
	public List<Map<String, String>> getOrderData(Map<String, Object> param);

	/**
	 * 校验是否已经下单：云母、挤出、火花
	 * 
	 * @param orderItemId 订单产品ID
	 * */
	public Map<String, Object> validateHasAuditOrder(String orderItemId);
	
	public void tempSave(String orderItemIds);
	
	public Integer countHandScheduleOrderTemp(Map<String,Object> param);

	/**
	 * 获取最新一条记录，时间最大的一条
	 * 
	 * @author DingXintao
	 * @date 2016-2-24
	 * @return CustomerOrderItem
	 */
	public CustomerOrderItem getLatestOrder();
	
	public void clearTemp(String orderItemIds);

	public List getProductManageList(Map<String, Object> param);

	public Integer countProductManageList(Map<String, Object> param);

	public void finishedOrderItem(String id,String userCode);

	public List<Map<String,String>> getGWInGrocessOrders();

	public List<Map<String, String>> getGWHisWorkOrders(String string);
}
