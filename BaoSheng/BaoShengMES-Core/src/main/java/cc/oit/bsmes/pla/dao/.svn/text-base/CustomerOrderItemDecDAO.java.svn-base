package cc.oit.bsmes.pla.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;

/**
 * CustomerOrderItemDecDAO
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月31日 下午2:10:07
 * @since
 * @version
 */
public interface CustomerOrderItemDecDAO extends BaseDAO<CustomerOrderItemDec> {
	
	public List<CustomerOrderItemDec> getByOrderItemId(String orderItemId);
	
	public String getCustomerOrderItemDecIdByOrderItemId(String orderItemId);

    public CustomerOrderItemDec getCurrentByWoNo(String workOrderNo);
    
    /** 
     * 
     * <p></p> 
     * @author JinHanyun
     * @date 2014-3-31 下午4:38:50
     * @param itemDecId
     * @param updator
     * @see
     */
    public void deleteCusOrderItemDecById(String itemDecId,String updator,String isDelItemDec);
    
    /**
	 * 获取未完成的订单明细分卷：根据订单明细ID
	 * 
	 * @author DingXintao
	 * @param customerOrderItemId 订单明细ID
	 */
	public List<CustomerOrderItemDec> getUncompleteOrderItemDecByItemId(String customerOrderItemId);
	
}
