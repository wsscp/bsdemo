package cc.oit.bsmes.history.wip.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.history.wip.model.HisWorkOrder;

public interface HisWorkOrderDAO extends BaseDAO<HisWorkOrder> {

	/**
	 * @Title:       countByOrderItemId
	 * @Description: TODO(查询订单的生产单数量)
	 * @param:       orderItemId 订单ID
	 * @return:      Integer   
	 * @throws
	 */
	public Integer countByOrderItemId(String orderItemId);
}
