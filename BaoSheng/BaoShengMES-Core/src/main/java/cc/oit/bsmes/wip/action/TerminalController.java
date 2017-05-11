package cc.oit.bsmes.wip.action;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cc.oit.bsmes.bas.model.Attachment;
import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.model.WorkShift;
import cc.oit.bsmes.bas.service.AttachmentService;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.bas.service.MesClientManEqipService;
import cc.oit.bsmes.bas.service.MesClientService;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.bas.service.WorkShiftService;
import cc.oit.bsmes.common.constants.DebugType;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.MaintainStatus;
import cc.oit.bsmes.common.constants.MaintainTemplateType;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.constants.SectionType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.cookie.CookieMachineResolver;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.DayOfWeekUtils;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.model.EventProcessLog;
import cc.oit.bsmes.eve.model.EventType;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.eve.service.EventProcessLogService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.MaintainRecord;
import cc.oit.bsmes.fac.model.MaintainRecordItem;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.MaintainRecordItemService;
import cc.oit.bsmes.fac.service.MaintainRecordService;
import cc.oit.bsmes.inv.model.AssistOption;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.inv.model.Location;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.inv.service.LocationService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.opc.client.OpcClient;
import cc.oit.bsmes.opc.client.OpcParmVO;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.model.BorrowMat;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.MaterialMng;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.model.SupplementMaterial;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.HandScheduleService;
import cc.oit.bsmes.pla.service.MaterialMngService;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProcessQcValue;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProcessInformationService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessQcValueService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;
import cc.oit.bsmes.wip.dto.MesClientEqipInfo;
import cc.oit.bsmes.wip.dto.MethodReturnDto;
import cc.oit.bsmes.wip.model.ApplyMat;
import cc.oit.bsmes.wip.model.Debug;
import cc.oit.bsmes.wip.model.OnoffRecord;
import cc.oit.bsmes.wip.model.RealCost;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.Section;
import cc.oit.bsmes.wip.model.TurnOverReport;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ApplyMatService;
import cc.oit.bsmes.wip.service.DebugService;
import cc.oit.bsmes.wip.service.MatUsageService;
import cc.oit.bsmes.wip.service.OnoffRecordService;
import cc.oit.bsmes.wip.service.RealCostService;
import cc.oit.bsmes.wip.service.ReceiptService;
import cc.oit.bsmes.wip.service.ReportService;
import cc.oit.bsmes.wip.service.SectionService;
import cc.oit.bsmes.wip.service.TerminalService;
import cc.oit.bsmes.wip.service.TurnOverReportService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;

@Controller
@RequestMapping("/wip/terminal")
public class TerminalController {
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private DebugService debugService;
	@Resource
	private ProcessReceiptService processReceiptService;
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private MesClientManEqipService mesClientManEqipService;
	@Resource
	private OnoffRecordService onoffRecordService;
	@Resource
	private MaterialRequirementPlanService materialRequirementPlanService;
	@Resource
	private ReceiptService receiptService;
	@Resource
	private SectionService sectionService;
	@Resource
	private WorkShiftService workShiftService;
	@Resource
	private ReportService reportService;
	@Resource
	private ProcessQcValueService processQcValueService;
	@Resource
	private ProcessInOutService processInOutService;
	@Resource
	private RealCostService realCostService;
	@Resource
	private ProcessInformationService processInformationService;
	@Resource
	private UserService userService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private SalesOrderService salesOrderService;
	@Resource
	private TerminalService terminalService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private MaintainRecordService maintainRecordService;
	@Resource
	private MaintainRecordItemService maintainRecordItemService;
	@Resource
	private MatService matService;
	@Resource
	private MesClientService mesClientService;
	@Resource
	private LocationService locationService;
	@Resource
	private EmployeeService employeeService;
	@Resource
	private EquipListService equipListService;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private HandScheduleService handScheduleService;
	@Resource
	private MaterialMngService materialMngService;
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	private TurnOverReportService turnOverReportService;
	@Resource
	private MatUsageService matUsageService;
	@Resource
	private EventProcessLogService eventProcessLogService;
	@Resource
	private ApplyMatService applyMatService;//opcClient
	
	@Resource
	private OpcClient opcClient;
	
	private static Map<String,String> BZSemiPropParam=new HashMap<String,String>();
	private static Map<String,String> BZMaterialPBPropParam=new HashMap<String,String>();
	private static Map<String,String> BZMaterialRBPropParam=new HashMap<String,String>();
	private static Map<String,String> BZQcPropParam=new HashMap<String,String>();
	private static Map<String,String> RBMaterialDCParam=new HashMap<String,String>();
	private static Map<String,String> RBMaterialDTParam=new HashMap<String,String>();
	private static Map<String,String> RBSemiInParam=new HashMap<String,String>();
	private static Map<String,String> RBQCParam=new HashMap<String,String>();
	private static Map<String,String> JYMaterialSMParam=new HashMap<String,String>();
	private static Map<String,String> JYMaterialDTParam=new HashMap<String,String>();
	private static Map<String,String> JYSemiInParam=new HashMap<String,String>();
	private static Map<String,String> JYQcParam=new HashMap<String,String>();
	private static Map<String,String> JYMaterialJYParam=new HashMap<String,String>();
	private static Map<String,String> JYSemiOutParam =new HashMap<String,String>();
	
	private static Map<String,String> HTMaterialSMParam=new HashMap<String,String>();
	private static Map<String,String> HTMaterialDTParam=new HashMap<String,String>();
	private static Map<String,String> HTSemiInParam=new HashMap<String,String>();
	private static Map<String,String> HTQcParam=new HashMap<String,String>();
	private static Map<String,String> HTMaterialJYParam=new HashMap<String,String>();
	private static Map<String,String> HTSemiOutParam =new HashMap<String,String>();
	
	public void init(){
		//编织系列参数
		BZSemiPropParam.put("最小外径", "minDia");
		BZSemiPropParam.put("最大外径", "maxDia");
		BZSemiPropParam.put("圆整度", "roundness");
		BZMaterialPBPropParam.put("单丝", "matDia");
		BZMaterialPBPropParam.put("材料", "material");
		BZQcPropParam.put("锭数", "spindle");
		BZQcPropParam.put("编织节距", "bzStep");
		BZQcPropParam.put("编织角", "bzAngle");
		BZQcPropParam.put("编织密度", "bzDensity");
		BZQcPropParam.put("编织后外径", "afterBzDia");
		BZQcPropParam.put("成品圆整度", "afterBzroundness");
		BZQcPropParam.put("外观", "surfaceQuality");
		BZQcPropParam.put("单丝直径", "singleDia");
		BZQcPropParam.put("单丝根数", "wireCount");
		BZQcPropParam.put("屏蔽材料", "PBMaterial");
		// 绕包系列参数
		RBMaterialDCParam.put("宽度", "width");
		RBMaterialDCParam.put("厚度", "thickness");
		RBMaterialDCParam.put("颜色", "color");
		RBMaterialDCParam.put("材质", "material");
		RBMaterialDTParam.put("单丝标准直径", "singleDia");
		RBMaterialDTParam.put("标准直径", "standardDia");
		RBMaterialDTParam.put("直流电阻", "directResistance");
		RBSemiInParam.put("标准外径", "standardDia");
		RBSemiInParam.put("标准重量", "standardWeight");
		RBSemiInParam.put("外径", "afterDia");
		RBQCParam.put("绕包方向", "RBDirect");	
		RBQCParam.put("导体单丝直径", "singleDia");	
		RBQCParam.put("绕包后外径", "RBAfterDia");	
		RBQCParam.put("包带搭盖率", "coverRate");
		//绝缘系列参数
		JYMaterialSMParam.put("标准色泽", "color");
		JYMaterialJYParam.put("颜色", "color");
		JYMaterialDTParam.put("单丝标准直径", "singleDia");
		JYMaterialDTParam.put("标准直径", "standardDia");
		JYSemiInParam.put("搭盖", "coverRate");
		JYSemiInParam.put("标准外径", "standardDia");
		JYQcParam.put("绝缘最小厚度", "minThickness");
		JYQcParam.put("绝缘指导厚度", "standardThickness");
		JYQcParam.put("绝缘标称外径", "standardDia");
		JYQcParam.put("同心度", "eccentricity");
		JYQcParam.put("导体单丝直径", "singleDia");
		JYSemiOutParam.put("字码/颜色", "color");
		//护套系列参数
		HTMaterialSMParam.put("标准色泽", "color");
		HTMaterialJYParam.put("颜色", "color");
		HTMaterialDTParam.put("单丝标准直径", "singleDia");
		HTMaterialDTParam.put("标准直径", "standardDia");
		HTSemiInParam.put("成品电线标准外径", "standardDia");
		HTQcParam.put("最小厚度", "minThickness");	
		HTQcParam.put("指导厚度", "standardThickness");
		HTQcParam.put("护套后外径", "standardDia");
		HTQcParam.put("护套圆整度", "eccentricity");
		HTSemiOutParam.put("护套颜色", "color");
		
	}

