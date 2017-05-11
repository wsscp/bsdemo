package cc.oit.bsmes.junit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.EquipCalShiftService;
import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.util.JxlUtils;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.MaintainItemService;
import cc.oit.bsmes.fac.service.MaintainRecordItemService;
import cc.oit.bsmes.fac.service.MaintainRecordService;
import cc.oit.bsmes.fac.service.MaintainTemplateService;
import cc.oit.bsmes.fac.service.ProductEquipService;
import cc.oit.bsmes.fac.service.WorkTaskService;
import cc.oit.bsmes.inv.service.InventoryDetailService;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.HighPriorityOrderItemProDecService;
import cc.oit.bsmes.pla.service.HighPriorityOrderItemService;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pla.service.ToolsRequirementPlanService;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProcessQCEqipService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessQcValueService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.pro.service.ProductQCDetailService;
import cc.oit.bsmes.pro.service.ProductQCResultService;
import cc.oit.bsmes.pro.service.ProductQCTemplateService;
import cc.oit.bsmes.wip.service.ReceiptService;
import cc.oit.bsmes.wip.service.WorkOrderService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/cc/META-INF/spring/applicationContext*.xml",
		"classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private org.springframework.core.io.Resource productData;
	@Resource
	private org.springframework.core.io.Resource equipdailyCheckData;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private CustomerOrderItemDecService customerOrderItemDecService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private SalesOrderItemService salesOrderItemService;
	@Resource
	private CustomerOrderService customerOrderService;
	@Resource
	private SalesOrderService salesOrderService;
	@Resource
	private WorkTaskService workTaskService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private HighPriorityOrderItemService highPriorityOrderItemService;
	@Resource
	private HighPriorityOrderItemProDecService highPriorityOrderItemProDecService;
	@Resource
	private MaterialRequirementPlanService materialRequirementPlanService;
	@Resource
	private ToolsRequirementPlanService toolsRequirementPlanService;
	@Resource
	private EquipCalendarService equipCalendarService;
	@Resource
	private EquipCalShiftService equipCalShiftService;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private InventoryDetailService inventoryDetailService;
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private EquipListService equipListService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private ProcessInOutService processInOutService;
	@Resource
	private MatService matService;
	@Resource
	private ProcessReceiptService processReceiptService;
	@Resource
	private ProductService productService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private ProductEquipService productEquipService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	@Resource
	private UserService userService;
	@Resource
	ProductQCTemplateService productQCTemplateService;
	@Resource
	ProductQCDetailService productQCDetailService;
	@Resource
	ProductQCResultService productQCResultService;
	@Resource
	ProcessQCEqipService processQCEqipService;
	@Resource
	ProcessQcService processQcService;
	@Resource
	ProcessQcValueService processQcValueService;
	@Resource
	ReceiptService receiptService;
	@Resource 
	private MaintainTemplateService maintainTemplateService;
	@Resource 
	private MaintainItemService maintainItemService;
	@Resource 
	private MaintainRecordItemService maintainRecordItemService;
	@Resource 
	private MaintainRecordService maintainRecordService;
	@Resource
    private EquipMESWWMappingService equipMESWWMappingService;
	@Before()
	public void setUp() {
		User user = userService.checkUserCodeUnique("admin");
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		userService.userLoginSuccess(user, request, response);
	}

	protected void deleteCraft() {
		deleteOrder();

/*		receiptService.deleteAll();
		processQcValueService.deleteAll();
		processQCEqipService.deleteAll();
		processQcService.deleteAll();
		processReceiptService.deleteAll();
		equipListService.deleteAll();
		processInOutService.deleteAll();
		matService.deleteAll();
		productProcessService.deleteAll();
		productCraftsService.deleteAll();
		productService.deleteAll();*/
	}

	protected void deleteOrder() {
		/*inventoryDetailService.deleteAll();
		inventoryService.deleteAll();
		equipCalShiftService.deleteAll();
		equipCalendarService.deleteAll();
		workTaskService.deleteAll();
		materialRequirementPlanService.deleteAll();
		toolsRequirementPlanService.deleteAll();
		workOrderService.deleteAll();
		orderTaskService.deleteAll();
		highPriorityOrderItemProDecService.deleteAll();
		customerOrderItemProDecService.deleteAll();
		customerOrderItemDecService.deleteAll();
		customerOrderItemService.deleteAll();
		salesOrderItemService.deleteAll();
		highPriorityOrderItemService.deleteAll();
		customerOrderService.deleteAll();
		salesOrderService.deleteAll();*/
	}

	protected void deleteQctemp() {
/*		productQCTemplateService.deleteAll();
		productQCDetailService.deleteAll();
		productQCResultService.deleteAll();*/

	}

	protected void deleteProcessParmData() {
/*		receiptService.deleteAll();
		processQcValueService.deleteAll();
		processQCEqipService.deleteAll();
		processQcService.deleteAll();
		processReceiptService.deleteAll();*/
	}

	public void initRealCraft(String orgCode) throws IOException, BiffException {
		deleteCraft();

		Workbook workbook = Workbook.getWorkbook(productData.getInputStream());
		productCraftsService.importCrafts(workbook.getSheet("产品工艺"), orgCode);
	}

	public void importDailyCheck(String orgCode) throws IOException, BiffException {
		maintainRecordItemService.deleteAll();
		maintainRecordService.deleteAll();
		maintainItemService.deleteAll();
		maintainTemplateService.deleteAll();
		Workbook workbook = Workbook.getWorkbook(equipdailyCheckData.getInputStream());
		maintainTemplateService.initMaintItem(workbook, orgCode);
	}

	/**
	 * 导入设备关系映射表
	 * T_INT_EQUIP_MES_WW_MAPPING
	 * */
	public void importEquipMESWWMapping(Workbook workbook, String orgCode) {
		// 先删除，再导入工艺参数
    	/*equipMESWWMappingService.deleteAll(); */
    	
		Sheet[] sheets = workbook.getSheets();
		for (int i = 0; i < sheets.length; i++) {
			Sheet sheet = sheets[i];
			List<Cell[]> qcList = new ArrayList<Cell[]>();
			String acEquipCode = sheet.getName(); // 真实设备编码
			// 第一行获取equipCode
			Cell[] firstCells = sheet.getRow(0);
			String equipCode = JxlUtils.getRealContents(firstCells[15]);
			if (StringUtils.isEmpty(equipCode)) {
				continue;
			}
			int maxRow = sheet.getRows();
			for (int j = 1; j < maxRow; j++) {
				Cell[] cells = sheet.getRow(j);
				if(null==cells||cells.length==0){
					break;
				}
				String parmTypeName = JxlUtils.getRealContents(cells[0]);
				if (StringUtils.isEmpty(parmTypeName)) {
					continue;
				}
				if ("工艺参数".equalsIgnoreCase(parmTypeName)) {
					qcList.add(cells);
				}
			}
			equipMESWWMappingService.importEquipMESWWMapping(qcList, equipCode, acEquipCode);
		}
	}
	
	/**
	 * 同步设备工艺参数的映射关系
	 * 
	 * 
	 * */
	public void insertProcessParmData(Workbook workbook, String orgCode) {
		Sheet[] sheets = workbook.getSheets();
		for (int i = 0; i < sheets.length; i++) {
			Sheet sheet = sheets[i];
			List<Cell[]> qcList = new ArrayList<Cell[]>();
			String acEquipCode = sheet.getName(); // 真实设备编码
			// 第一行获取equipCode
			Cell[] firstCells = sheet.getRow(0);
			String equipCode = JxlUtils.getRealContents(firstCells[15]);
			if (StringUtils.isEmpty(equipCode)) {
				continue;
			}
			int maxRow = sheet.getRows();
			for (int j = 1; j < maxRow; j++) {
				Cell[] cells = sheet.getRow(j);
				String parmTypeName = JxlUtils.getRealContents(cells[0]);
				if (StringUtils.isEmpty(parmTypeName)) {
					continue;
				}
				if ("工艺参数".equalsIgnoreCase(parmTypeName)) {
					qcList.add(cells);
				}
			}
			processReceiptService.importProcessQc(qcList, equipCode, acEquipCode, orgCode);
		}
	}
}
