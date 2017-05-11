package cc.oit.bsmes.pla.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pla.dao.CustomerOrderItemProDecOADAO;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDecOA;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecOAService;

/**
 * 
 * @author DingXintao
 * @date 2015年08月24日 下午2:10:16
 * @since
 * @version
 */
@Service
public class CustomerOrderItemProDecOAServiceImpl extends BaseServiceImpl<CustomerOrderItemProDecOA> implements
		CustomerOrderItemProDecOAService {
	@Resource
	private CustomerOrderItemProDecOADAO customerOrderItemProDecOADAO;
	
	/**
	 *  根据分卷ID获取工序分解明细
	 *  @author DingXintao
	 *  @date 2015-08-27
	 *  
	 *  @param orderItemDecId 分卷ID
	 * */
	public List<CustomerOrderItemProDecOA> getByCusOrderItemDecIdForPlan(String orderItemDecId){
		return customerOrderItemProDecOADAO.getByCusOrderItemDecIdForPlan(orderItemDecId);
	}
	
	/**
	 * 删除PRO_DEC_OA及相关数据：根据订单产品ID
	 * deleteByOrderItemId
	 * 
	 * @author DingXintao
	 * @date 2014-1-9 上午11:28:03
	 * @param orderItemId 订单明细ID
	 * @param updateUser 更新用户编号
	 * @see
	 */
	public void deleteByOrderItemId(String orderItemId, String updateUser){
		customerOrderItemProDecOADAO.deleteByOrderItemId(orderItemId, updateUser);
	}
	

	/**
	 * 根基ORDER_ITEM_ID查询PRO_DEC_OA是否已经分解，判断是否要分解 countProDecOa
	 * 
	 * @author DingXintao
	 * @date 2014-1-9 上午11:28:03
	 * @param orderItemId 订单明细ID
	 * @see
	 */
	public int countProDecOa(String orderItemId){
		return customerOrderItemProDecOADAO.countProDecOa(orderItemId);
	}
	
}