	@RequestMapping(produces = "text/html")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "terminal");

		CookieMachineResolver cookieMachineResolver = new CookieMachineResolver();
		String mac = cookieMachineResolver.getMac(request, response);
		String ip = cookieMachineResolver.getIp(request, response);
		model.addAttribute("middleCheckInterval", mesClientManEqipService.getMiddleCheckInterval());
		List<MesClientEqipInfo> eqipInfos = mesClientManEqipService.getByMesClientMac(mac, ip, true);
		model.addAttribute("eqips", eqipInfos);
		model.addAttribute("maxClient", eqipInfos.size());
		model.addAttribute("workUsers", onoffRecordService.assOnWorkEmployeeNames(mac));
		model.addAttribute("equipStatusArray", JSONArrayUtils.enumToJSON(EquipStatus.class));
		return "wip.terminal.multiple";
	}

	/**
	 * 员工刷卡
	 * 
	 * @param request
	 * @param response
	 * @param jsonText
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "employeeCreditCard", method = RequestMethod.GET)
	public ModelAndView employeeCreditCard(HttpServletRequest request, HttpServletResponse response, @RequestParam String jsonText) {
		CookieMachineResolver cookieMachineResolver = new CookieMachineResolver();
		String mac = cookieMachineResolver.getMac(request, response);
		Map map = new HashMap();
		OnoffRecord newRecord = JSON.parseObject(jsonText, OnoffRecord.class);
		String equipCodes = newRecord.getEquipCodes();
		String userCode = newRecord.getUserCode();
		String exceptionType = newRecord.getExceptionType();
		String usedEquipCode = "";
		String usedUserName = "";
		if (StringUtils.isNotBlank(equipCodes) && equipCodes.contains("[")) {
			equipCodes = equipCodes.replace("[", "").replace("]", "").replace("\"", "");
			newRecord.setEquipCodes(equipCodes);
		}
		//判断该用户账号是否为挡班，若是挡班，则检查该挡班登录的设备是否被其他挡班使用
		String certificate = onoffRecordService.checkIfDB(userCode);
		if("DB".equals(certificate) && "ON_WORK".equals(exceptionType)){
			String[] equipCodeList = equipCodes.split(",");
			for(String e : equipCodeList){
				String a = onoffRecordService.checkIfUsed(e);
				if(a != null && !"".equals(a)){
					usedEquipCode = e;
					usedUserName = a;
					break;
				}
			}
		}
		User user = userService.checkUserCodeUnique(userCode);
		String status = user.getStatus();
		ModelAndView modelAndView = new ModelAndView(new FastJsonJsonView());
		modelAndView.addObject("status", status);
		if("".equals(usedEquipCode)){
			modelAndView.addObject("result", onoffRecordService.employeeCreditCard(mac, newRecord, user,map));
		} else {
			modelAndView.addObject("usedEquipCode", usedEquipCode);
			modelAndView.addObject("usedUserName", usedUserName);
		}
		modelAndView.addObject("map", map);
		return modelAndView;
	}

	/**
	 * @Title: showhtml
	 * @Description: TODO(单元操作)
	 * @param: @param equipCode
	 * @param: @param woNo
	 * @param: @param operator
	 * @param: @return
	 * @return: String
	 * @throws
	 */
	@RequestMapping(value = "unitOperation", produces = "text/html")
	public String unitOperation(Map<String, Object> model, @RequestParam(required = false) String contractNo,
			@RequestParam(required = false) String taskId, @RequestParam(required = false) String productType,
			@RequestParam(required = false) String productSpec, @RequestParam(required = false) String taskLength,
			@RequestParam(required = false) String equipCode) {
		// 编织投入半成品
		Map<String, String> BZSemiInParm = new HashMap<String, String>();
		// 屏蔽材料
		Map<String, String> BZMaterialPB = new HashMap<String, String>();
		// 绕包材料
		Map<String, String> BZMaterialRB = new HashMap<String, String>();
		// QC属性
		Map<String, String> BZQcParam = new HashMap<String, String>();
		init();
		// 根据taskId获取该工序下的所有属性
		List<Map<String, Object>> BZQcProps = processQcService.getQcInfoByTaskId(taskId);
		terminalService.fillPropInMap(BZQcProps, BZQcPropParam, BZQcParam);
		// 根据taskId获取输入半成品的所有属性
		List<Map<String, Object>> BZSemiProductProps = terminalService.getBZSemiProducts(taskId);
		// 根据taskId获取输出半成品名
		List<Map<String, Object>> BZSemiOutProductProps = terminalService.getBZOutSemiProducts(taskId);
		terminalService.fillPropInMap(BZSemiProductProps, BZSemiPropParam, BZSemiInParm);
		BZSemiInParm.put("matCode", !CollectionUtils.isEmpty(BZSemiProductProps) ? BZSemiProductProps.get(0).get("MAT_CODE")
				.toString() : "");
		// 根据taskId获取该工序下的输入原材料
		List<Mat> mats = matService.getMatsByOrderTask(taskId);
		int countRB = 0;
		for (Mat mat : mats) {
			if (mat.getTempletName().equals("带材")) {
				List<Map<String, Object>> BZMaterialRBProps = terminalService.getBZMaterialProps(mat.getMatCode());
				BZMaterialRB.put("quantity", mat.getQuantity());
				BZMaterialRB.put("matCode", mat.getMatCode());
				terminalService.fillPropInMap(BZMaterialRBProps, BZMaterialRBPropParam, BZMaterialRB);
				countRB++;
			} else {
				List<Map<String, Object>> BZMaterialPBProps = terminalService.getBZMaterialProps(mat.getMatCode());
				BZMaterialPB.put("quantity", mat.getQuantity());
				BZMaterialPB.put("matCode", mat.getMatCode());
				terminalService.fillPropInMap(BZMaterialPBProps, BZMaterialPBPropParam, BZMaterialPB);
			}
		}
		EquipInfo equipInfo = equipInfoService.getByCode(equipCode, "bstl01");
		if (countRB > 0) {
			model.put("countRB", countRB);
		}
		model.put("length", taskLength);
		model.put("BZSemiParm", BZSemiInParm);
		model.put("productSpec", productSpec);
		model.put("BZMaterialPB", BZMaterialPB);
		model.put("BZMaterialRB", BZMaterialRB);
		model.put("equipInfo", equipInfo);
		model.put("BZQcParam", BZQcParam);
		model.put("BZSemiOutProductProps", !CollectionUtils.isEmpty(BZSemiProductProps) ? BZSemiOutProductProps.get(0).get("MAT_CODE")
				.toString() : "");
		return "html_wip.terminal.unitOperation";
	}

	@RequestMapping(value = "unitOperationJY", produces = "text/html")
	public String unitOperationJY(Map<String,Object> model,@RequestParam(required=false) String contractNo,
			@RequestParam(required=false) String taskId,@RequestParam(required=false) String productType,
			@RequestParam(required=false)String productSpec,
			@RequestParam(required=false)String taskLength,@RequestParam(required=false)String equipCode,
			@RequestParam(required=false)String custOrderItemId) {
		//导体物料
		Map<String,String> JYMaterialDT=new HashMap<String,String>();
		//绝缘料
		List<Map<String,String>> JYMaterialJYS=new ArrayList<Map<String,String>>();
		//色母料
		List<Map<String,String>> JYMaterialSMS=new ArrayList<Map<String,String>>();
		//输入半成品
		Map<String,String> JYSemiIn=new HashMap<String,String>();
		//输出半成品
		Map<String,String> JYSemiOut=new HashMap<String,String>();
		//QC属性
		Map<String,String> JYQc=new HashMap<String,String>();
		//根据custItemId得到工序下的输入原材料
		List<Mat> mats=matService.getMatsByCustItemId(custOrderItemId);
		//初始化参数
		init();
		for (Mat mat : mats) {
			if (mat.getTempletName().equals("色母料")) {
				Map<String, String> JYMaterialSM = new HashMap<String, String>();
				List<Map<String, Object>> JYMaterialSMProps = terminalService.getBZMaterialProps(mat.getMatCode());
				terminalService.fillPropInMap(JYMaterialSMProps, JYMaterialSMParam, JYMaterialSM);
				JYMaterialSM.put("id", mat.getMatCode());
				JYMaterialSMS.add(JYMaterialSM);
			} else if (mat.getTempletName().equals("绝缘护套")) {
				Map<String, String> JYMaterialJY = new HashMap<String, String>();
				List<Map<String, Object>> JYMaterialJYProps = terminalService.getBZMaterialProps(mat.getMatCode());
				terminalService.fillPropInMap(JYMaterialJYProps, JYMaterialJYParam, JYMaterialJY);
				JYMaterialJY.put("name", mat.getMatName());
				JYMaterialJY.put("id", mat.getMatCode());
				JYMaterialJY.put("quantity", mat.getQuantity());
				JYMaterialJYS.add(JYMaterialJY);
			} else {
				List<Map<String, Object>> JYMaterialDTProps = terminalService.getBZMaterialProps(mat.getMatCode());
				terminalService.fillPropInMap(JYMaterialDTProps, JYMaterialDTParam, JYMaterialDT);
				JYMaterialDT.put("id", mat.getMatCode());
				JYMaterialDT.put("name", mat.getMatName());
				JYMaterialDT.put("quantity", mat.getQuantity());
			}
		
		}		
		//判断该产品的第一道工序是否为绝缘挤出
		List<Map<String,String>> processCodes=terminalService.isJYProcess(custOrderItemId);
		if(processCodes!=null&&processCodes.size()>0){		
			List<Map<String,Object>> JYSemiInProps=terminalService.getBZSemiProducts(taskId);
			terminalService.fillPropInMap(JYSemiInProps, JYSemiInParam, JYSemiIn);
			String ymdProcessId = processCodes.get(0).get("ID");
			// 得到云母带绕包的包带属性
			List<Map<String, String>> JYRBMatProps = terminalService.getRBMatPropsByProcessId(ymdProcessId);
			JYSemiIn.put("id", JYSemiInProps.get(0).get("MAT_CODE").toString());
			JYSemiIn.put("material", JYRBMatProps.get(0).get("PROP_TARGET_VALUE"));

		}
		List<Map<String, Object>> JYQcProps = processQcService.getQcInfoByTaskId(taskId);
		terminalService.fillPropInMap(JYQcProps, JYQcParam, JYQc);
		// 获取输出半成品的属性
		List<Map<String, Object>> JYSemiOutProps = terminalService.getBZOutSemiProducts(taskId);
		terminalService.fillPropInMap(JYSemiOutProps, JYSemiOutParam, JYSemiOut);
		JYSemiOut.put("id", JYSemiOutProps.get(0).get("MAT_CODE").toString());
		//根据custItemid获取输出半成品的颜色
		String colors=terminalService.getSemiOutColors(custOrderItemId).get("COLORS");
		//根据taskId得到设备相应信息
		EquipInfo equipInfo=equipInfoService.getByCode(equipCode, "bstl01");
		model.put("equipInfo", equipInfo);
		model.put("length", taskLength);
		model.put("productSpec", productSpec);
		model.put("JYMaterialSMS", JYMaterialSMS);
		model.put("JYMaterialJYS", JYMaterialJYS);
		model.put("JYMaterialDT", JYMaterialDT);
		model.put("JYQc", JYQc);
		model.put("JYSemiIn", JYSemiIn);
		model.put("JYSemiOut", JYSemiOut);
		model.put("colors", colors);
		return "html_wip.terminal.unitOperationJY";
	}

	@RequestMapping(value = "unitOperationRB", produces = "text/html")
	public String unitOperationRB(Map<String, Object> model, @RequestParam(required = false) String contractNo,
			@RequestParam(required = false) String taskId, @RequestParam(required = false) String productType,
			@RequestParam(required = false) String productSpec, @RequestParam(required = false) String taskLength,
			@RequestParam(required = false) String equipCode, @RequestParam(required = false) String processCode) {
		// 绕包投入半成品
		Map<String, String> RBSemiIn = new HashMap<String, String>();
		// 绕包原材料(导体)
		Map<String, String> RBMaterialDT = new HashMap<String, String>();
		// 绕包原材料(带材)
		List<Map<String, String>> RBMaterialDCLists = new ArrayList<Map<String, String>>();
		// QC属性
		Map<String, String> RBQc = new HashMap<String, String>();
		// 为原材料计数
		Map<String, Integer> countNumMap = new HashMap<String, Integer>();
		int rbCount = 0;
		int dtCount = 0;
		init();
		// 根据taskId获取该工序下的输入原材料
		List<Mat> mats = matService.getMatsByOrderTask(taskId);
		for (Mat mat : mats) {
			if (mat.getTempletName().equals("带材")) {
				Map<String, String> RBMaterialDC = new HashMap<String, String>();
				rbCount++;
				List<Map<String, Object>> BRMaterialProps = terminalService.getBZMaterialProps(mat.getMatCode());
				terminalService.fillPropInMap(BRMaterialProps, RBMaterialDCParam, RBMaterialDC);
				RBMaterialDC.put("id", mat.getMatCode());
				RBMaterialDC.put("quantity", mat.getQuantity());
				RBMaterialDCLists.add(RBMaterialDC);
				countNumMap.put("rbCount", rbCount);
				model.put("RBMaterialDCLists", RBMaterialDCLists);
			} else {
				dtCount++;
				List<Map<String, Object>> BRMaterialProps = terminalService.getBZMaterialProps(mat.getMatCode());
				terminalService.fillPropInMap(BRMaterialProps, RBMaterialDTParam, RBMaterialDT);
				RBMaterialDT.put("id", mat.getMatCode());
				RBMaterialDT.put("quantity", mat.getQuantity());
				RBMaterialDT.put("name", mat.getMatName());
				countNumMap.put("dtCount", dtCount);
				model.put("RBMaterialDT", RBMaterialDT);
			}
		}
		// 根据taskId获取该工序下的输入半成品属性
		if (processCode.equals("wrapping")) {
			List<Map<String, Object>> RBSemiInParmProps = terminalService.getBZSemiProducts(taskId);
			terminalService.fillPropInMap(RBSemiInParmProps, RBSemiInParam, RBSemiIn);
			RBSemiIn.put("id", RBSemiInParmProps.get(0).get("MAT_CODE").toString());
			model.put("RBSemiIn", RBSemiIn);
		}
		EquipInfo equipInfo = equipInfoService.getByCode(equipCode, "bstl01");
		List<Map<String, Object>> RBQcProps = processQcService.getQcInfoByTaskId(taskId);
		terminalService.fillPropInMap(RBQcProps, RBQCParam, RBQc);
		model.put("equipInfo", equipInfo);
		model.put("length", taskLength);
		model.put("productSpec", productSpec);
		model.put("RBQc", RBQc);
		model.put("processCode", processCode);
		model.put("countNumMap", countNumMap);
		model.put("countNumMap", countNumMap);
		return "html_wip.terminal.unitOperationRB";
	}

	@RequestMapping(value = "unitOperationHT", produces = "text/html")
	public String unitOperationHT(Map<String,Object> model,@RequestParam(required=false) String contractNo,
			@RequestParam(required=false) String taskId,@RequestParam(required=false) String productType,
			@RequestParam(required=false)String productSpec,
			@RequestParam(required=false)String taskLength,@RequestParam(required=false)String equipCode) {
		//导体物料
		Map<String,String> HTMaterialDT=new HashMap<String,String>();
		//绝缘料
		List<Map<String,String>> HTMaterialJYS=new ArrayList<Map<String,String>>();
		//色母料
		List<Map<String,String>> HTMaterialSMS=new ArrayList<Map<String,String>>();
		//输入半成品
		Map<String,String> HTSemiIn=new HashMap<String,String>();
		//输出半成品
		Map<String,String> HTSemiOut=new HashMap<String,String>();
		//QC属性
		Map<String,String> HTQc=new HashMap<String,String>();
		//根据taskId得到工序下的输入原材料
		List<Mat> mats=matService.getMatsByOrderTask(taskId);
		//初始化参数
		init();
		for(Mat mat:mats){
			if(mat.getTempletName().equals("色母料")){
				Map<String,String> HTMaterialSM=new HashMap<String,String>();
				List<Map<String,Object>> HTMaterialSMProps=terminalService.getBZMaterialProps(mat.getMatCode());
				terminalService.fillPropInMap(HTMaterialSMProps, HTMaterialSMParam, HTMaterialSM);
				HTMaterialSM.put("id", mat.getMatCode());
				HTMaterialSMS.add(HTMaterialSM);
			}else if(mat.getTempletName().equals("绝缘护套")){
				Map<String,String> HTMaterialHT=new HashMap<String,String>();
				List<Map<String,Object>> HTMaterialHTProps=terminalService.getBZMaterialProps(mat.getMatCode());
				terminalService.fillPropInMap(HTMaterialHTProps, HTMaterialJYParam, HTMaterialHT);
				HTMaterialHT.put("name", mat.getMatName());
				HTMaterialHT.put("id", mat.getMatCode());
				HTMaterialHT.put("quantity", mat.getQuantity());
				HTMaterialJYS.add(HTMaterialHT);
			}else{
				List<Map<String,Object>> HTMaterialDTProps=terminalService.getBZMaterialProps(mat.getMatCode());
				terminalService.fillPropInMap(HTMaterialDTProps, HTMaterialDTParam, HTMaterialDT);
				HTMaterialDT.put("id", mat.getMatCode());	
				HTMaterialDT.put("name", mat.getMatName());
				HTMaterialDT.put("quantity", mat.getQuantity());
			}
		
		}		
		List<Map<String,Object>> HTQcProps=processQcService.getQcInfoByTaskId(taskId);
		terminalService.fillPropInMap(HTQcProps, HTQcParam, HTQc);
		//获取输入半成品属性
		List<Map<String,Object>> HTSemiInprops=terminalService.getBZSemiProducts(taskId);
		terminalService.fillPropInMap(HTSemiInprops, HTSemiInParam, HTSemiIn);
		HTSemiIn.put("id", HTSemiInprops.get(0).get("MAT_CODE").toString());
		//获取输出半成品的属性
		List<Map<String,Object>> HTSemiOutProps=terminalService.getBZOutSemiProducts(taskId);
		terminalService.fillPropInMap(HTSemiOutProps, HTSemiOutParam, HTSemiOut);
		HTSemiOut.put("id", HTSemiOutProps.get(0).get("MAT_CODE").toString());
		//根据taskId得到设备相应信息
		EquipInfo equipInfo=equipInfoService.getByCode(equipCode, "bstl01");
		model.put("equipInfo", equipInfo);
		model.put("length", taskLength);
		model.put("productSpec", productSpec);
		model.put("HTMaterialSMS", HTMaterialSMS);
		model.put("HTMaterialJYS", HTMaterialJYS);
		model.put("HTMaterialDT", HTMaterialDT);
		model.put("HTQc", HTQc);
		model.put("HTSemiIn", HTSemiIn);
		model.put("HTSemiOut", HTSemiOut);
		return "html_wip.terminal.unitOperationHT";
	}

	/**
	 * 接受任务
	 * 
	 * @param equipCode
	 * @param woNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "acceptTask", method = RequestMethod.POST)
	public JSONObject acceptTask(@RequestParam String equipCode, @RequestParam String woNo, @RequestParam String operator) {
		JSONObject result = new JSONObject();
		result.put("taskInfoList", customerOrderItemService.showWorkOrderDetailCommon(woNo));
		result.put("mesClientAcceptTask", equipInfoService.mesClientAcceptTask(equipCode, woNo, operator));
		return result;
	}

	/**
	 * 获取已报工数据集
	 * 
	 * @param woNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "report/{woNo}", method = RequestMethod.GET)
	public List<Section> toReport(@PathVariable String woNo) {
		List<Section> toReport = sectionService.getByWoNo(woNo);
		return toReport;
	}

	@ResponseBody
	@RequestMapping(value = "report/{equipCode}", method = RequestMethod.PUT, consumes = "application/json")
	public void report(@PathVariable String equipCode, @RequestBody String jsonText) {
		List<Section> sectionsToReport = null;
		if (jsonText.startsWith("{")) {
			sectionsToReport = new ArrayList<Section>();
			sectionsToReport.add(JSON.parseObject(jsonText, Section.class));
		} else {
			sectionsToReport = JSON.parseArray(jsonText, Section.class);
		}
		reportService.report(equipCode, sectionsToReport);
	}

	/**
	 * @Title report
	 * @Description TODO(终端报工提交事件)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年3月17日 下午4:26:07
	 * @param @param workOrderNo 生产单号
	 * @param @param reportLength 报工长度
	 * @param @param operator 操作人
	 * @param @param locationName 库位
	 * @param @param equipCode 设备编码
	 * @return JSONObject
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "report", method = RequestMethod.POST)
	public JSONObject report(@RequestParam String workOrderNo, @RequestParam Double reportLength, @RequestParam(required = false) String operator,
			@RequestParam(required = false) String locationName, @RequestParam String equipCode, @RequestParam(required = false) String[] reprotUser,
			@RequestParam(required = false) String disk,@RequestParam(required = false) Integer diskNumber) {
		JSONObject jsonObject = null;
		try {
			jsonObject = reportService.report(workOrderNo, reportLength, operator, locationName, equipCode, reprotUser,disk,diskNumber);
			jsonObject.put("taskInfoList", customerOrderItemService.showWorkOrderDetailCommon(workOrderNo));
		} catch (Exception e) {
			jsonObject = new JSONObject();
			jsonObject.put("success", false);
			jsonObject.put("message", e.getMessage());
			e.printStackTrace();
		}
		return jsonObject;
	}

	@RequestMapping(value = "/{equipCode}", produces = "text/html", method = RequestMethod.GET)
	public String main(HttpServletRequest request, HttpServletResponse response, @PathVariable String equipCode, Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "terminal");
		EquipInfo equipInfo = equipInfoService.getByCode(equipCode, SessionUtils.getUser().getOrgCode());
		model.addAttribute("middleCheckInterval", mesClientManEqipService.getMiddleCheckInterval());
		model.addAttribute("equip", equipInfo);
		WorkOrder workOrder = workOrderService.getCurrentByEquipCode(equipCode);
		if (workOrder == null) {
			return "redirect:/wip/terminal.action";
		}

		if (StringUtils.equals(workOrder.getStatus().name(), WorkOrderStatus.TO_DO.name())
				&& StringUtils.isNotBlank(workOrder.getProcessGroupRespool())) {
			WorkOrder lastWorkOrder = workOrderService.getByWorkOrderNO(workOrder.getProcessGroupRespool());
			model.addAttribute("lastWorkOrderStatus", lastWorkOrder.getStatus().name());
		}

		String preWorkOrderNo = workOrder.getProcessGroupRespool();
		String workOrderNo = workOrder.getWorkOrderNo();
		if (StringUtils.isNotBlank(preWorkOrderNo)) {
			WorkOrder preWorkOrder = workOrderService.getByWorkOrderNO(preWorkOrderNo);
			workOrder.setUserComment(workOrder.getUserComment() + "<br/>上道工序设备信息：" + preWorkOrder.getEquipName()
																+ "<br/>上道工序生产进度：" + workOrderService.getFinishedPercent(workOrderNo,preWorkOrderNo));
		}
		// 加载该设备该工序是否需要首检 中检 上车检 下查检
		model.addAttribute("order", workOrder);
		model.addAttribute("single", false);
		model.addAttribute("nameSize", (equipInfo.getName().length() + equipInfo.getStatusText().length()) * 23 * 2);
		model.addAttribute("processInfo", processInformationService.getByCode(workOrder.getProcessCode()));
		model.addAttribute("isFeedCompleted", realCostService.isFeedCompleted(workOrder.getWorkOrderNo()));
		model.addAttribute("detailOrder", workOrder);
		model.addAttribute("workUsers", "");

		OrderTask findParams = new OrderTask();
		findParams.setWorkOrderNo(workOrder.getWorkOrderNo());
		findParams.setStatus(WorkOrderStatus.IN_PROGRESS);
		List<OrderTask> orderTaskArray = orderTaskService.findByObj(findParams);
		OrderTask orderTask = !CollectionUtils.isEmpty(orderTaskArray) ? orderTaskArray.get(0) : null;
		model.addAttribute("currentOrderTaskId", orderTask == null ? "" : orderTask.getId());
		model.addAttribute("currentColor", orderTask == null ? "" : orderTask.getColor());
		return "wip.terminal.main";
	}

	/**
	 * @Title recentOrders
	 * @Description TODO(终端设备获取当前设备的所以生产单)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年3月18日 下午2:12:19
	 * @param @param equipCode 设备编码
	 * @param @param type 状态：已完成/未完成
	 * @param @param section 工段
	 * @param @param sort 排序
	 * @param @param page 页数
	 * @param @param start 开始数
	 * @param @param limit 每页数限制
	 * @return TableView
	 * @throws
	 */
	@RequestMapping(value = "/recentOrders")
	@ResponseBody
	public TableView recentOrders(@RequestParam String equipCode, @RequestParam String type, @RequestParam String section,
			@RequestParam(required = false) String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) {
		TableView tableView = new TableView();
		tableView.setRows(workOrderService.getWOByEquipCodeAndStatus(equipCode, type, start, limit, JSONArray.parseArray(sort, Sort.class)));
		tableView.setTotal(workOrderService.countGetWOByEquipCodeAndStatus(equipCode, type));
		return tableView;
	}

	/**
	 * 点检
	 * 
	 * @param equipCode
	 * @return
	 */
	@RequestMapping(value = "/check")
	@ResponseBody
	public TableView check(@RequestParam String equipCode) {
		MaintainRecord maintainRecord = new MaintainRecord();
		maintainRecord.setEquipCode(equipCode);
		maintainRecord.setStartTime(new Date());
		maintainRecord.setStatus(MaintainStatus.IN_PROGRESS);
		maintainRecord = maintainRecordService.insert(maintainRecord, MaintainTemplateType.valueOf("DAILY"));
		MaintainRecordItem maint = new MaintainRecordItem();
		maint.setRecordId(maintainRecord.getId());
		List<MaintainRecordItem> list = maintainRecordItemService.find(maint, 0, Integer.MAX_VALUE, null);
		TableView tableView = new TableView();
		tableView.setRows(list);
		return tableView;
	}

	/**
	 * @param workOrderNO
	 * @param receiptList
	 * @param operator
	 * @return
	 */
	@RequestMapping(value = "/saveDebugInfo", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveDebugInfo(@RequestParam String workOrderNO, @RequestParam String receiptList, @RequestParam String operator) {
		List<Receipt> receiptArray = JSONArray.parseArray(receiptList, Receipt.class);
		Calendar c = Calendar.getInstance();
		Debug debug = new Debug();
		debug.setWorkOrderNo(workOrderNO);
		debug.setDebugType(DebugType.COLOR);
		debug.setStartTime(c.getTime());
		debugService.saveDebugInfo(debug, receiptArray, operator);
		ModelAndView modelAndView = new ModelAndView(new FastJsonJsonView());
		modelAndView.addObject("success", true);
		return modelAndView;
	}

	/**
	 * 改变设备状态
	 * 
	 * @param equipCode
	 */
	@RequestMapping(value = "/changeEquipStatus", method = RequestMethod.POST)
	@ResponseBody
	public void changeEquipStatus(@RequestParam String equipCode, @RequestParam String operator) {
		workOrderService.changeEquipStatus(equipCode, operator);
	}

	/**
	 * 投料
	 * 
	 * @param workOrderNO
	 * @param operator
	 * @param batchNo
	 * @return
	 */
	@RequestMapping(value = "/feedMaterial/{workOrderNO}/{operator}", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject feedMaterial(@PathVariable String workOrderNO, @PathVariable String operator, @RequestParam String batchNo,
			@RequestParam(required = false) String orderTaskId, @RequestParam(required = false) String color) {
		// 确认这个batchNo 是否在里面
		// 如果是半成品要进行自动出库，如果已出库则
		JSONObject result = new JSONObject();
		/*
		 * if (processQcValueService.inCheck(workOrderNO)) {
		 * result.put("success", false); result.put("msg", "请做上车检"); } else {
		 */
		result = workOrderService.feedMaterial(workOrderNO, batchNo, operator, orderTaskId, color);
		// }
		return result;
	}

	/**
	 * 取消投料
	 * 
	 * @param workOrderNo
	 * @param barCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelFeedMaterial", method = RequestMethod.POST)
	public JSONObject cancelFeedMaterial(@RequestParam String workOrderNo, @RequestParam String barCode) {
		realCostService.cancelPutMat(barCode);
		JSONObject result = new JSONObject();
		result.put("success", true);
		return result;
	}

	/**
	 * 检查投料是否完成
	 * 
	 * @param workOrderNo
	 * @param barCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/checkProductPutIn", method = RequestMethod.POST)
	public JSONObject checkProductPutIn(@RequestParam String workOrderNo, @RequestParam String barCode) {
		List<RealCost> realCost = realCostService.checkProductPutIn(workOrderNo, barCode);
		JSONObject result = new JSONObject();
		result.put("success", CollectionUtils.isEmpty(realCost));
		if (!CollectionUtils.isEmpty(realCost)) {
			RealCost realCost1 = realCost.get(0);
			Mat mat = matService.getByCode(realCost1.getMatCode());
			result.put("matType", mat.getMatType().name());
		}
		return result;
	}

	/**
	 * 查询产品的工艺参数
	 * 
	 * @param woNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/processReceipt", method = RequestMethod.GET)
	public TableView getProcessReceiptList(@RequestParam String woNo, @RequestParam String equipCode) {
		TableView tableView = new TableView();
		List<ProcessReceipt> rows = processReceiptService.getByWorkOrderNo(woNo, equipCode);
		tableView.setRows(rows);
		return tableView;
	}

	/**
	 * 查询产品质量参数
	 * 
	 * @param woNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/processQc", method = RequestMethod.GET)
	public List<ProcessQc> getProcessQcList(@RequestParam String woNo) {
		return processQcService.getByWorkOrderNo(woNo);
	}

	/**
	 * 查询物料需求计划
	 * 
	 * @param woNo
	 */
	@RequestMapping(value = "/material")
	@ResponseBody
	public void getMpr(@RequestParam String woNo) {
		TableView tableView = new TableView();
		List<MaterialRequirementPlan> rows = materialRequirementPlanService.getByWorkOrderNo(woNo);
		tableView.setRows(rows);
	}

	/**
	 * 加载当天工作日历
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadTodayWorkShifts", method = RequestMethod.GET)
	public List<WorkShift> loadTodayWorkShifts() {
		Calendar calendar = Calendar.getInstance();
		return workShiftService.getWorkShiftsByWeekNo(DayOfWeekUtils.getCalendarDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK)));
	}

	/**
	 * 用户刷工号时加载刷卡类型和班次信息
	 * 
	 * @param request
	 * @param response
	 * @param userCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loadUserSingViewData/{userCode}", method = RequestMethod.GET)
	public ModelAndView loadUserSingViewData(HttpServletRequest request, HttpServletResponse response, @PathVariable String userCode) {
		CookieMachineResolver cookieMachineResolver = new CookieMachineResolver();
		String mac = cookieMachineResolver.getMac(request, response);
		return onoffRecordService.loadUserCreditCardTypeAndWorkShift(mac, userCode);
	}

	/**
	 * 查询当前生产单的参数
	 * 
	 * @param woNo
	 * @return
	 */
	@RequestMapping(value = "/receipt/{workOrderNo}", method = RequestMethod.GET)
	@ResponseBody
	public List<Receipt> getReceiptList(@PathVariable String workOrderNo) {
		List<Receipt> lastIsArray = receiptService.getLastReceiptList(workOrderNo);
		return lastIsArray;
	}
	
	/**
	 * 实时加载工艺参数采集值
	 * 
	 * @param workOrderNo
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryProcessQcValueCoilNum", method = RequestMethod.POST)
	public int queryProcessQcValueCoilNum(@RequestParam String workOrderNo, @RequestParam String type, @RequestParam String equipCode) {
		String CoilNum = processQcValueService.queryProcessQcValueCoilNum(workOrderNo, type, equipCode);
		int qcCoilNum = 0;
		if(CoilNum != null){
			qcCoilNum = Integer.parseInt(CoilNum);
		}
		return qcCoilNum;
	}

	/**
	 * 实时加载工艺参数采集值
	 * 
	 * @param workOrderNo
	 * @param type
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryProcessQcValue", method = RequestMethod.GET)
	public TableView queryProcessQcValue(@RequestParam String workOrderNo, @RequestParam String type, @RequestParam String equipCode) {
		TableView tableView = new TableView();
		tableView.setRows(processQcValueService.queryQACheckItems(workOrderNo, type, equipCode));
		return tableView;
	}

	/**
	 * 实时加载质量参数采集值
	 * 
	 * @param jsonText
	 * @param coilNum
	 */
	@ResponseBody
	@RequestMapping(value = "saveProcessQCValue", method = RequestMethod.POST)
	public void saveProcessQCValue(@RequestParam String jsonText, @RequestParam(required = false) Integer coilNum, @RequestParam String equipCode) {
		List<ProcessQcValue> qcList = JSON.parseArray(jsonText, ProcessQcValue.class);
		processQcValueService.entryProcessQAValue(qcList, coilNum, equipCode);
	}

	/**
	 * 保存点检信息
	 * 
	 * @param jsonText
	 */
	@ResponseBody
	@RequestMapping(value = "saveDailyCheck", method = RequestMethod.POST)
	public void saveDailyCheck(@RequestParam String jsonText) {
		List<MaintainRecordItem> recordItemList = JSON.parseArray(jsonText, MaintainRecordItem.class);
		String recordId = recordItemList.get(0).getRecordId();
		maintainRecordService.complete(recordId, recordItemList);
	}
	
	/**
	 * 查询点检完成时间
	 * 
	 * @param code
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value = "dailyCheckIsFinished", method = RequestMethod.POST)
	public boolean getDailyCheckFinishTime(@RequestParam String code) throws ParseException{
		Date finishTime = maintainRecordService.getDailyCheckFinishTime(code);

		SimpleDateFormat ftShiftDate = new SimpleDateFormat ("yyyy-MM-dd");
		SimpleDateFormat ftShiftTime = new SimpleDateFormat ("HH:mm:ss");
		SimpleDateFormat ftNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String ftShiftDateString = ftShiftDate.format(finishTime);
		String ftShiftTimeString = ftShiftTime.format(finishTime);
		
		Date shiftTimeMin = null;
		Date shiftTimeMax = null;
		if(ftShiftTimeString.compareTo("07:45:59") <= 0){
			Calendar cal = Calendar.getInstance();
			cal.setTime(finishTime);
			cal.add(Calendar.DAY_OF_MONTH, -1);// 取当前日期的前一天.
			Date shiftTimeMinString = cal.getTime();
			shiftTimeMin = ftNow.parse(ftShiftDate.format(shiftTimeMinString) + " 23:46:00");
			shiftTimeMax = ftNow.parse(ftShiftDateString + " 07:45:59");
		} else if(ftShiftTimeString.compareTo("15:45:59") <= 0) {
			shiftTimeMin = ftNow.parse(ftShiftDateString + " 07:46:00");
			shiftTimeMax = ftNow.parse(ftShiftDateString + " 15:45:59");
		} else if(ftShiftTimeString.compareTo("23:45:59") <= 0) {
			shiftTimeMin = ftNow.parse(ftShiftDateString + " 15:46:00");
			shiftTimeMax = ftNow.parse(ftShiftDateString + " 23:45:59");
		}
		
		Date dNow = new Date();
		
		if(dNow.compareTo(shiftTimeMin) >= 0 && dNow.compareTo(shiftTimeMax) <= 0){
			return true;
		} else {
			return false;
		}

	}
	

	/**
	 * @param woNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "processMatIn/{woNo}/{section}", method = RequestMethod.GET)
	public List<ProcessInOut> getProcessMatInList(@PathVariable String woNo, @PathVariable String section,
			@RequestParam(required = false) String orderTaskId) {
		List<ProcessInOut> rows = processInOutService.getInByWorkOrderNo(woNo);
		List<RealCost> costList = realCostService.getByWorkOrderNO(woNo);
		outter: for (ProcessInOut in : rows) {
			boolean flg = true;
			for (RealCost cost : costList) {
				if (StringUtils.equalsIgnoreCase(cost.getMatCode(), in.getMatCode())) {
					in.setHasPutIn(true);
					flg = false;
					continue outter;
				}
			}
			if (flg) {
				in.setHasPutIn(false);
			}
		}
		return rows;
	}

	@ResponseBody
	@RequestMapping(value = "dailyCheck/{equipCode}/{workOrderNo}", method = RequestMethod.GET)
	public List<MaintainRecord> dailyCheck(@PathVariable String equipCode, @PathVariable String workOrderNo) {
		return maintainRecordService.dailyCheck(equipCode, workOrderNo);
	}

	@ResponseBody
	@RequestMapping(value = "agreement/{woNo}", method = RequestMethod.GET)
	public TableView getAgreementInfo(@PathVariable String woNo) {
		User user = SessionUtils.getUser();
		List<OrderTask> OrderTaskList = orderTaskService.getByWorkOrderNo(woNo, user.getOrgCode());
		List<Product> rows = new ArrayList<Product>();
		for (OrderTask orderTask : OrderTaskList) {
			String contractNo = orderTask.getContractNo();
			String productCode = orderTask.getProductCode();
			SalesOrder salesOrder = salesOrderService.getByContractNo(contractNo, user.getOrgCode());
			CustomerOrderItem orderItem = customerOrderItemService.getByWorkOrderNoAndContractNo(woNo, contractNo);

			Product product = StaticDataCache.getProductMap().get(productCode);
			Product productNew = new Product();
			productNew.setProductType(product.getProductType());
			productNew.setProductCode(product.getProductCode());
			productNew.setProductSpec(product.getProductSpec());
			productNew.setContractNo(contractNo);
			productNew.setCustomerCompany(salesOrder.getCustomerCompany());
			productNew.setOrderLength(orderItem.getOrderLength());
			productNew.setOrderProLengthOnEquip(orderTask.getTaskLength());
			rows.add(productNew);
		}

		TableView tableView = new TableView();
		tableView.setRows(rows);
		return tableView;
	}

	/**
	 * <p>
	 * 切换生产的原因
	 * </p>
	 * 
	 * @return List<DataDic>
	 * @author DingXintao
	 * @date 2014-7-15 11:12:48
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "/pauseReasonDic", method = RequestMethod.GET)
	public List<DataDic> pauseReasonDic() {
		List<DataDic> eventTypeDescList = terminalService.queryPauseReasonDic();
		return eventTypeDescList;
	}

	/**
	 * <p>
	 * 切换生产单
	 * </p>
	 * 
	 * @param userCode 工号
	 * @param equipCode 设备号
	 * @return JSON
	 * @author DingXintao
	 * @date 2014-7-1 11:20:48
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "changeOrderSeq", method = RequestMethod.POST)
	public JSON changeOrderSeq(@RequestParam String userCode, @RequestParam String password, @RequestParam String equipCode,
			@RequestParam String pauseReasonDic, @RequestParam String oldWorkOrderNo, @RequestParam String newWorkOrderNo) {
		MethodReturnDto dto = terminalService.changeOrderSeq(userCode, password, equipCode, oldWorkOrderNo, newWorkOrderNo, pauseReasonDic);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}

	/**
	 * <p>
	 * 获取设备报警类型下拉框Store
	 * </p>
	 * 
	 * @return
	 * @author DingXintao
	 * @date 2014-6-3 14:12:48
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "/equipAlarmType", method = RequestMethod.GET)
	public List<EventType> equipAlarmType() {
		List<EventType> eventTypeList = terminalService.equipAlarmType();
		return eventTypeList;
	}

	/**
	 * <p>
	 * 获取设备报警类型明细下拉框Store
	 * </p>
	 * 
	 * @return
	 * @author DingXintao
	 * @date 2014-6-3 14:12:48
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "/equipAlarmTypeDesc/{query}", method = RequestMethod.GET)
	public List<DataDic> equipAlarmTypeDesc(@PathVariable String query) {
		String codeOrName = "";
		if (!StringUtils.equalsIgnoreCase(WebConstants.ROOT_ID, query)) {
			codeOrName = query;
		}
		List<DataDic> eventTypeDescList = terminalService.equipAlarmTypeDesc(codeOrName);
		return eventTypeDescList;
	}

	/**
	 * @Title: triggerEquipAlarm
	 * @Description: TODO(手动触发报警提交)
	 * @param: @param equipCode 设备编码
	 * @param: @param eventTypeCode 事件类型
	 * @param: @param eventTypeCodeDesc 事件类型明细
	 * @param: @param operator 操作工
	 * @param: @param director 维修班负责人
	 * @return: JSON
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/triggerEquipAlarm", method = RequestMethod.POST)
	public JSON triggerEquipAlarm(@RequestParam String equipCode, @RequestParam String eventTypeCode, @RequestParam String eventTypeCodeDesc,
			@RequestParam String operator, @RequestParam(required = false) String director) {
		User user = userService.checkUserCodeUnique(operator);
		if (user == null) {
			return JSONArrayUtils.ajaxJsonResponse(false, "用户不存在!");
		}
		MethodReturnDto dto = terminalService.triggerEquipAlarm(equipCode, eventTypeCode, eventTypeCodeDesc, operator, director);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}

	/**
	 * <p>
	 * 处理警报 显示列表
	 * </p>
	 * 
	 * @return
	 * @author DingXintao
	 * @date 2014-6-3 14:12:48
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "/equipAlarmList")
	public TableView equipAlarmList(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String[] eventStatusFindParam,
			@RequestParam(required = false) String equipCode, @RequestParam String sort, @RequestParam int page, @RequestParam int start,
			@RequestParam int limit) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eventStatusFindParam", eventStatusFindParam);
		param.put("equipCode", equipCode);
		if (StringUtils.isBlank(equipCode)) {
			CookieMachineResolver cookieMachineResolver = new CookieMachineResolver();
			String mac = cookieMachineResolver.getMac(request, response);
			String ip = cookieMachineResolver.getIp(request, response);
			param.put("mac", mac);
			param.put("ip", ip);
		}

		param.put("orgCode", SessionUtils.getUser().getOrgCode());
		// TableView tableView = terminalService.getEquipAlarmTableView(param,
		// sort, page, start, limit,
		// JSONArray.parseArray(sort, Sort.class));

		List<EventInformation> list = eventInformationService.getAlarmInfo(param, start, limit, JSONArray.parseArray(sort, Sort.class));
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(eventInformationService.alarmTotalCount(param));
		return tableView;
	}

	/**
	 * <p>
	 * 处理报警提交
	 * </p>
	 * 
	 * @return
	 * @author DingXintao
	 * @date 2014-6-3 14:12:48
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "/handleEquipAlarm", method = RequestMethod.POST)
	public JSON handleEquipAlarm(@RequestParam String userCode, @RequestParam String id, @RequestParam String equipCode) {
		MethodReturnDto dto = terminalService.handleEquipAlarm(userCode, id, equipCode);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}

	@ResponseBody
	@RequestMapping(value = "/findEventStatus", method = RequestMethod.POST)
	public JSON findEventStatus(@RequestParam String eventId) {
		EventInformation eventInformation = eventInformationService.getById(eventId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("eventStatus", eventInformation.getEventStatus());
		map.put("equipCode", eventInformation.getEquipCode());
		return JSONArrayUtils.ajaxJsonResponse(true, "", map);
	}

	@ResponseBody
	@RequestMapping(value = "/eventStatusChanged", method = RequestMethod.POST)
	public JSON EventStatusChanged(@RequestParam String id,@RequestParam String equipCode,@RequestParam String userCode,@RequestParam String isFinished,@RequestParam String thought) {
		User user = userService.checkUserCodeUnique(userCode);
		if(user == null){
			return JSONArrayUtils.ajaxJsonResponse(false, "员工号不存在，请重新输入。。");
		}
		EventInformation eventInformation = eventInformationService.getById(id);
		EventProcessLog log = new EventProcessLog();
		if("1".equals(isFinished)){
			eventInformation.setEventStatus(EventStatus.COMPLETED);
			eventInformation.setEvaluate(thought);
			log.setEventInfoId(id);
			log.setType(EventStatus.COMPLETED);
			log.setOrgCode(SessionUtils.getUser().getOrgCode());
			log.setCreateUserCode(userCode);
			eventProcessLogService.insert(log);
			eventInformationService.update(eventInformation);
		}else{
			eventInformation.setEventStatus(EventStatus.NOTCOMPLETED);
			log.setEventInfoId(id);
			log.setType(EventStatus.NOTCOMPLETED);
			log.setOrgCode(SessionUtils.getUser().getOrgCode());
			log.setCreateUserCode(userCode);
			eventProcessLogService.insert(log);
			eventInformationService.update(eventInformation);
		}
		return JSONArrayUtils.ajaxJsonResponse(true, "提交成功。。");
	}

	/**
	 * <p>
	 * 处理报设备故障 警告
	 * </p>
	 * 
	 * @return
	 * @author DingXintao
	 * @date 2014-6-3 14:12:48
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "/handleEquipError", method = RequestMethod.POST)
	public JSON handleEquipError(@RequestParam String userCode, @RequestParam String id, @RequestParam String equipCode) {
		MethodReturnDto dto = terminalService.handleEquipError(userCode, equipCode);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}

	/**
	 * 查询生产单报工
	 * 
	 * @param woNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryReport/{woNo}", method = RequestMethod.GET)
	public List<Report> queryReport(@PathVariable String woNo) {
		List<Report> rows = reportService.getByWorkOrder(woNo);
		return rows;
	}

	/**
	 * 报工信息查询
	 * 
	 * @param equipCode
	 * @param sort
	 * @param start
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "reportDetail/{equipCode}", method = RequestMethod.GET)
	public TableView reportDetail(@PathVariable String equipCode, @RequestParam(required = false) Integer reportDate,
			@RequestParam(required = false) String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) {
		Report findParams = new Report();
		reportDate = (reportDate == null  ? 0 : reportDate);
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, reportDate);
		calendar.set(Calendar.HOUR, -12);
		calendar.set(Calendar.MINUTE, 0);
 		calendar.set(Calendar.SECOND, 0);
 		findParams.setEquipCode(equipCode);
 		findParams.setReportTime(calendar.getTime());
 		List<Report> list = reportService.getReportByEquipCode(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(reportService.countFind(findParams));
		return tableView;
	}
	

	@ResponseBody
	@RequestMapping(value = "/productInformation", method = RequestMethod.GET)
	public TableView productInformation(@RequestParam String equipCodes,@RequestParam String userCode,@RequestParam String onTime,@RequestParam String offTime, @RequestParam(required = false) Integer reportDate,
			@RequestParam(required = false) String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) {
		Report findParams = new Report();
		Date onTime1 = new Date(Long.valueOf(onTime));
		Date offTime1 = new Date(Long.valueOf(offTime));
		findParams.setOnTime(onTime1);
		findParams.setOffTime(offTime1);
		findParams.setReportUserCode(userCode);
		findParams.setEquipCode(equipCodes);
		List<Report> list = reportService.getReportOutput(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
		for(Report li :list){
			String rMarks = reportService.getReMarks(li.getWorkOrderNo());
//			li.setRMarks(rMarks);
		}
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(reportService.countFind(findParams));
		return tableView;
	}
	
//	
	/**
	 * 查询生产单 某个物料的投料的信息
	 * 
	 * @param matCode
	 * @param workOrderNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "matBatchs", method = RequestMethod.GET)
	public TableView matBatchs(@RequestParam String matCode, @RequestParam String workOrderNo) {
		RealCost findParams = new RealCost();
		findParams.setWorkOrderNo(workOrderNo);
		findParams.setMatCode(matCode);
		TableView tableView = new TableView();
		tableView.setRows(realCostService.findByObj(findParams));
		return tableView;
	}

	/**
	 * 检查是否已做下车检
	 * 
	 * @param woNo
	 * @param equipCode
	 * @param coilNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkExistsInputQcValue", method = RequestMethod.GET)
	public JSONObject checkExistsInputQcValue(@RequestParam String woNo, @RequestParam String equipCode, @RequestParam int coilNum) {
		return processQcValueService.checkExistsInputQcValue(woNo, equipCode, coilNum);
	}

	/**
	 * @Title:       finishWorkOrder
	 * @Description: TODO(报工界面: 完成生产单按钮:校验生产单是否全部完成)
	 * @param:       workOrderNo 生产单号
	 * @param:       equipCode 设备编码
	 * @param:       operator 操作人
	 * @return:      JSON   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "checkIsFinished", method = RequestMethod.POST)
	public JSON checkWorkOrderIsFinished(@RequestParam String workOrderNo, @RequestParam String equipCode, @RequestParam String operator) {
		MethodReturnDto dto =  workOrderService.checkWorkOrderIsFinished(workOrderNo, equipCode, operator);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}
	
	/**
	 * @Title:       finishWorkOrder
	 * @Description: TODO(报工界面: 完成生产单按钮:完成生产单)
	 * @param:       workOrderNo 生产单号
	 * @param:       equipCode 设备编码
	 * @param:       operator 操作人
	 * @return:      JSON   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "finishWorkOrder", method = RequestMethod.POST)
	public JSON finishWorkOrder(@RequestParam String workOrderNo, @RequestParam String equipCode, @RequestParam String operator) {
		MethodReturnDto dto =  workOrderService.finishWorkOrder(workOrderNo, equipCode, operator);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}

	/**
	 * @param reportId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "reportSection/{reportId}", method = RequestMethod.GET)
	public List<Section> reportSection(@PathVariable String reportId) {
		return sectionService.getPrintSectionInfo(reportId);
	}

	/**
	 * 查询打印信息
	 * 
	 * @param reportId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "print", method = RequestMethod.POST)
	public JSONObject printBarCode(HttpServletRequest request, HttpServletResponse response, @RequestParam String reportId) {
		JSONObject result = new JSONObject();
		result.put("success", true);

		CookieMachineResolver cookieMachineResolver = new CookieMachineResolver();
		String mac = cookieMachineResolver.getMac(request, response);
		MesClient mesClient = mesClientService.getByClientMac(mac);
		Map<String, Object> printInfo = reportService.createBarCode(reportId);
		printInfo.put("printNum", mesClient.getPrintNum());
		result.put("report", printInfo);
		return result;
	}

	/**
	 * <p>
	 * 库存管理块：获取当前工序的物料位置信息
	 * </p>
	 * 
	 * @param params 物料号和工序ID
	 * @return JSONObject
	 * @author DingXintao
	 * @date 2014-9-15 11:16:48
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "invLocationList")
	public TableView invLocationList(HttpServletRequest request, @ModelAttribute Inventory params) {
		// String matCode = params.getMaterialCode();
		// Report findParams = new Report();
		// findParams.setMatCode(matCode);
		// List<Report> reportList = reportService.getByObj(findParams);
		// for (Report report : reportList) {
		// String barCode = report.getSerialNum();
		// Inventory inv = new Inventory();
		// inv.setBarCode(barCode);
		// inv.setOrgCode(SessionUtils.getUser().getOrgCode());
		// List<Inventory> list = inventoryService.getByObj(inv);
		//
		// }
		List<Inventory> list = terminalService.invLocationList(params);

		TableView tableView = new TableView();
		tableView.setRows(list);
		return tableView;
	}

	/**
	 * 验证用户权限
	 * 
	 * @param userCode
	 * @param equipCode
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validUserPerm", method = RequestMethod.POST)
	public JSONObject validUserPerm(@RequestParam String userCode, @RequestParam String equipCode, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result = new JSONObject();
		User user = userService.checkUserCodeUnique(userCode);

		boolean flag = true;
		if (user == null) {
			flag = false;
			result.put("msg", "用户不存在!");
		}

		if (flag) {
			CookieMachineResolver cookieMachineResolver = new CookieMachineResolver();
			String mac = cookieMachineResolver.getMac(request, response);

			int havePerm = onoffRecordService.validUserPermission(userCode, mac, equipCode);
			if (havePerm == 0) {
				flag = false;
				result.put("msg", "您未刷卡或者您没有该操作权限!");
			}
		}
		if (flag) {
			String status = user.getStatus();
			if (WebConstants.NO.equals(status)) {
				flag = false;
				result.put("msg", "用户已被冻结,请刷卡退出！");
			}
		}
		if(flag){
			int ifCreditCard  = onoffRecordService.checkIFCreditCard(userCode,equipCode);
			if(ifCreditCard== 0){
				flag = false;
				result.put("msg", "您未刷当前机台上班卡，没有操作权限!");
			}
		}
		// 验证用户权限
		result.put("success", flag);
		return result;
	}

	/**
	 * 验证用户是否是班组长，并且是否有设备权限
	 * 
	 * @param userCode
	 * @param equipCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validBZZPermissions", method = RequestMethod.POST)
	public MethodReturnDto validBZZPermissions(@RequestParam String userCode, @RequestParam String equipCode, @RequestParam String password) {
		Employee employee = employeeService.validUserBZZPermissions(userCode, equipCode);
		MethodReturnDto dto = new MethodReturnDto(employee != null);
		if (employee == null) {
			dto.setMessage("您没有该设备的权限或者你不是班组长!");
		} else {
			if (!password.equals(employee.getPassword())) {
				dto.setSuccess(false);
				dto.setMessage("您的密码错误，请重新输入!");
			}
		}
		return dto;
	}

	/**
	 * 实时刷新浪费长度
	 * 
	 * @param woNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "refreshWasteLen", method = RequestMethod.POST)
	public JSONObject refreshWasteLen(@RequestParam String woNo) {
		return reportService.calculateWasteLength(woNo);
	}

	/**
	 * @param reportId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "printSectionList/{reportId}", method = RequestMethod.GET)
	public List<Section> printSectionList(@PathVariable String reportId) {
		List<Section> list = sectionService.getPrintSectionInfo(reportId);
		List<Section> result = new ArrayList<Section>();
		double sectionLength = 0;
		double sectionLocal = 0;
		for (int i = 0; i < list.size(); i++) {
			Section section = list.get(i);

			sectionLength += section.getSectionLength();
			sectionLocal += section.getSectionLength();

			if (section.getSectionType() == SectionType.NORMAL) {
				continue;
			}
			section.setSectionLength(sectionLength);
			section.setSectionLocal(sectionLocal);
			result.add(section);
			if (result.size() == 0) {
				sectionLength = 0;
			}
		}
		return result;
	}

	/**
	 * 查询销售订单
	 * 
	 * @param workOrderNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "salesOrderInfo/{workOrderNo}/{section}", method = RequestMethod.GET)
	public List<Map<String, Object>> salesOrderInfo(@PathVariable String workOrderNo, @PathVariable String section) {
		return workOrderService.getWOSalesOrderInfo(workOrderNo, section);
	}

	/**
	 * 查询任务
	 * 
	 * @param workOrderNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "taskInfo/{workOrderNo}", method = RequestMethod.GET)
	public List<Map<String, String>> taskInfo(@PathVariable String workOrderNo) {
		List<Map<String, String>> result = customerOrderItemService.showWorkOrderDetailCommon(workOrderNo);
		return result;
	}

	/**
	 * 切换任务
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "startTask", method = RequestMethod.POST)
	public JSON startTask(@RequestParam String status, @RequestParam String ids, @RequestParam String equipCode) {
		String[] idsArr = ids.split(",");
		MethodReturnDto dto = orderTaskService.changeTask(idsArr, status, equipCode);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage(), dto.getJsonMap());
	}

	/**
	 * @param equipCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "toDayMatPlan", method = RequestMethod.GET)
	public List<MaterialRequirementPlan> toDayMatPlan(@RequestParam String equipCode) {
		return materialRequirementPlanService.getMapRByEquipCode(equipCode);
	}

	@ResponseBody
	@RequestMapping(value = "queryTaskEquipStatus", method = RequestMethod.POST)
	public JSONObject queryTaskEquipStatus(@RequestParam String taskId) {
		JSONObject jsonObject = new JSONObject();
		OrderTask params = new OrderTask();
		params.setId(taskId);
		List<OrderTask> orderTaskList = orderTaskService.findByObj(params);
		jsonObject.put("equipCode", orderTaskList.get(0).getEquipCode() == null ? "" : orderTaskList.get(0).getEquipCode());
		jsonObject.put("status", orderTaskList.get(0).getStatus().name());
		return jsonObject;
	}

	@ResponseBody
	@RequestMapping(value = "printMatList", method = RequestMethod.POST)
	public JSONObject printMatList(HttpServletRequest request, HttpServletResponse response, @RequestParam String workOrderNo) {
		List<Map<String, String>> mats = workOrderService.getPrintMatList(workOrderNo);
		JSONObject jsonObject = new JSONObject();
		if (mats == null) {
			jsonObject.put("success", false);
		} else {
			CookieMachineResolver cookieMachineResolver = new CookieMachineResolver();
			String mac = cookieMachineResolver.getMac(request, response);
			WorkOrder workOrder = workOrderService.getByWorkOrderNO(workOrderNo);
			MesClient mesClient = mesClientService.getByClientMac(mac);
			if (!StringUtils.isBlank(workOrder.getProcessGroupRespool())) {
				String processGroupRespool = workOrder.getProcessGroupRespool();
				List<Inventory> locationNameList = inventoryService.findByBarCode(processGroupRespool);
				String[] locationNameArr = new String[locationNameList.size()];
				for (int i = 0; i < locationNameList.size(); i++) {
					locationNameArr[i] = locationNameList.get(i).getLocationName();
				}
				jsonObject.put("locationName", locationNameArr);
			}

			jsonObject.put("printNum", mesClient.getPrintNum());
			jsonObject.put("processGroupRespool", workOrder.getProcessGroupRespool());
			jsonObject.put("mats", mats);
			jsonObject.put("success", true);
		}
		return jsonObject;
	}

	@ResponseBody
	@RequestMapping(value = "getLocationName", method = RequestMethod.GET)
	public List<Location> getLocationName(@RequestParam String processCode, @RequestParam(required = false) String kuaQu) {
		Location findParams = new Location();
		findParams.setProcessCode(processCode);
		findParams.setUsedOrNot("1");
		if (!"".equals(kuaQu)) {
			findParams.setKuaQu(kuaQu);
		}
		List<Location> locationList = locationService.getByObj(findParams);
		return locationList;
	}

	@ResponseBody
	@RequestMapping(value = "/changeNewWorkOrderNo", method = RequestMethod.POST)
	public void changeNewWorkOrderNo(@RequestParam String oldWorkOrderNo, @RequestParam String newWorkOrderNo) {
		WorkOrder workOrderNew = workOrderService.getByWorkOrderNO(newWorkOrderNo);
		workOrderNew.setStatus(WorkOrderStatus.IN_PROGRESS);
		workOrderService.update(workOrderNew);
		if (StringUtils.isEmpty(workOrderNew.getProcessGroupRespool())) {
			WorkOrder lastWorkOrder = workOrderService.getByWorkOrderNO(workOrderNew.getProcessGroupRespool());
			lastWorkOrder.setMatIsUsed("1");
			workOrderService.update(lastWorkOrder);
		}

		WorkOrder workOrderOld = workOrderService.getByWorkOrderNO(oldWorkOrderNo);
		workOrderOld.setStatus(WorkOrderStatus.TO_DO);
		workOrderService.update(workOrderOld);
	}

	@ResponseBody
	@RequestMapping(value = "/saveChangeWorkOrderNoReason", method = RequestMethod.POST)
	public void saveChangeWorkOrderNoReason(@RequestParam String reason, @RequestParam String oldWorkOrderNo, @RequestParam String equipCode,
			@RequestParam String userCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (reason != null && !reason.isEmpty()) {
			param.put("reason", reason);
		}
		if (oldWorkOrderNo != null && !oldWorkOrderNo.isEmpty()) {
			param.put("oldWorkOrderNo", oldWorkOrderNo);
		}
		if (equipCode != null && !equipCode.isEmpty()) {
			param.put("equipCode", equipCode);
		}
		if (userCode != null && !userCode.isEmpty()) {
			param.put("userCode", userCode);
		}
		param.put("orgCode", SessionUtils.getUser().getOrgCode());
		workOrderService.saveChangeWorkOrderNoReason(param);
	}

	class SortByNu implements Comparator {
		public int compare(Object o1, Object o2) {
			String s1 = (String) o1;
			String s2 = (String) o2;
			s1 = s1.substring(0, s1.indexOf("#"));
			s2 = s2.substring(0, s2.indexOf("#"));
			if (Integer.valueOf(s1) > Integer.valueOf(s2))
				return 1;
			return 0;
		}
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
	 * 设备调整：修改生产单的使用设备
	 * 
	 * @param workOrderNo 生产单编号
	 * @param equipNameArrayStr 设备名称集
	 * @param equipCodeArrayStr 设备编码集
	 * @return
	 * @author DingXintao
	 */
	@ResponseBody
	@RequestMapping(value = "changeWorkEquipSub", method = RequestMethod.POST)
	public JSON changeWorkEquipSub(@RequestParam String workOrderNo, @RequestParam String equipNameArrayStr, @RequestParam String equipCodeArrayStr) {
		MethodReturnDto dto = handScheduleService.changeWorkEquipSub(workOrderNo, equipNameArrayStr, equipCodeArrayStr);
		return JSONArrayUtils.ajaxJsonResponse(dto.isSuccess(), dto.getMessage());
	}

	@ResponseBody
	@RequestMapping(value = "getMaterial", method = RequestMethod.POST)
	public void getMaterial(@RequestParam String workOrderNo, @RequestParam String equipCode, @RequestParam String userCode) {
		WorkOrder workOrder = workOrderService.getByWorkOrderNO(workOrderNo);
		workOrder.setMatStatus(MaterialStatus.MAT_DOWN);
		workOrderService.update(workOrder);

		List<MaterialRequirementPlan> materialRequirementPlanList = materialRequirementPlanService.getByWorkOrderNo(workOrderNo);
		for (MaterialRequirementPlan materialRequirementPlan : materialRequirementPlanList) {
			materialRequirementPlan.setStatus(MaterialStatus.MAT_DOWN);
			materialRequirementPlan.setEquipName(equipCode);
			materialRequirementPlan.setUserCode(userCode);
			materialRequirementPlanService.update(materialRequirementPlan);
		}

	}

	@ResponseBody
	@RequestMapping(value = "getMaterialDetail", method = RequestMethod.POST)
	public JSONObject getMaterialDetail(@RequestParam String workOrderNo) {
		JSONObject result = new JSONObject();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("workOrderNo", workOrderNo);
		List<MaterialMng> materialMngList = materialMngService.findMap(param, 0, Integer.MAX_VALUE, null);

		result.put("materialDetail", materialMngList);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "getUserName", method = RequestMethod.POST)
	public String getUserName(@RequestParam String userCode) {
		String userName = userService.checkUserCodeUnique(userCode).getName();
		return userName;
	}

	/**
	 * @Title: getUsers
	 * @Description: TODO(终端获取在线工人信息)
	 * @param: request 请求
	 * @param: response 响应
	 * @param: role 角色:挡班/副挡板/辅助工
	 * @return: List<OnoffRecord>
	 * @throws
	 */
	@RequestMapping(value = "getUsers", method = RequestMethod.GET)
	@ResponseBody
	public List<OnoffRecord> getUsers(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String role) {
		CookieMachineResolver cookieMachineResolver = new CookieMachineResolver();
		String mac = cookieMachineResolver.getMac(request, response);
		List<OnoffRecord> list = onoffRecordService.getByMesClientMacAndRole(mac, role);
		return list;
	}

	@RequestMapping(value = "getAllUsers", method = RequestMethod.GET)
	@ResponseBody
	public List<OnoffRecord> getAllUsers(HttpServletRequest request, HttpServletResponse response) {
		CookieMachineResolver cookieMachineResolver = new CookieMachineResolver();
		String mac = cookieMachineResolver.getMac(request, response);
		List<OnoffRecord> list = onoffRecordService.getByMesClientUsers(mac);
		return list;
	}

	@RequestMapping(value = "shiftRecord", method = RequestMethod.GET)
	@ResponseBody
	public TableView getShiftRecord(HttpServletRequest request, @RequestParam String equipCode, @RequestParam(required = false) Integer start,
			@RequestParam(required = false) Integer limit, @RequestParam(required = false) String sort, @RequestParam int page,
			@RequestParam(required = false) String shift) throws ParseException {
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		String shiftDate = request.getParameter("shiftDate");
		TableView tableView = new TableView();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = null;
		String currentTime = f.format(new Date());
		String mShift = shiftDate + " " + "07:45:00";
		String aShift = shiftDate + " " + "15:45:00";
		String eShift = shiftDate + " " + "23:45:00";

		List<TurnOverReport> lists = new ArrayList<TurnOverReport>();
		if (shift.equals("mShift")) {
			currentTime = shiftDate + " " + "15:45:00";
			lists = turnOverReportService.getTurnOverReportByEquipCode(equipCode, mShift, currentTime);
			String productOutput = turnOverReportService.getProductOutput(equipCode,mShift,currentTime);
			for(TurnOverReport list :lists){
				list.setProductOutput(productOutput);
			}
			tableView.setTotal(turnOverReportService.countTurnOverReportByEquipCode(equipCode, mShift, currentTime));
		}
		if (shift.equals("aShift")) {
			currentTime = shiftDate + " " + "23:45:00";
			lists = turnOverReportService.getTurnOverReportByEquipCode(equipCode, aShift, currentTime);
			tableView.setTotal(turnOverReportService.countTurnOverReportByEquipCode(equipCode, aShift, currentTime));
		}
		if (shift.equals("eShift")) {

			c = Calendar.getInstance();
			Date d = new SimpleDateFormat("yyyy-MM-dd").parse(shiftDate);
			c.setTime(d);
			c.add(Calendar.DAY_OF_MONTH, 1);
			currentTime = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + " " + "07:45:00";

			lists = turnOverReportService.getTurnOverReportByEquipCode(equipCode, eShift, currentTime);
			tableView.setTotal(turnOverReportService.countTurnOverReportByEquipCode(equipCode, eShift, currentTime));
			/*
			 * if(f.format(now).substring(11,13).equals("23")){
			 * c=Calendar.getInstance(); c.add(Calendar.DAY_OF_MONTH, 1); String
			 * now1=f.format(c.getTime()); String
			 * mShift2=now1.substring(0,10)+" "+"07:45:00";
			 * lists=turnOverReportService
			 * .getTurnOverReportByEquipCode(equipCode,eShift,mShift2);
			 * tableView
			 * .setTotal(turnOverReportService.countTurnOverReportByEquipCode
			 * (equipCode,eShift,mShift2)); } else{ c=Calendar.getInstance();
			 * c.add(Calendar.DAY_OF_MONTH, -1); String
			 * now1=f.format(c.getTime()); String
			 * eShift2=now1.substring(0,10)+" "+"23:45:00";
			 * lists=turnOverReportService
			 * .getTurnOverReportByEquipCode(equipCode,eShift2,mShift);
			 * tableView
			 * .setTotal(turnOverReportService.countTurnOverReportByEquipCode
			 * (equipCode,eShift2,mShift)); }
			 */
		}

		tableView.setRows(lists);
		return tableView;

	}

	
	@RequestMapping(value = "getProductOutput", method = RequestMethod.GET)
	@ResponseBody
	public String getProductOutput(HttpServletRequest request, @RequestParam String equipCode,
			@RequestParam(required = false) String shift) throws ParseException {
		SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
		String shiftDate = a.format(new Date());
//		String shiftDate = request.getParameter("shiftDate");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = null;
		String currentTime = f.format(new Date());
		String mShift = shiftDate + " " + "07:45:00";
		String aShift = shiftDate + " " + "15:45:00";
		String eShift = shiftDate + " " + "23:45:00";
		String productOutput = "";
		if (shift.equals("mShift")) {
			currentTime = shiftDate + " " + "15:45:00";
			productOutput = turnOverReportService.getProductOutput(equipCode,mShift,currentTime);
		}
		if (shift.equals("aShift")) {
			currentTime = shiftDate + " " + "23:45:00";
			productOutput = turnOverReportService.getProductOutput(equipCode,mShift,currentTime);
		}
		if (shift.equals("eShift")) {
			c = Calendar.getInstance();
			Date d = new SimpleDateFormat("yyyy-MM-dd").parse(shiftDate);
			c.setTime(d);
			c.add(Calendar.DAY_OF_MONTH, 1);
			currentTime = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + " " + "07:45:00";
			productOutput = turnOverReportService.getProductOutput(equipCode,mShift,currentTime);
			
		}
		return productOutput;
		
	}
	
	@RequestMapping(value = "shiftRecordInsert", method = RequestMethod.POST)
	@ResponseBody
	public JSON shiftRecordInsert(@RequestParam String equipCode, @RequestParam String shiftName, @RequestParam String dbUserCode,
			@RequestParam String dbUserName, @RequestParam(required = false) String fdbUserCode, @RequestParam(required = false) String fdbUserName,
			@RequestParam(required = false) String fzgUserCode, @RequestParam(required = false) String fzgUserName,
			@RequestParam String realJsonDatas, @RequestParam(required = false) String turnOverDate, @RequestParam String operator,
			@RequestParam String shiftDate, @RequestParam String processCode, @RequestParam String param, @RequestParam String createTime)
			throws ParseException {
		JSONArray JSONArrParam = JSON.parseArray(param);
		JSONArray JSONArrData = JSON.parseArray(realJsonDatas);
		for (int i = 0; i < JSONArrParam.size(); i++) {
			JSONObject jo = JSONArrParam.getJSONObject(i);
			String orderItemDecId = jo.getString("id");
			String workOrderNo = jo.getString("workOrderNo");
			String contractNo = jo.getString("contractNo");
			String custProductType = jo.getString("custProductType");
			String custProductSpec = jo.getString("custProductSpec");
			Double workOrderLength = jo.getDouble("workOrderLength");
			Double reportLength = jo.getDouble("reportLength");
			String realJsonData = JSONArrData.getString(i);
			String matCode = jo.getString("matCode");
			String matName = jo.getString("matName");
			String quotaQuantity = jo.getString("quotaQuantity");
			turnOverReportService.insertTurnOverReport(orderItemDecId, equipCode, shiftName, dbUserCode, dbUserName, fdbUserCode, fdbUserName,
					fzgUserCode, fzgUserName, workOrderNo, contractNo, custProductType, custProductSpec, workOrderLength, reportLength, realJsonData,
					turnOverDate, matCode, matName, quotaQuantity, operator, shiftDate, processCode, createTime);
		}

		return JSONArrayUtils.ajaxJsonResponse(true, "成功");

	}

	@ResponseBody
	@RequestMapping(value = "saveSuppMaterialInfo", method = RequestMethod.POST)
	public JSON saveSuppMaterialInfo(@RequestParam String yaoLiaoInfo, @RequestParam String workOrderNo) {
		// JSONObject result = new JSONObject();
		List<BorrowMat> list = JSONArray.parseArray(yaoLiaoInfo, BorrowMat.class);

		for (BorrowMat b : list) {
			b.setId(UUID.randomUUID().toString());
			b.setStatus(MaterialStatus.MAT_DOWN);
			terminalService.insertBorrowMat(b);
		}
		WorkOrder w = workOrderService.getByWorkOrderNO(workOrderNo);
		w.setMatStatus(MaterialStatus.MAT_DOWN);
		workOrderService.update(w);
		return JSONArrayUtils.ajaxJsonResponse(true, "成功");
	}

	@ResponseBody
	@RequestMapping(value = "getMaterialInfo", method = RequestMethod.POST)
	public List<MaterialRequirementPlan> getMaterialInfo(@RequestParam String workOrderNo, @RequestParam String[] matCodeArr) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workOrderNo", workOrderNo);
		params.put("matCodeArr", Arrays.asList(matCodeArr));
		return terminalService.getMaterialInfo(params);
	}

	@ResponseBody
	@RequestMapping(value = "insertMoreMaterial", method = RequestMethod.POST)
	public void insertMoreMaterial(@RequestParam String workOrderNo, @RequestParam String userCode, @RequestParam String equipCode,
			@RequestParam String[] moreLengthArr, @RequestParam String[] moreLengthCodeArr, @RequestParam String[] planAmountArr) {
		for (int i = 0; i < moreLengthCodeArr.length; i++) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("workOrderNo", workOrderNo);
			params.put("userCode", userCode);
			params.put("equipCode", equipCode);
			params.put("moreLengthArr", moreLengthArr[i]);
			params.put("moreLengthCodeArr", moreLengthCodeArr[i]);
			params.put("planAmountArr", planAmountArr[i]);
			params.put("sysuserCode", SessionUtils.getUser().getUserCode());
			terminalService.insertMoreMaterial(params);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getProcessCode", method = RequestMethod.POST)
	public List<MesClient> getProcessCode(HttpServletRequest request, @RequestParam String code) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", code);
		return handScheduleService.getProcessCode(param);
	}

	@ResponseBody
	@RequestMapping(value = "checkOrderId", method = RequestMethod.POST)
	public boolean checkOrderId(@RequestParam String workOrderNo) {
		OrderTask findParams = new OrderTask();
		findParams.setWorkOrderNo(workOrderNo);
		findParams.setStatus(WorkOrderStatus.IN_PROGRESS);
		List<OrderTask> orderTaskArray = orderTaskService.findByObj(findParams);
		if (orderTaskArray == null || orderTaskArray.size() == 0) {
			findParams.setStatus(WorkOrderStatus.TO_DO);
			orderTaskArray = orderTaskService.findByObj(findParams);
			if (orderTaskArray == null || orderTaskArray.size() == 0) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}

	@ResponseBody
	@RequestMapping(value = "getMat", method = RequestMethod.POST)
	public List<Mat> getMatbyWorkOrderNo(@RequestParam String workOrderNo, @RequestParam String processCode) {
		return terminalService.getMatbyWorkOrderNo(workOrderNo, processCode);
	}

	@ResponseBody
	@RequestMapping(value = "getAssistOp", method = RequestMethod.POST)
	public List<AssistOption> getAssistOp(@RequestParam String processCode) {
		return terminalService.getAssistOp(processCode);
	}

	@ResponseBody
	@RequestMapping(value = "matTurnover", method = RequestMethod.POST)
	public JSONObject matTurnover(HttpServletRequest request, @RequestParam String jsonData, @RequestParam String operator) throws ParseException {
		JSONObject realJs = JSON.parseObject(jsonData);
		JSONArray matNames = realJs.getJSONArray("matNames");
		JSONArray assistNames = realJs.getJSONArray("assistNames");
		String matUsageId = UUID.randomUUID().toString();
		String assistId = UUID.randomUUID().toString();
		JSONObject result = new JSONObject();
		String processCode = realJs.getString("processCode");
		Map<String, String> map = new HashMap<String, String>();
		matUsageService.checkData(realJs, operator);
		matUsageService.insertData(realJs, matUsageId, operator);
		if (processCode.equals("Extrusion-Single") || processCode.equals("Jacket-Extrusion")) {
			for (int i = 0; i < matNames.size(); i++) {
				Map<String, String> param = new HashMap<String, String>();
				String id = UUID.randomUUID().toString();
				String matName = ((JSONObject) matNames.get(i)).getString("matName");
				String matCode = ((JSONObject) matNames.get(i)).getString("matCode");
				String sbjl = realJs.getString(matCode + "Sbjl");
				String bbll = realJs.getString(matCode + "Bbll");
				String bbtl = realJs.getString(matCode + "Bbtl");
				String bbjl = realJs.getString(matCode + "Bbjl");
				param.put("id", id);
				param.put("matUsageId", matUsageId);
				param.put("matName", matName);
				param.put("matCode", matCode);
				param.put("sbjl", sbjl);
				param.put("bbll", bbll);
				param.put("bbtl", bbtl);
				param.put("bbjl", bbjl);
				param.put("operator", operator);
				matUsageService.insertMatDetail(param);
			}
		}
		for (int i = 0; i < assistNames.size(); i++) {
			Map<String, String> param = new HashMap<String, String>();
			String id = UUID.randomUUID().toString();
			String assistOptionEname = ((JSONObject) assistNames.get(i)).getString("assistOptionEname");
			// String
			// assistOptionName=((JSONObject)assistNames.get(i)).getString("assistOptionName");
			String userCode = realJs.getString(assistOptionEname + "Opt");
			String userName = realJs.getString(assistOptionEname + "OptName");
			String assistTime = realJs.getString(assistOptionEname + "Hour");
			if (StringUtils.isNotEmpty(userCode)) {
				param.put("id", id);
				param.put("matUsageId", matUsageId);
				param.put("assistTime", assistTime);
				param.put("assistOptionEname", assistOptionEname);
				param.put("operator", operator);
				param.put("userCode", userCode);
				param.put("userName", userName);
				param.put("processCode", processCode);
				matUsageService.insertProAssistDetail(param);
			}
		}
		map.put("id", assistId);
		map.put("matUsageId", matUsageId);
		map.put("assistTime", realJs.getString("assistHour"));
		map.put("operator", operator);
		map.put("userCode", realJs.getString("assistHourOpt"));
		map.put("userName", realJs.getString("assistHourOptName"));
		if (StringUtils.isNotEmpty(realJs.getString("assistHourOpt"))) {
			matUsageService.insertAssistDetail(map);
		}
		result.put("success", true);
		result.put("message", "保存成功");
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/saveValue", method = RequestMethod.POST)
	public JSON saveValue(HttpServletRequest request, HttpServletResponse response, @RequestParam String workOrderNo) {
		String time = request.getParameter("time");
		String temperature = request.getParameter("temperature");
		Map<String, String> param = new HashMap<String, String>();
		param.put("time", time);
		param.put("temperature", temperature);
		param.put("workOrderNo", workOrderNo);
		workOrderService.saveTimeAndTempValue(param);
		return JSONArrayUtils.ajaxJsonResponse(true, "导入成功！");
	}

	@ResponseBody
	@RequestMapping(value = "/getMaterialInventory", method = RequestMethod.POST)
	public JSONObject getMaterialInventory(@RequestParam String workOrderNo, @RequestParam String userCode) {
		JSONObject result = new JSONObject();
		List<MaterialMng> list = materialMngService.getByWorkOrderNo(workOrderNo);
		result.put("list", list);
		WorkOrder o = workOrderService.getByWorkOrderNO(workOrderNo);
		result.put("MaterialStatus", o.getMatStatus());
		Employee e = employeeService.getByUserCode(userCode);
		result.put("userName", e.getName());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "getBuLiaoQuntity", method = RequestMethod.POST)
	public JSONObject getBuLiaoQuntity(@RequestParam String workOrderNo, @RequestParam String userCode) {
		JSONObject result = new JSONObject();
		List<MaterialMng> list = materialMngService.getBuLiaoByWorkOrderNo(workOrderNo);
		result.put("list", list);
		Employee e = employeeService.getByUserCode(userCode);
		result.put("userName", e.getName());
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "updateSuppMaterialInfo", method = RequestMethod.POST)
	public JSON updateSuppMaterialInfo(@RequestParam String buLiaoInfo, @RequestParam String workOrderNo) {
		List<SupplementMaterial> list = JSONArray.parseArray(buLiaoInfo, SupplementMaterial.class);
		for (SupplementMaterial b : list) {
			b.setId(UUID.randomUUID().toString());
			b.setStatus(MaterialStatus.MAT_BORROW);
			terminalService.saveSupplementMaterial(b);
		}
		WorkOrder w = workOrderService.getByWorkOrderNO(workOrderNo);
		w.setMatStatus(MaterialStatus.MAT_BORROW);
		workOrderService.update(w);
		return JSONArrayUtils.ajaxJsonResponse(true, "成功");
	}

	/**
	 * @Title: initTerminalSingleAllData
	 * @Description: TODO(获取进入终端页面的所有数据：提高效率，减少请求)
	 * @author: DinXintao
	 * @version V1.0
	 * @date: 2016年3月17日 上午11:14:41
	 * @param: section 工段
	 * @param: equipCode 设备编码
	 * @param: workOrderNo 生产单号
	 * @return: Map<String,List<Object>>
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "initTerminalSingleAllData", method = RequestMethod.GET)
	public Map<String, List<Object>> initTerminalSingleAllData(@RequestParam String section, @RequestParam String equipCode,
			@RequestParam String workOrderNo) {
		/**
		 * 请求返回内容 <br/>
		 * 1、workOrder: 生产单信息<br/>
		 * 2、taskInfoList: 主任务信息<br/>
		 * 3、materialList: 物料需求<br/>
		 * 4、receiptList: 操作参数<br/>
		 * 5、processQcList: 生产要求<br/>
		 * 6、materialPlanList: 物料计划(设备所有的)<br/>
		 * 7、dailyCheckList: 质检信息<br/>
		 * 8、mesClientManEquip: 设备上的信息，包括采集长度，剩余长度等
		 * */
		
		Map map = new HashMap();
		
		Date a = new Date();
		WorkOrder workOrder = workOrderService.getByWorkOrderNO(workOrderNo);
		map.put("workOrder", workOrder); // 1
		
		Date b = new Date();
		System.err.println("workOrder========================"+ (b.getTime() - a.getTime() ));
		List<Map<String, String>> taskInfoList = customerOrderItemService.showWorkOrderDetailCommon(workOrderNo);
		map.put("taskInfoList", taskInfoList); // 2
		

		Date c = new Date();
		System.err.println("taskInfoList========================" + (c.getTime() - b.getTime()));
		
		map.put("materialList", this.getMaterialGrid(workOrderNo,workOrder,taskInfoList)); // 3
		
		Date d = new Date();
		System.err.println("materialList========================" + (d.getTime() - c.getTime()));
		
		map.put("receiptList", processReceiptService.getByWorkOrderNo(workOrderNo, equipCode)); // 4
		
		Date e = new Date();
		System.err.println("receiptList========================" + (e.getTime() - d.getTime()));
		
		map.put("processQcList", processQcService.getByWorkOrderNo(workOrderNo)); // 5
		
		Date f = new Date();
		System.err.println("processQcList========================" + (f.getTime() - e.getTime()));
		
		map.put("materialPlanList", new ArrayList<MaterialRequirementPlan>());
				//materialRequirementPlanService.getMapRByEquipCode(equipCode)); // 6
		
		Date g = new Date();
		System.err.println("materialPlanList========================" + (g.getTime() - f.getTime()));
		
		map.put("dailyCheckList", maintainRecordService.dailyCheck(equipCode, workOrderNo)); // 7
		
		Date h = new Date();
		System.err.println("dailyCheckList========================" + (h.getTime() - g.getTime()));
		
		map.put("mesClientManEquip", mesClientManEqipService.findByMesClientMac(equipCode)); // 8
		
		Date i = new Date();
		System.err.println("mesClientManEquip========================" + (i.getTime() - h.getTime()));
		
		System.err.println("totla========================" + (i.getTime() - a.getTime()));
		
		
		return map;
	}

	/**
	 * 多终端界面刷新
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "refresh", method = RequestMethod.GET)
	public List<MesClientEqipInfo> refresh(HttpServletRequest request, HttpServletResponse response) {
		CookieMachineResolver cookieMachineResolver = new CookieMachineResolver();
		String mac = cookieMachineResolver.getMac(request, response);
		String ip = cookieMachineResolver.getIp(request, response);
		return mesClientManEqipService.getByMesClientMac(mac, ip, true);
	}

	/**
	 * @Title: refresh
	 * @Description: TODO(具体终端界面刷新)
	 * @author: DinXintao
	 * @version V1.0
	 * @date: 2016年3月17日 上午11:22:54
	 * @param: equipCode 设备编码
	 * @param: workOrderNo 生产单号
	 * @return: Map<String,List<Object>>
	 * @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	@RequestMapping(value = "refreshSingle", method = RequestMethod.GET)
	public Map<String, List<Object>> refreshSingle(@RequestParam String equipCode, @RequestParam String workOrderNo) {

		/**
		 * 请求返回内容 <br/>
		 * 1、workOrder: 生产单信息<br/>
		 * 2、taskInfoList: 主任务信息<br/>
		 * 3、materialList: 物料需求<br/>
		 * 4、receiptList: 操作参数<br/>
		 * 5、processQcList: 生产要求<br/>
		 * 6、materialPlanList: 物料计划(设备所有的)<br/>
		 * 7、dailyCheckList: 质检信息<br/>
		 * 8、mesClientManEquip: 设备上的信息，包括采集长度，剩余长度等
		 * */
		/*
		Map map = new HashMap();
		// map.put("workOrder", workOrderService.getByWorkOrderNO(workOrderNo));
		// // 1
		map.put("taskInfoList", customerOrderItemService.showWorkOrderDetailCommon(workOrderNo)); // 2
		// map.put("materialList", this.getMaterialGrid(workOrderNo)); // 3
		map.put("receiptList", processReceiptService.getByWorkOrderNo(workOrderNo, equipCode)); // 4
		// map.put("processQcList",
		// processQcService.getByWorkOrderNo(workOrderNo)); // 5
		// map.put("materialPlanList",
		// materialRequirementPlanService.getMapRByEquipCode(equipCode)); // 6
		// map.put("dailyCheckList", maintainRecordService.dailyCheck(equipCode,
		// workOrderNo)); // 7
		map.put("mesClientManEquip", mesClientManEqipService.findByMesClientMac(equipCode)); // 8
		
		
		return map;
		*/
		return workOrderService.refreshSingle(equipCode,workOrderNo);

	}

	/**
	 * @Title: getMaterialGrid
	 * @Description: TODO(获取生产单的物料需求)
	 * @author: DinXintao
	 * @version V1.0
	 * @date: 2016年3月17日 上午11:12:23
	 * @param: workOrderNo 生产单号
	 * @return: List<ProcessInOut>
	 * @throws
	 */
	private List<ProcessInOut> getMaterialGrid(String workOrderNo,WorkOrder workOrder,List<Map<String, String>> taskInfoList) {
		List<ProcessInOut> rows = processInOutService.getInByWorkOrderNo(workOrderNo);
		List<RealCost> costList = realCostService.getByWorkOrderNO(workOrderNo);
		Boolean bIsJacketHasBraiding = false;
		if(("Jacket-Extrusion".equals(workOrder.getProcessCode())
			|| "wrapping_ht".equals(workOrder.getProcessCode()))
			&& !CollectionUtils.isEmpty(workOrderService.getWorkOrderBraidingArray(workOrderNo))
				)
		{// 护套（护套-挤出、铠装后绕包）&有编织（Braiding）
			bIsJacketHasBraiding = true;
		}
		outter: for (ProcessInOut in : rows) {
			if(in.getMatType() == MatType.SEMI_FINISHED_PRODUCT)
			{// 如果是半成品
				if(bIsJacketHasBraiding)
				{
					in.setMatName("编织绕包半成品");
				}
				if(!CollectionUtils.isEmpty(taskInfoList))
				{
					Map<String, String> taskInfoMap = taskInfoList.get(0);
					// 型号+' '+规格
					in.setMatCode(getMapString(taskInfoMap, "PRODUCTTYPE") + " " + getMapString(taskInfoMap, "PRODUCTSPEC"));
				}
			}
			boolean flg = true;
			for (RealCost cost : costList) {
				if (StringUtils.equalsIgnoreCase(cost.getMatCode(), in.getMatCode())) {
					in.setHasPutIn(true);
					flg = false;
					continue outter;
				}
			}
			if (flg) {
				in.setHasPutIn(false);
			}
		}
		return rows;
	}

	@Resource
	public AttachmentService attachmentService;

	/**
	 * @Title: showFileList
	 * @Description: TODO(获取关联的所有附件)
	 * @author: DinXintao
	 * @version V1.0
	 * @date: 2016年3月17日 上午11:12:29
	 * @param: request 请求
	 * @param: response 响应
	 * @param: refId 附件表关联主键
	 * @throws: IOException
	 * @return: List<Attachment>
	 * @throws
	 */
	@RequestMapping(value = "/showFileList", method = RequestMethod.GET)
	@ResponseBody
	public List<Attachment> showFileList(HttpServletRequest request, HttpServletResponse response, @RequestParam String refId) throws IOException {
		Attachment findParams = new Attachment();
		findParams.setRefId(refId);
		return attachmentService.findByObj(findParams);
	}

	/**
	 * @Title: showFile
	 * @Description: TODO(获取图片流)
	 * @author: DinXintao
	 * @version V1.0
	 * @date: 2016年3月17日 上午11:12:34
	 * @param: request 请求
	 * @param: response 响应
	 * @param: id 附件表主键
	 * @param: refId 附件表关联主键
	 * @throws: IOException
	 * @return: JSON
	 * @throws
	 */
	@RequestMapping(value = "/showFile", method = RequestMethod.GET)
	@ResponseBody
	public JSON showFile(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String id,
			@RequestParam(required = false) String refId) throws IOException {
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			if (StringUtils.isNotEmpty(id)) {
				attachmentService.downLoad(os, id);
			} else if (StringUtils.isNotEmpty(refId)) {
				attachmentService.downLoadOne(os, refId);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return JSONArrayUtils.ajaxJsonResponse(false, "文件读取错误");
		} finally {
			if (null != os) {
				os.close();
			}
		}
		return JSONArrayUtils.ajaxJsonResponse(true, "");
	}

	/**
	 * 查询编织机物料所需半成品对应的生产单信息
	 */

	@ResponseBody
	@RequestMapping(value = "isWorkOrderNo", method = RequestMethod.GET)
	public List<Map<String, Object>> isWorkOrderNo(HttpServletRequest request, HttpServletResponse response, @RequestParam String barCode) {
		List<Map<String, Object>> rows = reportService.createWoNoInfo(barCode);
		return rows;
	}

	@ResponseBody
	@RequestMapping(value = "queryWorkOrderNoInfo/{woNo}", method = RequestMethod.GET)
	public List<Map<String, Object>> queryWorkOrderNo(@PathVariable String woNo) {
		List<Map<String, Object>> rows = reportService.createWoNoInfo(woNo);
		return rows;
	}
	
	@ResponseBody
	@RequestMapping(value = "refreshEquipEvent",method = RequestMethod.GET)
	public JSON refreshEquipEvent(HttpServletRequest request, HttpServletResponse response, @RequestParam(required=false) String equipCode){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("equipCode", equipCode);
		if (StringUtils.isBlank(equipCode)) {
			CookieMachineResolver cookieMachineResolver = new CookieMachineResolver();
			String mac = cookieMachineResolver.getMac(request, response);
			String ip = cookieMachineResolver.getIp(request, response);
			param.put("mac", mac);
			param.put("ip", ip);
		}

		param.put("orgCode", SessionUtils.getUser().getOrgCode());
		map.put("equipEventConfirm", eventInformationService.getPendingEvent(param));
		return JSONArrayUtils.ajaxJsonResponse(true, "", map);
	}
	
	@ResponseBody
	@RequestMapping(value="applyMaterials",method = RequestMethod.POST)
	public JSON applyMaterials(@RequestParam String userCode,@RequestParam String material,
			@RequestParam String materialNum,@RequestParam String equipCode){
		User user = userService.checkUserCodeUnique(userCode);
		if(user==null){
			return JSONArrayUtils.ajaxJsonResponse(false, "用户不存在，请重新输入!");
		}
		String[] materials = material.split(",");
		String[] materialNums = materialNum.split(",");
		for(int i=0;i<materials.length;i++){
			ApplyMat applyMat = new ApplyMat();
			applyMat.setId(UUID.randomUUID().toString());
			applyMat.setEquipCode(equipCode);
			applyMat.setUserCode(userCode);
			applyMat.setApplyQuntity(Double.valueOf(materialNums[i]));
			applyMat.setMatName(materials[i]);
			applyMat.setStatus(MaterialStatus.MAT_DOWN);
			applyMatService.insert(applyMat);
		}
		return JSONArrayUtils.ajaxJsonResponse(true, "要料成功!!");
	}
	
	@ResponseBody
	@RequestMapping(value="getMaterials",method = RequestMethod.GET)
	public List<ApplyMat> getMaterials(@RequestParam String processCode){
		return applyMatService.getMaterials(processCode);
	}
	
    public String getMapString(final Map map, final Object key) {
        String val = MapUtils.getString(map, key);
        return val == null?"":val;
    }

    
    @ResponseBody
	@RequestMapping(value = "issuePrintParam",method = RequestMethod.POST)
	public JSON issuePrintParam(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(required=false) Map<String,String> params){
		debugService.issueParam(params);	
		return JSONArrayUtils.ajaxJsonResponse(true, "");
	}
    

    
    @ResponseBody
	@RequestMapping(value="printerParam",method = RequestMethod.POST)
	public JSONObject printerParam(@RequestParam String outmatdesc){
		JSONObject result = new JSONObject();
		List<OpcParmVO> parmvoList=new ArrayList<OpcParmVO>();
		OpcParmVO o1=new OpcParmVO("PMJ6.PMJ6.W_CompanyName", null);
		OpcParmVO o2=new OpcParmVO("PMJ6.PMJ6.W_Model", null);
		OpcParmVO o3=new OpcParmVO("PMJ6.PMJ6.W_Switch", null);
		parmvoList.add(o1);
		parmvoList.add(o2);
		parmvoList.add(o3);
		opcClient.writeOpcPMJValue(parmvoList);
		parmvoList.clear();
		OpcParmVO o4=new OpcParmVO("PMJ6.PMJ6.W_CompanyName", outmatdesc);
		OpcParmVO o5=new OpcParmVO("PMJ6.PMJ6.W_Model", "1");
		OpcParmVO o6=new OpcParmVO("PMJ6.PMJ6.W_Switch", "1");
		parmvoList.add(o4);
		parmvoList.add(o5);
		parmvoList.add(o6);
		opcClient.writeOpcPMJValue(parmvoList);
		result.put("succcess", true);
		return result;
	}

}
