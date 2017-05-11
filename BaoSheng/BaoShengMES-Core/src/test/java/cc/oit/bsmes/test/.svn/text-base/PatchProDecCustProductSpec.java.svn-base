package cc.oit.bsmes.test;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;

/**
 * 补充T_PLA_CU_ORDER_ITEM_PRO_DEC表CUST_PRODUCT_SPEC字段
 */
public class PatchProDecCustProductSpec extends BaseTest {
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private CustomerOrderItemDecService customerOrderItemDecService;
	@Resource
	private SalesOrderItemService salesOrderItemService;

	/**
	 * bean初始化后执行的方法
	 * */
	@PostConstruct
	public void initMethod() {
	}

	/**
	 * 执行函数
	 * */
	@Test
	@Rollback(false)
	public void process() {
		List<CustomerOrderItemProDec> customerOrderItemProDecArray = customerOrderItemProDecService.getAll();
		if (null == customerOrderItemProDecArray || customerOrderItemProDecArray.size() == 0) {
			return;
		}
		for (CustomerOrderItemProDec customerOrderItemProDec : customerOrderItemProDecArray) {
			String orderItemDecId = customerOrderItemProDec.getOrderItemDecId();
			CustomerOrderItemDec customerOrderItemDec = customerOrderItemDecService.getById(orderItemDecId);
			if (null == customerOrderItemDec) {
				continue;
			}
			String orderItemId = customerOrderItemDec.getOrderItemId();
			CustomerOrderItem customerOrderItem = customerOrderItemService.getById(orderItemId);
			if (null == customerOrderItem) {
				continue;
			}
			String sorderItemId = customerOrderItem.getSalesOrderItemId();
			SalesOrderItem salesOrderItem = salesOrderItemService.getById(sorderItemId);
			if (null == salesOrderItem) {
				continue;
			}
			customerOrderItemProDec.setCustProductSpec(salesOrderItem.getCustProductSpec());
			customerOrderItemProDecService.update(customerOrderItemProDec);
		}

	}

}
