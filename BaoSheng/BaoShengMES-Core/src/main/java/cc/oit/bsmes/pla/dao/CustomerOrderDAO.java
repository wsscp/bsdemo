package cc.oit.bsmes.pla.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.ImportProduct;


/**
 * CustomerOrderDAO
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
public interface CustomerOrderDAO extends BaseDAO<CustomerOrder> {
	
	public List<CustomerOrder> getByIds(String[] ids);

	public List<CustomerOrder> getBySalesOrderId(String salesOrderId);

	public int deleteAndMerge(String orderIdToDelete, String orderIdToAddQuantity, String updator);

	public int split(String orderId, int targetCount, String updator, List<CustomerOrder> splited);

    public List<CustomerOrder> findByOrderInfo(Map<String,Object> findParams);

    public int countByOrderInfo(Map<String,Object> findParams);

    public List<CustomerOrder> findForSetPriority(CustomerOrder findParams);

    public int countForSetPriority(CustomerOrder findParams);
    
    /**
	 * 手动排程：查找可排序的订单
	 * 
	 * @author DingXintao
	 * @param findParams
	 *            参数
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
	 * 根据产品代码查询线芯结构
	 * @param productCode
	 * @return
	 */
	public String getWiresStructure(String productCode);
	
	public List<ImportProduct> getImportProduct(Map<String,Object> param);
	
	public int countImportProduct(Map<String,Object> param);
}
