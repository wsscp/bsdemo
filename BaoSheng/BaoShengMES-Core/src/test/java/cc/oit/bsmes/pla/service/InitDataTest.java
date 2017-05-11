package cc.oit.bsmes.pla.service;

import cc.oit.bsmes.common.constants.*;
import cc.oit.bsmes.fac.dao.EquipInfoDAO;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.inv.dao.MatDAO;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.ord.dao.SalesOrderDAO;
import cc.oit.bsmes.ord.dao.SalesOrderItemDAO;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.pla.dao.CustomerOrderDAO;
import cc.oit.bsmes.pla.dao.CustomerOrderItemDAO;
import cc.oit.bsmes.pla.dao.HighPriorityOrderItemDAO;
import cc.oit.bsmes.pla.dao.ProductDAO;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.HighPriorityOrderItem;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pro.dao.ProcessInOutDAO;
import cc.oit.bsmes.pro.dao.ProductCraftsDAO;
import cc.oit.bsmes.pro.dao.ProductProcessDAO;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InitDataTest extends BaseTest{
	
	@Resource
	private ProductDAO productDAO; 
	@Resource
	private MatDAO matDAO;
	@Resource
	private ProductProcessDAO productProcessDAO;
    @Resource
	private ProductCraftsDAO productCraftsDAO;
	@Resource
	private ProcessInOutDAO  processInOutDAO;
	@Resource
	private EquipInfoDAO  equipInfoDAO;
	@Resource
	private SalesOrderDAO  salesOrderDAO;
	@Resource
	private SalesOrderItemDAO salesOrderItemDAO;
	@Resource
	private CustomerOrderDAO customerOrderDAO ;
	@Resource
	private CustomerOrderItemDAO customerOrderItemDAO ;
	@Resource
	private HighPriorityOrderItemDAO highPriorityOrderItemDAO ;

	@Test
	public void test() {
    }

    @Test
    public void testInsertCrafts(){
        List<Product> list = productDAO.getAll();
        logger.info("Product size:"+list.size());
        for (Product product:list){
            Mat mat = new Mat();
            mat.setMatCode(product.getProductCode());
            mat.setMatName(product.getProductName());
            mat.setMatType(MatType.FINISHED_PRODUCT);
            mat.setHasPic(false);
            mat.setIsProduct(false);
            mat.setTempletId("2");
            matDAO.insert(mat);
        }
    }

    @Test
    @Rollback(false)
    public void insertEquip(){
        EquipInfo info = new EquipInfo();
        info.setCode("RB-0002");
        info.setName("绕包2");
        info.setStatus(EquipStatus.IDLE);
        info.setType(EquipType.MAIN_EQUIPMENT);
        info.setOrgCode("1");
        equipInfoDAO.insert(info);

        info = new EquipInfo();
        info.setCode("SJ-0002");
        info.setName("烧结2");
        info.setStatus(EquipStatus.IDLE);
        info.setType(EquipType.MAIN_EQUIPMENT);
        info.setOrgCode("1");
        equipInfoDAO.insert(info);
    }


    @Test
    public void craftsInit(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,10);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        ProductCrafts craft = new ProductCrafts();
        craft.setCraftsCode("POO4");
        craft.setCraftsName("电力电缆制造");
        craft.setStartDate(calendar.getTime());
        calendar.set(Calendar.YEAR,2015);
        craft.setEndDate(calendar.getTime());
        craft.setCraftsVersion(1);
        craft.setProductCode("D00002");
        craft.setOrgCode("1");
       // craft.setIsDefault(true);
        productCraftsDAO.insert(craft);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,10);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        craft = new ProductCrafts();
        craft.setCraftsCode("POO5");
        craft.setCraftsName("通信电缆制造");
        craft.setStartDate(calendar.getTime());
        calendar.set(Calendar.YEAR,2015);
        craft.setEndDate(calendar.getTime());
        craft.setCraftsVersion(1);
        craft.setProductCode("T00002");
        craft.setOrgCode("1");
        // craft.setIsDefault(true);
        productCraftsDAO.insert(craft);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR,10);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        craft = new ProductCrafts();
        craft.setCraftsCode("POO6");
        craft.setCraftsName("高温电缆制造");
        craft.setStartDate(calendar.getTime());
        calendar.set(Calendar.YEAR,2015);
        craft.setEndDate(calendar.getTime());
        craft.setCraftsVersion(1);
        craft.setProductCode("G00002");
        craft.setOrgCode("1");
        // craft.setIsDefault(true);
        productCraftsDAO.insert(craft);
    }


    public  void processInit(){
        ProductProcess process = new ProductProcess();
        process.setProcessCode("Extrusion-Single");
        process.setProcessName("绝缘");
        process.setProductCraftsId("8cfd3d95-04c2-4c36-967f-931783222574");
        process.setProcessTime(8.0);
    }


	@Test
    @Rollback(false)
	public void initProcessInOut(){
		ProcessInOut inOut = new ProcessInOut();
		inOut.setProductProcessId("21");
		inOut.setMatCode("M00014");
		inOut.setInOrOut(InOrOut.IN);
        inOut.setQuantity(1.0);
		processInOutDAO.insert(inOut);
		
		inOut = new ProcessInOut();
		inOut.setProductProcessId("21");
		inOut.setMatCode("M0006");
		inOut.setInOrOut(InOrOut.IN);
        inOut.setQuantity(1.0);
		processInOutDAO.insert(inOut);
		
		inOut = new ProcessInOut();
		inOut.setProductProcessId("21");
		inOut.setMatCode("S0006");
		inOut.setInOrOut(InOrOut.OUT);
        inOut.setQuantity(1.0);
		processInOutDAO.insert(inOut);

		inOut = new ProcessInOut();
		inOut.setProductProcessId("22");
		inOut.setMatCode("S0006");
		inOut.setInOrOut(InOrOut.IN);
		processInOutDAO.insert(inOut);
		
		inOut = new ProcessInOut();
        inOut.setProductProcessId("22");
		inOut.setMatCode("M0008");
		inOut.setInOrOut(InOrOut.IN);
        inOut.setQuantity(1.0);
		processInOutDAO.insert(inOut);
		
		inOut = new ProcessInOut();
        inOut.setProductProcessId("22");
		inOut.setMatCode("S0007");
		inOut.setInOrOut(InOrOut.OUT);
        inOut.setQuantity(1.0);
		processInOutDAO.insert(inOut);
		
		inOut = new ProcessInOut();
        inOut.setProductProcessId("23");
		inOut.setMatCode("S0007");
		inOut.setInOrOut(InOrOut.IN);
        inOut.setQuantity(1.0);
		processInOutDAO.insert(inOut);
		
		inOut = new ProcessInOut();
        inOut.setProductProcessId("23");
		inOut.setMatCode("M0009");
        inOut.setQuantity(1.0);
		inOut.setInOrOut(InOrOut.IN);
		processInOutDAO.insert(inOut);
		
		inOut = new ProcessInOut();
        inOut.setProductProcessId("23");
		inOut.setMatCode("G00002");
        inOut.setQuantity(1.0);
		inOut.setInOrOut(InOrOut.OUT);
		processInOutDAO.insert(inOut);
     }
	
	@Test
	public void initMat(){
/*		List<Product> list = productDAO.getAll();
		for(Product product:list){
			Mat mat = new Mat();
			mat.setMatCode(product.getProductCode());
			mat.setMatName(product.getProductName());
			mat.setMatType(MatType.FINISHED_PRODUCT);
			mat.setHasPic(false);
			mat.setIsProduct(true);
			mat.setTempletId("1");
			matDAO.insert(mat);
		}
	*/	
		for(int i=4;i<15;i++){
			Mat mat = new Mat();
			mat.setMatCode("M000"+i);
			mat.setMatName("原材料"+i);
			mat.setMatType(MatType.MATERIALS);
			mat.setHasPic(false);
			mat.setIsProduct(false);
			mat.setTempletId("2");
			matDAO.insert(mat);
		}
		for(int i=1;i<15;i++){
			Mat mat = new Mat();
			mat.setMatCode("M000"+i);
			mat.setMatName("半成品"+i);
			mat.setMatType(MatType.SEMI_FINISHED_PRODUCT);
			mat.setHasPic(false);
			mat.setIsProduct(true);
			mat.setTempletId("3");
			matDAO.insert(mat);
		}
	}

	@Test
	public void initEqipInfo(){
		EquipInfo equipInfo = new EquipInfo();
		equipInfo.setCode("CL-0001");
		equipInfo.setName("成缆1");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		
		equipInfo = new EquipInfo();
		equipInfo.setCode("CL-0002");
		equipInfo.setName("成缆2");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		
		equipInfo = new EquipInfo();
		equipInfo.setCode("JX-0001");
		equipInfo.setName("绞线1");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		
		equipInfo = new EquipInfo();
		equipInfo.setCode("JX-0002");
		equipInfo.setName("绞线2");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		
		equipInfo = new EquipInfo();
		equipInfo.setCode("FZ-0001");
		equipInfo.setName("辐照1");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		
		equipInfo = new EquipInfo();
		equipInfo.setCode("JC-0001");
		equipInfo.setName("挤出1");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		equipInfo = new EquipInfo();
		equipInfo.setCode("JC-0002");
		equipInfo.setName("挤出2");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		equipInfo = new EquipInfo();
		equipInfo.setCode("JC-0003");
		equipInfo.setName("挤出3");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		
		equipInfo = new EquipInfo();
		equipInfo.setCode("DY-0001");
		equipInfo.setName("打印1");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		equipInfo = new EquipInfo();
		equipInfo.setCode("DY-0002");
		equipInfo.setName("打印2");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		
		equipInfo = new EquipInfo();
		equipInfo.setCode("RBSJ-0001");
		equipInfo.setName("绕包烧结1");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		equipInfo = new EquipInfo();
		equipInfo.setCode("RBSJ-0002");
		equipInfo.setName("绕包烧结2");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		equipInfo = new EquipInfo();
		equipInfo.setCode("RBSJ-0003");
		equipInfo.setName("绕包烧结3");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		
		equipInfo = new EquipInfo();
		equipInfo.setCode("RB-0001");
		equipInfo.setName("绕包1");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
		
		equipInfo = new EquipInfo();
		equipInfo.setCode("SJ-0001");
		equipInfo.setName("烧结1");
		equipInfo.setStatus(EquipStatus.IDLE);
		equipInfo.setType(EquipType.MAIN_EQUIPMENT);
		equipInfoDAO.insert(equipInfo);
	}
	
	@Test
	public void initOrderInfo(){
		SalesOrder t = new SalesOrder();
		t.setSalesOrderNo("D20140303001");
		t.setCustomerCompany("北京XXXX公司");
		t.setContractNo("HT20140303001");
		t.setOperator("李四");
		t.setStatus(SalesOrderStatus.TO_DO);
		t.setConfirmDate(new Date());
		t.setOrgCode("8770318921");
		salesOrderDAO.insert(t);
		
		SalesOrderItem item = new SalesOrderItem();
		item.setSalesOrderId(t.getId());
		item.setCustProductType("通信电缆");
		item.setCustProductSpec("d30");
		item.setProductCode("T00001");
		item.setProductType("通信电缆");
		item.setProductSpec("d30");
		item.setNumberOfWires(5);
		item.setSection("6.0");
		item.setWiresLength(100000.0);
		item.setStandardLength(1000.0);
//		item.setMinLength(200.0);
		item.setSaleorderLength(10000.0);
		item.setStatus(SalesOrderStatus.TO_DO);
		salesOrderItemDAO.insert(item);
		
		
		t = new SalesOrder();
		t.setSalesOrderNo("D20140303002");
		t.setCustomerCompany("上海XXXX公司");
		t.setContractNo("HT20140303002");
		t.setOperator("李四");
		t.setStatus(SalesOrderStatus.TO_DO);
		t.setConfirmDate(new Date());
		t.setOrgCode("8770318921");
		salesOrderDAO.insert(t);
		
		item = new SalesOrderItem();
		item.setSalesOrderId(t.getId());
		item.setCustProductType("电力电缆");
		item.setCustProductSpec("d10");
		item.setProductCode("D00001");
		item.setProductType("电力电缆");
		item.setProductSpec("d10");
		item.setNumberOfWires(10);
		item.setSection("10.0");
		item.setWiresLength(1000000.0);
		item.setStandardLength(1000.0);
//		item.setMinLength(500.0);
		item.setSaleorderLength(100000.0);
		item.setStatus(SalesOrderStatus.TO_DO);
		salesOrderItemDAO.insert(item);
		
		
		t = new SalesOrder();
		t.setSalesOrderNo("D20140303003");
		t.setCustomerCompany("南京XXXX公司");
		t.setContractNo("HT20140303003");
		t.setOperator("李四");
		t.setStatus(SalesOrderStatus.TO_DO);
		t.setConfirmDate(new Date());
		t.setOrgCode("8770318921");
		salesOrderDAO.insert(t);
		
		item = new SalesOrderItem();
		item.setSalesOrderId(t.getId());
		item.setCustProductType("高温电缆");
		item.setCustProductSpec("d40");
		item.setProductCode("G00001");
		item.setProductType("高温电缆");
		item.setProductSpec("d40");
		item.setNumberOfWires(4);
		item.setSection("4.0");
		item.setWiresLength(2000.0);
		item.setStandardLength(500.0);
//		item.setMinLength(50.0);
		item.setSaleorderLength(1000.0);
		item.setStatus(SalesOrderStatus.TO_DO);
		salesOrderItemDAO.insert(item);
	}

	@Test
	public void initCustomerOrder(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, 4, 30);
		CustomerOrder cusOrder = new CustomerOrder();
		cusOrder.setCustomerOrderNo("CO20140303001");
		cusOrder.setCustomerOaDate(calendar.getTime());
		cusOrder.setSalesOrderId("8bd95c4f-d118-4627-9327-5ce923cd5d56");
		cusOrder.setOrgCode("8770318921");
		cusOrder.setStatus(CustomerOrderStatus.TO_DO);
		customerOrderDAO.insert(cusOrder);
		
		calendar.set(2014, 3, 10);
		HighPriorityOrderItem t = new HighPriorityOrderItem();
		calendar.set(2014, 3, 15);
		t.setId(cusOrder.getId());
		highPriorityOrderItemDAO.insert(t);
		
		CustomerOrderItem item = new CustomerOrderItem();
		item.setCustomerOrderId(cusOrder.getId());
		item.setOrderLength(10000.00);
		item.setStatus(ProductOrderStatus.TO_DO);
		item.setSalesOrderItemId("b941f3c5-6812-4150-89f1-9f2b06e81ddf");
		item.setCraftsId("2");
		customerOrderItemDAO.insert(item);
		
		
		calendar.set(2014, 6, 30);
		cusOrder = new CustomerOrder();
		cusOrder.setCustomerOrderNo("CO20140303002");
		cusOrder.setCustomerOaDate(calendar.getTime());
		cusOrder.setSalesOrderId("5be3da66-d406-4172-9423-18e494ac2402");
		cusOrder.setOrgCode("8770318921");
		cusOrder.setStatus(CustomerOrderStatus.TO_DO);
		customerOrderDAO.insert(cusOrder);
		
		calendar = Calendar.getInstance();
		calendar.set(2014, 3, 15);
		t = new HighPriorityOrderItem();
		calendar.set(2014, 3, 30);
		t.setId(cusOrder.getId());
		highPriorityOrderItemDAO.insert(t);
		
		item = new CustomerOrderItem();
		item.setCustomerOrderId(cusOrder.getId());
		item.setOrderLength(100000.00);
		item.setStatus(ProductOrderStatus.TO_DO);
		item.setSalesOrderItemId("8f822d95-af43-463d-a44e-77723a32eb28");
		item.setCraftsId("1");
		customerOrderItemDAO.insert(item);
		
		calendar.set(2014, 3, 30);
		cusOrder = new CustomerOrder();
		cusOrder.setCustomerOrderNo("CO20140303003");
		cusOrder.setCustomerOaDate(calendar.getTime());
		cusOrder.setSalesOrderId("fc03959a-2f20-48a4-9830-5968d4030c17");
		cusOrder.setOrgCode("8770318921");
		cusOrder.setStatus(CustomerOrderStatus.TO_DO);
		customerOrderDAO.insert(cusOrder);
		
		calendar = Calendar.getInstance();
		calendar.set(2014, 4, 1);
		t = new HighPriorityOrderItem();
		calendar.set(2014, 4, 10);
		t.setId(cusOrder.getId());
		highPriorityOrderItemDAO.insert(t);
		
		item = new CustomerOrderItem();
		item.setCustomerOrderId(cusOrder.getId());
		item.setOrderLength(2000.00);
		item.setStatus(ProductOrderStatus.TO_DO);
		item.setSalesOrderItemId("df5c4cf6-f47a-4e6d-a988-680eec068f6d");
		item.setCraftsId("3");
		customerOrderItemDAO.insert(item);
	}
}
