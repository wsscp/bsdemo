package cc.oit.bsmes.pla.dao;

import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.ord.dao.SalesOrderDAO;
import cc.oit.bsmes.ord.dao.SalesOrderItemDAO;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

public class CustomerOrderDataInitTest extends BaseTest{
	
	@Resource
	private SalesOrderDAO salesOrderDAO;
	
	@Resource
	private SalesOrderItemDAO salesOrderItemDAO;
	
	@Resource
	private CustomerOrderDAO customerOrderDAO;
	
	@Resource
	private  CustomerOrderItemDAO customerOrderItemDAO;
	
	@Resource
	private CustomerOrderItemDecDAO customerOrderItemDecDAO;

	@Test
	public void test() {
		SalesOrder so = new SalesOrder();
		salesOrderDAO.insert(so);
		
		CustomerOrder co = new CustomerOrder();
		co.setSalesOrderId(so.getId());
		co.setOaDate(new Date());
		co.setConfirmDate(new Date());
		customerOrderDAO.insert(co);
		
		SalesOrderItem soi = new SalesOrderItem();
		salesOrderItemDAO.insert(soi);
		
		CustomerOrderItem coi = new CustomerOrderItem();
		coi.setSalesOrderItemId(soi.getId());
		coi.setOrderLength(5000.0);
		coi.setCustomerOrderId(co.getId());
		coi.setStatus(ProductOrderStatus.TO_DO);
		customerOrderItemDAO.insert(coi);
		
		CustomerOrderItemDec coid = new CustomerOrderItemDec();
		coid.setOrderItemId(coi.getId());
		coid.setLength(1000.0);
		coid.setUseStock(false);
		coid.setStatus(ProductOrderStatus.TO_DO);
		customerOrderItemDecDAO.insert(coid);
		
		coid = new CustomerOrderItemDec();
		coid.setOrderItemId(coi.getId());
		coid.setLength(1000.0);
		coid.setUseStock(false);
		coid.setStatus(ProductOrderStatus.TO_DO);
		customerOrderItemDecDAO.insert(coid);
		
		coid = new CustomerOrderItemDec();
		coid.setOrderItemId(coi.getId());
		coid.setLength(1000.0);
		coid.setUseStock(false);
		coid.setStatus(ProductOrderStatus.TO_DO);
		customerOrderItemDecDAO.insert(coid);
		
		coid = new CustomerOrderItemDec();
		coid.setOrderItemId(coi.getId());
		coid.setLength(2000.0);
		coid.setUseStock(true);
		coid.setStatus(ProductOrderStatus.FINISHED);
		customerOrderItemDecDAO.insert(coid);
	}
}
