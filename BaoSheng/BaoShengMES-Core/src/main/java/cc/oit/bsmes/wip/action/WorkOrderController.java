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
package cc.oit.bsmes.wip.action;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.JobConstants;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.CollectionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.job.base.factory.InitSchedulerFactoryBean;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.model.ToolsRequirementPlan;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.pla.service.ToolsRequirementPlanService;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ReportService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2013-12-11 下午1:09:22
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/workOrder")
public class WorkOrderController {

	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private ProductService productService;
	@Resource
	private MaterialRequirementPlanService materialRequirementPlanService;
	@Resource
	private ToolsRequirementPlanService toolsRequirementPlanService;
	@Resource
	private EquipListService equipListService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private ReportService reportService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "workOrder");
		return "wip.workOrder";
	}

	@RequestMapping
	@ResponseBody
	@SuppressWarnings({ "unchecked" })
	public TableView list(HttpServletRequest request, @RequestParam String sort, @RequestParam int page,
			@RequestParam int start, @RequestParam int limit) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// 设置findParams属性
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, Object> requestMap = new HashMap<String, Object>();
		for (String key : parameterMap.keySet()) {
			if (parameterMap.get(key) != null && StringUtils.isNotBlank(parameterMap.get(key)[0])) {

				if (StringUtils.containsIgnoreCase(key, "preStartTime")) {
					// requestMap.put(key, parameterMap.get(key)[0]);

					try {
						requestMap.put(key, df.format(df.parse(parameterMap.get(key)[0])));
					} catch (ParseException e) {
					}
				} else if (StringUtils.equalsIgnoreCase(key, "isDelayed")) {
					if (parameterMap.get(key).length == 1) {
						requestMap.put(key, parameterMap.get(key)[0]);
					}
				} else if (StringUtils.equalsIgnoreCase(key, "processCode")) {
					requestMap.put(key, parameterMap.get(key)[0]);
				} else {
					requestMap.put(key, "%" + parameterMap.get(key)[0] + "%");
				}
			}
		}

		// 查询
		List<WorkOrder> list = workOrderService.findByRequestMap(requestMap, start, limit,
				JSONArray.parseArray(sort, Sort.class));

		/**
		 * ----------------------------------------
		 * 
		 * @audit DingXintao
		 * @date 2014-09-25 临时解决延迟工单黄色显示：后期改为计算改变isDelayed
		 * */
		// Date s = new Date();
		for (WorkOrder wo : list) {
			wo.setIsDelayed(false);
			List<OrderTask> otList = orderTaskService.getByWorkOrderId(wo.getId());
			for (OrderTask ot : otList) {
				if (null != ot.getLatestFinishDate() && ot.getLatestFinishDate().before(ot.getPlanFinishDate())) {
					wo.setIsDelayed(true);
					break;
				}
			}
		}
		// Date e = new Date();
		// System.out.println(e.getTime()-s.getTime());
		// ----------------------------------------

		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(workOrderService.countByRequestMap(requestMap));
		return tableView;
	}

	@ResponseBody
	@RequestMapping(value = "disorderWorkOrder/{equipCode}", method = RequestMethod.GET)
	public TableView getDisorderWorkOrder(@PathVariable("equipCode") String equipCode, @RequestParam String sort,
			@RequestParam int page, @RequestParam int start, @RequestParam int limit) {
		// 查询
		List<WorkOrder> list = workOrderService.getDisorderWorkOrderByEquipCode(equipCode, start, limit,
				JSONArray.parseArray(sort, Sort.class));
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(workOrderService.countDisorderWorkOrderByEquipCode(equipCode));
		return tableView;
	}

	/**
	 * <p>
	 * 生产单状态下拉框组件
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-7-15 11:20:48
	 * @return JSON json格式
	 * */
	@ResponseBody
	@RequestMapping(value = "workOrderStatusCombo", method = RequestMethod.GET)
	public JSON getWorkOrderStatusCombo() {
		JSON result = workOrderService.getWorkOrderStatusCombo();
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "seqWorkOrder/{equipCode}", method = RequestMethod.GET)
	public List<WorkOrder> getSeqWorkOrder(@PathVariable("equipCode") String equipCode) {
		// 查询
		return workOrderService.getSeqWorkOrderByEquipCode(equipCode);
	}

	@ResponseBody
	@RequestMapping(value = "moveToRight", method = RequestMethod.POST)
	public void moveToRight(@RequestParam String workOrderNoList) throws ParseException {
		if (StringUtils.isNotBlank(workOrderNoList)) {
			List<String> workOrderNos = new ArrayList<String>(CollectionUtils.convertToArrayList(StringUtils.split(
					workOrderNoList, WebConstants.REQUEST_PARAM_LIST_SEPARATOR)));
			workOrderService.insertToPriorityByWorkOrders(workOrderNos);
		}
	}

	@ResponseBody
	@RequestMapping(value = "moveToLeft", method = RequestMethod.POST)
	public void moveToLeft(@RequestParam String workOrderNoList) throws ParseException {
		if (StringUtils.isNotBlank(workOrderNoList)) {
			List<String> workOrderNos = new ArrayList<String>(CollectionUtils.convertToArrayList(StringUtils.split(
					workOrderNoList, WebConstants.REQUEST_PARAM_LIST_SEPARATOR)));
			workOrderService.removeFromPriorityByWorkOrders(workOrderNos);
		}
	}

	@ResponseBody
	@RequestMapping(value = "updateSeq", method = RequestMethod.POST)
	public void updateSeq(@RequestParam String updateSeq) {
		workOrderService.updateSeq(updateSeq);
	}

	@ResponseBody
	@RequestMapping(value = "auditWorkOrder", method = RequestMethod.GET)
	public void auditWorkOrder() {
		workOrderService.auditWorkOrder();
	}

	@ResponseBody
	@RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
	public void cancelWorkOrder(HttpServletRequest request) {

		String arrayData = request.getParameter("array");
		List<WorkOrder> woList = new ArrayList<WorkOrder>();
		if (StringUtils.startsWith(arrayData, "[")) {
			woList.addAll(JSONArray.parseArray(arrayData, WorkOrder.class));

		} else {
			WorkOrder wo = JSONObject.parseObject(arrayData, WorkOrder.class);
			woList.add(wo);

		}
		workOrderService.cancelWorkOrder(woList);
	}

	@ResponseBody
	@RequestMapping(value = "/orderTasks/{workOrderId}", method = RequestMethod.GET)
	public TableView sublist(@PathVariable String workOrderId, @RequestParam(required = false) String sort,
			@RequestParam int page, @RequestParam int start, @RequestParam int limit) {
		// 查询
		List<OrderTask> list = workOrderService.getSubListWorkOrderId(workOrderId, start, limit,
				JSONArray.parseArray(sort, Sort.class));
		/**
		 * ----------------------------------------
		 * 
		 * @audit DingXintao
		 * @date 2014-09-25 临时解决延迟工单黄色显示：后期改为计算改变isDelayed
		 * */
		for (OrderTask ot : list) {
			ot.setIsDelayed(false);
			if (ot.getLatestFinishDate() != null && ot.getLatestFinishDate().before(ot.getPlanFinishDate())) {
				ot.setIsDelayed(true);
				break;
			}
		}
		// ----------------------------------------

		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(workOrderService.countSubListWorkOrderId(workOrderId));
		return tableView;
	}

	@ResponseBody
	@RequestMapping(value = "/workOrderReport/{workOrderNo}", method = RequestMethod.GET)
	public TableView workOrderReport(@PathVariable String workOrderNo) {
		List<Report> list = reportService.getByWorkOrder(workOrderNo);
		TableView tableView = new TableView();
		tableView.setRows(list);
		return tableView;
	}

	@ResponseBody
	@RequestMapping(value = "productList/{workOrderNo}", method = RequestMethod.GET)
	public List<Product> getProductListByWorkOrderNO(@PathVariable String workOrderNo) {
		List<Product> result = productService.getByWorkOrderNO(workOrderNo);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "matList/{workOrderNo}", method = RequestMethod.GET)
	public List<MaterialRequirementPlan> getMatListByWorkOrderNO(@PathVariable String workOrderNo) {
		List<MaterialRequirementPlan> result = materialRequirementPlanService.getByWorkOrderNo(workOrderNo);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "toolsList/{workOrderNo}", method = RequestMethod.GET)
	public List<ToolsRequirementPlan> getToolsListByWorkOrderNO(@PathVariable String workOrderNo) {
		List<ToolsRequirementPlan> result = toolsRequirementPlanService.getByWorkOrderNo(workOrderNo);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/reScheduleWorkOrder", method = RequestMethod.POST)
	public JSONObject reScheduleWorkOrder() throws InvocationTargetException, IllegalAccessException {
		// tempService.schedule(SessionUtils.getUser().getOrgCode());
		InitSchedulerFactoryBean initSchedulerFactoryBean = (InitSchedulerFactoryBean) ContextUtils
				.getBean(InitSchedulerFactoryBean.class);
		try {
			initSchedulerFactoryBean.startJob(JobConstants.SHORT_SCHEDULE);
		} catch (Exception e) {
			throw new MESException(e);
		}

		JSONObject result = new JSONObject();
		result.put("succcess", true);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/equipListByProcessId/{processId}", method = RequestMethod.GET)
	public List<EquipList> getEquipListByProcessId(@PathVariable String processId) throws InvocationTargetException,
			IllegalAccessException {
		return equipListService.getByProcessId(processId);
	}

	/**
	 * 
	 * <p>
	 * 设置固定设备
	 * </p>
	 * <p>
	 * 1.删除排序表记录
	 * </p>
	 * <p>
	 * 2.更新pro_dec
	 * </p>
	 * <p>
	 * 3.更新WorkOrder
	 * </p>
	 * <p>
	 * 设置固定设备
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-29 下午4:58:48
	 * @param workOrderNo
	 * @param fixedEquipCode
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "/updateFixedEquip", method = RequestMethod.POST)
	public UpdateResult updateFixedEquip(@RequestParam String workOrderNo, @RequestParam String fixedEquipCode)
			throws InvocationTargetException, IllegalAccessException {
		WorkOrder workOrder = workOrderService.updateFixedEquipByWorkOrderNo(workOrderNo, fixedEquipCode);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(workOrder);
		return updateResult;
	}

	/**
	 * 根据设备编码获取当前加工的产品信息
	 * */
	@ResponseBody
	@RequestMapping(value = "getProductByEquipCode", method = RequestMethod.GET)
	public Map<String, String> getProductByEquipCode(@RequestParam String equipCode) {
		List<Map<String, String>> productList = workOrderService.getProductByEquipCode(equipCode);
		StringBuffer productStr = new StringBuffer();
		for (Map<String, String> product : productList) {
			productStr.append(product.get("CONTRACTNO").substring(product.get("CONTRACTNO").length() - 5)).append("[")
					.append(product.get("OPERATOR")).append("]").append(" - ").append(product.get("PRODUCTTYPE"))
					.append(" ").append(product.get("PRODUCTSPEC")).append(" - ")
					.append(((Object) product.get("CONTRACTLENGTH")).toString()).append("; ");
		}

		EquipInfo equip = equipInfoService.getEquipLineByEquip(equipCode);
		Map<String, String> re = new HashMap<String, String>();
		re.put("product", productStr.toString());
		re.put("status", equip == null ? "" : equip.getStatus().name());
		return re;
	}
}
