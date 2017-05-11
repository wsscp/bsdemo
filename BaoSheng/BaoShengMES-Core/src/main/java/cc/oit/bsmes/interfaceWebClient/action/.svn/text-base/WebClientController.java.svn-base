package cc.oit.bsmes.interfaceWebClient.action;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.CamelCaseUtils;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.MD5Utils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.StatusHistory;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.StatusHistoryService;
import cc.oit.bsmes.interfaceWebClient.model.EquipSummary;
import cc.oit.bsmes.interfaceWebClient.model.FacSummary;
import cc.oit.bsmes.interfaceWebClient.model.ProOutput;
import cc.oit.bsmes.interfaceWebClient.model.ProQualitySum;
import cc.oit.bsmes.interfaceWebClient.service.EquipSummaryService;
import cc.oit.bsmes.interfaceWebClient.service.FacSummaryService;
import cc.oit.bsmes.interfaceWebClient.service.ProOutputService;
import cc.oit.bsmes.interfaceWebClient.service.ProQualitySumService;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.OrderOAService;
import cc.oit.bsmes.wip.model.GraphValue;
import cc.oit.bsmes.wip.service.ReportService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.ValueFilter;

/**
 * Created by Jinhy on 2015/3/18.
 */
@Controller
@RequestMapping("/webClient")
public class WebClientController {

	private static String[] equipStatus = { "IN_PROGRESS", "IN_DEBUG", "CLOSED", "IDLE", "ERROR", "IN_MAINTAIN" };
	@Resource
	private UserService userService;
	@Resource
	private FacSummaryService facSummaryService;
	@Resource
	ProOutputService proOutputService;
	@Resource
	ProQualitySumService proQualitySumService;
	@Resource
	private StatusHistoryService statusHistoryService;
	@Resource
	EquipSummaryService equipSummaryService;
	@Resource
	private CustomerOrderService customerOrderService;

	@Resource
	private OrderOAService orderOAService;
	@Resource
	private ReportService reportService;

	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private EquipInfoService equipInfoService;

	@ResponseBody
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public void login(@RequestParam String username, @RequestParam String password, @RequestParam String callback,
			HttpServletResponse response) {
		User user = userService.checkUserCodeUnique(username);
		int loginStatus = Constants.LOGIN_SUCCESS;
		boolean flag = true;
		if (user == null) {
			loginStatus = Constants.LOGIN_USER_NOT_EXISTS;
			flag = false;
		} else if (user.getStatus().equals(WebConstants.NO)) {
			loginStatus = Constants.LOGIN_USER_NOT_EXISTS;
			flag = false;
		} else if (!user.getPassword().equals(MD5Utils.string2MD5(password))) {
			loginStatus = Constants.LOGIN_PASSWORD_WRONG;
			flag = false;
		}
		generateOutput(response, callback, "{'success':" + flag + ",'loginStatus':" + loginStatus + "}");

	}

	// 工厂概况
	@ResponseBody
	@RequestMapping(value = "facSummary", method = RequestMethod.GET)
	public void facSummary(@RequestParam(required = false) String reportDate,
			@RequestParam(required = false) String reportType, @RequestParam(required = false) String callback,
			HttpServletResponse response) throws ParseException {
		Date dDate = null;
		if (StringUtils.isEmpty(reportDate)) {
			dDate = new Date();
		} else {
			dDate = DateUtils.convert(reportDate, DateUtils.DATE_FORMAT);
		}
		dDate = DateUtils.getStartDatetime(dDate);

		Date dFromDate = null;
		Date dtoDate = null;

		if (StringUtils.isEmpty(reportType) || "DAY".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartDatetime(dDate);
			dtoDate = DateUtils.getEndDatetime(dDate);

		} else if ("WEEK".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartWeektime(dDate);
			dtoDate = DateUtils.getEndWeektime(dDate);

		} else if ("MONTH".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartMonthtime(dDate);
			dtoDate = DateUtils.getEndMonthtime(dDate);
		} else {
			return;
		}
		FacSummary parm = new FacSummary();
		parm.setStartTime(dFromDate);
		parm.setEndTime(dtoDate);
		parm.setReportDate(dDate);
		List<FacSummary> alist = facSummaryService.getFacSummary(parm);
		JSONObject obj = new JSONObject();
		// for (FacSummary facSummary : alist) {
		// obj.put(facSummary.getType(), facSummary.getTypeValue());
		// }
		obj.put("result", alist);
		generateOutput(response, callback, obj.toJSONString());

	}

