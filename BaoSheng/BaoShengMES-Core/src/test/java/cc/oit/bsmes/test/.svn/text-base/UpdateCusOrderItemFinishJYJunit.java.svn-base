package cc.oit.bsmes.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.wip.model.WorkCusorderRelation;
import cc.oit.bsmes.wip.service.WorkCusorderRelationService;

/**
 * 更新订单(CustomerOrderItem)的FINISH_JY字段junit测试
 */
public class UpdateCusOrderItemFinishJYJunit extends BaseTest {
	@Resource
	private WorkCusorderRelationService workCusorderRelationService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;

	/**
	 * 删除生产单
	 * */
	@Test
	@Rollback(false)
	public void process() {

		WorkCusorderRelation workCusorderRelation = new WorkCusorderRelation();
		workCusorderRelation.setProcessCode("Respool");
		List<WorkCusorderRelation> list = workCusorderRelationService.findByObj(workCusorderRelation);
		for (WorkCusorderRelation w : list) {
			CustomerOrderItem customerOrderItem = customerOrderItemService.getById(w.getCusOrderItemId());
			SalesOrderItem salesOrderItem = customerOrderItem.getSalesOrderItem();
			if (salesOrderItem.getCustProductType().indexOf("YJ") < 0) {
				customerOrderItem.setFinishJy(true);
				customerOrderItemService.update(customerOrderItem);
			}
		}

		workCusorderRelation.setProcessCode("Steam-Line");
		list = workCusorderRelationService.findByObj(workCusorderRelation);
		for (WorkCusorderRelation w : list) {
			CustomerOrderItem customerOrderItem = customerOrderItemService.getById(w.getCusOrderItemId());
			customerOrderItem.setFinishJy(true);
			customerOrderItemService.update(customerOrderItem);
		}

	}

}
