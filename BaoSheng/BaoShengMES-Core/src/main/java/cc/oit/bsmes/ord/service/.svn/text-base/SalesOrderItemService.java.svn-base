package cc.oit.bsmes.ord.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.wip.model.FinishedOrder;


/**
 * SalesOrderService
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
public interface SalesOrderItemService extends BaseService<SalesOrderItem> {
	
	/**
	 * 根据{@link SalesOrder#getId()}获取所有{@link SalesOrderItem}。
	 * 
	 * @author chanedi
	 * @date 2013年12月24日 下午2:47:34
	 * @param salesOrderId
	 * @return
	 * @see
	 */
	public List<SalesOrderItem> getBySalesOrderId(String salesOrderId);
	
	/**
	 * 根据{@link CustomerOrderItem#getId()}获取所有{@link SalesOrderItem}。
	 * 
	 * @author chanedi
	 * @date 2013年12月24日 下午2:47:34
	 * @param salesOrderId
	 * @return
	 * @see
	 */
	public SalesOrderItem getByCustomerOrderItemId(String customerOrderItemId);

	public List<SalesOrderItem> getByWorkOrder(String workOrderNo);

	public String searchCraftsId(String productCode,String productType,String productSpec);
	
	public String dataSeparationFunction(String craftsId,String salesOrderItemId);
	
	// 更换工艺：删除旧WIP表工艺数据，添加新WIP表工艺数据
	// @param oldCraftsId 旧工艺ID[WIP]
	// @param newCraftsId 新工艺ID[实例库:不带后缀]
	// @param salesOrderItemId 订单明细id
	public String changeCrafts(String oldCraftsId,String newCraftsId,String salesOrderItemId);
	
	// 删除原工艺数据
	// @param oldCraftsId 工艺ID
	public void removeCrafts(String oldCraftsId);

	public String getLastProcessCode(String salesOrderItemId);

	public Map<String,Object> getReportLength(String string);

	public void insertToFinishedOrder(FinishedOrder order);

	public List<Map<String, String>> getFinishedGWData(Map<String, Object> param);
	
}
