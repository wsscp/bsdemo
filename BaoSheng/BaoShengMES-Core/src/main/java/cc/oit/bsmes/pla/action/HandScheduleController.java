package cc.oit.bsmes.pla.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cc.oit.bsmes.bas.model.Attachment;
import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.bas.service.AttachmentService;
import cc.oit.bsmes.common.concurrent.RenameThreadPoolExecutor;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.constants.ProcessInfoType;
import cc.oit.bsmes.common.constants.ProcessSection;
import cc.oit.bsmes.common.constants.WorkOrderOperateType;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfacePLM.model.Prcv;
import cc.oit.bsmes.interfacePLM.service.PrcvService;
import cc.oit.bsmes.interfacemessage.service.MessageService;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.dao.CustomerOrderItemDAO;
import cc.oit.bsmes.pla.dao.ProductDAO;
import cc.oit.bsmes.pla.model.AttachFile;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.FinishedProduct;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.AttachFileService;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.HandScheduleService;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.pla.service.OrderOAService;
import cc.oit.bsmes.pla.service.OrderProcessPRService;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductCraftsBz;
import cc.oit.bsmes.pro.model.ProductCraftsWip;
import cc.oit.bsmes.pro.service.EqipListBzService;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProcessInOutBzService;
import cc.oit.bsmes.pro.service.ProcessInformationService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;
import cc.oit.bsmes.pro.service.ProductCraftsBzService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductCraftsWipService;
import cc.oit.bsmes.pro.service.ProductProcessBzService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.dto.MethodReturnDto;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.model.WorkOrderOperateLog;
import cc.oit.bsmes.wip.service.WorkOrderOperateLogService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 手动排程
 * 
 * @author DingXintao
 */
@Controller
@RequestMapping("/pla/handSchedule")
public class HandScheduleController {
	@Resource
	private HandScheduleService handScheduleService;
	@Resource
	private OrderOAService orderOAService;
	@Resource
	private EqipListBzService eqipListBzService;
	@Resource
	private AttachFileService attachFileService;
	@Resource
	private SalesOrderItemService salesOrderItemService;
	@Resource
	private SalesOrderService salesOrderService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private CustomerOrderItemDecService customerOrderItemDecService;
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private ProductProcessBzService productProcessBzService;
	@Resource
	private EquipListService equipListService;
	@Resource
	private ProcessInformationService processInformationService;
	@Resource
	private MatService matService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private CustomerOrderService customerOrderService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private ProcessInOutBzService processInOutBzService;
	@Resource
	private OrderProcessPRService orderProcessPRService;
	@Resource
	private MaterialRequirementPlanService materialRequirementPlanService;
	@Resource
	private ProductDAO productDAO;
	@Resource
	private CustomerOrderItemDAO customerOrderItemDAO;
	@Resource
	private ProcessReceiptService processReceiptService;
	@Resource
	private MessageService messageService;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private ProductCraftsWipService productCraftsWipService;
	@Resource
	private WorkOrderOperateLogService workOrderOperateLogService;
	@Resource
	private AttachmentService attachmentService;
	
	@RequestMapping(produces = "text/html")
	public String index(Model model,HttpServletRequest request) {
		model.addAttribute("moduleName", "pla");
		model.addAttribute("submoduleName", "handSchedule");
		// 1、获取当前用户的角色
	    HttpSession	session = request.getSession();
		List<Role> roleList = SessionUtils.getUserRoles(session);
		JSONArray arry = new JSONArray();
		for(Role role: roleList){
			JSONObject obj = new JSONObject();
			if(StringUtils.equals(role.getCode(), "JY_DD")){
//				obj.put("code", role.getCode());
//				obj.put("name", "绝缘");
//				arry.add(obj);
				arry.add(role.getCode());
			}
			if(StringUtils.equals(role.getCode(), "CL_DD")){
//				obj.put("code", role.getCode());
//				obj.put("name", "成缆");
				arry.add(role.getCode());
			}
			if(StringUtils.equals(role.getCode(), "HT_DD")){
//				obj.put("code", role.getCode());
//				obj.put("name", "护套");
				arry.add(role.getCode());
			}
		}
		
		model.addAttribute("roleList", arry);
		
		return "pla.handSchedule";
	}

