package cc.oit.bsmes.history.wip.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.history.wip.dao.HisWorkOrderDAO;
import cc.oit.bsmes.history.wip.model.HisWorkOrder;
import cc.oit.bsmes.history.wip.service.HisWorkOrderService;

@Service
public class HisWorkOrderServiceImpl extends BaseServiceImpl<HisWorkOrder> implements HisWorkOrderService {
	@Resource
	private HisWorkOrderDAO hisWorkOrderDAO;
	
	/**
	 * @Title:       countByOrderItemId
	 * @Description: TODO(查询订单的生产单数量)
	 * @param:       orderItemId 订单ID
	 * @return:      Integer   
	 * @throws
	 */
	public Integer countByOrderItemId(String orderItemId){
		return hisWorkOrderDAO.countByOrderItemId(orderItemId);
	}
	
}
