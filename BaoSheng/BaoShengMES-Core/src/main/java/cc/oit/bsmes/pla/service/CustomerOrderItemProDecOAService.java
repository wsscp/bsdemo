package cc.oit.bsmes.pla.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDecOA;

/**
 * CustomerOrderItemProDecOAService
 * 
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author DingXintao
 * @date 2015年08月24日 下午2:10:16
 * @since
 * @version
 */
public interface CustomerOrderItemProDecOAService extends BaseService<CustomerOrderItemProDecOA> {

	/**
	 * 根据分卷ID获取工序分解明细
	 * 
	 * @author DingXintao
	 * @date 2015-08-27
	 * 
	 * @param orderItemDecId 分卷ID
	 * */
	public List<CustomerOrderItemProDecOA> getByCusOrderItemDecIdForPlan(String orderItemDecId);

	/**
	 * 删除PRO_DEC_OA及相关数据：根据订单产品ID deleteByOrderItemId
	 * 
	 * @author DingXintao
	 * @date 2014-1-9 上午11:28:03
	 * @param orderItemId 订单明细ID
	 * @param updateUser 更新用户编号
	 * @see
	 */
	public void deleteByOrderItemId(String orderItemId, String updateUser);

	/**
	 * 根基ORDER_ITEM_ID查询PRO_DEC_OA是否已经分解，判断是否要分解 countProDecOa
	 * 
	 * @author DingXintao
	 * @date 2014-1-9 上午11:28:03
	 * @param orderItemId 订单明细ID
	 * @see
	 */
	public int countProDecOa(String orderItemId);

}
