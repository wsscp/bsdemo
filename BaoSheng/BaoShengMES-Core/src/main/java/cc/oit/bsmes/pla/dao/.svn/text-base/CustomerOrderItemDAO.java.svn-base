package cc.oit.bsmes.pla.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.wip.model.WorkOrder;

/**
 * CustomerOrderItemDAO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
public interface CustomerOrderItemDAO extends BaseDAO<CustomerOrderItem> {

	/**
	 * 获取没有完成的订单产品：默认最新导入的300个有工序的订单
	 * 
	 * @param params：orgCode 组织编码， limit 数量限制：默认300
	 * @return List<CustomerOrderItem>
	 */
	public List<CustomerOrderItem> getUncompleted(Map<String, Object> params);

	public void cancel(String id, String updator, List<WorkOrder> unfinishedWorkOrders);

	public List<CustomerOrderItem> findByOrderIdAndSalesOrderItemInfo(CustomerOrderItem findParams);

	public int countByOrderIdAndSalesOrderItemInfo(CustomerOrderItem findParams);

	public List<CustomerOrderItem> getFirstTime(String orgCode);

	public List<CustomerOrderItem> getUnLocked(String orgCode);

	public CustomerOrderItem getByWorkOrderNoAndContractNo(String workOrderNo, String contractNo);

	/**
	 * 下发生产单主列表
	 * 
	 * */
	public List<Map<String, String>> getHandScheduleOrder(Map<String, Object> param);
	
	/**
	 * 
	 * 临时生产单列表
	 */
	public List<Map<String, String>> getHandScheduleOrderTemp(Map<String, Object> param);

	/**
	 * 下发生产单主列表:计数
	 * 
	 * */
	public Integer countHandScheduleOrder(Map<String, Object> param);

	/**
	 * 获取未完成的订单明细：根据订单ID
	 * 
	 * @author DingXintao
	 * @param customerOrderId 订单Id
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
	 * 排产[成缆/护套]：根据订单ID获取订单明细
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
	
	public List<CustomerOrderItem> checkExistsCustId(String custId);
	
	public List<CustomerOrderItem> checkExistsCustIdTemp(String custId);
	
	public void insertCustId(String custId, String creatorUserCode);
	
	public void insertCustIdTemp(String custId, String creatorUserCode);
	
	public void updateCraftsId(ProductCrafts productCrafts);
	
	public List<CustomerOrderItem> getByProductNo(String productNo);
	
	public Integer countHandScheduleOrderTemp(Map<String,Object> param);
	
	/**
	 * 获取最新一条记录，时间最大的一条
	 * 
	 * @author DingXintao
	 * @date 2016-2-24
	 * @return CustomerOrderItem
	 */
	public CustomerOrderItem getLatestOrder();
	
	public void deleteTempInfo(Map<String,List<String>> map);
	
	public CustomerOrderItem getBySalesOrderItemId(String salesOrderItemId);
	
	public List<CustomerOrderItem> getItems();

	public List getProductManageList(Map<String, Object> param);

	public Integer countProductManageList(Map<String, Object> param);

	public void finishedOrderItem(String id,String  userCode);

	public List<Map<String,String>> getGWInGrocessOrders();

	public List<Map<String, String>> getGWHisWorkOrders(String cusItemId);
}
