package cc.oit.bsmes.pla.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;

/**
 * CustomerOrderItemDecService
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author JinHanyun
 * @date 2013-12-24 下午4:46:09
 * @since
 * @version
 */
public interface CustomerOrderItemDecService extends BaseService<CustomerOrderItemDec> {

	public void insertByItem(CustomerOrderItem customerOrderItem, double standardLength, double leftLength,
			double idealMinLength);

	/**
	 * 根据订单明细ID查询订单明细分解记录集合
	 * 
	 * @author JinHanyun
	 * @date 2013-12-24 下午4:58:57
	 * @param orderItemId 订单明细ID
	 * @return
	 * @see
	 */
	public List<CustomerOrderItemDec> getByOrderItemId(String orderItemId);

	/**
	 * 
	 * <p>
	 * 订单拆分
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-4 上午10:58:19
	 * @see
	 */
	public void splitCustomerOrderItem(String deleteJsonData, String updateJsonData, String offsetJsonData,
			String orderItemId, String offsetItemDecId, Double offsetLength);

	public CustomerOrderItemDec getCurrentByWoNo(String workOrderNo);

	/**
	 * 
	 * <p>
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-31 下午4:38:50
	 * @param itemDecId
	 * @param updator
	 * @see
	 */
	public void deleteCusOrderItemDecById(String itemDecId, String updator, String isDelItemDec);

	/**
	 * 获取未完成的订单明细分卷：根据订单明细ID
	 * 
	 * @author DingXintao
	 * @param customerOrderItemId 订单明细ID
	 */
	public List<CustomerOrderItemDec> getUncompleteOrderItemDecByItemId(String customerOrderItemId);

	public String getCustomerOrderItemDecIdByOrderItemId(String orderItemId);

	/**
	 * 新的导入方法： 不作分卷，分卷表只有一条数据，不拆分
	 * */
	public void insertByItemToItemDec(CustomerOrderItem customerOrderItem, double standardLength, double leftLength,
			double idealMinLength);
}
