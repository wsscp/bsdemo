package cc.oit.bsmes.test;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;

/**
 * 补充生产单数据 T_WIP_WORK_ORDER_REPORT T_WIP_USER_WORK_HOURS
 */
public class PatchProdecRelateOrderIds extends BaseTest {
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;

	/**
	 * bean初始化后执行的方法
	 * */
	@PostConstruct
	public void initMethod() {

	}

	/**
	 * 执行函数
	 * 
	 * @throws ParseException
	 * */
	@Test
	@Rollback(false)
	public void process() {
		List<WorkOrder> workOrderArray = workOrderService.getAll();
		for (WorkOrder workOrder : workOrderArray) {
			List<Map<String, String>> mapArray = customerOrderItemProDecService
					.patchProdecRelateOrderIds(workOrder.getWorkOrderNo());
			for (Map<String, String> map : mapArray) {
				String PRODECIDS = map.get("PRODECIDS");
				String ORDERITEMIDS = map.get("ORDERITEMIDS");
				if (StringUtils.isNotEmpty(PRODECIDS)) {
					String[] PRODECIDARRAY = PRODECIDS.split(",");
					for (String PRODECID : PRODECIDARRAY) {
						System.out.println(workOrder.getWorkOrderNo());
						System.out.println(PRODECID);
						CustomerOrderItemProDec customerOrderItemProDec = customerOrderItemProDecService
								.getById(PRODECID);
						if (StringUtils.isEmpty(customerOrderItemProDec
								.getRelateOrderIds())) {
							customerOrderItemProDec
									.setRelateOrderIds(ORDERITEMIDS);
							customerOrderItemProDecService
									.update(customerOrderItemProDec);
						}

					}
				}
			}
		}
	}

}