	// 正在运行设备
	@ResponseBody
	@RequestMapping(value = "equip", method = RequestMethod.GET)
	public void equip(@RequestParam(required = false) String callback, HttpServletResponse response,
			@RequestParam(required = false) String status) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orgCode", equipInfoService.getOrgCode());
		if (StringUtils.isNotBlank(status)) {
			param.put("equipStatus", status.trim().split(","));
		}
		List<EquipInfo> list = equipInfoService.getEquipLine(param);
		JSONArray array = new JSONArray();
		for (EquipInfo object : list) {
			JSONObject obj = new JSONObject();
			obj.put("code", object.getCode());
			obj.put("name", object.getName());
			obj.put("length", object.getStatus().toString());
			array.add(obj);
		}
		generateOutput(response, callback, array.toJSONString());
	}

	// 正在生产合同
	@ResponseBody
	@RequestMapping(value = "contractList", method = RequestMethod.GET)
	public void contractList(@RequestParam(required = false) String callback, HttpServletResponse response,
			@RequestParam(required = false) String sort, @RequestParam int start, @RequestParam int limit,
			@RequestParam(required = false) String contractNo) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("orderStatus", new String[] { "IN_PROGRESS" });
		if (StringUtils.isNotBlank(contractNo)) {
			findParams.put("contractNo", contractNo);
		}
		List<CustomerOrder> rows = customerOrderService.findByOrderInfo(findParams, start, limit,
				JSONArray.parseArray(sort, Sort.class));
		JSONArray array = new JSONArray();

		for (CustomerOrder object : rows) {
			JSONObject obj = new JSONObject();
			obj.put("contractNo", object.getContractNo());
			obj.put("customerCompany", object.getCustomerCompany());
			obj.put("customerOaDate", DateUtils.convert(object.getOaDate(), "yyyy-MM-dd"));
			obj.put("operator", object.getOperator());
			array.add(obj);
		}
		generateOutput(response, callback, array.toJSONString());

	}

	// 正在生产产品
	@ResponseBody
	@RequestMapping(value = "productList", method = RequestMethod.GET)
	public void productList(@RequestParam(required = false) String callback, HttpServletResponse response,
			@RequestParam(required = false) String sort, @RequestParam int start, @RequestParam int limit,
			@RequestParam(required = false) String contractNo) {
		OrderOA param = new OrderOA();
		param.setQueryStatus(new String[] { "IN_PROGRESS" });

		User user = SessionUtils.getUser();
		if (user != null) {
			param.setOrgCode(user.getOrgCode());
		}
		if (StringUtils.isNotBlank(contractNo)) {
			param.setContractNo(contractNo);
		}
		List<OrderOA> list = orderOAService.find(param, start, limit, JSONArray.parseArray(sort, Sort.class));
		JSONArray array = new JSONArray();

		for (OrderOA object : list) {
			JSONObject obj = new JSONObject();
			obj.put("contractNo", object.getContractNo());
			obj.put("customerCompany", object.getCustomerCompany());
			obj.put("productType", object.getProductType());
			obj.put("productSpec", object.getProductSpec());
			obj.put("length", object.getOrderLength());
			obj.put("finishedLength", object.getFinishedLength());
			obj.put("finishedRate", String.format("%.0f", object.getFinishedLength() / object.getOrderLength() * 100)
					+ "%");
			obj.put("contractAmount", object.getContractAmount());
			obj.put("oa", DateUtils.convert(object.getOa(), "yyyy-MM-dd"));
			obj.put("operator", object.getOperator());
			array.add(obj);
		}
		generateOutput(response, callback, array.toJSONString());

	}

	// 已经耗费工时
	@ResponseBody
	@RequestMapping(value = "workHour", method = RequestMethod.GET)
	public void workHour(@RequestParam(required = false) String callback, HttpServletResponse response,
			@RequestParam(required = false) String sort, @RequestParam int start, @RequestParam int limit,
			@RequestParam(required = false) String reportDate, @RequestParam(required = false) String reportType,
			@RequestParam(required = false) String workOrderNo) {

		Date dDate = null;
		if (StringUtils.isEmpty(reportDate)) {
			dDate = new Date();
		} else {
			dDate = DateUtils.convert(reportDate, DateUtils.DATE_FORMAT);
		}
		dDate = DateUtils.getStartDatetime(dDate);

		Date dFromDate = null;
		Date dtoDate = null;

		if (StringUtils.isEmpty(reportType) || "DAY".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartDatetime(dDate);
			dtoDate = DateUtils.getEndDatetime(dDate);

		} else if ("WEEK".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartWeektime(dDate);
			dtoDate = DateUtils.getEndWeektime(dDate);

		} else if ("MONTH".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartMonthtime(dDate);
			dtoDate = DateUtils.getEndMonthtime(dDate);

		} else {
			return;
		}
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("startTime", dFromDate);
		findParams.put("endTime", dtoDate);
		if (StringUtils.isNotBlank(workOrderNo)) {
			findParams.put("workOrderNo", workOrderNo);
		}
		// 查询
		List<Map<String, Object>> list = reportService.findForUserProcessTrace(findParams, start, limit,
				JSONArray.parseArray(sort, Sort.class));

		NameFilter filter = new NameFilter() {
			@Override
			public String process(Object object, String name, Object value) {
				return CamelCaseUtils.toCamelCase(name);
			}
		};
		ValueFilter dateFilter = new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				if (value instanceof oracle.sql.TIMESTAMP) {

					try {
						return DateUtils
								.convert(((oracle.sql.TIMESTAMP) value).dateValue(), DateUtils.DATE_TIME_FORMAT);
					} catch (SQLException e) {

					}
				}
				return value;
			}

		};
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.getNameFilters().add(filter);
		serializer.getValueFilters().add(dateFilter);
		serializer.write(list);
		String text = out.toString();
		generateOutput(response, callback, JSONObject.parseArray(text).toJSONString());
	}

	// 已经耗费机器工时
	@ResponseBody
	@RequestMapping(value = "equipHour", method = RequestMethod.GET)
	public void equipHour(@RequestParam(required = false) String callback, HttpServletResponse response,
			@RequestParam(required = false) String sort, @RequestParam int start, @RequestParam int limit,
			@RequestParam(required = false) String reportDate, @RequestParam(required = false) String reportType) {

		Date dDate = null;
		if (StringUtils.isEmpty(reportDate)) {
			dDate = new Date();
		} else {
			dDate = DateUtils.convert(reportDate, DateUtils.DATE_FORMAT);
		}
		dDate = DateUtils.getStartDatetime(dDate);

		Date dFromDate = null;
		Date dtoDate = null;

		if (StringUtils.isEmpty(reportType) || "DAY".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartDatetime(dDate);
			dtoDate = DateUtils.getEndDatetime(dDate);

		} else if ("WEEK".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartWeektime(dDate);
			dtoDate = DateUtils.getEndWeektime(dDate);

		} else if ("MONTH".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartMonthtime(dDate);
			dtoDate = DateUtils.getEndMonthtime(dDate);

		} else {
			return;
		}
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("realStartTime", dFromDate);
		findParams.put("realEndTime", dtoDate);
		// TODO 添加查询条件
		// 设备加工追溯报表：还没有加工的数据不用显示，只显示正在加工或者加工完成的数据
		WorkOrderStatus[] statuss = { WorkOrderStatus.IN_PROGRESS, WorkOrderStatus.FINISHED };
		findParams.put("statuss", statuss);
		// 查询
		List list = workOrderService.findForEquipProcessTrace(findParams, start, limit,
				JSONArray.parseArray(sort, Sort.class));

		NameFilter filter = new NameFilter() {
			@Override
			public String process(Object object, String name, Object value) {
				return CamelCaseUtils.toCamelCase(name);
			}
		};
		ValueFilter dateFilter = new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				if (value instanceof oracle.sql.TIMESTAMP) {

					try {
						return DateUtils
								.convert(((oracle.sql.TIMESTAMP) value).dateValue(), DateUtils.DATE_TIME_FORMAT);
					} catch (SQLException e) {

					}
				}
				return value;
			}

		};

		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.getNameFilters().add(filter);
		serializer.getValueFilters().add(dateFilter);
		serializer.write(list);
		String text = out.toString();
		generateOutput(response, callback, JSONObject.parseArray(text).toJSONString());
	}

	// 已经耗费机器工时
	@ResponseBody
	@RequestMapping(value = "shutdownReason", method = RequestMethod.GET)
	public void shutdownReason(@RequestParam(required = false) String callback, HttpServletResponse response,
			@RequestParam(required = false) String sort, @RequestParam int start, @RequestParam int limit,
			@RequestParam(required = false) String startTime, @RequestParam(required = false) String endTime,
			@RequestParam(required = false) String equipCode) {
		StatusHistory param = new StatusHistory();
		User user = SessionUtils.getUser();
		if (user != null)
			param.setOrgCode(user.getOrgCode());
		if (startTime != null) {
			param.setStartTime(DateUtils.convert(startTime));
		}
		if (endTime != null) {
			param.setEndTime(DateUtils.convert(endTime));
		}
		if (equipCode != null) {
			param.setEquipCode(equipCode);
		}
		List<StatusHistory> list = statusHistoryService.findShutDownReason(param, start, limit, null, true);
		ValueFilter dateFilter = new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				if (value instanceof Date) {

					return DateUtils.convert((Date) value, DateUtils.DATE_TIME_FORMAT);

				}
				return value;
			}
		};
		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.getValueFilters().add(dateFilter);
		serializer.write(list);
		String text = out.toString();
		generateOutput(response, callback, JSONObject.parseArray(text).toJSONString());
	}

	@ResponseBody
	@RequestMapping(value = "chartList", method = RequestMethod.GET)
	public void chartList(@RequestParam(required = false) String reportDate,
			@RequestParam(required = false) String reportType, @RequestParam(required = false) String callback,
			HttpServletResponse response) throws ParseException {
		Date dDate = null;
		if (StringUtils.isEmpty(reportDate)) {
			dDate = new Date();
		} else {
			dDate = DateUtils.convert(reportDate, DateUtils.DATE_FORMAT);
		}
		dDate = DateUtils.getStartDatetime(dDate);

		Date dFromDate = null;
		Date dtoDate = null;

		if (StringUtils.isEmpty(reportType) || "DAY".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartDatetime(dDate);
			dtoDate = DateUtils.getEndDatetime(dDate);

		} else if ("WEEK".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartWeektime(dDate);
			dtoDate = DateUtils.getEndWeektime(dDate);

		} else if ("MONTH".equalsIgnoreCase(reportType)) {
			dFromDate = DateUtils.getStartMonthtime(dDate);
			dtoDate = DateUtils.getEndMonthtime(dDate);
		} else {
			return;
		}
		response.setCharacterEncoding("utf-8");
		JSONArray array = new JSONArray();
		// 产量统计
		array.add(this.proOutput(dFromDate, dtoDate, dDate));
		// 质量问题发生次数统计
		array.add(this.proProQualitySum(dFromDate, dtoDate, dDate));
		// 前五大质量问题发生次数统计
		array.add(this.topProProQuality(dFromDate, dtoDate, dDate));
		// 设备停机原因统计
		array.add(this.shutDownStatistics(dFromDate, dtoDate, dDate));
		// 车间设备状态统计
		array.add(this.equipSummary(dFromDate, dtoDate, dDate));
		// 设备OEE

		array.add(this.equipOEE(dFromDate, dtoDate, dDate));
		generateOutput(response, callback, array.toJSONString());
	}

	public JSONObject proOutput(Date dFromDate, Date dtoDate, Date dDate) throws ParseException {
		ProOutput parm = new ProOutput();
		parm.setStartTime(dFromDate);
		parm.setEndTime(dtoDate);
		parm.setReportDate(dDate);
		List<ProOutput> alist = proOutputService.getProOutput(parm);
		if (alist.size() > 0) {
			String[] processName = new String[alist.size() / 2];
			Double[] finishLength = new Double[alist.size() / 2];
			Double[] unfinishLength = new Double[alist.size() / 2];
			int i = 0;
			int j = 0;
			int k = 0;
			Map<String, String> map = new HashMap<String, String>();
			for (ProOutput pro : alist) {
				if (map.get(pro.getProcessCode()) == null) {
					processName[i] = pro.getProcessName();
					i++;
				}
				if (StringUtils.equals("FINISH_LENGTH", pro.getType())) {
					finishLength[j] = pro.getTypeValue();
					j++;
				}
				if (StringUtils.equals("UNFINISH_LENGTH", pro.getType())) {
					unfinishLength[k] = pro.getTypeValue();
					k++;
				}
				map.put(pro.getProcessCode(), pro.getProcessName());
			}
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("processName", processName);
			jsonObj.put("finishLength", finishLength);
			jsonObj.put("unfinishLength", unfinishLength);
			return jsonObj;
		} else {
			return null;
		}

	}

	public JSONObject proProQualitySum(Date dFromDate, Date dtoDate, Date dDate) throws ParseException {
		ProQualitySum parm = new ProQualitySum();
		parm.setStartTime(dFromDate);
		parm.setEndTime(dtoDate);
		parm.setReportDate(dDate);
		List<ProQualitySum> alist = proQualitySumService.getProProQualitySum(parm);
		String[] proName = new String[alist.size()];
		int[] times = new int[alist.size()];
		for (int i = 0; i < alist.size(); i++) {
			ProQualitySum proQualitySum = alist.get(i);
			times[i] = proQualitySum.getTimes();
			proName[i] = proQualitySum.getProcessName();
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("proName", proName);
		jsonObj.put("times", times);
		return jsonObj;

	}

	public JSONObject topProProQuality(Date dFromDate, Date dtoDate, Date dDate) throws ParseException {
		ProQualitySum parm = new ProQualitySum();
		parm.setStartTime(dFromDate);
		parm.setEndTime(dtoDate);
		parm.setReportDate(dDate);
		List<ProQualitySum> alist = proQualitySumService.getTopProProQuality(parm);
		JSONArray array = new JSONArray();
		int total = 0;
		for (ProQualitySum proQualitySum : alist) {
			JSONObject obj = new JSONObject();
			obj.put("y", proQualitySum.getTimes());
			obj.put("name", proQualitySum.getTypeName());
			total += proQualitySum.getTimes();
			array.add(obj);
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("topProProQuality", array);
		jsonObj.put("total", total);
		return jsonObj;

	}

	public JSONObject shutDownStatistics(Date dFromDate, Date dtoDate, Date dDate) throws ParseException {
		StatusHistory statusHistory = new StatusHistory();
		statusHistory.setStartTime(dFromDate);
		statusHistory.setEndTime(dtoDate);
		User user = SessionUtils.getUser();
		if (user != null)
			statusHistory.setOrgCode(user.getOrgCode());

		Map<String, Object> map = statusHistoryService.getIdleDataByEquipAndLimitTime(statusHistory);
		@SuppressWarnings("unchecked")
		List<GraphValue> alist = (List<GraphValue>) map.get("pieData");

		JSONArray array = new JSONArray();
		if (alist != null) {
			for (GraphValue graphValue : alist) {
				JSONObject obj = new JSONObject();
				obj.put("name", graphValue.getName());
				obj.put("y", graphValue.getY());
				array.add(obj);
			}
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("shutDownStatistics", array);
		return jsonObj;

	}

	public JSONObject equipSummary(Date dFromDate, Date dtoDate, Date dDate) throws ParseException {
		EquipSummary parm = new EquipSummary();
		parm.setStartTime(dFromDate);
		parm.setEndTime(dtoDate);

		List<EquipSummary> alist = equipSummaryService.getEquipSummary(parm);
		JSONArray array = new JSONArray();

		for (EquipSummary object : alist) {
			JSONObject obj = new JSONObject();
			obj.put("name", object.getType().toString());
			obj.put("y", object.getTimes());
			array.add(obj);
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("equipSummary", array);
		return jsonObj;

	}

	public JSONObject equipOEE(Date dFromDate, Date dtoDate, Date dDate) throws ParseException {

		dFromDate = DateUtils.getStartWeektime(dDate);
		dtoDate = DateUtils.getEndWeektime(dDate);
		EquipSummary parm = new EquipSummary();
		parm.setStartTime(dFromDate);
		parm.setEndTime(dtoDate);
		List<EquipSummary> alist = equipSummaryService.getEquipOEE(parm);
		if (alist.size() > 0) {
			String[] dateColumn = new String[alist.size()];
			Double[] oEE = new Double[alist.size()];
			for (int i = 0; i < alist.size(); i++) {
				EquipSummary object = alist.get(i);
				dateColumn[i] = DateUtils.convert(object.getReportDate(), "yy/MM/dd");
				oEE[i] = Double.parseDouble(String.format("%.2f", object.getUsed() / object.getTotal() * 100));
			}
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("dateColumn", dateColumn);
			jsonObj.put("oEE", oEE);
			return jsonObj;
		} else {
			return null;
		}
	}

	public void newOrders(@RequestParam String callback, HttpServletResponse response, @RequestParam String username) {
		// User user = userService.checkUserCodeUnique(username);

	}

	private static class Constants {
		private static final int LOGIN_SUCCESS = 0;
		private static final int LOGIN_PASSWORD_WRONG = 1;
		private static final int LOGIN_USER_NOT_EXISTS = 2;
		private static final int YES = 0;
		private static final int NO = 1;
	}

	private void generateOutput(HttpServletResponse response, String callback, String jsonString) {
		try {
			boolean jsonP = false;
			if (callback != null) {
				jsonP = true;
				response.setContentType("text/javascript");
			} else {
				response.setContentType("application/x-json");
			}
			Writer out = response.getWriter();
			if (jsonP) {
				out.write(callback + "(");
			}
			out.write(jsonString);
			if (jsonP) {
				out.write(");");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
