package cc.oit.bsmes.test;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.wip.model.WorkCusorderRelation;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkCusorderRelationService;
import cc.oit.bsmes.wip.service.WorkOrderService;

/**
 * 补充生产单与订单产品关系表 T_WIP_WO_CUSORDER_RELATION
 */
public class PatchWoCusorderRelation extends BaseTest {
	@Resource
	private WorkCusorderRelationService workCusorderRelationService;
	@Resource
	private WorkOrderService workOrderService;

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
	public void process() throws BiffException, IOException {
		try {
			List<WorkOrder> workOrderArray = workOrderService.getAll();
			for (WorkOrder workOrder : workOrderArray) {
				if (StringUtils.isNotEmpty(workOrder.getCusOrderItemIds())) {
					for (String cusOrderItemId : workOrder.getCusOrderItemIds().split(",")) {
						WorkCusorderRelation workCusorderRelation = new WorkCusorderRelation();
						workCusorderRelation.setWorkOrderId(workOrder.getId());
						workCusorderRelation.setWorkOrderNo(workOrder.getWorkOrderNo());
						workCusorderRelation.setCusOrderItemId(cusOrderItemId);
						workCusorderRelation.setProcessCode(workOrder.getProcessCode());
						workCusorderRelationService.insert(workCusorderRelation);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
