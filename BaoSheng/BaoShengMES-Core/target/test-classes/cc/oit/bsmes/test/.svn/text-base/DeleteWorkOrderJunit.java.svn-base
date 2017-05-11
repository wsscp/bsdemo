package cc.oit.bsmes.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.wip.model.WorkCusorderRelation;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.model.WorkOrderEquipRelation;
import cc.oit.bsmes.wip.service.WorkCusorderRelationService;
import cc.oit.bsmes.wip.service.WorkOrderEquipRelationService;
import cc.oit.bsmes.wip.service.WorkOrderService;

/**
 * 删除生产单的junit测试
 */
/**
 * @ClassName:   DeleteWorkOrderJunit
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author:      DingXintao
 * @date:        2016年4月7日 下午5:10:47
 */
public class DeleteWorkOrderJunit extends BaseTest {
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private WorkCusorderRelationService workCusorderRelationService;
	@Resource
	private WorkOrderEquipRelationService workOrderEquipRelationService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private MaterialRequirementPlanService materialRequirementPlanService;

	public String[] workOrderNoArray = {"20160411160126856"};
	
	
	
	
	
	/**
	 * 删除生产单
	 * */
	/**
	 * @Title       process
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 * @author      DinXintao
	 * @version     V1.0 
	 * @date        2016年4月7日 下午5:10:35
	 * @param          
	 * @return      void   
	 * @throws
	 */
	@Test
	@Rollback(false)
	public void process() {
		for (String workOrderNo : workOrderNoArray) {
			WorkOrder workOrder = workOrderService.getByWorkOrderNO(workOrderNo);

			WorkCusorderRelation findParam1 = new WorkCusorderRelation();
			findParam1.setWorkOrderId(workOrder.getId());
			List<WorkCusorderRelation> workCusorderRelationArray = workCusorderRelationService.getByObj(findParam1);

			WorkOrderEquipRelation findParam2 = new WorkOrderEquipRelation();
			findParam2.setWorkOrderId(workOrder.getId());
			List<WorkOrderEquipRelation> workOrderEquipRelationArray = workOrderEquipRelationService
					.getByObj(findParam2);

			CustomerOrderItemProDec findParam3 = new CustomerOrderItemProDec();
			findParam3.setWorkOrderNo(workOrder.getWorkOrderNo());
			findParam3.setUsedStockLength(null);
			findParam3.setFinishedLength(null);
			List<CustomerOrderItemProDec> customerOrderItemProDecArray = customerOrderItemProDecService
					.getByObj(findParam3);

			OrderTask findParam4 = new OrderTask();
			findParam4.setWorkOrderNo(workOrder.getWorkOrderNo());
			List<OrderTask> orderTaskArray = orderTaskService.getByObj(findParam4);

			MaterialRequirementPlan findParam5 = new MaterialRequirementPlan();
			findParam5.setWorkOrderId(workOrder.getId());
			List<MaterialRequirementPlan> materialRequirementPlanArray = materialRequirementPlanService
					.getByObj(findParam5);

			// System.out.println("workCusorderRelationArray:" +
			// workCusorderRelationArray.size());
			// System.out.println("workOrderEquipRelationArray:" +
			// workOrderEquipRelationArray.size());
			// System.out.println("customerOrderItemProDecArray:" +
			// customerOrderItemProDecArray.size());
			// System.out.println("orderTaskArray:" + orderTaskArray.size());
			// System.out.println("materialRequirementPlanArray:" +
			// materialRequirementPlanArray.size());

			workCusorderRelationService.delete(workCusorderRelationArray);
			workOrderEquipRelationService.delete(workOrderEquipRelationArray);
			orderTaskService.delete(orderTaskArray);
			customerOrderItemProDecService.delete(customerOrderItemProDecArray);
			materialRequirementPlanService.delete(materialRequirementPlanArray);
			workOrderService.delete(workOrder);
		}
	}

}
