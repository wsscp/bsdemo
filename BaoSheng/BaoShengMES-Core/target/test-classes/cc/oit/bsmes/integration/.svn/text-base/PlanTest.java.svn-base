package cc.oit.bsmes.integration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import jxl.Cell;
import jxl.read.biff.BiffException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.dao.EquipMESWWMappingDAO;
import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.bas.model.MesClientManEqip;
import cc.oit.bsmes.bas.service.EquipCalShiftService;
import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.bas.service.MesClientManEqipService;
import cc.oit.bsmes.common.constants.CustomerOrderStatus;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.SalesOrderStatus;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.util.JxlUtils;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.ProductEquip;
import cc.oit.bsmes.fac.model.WorkTask;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.MaintainItemService;
import cc.oit.bsmes.fac.service.MaintainTemplateService;
import cc.oit.bsmes.fac.service.ProductEquipService;
import cc.oit.bsmes.fac.service.WorkTaskService;
import cc.oit.bsmes.interfaceWWAc.dao.EquipParamAcquisitionDAO;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamAcquisition;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.tasks.CanShukuTask;
import cc.oit.bsmes.job.tasks.EquipUpdateTask;
import cc.oit.bsmes.job.tasks.PLMProductTask;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderPlanService;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pla.service.ProductSOPService;
import cc.oit.bsmes.pro.dao.EquipListDAO;
import cc.oit.bsmes.pro.dao.ProductCraftsDAO;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProductCraftsBzService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.pro.service.ProductQCTemplateService;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by chanedi on 14-3-27.
 */
public class PlanTest extends BaseTest {

	@Resource
	private org.springframework.core.io.Resource planData;
	@Resource
	private org.springframework.core.io.Resource saleOrderData;
	@Resource
	private org.springframework.core.io.Resource qcTempData;
	@Resource
	private org.springframework.core.io.Resource processParmData;
	@Resource
	private org.springframework.core.io.Resource maintainData;
	@Resource
	private ProductSOPService productSOPService;
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
	private EquipInfoService equipInfoService;
	@Resource
	private CustomerOrderPlanService customerOrderPlanService;
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private EquipCalendarService equipCalendarService;
	@Resource
	private EquipCalShiftService equipCalShiftService;
	@Resource
	private ProductEquipService productEquipService;
	@Resource
	private ProductQCTemplateService productQCTemplateService;
	@Resource
	private MaintainTemplateService maintainTemplateService;
	@Resource
	private MaintainItemService maintainItemService;
	@Resource
	private MesClientManEqipService mesClientManEqipService;
	@Resource
	private EquipUpdateTask equipUpdateTask;
	@Resource
	private PLMProductTask pLMProductTask;
	@Resource
	private CanShukuTask canShukuTask;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private ProductCraftsBzService productCraftsBzService;
	@Resource
	private ProductCraftsDAO productCraftsDAO;
	@Resource
	private EquipListDAO equipListDAO;
	@Resource
	private EquipMESWWMappingDAO equipMESWWMappingDAO;
	@Resource
	private EquipParamAcquisitionDAO equipParamAcquisitionDAO;
	@Resource
	private ResourceCache resourceCache;
	private String orgCode = "bstl01";
	// 参数设置
	private boolean useNoiseData = false;

	@Test
	@Rollback(false)
	public void importCraft() throws BiffException, IOException {
		// super.initRealCraft(orgCode);
		generateEquipInfo();
		// generateMesClientInfo();
	}

	@Test
	public void importOrder() throws BiffException, IOException {
		deleteOrder();
		// Workbook workbook = Workbook.getWorkbook(planData.getInputStream());
		// salesOrderService.importOrders(workbook.getSheet("通用订单"), orgCode);
	}

