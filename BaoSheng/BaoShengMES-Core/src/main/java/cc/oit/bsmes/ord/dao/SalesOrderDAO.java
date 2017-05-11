package cc.oit.bsmes.ord.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.ord.model.SalesOrder;

/**
 * SalesOrderDAO
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
public interface SalesOrderDAO extends BaseDAO<SalesOrder> {

	public List<SalesOrder> getCustomerCompany(String contractNo, String orgcode);
	
	/**
	 * 根据合同号(不带字母)的后五位匹配订单查询订单信息
	 * @author chenxiang
	 * @param contractNo
	 * @param orgCode
	 * @return
	 */
	public SalesOrder getByContract(String contractNo,String orgCode);
	
	/**
	 * 根据合同号查询查询订单信息
	 * @param salesOrder
	 * @return
	 */
	public SalesOrder getSalesOrderByContractNo(SalesOrder salesOrder);

	public List<SalesOrder> getSalesOrderByGW();
}
