package cc.oit.bsmes.inv.dao;


import cc.oit.bsmes.common.constants.*;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.interfacePLM.model.*;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.model.Templet;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.dao.MaterialRequirementPlanDAO;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.dao.WorkOrderDAO;
import cc.oit.bsmes.wip.model.WorkOrder;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatDAOTest extends BaseTest {
	
	@Resource
	private MatDAO matDAO;
	@Resource
	private TempletDAO templetDAO;

	@Resource
	private ProductProcessService productProcessService;

	@Resource
	private ProcessInOutService processInOutService;

	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;

	@Resource
	private OrderTaskService orderTaskService;

	@Resource
	private WorkOrderDAO workOrderDAO;


	@Test
	@Rollback(false)
	public void test() {
		for(int i=0;i<5;i++){
			Mat mat = new Mat();
			mat.setMatCode("CL50100500006-20-002"+i);
			mat.setMatName("火花配套半成品");
			mat.setMatType(MatType.SEMI_FINISHED_PRODUCT);
			mat.setHasPic(false);
			mat.setIsProduct(false);
			mat.setTempletId("1");
			matDAO.insert(mat);
		}
	}

	@Test
	@Rollback(false)
	public void test1(){
		String[] processIds = { "01_A34FC3C0F9B64DF19BB289F1FE3540EB",
								"01_7E1AE8000A454830AC67767EA110BDD1",
								"01_84EFF9AF114742D6BE43C80075DB34F2",
								"01_2B978DC02F594652AD3289E51B32325B",
								"01_9D2ECBAD7974497DA9A6F018CC685E40",
								"01_91D0C95A9CC143D482EC1B220ECB34DC"};



		Map<String,String> map = new HashMap<String, String>();

		String nextProcessId = "01_8A5A960AFC7A4BEC84BEACF17847731D";
		String matCode = "CL50100500006-20-002";

		//CustomerOrderItemProDec findParams = new CustomerOrderItemProDec();

		for(int i=5;i>=0;i--){
//			map.put(processIds[5 - i],matCode+i);
//			ProcessInOut out = processInOutService.getOutByProcessId(processIds[5 - i]);
//			out.setMatCode(matCode+i);
//			processInOutService.update(out);

			ProcessInOut in = new ProcessInOut();
			in.setMatCode(matCode+i);
			in.setProductProcessId(nextProcessId);
			in.setInOrOut(InOrOut.IN);
			in.setQuantity(1.0);
			in.setUnit(UnitType.M);

			processInOutService.insert(in);
//			map.put(processIds[5 - i],matCode+i);


//			findParams.setProcessId(processIds[9 - i]);
//			List<CustomerOrderItemProDec> list = customerOrderItemProDecService.getByObj(findParams);
//
//			for(CustomerOrderItemProDec proDec:list){
//				proDec.setHalfProductCode(map.get(proDec.getProcessId()));
//				proDec.setIsLocked(false);
//			}
//			customerOrderItemProDecService.update(list);
		}
	}
}