	@Test
	public void importOrderData() throws BiffException, IOException {
		deleteOrder();
		String fileName = saleOrderData.getFilename();
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(saleOrderData.getInputStream());
		} catch (Exception e) {
			workbook = new HSSFWorkbook(saleOrderData.getInputStream());
		}
		JSONObject result = new JSONObject();
		salesOrderService.importProPlan(workbook.getSheet("生产计划"), orgCode, result);
		logger.info("成功导入{}个产品", result.get("importNum"));
	}

	@Test
	@Rollback(false)
	public void sop() throws IOException, BiffException, InvocationTargetException, IllegalAccessException {
		productSOPService.calculateSOP(resourceCache, orgCode);
	}

	@Test
	@Rollback(false)
	public void synProductScxMpart() {
		JobParams parm = new JobParams();
		parm.setOrgCode("bstl01");
		// 执行业务操作
		/* equipUpdateTask.process(parm); */
		pLMProductTask.process(parm);
		canShukuTask.process(parm);

	}

	@Test
	@Rollback(false)
	public void synUpdateAll() {
		JobParams parm = new JobParams();
		parm.setOrgCode("bstl01");

		// pLMProductTask.updateProduct(parm);

	}

	/**
	 * 单独同步mes中没有生产线的数据
	 */
	@Test
	@Rollback(false)
	public void synScxPlmToMes() {
		// 得到mes中与plm中产品下对应生产线数量不同的产品列表
		List<String> productCodeList = productCraftsDAO.getUncompletedScxFromMes();
		for (String productCode : productCodeList) {
			System.out.println(productCode);
			equipListDAO.insertScxFromPlm(productCode);
		}
	}

	@Test
	@Rollback(false)
	public void oa() throws BiffException, IOException, InvocationTargetException, IllegalAccessException {
		// customerOrderItemProDecService.analysisOrderToProcess(resourceCache,
		// orgCode);
		equipInfoService.initEquipLoad(orgCode);
		customerOrderPlanService.calculatorOA(resourceCache, orgCode);
	}

	@Test
	@Rollback(false)
	public void schedule() throws BiffException, IOException, InvocationTargetException, IllegalAccessException {
		equipInfoService.initEquipLoad(orgCode);
		orderTaskService.generate(resourceCache, orgCode);
	}

	@Test
	@Rollback(false)
	public void schedule_all() throws BiffException, IOException, InvocationTargetException, IllegalAccessException {
		// 由于事务的问题，去除了import部分
		long now1 = System.currentTimeMillis();
		oa();
		long now2 = System.currentTimeMillis();
		orderTaskService.generate(resourceCache, orgCode);
		long now3 = System.currentTimeMillis();
		System.err.println(now2 - now1);
		System.err.println(now3 - now2);

	}

	private void initNoiseData() {
		if (useNoiseData) {
			insertNoiseData();
		}
	}

	public void generateEquipInfo1() throws BiffException, IOException {

	}

	@Rollback(false)
	@Transactional(readOnly = false)
	public void generateEquipInfo() throws BiffException, IOException {
		generateMainEquip();
		/*
		 * jxl.Workbook workbook =
		 * jxl.Workbook.getWorkbook(maintainData.getInputStream()); jxl.Sheet
		 * dailySheet = workbook.getSheet("点检"); List<String> dailyItems =
		 * getListBySheet(dailySheet); jxl.Sheet monthlySheet =
		 * workbook.getSheet("月检"); List<String> monthlyItems =
		 * getListBySheet(monthlySheet);
		 */

		// 点检
		// super.importDailyCheck(orgCode);
		// MaintainTemplate dailyTmpl = new MaintainTemplate();
		// dailyTmpl.setOrgCode("1");
		// dailyTmpl.setModel("普通设备");
		// dailyTmpl.setType(MaintainTemplateType.DAILY);
		// maintainTemplateService.insert(dailyTmpl);
		// for (String dailyItemStr : dailyItems) {
		// MaintainItem dailyItem = new MaintainItem();
		// dailyItem.setTempId(dailyTmpl.getId());
		// dailyItem.setDescribe(dailyItemStr);
		// maintainItemService.insert(dailyItem);
		// }
		// 月检
		/*
		 * MaintainTemplate monthlyTmpl = new MaintainTemplate();
		 * monthlyTmpl.setOrgCode("1"); monthlyTmpl.setModel("普通设备");
		 * monthlyTmpl.setType(MaintainTemplateType.MONTHLY);
		 * monthlyTmpl.setTriggerType(MaintainTriggerType.NATURE);
		 * monthlyTmpl.setTriggerCycle(1);
		 * maintainTemplateService.insert(monthlyTmpl); for (String
		 * monthlyItemStr : monthlyItems) { MaintainItem monthlyItem = new
		 * MaintainItem(); monthlyItem.setTempId(monthlyTmpl.getId());
		 * monthlyItem.setDescribe(monthlyItemStr);
		 * maintainItemService.insert(monthlyItem); } // 月检 monthlyTmpl = new
		 * MaintainTemplate(); monthlyTmpl.setOrgCode("1");
		 * monthlyTmpl.setModel("起重机");
		 * monthlyTmpl.setType(MaintainTemplateType.MONTHLY);
		 * monthlyTmpl.setTriggerType(MaintainTriggerType.NATURE);
		 * monthlyTmpl.setTriggerCycle(3);
		 * maintainTemplateService.insert(monthlyTmpl); List<String>
		 * qizhongItems = new ArrayList<String>();
		 * qizhongItems.add("吊钩不应有裂纹、剥裂等缺陷，存在缺陷不得补焊");
		 * qizhongItems.add("吊钩危险断面检查应符合技术要求");
		 * qizhongItems.add("钢丝绳的规格、型号应符合设计要求，与滑轮和卷筒相匹配，并正确穿绕");
		 * qizhongItems.add("钢丝绳应润滑良好，不应与金属结构磨擦");
		 * qizhongItems.add("钢丝绳不应有扭结、压扁、弯折、断股、笼状畸变、断芯、断丝严重、磨损严重等现象");
		 * qizhongItems.add("滑轮应转动良好，不得出现裂纹、轮缘破损等伤害钢丝绳的缺陷，应有防止钢丝绳脱槽的装置，且有效可靠");
		 * qizhongItems.add("制动器制动应平稳可靠");
		 * qizhongItems.add("减速器工作时应无异常声响、振动和漏油"); for (String qizhongItemStr :
		 * qizhongItems) { MaintainItem qizhongItem = new MaintainItem();
		 * qizhongItem.setTempId(monthlyTmpl.getId());
		 * qizhongItem.setDescribe(qizhongItemStr);
		 * maintainItemService.insert(qizhongItem); }
		 * 
		 * EquipInfo newEquip = new EquipInfo(); newEquip.setCode("QIZHONGJI");
		 * newEquip.setName("起重机"); newEquip.setType(EquipType.MAIN_EQUIPMENT);
		 * newEquip.setCreateUserCode("admin");
		 * newEquip.setModifyUserCode("admin"); newEquip.setCreateTime(new
		 * Date()); newEquip.setModifyTime(new Date());
		 * newEquip.setStatus(EquipStatus.IDLE); newEquip.setOrgCode("1");
		 * newEquip.setModel("起重机"); newEquip.setMaintainDate(3);
		 * equipInfoService.insert(newEquip);
		 */}

	private void generateMainEquip() {
		EquipInfo findParams = new EquipInfo();
		findParams.setType(EquipType.PRODUCT_LINE);
		List<EquipInfo> allline = equipInfoService.find(findParams, 0, Integer.MAX_VALUE, null);

		for (int i = 0; i < allline.size(); i++) {
			EquipInfo line = allline.get(i);
			if (line.getCode().contains("VIRTUAL")) {
				continue;
			}
			if (!line.getOrgCode().equals("bstl01")) {
				continue;
			}
			EquipInfo mainEquip = equipInfoService.getByCode(line.getCode() + "_EQUIP", "bstl01");
			if (mainEquip != null) {
				continue;
			}

			logger.info("-------------" + line.getCode());
			EquipInfo newEquip = new EquipInfo();
			BeanUtils.copyProperties(line, newEquip);
			String uuid = UUID.randomUUID().toString();
			newEquip.setId(uuid);
			newEquip.setCode(line.getCode() + "_EQUIP");
			newEquip.setName(line.getName() + "_设备");
			newEquip.setType(EquipType.MAIN_EQUIPMENT);
			newEquip.setCreateUserCode("admin");
			newEquip.setModifyUserCode("admin");
			newEquip.setCreateTime(new Date());
			newEquip.setModifyTime(new Date());
			newEquip.setStatus(EquipStatus.IDLE);
			newEquip.setOrgCode("1");
			newEquip.setModel("普通设备");
			newEquip.setMaintainer("admin");
			equipInfoService.insert(newEquip);
			ProductEquip t = new ProductEquip();
			t.setCreateUserCode("admin");
			t.setModifyUserCode("admin");
			t.setCreateTime(new Date());
			t.setModifyTime(new Date());
			t.setEquipId(newEquip.getId());
			t.setProductLineId(line.getId());
			t.setIsMain(true);
			t.setOrgCode("1");
			t.setStatus("VALID");
			productEquipService.insert(t);
		}
	}

	public void generateMesClientInfo() throws BiffException, IOException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codeOrName", "绝缘机");
		param.put("orgCode", orgCode);
		List<EquipInfo> equips = equipInfoService.getEquipLine(param);
		for (EquipInfo equip : equips) {
			MesClientManEqip mesClientManEqip = new MesClientManEqip();
			mesClientManEqip.setEqipId(equip.getId());
			mesClientManEqip.setMesClientId("1");
			mesClientManEqipService.insert(mesClientManEqip);
		}
	}

	private List<String> getListBySheet(jxl.Sheet sheet) {
		List<String> items = new ArrayList<String>();
		int maxRow = sheet.getRows();
		for (int i = 1; i < maxRow; i++) {
			Cell cell = sheet.getRow(i)[0];
			String realContents = JxlUtils.getRealContents(cell);
			if (realContents.isEmpty()) {
				break;
			}
			items.add(realContents);
		}
		return items;
	}

	@Test
	@Rollback(false)
	public void importQCTemp() throws BiffException, IOException {
		deleteQctemp();
		jxl.Workbook workbook = jxl.Workbook.getWorkbook(qcTempData.getInputStream());
		productQCTemplateService.importQcTemp(workbook.getSheet("中文"), orgCode);

	}

	/**
	 * 导入设备工艺参数映射表: T_INT_EQUIP_MES_WW_MAPPING
	 * */
	@Test
	@Rollback(false)
	public void importEquipMESWWMapping() throws BiffException, IOException {
		jxl.Workbook workbook = jxl.Workbook.getWorkbook(processParmData.getInputStream());
		super.importEquipMESWWMapping(workbook, orgCode);
		
		this.findLiveDataExistAndUpdate();  // 将采集不到的参数采集设置为false
	}

	/**
	 * 判断code是否存在在数采库里面
	 * */
	private void findLiveDataExistAndUpdate() {
		List<EquipMESWWMapping> noExist = new ArrayList<EquipMESWWMapping>();
		EquipMESWWMapping param = new EquipMESWWMapping();
		param.setNeedDa(true);
		List<EquipMESWWMapping> mappingArray = equipMESWWMappingDAO.find(param);
		for (EquipMESWWMapping mapping : mappingArray) {
			try {
				List<String> param1 = new ArrayList<String>();
				param1.add(mapping.getTagName());

				List<EquipParamAcquisition> liveArray = equipParamAcquisitionDAO.findLiveValue(param1, null, null);
				// if (null == liveArray || liveArray.size() == 0) {
				// noExist.add(mapping);
				// }
			} catch (Exception e) {
				noExist.add(mapping);
			}
		}
		for (EquipMESWWMapping mapping : noExist) {
			EquipInfo equipInfo = StaticDataCache.getLineEquipInfo(mapping.getEquipCode());
			System.out.println(mapping.getTagName() + "-----" + mapping.getEquipCode() + "-----"
					+ (null == equipInfo ? "无" : equipInfo.getStatus()));
			mapping.setNeedDa(false);
			equipMESWWMappingDAO.update(mapping);
		}
	}

	/**
	 * 导入设备工艺参数表: T_PRO_PROCESS_RECEIPT
	 * */
	@Test
	@Rollback(false)
	public void importProcessParmData() throws BiffException, IOException {
		jxl.Workbook workbook = jxl.Workbook.getWorkbook(processParmData.getInputStream());
		super.insertProcessParmData(workbook, orgCode);
	}

	// CustomerOrder生产中、已取消、已完成、所属组织为2各一条数据。
	// CustomerOrderItemDec生产中、已取消、已完成、无效状态、所属组织为2各一条数据。
	// WorkOrder生产中、已取消、已完成、所属组织为2各一条数据。
	// OrderTask生产中、已取消、已完成、所属组织为2各一条数据。
	private void insertNoiseData() {
		SalesOrder salesOrder = null;
		SalesOrderItem salesOrderItem = null;
		CustomerOrder customerOrder = null;
		CustomerOrderItem orderItem = null;
		CustomerOrderItemDec itemDec = null;
		CustomerOrderItemProDec proDec = null;
		OrderTask orderTask = null;
		WorkOrder workOrder = null;

		List<SalesOrderStatus> salesOrderStatusList = new ArrayList<SalesOrderStatus>();
		salesOrderStatusList.add(SalesOrderStatus.TO_DO);
		salesOrderStatusList.add(SalesOrderStatus.CANCELED);
		salesOrderStatusList.add(SalesOrderStatus.FINISHED);

		List<CustomerOrderStatus> customerOrderStatusList = new ArrayList<CustomerOrderStatus>();
		customerOrderStatusList.add(CustomerOrderStatus.TO_DO);
		customerOrderStatusList.add(CustomerOrderStatus.CANCELED);
		customerOrderStatusList.add(CustomerOrderStatus.FINISHED);

		List<ProductOrderStatus> productOrderStatusList = new ArrayList<ProductOrderStatus>();
		productOrderStatusList.add(ProductOrderStatus.TO_DO);
		productOrderStatusList.add(ProductOrderStatus.CANCELED);
		productOrderStatusList.add(ProductOrderStatus.FINISHED);

		List<WorkOrderStatus> workOrderStatusList = new ArrayList<WorkOrderStatus>();
		workOrderStatusList.add(WorkOrderStatus.TO_DO);
		workOrderStatusList.add(WorkOrderStatus.CANCELED);
		workOrderStatusList.add(WorkOrderStatus.FINISHED);

		String orgCode = "1";
		for (int i = 0; i < 3; i++) {
			if (salesOrderStatusList.get(i) == SalesOrderStatus.TO_DO) {
				orgCode = "2";
			}
			// 生产中
			salesOrder = new SalesOrder();
			salesOrder.setSalesOrderNo("T0000000" + (i + 1));
			salesOrder.setContractNo("T000" + (i + 1));
			salesOrder.setCustomerCompany("上海xx公司");
			salesOrder.setOperator("zdp");
			salesOrder.setStatus(salesOrderStatusList.get(i));
			salesOrder.setOrgCode(orgCode);
			salesOrderService.insert(salesOrder);

			salesOrderItem = new SalesOrderItem();
			salesOrderItem.setSalesOrderId(salesOrder.getId());
			salesOrderItem.setProductCode("D00001");
			salesOrderItemService.insert(salesOrderItem);

			customerOrder = new CustomerOrder();
			if (customerOrderStatusList.get(i) != CustomerOrderStatus.TO_DO) {
				customerOrder.setConfirmDate(new Date());
			}
			customerOrder.setCustomerOrderNo("20140328000" + (i + 1));
			customerOrder.setStatus(customerOrderStatusList.get(i));
			customerOrder.setFixedOa(true);
			customerOrder.setOrgCode(orgCode);
			customerOrder.setSalesOrderId(salesOrder.getId());

			customerOrderService.insert(customerOrder);

			orderItem = new CustomerOrderItem();
			orderItem.setCustomerOrderId(customerOrder.getId());
			orderItem.setSalesOrderItemId(salesOrderItem.getId());
			orderItem.setOrderLength(1000.0);
			orderItem.setProductCode("D00001");
			orderItem.setCraftsId("2");
			orderItem.setStatus(productOrderStatusList.get(i));
			orderItem.setUseStock(false);
			customerOrderItemService.insert(orderItem);
			List<ProductProcess> proProcessesList = resourceCache.getProductProcessByCraftId(orderItem.getCraftsId());

			itemDec = new CustomerOrderItemDec();
			itemDec.setOrderItemId(orderItem.getId());
			itemDec.setStatus(productOrderStatusList.get(i));
			itemDec.setLength(500.0);
			customerOrderItemDecService.insert(itemDec);

			for (ProductProcess process : proProcessesList) {
				proDec = new CustomerOrderItemProDec();
				proDec.setOrgCode(orgCode);
				proDec.setCraftsId("2");
				proDec.setProcessId(process.getId());
				proDec.setProcessPath(process.getFullPath());
				proDec.setProcessCode(process.getProcessCode());
				proDec.setUnFinishedLength(500.0);
				proDec.setOrderItemDecId(itemDec.getId());
				proDec.setStatus(productOrderStatusList.get(i));
				customerOrderItemProDecService.insert(proDec);

				// WorkOrder生产中
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -1);

				workOrder = new WorkOrder();
				workOrder.setOrgCode(orgCode);
				workOrder.setPreStartTime(calendar.getTime());
				workOrder.setWorkOrderNo("T0001_01");

				calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, 10);
				workOrder.setPreEndTime(calendar.getTime());
				workOrder.setStatus(workOrderStatusList.get(i));
				workOrderService.insert(workOrder);

				//
				orderTask = new OrderTask();
				orderTask.setOrderItemProDecId(proDec.getId());
				orderTask.setWorkOrderNo(workOrder.getWorkOrderNo());
				orderTask.setProcessId(proDec.getProcessId());
				orderTask.setProcessPath(proDec.getProcessPath());
				orderTask.setProductCode("D00001");
				orderTask.setContractNo("test");
				orderTask.setTaskLength(500.0);
				orderTask.setPlanStartDate(workOrder.getPreStartTime());
				orderTask.setPlanFinishDate(workOrder.getPreEndTime());
				orderTask.setOrgCode(orgCode);
				orderTask.setStatus(workOrderStatusList.get(i));
				orderTaskService.insert(orderTask);
			}
			orgCode = "1";
		}
		insertInProgress();
	}

	private void insertInProgress() {
		SalesOrder salesOrder = new SalesOrder();
		SalesOrderItem salesOrderItem = new SalesOrderItem();
		CustomerOrderItemProDec proDec = new CustomerOrderItemProDec();
		OrderTask orderTask = new OrderTask();
		orderTask.setOrgCode(orgCode);
		orderTask.setContractNo("test");
		orderTask.setTaskLength(500.0);
		orderTask.setStatus(WorkOrderStatus.FINISHED);
		WorkOrder workOrder = new WorkOrder();
		WorkTask workTask = new WorkTask();

		String productCode = "G00002";
		// 销售订单
		salesOrder.setSalesOrderNo("D0000001");
		salesOrder.setContractNo("D0001");
		salesOrder.setCustomerCompany("上海xx公司");
		salesOrder.setOperator("zdp");
		salesOrder.setStatus(SalesOrderStatus.IN_PROGRESS);
		salesOrder.setOrgCode(orgCode);
		salesOrderService.insert(salesOrder);
		// 销售订单明细
		salesOrderItem.setSalesOrderId(salesOrder.getId());
		salesOrderItem.setProductCode(productCode);
		salesOrderItem.setCustProductSpec("D1");
		salesOrderItem.setCustProductType("10*10");
		salesOrderItem.setProductType("高温电缆");
		salesOrderItem.setOrgCode(orgCode);
		salesOrderItem.setSaleorderLength(500.0);
		salesOrderItem.setStandardLength(500.0);
		salesOrderItemService.insert(salesOrderItem);

		CustomerOrder customer = customerOrderService.insert(salesOrder);
		customer.setStatus(CustomerOrderStatus.IN_PROGRESS);
		customerOrderService.update(customer);
		List<CustomerOrderItem> customerOrderItem = customerOrderItemService.getByCusOrderId(customer.getId());
		for (CustomerOrderItem item : customerOrderItem) {
			item.setStatus(ProductOrderStatus.IN_PROGRESS);
			customerOrderItemService.update(item);
		}
		List<CustomerOrderItemDec> customerOrderItemDec = customerOrderItemDecService
				.getByOrderItemId(customerOrderItem.get(0).getId());
		for (CustomerOrderItemDec dec : customerOrderItemDec) {
			dec.setStatus(ProductOrderStatus.IN_PROGRESS);
			customerOrderItemDecService.update(dec);
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		List<ProductProcess> list = resourceCache.getProductProcessByProductCode(productCode);
		Collections.reverse(list);
		Date startTime = null;
		Date endTime = null;

		// equips 用来存放设备
		String[] equips = { "LINE-JC1-0001", "LINE-JC1-0001", "LINE-PT-0001" };
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				cal.add(Calendar.DAY_OF_YEAR, -2);
				startTime = cal.getTime();
				cal.setTime(new Date());
				cal.add(Calendar.DAY_OF_YEAR, -1);
				endTime = cal.getTime();
			} else {
				startTime = endTime;
				cal.add(Calendar.DAY_OF_YEAR, 3);
				endTime = cal.getTime();
			}

			if (i == 0) {
				proDec.setStatus(ProductOrderStatus.FINISHED);
				workOrder.setStatus(WorkOrderStatus.FINISHED);
				workTask.setFinishwork(true);
				orderTask.setStatus(WorkOrderStatus.FINISHED);
			} else if (i == 1) {
				proDec.setStatus(ProductOrderStatus.FINISHED);
				workOrder.setStatus(WorkOrderStatus.FINISHED);
				orderTask.setStatus(WorkOrderStatus.FINISHED);
				workTask.setFinishwork(true);
			} else {
				proDec.setStatus(ProductOrderStatus.IN_PROGRESS);
				workOrder.setStatus(WorkOrderStatus.IN_PROGRESS);
				orderTask.setStatus(WorkOrderStatus.IN_PROGRESS);
				workTask.setFinishwork(false);
			}

			EquipInfo equip = equipInfoService.getByCode("LINE-PT-0001", orgCode);
			equip.setStatus(EquipStatus.IN_PROGRESS);
			equipInfoService.update(equip);

			// 订单明细工序用时分解
			proDec.setId(UUID.randomUUID().toString());
			proDec.setOrgCode(orgCode);
			proDec.setCraftsId(productCraftsService.getByProductCode(productCode).getId());
			proDec.setProcessId(list.get(i).getId());
			proDec.setProcessCode(list.get(i).getProcessCode());
			proDec.setProcessPath(list.get(i).getFullPath());
			proDec.setOrderItemDecId(customerOrderItemDec.get(0).getId());
			proDec.setUnFinishedLength(customerOrderItemDec.get(0).getLength());
			proDec.setLatestStartDate(startTime);
			proDec.setLatestFinishDate(endTime);
			proDec.setEquipCode(equips[i]);
			customerOrderItemProDecService.insert(proDec);

			// 生产单
			workOrder.setId(UUID.randomUUID().toString());
			workOrder.setOrgCode(orgCode);
			workOrder.setPreStartTime(startTime);
			workOrder.setPreEndTime(endTime);
			workOrder.setWorkOrderNo("D0001_01");
			workOrder.setEquipCode(equips[i]);
			workOrderService.insert(workOrder);

			// 设备负载
			workTask.setId(UUID.randomUUID().toString());
			workTask.setOrderItemProDecId(proDec.getId());
			workTask.setEquipCode(equips[i]);
			workTask.setWorkStartTime(proDec.getLatestStartDate());
			workTask.setWorkEndTime(proDec.getLatestFinishDate());
			workTaskService.insert(workTask);

			// 订单
			orderTask.setId(UUID.randomUUID().toString());
			orderTask.setOrderItemProDecId(proDec.getId());
			orderTask.setWorkOrderNo(workOrder.getWorkOrderNo());
			orderTask.setProcessId(proDec.getProcessId());
			orderTask.setProcessPath(proDec.getProcessPath());
			orderTask.setProductCode(productCode);
			orderTask.setEquipCode(equips[i]);
			orderTask.setPlanStartDate(proDec.getLatestStartDate());
			orderTask.setPlanFinishDate(proDec.getLatestFinishDate());
			orderTaskService.insert(orderTask);
		}
	}

	public void lineCalendar() {
		equipCalShiftService.delete(equipCalShiftService.getAll());
		equipCalendarService.delete(equipCalendarService.getAll());

		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 7);
		Date date1 = calendar.getTime();
		calendar.add(Calendar.DATE, 1);
		Date date2 = calendar.getTime();

		EquipCalendar equipCalendar = new EquipCalendar();
		equipCalendar.setDateOfWork(df.format(date1));
		equipCalendar.setOrgCode(orgCode);
		equipCalendar.setEquipCode("LINE-DY-0001");
		equipCalendarService.insert(equipCalendar);
		EquipCalShift equipCalShift = new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId("1");
		equipCalShiftService.insert(equipCalShift);
		equipCalShift = new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId("3");
		equipCalShiftService.insert(equipCalShift);

		equipCalendar = new EquipCalendar();
		equipCalendar.setDateOfWork(df.format(date2));
		equipCalendar.setOrgCode(orgCode);
		equipCalendar.setEquipCode("LINE-DY-0001");
		equipCalendarService.insert(equipCalendar);
		equipCalShift = new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId("1");
		equipCalShiftService.insert(equipCalShift);
		equipCalShift = new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId("3");
		equipCalShiftService.insert(equipCalShift);
		equipCalShift = new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId("4");
		equipCalShiftService.insert(equipCalShift);

		equipCalendar = new EquipCalendar();
		equipCalendar.setDateOfWork(df.format(date1));
		equipCalendar.setOrgCode(orgCode);
		equipCalendar.setEquipCode("LINE-RB-0001");
		equipCalendarService.insert(equipCalendar);
		equipCalShift = new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId("1");
		equipCalShiftService.insert(equipCalShift);
		equipCalShift = new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId("3");
		equipCalShiftService.insert(equipCalShift);
		equipCalShift = new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId("4");
		equipCalShiftService.insert(equipCalShift);

		equipCalendar = new EquipCalendar();
		equipCalendar.setDateOfWork(df.format(date2));
		equipCalendar.setOrgCode(orgCode);
		equipCalendar.setEquipCode("LINE-RB-0001");
		equipCalendarService.insert(equipCalendar);
		equipCalShift = new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId("1");
		equipCalShiftService.insert(equipCalShift);
		equipCalShift = new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId("3");
		equipCalShiftService.insert(equipCalShift);
		equipCalShift = new EquipCalShift();
		equipCalShift.setEquipCalendarId(equipCalendar.getId());
		equipCalShift.setWorkShiftId("4");
		equipCalShiftService.insert(equipCalShift);
	}
}
