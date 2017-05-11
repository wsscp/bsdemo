package cc.oit.bsmes.pla.dao;

import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.OrderTask;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

public class OrderTaskDAOTest extends BaseTest{

	@Resource
	private OrderTaskDAO orderTaskDAO;
		
	@Test
	public void test() {
	}

	@Test
	public void insertTest(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, 2, 28,7,30);
		OrderTask task = new OrderTask();
		task.setOrderItemProDecId("1");
		task.setContractNo("2014022601");
		task.setProductCode("D00001");
		task.setProcessId("1");
		task.setEquipCode("CL-0002");
		task.setStatus(WorkOrderStatus.TO_DO);
		task.setProcessPath("1");
		task.setPlanStartDate(calendar.getTime());
		calendar.set(2014, 2, 28,10,30);
		task.setPlanFinishDate(calendar.getTime());
		task.setWorkOrderNo("SC-2014022601");
		task.setTaskLength(1000.0);
		task.setOrgCode("0835156976");
		orderTaskDAO.insert(task);
		
		
		calendar.set(2014, 2, 28,10,30);
		task = new OrderTask();
		task.setOrderItemProDecId("1");
		task.setContractNo("2014022602");
		task.setProductCode("D00001");
		task.setProcessId("1");
		task.setEquipCode("CL-0002");
		task.setStatus(WorkOrderStatus.TO_DO);
		task.setProcessPath("1");
		task.setPlanStartDate(calendar.getTime());
		calendar.set(2014, 2, 28,14,30);
		task.setPlanFinishDate(calendar.getTime());
		task.setWorkOrderNo("SC-2014022601");
		task.setTaskLength(1500.0);
		task.setOrgCode("0835156976");
		orderTaskDAO.insert(task);
		
		calendar.set(2014, 2, 28,14,30);
		task = new OrderTask();
		task.setOrderItemProDecId("1");
		task.setContractNo("2014022603");
		task.setProductCode("D00001");
		task.setProcessId("1");
		task.setEquipCode("CL-0002");
		task.setStatus(WorkOrderStatus.TO_DO);
		task.setProcessPath("1");
		task.setPlanStartDate(calendar.getTime());
		calendar.set(2014, 2, 28,17,30);
		task.setPlanFinishDate(calendar.getTime());
		task.setWorkOrderNo("SC-2014022601");
		task.setTaskLength(1500.0);
		task.setOrgCode("0835156976");
		orderTaskDAO.insert(task);
	}

}
