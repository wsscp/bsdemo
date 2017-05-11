package cc.oit.bsmes.test;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;

/**
 * 修复WORK_ORDER被删掉的prodessId
 */
public class PatchWorkOrderProcessId extends BaseTest {
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;

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
		int count = 0;
		List<WorkOrder> workOrderArray = workOrderService.getAll();
		for (WorkOrder workOrder : workOrderArray) {
			ProductProcess productProcess = productProcessService.getById(workOrder.getProcessId());
			if (null == productProcess) {

				String cusOrderItemIds = workOrder.getCusOrderItemIds();
				if (StringUtils.isNotEmpty(cusOrderItemIds)) {
					String cusOrderItemId = cusOrderItemIds.split(",")[0];
					CustomerOrderItem customerOrderItem = customerOrderItemService.getById(cusOrderItemId);
					if (null != customerOrderItem) {

						String newProcessId = null;
						List<ProductProcess> productProcessArray = productProcessService.getByProductCraftsId(customerOrderItem
								.getCraftsId());
						for (ProductProcess productProcess1 : productProcessArray) {
							if (productProcess1.getProcessCode().equals(workOrder.getProcessCode())) {
								newProcessId = productProcess1.getId();
								break;
							}
						}
						if (StringUtils.isNotEmpty(newProcessId)) {
							System.out.println("生产单：" + workOrder.getWorkOrderNo() + "，修复工序ID，原：" + workOrder.getProcessId() + "，新："
									+ newProcessId);
							workOrder.setProcessId(newProcessId);
							workOrderService.update(workOrder);
							count++;
						}
					}
				}
			}
		}
		System.out.println("共更新了" + count + "条！");
	}

}
