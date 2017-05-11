package cc.oit.bsmes.wip.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.dao.EmployeeDAO;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.MesClientManEqipService;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.constants.OrderPushStatus;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.QuailityNameConstants;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.constants.WorkOrderOperateType;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.CamelCaseUtils;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.WorkTask;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.WorkTaskService;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.HighPriorityOrderItemProDecService;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.EquipListService;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProcessInformationService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessQcValueService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.dao.FinishOrderItemDAO;
import cc.oit.bsmes.wip.dao.WorkOrderDAO;
import cc.oit.bsmes.wip.dto.MethodReturnDto;
import cc.oit.bsmes.wip.model.FinishOrderItem;
import cc.oit.bsmes.wip.model.OutAttrDesc;
import cc.oit.bsmes.wip.model.RealCost;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.Scrap;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.RealCostService;
import cc.oit.bsmes.wip.service.ReportService;
import cc.oit.bsmes.wip.service.ScrapService;
import cc.oit.bsmes.wip.service.WorkOrderOperateLogService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class WorkOrderServiceImpl extends BaseServiceImpl<WorkOrder> implements WorkOrderService {
	@Resource
	private WorkOrderDAO workOrderDAO;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private MaterialRequirementPlanService materialRequirementPlanService;
	@Resource
	private EquipListService equipListService;
	@Resource
	private WorkTaskService workTaskService;
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private ProcessInformationService processInformationService;
	@Resource
	private ScrapService scrapService;
	@Resource
	private RealCostService realCostService;
	@Resource
	private HighPriorityOrderItemProDecService highPriorityOrderItemProDecService;
	@Resource
	private ReportService reportService;
	@Resource
	private ProcessQcValueService processQcValueService;
	@Resource
	private ProcessInOutService processInOutService;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private MatService matService;
	@Resource
	private WorkOrderOperateLogService workOrderOperateLogService; 
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	
	@Resource
	private ProcessReceiptService processReceiptService;
	
	@Resource
	private MesClientManEqipService mesClientManEqipService;
	
	@Resource
	private FinishOrderItemDAO finishOrderItemDAO;
	
	@Resource
	private EmployeeDAO employeeDAO;
	
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	/**
	 * <p>
	 * 根据页面查询条件查找数据
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-17 下午2:17:16
	 * @param requestMap
	 * @param sortList
	 * @return
	 * @see cc.oit.bsmes.wip.service.WorkOrderService#findByRequestMap
	 */
	@Override
	public List<WorkOrder> findByRequestMap(Map<String, Object> requestMap, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		List<WorkOrder> workOrderList = workOrderDAO.findByRequestMap(requestMap);
		return workOrderList;
	}

	@Override
	public List<WorkOrder> findForExport(JSONObject queryParams) {
		addLikeForParams(queryParams);
		return workOrderDAO.findByRequestMap(queryParams);
	}

	private void addLikeForParams(JSONObject queryParams) {
		if (StringUtils.isNotBlank(queryParams.getString("customerContractNO"))) {
			queryParams.put("customerContractNO", "%" + queryParams.getString("customerContractNO") + "%");
		}

		if (StringUtils.isNotBlank(queryParams.getString("productSpec"))) {
			queryParams.put("productSpec", "%" + queryParams.getString("productSpec") + "%");
		}

		if (StringUtils.isNotBlank(queryParams.getString("productType"))) {
			queryParams.put("productType", "%" + queryParams.getString("productType") + "%");
		}

		if (StringUtils.isNotBlank(queryParams.getString("isDelayed"))
				&& queryParams.getString("isDelayed").length() == 1) {
			queryParams.put("isDelayed", new String[] { queryParams.getString("isDelayed") });

		}
	}

	@Override
	public int countForExport(JSONObject queryParams) {
		addLikeForParams(queryParams);
		return workOrderDAO.countByRequestMap(queryParams);
	}

	@Override
	public List<WorkOrder> getByEquipCodeAndStatus(String equipCode, WorkOrderStatus status) {
		WorkOrder findParams = new WorkOrder();
		findParams.setEquipCode(equipCode);
		findParams.setStatus(status);
		findParams.setOrgCode(getOrgCode());
		List<WorkOrder> workOrderList = workOrderDAO.getByEquipCodeAndStatus(findParams);
		return workOrderList;
	}

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-26 下午4:00:33
	 * @param requestMap
	 * @return
	 * @see cc.oit.bsmes.wip.service.WorkOrderService#countByRequestMap(java.util.Map)
	 */
	@Override
	public Integer countByRequestMap(Map<String, Object> requestMap) {
		return workOrderDAO.countByRequestMap(requestMap);
	}

	/**
	 * <p>
	 * 根据生产单号查询订单任务信息
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-20 下午6:07:05
	 * @param workOrderId
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see cc.oit.bsmes.wip.service.WorkOrderService#getSubListWorkOrderId
	 */
	@Override
	public List<OrderTask> getSubListWorkOrderId(String workOrderId, int start, int limit, List<Sort> sortList) {
		List<OrderTask> orderTaskList = orderTaskService.getByWorkOrderId(workOrderId, start, limit, sortList);
		return orderTaskList;
	}

	@Override
	public int countSubListWorkOrderId(String workOrderId) {
		return orderTaskService.countByWorkOrderId(workOrderId);
	}

	/**
	 * 
	 * <p>
	 * 审核生产单
	 * </p>
	 * <p>
	 * 生成物料需求清单和工装夹具需求清单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-23 下午12:02:18
	 * @return
	 * @see
	 */
	@Transactional(readOnly = false)
	@Override
	public List<WorkOrder> auditWorkOrder() {
		Calendar now = Calendar.getInstance();
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DATE, 1);
		// Map<String, ProductProcess> processCacheMap =
		// StaticDataCache.getProcessMap();
		String auditUserCode = SessionUtils.getUser().getUserCode();
		List<WorkOrder> workOrderList = workOrderDAO.getForAudit(getOrgCode());
		for (WorkOrder wo : workOrderList) {
			if (!WorkOrderStatus.TO_AUDIT.equals(wo.getStatus())) {
				throw new MESException("生产单号:" + wo.getWorkOrderNo() + ",状态为:" + wo.getStatus().toString() + ",无法审核!");
			}
			wo.setStatus(WorkOrderStatus.TO_DO);
			wo.setAuditTime(now.getTime());
			wo.setAuditUserCode(auditUserCode);

			ProductProcess process = StaticDataCache.getProcessByProcessId(wo.getProcessId());

			// 根据工序ID查询出工序所有投入的物料
			List<ProcessInOut> inList = StaticDataCache.getByProcessId(process.getId());

			// 计算物料需求
			List<MaterialRequirementPlan> mrpResultList = calculatorMRP(process, inList, wo,
					new BigDecimal(wo.getOrderLength()));
			materialRequirementPlanService.insert(mrpResultList);

			// 计算工装夹具需求 (已写入存储过程)

			// 24小时内的生产单锁定
			String lock = (wo.getPreStartTime().before(tomorrow.getTime()) || wo.getPreStartTime().equals(
					tomorrow.getTime())) ? "1" : "0";

			workOrderDAO.auditWorkOrder(wo.getId(), auditUserCode, lock, wo.getPreStartTime(),
					process.getProcessCode(), wo.getOrgCode(), process.getId());
		}
		update(workOrderList);
		return workOrderList;
	}

	/**
	 * 
	 * <p>
	 * 计算物料需求
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-23 下午4:42:25
	 */
	private List<MaterialRequirementPlan> calculatorMRP(ProductProcess process, List<ProcessInOut> inList,
			WorkOrder wo, BigDecimal length) {
		List<MaterialRequirementPlan> result = new ArrayList<MaterialRequirementPlan>();
		for (ProcessInOut in : inList) {
			// @edit DingXintao <p>只应该存入原材料数据，即T_INV_MAT 中类型为
			// MATERIALS的数据，其他半成品不需要存入在计算表中</p>
			if (!MatType.MATERIALS.equals(in.getMat().getMatType())) {
				continue;
			}
			BigDecimal totalCount = length.multiply(new BigDecimal(in.getQuantity())).divide(new BigDecimal(1000), 2,
					BigDecimal.ROUND_HALF_UP);
			MaterialRequirementPlan mrp = new MaterialRequirementPlan();
			mrp.setOrgCode(wo.getOrgCode());
			mrp.setMatCode(in.getMatCode());
			mrp.setProcessCode(process.getProcessCode());
			mrp.setPlanDate(wo.getPreStartTime());
			mrp.setStatus(MaterialStatus.UNAUDITED);
			mrp.setQuantity(totalCount.doubleValue());
			mrp.setWorkOrderId(wo.getId());
			mrp.setUnit(in.getUnit());
			mrp.setEquipCode(wo.getEquipCode());
			result.add(mrp);
		}
		return result;
	}

	/**
	 * TODO 同一天,同一个生产单的工装夹具只需要一个
	 * <p>
	 * 计算工装夹具需求
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-23 下午4:42:10
	 */
	// private void calculatorToolsRP(ProductProcess process, List<EquipList>
	// tools, WorkOrder wo,List<ToolsRequirementPlan> reqList, Date reqDate){
	// reqList.clear();
	// for(EquipList tool:tools){
	// ToolsRequirementPlan trp=new ToolsRequirementPlan();
	// trp.setTooles(tool.getEquipCode());
	// trp.setProcessCode(process.getProcessCode());
	// trp.setPlanDate(reqDate);
	// trp.setQuanyity(1);
	// trp.setOrgCode(wo.getOrgCode());
	// trp.setStatus(MaterialStatus.AUDITED);
	// trp.setWorkOrderId(wo.getId());
	// reqList.add(trp);
	// }
	// }

	/**
	 * 
	 * <p>
	 * 取消生产单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-23 下午12:02:18
	 * @param workOrderList
	 * @return
	 * @see
	 */
	@Transactional(readOnly = false)
	@Override
	public void cancelWorkOrder(List<WorkOrder> workOrderList) {
		List<String> ids = new ArrayList<String>();
		for (WorkOrder wo : workOrderList) {
			ids.add(wo.getId());
		}
		workOrderList = workOrderDAO.getByIds(ids);

		for (WorkOrder wo : workOrderList) {
			// if(!WorkOrderStatus.CANCELED.equals(wo.getStatus())){
			// throw new
			// MESException("生产单号:"+wo.getWorkOrderNo()+",状态为:"+wo.getStatus().toString()+",不需要取消!");
			// }
			workOrderDAO.cancelByWorkOrderNo(wo.getWorkOrderNo(), SessionUtils.getUser().getUserCode());
		}

	}

	@Override
	public WorkOrder getCurrentByEquipCode(String equipCode) {
		if (equipCode == null) {
			return null;
		}
		return workOrderDAO.getCurrentByEquipCode(equipCode, getOrgCode());
	}

	@Override
	public List<WorkOrder> getRecentByEquipCode(String equipCode, String status) {
		List<WorkOrder> list = null;
		if (StringUtils.equalsIgnoreCase(status, "TO_DO")) {
			list = workOrderDAO.getRecentByEquipCode(equipCode, getOrgCode());
			list.add(0, getCurrentByEquipCode(equipCode)); // 当前生产订单
		} else {
			list = workOrderDAO.getFinishWorkOrderForTerminal(equipCode, getOrgCode());
		}
		for (WorkOrder workOrder : list) {
			ProductProcess productProcess = StaticDataCache.getProcessByProcessId(workOrder.getProcessId());
			ProductCrafts crafts = StaticDataCache.getCrafts(productProcess.getProductCraftsId());
			Product product = StaticDataCache.getProductMap().get(crafts.getProductCode());
			workOrder.setProductType(product.getProductType());
			workOrder.setProductSpec(product.getProductSpec());
			/*
			 * Mat outMat = StaticDataCache.getMatMap().get(
			 * workOrder.getHalfProductCode()); if
			 * (StringUtils.isNotBlank(outMat.getMatSection())) {
			 * workOrder.setOutProductColor(outMat.getColor() + "(" +
			 * outMat.getMatSection() + ")"); } else {
			 * workOrder.setOutProductColor(outMat.getColor()); }
			 */
		}
		return list;
	}

	@Transactional(readOnly = false)
	@Override
	public void upWorkOrder(String equipCode, String workOrderId) throws ParseException {
		List<WorkOrder> list = getByEquipCodeAndStatus(equipCode, WorkOrderStatus.TO_AUDIT);
		WorkOrder preWorkOrder = null;
		boolean flg = false;
		Date endTime = null;
		Calendar c = Calendar.getInstance();
		for (WorkOrder wo : list) {
			if (wo.getId().equalsIgnoreCase(workOrderId) || flg) {
				flg = true;
				if (preWorkOrder != null) {
					Date startDate = preWorkOrder.getPreEndTime();

					EquipInfo equip = equipInfoService.getByCode(equipCode, SessionUtils.getUser().getOrgCode());
					List<EquipCalendar> equipCalendars = equip.getEquipCalendar();
					Iterator<EquipCalendar> equipCalendarsIterator = equipCalendars.iterator();

					List<OrderTask> orderTasks = orderTaskService.getByWorkOrderId(workOrderId);
					ListIterator<OrderTask> orderTaskIterator = orderTasks.listIterator();
					while (orderTaskIterator.hasNext()) {
						OrderTask ot = orderTaskIterator.next();
						BigDecimal taskLength = new BigDecimal(ot.getTaskLength());
						endTime = null;
						EquipList equipListInfo = equipListService.getByProcessIdAndEquipCode(ot.getProcessId(),
								equipCode);
						c.setTime(startDate);
						endTime = matcherEquipCalendars(taskLength, c.getTime(), equipListInfo, equipCalendarsIterator);
						if (endTime != null) {
							BigDecimal equipCapacity = new BigDecimal(equipListInfo.getEquipCapacity());
							BigDecimal useTime = taskLength.divide(equipCapacity);
							c.setTime(endTime);
							c.add(Calendar.SECOND, -useTime.intValue());
							Date time = c.getTime();
							if (wo.getId().equalsIgnoreCase(workOrderId)) {
								if (time.before(ot.getPlanStartDate())
										&& (time.after(startDate) || time.equals(startDate))) {
									startDate = c.getTime();
									ot.setPlanStartDate(startDate);
									ot.setPlanFinishDate(endTime);
									orderTaskService.update(ot);
									WorkTask wt = workTaskService.getByEquipCodeAndOrderItemProDecId(equipCode,
											ot.getOrderItemProDecId());
									wt.setWorkStartTime(startDate);
									wt.setWorkEndTime(endTime);
									workTaskService.update(wt);

								} else {
									throw new MESException("wip.workOrder.changeOrderError");
								}
							} else {
								startDate = c.getTime();
								ot.setPlanStartDate(startDate);
								ot.setPlanFinishDate(endTime);
								orderTaskService.update(ot);
								WorkTask wt = workTaskService.getByEquipCodeAndOrderItemProDecId(equipCode,
										ot.getOrderItemProDecId());
								wt.setWorkStartTime(startDate);
								wt.setWorkEndTime(endTime);
								workTaskService.update(wt);
							}
						} else {
							throw new MESException("wip.workOrder.changeOrderError");
						}

					}
					while (orderTaskIterator.hasPrevious()) {
						wo.setPreStartTime(orderTaskIterator.previous().getPlanStartDate());
					}
					wo.setPreEndTime(endTime);
					update(wo);
				}
			}
			preWorkOrder = wo;
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void downWorkOrder(String equipCode, String workOrderId) throws ParseException {

		WorkOrder preWorkOrder = null;
		boolean flg = false;
		Date endTime = null;
		Calendar c = Calendar.getInstance();
		List<WorkOrder> workOrderlist = getByEquipCodeAndStatus(equipCode, WorkOrderStatus.TO_AUDIT);
		ListIterator<WorkOrder> workOrderIterator = workOrderlist.listIterator();
		while (workOrderIterator.hasNext()) {
			WorkOrder wo = workOrderIterator.next();
			if (wo.getId().equalsIgnoreCase(workOrderId) || flg) {

				Date startDate;
				if (preWorkOrder != null) {
					startDate = preWorkOrder.getPreEndTime();
				} else {
					startDate = wo.getPreStartTime();
				}
				if (wo.getId().equalsIgnoreCase(workOrderId) && !flg) {
					WorkOrder tmp = wo;
					workOrderIterator.remove();
					wo = workOrderIterator.next();
					workOrderIterator.add(tmp);
				}
				flg = true;

				EquipInfo equip = equipInfoService.getByCode(equipCode, SessionUtils.getUser().getOrgCode());
				List<EquipCalendar> equipCalendars = equip.getEquipCalendar();
				Iterator<EquipCalendar> equipCalendarsIterator = equipCalendars.iterator();

				List<OrderTask> orderTasks = orderTaskService.getByWorkOrderId(workOrderId);
				ListIterator<OrderTask> orderTaskIterator = orderTasks.listIterator();
				while (orderTaskIterator.hasNext()) {
					OrderTask ot = orderTaskIterator.next();
					BigDecimal taskLength = new BigDecimal(ot.getTaskLength());
					endTime = null;
					EquipList equipListInfo = equipListService.getByProcessIdAndEquipCode(ot.getProcessId(), equipCode);
					c.setTime(startDate);
					endTime = matcherEquipCalendars(taskLength, c.getTime(), equipListInfo, equipCalendarsIterator);
					if (endTime != null) {
						BigDecimal equipCapacity = new BigDecimal(equipListInfo.getEquipCapacity());
						BigDecimal useTime = taskLength.divide(equipCapacity);
						c.setTime(endTime);
						c.add(Calendar.SECOND, -useTime.intValue());

						startDate = c.getTime();
						ot.setPlanStartDate(startDate);
						ot.setPlanFinishDate(endTime);
						orderTaskService.update(ot);
						WorkTask wt = workTaskService.getByEquipCodeAndOrderItemProDecId(equipCode,
								ot.getOrderItemProDecId());
						wt.setWorkStartTime(startDate);
						wt.setWorkEndTime(endTime);
						workTaskService.update(wt);
					} else {
						throw new MESException("wip.workOrder.changeOrderError");
					}

				}
				while (orderTaskIterator.hasPrevious()) {
					wo.setPreStartTime(orderTaskIterator.previous().getPlanStartDate());
				}
				wo.setPreEndTime(endTime);
				update(wo);

			}
			preWorkOrder = wo;
		}

	}

	private Date matcherEquipCalendars(BigDecimal length, Date startDate, EquipList equipListInfo,
			Iterator<EquipCalendar> equipCalendarsIterator) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		// Iterator<EquipCalendar> equipCalendarsIterator =
		// equipCalendars.iterator();

		while (equipCalendarsIterator.hasNext()) {
			EquipCalendar ec = equipCalendarsIterator.next();
			Date ecDate = df.parse(ec.getDateOfWork());
			if (ecDate.equals(df.parse(df.format(startDate))) || ecDate.after(df.parse(df.format(startDate)))) {
				List<EquipCalShift> equipCalShiftList = ec.getEquipCalShift();
				Iterator<EquipCalShift> equipCalShiftIterator = equipCalShiftList.iterator();
				if (equipCalShiftIterator.hasNext()) {
					Date endTime = matcherEquipCalendarsShift(length, calendar, equipListInfo, equipCalShiftIterator);
					if (endTime != null) {
						return endTime;
					}
				}
			}

		}
		return null;
	}

	private Date matcherEquipCalendarsShift(BigDecimal length, Calendar start, EquipList equipListInfo,
			Iterator<EquipCalShift> equipCalShiftIterator) throws ParseException {

		// TODO 设备生产能力 米/秒
		BigDecimal equipCapacity = new BigDecimal(equipListInfo.getEquipCapacity());
		if (equipCalShiftIterator.hasNext()) {
			EquipCalShift ecs = equipCalShiftIterator.next();

			Date startTime = df2.parse(df.format(start.getTime()) + " " + ecs.getShiftStartTime());
			Date endTime = df2.parse(df.format(start.getTime()) + " " + ecs.getShiftEndTime());

			if ((startTime.before(start.getTime()) || startTime.equals(start.getTime()))
					&& endTime.after(start.getTime())) {

				// 完成任务需要用时 = 任务长度/设备生产能力
				BigDecimal usedTime = length.divide(equipCapacity);
				start.add(Calendar.SECOND, usedTime.intValue());
				if (endTime.before(start.getTime()) || endTime.equals(start.getTime())) {
					return start.getTime();
				} else {
					// TODO 加班时间计算
					Calendar end = Calendar.getInstance();
					end.setTime(endTime);
					end.add(Calendar.SECOND, getOverTime());

					// 判断加班是否能够完成
					if (end.getTime().before(start.getTime()) || end.getTime().equals(start.getTime())) {
						return start.getTime();
					} else if (equipCalShiftIterator.hasNext()) {
						// 剩余任务长度=总任务长度-(这个班次时间+加班时间能完成的任务长度)
						BigDecimal totalTime = new BigDecimal(endTime.getTime() - start.getTime().getTime())
								.divide(new BigDecimal(1000));
						BigDecimal lastlength = length.subtract(totalTime.multiply(totalTime));
						// 判断下一个班次是否连续,并且是否可以完成剩余任务
						return matcherEquipCalendarsShift(lastlength, end, equipListInfo, equipCalShiftIterator);
					} else {
						return null;
					}
				}

			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	// 获取加班时间
	private int getOverTime() {
		// TODO 加班时间计算
		return 0;
	}

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-19 上午10:23:53
	 * @param workOrderNO
	 * @param userCode
	 * @param weight
	 * @see cc.oit.bsmes.wip.service.WorkOrderService#productWeightSave(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Transactional(readOnly = false)
	@Override
	public void productWeightSave(String workOrderNO, String userCode, String weight) {
		processQcService.insertByWorkOrderNO(workOrderNO, weight, QuailityNameConstants.OUT_WEIGHT, userCode);
	}

	/**
	 * <p>
	 * 废料称重
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-19 下午4:21:45
	 * @param matCode
	 * @param workOrderNO
	 * @param userCode
	 * @param weight
	 * @see cc.oit.bsmes.wip.service.WorkOrderService#scrapWeightSave(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional(readOnly = false)
	@Override
	public void scrapWeightSave(String matCode, String workOrderNO, String userCode, String weight) {
		WorkOrder findParams = new WorkOrder();
		findParams.setWorkOrderNo(workOrderNO);
		WorkOrder wo = workOrderDAO.getOne(findParams);
		if (wo != null) {
			Scrap scrap = new Scrap();
			scrap.setMatCode(matCode);
			scrap.setWorkOrderNo(workOrderNO);
			scrap.setUserCode(userCode);
			scrap.setWeight(Double.valueOf(weight));
			scrap.setOrgCode(wo.getOrgCode());
			scrapService.insert(scrap);
		} else {
			throw new MESException("wip.workOrder.notfound");
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void unitWeightSave(String workOrderNO, String userCode, String weight) {
		processQcService.insertByWorkOrderNO(workOrderNO, weight, QuailityNameConstants.UNIT_WEIGHT, userCode);
	}

	/**
	 * <p>
	 * 实际投放物料
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-21 下午4:46:28
	 * @param workOrderNO
	 * @param batchNo
	 */
	@Override
	public JSONObject feedMaterial(String workOrderNO, String batchNo, String operator, String orderTaskId, String color) {
		Mat mat = matService.getByCode(batchNo);
		JSONObject result = new JSONObject();
		WorkOrder workOrder = getByWorkOrderNO(workOrderNO);
		RealCost realCost = null;
		List<ProcessInOut> putIns = processInOutService.getInByWorkOrderNo(workOrderNO);
		String parentWorkOrderNo = workOrder.getProcessGroupRespool();
		if (mat == null && StringUtils.isNotBlank(parentWorkOrderNo)) {
			Report report = reportService.getByWorkOrderNoAndBarCode(batchNo, parentWorkOrderNo);
			if (report != null) {
				for (ProcessInOut putIn : putIns) {
					if (MatType.SEMI_FINISHED_PRODUCT == putIn.getMatType()) {
						if (StringUtils.isNotBlank(report.getProductColor())
								&& StringUtils.isNotBlank(putIn.getColor())
								&& StringUtils.equals(report.getProductColor(), putIn.getColor())) {
							realCost = new RealCost();
							realCost.setBatchNo(batchNo);
							realCost.setWorkOrderNo(workOrderNO);
							realCost.setOrgCode(SessionUtils.getUser().getOrgCode());
							realCost.setCreateUserCode(operator);
							realCost.setModifyUserCode(operator);
							realCost.setColor(putIn.getColor());
							realCost.setOrderTaskId(orderTaskId);
							realCost.setMatCode(putIn.getMatCode());
							realCostService.insert(realCost);
							inventoryService.deleteByBarCode(batchNo);
						}
					}
				}
				if (realCost == null && putIns.get(0).getMatType() == MatType.SEMI_FINISHED_PRODUCT) {
					realCost = new RealCost();
					realCost.setBatchNo(batchNo);
					realCost.setWorkOrderNo(workOrderNO);
					realCost.setOrgCode(SessionUtils.getUser().getOrgCode());
					realCost.setCreateUserCode(operator);
					realCost.setModifyUserCode(operator);
					realCost.setColor(putIns.get(0).getColor());
					realCost.setOrderTaskId(orderTaskId);
					realCost.setMatCode(putIns.get(0).getMatCode());
					realCostService.insert(realCost);
					inventoryService.deleteByBarCode(batchNo);
				}
			}

			if (realCost == null) {
				result.put("success", false);
				result.put("msg", "请查看库位信息，刷正确的条码或者上一工序未完成，请等待完成后操作!");
			} else {
				result.put("success", true);
			}
		} else {
			realCost = new RealCost();
			realCost.setBatchNo(batchNo);
			realCost.setWorkOrderNo(workOrderNO);
			realCost.setMatCode(batchNo);
			realCost.setOrgCode(SessionUtils.getUser().getOrgCode());
			realCost.setCreateUserCode(operator);
			realCost.setModifyUserCode(operator);
			realCost.setColor(color);
			realCostService.insert(realCost);
			result.put("success", true);
		}
		return result;
	}

	/**
	 * 
	 * <p>
	 * 检查实际投放的物料和物料需求表中记录是否是同一类型
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-21 下午4:47:52
	 * @param wo
	 * @param matCode 物料需求code
	 * @param batchNo 实际投放物料编号
	 * @return
	 * @see
	 */
	private boolean checkMaterial(WorkOrder wo, String matCode, String batchNo) {
		// TODO 需要明确物料编码规则
		// 检查物料是否投放过
		if (StringUtils.isNotBlank(batchNo)) {
			List<RealCost> list = realCostService.getByWorkOrderNO(wo.getWorkOrderNo());
			if (list != null) {
				for (RealCost rc : list) {
					if (StringUtils.equalsIgnoreCase(rc.getBatchNo(), batchNo)) {
						throw new MESException("wip.feedMaterial.hasPutIn");
					}
				}
			}
		}
		return StringUtils.equalsIgnoreCase(matCode, batchNo);
	}

	@Override
	public List findForEquipProcessTrace(Map<String, Object> findParam, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		findParam.put("orgCode", getOrgCode());
		List list = workOrderDAO.findForEquipProcessTrace(findParam);
		return list;
	}

	@Override
	public int countForEquipProcessTrace(Map<String, Object> findParam) {
		findParam.put("orgCode", getOrgCode());
		return workOrderDAO.countForEquipProcessTrace(findParam);
	}

	/**
	 * <p>
	 * 通过工单号查询工单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-26 上午11:56:13
	 * @param workOrderNo
	 * @return
	 * @see cc.oit.bsmes.wip.service.WorkOrderService#getByWorkOrderNO(java.lang.String)
	 */
	@Override
	public WorkOrder getByWorkOrderNO(String workOrderNo) {
		WorkOrder findParams = new WorkOrder();
		findParams.setWorkOrderNo(workOrderNo);
		return workOrderDAO.getOne(findParams);
	}

	@Override
	public void updateStatusByNo(String workOrderNo) {
		workOrderDAO.updateStatusByNo(workOrderNo);
	}

	@Override
	@Transactional(readOnly = false)
	public void changeEquipStatus(String equipCode, String operator) {
		equipInfoService.changeEquipStatusBetweenDebugAndInProgress(equipCode, operator);
	}

	@Override
	public void acceptWorkOrder(String workOrderNo, String updator) {
		workOrderDAO.acceptWorkOrder(workOrderNo, updator);
	}

	@Override
	public List<WorkOrder> getDisorderWorkOrderByEquipCode(String equipCode, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		WorkOrder findParams = new WorkOrder();
		findParams.setEquipCode(equipCode);
		findParams.setStatus(WorkOrderStatus.TO_AUDIT);
		findParams.setOrgCode(getOrgCode());
		List<WorkOrder> workOrderList = workOrderDAO.getDisorderWorkOrderByEquipCode(findParams);
		return workOrderList;
	}

	@Override
	public int countDisorderWorkOrderByEquipCode(String equipCode) {
		WorkOrder findParams = new WorkOrder();
		findParams.setEquipCode(equipCode);
		findParams.setStatus(WorkOrderStatus.TO_AUDIT);
		return workOrderDAO.countDisorderWorkOrderByEquipCode(findParams);
	}
	
	// 更细生产单长度百分比
	public int updatePercent(WorkOrder workOrder){
		return workOrderDAO.updatePercent(workOrder);
	}

	/**
	 * <p>
	 * 字典查询下拉框
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-7-15 11:12:48
	 * @return List<DataDic>
	 * @see
	 */
	@Override
	public JSON getWorkOrderStatusCombo() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("", "全部");
		for (WorkOrderStatus wos : WorkOrderStatus.values()) {
			map.put(wos.name(), wos.toString());
		}
		return JSONArrayUtils.mapToJSON(map, "code", "name");
	}

	/**
	 * <p>
	 * 根据设备code 获取已经排序的生产单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午4:04:22
	 * @param equipCode
	 * @return
	 * @see cc.oit.bsmes.wip.service.WorkOrderService#getSeqWorkOrderByEquipCode(java.lang.String)
	 */
	@Override
	public List<WorkOrder> getSeqWorkOrderByEquipCode(String equipCode) {
		WorkOrder findParams = new WorkOrder();
		findParams.setEquipCode(equipCode);
		findParams.setStatus(WorkOrderStatus.TO_AUDIT);
		findParams.setOrgCode(getOrgCode());
		List<WorkOrder> workOrderList = workOrderDAO.getSeqWorkOrderByEquipCode(findParams);
		return workOrderList;
	}

	/**
	 * <p>
	 * 将生产单加入排序表中
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午5:14:07
	 * @param workOrderNos
	 * @see cc.oit.bsmes.wip.service.WorkOrderService#insertToPriorityByWorkOrders(java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void insertToPriorityByWorkOrders(List<String> workOrderNos) {
		if (workOrderNos != null) {
			int seq = 0;
			String equipCode = "";
			List<WorkOrder> workOrderList = new ArrayList<WorkOrder>();
			for (String workOrderNo : workOrderNos) {
				if (StringUtils.isBlank(equipCode)) {
					WorkOrder findParams = new WorkOrder();
					findParams.setWorkOrderNo(workOrderNo);
					WorkOrder wo = workOrderDAO.getOne(findParams);
					if (StringUtils.isNotBlank(wo.getFixedEquipCode())) {
						equipCode = wo.getFixedEquipCode();
					} else {
						equipCode = wo.getEquipCode();
					}
				}
				if (seq == 0) {
					int tmp = highPriorityOrderItemProDecService.getMaxSeqByEquipCode(equipCode);
					if (tmp > seq) {
						seq = tmp;
					}
				}
				seq++;
				WorkOrder wo = new WorkOrder();
				wo.setWorkOrderNo(workOrderNo);
				wo.setEquipCode(equipCode);
				wo.setSeq(seq);
				wo.setFixedEquipCode(equipCode);
				workOrderList.add(wo);

			}
			updateWorkOrderFixEquip(workOrderList);
			updateSeq(workOrderList);
		}
	}

	private void updateWorkOrderFixEquip(List<WorkOrder> workOrderList) {
		if (workOrderList != null && !workOrderList.isEmpty()) {
			List<String> workOrderNoList = new ArrayList<String>(workOrderList.size());
			String fixEquipCode = "";
			for (WorkOrder wo : workOrderList) {
				workOrderNoList.add(wo.getWorkOrderNo());
				if (StringUtils.isBlank(fixEquipCode)) {
					fixEquipCode = wo.getFixedEquipCode();
				}
			}
			updateWorkOrderFixEquip(fixEquipCode, workOrderNoList);
		}
	}

	private void updateWorkOrderFixEquip(String fixedEquipCode, List<String> workOrderNoList) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fixedEquipCode", fixedEquipCode);
		param.put("workOrderNoList", workOrderNoList);
		workOrderDAO.updateWorkOrderFixEquip(param);
	}

	/**
	 * <p>
	 * 将生产单从排序表移除
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午6:06:27
	 * @param workOrderNos
	 * @see cc.oit.bsmes.wip.service.WorkOrderService#removeFromPriorityByWorkOrders(java.util.List)
	 */
	@Override
	@Transactional(readOnly = false)
	public void removeFromPriorityByWorkOrders(List<String> workOrderNos) {
		if (workOrderNos != null) {
			for (String workOrderNo : workOrderNos) {
				highPriorityOrderItemProDecService.deleteByWorkOrderNo(workOrderNo);
				customerOrderItemProDecService.updateFixEquipForWorkOrderSeq("", workOrderNo);
			}
			updateWorkOrderFixEquip("", workOrderNos);
		}
	}

	/**
	 * <p>
	 * 更新设备上生产单顺序
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-20 下午1:56:09
	 * @param updateSeq
	 * @see cc.oit.bsmes.wip.service.WorkOrderService#updateSeq(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false)
	public void updateSeq(String updateSeq) {
		List<WorkOrder> workOrderList = JSON.parseArray(updateSeq, WorkOrder.class);
		if (workOrderList != null && !workOrderList.isEmpty()) {
			highPriorityOrderItemProDecService.deleteByEquipCode(workOrderList.get(0).getEquipCode());
			updateSeq(workOrderList);
		}
	}

	@Transactional(readOnly = false)
	public void updateSeq(List<WorkOrder> workOrderList) {
		if (workOrderList != null && !workOrderList.isEmpty()) {
			String equipCode = "";
			for (WorkOrder workOrder : workOrderList) {
				if (StringUtils.isBlank(equipCode)) {
					equipCode = workOrder.getEquipCode();
				}
				highPriorityOrderItemProDecService.insertSeqByWorkOrderNo(workOrder.getWorkOrderNo(), equipCode,
						workOrder.getSeq());
				customerOrderItemProDecService.updateFixEquipForWorkOrderSeq(equipCode, workOrder.getWorkOrderNo());
			}
		}
	}

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-21 下午6:16:06
	 * @param workOrderNo
	 * @param fixedEquipCode
	 * @return
	 * @see cc.oit.bsmes.wip.service.WorkOrderService#updateFixedEquipByWorkOrderNo(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public WorkOrder updateFixedEquipByWorkOrderNo(String workOrderNo, String fixedEquipCode) {
		// 根据工单号删除排序表内数据
		highPriorityOrderItemProDecService.deleteByWorkOrderNo(workOrderNo);
		// 根据设备code和工单号清空PRODEC内的seq字段,设置固定设备字段
		customerOrderItemProDecService.updateFixEquipForWorkOrderSeq(fixedEquipCode, workOrderNo);
		List<String> workOrderNos = new ArrayList<String>();
		workOrderNos.add(workOrderNo);
		// 根据固定设备代码和工单号更新生产单固定设备
		updateWorkOrderFixEquip(fixedEquipCode, workOrderNos);
		return getByWorkOrderNO(workOrderNo);
	}

	@Override
	public WorkOrder getLastByEquipCode(String equipCode) {
		List<WorkOrder> list = workOrderDAO.getLastByEquipCode(equipCode, getOrgCode());
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void export(OutputStream os, String sheetName, JSONArray columns, Map<String, Object> findParams)
			throws IOException, WriteException, InvocationTargetException, IllegalAccessException {

		WritableWorkbook wwb = Workbook.createWorkbook(os);
		List<String> columnList = new ArrayList<String>();
		WritableSheet sheet = wwb.createSheet(sheetName, 0);
		for (int i = 0; i < columns.size(); i++) {
			JSONObject jsonObject = (JSONObject) columns.get(i);
			sheet.addCell(new Label(i, 0, jsonObject.getString("text")));
			columnList.add(jsonObject.getString("dataIndex"));
		}

		JSONArray list = getResList(findParams);
		if (list.size() == 0) {
			wwb.write();
			wwb.close();
			return;
		}
		Date endTime = null;
		for (int i = 0; i < list.size(); i++) {
			JSONObject obj = (JSONObject) list.get(i);
			for (int j = 0; j < columnList.size(); j++) {
				String key = columnList.get(j);
				Object val = obj.get(key);
				if (StringUtils.equals("realEndTime", columnList.get(j)) && val == null) {
					val = DateUtils.convert(new Date(), DateUtils.DATE_TIME_FORMAT);
					endTime = new Date();
				}
				if (StringUtils.equals("usedTime", columnList.get(j))) {
					Date startTime = DateUtils.convert(obj.get("realStartTime").toString(), DateUtils.DATE_TIME_FORMAT);
					long minutes = 0;
					if (endTime == null) {

						minutes = (new Date().getTime() - startTime.getTime()) / 60000;
					} else {
						minutes = (endTime.getTime() - startTime.getTime()) / 60000;
					}

					val = minutes / (60 * 24) + "天" + minutes / 60 % 24 + "时" + minutes % 60 + "分";

				}
				if (val != null) {
					sheet.addCell(new Label(j, i + 1, val.toString()));
				}
			}
		}

		wwb.write();
		wwb.close();

	}

	public JSONArray getResList(Map<String, Object> findParams) {
		List list = workOrderDAO.findForEquipProcessTrace(findParams);
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
		JSONArray result = JSON.parseArray(text);
		;
		return result;
	}
	
	/**
	 * @Title:       checkWorkOrderIsFinished
	 * @Description: TODO((报工界面: 完成生产单按钮:校验生产单是否全部完成)
	 * @param:       workOrderNo 生产单号
	 * @param:       equipCode 设备编码
	 * @param:       operator 操作人
	 * @return:      JSONObject   
	 * @throws
	 */
	@Override
	public MethodReturnDto checkWorkOrderIsFinished(String workOrderNo, String equipCode, String operator){
		// 1、获取生产单下面的所有任务
		StringBuffer msg = new StringBuffer();
		CustomerOrderItemProDec findParams = new CustomerOrderItemProDec();
		findParams.setWorkOrderNo(workOrderNo);
		List<CustomerOrderItemProDec> proDecList = customerOrderItemProDecService.findByObj(findParams);
		for(CustomerOrderItemProDec proDec : proDecList){
			// 2、判断未完成提示信息
			if((!ProductOrderStatus.FINISHED.equals(proDec.getStatus())) && Math.floor(proDec.getUnFinishedLength()) > 0)
			{
				msg.append(proDec.getContractNo()).append("[").append(proDec.getCustProductType()).append("-")
				.append(proDec.getCustProductSpec()).append("][").append(proDec.getColor()).append("]还剩未完成长度:")
				.append(proDec.getUnFinishedLength()).append("<br/>");
			}
		}
		// 3、返回
		String msgStr = msg.toString();
		if (StringUtils.isNotEmpty(msgStr)) {
			return new MethodReturnDto(false, msgStr);
		}
		// 4、任务全部完成执行完成生产单操作：减少一次请求
		this.finishWorkOrder(workOrderNo, equipCode, operator);
		return new MethodReturnDto(true, "");
	}
	
	/**
	 * @Title:       finishWorkOrder
	 * @Description: TODO((报工界面: 完成生产单按钮:完成生产单)
	 * @param:       workOrderNo 生产单号
	 * @param:       equipCode 设备编码
	 * @param:       operator 操作人
	 * @return:      JSONObject   
	 * @throws
	 */
	@Override
	@Transactional(readOnly = false)
	public MethodReturnDto finishWorkOrder(String workOrderNo, String equipCode, String operator){
		User user = SessionUtils.getUser();
		// 1、获取操作人员信息
		operator = StringUtils.isNotEmpty(operator) ? operator : (user == null ? "" : user.getUserCode());
		// 2、更新物料需求信息
		this.finishMat(workOrderNo);
		// 3、调用存储过程变更生产单相关状态
		workOrderDAO.finishWorkOrder(workOrderNo, operator);
		// 4、生产单状态变更记录
		workOrderOperateLogService.changeWorkOrderStatus(workOrderNo, equipCode, WorkOrderStatus.FINISHED, WorkOrderOperateType.FINISHED, WorkOrderOperateType.FINISHED.toString(),
				user.getUserCode());
		// 5、判断订单是否为最后一道工序
		WorkOrder workOrder = this.getByWorkOrderNO(workOrderNo);
		if(WebConstants.END_WO_ORDER.equals(workOrder.getNextSection())){
			//根据生产单号获取旗下订单的SALES_ORDER_ITEM_ID，
			//同一订单下的REPORT_LENGTH之和，以及同一订单下的所有报告人
			List<Map<String,String>> list = workOrderDAO.getJJXX(workOrderNo);
			//插入中间表T_WIP_FINISHED_ORDER_ITEM，准备向ERP传送数据
			for(Map<String,String> map : list){
				FinishOrderItem finishedOrder = new FinishOrderItem();
				finishedOrder.setId(UUID.randomUUID().toString());
				finishedOrder.setJjsl(Double.valueOf(map.get("REPORT_LENGTH")));
				finishedOrder.setJldw("公里");
				finishedOrder.setProcessCode(workOrder.getProcessCode());
				finishedOrder.setPushStatus(OrderPushStatus.TO_PUSH);
				finishedOrder.setSalesOrderItemId(map.get("SALES_ORDER_ITEM_ID"));
				Employee employee = employeeDAO.getByUserCode(map.get("USER_CODE"));
				String jjr = employee.getName();
				finishedOrder.setJjrid(jjr);
				finishedOrder.setJjr(jjr);
				finishOrderItemDAO.insert(finishedOrder);
			}
		}
		return new MethodReturnDto(true, "");
	}

	/**
	 * 生产单完成，物料清单状态改为已使用，未生产长度改为0
	 * 
	 * @param woNo
	 */
	private void finishMat(String woNo) {
		List<MaterialRequirementPlan> materialRequirementPlanList = materialRequirementPlanService
				.getByWorkOrderNo(woNo);
		for (MaterialRequirementPlan materialRequirementPlan : materialRequirementPlanList) {
			materialRequirementPlan.setStatus(MaterialStatus.FINISHED);
			materialRequirementPlanService.update(materialRequirementPlan);
		}
	}

	@Override
	public List<WorkOrder> getPausedWorkOrders(String orgCode) {
		WorkOrder params = new WorkOrder();
		params.setStatus(WorkOrderStatus.PAUSE);
		params.setOrgCode(orgCode);

		return workOrderDAO.get(params);

	}

	@Override
	public WorkOrder getByBarCode(String barCode, String orgCode) {
		return workOrderDAO.getByBarCode(barCode, orgCode);
	}

	@Override
	public int countToday(String equipCode, String orgCode) {
		return workOrderDAO.countToday(equipCode, orgCode);
	}

	@Override
	public List<WorkOrder> getByEquipCode(String equipCode) {
		return workOrderDAO.getByEquipCode(equipCode, getOrgCode());
	}

	/**
	 * 根据设备编码获取当前加工的产品信息
	 * 
	 * @param equipCode 设备编码
	 * @return List<Map<String,String>>
	 * */
	@Override
	public List<Map<String, String>> getProductByEquipCode(String equipCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("equipCode", equipCode);
		params.put("orgCode", this.getOrgCode());
		return workOrderDAO.getProductByEquipCode(params);
	}

	@Override
	public Map<String, Object> getProductsCoordinate(WorkOrder workOrder) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Double> speed = new ArrayList<Double>();
		workOrder.setStatus(WorkOrderStatus.FINISHED);
		List<WorkOrder> list = workOrderDAO.getFinishedWorkOrder(workOrder);
		if (list.size() == 0) {
			return null;
		}
		for (WorkOrder workOrder_ : list) {
			Long timeBet = DateUtils.getMinuteDiff(workOrder_.getRealStartTime(), workOrder_.getRealEndTime());
			double productSpeed = workOrder_.getOrderLength() / timeBet;
			speed.add(productSpeed);
		}
		Object[] x_ = new Object[speed.size()];
		Object[] y_ = new Object[speed.size()];
		Collections.sort(speed);
		double avg = (speed.get(speed.size() - 1) - speed.get(0)) / 10;
		if (avg == 0) {
			map.put("x_axis", new Object[] { speed.get(0) });
			map.put("y_axis", new Object[] { speed.size() });
			return map;
		}
		double product_last = speed.get(0);
		int i = 0;
		while (product_last <= speed.get(speed.size() - 1)) {
			int num = 0;
			double product = product_last;
			product_last = product_last + avg;
			for (Double product_ : speed) {
				if (product_ >= product && product_ < product_last) {
					num++;
				}
			}
			String key = Double.parseDouble(String.format("%.1f", product)) + "~"
					+ Double.parseDouble(String.format("%.1f", product_last));
			x_[i] = key;
			y_[i] = num;
			i++;
		}
		List<String> xList = new ArrayList<String>();
		List<Integer> yList = new ArrayList<Integer>();
		for (int k = 0; k < x_.length; k++) {
			Object obj = x_[k];
			if (obj != null) {
				xList.add(obj.toString());
				yList.add((Integer) y_[k]);
			} else {
				break;
			}
		}
		map.put("x_axis", xList.toArray());
		map.put("y_axis", yList.toArray());
		return map;
	}

	@Override
	public List<WorkOrder> getWorkOrderAndProduct(WorkOrder workOrder) {
		return workOrderDAO.getWorkOrderAndProduct(workOrder);
	}

	@Override
	public List<WorkOrder> getWorkOrderByEquipList(Map<String, Object> map) {
		return workOrderDAO.getWorkOrderByEquipList(map);
	}

	@Override
	public void updateWorkerOrderIsDispatchByNo(String workOrderNo) {
		if (StringUtils.isEmpty(workOrderNo)) {
			return;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("workOrderNo", Arrays.asList(workOrderNo.split(",")));
		workOrderDAO.updateWorkerOrderIsDispatchByNo(param);
	}

	/**
	 * 手动排程 查看生产单/成缆护套下单界面
	 * 
	 * @author DingXintao
	 * @return List<WorkOrder>
	 */
	@Override
	public List<WorkOrder> findForHandSchedule(Map<String, Object> param) {
		List<WorkOrder> list1 = workOrderDAO.findForHandSchedule(param);
		if(list1.size() > 0)
		{
			List<WorkOrder> list2 = workOrderDAO.findForHandScheduleContract(list1);
			for (WorkOrder workOrder : list1) {
				for (WorkOrder workOrder2 : list2)
				{
					if(StringUtils.equals(workOrder.getId(), workOrder2.getId()))
					{
						workOrder.setContractNo(workOrder2.getContractNo());
						continue;
					}
				}
			}
		}
		return list1;
	}

	/**
	 * 手动排程 查看生产单
	 * 
	 * @author DingXintao
	 * @return List<WorkOrder>
	 */
	@Override
	public int findForHandScheduleCount(Map<String, Object> param) {
		return workOrderDAO.findForHandScheduleCount(param);
	}

	/**
	 * 手动排程 查看生产单 - 根据设备编码
	 * 
	 * @author DingXintao
	 * @param equipCode 设备编码
	 * @return List<WorkOrder>
	 */
	public List<WorkOrder> findForHandScheduleByEquipCode(String equipCode) {
//		return workOrderDAO.findForHandScheduleByEquipCode(equipCode);
		List<WorkOrder> list1 = workOrderDAO.findForHandScheduleByEquipCode(equipCode);
		if(list1.size() > 0)
		{
			List<WorkOrder> list2 = workOrderDAO.findForHandScheduleContract(list1);
			for (WorkOrder workOrder : list1) {
				for (WorkOrder workOrder2 : list2)
				{
					if(StringUtils.equals(workOrder.getId(), workOrder2.getId()))
					{
						workOrder.setContractNo(workOrder2.getContractNo());
						continue;
					}
				}
			}
		}
		return list1;
	}

	/**
	 * @Title getWOByEquipCodeAndStatus
	 * @Description TODO(根据设备编号 + 订单状态 查询生产单 先按创建时间排序)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年3月18日 下午2:15:26
	 * @param @param equipCode 设备编码
	 * @param @param status 状态
	 * @param @param start 开始数
	 * @param @param limit 每页数限制
	 * @param @param sortList 排序
	 * @return List<WorkOrder>
	 * @throws
	 */
	@Override
	public List<WorkOrder> getWOByEquipCodeAndStatus(String equipCode, String status, int start, int limit,
			List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		Map<String, String> findParams = new HashMap<String, String>();
		findParams.put("status", status);
		findParams.put("equipCode", equipCode);
		return workOrderDAO.getWOByEquipCodeAndStatus(findParams);
	}

	/**
	 * @Title countGetWOByEquipCodeAndStatus
	 * @Description TODO(根据设备编号 + 订单状态 查询生产单 计数)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年3月18日 下午2:16:16
	 * @param @param equipCode 设备编码
	 * @param @param status 状态
	 * @return int
	 * @throws
	 */
	@Override
	public int countGetWOByEquipCodeAndStatus(String equipCode, String status) {
		Map<String, String> findParams = new HashMap<String, String>();
		findParams.put("status", status);
		findParams.put("equipCode", equipCode);
		return workOrderDAO.countWOByEquipCodeAndStatus(findParams);
	}

	/**
	 * 根据不同的工段返回不同的结果
	 * 
	 * @param workOrderNo
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getWOSalesOrderInfo(String workOrderNo, String section) {
		/*
		 * if(StringUtils.equals("成缆",section)){ return
		 * workOrderDAO.getWOSalesOrderInfoCL(workOrderNo); }else{ }
		 */
		List<Map<String, Object>> list = workOrderDAO.getWOSalesOrderInfo(workOrderNo);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		for (Map<String, Object> stringObjectMap : list) {
			Object object = stringObjectMap.get("OUT_ATTR_DESC");
			if (object != null) {
				String objectStr = String.valueOf(object);
				OutAttrDesc outAttrDesc = gson.fromJson(objectStr, OutAttrDesc.class);
				Class clazz = outAttrDesc.getClass();
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					try {
						Method getMethod = clazz.getDeclaredMethod("get" + toUpperCaseFirstOne(field.getName()));
						stringObjectMap.put(field.getName(), getMethod.invoke(outAttrDesc));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			stringObjectMap.remove("OUT_ATTR_DESC");
		}
		return list;
	}

	@Override
	public List<String> getWorkOrderEquipByWoNum(String workOrderNum) {
		return workOrderDAO.getWorkOrderEquip(workOrderNum);
	}

	/**
	 * 更新生产单状态：1、workOrder 2、orderTask 3、proDec
	 * 
	 * @param workOrderNo 生产单号
	 * @param status 状态
	 * */
	@Override
	@Transactional(readOnly = false)
	public MethodReturnDto updateWorkerOrderStatus(String workOrderNo, WorkOrderStatus status) {
		if (StringUtils.isEmpty(workOrderNo)) {
			return new MethodReturnDto(false, "参数[生产单号]传递错误！");
		}
		String userCode = SessionUtils.getUser().getUserCode();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("workOrderNo", Arrays.asList(workOrderNo.split(",")));
		param.put("status", status.name());

		for (String woNo : Arrays.asList(workOrderNo.split(","))) {
			WorkOrder workOrder = this.getByWorkOrderNO(woNo);
			workOrder.setStatus(status);
			workOrder.setAuditTime(status == WorkOrderStatus.TO_DO ? new Date() : null);
			workOrder.setAuditUserCode(status == WorkOrderStatus.TO_DO ? userCode : null);
			workOrder.setRealStartTime(status == WorkOrderStatus.FINISHED ? new Date() : null);
			workOrder.setRealEndTime(status == WorkOrderStatus.FINISHED ? new Date() : null);
			workOrderDAO.update(workOrder);
			workOrderOperateLogService.changeWorkOrderStatus(woNo, "", status, WorkOrderOperateType.UPDATE, WorkOrderOperateType.UPDATE.toString(), userCode); // 1、生产单状态变更记录
		}
		orderTaskService.updateWorkerOrderStatus(param);
		customerOrderItemProDecService.updateWorkerOrderStatus(param);
		if (status == WorkOrderStatus.TO_DO) { // 下发生产单的话哟调整顺序
			workOrderDAO.setRelationSeq(workOrderNo);
		} else if (status == WorkOrderStatus.CANCELED) {
			// 取消生产单: 将上一道生产单的nextSection设置为这一道生产单的WorkOrderSection
			for (String no : workOrderNo.split(",")) {
				WorkOrder workOrder = this.getByWorkOrderNO(no);
				WorkOrder parWorkOrder = null;
				if (StringUtils.isNotEmpty(workOrder.getProcessGroupRespool())) { // 取消的话要将上一道生产单已经下发的字段重新清空
					parWorkOrder = this.getByWorkOrderNO(workOrder.getProcessGroupRespool());
					parWorkOrder.setNextSection(workOrder.getWorkOrderSection());

					String auditCusIds = parWorkOrder.getAuditedCusOrderItemIds() + ","; // 上一道生产单已经下发的订单id
					String cusIds = workOrder.getCusOrderItemIds(); // 取消的生产单的订单id
					for (String cusId : cusIds.split(",")) { // 更新已经下发的订单ID，去掉已经取消的
						auditCusIds = auditCusIds.replace(cusId + ",", "");
					}
					if (StringUtils.isNotEmpty(auditCusIds)
							&& ",".equals(auditCusIds.substring(auditCusIds.length() - 1))) { // 去掉逗号
						auditCusIds = auditCusIds.substring(0, auditCusIds.length() - 1);
					}
					parWorkOrder.setAuditedCusOrderItemIds(auditCusIds);
					// parWorkOrder.setAuditedCusOrderItemIds("");
					workOrderDAO.update(parWorkOrder);
				}
			}
		}
		return new MethodReturnDto(true);
	}

	@Override
	public List<WorkOrder> getWorkOrderBaseInfo(String orderItemId, String section) {
		return workOrderDAO.getWorkOrderBaseInfo(orderItemId, section);
	}

	@Override
	public List<WorkOrder> getWorkOrderFinishDetail(String orderItemId, String workOrderNo) {
		return workOrderDAO.getWorkOrderFinishDetail(orderItemId, workOrderNo);
	}

	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 上一道工序工序合成JSON字段 [成缆专用]
	 * 
	 * @param workOrderNo 生产单号
	 * @return String
	 */
	@Override
	public String getProcessesMergedArrayByWorkNo(String workOrderNo) {
		return workOrderDAO.getProcessesMergedArrayByWorkNo(workOrderNo);
	}

	/**
	 * 根据其中一个生产单获取整个生产单流程
	 * 
	 * @param workOrderNo 生产单号
	 * @return List<WorkOrder>
	 */
	@Override
	public List<WorkOrder> getWorkOrderFlowArray(String workOrderNo) {
		return workOrderDAO.getWorkOrderFlowArray(workOrderNo);
	}

	/**
	 * @Title getAllWorkOrderByOIID
	 * @Description TODO(获取这批所有的相关的生产单信息)
	 * @author DinXintao
	 * @version V1.0
	 * @date 2016年3月31日 下午1:39:41
	 * @param orderItemIdArray 查询参数
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getAllWorkOrderByOIID(String[] orderItemIdArray) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderItemIdArray", orderItemIdArray);
		return workOrderDAO.getAllWorkOrderByOIID(param);
	}

	@Override
	public List<Map<String, String>> getPrintMatList(String workOrderNo) {
		WorkOrder workOrder = getByWorkOrderNO(workOrderNo);
		if (StringUtils.isNotBlank(workOrder.getProcessGroupRespool())) {
			return workOrderDAO.getPrintMatList(workOrder.getProcessGroupRespool());
		}
		return null;
	}

	public void saveChangeWorkOrderNoReason(Map<String, Object> param) {
		workOrderDAO.saveChangeWorkOrderNoReason(param);
	}

	@Override
	public void saveTimeAndTempValue(Map<String, String> param) {
		workOrderDAO.saveTimeAndTempValue(param);

	}

	@Override
	public String getContractLengthByWorkOrderNo(String workOrderNo) {
		// TODO Auto-generated method stub
		return workOrderDAO.getContractLengthByWorkOrderNo(workOrderNo);
	}

	@Override
	public String getContractLengthByProDecId(String parentId) {
		// TODO Auto-generated method stub
		return workOrderDAO.getContractLengthByProDecId(parentId);
	}
	
	//根据生产单号获取该生产单的生产进度
	@Override
	public String getFinishedPercent(String woOrderNo,String preWorkOrderNo){
		return workOrderDAO.getFinishedPercent(woOrderNo,preWorkOrderNo);
	}
	
	@Override
	public Map<String, List<Object>> refreshSingle(String equipCode,String workOrderNo)
	{
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
	}
	
	/**
	 * @Title:       getLatestWorkOrderNo
	 * @Description: TODO(获取最新的订单的生产单号)
	 * @param:       orderItemIdArray 订单id
	 * @return:      String   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WorkOrder getLatestWorkOrder(List<String> orderItemIdArray){
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map.put("orderItemIdArray", orderItemIdArray);
		return workOrderDAO.getLatestWorkOrder(map);
	}
	
	/**
	 * 更新生产单状态: 排除状态finished的bug
	 * 
	 * @param workOrderNo 生产单号
	 * @param status 状态
	 * */
	public void updateWorkerOrderStatusNotFinished(String workOrderNo, WorkOrderStatus status){
		workOrderDAO.updateWorkerOrderStatusNotFinished(workOrderNo, status);
	}

	/**
	 * 根据其中一个生产单(护套）获取编织的任务
	 * 
	 * @param workOrderNo 生产单号
	 * @author 王国华
	 * @date 2017-01-17 09:56:39
	 * @return List<WorkOrder>
	 */
	@Override
	public List<WorkOrder> getWorkOrderBraidingArray(String workOrderNo) {
		return workOrderDAO.getWorkOrderBraidingArray(workOrderNo);
	}

	@Override
	public List<WorkOrder> changeWorkOrderSeq(String equipCode) {
		List<WorkOrder> list1 = workOrderDAO.findForHandScheduleByEquipCode(equipCode);
		if(list1.size() > 0)
		{
			List<WorkOrder> list2 = workOrderDAO.changeWorkOrderSeq(list1);
			for (WorkOrder workOrder : list1) {
				for (WorkOrder workOrder2 : list2)
				{
					if(StringUtils.equals(workOrder.getId(), workOrder2.getId()))
					{
						workOrder.setContractNo(workOrder2.getContractNo());
						continue;
					}
				}
			}
		}
		return list1;
		
	}
}
