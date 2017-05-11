package cc.oit.bsmes.pla.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.ImportProduct;


/**
 * CustomerOrderService
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
public interface CustomerOrderService extends BaseService<CustomerOrder> {

	/**
	 * 根据{@link SalesOrder#getId()}获取订单，根据顺序和创建时间排序。
	 * 
	 * @author chanedi
	 * @date 2013年12月30日 下午7:03:49
	 * @param salesOrderId
	 * @return
	 * @see
	 */
	public List<CustomerOrder> getBySalesOrderId(String salesOrderId);

	/**
	 * 根据salesOrder保存{@link CustomerOrder}及{@link CustomerOrderItem}信息。
	 * 
	 * @author chanedi
	 * @date 2013年12月24日 下午6:19:02
	 * @param salesOrder
	 * @return true if 保存成功
	 * @see
	 */
	public CustomerOrder insert(SalesOrder salesOrder);

	/**
	 * 将一张订单拆分成targetCount张，每张订单的生产长度均分。（待生产和生产中不允许拆分） 详见《订单OA计算程序逻辑设计-拆分订单》活动图。
	 * 
	 * @author chanedi
	 * @date 2013年12月24日 下午6:08:00
	 * @param customerOrder
	 * @param targetCount
	 * @return 拆分后的订单集合
	 * @see
	 */
	public List<CustomerOrder> split(CustomerOrder customerOrder, int targetCount);

	/**
	 * 删除拆分的订单，删除的量增加到后一个或前一个订单中。（待生产和生产中不允许删除或调整数量）
	 * 同时删除所有关联的对象。关联对象参见《订单OA计算程序逻辑设计-取消订单》活动图。
	 * 
	 * @author chanedi
	 * @date 2013年12月24日 下午6:08:00
	 * @param customerOrder
	 * @see
	 */
	// public boolean deleteAndMerge(CustomerOrder customerOrder);

	/**
	 * 订单管理前天数据查询
	 * 
	 * @author Jin Hanyun
	 * @param findParams
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 */

	public List<CustomerOrder> findByOrderInfo(Map<String, Object> findParams, int start, int limit, List<Sort> sortList);

	public int countByOrderInfo(Map<String, Object> findParams);

	/**
	 * 订单优先级数据查询
	 * 
	 * @author Jin Hanyun
	 * @param findParams
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 */
	public List<CustomerOrder> findForSetPriority(CustomerOrder findParams, int start, int limit, List<Sort> sortList);

	public int countForSetPriority(CustomerOrder findParams);

	public void setCustomerOaDate(CustomerOrder customerOrder, Date customerOaDate);

	/**
	 * 按钮确认交货期
	 * 
	 * @author DingXintao
	 * @date 2014-10-27 17:50:58
	 * @param customerOrderId
	 */
	public void confirmOrderDeliver(String customerOrderId);

	/**
	 * 手动排程：查找可排序的订单
	 * 
	 * @author DingXintao
	 * @param findParams 参数
	 * */
	public List<CustomerOrder> findForHandSetPriority(CustomerOrder findParams);

	/**
	 * 获取未完成的订单：包含了排序
	 * 
	 * @author DingXintao
	 * @param orgCode 组织编码
	 */
	public List<CustomerOrder> getUncompleteCustomerOrder(String orgCode);

	/**
	 * 新的导入方法： 不作分卷，分卷表只有一条数据，不拆分
	 * 
	 * @param salesOrder 订单列表
	 * @param orderItemArray 订单产品存放list，用于解决性能问题，减少数据库查询
	 * @param productCraftsCache 存放产品编码对应工艺ID的缓存，用于解决性能问题，减少数据库查询
	 * */
	public void insertToItemDec(SalesOrder salesOrder, List<SalesOrderItem> orderItemArray, Map<String, String> productCraftsCache);
	/**
	 * 根据产品代码查询线芯结构
	 * @param productCode
	 * @return
	 */
	public String getWiresStructure(String productCode);
	
	
	public List<ImportProduct> getImportProduct(Map<String,Object> param,int start,int limit,List<Sort> sortArray);
	
	public int countImportProduct(Map<String,Object> param);
}