	/**
	 * 把请求参数放入map对象
	 * 
	 * @param request HTTP请求
	 * @param findParams 放入的map对象
	 * @param query 模糊查询值
	 * @param queryType 模糊查询对象
	 * */
	@SuppressWarnings("unchecked")
	private void putQueryParam2Map(HttpServletRequest request, Map<String, Object> findParams, String[] likeParams,
			String[] equalsParams, Integer start, Integer end, List<Sort> sortArray) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Iterator<String> paramKeys = parameterMap.keySet().iterator();
		paramLoop: while (paramKeys.hasNext()) {
			String paramKey = paramKeys.next();
			String[] param = parameterMap.get(paramKey);
			if (null != param && param.length > 0 && StringUtils.isNotBlank(param[0])) {
				if (null != likeParams) {
					for (String likeParam : likeParams) {
						if (likeParam.equals(paramKey)) {
							findParams.put(paramKey, "%" + param[0] + "%");
							continue paramLoop;
						}
					}
				}
				if (null != equalsParams) {
					for (String equalsParam : equalsParams) {
						if (equalsParam.equals(paramKey)) {
							findParams.put(paramKey, param[0]);
							continue paramLoop;
						}
					}
				}
				findParams.put(paramKey, new ArrayList<String>(Arrays.asList(param)));
			}
		}
		findParams.put("start", start); // 顺序不能变，必须要覆盖
		findParams.put("end", end);
		findParams.put("sort", sortArray);
		findParams.put("orgCode", orderOAService.getOrgCode());
	}
	
	/**
	 * 获取可以手动排程的订单
	 * 
	 * @param request 请求对象
	 * @param contractNo 合同号
	 * @param productType 产品型号
	 * @param productSpec 产品规格
	 * @param start 分页开始
	 * @param limit 分页结束
	 * @param sort 列表排序
	 * */
	@RequestMapping
	@ResponseBody
	public TableView list(HttpServletRequest request, @RequestParam(required = false) Integer start,
			@RequestParam(required = false) Integer limit, @RequestParam(required = false) String sort) {
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		Map<String, Object> findParams = new HashMap<String, Object>();
		// 将查询条件放入查询对象
		this.putQueryParam2Map(request, findParams, null, new String[] { "createDate", "isYunMu", "finishJy" }, start,
				(start + limit), sortArray);
		List<Map<String, String>> list = customerOrderItemService.getHandScheduleOrder(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(customerOrderItemService.countHandScheduleOrder(findParams));
		return tableView;
	}
	
	/**
	 * 排产界面：成缆、护套的查询页面
	 * 
	 * @return TableView
	 * */
	@ResponseBody
	@RequestMapping(value = "list2", method = RequestMethod.GET)
	public TableView list2(HttpServletRequest request, 
			@RequestParam(required = false) ProcessSection currentSection,
			@RequestParam(required = false) Integer start,
			@RequestParam(required = false) Integer limit, 
			@RequestParam(required = false) String sort) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		// 1、将排序的字段自动转换为数据库字段 适用于自定义分页
		this.changeSortProperty2Column(sortArray);
		// 2、把请求参数放入map对象
		this.putQueryParam2Map(request, findParams, new String[] { "workOrderNo", "contractNo"}, new String[] { "releaseDate", "releaseDateEnd", "isDispatch" }, start,
				(start + limit), sortArray);
		
		// 3、获取工段的seq，放入查询条件
		findParams.put("nextSection", currentSection.getOrder());
		// 4、查询
		TableView tableView = new TableView();
		tableView.setRows(workOrderService.findForHandSchedule(findParams));
		tableView.setTotal(workOrderService.findForHandScheduleCount(findParams));
		return tableView;
	}
	
	/**
	 * 获取可以手动排程的订单: 绝缘的主列表 - 点击排生产单弹出编辑框时所需要的订单数据
	 * 
	 * @param request 请求对象
	 * @param workOrderIds 生产单ID数组
	 * */
	@RequestMapping(value = "/getOrderData")
	@ResponseBody
	public List<Map<String, String>> getOrderData(HttpServletRequest request, @RequestParam String workOrderIds) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("workOrderIdArray", Arrays.asList(workOrderIds.split(",")));
		List<Map<String, String>> list = customerOrderItemService.getOrderData(param);
		return list;
	}
	
	/**
	 * 排生产单 : 获取成缆初始化页面所需要的数据：工序、投入产出
	 * 
	 * @param section 工段
	 * @param orderItemIdArray 查询生产单proDec的所有生产单ID
	 * @param orderItemIdProcessArray 查询工序所需的生产单号，相当于orderItemIdArray的去重
	 * @param preWorkOrderNo 上一道生产单的生产单号：用于查询级联字段[preProcessesMerged-上一道工序工序合成JSON字段]
	 * */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ResponseBody
	@RequestMapping(value = "getInitData", method = RequestMethod.GET)
	public Map<String, List<Object>> getInitData(@RequestParam ProcessSection section, @RequestParam String[] orderItemIdArray,
			@RequestParam(required=false) String[] orderItemIdProcessArray, @RequestParam(required=false) String preWorkOrderNo) {

		Date a = new Date();

		// 返回MAP
		Map map = new HashMap(); 
		// 根据工段查询出来的工序组合
		processInformationService.getOrderProcessBySection(map, section, (null == orderItemIdProcessArray) ? orderItemIdArray : orderItemIdProcessArray);

		Date b = new Date();
		System.err.println((b.getTime() - a.getTime() )+"========================processesMap");
		
		// 根据工段查询出来的所有投入产出信息
		List<CustomerOrderItemProDec> proDecList = customerOrderItemProDecService.getOrderProcessByCode(section, orderItemIdArray, preWorkOrderNo);
		if(section == ProcessSection.JY){
			List<Map<String, String>> prodecArray = customerOrderItemProDecService.getFirstProcessByCode(orderItemIdArray);
			if (!CollectionUtils.isEmpty(prodecArray) && prodecArray.get(0).get("PROCESS_NAME").indexOf("云母带") >= 0) {
				for (CustomerOrderItemProDec c : proDecList) {
					c.setWiresStructure(c.getWiresStructure() + "<br/>" + prodecArray.get(0).get("PROCESS_NAME"));
				}
			}
		}
		
		Date c = new Date();
		System.err.println((c.getTime() - b.getTime())+"========================proDecList");
		
		// 根据生产单号查询当前生产单已经排过的工序情况
		String processesMergedArrayStr = workOrderService.getProcessesMergedArrayByWorkNo(preWorkOrderNo);
		
		Date d = new Date();
		System.err.println((d.getTime() - c.getTime())+"========================processesMergedArrayStr");
		
		// 根据生产单号查询出来的生产单流程
		List<WorkOrder> workOrderArray = workOrderService.getWorkOrderFlowArray(preWorkOrderNo);

		Date e = new Date();
		System.err.println((e.getTime() - d.getTime())+"========================workOrderArray");
		
		
		// 该工段需要的设备集合
		List<EquipInfo> equipArray = equipInfoService.getEquipByProcessSection(section.getOrder());

		Date f = new Date();
		System.err.println((f.getTime() - e.getTime())+"========================equipArray");
		
		// 原材料库存信息
		List<Map<String, String>> materialsInventory = inventoryService.getMaterialsInventory(preWorkOrderNo,
				orderItemIdArray, section);

		Date g = new Date();
		System.err.println((g.getTime() - f.getTime())+"========================materialsInventory");
		
		// 查询接收人信息
		List<WorkOrder> receivers = handScheduleService.getReceiver(section.getOrder());
		
		// 查询此订单关联的所有的生产单号
		List<Map<String, Object>> allWorkOrder = workOrderService.getAllWorkOrderByOIID(orderItemIdArray);

		Date h = new Date();
		System.err.println((h.getTime() - g.getTime())+"========================receivers");
		
		System.err.println((h.getTime() - a.getTime())+"========================totla");
		
		map.put("proDecList", proDecList);
		map.put("processesMergedArrayStr", processesMergedArrayStr);
		map.put("equipArray", equipArray);
		map.put("workOrderArray", workOrderArray);
		map.put("materialsInventory", materialsInventory);
		map.put("receivers", receivers);
		map.put("allWorkOrder", allWorkOrder);
		return map;
	}

	/**
	 * @throws IOException
	 * @Title: onExportOutPlan
	 * @Description: TODO(导出外计划)
	 * @param: request 请求
	 * @param: response 响应
	 * @param: dataArray 导出数据列表
	 * @return: void
	 * @throws IOException
	 * @throws BiffException
	 */
	@ResponseBody
	@RequestMapping(value = "putOutPlan2Excel", method = RequestMethod.POST)
	public String putOutPlan2Excel(HttpServletRequest request, HttpServletResponse response, String columnArray,
			String dataIndexArray, String dataArray) throws FileNotFoundException, IOException, RowsExceededException,
			WriteException, BiffException {
		return handScheduleService.putOutPlan2Excel(columnArray, dataIndexArray, dataArray);
	}

	/**
	 * @throws IOException
	 * @Title: onExportOutPlan
	 * @Description: TODO(导出外计划)
	 * @param: request 请求
	 * @param: response 响应
	 * @param: dataArray 导出数据列表
	 * @return: void
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "onExportOutPlan", method = RequestMethod.GET)
	public JSONObject onExportOutPlan(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String sourcePath) throws IOException {
		File file = new File(sourcePath);
		if (file.exists()) {
			String fileName = URLEncoder.encode(file.getName(), "UTF8");
			String userAgent = request.getHeader("User-Agent").toLowerCase(); // 获取用户代理
			if (userAgent.indexOf("msie") != -1) { // IE浏览器
				fileName = "filename=\"" + fileName + "\"";
			} else if (userAgent.indexOf("mozilla") != -1) { // firefox浏览器
				fileName = "filename*=UTF-8''" + fileName;
			}
			response.reset();
			// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
			response.setContentType("multipart/form-data");
			// 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
			response.setHeader("Content-Disposition", "attachment;" + fileName);
			handScheduleService.onExportOutPlan(response.getOutputStream(), file);
		}
		return null;
	}

	/**
	 * @Title: updateSpecialFlag
	 * @Description: TODO((更新订单的特殊状态: 0:厂外计划;1:计划已报;2:手工单;3:库存生产)
	 * @author: DinXintao
	 * @version V1.0
	 * @date: 2016年3月17日 上午9:42:29
	 * @param: request 请求
	 * @param: ids 订单ID(T_PLA_CUSTOMER_ORDER_ITEM)
	 * @param: specialFlag 0:厂外计划;1:计划已报;2:手工单;3:库存生产
	 * @param: finished 是否完成订单状态
	 * @return: JSON
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "updateSpecialFlag", method = RequestMethod.POST)
	public JSON updateSpecialFlag(HttpServletRequest request, @RequestParam String ids, @RequestParam String specialFlag,
			@RequestParam(required=false) Boolean finished) {
		MethodReturnDto dto = handScheduleService.updateSpecialFlag(ids, specialFlag, finished);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage(), dto.getJsonMap());
	}

	/**
	 * 绝缘查看库存明细
	 * 
	 * @author DingXintao
	 * @param preWorkOrderNo 上道生产单号
	 * @param orderItemIds 订单id字符串，逗号分割
	 * @param section 工段
	 * @return TableView
	 * */
	@ResponseBody
	@RequestMapping(value = "inventoryGrid", method = RequestMethod.GET)
	public List<Map<String, String>> inventoryGrid(HttpServletRequest request,
			@RequestParam(required = false) String preWorkOrderNo, @RequestParam String orderItemIds,
			@RequestParam ProcessSection section) {
		return inventoryService.getMaterialsInventory(preWorkOrderNo, orderItemIds.split(","), section);
	}

	/**
	 * 排程前校验：产品是否已经下发
	 * 
	 * @param request 请求对象
	 * @param orderItemIdArray 校验的订单ID
	 * */
	@RequestMapping(value = "/hasAuditOrder")
	@ResponseBody
	public JSON hasAuditOrder(HttpServletRequest request, @RequestParam String orderItemIdArray,
			@RequestParam boolean isyunmu) {
		MethodReturnDto dto = handScheduleService.hasAuditOrder(orderItemIdArray, isyunmu);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage(), dto.getJsonMap());
	}

	/**
	 * 将排序的字段自动转换为数据库字段 适用于自定义分页
	 * */
	private void changeSortProperty2Column(List<Sort> sortArray) {
		if (null != sortArray) {
			for (Sort sort : sortArray) {
				sort.setProperty(sort.getProperty().replaceAll("([A-Z])", "_$0").toUpperCase());
			}
		}
	}

	/**
	 * 查看生产单
	 * 
	 * @author DingXintao
	 * @return TableView
	 * */
	@ResponseBody
	@RequestMapping(value = "showWorkOrder", method = RequestMethod.GET)
	public TableView showWorkOrder(HttpServletRequest request, @RequestParam(required = false) Integer start,
			@RequestParam(required = false) Integer limit, @RequestParam(required = false) String sort,
			@RequestParam int page) {
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		this.changeSortProperty2Column(sortArray);
		Map<String, Object> findParams = new HashMap<String, Object>();
		// 把请求参数放入map对象
		this.putQueryParam2Map(request, findParams, new String[] { "workOrderNo" }, new String[] { "releaseDate",
				"requireFinishDate", "isDispatch", "equipCode" }, start, (start + limit), sortArray);

		List<WorkOrder> list = workOrderService.findForHandSchedule(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(workOrderService.findForHandScheduleCount(findParams));
		return tableView;
	}

	/**
	 * 
	 * 获取工段可选设备：调整设备使用
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单号
	 * 
	 * @return List<EquipInfo>
	 */
	@ResponseBody
	@RequestMapping(value = "getEquipForChangeOrderEquip", method = RequestMethod.GET)
	public List<Map<String, String>> getEquipForChangeOrderEquip(@RequestParam String workOrderNo) {
		return equipInfoService.getEquipForChangeOrderEquip(workOrderNo);
	}
	
	/**
	 * 根据生产单获取该生产单关联的所有可选设备：调整设备使用
	 * 
	 * @author songqianke
	 * @param workOrderNo 生产单编号
	 * @param processCode 工序编码
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(value = "getAllCanChooseEquipByProcessCode", method = RequestMethod.GET)
	public List<Map<String, Object>> getAllCanChooseEquipByProcessCode(@RequestParam String processCode, @RequestParam String workOrderNo) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		WorkOrder order = workOrderService.getByWorkOrderNO(workOrderNo);
		if (order == null) {
			return list;
		}
		String equipCodes = order.getEquipCode();

		List<String> equipCodeList = new ArrayList<String>();
		if (order.getEquipCode() != null) {
			equipCodeList = Arrays.asList(equipCodes.split(","));
		}
		ProcessInformation processinfo = processInformationService.getByCode(processCode);
		List<EquipInfo> equipArray = equipInfoService.getEquipByProcessSectionN(processinfo.getSectionSeq());
		for (EquipInfo info : equipArray) {
			Map<String, Object> item = new HashMap<String, Object>();
			Boolean hasCode = false;
			item.put("EQUIPNAME", info.getName());
			item.put("EQUIPALIAS", info.getEquipAlias());
			item.put("EQUIPCODE", info.getCode());
			for (String equipCode : equipCodeList) {
				if (info.getCode().equals(equipCode)) {
					item.put("CODE", info.getCode());
					hasCode = true;
					break;
				}
			}
			if (!hasCode) {
				item.put("CODE", "");
			}
			list.add(item);

		}
		return list;
	}

	/**
	 * 设备调整：修改生产单的使用设备
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单编号
	 * @param equipNameArrayStr 设备名称集
	 * @param equipCodeArrayStr 设备编码集
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(value = "changeWorkEquipSub", method = RequestMethod.POST)
	public JSON changeWorkEquipSub(@RequestParam String workOrderNo, @RequestParam String equipNameArrayStr,
			@RequestParam String equipCodeArrayStr) {
		MethodReturnDto dto = handScheduleService.changeWorkEquipSub(workOrderNo, equipNameArrayStr, equipCodeArrayStr);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}

	/**
	 * 查看生产单单个明细
	 * 
	 * @author DingXintao
	 * @return TableView
	 * */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "showWorkOrderDetail", method = RequestMethod.GET)
	public Map<String, List> showWorkOrderDetail(HttpServletRequest request, @RequestParam String workOrderNo,
			@RequestParam(required = false) String processSection) {
		Map<String, List> map = new HashMap<String, List>();

		// 查询
		List<Map<String, String>> orderItemList = customerOrderItemService.showWorkOrderDetailCommon(workOrderNo);
		List<Map<String, String>> matList = materialRequirementPlanService.showWorkOrderDetailMat(workOrderNo);
		// 根据生产单号查询出来的生产单流程
		List<WorkOrder> workOrderArray = workOrderService.getWorkOrderFlowArray(workOrderNo);
		map.put("workOrderArray", workOrderArray);
		map.put("orderItemList", orderItemList);
		map.put("matList", matList);
		return map;
	}

	/**
	 * 查询产品可选工艺
	 * 
	 * @author DingXintao
	 * @param productCode 产品编码
	 * @return
	 * */
	@RequestMapping(value = "/chooseCrafts")
	@ResponseBody
	public List<ProductCrafts> chooseCrafts(@RequestParam String productCode, @RequestParam String productType,
			@RequestParam String productSpec) {
		List<ProductCrafts> list = productCraftsService.getChooseCraftsArray(productCode);
		if (list.size() == 0) {
			productCode = productCode.replaceAll("(\\(.+\\))", "");
			list = productCraftsService.getChooseCraftsArray(productCode);
		}
		if (list.size() == 0) {
			productType = productType.replaceAll("(\\(.+\\))", "");
			productSpec = productSpec.replaceAll("(\\(.+\\))", "");
			Product product = productDAO.getByProductTypeAndSpec(productType, productSpec);
			if (product == null) {
				product = productDAO.getByProductTypeAndSpec(productType, productSpec);
			}

			if (product != null) {
				list = productCraftsService.getChooseCraftsArray(product.getProductCode());
			}
		}
		return list;
	}

	/**
	 * 保存订单产品的工艺关系：(重置工艺：生成wip表，并更新订单的工艺id)
	 * 
	 * @author DingXintao
	 * @param orderItemId 订单产品工ID
	 * @param craftsId 关联工艺ID
	 * @return ProductCraftsWip
	 * */
	@ResponseBody
	@RequestMapping(value = "updateOrderItemCraftsId", method = RequestMethod.POST)
	public ProductCraftsWip updateOrderItemCraftsId(@RequestParam String orderItemId, @RequestParam String craftsId) {
		CustomerOrderItem customerOrderItem = customerOrderItemService.getById(orderItemId);
		String newCraftsId = salesOrderItemService.changeCrafts(customerOrderItem.getCraftsId(), craftsId, customerOrderItem.getSalesOrderItemId());
		if(StringUtils.isNotEmpty(newCraftsId)){
			if(StringUtils.isEmpty(customerOrderItem.getCraftsId())){ // 一开始没有工艺的话，changeCrafts方法里是不会更新的，需要自己更新
				customerOrderItem.setCraftsId(newCraftsId);
				customerOrderItemService.update(customerOrderItem);
			}
			return productCraftsWipService.getById(newCraftsId);
		}else{
			return new ProductCraftsWip();
		}
	}

	/**
	 * 获取可排序的订单
	 * 
	 * @author DingXintao
	 * @return List<CustomerOrder>
	 * */
	@ResponseBody
	@RequestMapping(value = "findForHandSetPriority", method = RequestMethod.GET)
	public List<CustomerOrder> findForHandSetPriority() {
		List<CustomerOrder> list = customerOrderService.findForHandSetPriority(new CustomerOrder());
		return list;
	}

	/**
	 * 保存排序并计算
	 * 
	 * @author DingXintao
	 * @return JSON
	 * */
	@ResponseBody
	@RequestMapping(value = "updateSeqAndCalculate", method = RequestMethod.POST)
	public JSON updateSeqAndCalculate(@RequestParam String jsonText) throws Exception {
		MethodReturnDto dto = handScheduleService.updateSeqAndCalculate(jsonText);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}

	/**
	 * 重新计算
	 * 
	 * @author DingXintao
	 * @return JSON
	 * */
	@ResponseBody
	@RequestMapping(value = "calculateAgain", method = RequestMethod.POST)
	public JSON calculateAgain() throws Exception {
		MethodReturnDto dto = handScheduleService.calculateAgain();
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}

	/**
	 * 查看生产单 - 调整生产单加工顺序
	 * 
	 * @author DingXintao
	 * @return List<WorkOrder>
	 * */
	@ResponseBody
	@RequestMapping(value = "changeWorkOrderSeq", method = RequestMethod.GET)
	public List<WorkOrder> changeWorkOrderSeq(@RequestParam String equipCode) {
		// 查询
		List<WorkOrder> list = workOrderService.changeWorkOrderSeq(equipCode);
		return list;
	}
	
	/**
	 * 根据工段获取设备列表
	 * @author 前克
	 * @date 2016-08-31
	 * @param processCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getEquipByProcessSectionN",method=RequestMethod.GET)		    
	public List<EquipInfo> getEquipByProcessSectionN(@RequestParam(required = false) String section){
		List<EquipInfo> result = equipInfoService.getEquipByProcessSectionN(section);
	    return result;
	}

	/**
	 * 查看生产单 - 调整生产单加工顺序 - 保存
	 * 
	 * @author DingXintao
	 * @param equipCode 设备编码
	 * @param updateSeq 生产单排序JSON:[WORKORDERID, WORKORDERID]
	 * @return JSON
	 * */
	@ResponseBody
	@RequestMapping(value = "updateWOrkOrderSeq", method = RequestMethod.POST)
	public JSON updateWOrkOrderSeq(@RequestParam String equipCode, @RequestParam String updateSeq) throws Exception {
		MethodReturnDto dto = handScheduleService.updateWOrkOrderSeq(equipCode, updateSeq);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}

	/**
	 * @Title mergeCustomerOrderItem
	 * @Description TODO(保存合并订单的生产单)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年4月6日 下午4:13:27
	 * @param docMakerUserCode 制单人
	 * @param receiverUserCode 接受人
	 * @param requireFinishDate 完成日期
	 * @param equipCodes 设备编码数组
	 * @param equipName 设备名称
	 * @param processName 工序名称
	 * @param processCode 工序编码
	 * @param userComment 备注
	 * @param specialReqSplit
	 * @param processJsonData 
	 *            工序Json对象，投入产出：转换对象CustomerOrderItemProDec、MaterialRequirementPlan保存
	 * @param orderTaskLengthJsonData 该订单产品该生产单所下发的长度
	 * @param preWorkOrderNo 上一道生产单的生产单号
	 * @param processesMergedArray 成缆工段生产单保存当前生产单所用工序的Json字符串
	 * @param workOrderSection 生产单所属工段 1:绝缘工段 2:成缆工段 3:护套工段
	 * @param nextSection 生产单下一个加工工段 1:绝缘工段 2:成缆工段 3:护套工段
	 * @param cusOrderItemIds 当前生产单中下发的所有客户生产订单明细IDs
	 * @param ids4FinishedJY 绝缘工段：已经完全下发所有工序的订单ID
	 * @param completeCusOrderItemIds 成缆工段：已经完全下完成缆工序的订单ID
	 * @param isDispatch 是否急件
	 * @param isHaved 是否陈线
	 * @param isAbroad 是否出口
	 * @return JSON
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "mergeCustomerOrderItem", method = RequestMethod.POST)
	public JSON mergeCustomerOrderItem(@RequestParam String docMakerUserCode, @RequestParam String receiverUserCode,
			@RequestParam String requireFinishDate, @RequestParam String equipCodes, @RequestParam String equipName,
			@RequestParam String processName, @RequestParam String processCode, @RequestParam String userComment,
			@RequestParam(required = false) String specialReqSplit,
			@RequestParam(required = false) String processJsonData,
			@RequestParam(required = false) String orderTaskLengthJsonData,
			@RequestParam(required = false) String preWorkOrderNo,
			@RequestParam(required = false) String processesMergedArray,
			@RequestParam(required = false) String workOrderSection,
			@RequestParam(required = false) String nextSection, @RequestParam(required = false) String cusOrderItemIds,
			@RequestParam(required = false) String ids4FinishedJY,
			@RequestParam(required = false) String completeCusOrderItemIds,
			@RequestParam(required = false) String isDispatch, @RequestParam(required = false) String isHaved,
			@RequestParam(required = false) String isAbroad) {

		MethodReturnDto dto = handScheduleService.mergeCustomerOrderItem(docMakerUserCode, receiverUserCode,
				requireFinishDate, equipCodes, equipName, processName, processCode, userComment, specialReqSplit,
				processJsonData, orderTaskLengthJsonData, preWorkOrderNo, processesMergedArray, workOrderSection,
				nextSection, cusOrderItemIds, ids4FinishedJY, completeCusOrderItemIds, isDispatch, isHaved, isAbroad);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage(), dto.getJsonMap());

	}

	/**
	 * @Title:       updateFinishedJY
	 * @Description: TODO(更新订单已经全部下发[绝缘工段])
	 * @param:       finishIds 已经完成绝缘工段全部下发的订单id
	 * @return:      void   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "updateFinishedJY")
	public void updateFinishedJY(@RequestParam String finishIds) {
		for (String id : finishIds.split(",")) {
			CustomerOrderItem customerOrderItem = customerOrderItemService.getById(id);
			customerOrderItem.setFinishJy(true);
			customerOrderItemService.update(customerOrderItem);
		}
	}
	
	/**
	 * 更新生产单的下一个工段
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单号
	 * @param nextSection 下一个工段
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(value = "updateNextSection")
	public void updateNextSection(@RequestParam String workOrderNo, @RequestParam String nextSection, 
			@RequestParam(required=false) String completeCusOrderItemIds) {
		for (String no : workOrderNo.split(",")) {
			WorkOrder workOrder = workOrderService.getByWorkOrderNO(no);
			workOrder.setNextSection(nextSection);
			if(StringUtils.isNotEmpty(completeCusOrderItemIds)){
				String completeCusOrderItemId = workOrder.getCompleteCusOrderItemIds();
				// 更新auditedCusOrderItemIds
				workOrder.setCompleteCusOrderItemIds(StringUtils.isEmpty(completeCusOrderItemId) ? completeCusOrderItemIds
						: (completeCusOrderItemId + "," + completeCusOrderItemIds));
			}
			workOrderService.update(workOrder);
		}
	}

	/**
	 * 获得订单产品详细生产情况
	 * 
	 * @author HouLianXue
	 * @param processId
	 * 
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(value = "getWorkOrderFinishDetail", method = RequestMethod.GET)
	public JSON getWorkOrderFinishDetail(@RequestParam(required=false) String orderItemId, @RequestParam(required=false) ProcessInfoType section, 
			@RequestParam(required=false) String workOrderNo) {
		// 1、获取生产单：
		// 1.1、如果订单id不为空，则获取订单下的所有指定工段的生产单；
		// 1.2、如果生产单号不为空，则获取该生产单。
		List<WorkOrder> workOrderBaseInfoList = new ArrayList<WorkOrder>();
		if(StringUtils.isNotEmpty(orderItemId)){
			workOrderBaseInfoList = workOrderService.getWorkOrderBaseInfo(orderItemId, section.toString());
		}else if(StringUtils.isNotEmpty(workOrderNo)){
			WorkOrder wo = workOrderService.getByWorkOrderNO(workOrderNo);
			workOrderBaseInfoList.add(wo);
		}

		// 2、循环生产单，获取生产单下的任务明细：加工情况。
		JSONArray workOrderInfoList = new JSONArray();
		for (WorkOrder workOrder : workOrderBaseInfoList) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("workOrderNo", workOrder.getWorkOrderNo() + "(" + workOrder.getProcessName() + ")");
			jsonObject.put("docMakerUserCode", workOrder.getDocMakerUserCode());
			jsonObject.put("receiverUserCode", workOrder.getReceiverUserCode());

			jsonObject.put("releaseDate", DateUtils.convert(workOrder.getReleaseDate(), DateUtils.DATE_FORMAT));
			jsonObject.put("requireFinishDate",
					DateUtils.convert(workOrder.getRequireFinishDate(), DateUtils.DATE_FORMAT));
			List<WorkOrder> datailList = workOrderService.getWorkOrderFinishDetail(orderItemId,
					workOrder.getWorkOrderNo());

			jsonObject.put("data", datailList);
			workOrderInfoList.add(jsonObject);
		}
		return workOrderInfoList;
	}
	
	/**
	 * 更新生产单状态：1、workOrder 2、orderTask 3、proDec
	 * 
	 * @param workOrderNo 生产单号
	 * @param status 状态
	 * */
	@ResponseBody
	@RequestMapping(value = "updateWorkerOrderStatus", method = RequestMethod.POST)
	public JSON updateWorkerOrderStatus(@RequestParam String workOrderNo, @RequestParam WorkOrderStatus status) {
		MethodReturnDto dto = workOrderService.updateWorkerOrderStatus(workOrderNo, status);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}

	/**
	 * 生产单设置为急件
	 * */
	@ResponseBody
	@RequestMapping(value = "updateWorkerOrderToDispatch", method = RequestMethod.POST)
	public JSON updateWorkerOrderToDispatch(@RequestParam String workOrderNo) {
		workOrderService.updateWorkerOrderIsDispatchByNo(workOrderNo);
		return JSONArrayUtils.ajaxJsonResponse(true, "");
	}

	/**
	 * 把请求参数放入map对象
	 * 
	 * @param request HTTP请求
	 * @param findParams 放入的map对象
	 * @param query 模糊查询值
	 * @param queryType 模糊查询对象
	 * */
	// private String[] queryParamNames = { "contractNo", "operator",
	// "customerCompany", "custProductType", "productType", "productSpec",
	// "wiresStructure", "numberOfWires", "section", "status" };
	@SuppressWarnings("unchecked")
	private void putQueryQueryParam2Map(HttpServletRequest request, Map<String, Object> findParams, String queryType) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Iterator<String> paramKeys = parameterMap.keySet().iterator();
		paramLoop : while (paramKeys.hasNext()) {
			String paramKey = paramKeys.next();
			String[] param = parameterMap.get(paramKey);
			if (null != param && param.length > 0 && StringUtils.isNotBlank(param[0])) {
				if(paramKey.equals(queryType)){ // 自己的话直接跳过，不把自己的查询条件带到参数中
					continue paramLoop;
				}
				if(paramKey.equals("query")){ // 设置自身查询条件
					findParams.put(queryType, "%" + param[0] + "%");
					continue paramLoop;
				}
				if(paramKey.equals("currentSection")){ // 设置自身查询条件
					findParams.put(paramKey, ProcessSection.valueOf(param[0]).getOrder());
					continue paramLoop;
				}
				if (param.length > 1) {
					findParams.put(paramKey, new ArrayList<String>(Arrays.asList(param)));
				} else {
					findParams.put(paramKey, param[0]);
				}
				
//				if (null != equalsParams) {
//					for (String equalsParam : equalsParams) {
//						if (equalsParam.equals(paramKey)) {
//							findParams.put(paramKey, param[0]);
//							continue paramLoop;
//						}
//					}
//				}
//				findParams.put(paramKey, new ArrayList<String>(Arrays.asList(param)));
			}
		}
