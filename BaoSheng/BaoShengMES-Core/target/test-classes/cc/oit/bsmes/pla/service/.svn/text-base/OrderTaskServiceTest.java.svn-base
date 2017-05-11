/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */ 
package cc.oit.bsmes.pla.service;

import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ReportService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-1-21 上午9:36:31
 * @since
 * @version
 */
public class OrderTaskServiceTest extends BaseTest {
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private ProductProcessService productProcessService; 
	@Resource
	private ReportService reportService;
	@Test
	public void insertTest(){
		DateFormat df= new SimpleDateFormat("yyyyMMddHHmmss");
		Random random = new Random();
		Calendar c = Calendar.getInstance();
		List<WorkOrder> workOrderList=workOrderService.getAll();
		List<ProductProcess> productProcessList=productProcessService.getAll();
		List<OrderTask> list = new ArrayList<OrderTask>();
		for(int i=0;i<100;i++){
			WorkOrder wo = workOrderList.get(random.nextInt(workOrderList.size()));
			ProductProcess process = productProcessList.get(random.nextInt(productProcessList.size()));
			OrderTask ot = new OrderTask();
			ot.setWorkOrderNo(wo.getWorkOrderNo());
			ot.setPlanStartDate(wo.getPreStartTime());
			ot.setPlanFinishDate(wo.getPreEndTime());
			ot.setEquipCode(wo.getEquipCode());
			ot.setOrderItemProDecId(UUID.randomUUID().toString());
			ot.setProcessId(process.getId());
			Date date=c.getTime();
			ot.setContractNo(df.format(date));
			list.add(ot);
			c.add(Calendar.SECOND, 1);
		}
		
		orderTaskService.insert(list);
	}

    @Test
    public void testGetOrdersTodayByEquipcodes() {
        Map<String, Map<String, String>> ordersTodayByEquipcodes = orderTaskService.getOrdersTodayByEquipcodes(null, "1");
        System.out.println(ordersTodayByEquipcodes);
    }
    
    @Test
    @Rollback(false)
    public void testReport(){
    	String[] nos = new String[]{"20160108090204391","20160110094912996","20160110110445973","20160110110840975",
    			"20160111105718251","20160111152217186","2016011115381626","20160113104854332"};
    	for(String workOrderNo : nos){
    		String equipCode = "445-261";
    		List<OrderTask> list = orderTaskService.getByWorkOrderNo(workOrderNo);
    		Double reportLength = 0.0;
    		String[] reportUser = new String[]{"b17e2584-6fb4-4175-8a7e-60a21bdb3c13","70f05f54-ada8-43c0-9061-6882fc2c1144"};
    		for(OrderTask orderTask: list){
    			reportLength += orderTask.getTaskLength();
    			orderTask.setStatus(WorkOrderStatus.IN_PROGRESS);
    			orderTask.setEquipCode(equipCode);
    			orderTaskService.update(orderTask);
    		}
//    		reportService.report(workOrderNo, reportLength, "10001", "", equipCode, reportUser);
    		
    		workOrderService.finishWorkOrder(workOrderNo, equipCode, "10001");
    	}
    }
	
}
