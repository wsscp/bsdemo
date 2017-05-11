package cc.oit.bsmes.wip.action;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.MesClientManEqipService;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.model.EventStore;
import cc.oit.bsmes.common.model.ResourcesStore;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.LineChart;
import cc.oit.bsmes.fac.model.StatusHistory;
import cc.oit.bsmes.fac.model.StatusHistoryChart;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.StatusHistoryService;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.dao.ProductProcessWipDAO;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.model.GraphValue;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 设备状态历史
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author Administrator
 * @date 2014-7-21 上午11:40:55
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/realEquipStatusChart")
public class RealEquipStatusChartController {

	@Resource
	private StatusHistoryService statusHistoryService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private MesClientManEqipService mesClientManEqipService;
	@Resource
	private ProcessInOutService processInOutService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private SalesOrderItemService salesOrderItemService;
	@Resource
	private EquipListService equipListService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private ProductProcessWipDAO productProcessWipDAO;

	@RequestMapping(produces = "text/html")
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "realEquipStatusChart");

		String equipCode = request.getParameter("code");
		WorkOrder workOrder = workOrderService.getCurrentByEquipCode(equipCode);
		if (workOrder != null) {
//			AddBaseInfo addBaseInfo = new AddBaseInfo(workOrder).invoke();
//			List<JSONObject> result = addBaseInfo.getResult();
//			model.addAttribute("emphShowInfo", result);
			model.addAttribute("order", workOrder);
			model.addAttribute("contractNo", getContractNo(workOrder.getWorkOrderNo()));
		}
		model.addAttribute("equip", equipInfoService.getEquipLineByEquip(equipCode));
		return "wip.realEquipStatusChart";
	}

	/**
	 * 设备状态监控历史: 时序图-getResource
	 * 
	 * @author DingXintao
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "getResource", method = RequestMethod.GET)
	public ResourcesStore getResource(@RequestParam(required = false) String equipCode,
			@RequestParam(required = false) String startTime) {
		ResourcesStore resourcesStore = new ResourcesStore();
		resourcesStore.setId(equipCode);
		resourcesStore.setName(equipCode);
		return resourcesStore;
	}

	/**
	 * 设备状态监控历史: 时序图-getEvent
	 * 
	 * @author DingXintao
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "getEvent", method = RequestMethod.GET)
	public List<EventStore> getEvent(@RequestParam(required = false) String equipCode,
			@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime) {
		StatusHistory statusHistory = new StatusHistory();
		statusHistory.setOrgCode(statusHistoryService.getOrgCode());
		statusHistory.setEquipCode(equipCode);
		statusHistory.setStartTime(DateUtils.convert(startTime, DateUtils.DATE_TIME_FORMAT));
		statusHistory.setEndTime(DateUtils.convert(endTime, DateUtils.DATE_TIME_FORMAT));
		
		List<EventStore> eventList = statusHistoryService.getEvent(statusHistory);
		return eventList;
	}

	/**
	 * 设备状态监控历史: 状态历史百分比
	 * 
	 * @author DingXintao
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "equipHistoryColumnPercent", method = RequestMethod.GET)
	public Map equipHistoryColumnPercent(@RequestParam String equipCode,
			@RequestParam String startTime, @RequestParam String endTime) throws Exception {
		StatusHistory statusHistory = new StatusHistory();
		statusHistory.setEquipCode(equipCode);
		statusHistory.setOrgCode(statusHistoryService.getOrgCode());
		statusHistory.setStartTime(DateUtils.convert(startTime, DateUtils.DATE_TIME_FORMAT));
		statusHistory.setEndTime(DateUtils.convert(endTime, DateUtils.DATE_TIME_FORMAT));
		
		Map<EquipStatus, StatusHistoryChart> hisCharMap = statusHistoryService.getStatusPercent(statusHistory);
		return this.getResult(hisCharMap);
	}

	/**
	 * 设备状态监控历史: 加工时间分析/设备OEE
	 * @author DingXintao
	 * @return 返回数据格式 [{ name: 'IDEL', data : [{ name : '2011', y : 7.0 }, { name : '2012', y : 6.9 }, ...]}, 
	 * 						{ name: 'IDEL', data : [{ name : '2011', y : 7.0 }, { name : '2012', y : 6.9 }, ..]}, ...]
	 * */
	@ResponseBody
	@RequestMapping(value = "equipHistoryLine", method = RequestMethod.GET)
	public List<LineChart> equipHistoryLine(@RequestParam(required = false) String equipCode,
			@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = false) String type, @RequestParam(required = false) Boolean oEE) {
		StatusHistory statusHistory = new StatusHistory();
		statusHistory.setEquipCode(equipCode);
		statusHistory.setOrgCode(statusHistoryService.getOrgCode());
		statusHistory.setStartTime(DateUtils.convert(startTime, DateUtils.DATE_TIME_FORMAT));
		statusHistory.setEndTime(DateUtils.convert(endTime, DateUtils.DATE_TIME_FORMAT));
		return statusHistoryService.getByTimeAndStatus(statusHistory, type, oEE);
	}
	
	@ResponseBody
	@RequestMapping(value = "equipHistoryYield", method = RequestMethod.GET)
	public List<LineChart> equipHistoryYield(@RequestParam(required = false) String equipCode,
			@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = false) String type, @RequestParam(required = false) Boolean oEE) {
		StatusHistory statusHistory = new StatusHistory();
		statusHistory.setEquipCode(equipCode);
		statusHistory.setOrgCode(statusHistoryService.getOrgCode());
		statusHistory.setStartTime(DateUtils.convert(startTime, DateUtils.DATE_TIME_FORMAT));
		statusHistory.setEndTime(DateUtils.convert(endTime, DateUtils.DATE_TIME_FORMAT));
		return statusHistoryService.getEquipYield(equipCode,statusHistory, type, oEE);
	}
	


	@RequestMapping(value = "{equipCode}", produces = "text/html", method = RequestMethod.GET)
	public String scheduler(HttpServletRequest request, HttpServletResponse response, @PathVariable String equipCode,
			@RequestParam String title, Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "statusChart");
		model.addAttribute("equipCode", equipCode);
		EquipInfo equipInfo = equipInfoService.getByCode(equipCode, equipInfoService.getOrgCode());
		model.addAttribute("equipName", equipInfo.getName());
		if (StringUtils.equals(title, "加工时间统计")) {
			return "wip.statusChart.scheduler";
		} else if (StringUtils.equals(title, "加工时间分析")) {
			return "wip.statusChart.chartLine";
		} else {
			return "wip.statusChart.chartOEE";
		}
	}

	/**
	 * 设备状态监控历史: 状态历史百分比
	 * 
	 * @author DingXintao
	 * 
	 * */
	public Map<String, Object> getResult(Map<EquipStatus, StatusHistoryChart> hisChartMap) throws Exception {
		if (hisChartMap.size() == 0) {
			return null;
		} else {
			Map<String, Object> results = new HashMap<String, Object>();
			//List<String> names = new ArrayList<String>();
			List<GraphValue> columnChart = new ArrayList<GraphValue>();
			List<GraphValue> pieChart = new ArrayList<GraphValue>();

			Iterator<EquipStatus> hiskeys = hisChartMap.keySet().iterator();
			while (hiskeys.hasNext()) {
				EquipStatus hiskey = hiskeys.next();
				StatusHistoryChart hisChart = hisChartMap.get(hiskey);
				Double time = Double
						.parseDouble(new DecimalFormat("#0.00").format(hisChart.getProcess() / 1000 / 60 / 60)); // 将时间秒转换成Double的小时
				GraphValue graphValue = new GraphValue();
				graphValue.setY(time);
				graphValue.setName(hiskey.toString());
						//+ "(" + time + ")");
				if (hiskey == EquipStatus.IN_PROGRESS) {
					graphValue.setColor("#32B168");
				} else if (hiskey == EquipStatus.IN_DEBUG) {
					graphValue.setColor("#57A0CC");
				} else if (hiskey == EquipStatus.IDLE) {
					graphValue.setColor("#CAD638");
				} else if (hiskey == EquipStatus.CLOSED) {
					graphValue.setColor("#C2C6C9");
				} else if (hiskey == EquipStatus.ERROR) {
					graphValue.setColor("#E52E20");
				} else {
					graphValue.setColor("#EEB12B");
				}
				columnChart.add(graphValue);
				if (hisChart.getProcess() > 0) {
					GraphValue clone = (GraphValue) BeanUtils.cloneBean(graphValue);
					clone.setName(hiskey.toString() + "(" + time + ")");
					pieChart.add(clone); // 拷贝的原因：返回json格式处理了，同意对象引用会有冲突
				}
				//names.add(hiskey.toString());
			}

			results.put("pieData", pieChart);
			//results.put("names", names);
			results.put("columnData", columnChart);
			return results;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/exportToXls/{equipCode}/{startDate}/{endDate}", method = RequestMethod.GET)
	public void exportToXls(HttpServletRequest request, HttpServletResponse response, @PathVariable String equipCode,
			@PathVariable String startDate, @PathVariable String endDate) {
		Date start = DateUtils.convert(startDate, DateUtils.DATE_FORMAT);
		Date end = null;
		if (StringUtils.equals(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT).replaceAll("-0", "-"), endDate)) {
			Date date = new Date();
			String endDates = endDate + " " + (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) + ":"
					+ (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()) + ":"
					+ (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds());
			end = DateUtils.convert(endDates, DateUtils.DATE_TIME_FORMAT);
		} else {
			end = DateUtils.convert(endDate + " " + DateUtils.DAYTIME_END, DateUtils.DATE_TIME_FORMAT);
		}

		StatusHistory statusHistory = new StatusHistory();
		statusHistory.setEquipCode(equipCode);
		statusHistory.setStartTime(start);
		statusHistory.setEndTime(end);
		EquipInfo equip = equipInfoService.getByCode(equipCode, equipInfoService.getOrgCode());
		String sheetName = equip.getName() + "状态历史";
		try {
			String fileName = URLEncoder.encode(sheetName, "UTF8") + ".xls";
			String userAgent = request.getHeader("User-Agent").toLowerCase(); // 获取用户代理
			if (userAgent.indexOf("msie") != -1) { // IE浏览器
				fileName = "filename=\"" + fileName + "\"";
			} else if (userAgent.indexOf("mozilla") != -1) { // firefox浏览器
				fileName = "filename*=UTF-8''" + fileName;
			}
			response.reset();
			response.setContentType("application/ms-excel");
			response.setHeader("Content-Disposition", "attachment;" + fileName);
			OutputStream os = response.getOutputStream();
			statusHistoryService.exportToXls(os, sheetName, statusHistory);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getContractNo(String workOrderNo) {
		User user = SessionUtils.getUser();
		List<OrderTask> orderTaskList = orderTaskService.getByWorkOrderNo(workOrderNo, user.getOrgCode());
		if (orderTaskList.size() == 0) {
			return "";
		}
		OrderTask orderTask = orderTaskList.get(0);
		return orderTask.getContractNo();
	}

	private class AddBaseInfo {
		private WorkOrder workOrder;
		private ProductProcess productProcess;
		private List<JSONObject> result;

		public AddBaseInfo(WorkOrder workOrder) {
			this.workOrder = workOrder;
		}

		public ProductProcess getProductProcess() {
			return productProcess;
		}

		public List<JSONObject> getResult() {
			return result;
		}

		public AddBaseInfo invoke() {
			String oldProcessId = productProcessWipDAO.getOldPorcessIdById(workOrder.getProcessId());
			workOrder.setProcessId(oldProcessId);
			List<Map<String, String>> list = mesClientManEqipService.emphReceipt(workOrder.getProcessId(),
					workOrder.getEquipCode());
			productProcess = productProcessService.getById(workOrder.getProcessId());

			result = new ArrayList<JSONObject>();
			JSONObject jsonObject = new JSONObject();
			if (productProcess.getProcessName().contains("火花配套")) {
				// 配套长度
				List<SalesOrderItem> orderItems = salesOrderItemService.getByWorkOrder(workOrder.getWorkOrderNo());
				SalesOrderItem salesOrderItem = orderItems.get(0);
				jsonObject.put("receiptName", "配套长度");
				int pan = (int) (workOrder.getOrderLength() / salesOrderItem.getStandardLength());
				double panLen = workOrder.getOrderLength() / pan;
				jsonObject.put("targetValue", panLen + "*" + pan);
				result.add(jsonObject);

				// 收线盘踞
				EquipList findParams = new EquipList();
				findParams.setEquipCode(workOrder.getEquipCode());
				findParams.setProcessId(workOrder.getProcessId());
				List<EquipList> equipLists = equipListService.getByObj(findParams);
				if (equipLists.size() > 0) {
					if (equipLists.get(0).getSxpj() != null) {
						jsonObject = new JSONObject();
						jsonObject.put("receiptName", "收线盘距");
						jsonObject.put("targetValue", equipLists.get(0).getSxpj() + "mm");
						result.add(jsonObject);
					}
				}

				// 火花带出上一工序 材料 绝缘厚度 颜色 线芯规格

				// 线芯规格
				jsonObject = new JSONObject();
				jsonObject.put("receiptName", "线芯规格");
				jsonObject.put("targetValue", salesOrderItem.getWiresStructure());
				result.add(jsonObject);

				// 颜色
				List<ProcessInOut> inList = processInOutService.getInByProcessId(workOrder.getProcessId());
				for (ProcessInOut inProcess : inList) {
					Mat mat = StaticDataCache.getByMatCode(inProcess.getMatCode());
					if (mat.getMatType() == MatType.SEMI_FINISHED_PRODUCT) {
						jsonObject = new JSONObject();
						jsonObject.put("receiptName", "绝缘颜色");
						jsonObject.put("targetValue", mat.getColor());
						result.add(jsonObject);
					}
				}
				// 材料
				// 绝缘厚度

				ProductProcess findProcess = new ProductProcess();
				findProcess.setNextProcessId(workOrder.getProcessId());
				List<ProductProcess> faProcess = productProcessService.getByObj(findProcess);
				if (faProcess.size() > 0) {
					ProductProcess jyProcess = faProcess.get(0);
					// 材料
					List<ProcessInOut> inOuts = processInOutService.getInByProcessId(jyProcess.getId());
					for (ProcessInOut inOut : inOuts) {
						jsonObject = new JSONObject();
						jsonObject.put("receiptName", StaticDataCache.getByMatCode(inOut.getMatCode()).getMatName());
						jsonObject.put("targetValue", inOut.getMatCode());
						result.add(jsonObject);
					}

					// 绝缘厚度

					ProcessQc pqFindParams = new ProcessQc();
					pqFindParams.setProcessId(jyProcess.getId());
					pqFindParams.setCheckItemName("绝缘厚度");
					List<ProcessQc> qcList = processQcService.getByObj(pqFindParams);
					if (qcList.size() > 0) {
						jsonObject = new JSONObject();
						jsonObject.put("receiptName", "绝缘厚度");
						jsonObject.put("targetValue", qcList.get(0).getItemTargetValue());
						result.add(jsonObject);
					}
				}
			}

			if (productProcess.getProcessName().contains("成缆二层")) {
				List<SalesOrderItem> orderItems = salesOrderItemService.getByWorkOrder(workOrder.getWorkOrderNo());
				SalesOrderItem salesOrderItem = orderItems.get(0);
				jsonObject.put("receiptName", "配套长度");
				int pan = (int) (workOrder.getOrderLength() / salesOrderItem.getStandardLength());
				double panLen = workOrder.getOrderLength() / pan;
				jsonObject.put("targetValue", panLen + "*" + pan);
				result.add(jsonObject);

				jsonObject = new JSONObject();
				jsonObject.put("receiptName", "交货长度");
				panLen = salesOrderItem.getContractLength() / pan;
				jsonObject.put("targetValue", panLen + "*" + pan);
				result.add(jsonObject);
			}

			for (Map<String, String> map : list) {
				jsonObject = new JSONObject();
				jsonObject.put("receiptcode", map.get("RECEIPTCODE"));
				jsonObject.put("receiptName", map.get("RECEIPTNAME"));
				jsonObject.put("targetValue", map.get("TARGETVALUE"));
				result.add(jsonObject);
			}
			return this;
		}
	}

	private Map<String, Object> changeToMap(List<StatusHistoryChart> history, String oEE) {
		Map<String, Object> maps = new HashMap<String, Object>();
		Double[] progressData = new Double[history.size()];
		Double[] debugData = new Double[history.size()];
		Double[] idleData = new Double[history.size()];
		Double[] closeData = new Double[history.size()];
		Double[] errorData = new Double[history.size()];
		Double[] maintData = new Double[history.size()];
		String[] lineNames = new String[history.size()];
		for (int i = 0; i < history.size(); i++) {
			StatusHistoryChart chart = history.get(i);
			lineNames[i] = chart.getName();
			progressData[i] = chart.getProcess();
			debugData[i] = chart.getDebug();
			idleData[i] = chart.getIdle();
			closeData[i] = chart.getClosed();
			errorData[i] = chart.getError();
			maintData[i] = chart.getMaint();
		}
		maps.put("lineNames", lineNames);
		maps.put("progressData", progressData);
		if (StringUtils.isNotBlank(oEE)) {
			return maps;
		}
		maps.put("debugData", debugData);
		maps.put("idleData", idleData);
		maps.put("closeData", closeData);
		maps.put("errorData", errorData);
		maps.put("maintData", maintData);
		return maps;
	}

}