//		if (!WebConstants.ROOT_ID.equals(query)) {
//			findParams.put(queryType, "%" + query + "%");
//		} else {
//			if(null == findParams.get(queryType) || StringUtils.isEmpty(findParams.get(queryType).toString()))
//				findParams.put(queryType, null); // 不可省，清空功能
//		}
		findParams.put("orgCode", orderOAService.getOrgCode());
	}

	/**
	 * 获取查询下拉框：合同号
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getcontractNo", method = RequestMethod.GET)
	public List<OrderOA> findContractNo(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "contractNo");
		List<OrderOA> list = handScheduleService.getContractNo(findParams);
		return list;
	}

	/**
	 * 获取查询下拉框：单位
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getcustomerCompan}", method = RequestMethod.GET)
	public List<OrderOA> findCustomerCompany(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "customerCompany");
		List<OrderOA> list = handScheduleService.getCustomerCompany(findParams);
		return list;
	}

	/**
	 * 获取查询下拉框：经办人
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getoperator", method = RequestMethod.GET)
	public List<OrderOA> findOperator(HttpServletRequest request ) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "operator");
		List<OrderOA> list = handScheduleService.getOperator(findParams);
		return list;
	}

	/**
	 * 获取查询下拉框：客户型号
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getcustproductType", method = RequestMethod.GET)
	public List<OrderOA> findCustProductType(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "custProductType");
		List<OrderOA> list = handScheduleService.getCustproductType(findParams);
		return list;
	}

	/**
	 * 获取查询下拉框：产品型号
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getproductType", method = RequestMethod.GET)
	public List<OrderOA> findproductType(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "productType");
		List<OrderOA> list = handScheduleService.getProductType(findParams);
		return list;
	}

	/**
	 * 获取查询下拉框：产品规格
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getproductSpec", method = RequestMethod.GET)
	public List<OrderOA> findProductSpec(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "productSpec");
		List<OrderOA> list = handScheduleService.getProductSpec(findParams);
		return list;
	}

	/**
	 * 获取查询下拉框：线芯结构
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getwiresStructure", method = RequestMethod.GET)
	public List<OrderOA> findWiresStructure(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "wiresStructure");
		List<OrderOA> list = handScheduleService.getWiresStructure(findParams);
		return list;
	}

	/**
	 * 获取查询下拉框：线芯数
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getnumberOfWires", method = RequestMethod.GET)
	public List<OrderOA> findnumberOfWires(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "numberOfWires");
		List<OrderOA> list = handScheduleService.getnumberOfWires(findParams);
		return list;
	}

	/**
	 * 获取查询下拉框：截面
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getSection", method = RequestMethod.GET)
	public List<OrderOA> findSection(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "section");
		List<OrderOA> list = handScheduleService.getSection(findParams);
		return list;
	}

	/**
	 * 获取查询下拉框：状态
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getstatus", method = RequestMethod.GET)
	public List<OrderOA> findStatus(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "status");
		List<OrderOA> list = handScheduleService.getstatus(findParams);
		return list;
	}

	/**
	 * [生产单]获取查询下拉框：合同号
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getWorkOrderContractNo", method = RequestMethod.GET)
	public List<WorkOrder> getWorkOrderContractNo(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "contractNo");
		List<WorkOrder> list = handScheduleService.getWorkOrderContractNo(findParams);
		return list;
	}

	/**
	 * [生产单]获取查询下拉框：单位
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getWorkOrderCustomerCompany", method = RequestMethod.GET)
	public List<WorkOrder> getWorkOrderCustomerCompany(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "customerCompany");
		List<WorkOrder> list = handScheduleService.getWorkOrderCustomerCompany(findParams);
		return list;
	}

	/**
	 * [生产单]获取查询下拉框：经办人
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getWorkOrderOperator", method = RequestMethod.GET)
	public List<WorkOrder> getWorkOrderOperator(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "operator");
		List<WorkOrder> list = handScheduleService.getWorkOrderOperator(findParams);
		return list;
	}

	/**
	 * [生产单]获取查询下拉框：客户型号
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getWorkOrderCustproductType", method = RequestMethod.GET)
	public List<WorkOrder> getWorkOrderCustproductType(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "custProductType");
		List<WorkOrder> list = handScheduleService.getWorkOrderCustproductType(findParams);
		return list;
	}

	/**
	 * [生产单]获取查询下拉框：产品型号
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getWorkOrderProductType", method = RequestMethod.GET)
	public List<WorkOrder> getWorkOrderProductType(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "productType");
		List<WorkOrder> list = handScheduleService.getWorkOrderProductType(findParams);
		return list;
	}

	/**
	 * [生产单]获取查询下拉框：产品规格
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getWorkOrderProductSpec", method = RequestMethod.GET)
	public List<WorkOrder> getWorkOrderProductSpec(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "productSpec");
		List<WorkOrder> list = handScheduleService.getWorkOrderProductSpec(findParams);
		return list;
	}

	/**
	 * [生产单]获取查询下拉框：线芯结构
	 * 
	 * @param request HTTP请求
	 * @param query 模糊查询对象
	 * */
	@ResponseBody
	@RequestMapping(value = "/getWorkOrderWiresStructure", method = RequestMethod.GET)
	public List<WorkOrder> getWorkOrderWiresStructure(HttpServletRequest request) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		this.putQueryQueryParam2Map(request, findParams, "wiresStructure");
		List<WorkOrder> list = handScheduleService.getWorkOrderWiresStructure(findParams);
		return list;
	}

	/**
	 * @Title:       workOrderOperateLog
	 * @Description: TODO(生产单->查看操作日志)
	 * @param:       request 请求
	 * @param:       workOrderNo 生产单号
	 * @return:      List<Map<String,String>>   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/workOrderOperateLog")
	public List<WorkOrderOperateLog> workOrderOperateLog(HttpServletRequest request, @RequestParam String workOrderNo) {
		WorkOrderOperateLog param = new WorkOrderOperateLog();
		param.setWorkOrderNo(workOrderNo);
		param.setOperateType(WorkOrderOperateType.SWITCH);
		List<WorkOrderOperateLog> logArray = workOrderOperateLogService.findByObj(param);
		return logArray;
	}

	// 报工历史记录查询
	@ResponseBody
	@RequestMapping(value = "/getWorkOrderReportHistory")
	public List<Report> getWorkOrderReportHistory(HttpServletRequest request, @RequestParam String workOrderNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("workorderno", workOrderNo);
		List<Report> matList = handScheduleService.getWorkOrderReportHistory(param);
		return matList;
	}

	/**
	 * 获取生产单列表：有级联关系
	 * */
	@ResponseBody
	@RequestMapping(value = "/getSeriesWorkOrder", method = RequestMethod.GET)
	public List<WorkOrder> getSeriesWorkOrderNo(HttpServletRequest request,
			@RequestParam(required = false) String preWorkOrderNo) {
		return workOrderService.getWorkOrderFlowArray(preWorkOrderNo);
	}

	@Resource
	private TaskExecutor taskExecutor;// 线程池
	@Resource
	private PrcvService prcvService;
	@Resource
	private ProductCraftsBzService productCraftsBzService;
	private static List<ErrorBean> errorBeanArray = new ArrayList<ErrorBean>();
	private static final Executor executor = new RenameThreadPoolExecutor(5, 50, 0L, TimeUnit.MILLISECONDS,
			new LinkedBlockingQueue<Runnable>());
	private static String[] codeArray = { "CZ50200420114-10*4" };

	// { "CZ20100760345-5*2.5","CZ20100580239-4*2.5" };

	/**
	 * ------------------------------
	 * 
	 * @throws InterruptedException
	 * */
	@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public JSON test() throws InterruptedException {
		List<String> productCodeArray = Arrays.asList(codeArray);
		CountDownLatch countDownLatch = new CountDownLatch(productCodeArray.size());
		System.out.println("总共需要导入产品数：" + productCodeArray.size());
		int i = 1;
		for (String productNo : productCodeArray) {
			List<Prcv> lists = prcvService.getPrcvByProductNo(productNo);
			if (null == lists || lists.size() == 0 || lists.size() > 1) {
				System.out.println("plm中找不到改产品数据或者生产品对应多个工艺路线！");
				countDownLatch.countDown();
				i++;
				continue;
			}
			Prcv prcv = lists.get(0);
			System.out.println("第" + i + "个加入线程队列-----开始-------------" + prcv.getNo());

			executor.execute(new AsyncDataThread(prcv, countDownLatch, i));
			System.out.println("第" + i + "个加入线程队列-----结束-------------" + prcv.getId());
			i++;
		}

		countDownLatch.await(1L, TimeUnit.HOURS);
		System.out.println("所有任务执行完毕");
		for (ErrorBean errorBean : errorBeanArray) {
			System.out.println(errorBean);
		}
		return JSONArrayUtils.ajaxJsonResponse(true, "");
	}

	/**
	 * ------------------------------
	 * 
	 * @throws InterruptedException
	 * */
	@ResponseBody
	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public JSON test1() throws InterruptedException {

		List<Prcv> prcvList = prcvService.getNoNotExistsInMes(); // 获取PLM变更的工艺信息

		CountDownLatch countDownLatch = new CountDownLatch(prcvList.size());
		System.out.println("总共需要导入工艺数：" + prcvList.size());
		int i = 1;
		for (Prcv prcv : prcvList) {

			System.out.println("第" + i + "个加入线程队列-----开始-------------" + prcv.getId());
			executor.execute(new AsyncDataThread(prcv, countDownLatch, i));
			System.out.println("第" + i + "个加入线程队列-----结束-------------" + prcv.getId());
			i++;
		}

		countDownLatch.await(1L, TimeUnit.HOURS);
		System.out.println("所有任务执行完毕");
		for (ErrorBean errorBean : errorBeanArray) {
			System.out.println(errorBean);
		}
		return JSONArrayUtils.ajaxJsonResponse(true, "");
	}

	/**
	 * ------------------------------
	 * 
	 * @throws InterruptedException
	 * */
	@ResponseBody
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public JSON show() throws InterruptedException {

		return JSONArrayUtils.ajaxJsonResponse(true, errorBeanArray.toString());
	}

	/**
	 * */
	class AsyncDataThread extends Thread {
		private int i = 0;
		private Prcv prcv; // 标准工艺对象
		private CountDownLatch countDownLatch;
		@Resource
		private PrcvService prcvService;

		public AsyncDataThread(Prcv prcv, CountDownLatch countDownLatch, int i) {
			this.i = i;
			this.prcv = prcv;
			this.countDownLatch = countDownLatch;
		}

		public void run() {
			System.out.println("第" + i + "个---ACTION，线程执行开始执行------------------" + prcv.getNo());
			if (this.prcvService == null) {
				this.prcvService = (PrcvService) ContextUtils.getBean(PrcvService.class);
			}

			try {
				this.prcvService.asyncData(prcv);
				// Thread.sleep(1500);

				countDownLatch.countDown();

				System.out.println("第" + i + "个---SUCCESS，线程执行完毕------------------" + prcv.getNo());
			} catch (Exception e) {
				System.out.println("第" + i + "个---FAILED，线程执行完毕------------------" + prcv.getNo());
				String mes = e.toString();
				if (null != mes) {
					mes = mes.replaceAll("/[ ]/g", "");
					mes = mes.replaceAll("/[\r\n]/g", "");
				}
				errorBeanArray.add(new ErrorBean(prcv.getNo(), mes));
				countDownLatch.countDown();
				e.printStackTrace();
			}
		}
	}

	// // 实例化所有工艺
	/**
	 * ------------------------------
	 * 
	 * @throws InterruptedException
	 * */
	@ResponseBody
	@RequestMapping(value = "/shili", method = RequestMethod.GET)
	public JSON shili() throws InterruptedException {
		List<String> productCodeArray = Arrays.asList(codeArray);
		List<ProductCraftsBz> productCraftsBzArray = productCraftsBzService.getAll();
		CountDownLatch countDownLatch = new CountDownLatch(productCraftsBzArray.size());
		int i = 1;
		for (ProductCraftsBz productCraftsBz : productCraftsBzArray) {

			if (!productCodeArray.contains(productCraftsBz.getProductCode())) {
				System.out.println("第" + i + "个不在今天产品范围");
				countDownLatch.countDown();
				i++;
				continue;
			}

			// ProductCraftsBz productCraftsBz =
			// productCraftsBzService.getById(prcv.getId());
			// if (productCraftsBz != null) {
			// System.out.println("第"+i+"个已经存在，continue---------------" +
			// prcv.getId());
			// countDownLatch.countDown();
			// i ++;
			// continue;
			// }

			System.out.println("第" + i + "个加入线程队列-----开始-------------" + productCraftsBz.getId());
			executor.execute(new ShiliDataThread(productCraftsBz, countDownLatch, i));
			System.out.println("第" + i + "个加入线程队列-----结束-------------" + productCraftsBz.getId());
			i++;
		}
		return JSONArrayUtils.ajaxJsonResponse(true, errorBeanArray.toString());
	}

	/**
	 * 该请求处理：显示订单附件
	 * 
	 * @author chenXiang
	 * @param orderItemId
	 * @param contractNo
	 * @return 返回附件列表信息
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "lookUpAttachFile", method = RequestMethod.GET)
	public List<AttachFile> lookUpAttachFile(@RequestParam(required = false) String orderItemId, @RequestParam(required = false) String contractNo, 
			@RequestParam(required = false) String workOrderNo) throws IOException {
		// 1、根据订单id
		if (StringUtils.isNotBlank(orderItemId)) {
			//若workOrderNo = "TECHNIQUENUM"时，则是查看内部附件
			if(StringUtils.equals(workOrderNo, "TECHNIQUENUM")){
				return attachFileService.getTechnique(contractNo);
			}
			return attachFileService.getByOrderItemId(orderItemId);
		} else if(StringUtils.isNotBlank(contractNo)) {
			// 2、根据合同号获取
			String[] contractNoArr = contractNo.split(",");
			List<String> contractNoList = new ArrayList<String>(Arrays.asList(contractNoArr));
			return attachFileService.getByContractNo(contractNoList);
		}else if(StringUtils.isNotBlank(workOrderNo)) {
			// 3、根据生产单号
			return attachFileService.getByWorkOrderNo(workOrderNo);
		}
		return null;
	}

	/**
	 * 该请求处理：打开查看附件
	 * 
	 * @author chenXiang
	 * @param path
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "openAttachFile", method = RequestMethod.GET)
	public void openAttachFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) String id) throws IOException {
		AttachFile attachFile = attachFileService.getById(id);
		String path = "";
		//如果为null，则是内部工艺
		Attachment attachment = attachmentService.getById(id);
		if(attachFile == null){
			path = attachment.getDownloadPath();
		}else{
			path = "e:\\attachfile\\" + attachFile.getUploadTime() + "\\" + attachFile.getRealFileName();
		}
		File file = new File(path);
		if (file.exists()) {
			// 以流的形式下载文件
			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			inputStream.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setCharacterEncoding("utf-8");
			if(attachFile == null){
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(attachment.getFileName().getBytes("GB2312"), "ISO_8859_1"));
			}else{
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(attachFile.getRealFileName().getBytes("GB2312"), "ISO_8859_1"));
			}
			response.addHeader("Content-Length", "" + file.length());
			OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			outputStream.write(buffer);
			outputStream.flush();
			outputStream.close();
		}
	}

	/**
	 * */
	class ShiliDataThread extends Thread {
		private int i = 0;
		private ProductCraftsBz productCraftsBz; // 标准工艺对象
		private CountDownLatch countDownLatch;
		@Resource
		private PrcvService prcvService;

		public ShiliDataThread(ProductCraftsBz productCraftsBz, CountDownLatch countDownLatch, int i) {
			this.i = i;
			this.productCraftsBz = productCraftsBz;
			this.countDownLatch = countDownLatch;
		}

		public void run() {
			System.out.println("第" + i + "个---ACTION，线程执行开始执行------------------" + productCraftsBz.getId());
			if (this.prcvService == null) {
				this.prcvService = (PrcvService) ContextUtils.getBean(PrcvService.class);
			}

			try {
				productCraftsService.instanceCrafts(null, "标准工艺", productCraftsBz, null, true); // 实例化工艺
				// Thread.sleep(1500);

				countDownLatch.countDown();

				System.out.println("第" + i + "个---SUCCESS，线程执行完毕------------------" + productCraftsBz.getId());
			} catch (Exception e) {
				System.out.println("第" + i + "个---FAILED，线程执行完毕------------------" + productCraftsBz.getId());

				String mes = e.toString();
				if (null != mes) {
					mes = mes.replaceAll("/[ ]/g", "");
					mes = mes.replaceAll("/[\r\n]/g", "");
				}
				errorBeanArray.add(new ErrorBean(productCraftsBz.getId(), mes));
				countDownLatch.countDown();
				e.printStackTrace();
			}
		}
	}

	class ErrorBean {
		private String id;
		private String mes;

		public ErrorBean(String id, String mes) {
			this.id = id;
			this.mes = mes;
		}

		@Override
		public String toString() {
			return "{id:" + id + ", mes:" + mes + "}";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getTaskStatueInEquip", method = RequestMethod.POST)
	public List<MesClient> getTaskStatueInEquip(HttpServletRequest request, @RequestParam String workOrderNo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("workOrderNo", workOrderNo);
		return handScheduleService.getTaskStatueInEquip(param);
	}

	@ResponseBody
	@RequestMapping(value = "/getEquipInfo", method = RequestMethod.POST)
	public List<MesClient> getEquipInfo(HttpServletRequest request, @RequestParam String id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("Equip", id);
		return handScheduleService.getEquipInfo(param);
	}


	/**
	 * 保存无工艺的产品并发送邮件
	 * 
	 * @param pro
	 */
	@RequestMapping(value = "/saveNoPrcv")
	@ResponseBody
	public JSONObject saveNoPrcv(@RequestParam String pro, @RequestParam String prcvs, @RequestParam String orderItemIds) {
		JSONObject result = new JSONObject();
		// 判断mes中是否存在工艺
		if (messageService.checkExistPrcv(prcvs, result)) {
			return result;
		}

		// 保存无工艺路线的临时产品
		messageService.saveTemp(orderItemIds, pro);
		// 发送邮件

		// messageService.sendMessagebyMail(pro);

		result.put("message", "邮件发送成功！");
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/clearTemp", method = RequestMethod.POST)
	public JSON clearTemp(@RequestParam String orderItemIds) {
		customerOrderItemService.clearTemp(orderItemIds);
		return JSONArrayUtils.ajaxJsonResponse(true, "");

	}

	@ResponseBody
	@RequestMapping(value = "/insertNewColorData", method = RequestMethod.POST)
	public void insertNewColorData(HttpServletRequest request, @RequestParam String id, @RequestParam String color) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		param.put("color", color);
		handScheduleService.insertNewColorData(param);
	}

	@RequestMapping(value = "tempSearch/{isTempSave}", method = RequestMethod.GET)
	@ResponseBody
	public TableView listTemp(HttpServletRequest request, @RequestParam(required = false) Integer start,
			@RequestParam(required = false) Integer limit, @RequestParam(required = false) String sort,
			@PathVariable String isTempSave) {
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("isTempSave", isTempSave);
		// 将查询条件放入查询对象
		this.putQueryParam2Map(request, findParams, null, new String[] { "createDate", "isYunMu" }, start,
				(start + limit), sortArray);
		List<Map<String, String>> list = customerOrderItemService.getHandScheduleOrderTemp(findParams, start, limit);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(customerOrderItemService.countHandScheduleOrderTemp(findParams));
		return tableView;
	}

	@RequestMapping(value = "tempSave", method = RequestMethod.GET)
	@ResponseBody
	public void tempSave(@RequestParam String orderItemIds) {
		customerOrderItemService.tempSave(orderItemIds);
	}
	
	@ResponseBody
	@RequestMapping(value = "importMySelf")
	public void updateFinishedJY(HttpServletRequest request) {
		//获取2016.1.1后的未实例化的订单
		List<CustomerOrderItem> thisList = customerOrderItemDAO.getItems();	
		for(int i = 0;i<thisList.size();i++){
			salesOrderItemService.dataSeparationFunction(thisList.get(i).getCraftsId(), thisList.get(i).getSalesOrderItemId());
		}
		//插入单条记录
		//salesOrderItemService.dataSeparationFunction("", "");
	}
	
	
	//成品现货
	@RequestMapping(value = "importFinishedProduct",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject importFinishedProduct(HttpServletRequest request,@RequestParam MultipartFile importFile) throws IOException{
		org.apache.poi.ss.usermodel.Workbook workbook = null;
		try {
            workbook = new XSSFWorkbook(importFile.getInputStream());
        } catch (Exception e) {
            workbook = new HSSFWorkbook(importFile.getInputStream());
        }
		Sheet sheet = workbook.getSheetAt(0);
        JSONObject result = new JSONObject();
        if(sheet == null){
        	result.put("message", "导入文件sheet，请命名为'成品现货'");
            result.put("success", false);
        }else{
        	handScheduleService.importFinishedProduct(sheet,result);
        }
		return result;
	}
	
	@RequestMapping(value = "listFinishedProduct",method = RequestMethod.GET)
	@ResponseBody
	public TableView listFinishedProduct(HttpServletRequest request, @RequestParam(required = false) Integer start,
			@RequestParam(required = false) Integer limit, @RequestParam(required = false) String sort,@RequestParam(required = false) String model,
			@RequestParam(required = false) String spec){
		FinishedProduct findParams = new FinishedProduct();
		if(model != "" && model != null){
			model = "%" + model + "%";
			findParams.setModel(model);
		}
		if(spec != "" && spec != null){
			spec = "%" + spec + "%";
			findParams.setSpec(spec);
		}
		//只需要查询成品现货再使用中的数据
		findParams.setStatus("2");
		TableView tableView = new TableView();
		List<FinishedProduct> list = handScheduleService.listFinishedProduct(start,limit,findParams);
		tableView.setRows(list);
		tableView.setTotal(handScheduleService.countFinishedProduct(findParams));
		return tableView;
	}
	
	@RequestMapping(value = "getAllModelORSpec",method = RequestMethod.GET)
	@ResponseBody
	public List<FinishedProduct> getAllModelORSpec(@RequestParam String type,@RequestParam(required=false) String query){
		return handScheduleService.getAllModelORSpec(type,query);
	}
	
	@RequestMapping(value = "updateFinishedProduct",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateFinishedProduct(@RequestParam(required=false) String id,@RequestParam(required=false) String salesOrderItemId,@RequestParam(required=false) String uselength){
		JSONObject result = new JSONObject();
		handScheduleService.updateFinishedProduct(id,uselength,salesOrderItemId,result);
		return result;
	}
	
	@RequestMapping(value = "importTechnique",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject importTechnique(HttpServletRequest request,@RequestParam String contractNo,@RequestParam MultipartFile importFile) throws IOException{
		JSONObject result = new JSONObject();
		attachmentService.upload(importFile, InterfaceDataType.TECHNIQUE, "合同", contractNo);
		result.put("success", true);
		result.put("message", "导入成功!");
		return result;
	}
	@RequestMapping(value = "getFinishedProductById",method = RequestMethod.GET)
	@ResponseBody
	public List<FinishedProduct> getFinishedProductById(HttpServletRequest request,@RequestParam String salesOrderItemId){
		List<FinishedProduct> list = handScheduleService.getFinishedProductById(salesOrderItemId);
		return list;
	}

}
