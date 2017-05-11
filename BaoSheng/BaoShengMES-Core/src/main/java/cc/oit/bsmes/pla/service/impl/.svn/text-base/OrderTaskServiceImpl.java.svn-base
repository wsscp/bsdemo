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
package cc.oit.bsmes.pla.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.EquipLoadCache;
import cc.oit.bsmes.common.util.JSONUtils;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.common.util.WebContextUtils;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.pla.dao.OrderTaskDAO;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.schedule.Scheduler;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.wip.dto.MethodReturnDto;
import cc.oit.bsmes.wip.model.OutAttrDesc;
import cc.oit.bsmes.wip.model.Section;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.SectionService;
import cc.oit.bsmes.wip.service.WorkOrderService;

/**
 * OrderTaskServiceImpl
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-1-3 下午3:18:55
 */
@Service
public class OrderTaskServiceImpl extends BaseServiceImpl<OrderTask> implements OrderTaskService {

	@Resource
	private OrderTaskDAO orderTaskDAO;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private EquipCalendarService equipCalendarService;
	@Resource
	private SectionService sectionService;

	@Override
	public List<OrderTask> getLimitByTime(String orgCode, String fromDate, String toDate) throws Exception {
		OrderTask o = new OrderTask();
		o.setOrgCode(orgCode);
		Date dFromDate = null;
		Date dtoDate = null;
		SimpleDateFormat fd = new SimpleDateFormat(DateUtils.DATE_FORMAT);
		if (StringUtils.isEmpty(fromDate) || StringUtils.isEmpty(toDate)) {

			dFromDate = fd.parse(DateUtils.convert(new Date(), DateUtils.DATE_FORMAT));
			dtoDate = DateUtils.addDayToDate(dFromDate, 15);

		} else {

			dFromDate = fd.parse(fromDate);
			dtoDate = DateUtils.addDayToDate(fd.parse(toDate), 1);

		}
		o.setPlanStartDate(dFromDate);
		o.setPlanFinishDate(dtoDate);

		return orderTaskDAO.getOrderTasksLimitByTime(o);
	}

	@Override
	public List<OrderTask> getUncompletedByWoNo(String workOrderNo) {
		return orderTaskDAO.getUncompletedByWoNo(workOrderNo);
	}

	@Override
	public List<String> getForOALocked(String orgCode) {
		return orderTaskDAO.getForOALocked(orgCode);
	}

	@Override
	public void deleteByOrgCode(String orgCode) {
		if (orderTaskDAO.deleteByOrgCode(orgCode) <= 0) {
			throw new DataCommitException();
		}
	}

	@Override
	public boolean checkFirstTime(String productCode, String contractNo) {
		OrderTask findParams = new OrderTask();
		findParams.setProductCode(productCode);
		List<OrderTask> orderTasks = orderTaskDAO.get(findParams);
		for (OrderTask orderTask : orderTasks) {
			if (!orderTask.getContractNo().equals(contractNo) && orderTask.getStatus() == WorkOrderStatus.TO_AUDIT) {
				return false;
			}
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public void generate(ResourceCache resourceCache, String orgCode) {
		int days = getDaysByParam(BusinessConstants.DAYS_TO_SCHEDULE, orgCode);
		Date daysToSchedule = equipCalendarService.getNextDays(new Date(), days, orgCode); // 确定要排到哪一天（跳过休息日）

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, getDaysByParam(BusinessConstants.All_DAYS, orgCode));
		Date allDays = calendar.getTime(); // 确定最多要处理的订单到哪一天
		Scheduler scheduler = new Scheduler(resourceCache, daysToSchedule, allDays, orgCode);
		scheduler.schedulePause();
		// scheduler.calculateBottleProcess();
		scheduler.scheduleHighPriority();
		// scheduler.scheduleBottle();
		// scheduler.scheduleBottleRelated();
		scheduler.scheduleBottleUnRelated();
		scheduler.scheduleInProgressForNotUrgent();
		// scheduler.scheduleFirstProcessForNotUrgent();
		// scheduler.scheduleLeftProcessForNotUrgent();
		// scheduler.compressAll();

		List<CustomerOrderItemProDec> ordersMatched = scheduler.getOrdersMatched();
		// 保存结果

		Map<String, WorkOrder> workOrderMap = getStringWorkOrderMap();

		for (CustomerOrderItemProDec customerOrderItemProDec : ordersMatched) {
			// System.out.println(customerOrderItemProDec.getId());
			OrderTask orderTask = customerOrderItemProDec.getOrderTask();
			orderTask.fixPlan();
			if (customerOrderItemProDec.getOrderTask().getRange().getLong() == 0) {
				continue;// 可选工序
			}
			orderTaskDAO.insert(orderTask);

			// WorkOrder wo =
			// workOrderService.getByWorkOrderNO(orderTask.getWorkOrderNo());
			WorkOrder wo = workOrderMap.get(orderTask.getWorkOrderNo());
			if (wo == null) {
				wo = new WorkOrder();
				wo.setWorkOrderNo(orderTask.getWorkOrderNo());
				wo.setOrgCode(orgCode);
				wo.setEquipCode(orderTask.getEquipCode());
				wo.setStatus(WorkOrderStatus.TO_AUDIT);
				wo.setProcessName(orderTask.getProcessName());
				wo.setHalfProductCode(orderTask.getHalfProductCode());
				wo.setPreStartTime(orderTask.getPlanStartDate());
				wo.setPreEndTime(orderTask.getPlanFinishDate());
				wo.setProcessId(customerOrderItemProDec.getProcessId());
				wo.setOrderLength(customerOrderItemProDec.getUnFinishedLength());
				wo.setFixedEquipCode(customerOrderItemProDec.getFixedEquipCode());
				if (orderTask.getIsDelayed()) {
					wo.setIsDelayed(true);
				}
				workOrderService.insert(wo);
				workOrderMap.put(orderTask.getWorkOrderNo(), wo);
			} else {
				if (orderTask.getIsDelayed()) {
					wo.setIsDelayed(true);
				}
				wo.setPreEndTime(orderTask.getPlanFinishDate());
				wo.setOrderLength(wo.getOrderLength() + customerOrderItemProDec.getUnFinishedLength());
				workOrderService.update(wo);
			}
		}
	}

	private Map<String, WorkOrder> getStringWorkOrderMap() {
		WorkOrder findParams = new WorkOrder();
		findParams.setStatus(WorkOrderStatus.TO_AUDIT);
		List<WorkOrder> workOrders = workOrderService.getByObj(findParams);
		Map<String, WorkOrder> workOrderMap = new HashMap<String, WorkOrder>();
		for (WorkOrder workOrder : workOrders) {
			workOrderMap.put(workOrder.getWorkOrderNo(), workOrder);
		}
		return workOrderMap;
	}

	private int getDaysByParam(String param, String orgCode) {
		return WebContextUtils.getSysParamIntValue(param, orgCode);
	}

	/**
	 * 根据生产单号获取所有订单任务数据
	 * 
	 * @author QiuYangjun
	 * @date 2014-1-20 下午3:13:25
	 */
	@Override
	public List<OrderTask> getByWorkOrderId(String workOrderId, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return getByWorkOrderId(workOrderId);
	}

	@Override
	public int countByWorkOrderId(String workOrderId) {
		return orderTaskDAO.countByWorkOrderId(workOrderId);
	}

	@Override
	public List<OrderTask> getByWorkOrderId(String workOrderId) {
		return orderTaskDAO.getByWorkOrderId(workOrderId);
	}

	@Override
	public List<OrderTask> getRecentByWorkOrderNo(String workOrderNo) {
		return orderTaskDAO.getRecentByWorkOrderNo(workOrderNo);
	}

	@Override
	public List<OrderTask> getProduceByWorkOrderNo(String workOrderNo) {
		return orderTaskDAO.getProduceByWorkOrderNo(workOrderNo);
	}

	/**
	 * <p>
	 * 根据生产单号获取所有订单任务数据
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-19 下午5:39:13
	 * @param workOrderNo
	 * @see cc.oit.bsmes.pla.service.OrderTaskService#getByWorkOrderNo(String)
	 */
	@Override
	public List<OrderTask> getByWorkOrderNo(String workOrderNo) {
		OrderTask findParams = new OrderTask();
		findParams.setWorkOrderNo(workOrderNo);
		return orderTaskDAO.get(findParams);
	}

	@Override
	public List<OrderTask> getByWorkOrderNo(String workOrderNo, String orgCode) {
		return orderTaskDAO.getByWorkOrderNo(workOrderNo, orgCode);
	}

	@Override
	public void fixEquipLoad(EquipInfo equipInfo, EquipLoadCache.Counter counter)
			throws EquipLoadCache.TimeOutException {
		if (counter.getCount() == -1) {
			List<OrderTask> oriOrderTasks = equipInfo.getOrderTasks();// 延迟加载
			equipInfo.setOriOrderTasks(oriOrderTasks);
			equipInfo.setOrderTasks(new ArrayList<OrderTask>());
			counter.increase();
		}
		List<OrderTask> fixedOrderTasks = equipInfo.getOrderTasks();
		List<OrderTask> oriOrderTasks = equipInfo.getOriOrderTasks();

		Calendar calendar = Calendar.getInstance();
		Date lastPlanFinishDate = calendar.getTime(); // 上一订单结束时间即当前订单开始时间，默认为当前时间
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		long endTime = calendar.getTime().getTime();
		boolean end = false;
		WorkOrder lastWorkOrder = null;
		for (; counter.getCount() < oriOrderTasks.size(); counter.increase()) {
			OrderTask orderTask = oriOrderTasks.get(counter.getCount());
			if (orderTask.getStatus() == WorkOrderStatus.TO_AUDIT) {
				break;
			}
			if (end) {
				checkLockState(orderTask);
				EquipLoadCache.putFixedOrderEmpty(orderTask.getOrderItemProDecId());
				continue;
			}

			boolean isBelongToLastWorkOrder = belongToWorkOrder(orderTask, lastWorkOrder);

			if (lastPlanFinishDate.getTime() > endTime && !isBelongToLastWorkOrder) { // 超出24小时且不同工单
				end = true;
				checkLockState(orderTask);
				EquipLoadCache.putFixedOrderEmpty(orderTask.getOrderItemProDecId());
				continue;
			}

			// 获取订单
			WorkOrder workOrder = null;
			if (isBelongToLastWorkOrder) {
				workOrder = lastWorkOrder;
			} else {
				updateWorkOrder(lastWorkOrder, lastPlanFinishDate);
				workOrder = workOrderService.getByWorkOrderNO(orderTask.getWorkOrderNo());
				workOrder.setPreEndTime(null);
			}
			if (workOrder != null
					&& (workOrder.getStatus() == WorkOrderStatus.TO_DO || workOrder.getStatus() == WorkOrderStatus.IN_PROGRESS)) {
				lastPlanFinishDate = fixOrderTask(orderTask, lastPlanFinishDate, fixedOrderTasks);
				if (lastPlanFinishDate == null) {
					end = true;
					checkLockState(orderTask);
					EquipLoadCache.putFixedOrderEmpty(orderTask.getOrderItemProDecId());
					continue;
				}
				// 调整工单
				if (!isBelongToLastWorkOrder) {
					workOrder.setPreStartTime(orderTask.getPlanStartDate());
				}
			}

			lastWorkOrder = workOrder;
			EquipLoadCache.putFixedOrder(orderTask.getOrderItemProDecId(), workOrder);
		}

		updateWorkOrder(lastWorkOrder, lastPlanFinishDate);

		equipInfo.setOriOrderTasks(equipInfo.getOrderTasks());
	}

	@Override
	public Map<String, Map<String, String>> getOrdersTodayByEquipcodes(String[] equipCodes, String orgCode) {
		return orderTaskDAO.getOrdersTodayByEquipcodes(equipCodes, orgCode);
	}

	@Override
	public OrderTask getCurrentOrder(String equipCode, String orgCode) {
		return orderTaskDAO.getCurrentOrder(equipCode, orgCode);
	}

	private void updateWorkOrder(WorkOrder workOrder, Date planFinishDate) {
		if (workOrder != null) {
			workOrder.setPreEndTime(planFinishDate);
			workOrderService.update(workOrder);
		}
	}

	/**
	 * 
	 * @param orderTask
	 * @param lastPlanFinishDate
	 * @param fixedOrderTasks
	 * @return fixedPlanFinishDate
	 */
	private Date fixOrderTask(OrderTask orderTask, Date lastPlanFinishDate, List<OrderTask> fixedOrderTasks)
			throws EquipLoadCache.TimeOutException {
		// 调整时间
		Date planStartDate = orderTask.getPlanStartDate();
		Date planFinishDate = orderTask.getPlanFinishDate();
		long lastTime = planFinishDate.getTime() - planStartDate.getTime(); // 持续时间
		Double taskLength = orderTask.getTaskLength();
		double speed = taskLength * 1000 / lastTime;

		if (orderTask.getStatus() == WorkOrderStatus.IN_PROGRESS) {
			// 计算完成长度
			List<Section> sections = sectionService.getByWoNo(orderTask.getWorkOrderNo());
			Double finished = 0d;
			for (Section section : sections) {
				finished += section.getGoodLength();
			}
			if (finished > taskLength) {
				planFinishDate = new Date();
			} else {
				lastTime = (long) ((taskLength - finished) / speed * 1000);
				planFinishDate = new Date(planStartDate.getTime() + lastTime);
			}
			orderTask.setPlanFinishDate(planFinishDate);
		} else {
			CustomerOrderItemProDec customerOrderItemProDec = customerOrderItemProDecService.getById(orderTask
					.getOrderItemProDecId());
			Double unFinishedLength = customerOrderItemProDec.getUnFinishedLength();
			if (taskLength != unFinishedLength) {
				// 如果长度不符修正持续时间
				lastTime = (long) (unFinishedLength / speed * 1000);
			}

			// 计算前置工序条件
			List<CustomerOrderItemProDec> lastOrders = customerOrderItemProDecService.getLastOrders(orderTask
					.getOrderItemProDecId());

			Date lastProcessFinishDate = null;
			// logger.info("当前订单：{}", customerOrderItemProDec.getId());
			for (CustomerOrderItemProDec lastOrder : lastOrders) {
				if (lastOrder.getStatus() != ProductOrderStatus.TO_DO
						&& lastOrder.getStatus() != ProductOrderStatus.IN_PROGRESS) {
					continue;
				}
				WorkOrder fixedOrder = (WorkOrder) EquipLoadCache.getFixedOrder(lastOrder.getId());
				if (EquipLoadCache.isEmptyOrder(fixedOrder)) {
					return null;
				}
				if (orderTask.getWorkOrderNo().equals(fixedOrder.getWorkOrderNo())) {
					continue;
				}
				Date _lastProcessFinishDate = fixedOrder.getPreEndTime();
				while (_lastProcessFinishDate == null) {
					_lastProcessFinishDate = fixedOrder.getPreEndTime();
				}
				if (lastProcessFinishDate == null || lastProcessFinishDate.getTime() < _lastProcessFinishDate.getTime()) {
					lastProcessFinishDate = _lastProcessFinishDate;
				}
			}

			if (lastProcessFinishDate == null || lastPlanFinishDate.getTime() > lastProcessFinishDate.getTime()) { // lastPlanFinishDate
																													// >
																													// lastProcessFinishDate
				planStartDate = lastPlanFinishDate;
			} else {
				planStartDate = lastProcessFinishDate;
			}
			planFinishDate = new Date(planStartDate.getTime() + lastTime);

			orderTask.setPlanStartDate(planStartDate);
			orderTask.setPlanFinishDate(planFinishDate);
		}
		orderTask.setIsLocked(true);
		orderTaskDAO.update(orderTask);
		fixedOrderTasks.add(orderTask);

		return planFinishDate;
	}

	private boolean belongToWorkOrder(OrderTask orderTask, WorkOrder workOrder) {
		if (workOrder == null) {
			return false;
		}
		return workOrder.getWorkOrderNo().equals(orderTask.getWorkOrderNo());
	}

	private void checkLockState(OrderTask orderTask) {
		if (orderTask.getIsLocked()) {
			orderTask.setIsLocked(false);
			orderTaskDAO.update(orderTask);
		}
	}

	@Override
	public Double getSumFinishTaskLength(String workOrderNo) {
		return orderTaskDAO.getSumFinishTaskLength(workOrderNo);
	}

	/**
	 * 新增orderTask与prodec关系表
	 * 
	 * @param userCode 用户编码
	 * @param workOrderNo 生产单号
	 * 
	 * @return
	 */
	@Override
	public void insertOrderTask(String userCode, String workOrderNo, List<String> proDecIdList) {
		orderTaskDAO.insertOrderTask(userCode, workOrderNo, proDecIdList);
	}

	/**
	 * 新增orderTask与prodec关系表
	 * 
	 * @param customerOrderItemProDec 参数对象
	 * @param userCode 用户编码
	 * @param workOrderNo 生产单号
	 * @return
	 */
	@Override
	public void insertOrderTask(CustomerOrderItemProDec customerOrderItemProDec, String orgCode, String userCode,
			String workOrderNo) {
		OrderTask orderTask = new OrderTask();
		orderTask.setOrderItemProDecId(customerOrderItemProDec.getId());
		orderTask.setContractNo(customerOrderItemProDec.getContractNo());
		orderTask.setProductCode(customerOrderItemProDec.getProductCode());
		orderTask.setProcessId(customerOrderItemProDec.getProcessId());
		orderTask.setStatus(WorkOrderStatus.TO_DO);
		orderTask.setProcessPath(customerOrderItemProDec.getProcessPath());
		orderTask.setPlanStartDate(new Date());
		orderTask.setPlanFinishDate(new Date());
		orderTask.setOperator(customerOrderItemProDec.getOperator());
		orderTask.setIsLocked(false);
		orderTask.setIsDelayed(false);
		orderTask.setWorkOrderNo(workOrderNo);
		orderTask.setTaskLength(customerOrderItemProDec.getUnFinishedLength());
		orderTask.setCreateUserCode(userCode);
		orderTask.setOrgCode(orgCode);
		orderTaskDAO.insert(orderTask);
	}

	@Override
	public void insertOrderTask(String orderItemId, String userCode, String workOrderNo) {

	}

	@Override
	public List<OrderTask> findByWoNoAndColor(String woNo, String color) {
		return orderTaskDAO.findByWoNoAndColor(woNo, color);
	}

	@Override
	public MethodReturnDto changeTask(String[] ids, String status, String equipCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		String targetStatus = status.equals("TO_DO") ? "IN_PROGRESS" : "TO_DO";
		params.put("updateStatus", targetStatus);
		params.put("ids", ids);
		params.put("equipCode", equipCode);
		params.put("oldStatus", status);
		int updateNum = orderTaskDAO.changeTask(params);
		customerOrderItemProDecService.changeTask(params);
		MethodReturnDto dto = new MethodReturnDto(true);
		if (updateNum == 0) {
			OrderTask orderTask = orderTaskDAO.getById(ids[0]);
			dto.getJsonMap().put("targetStatus", orderTask.getStatus());
			dto.getJsonMap().put("equipCode", orderTask.getEquipCode());
			dto.setSuccess(false);
		}
		return dto;
	}

	@Override
	public List<String> getOrderItemDecIds(String orderTaskId, String workOrderNo, String color) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderTaskId", orderTaskId);
		params.put("workOrderNo", workOrderNo);
		params.put("color", color);
		return orderTaskDAO.getOrderItemDecIds(params);
	}

	@Override
	public List<OrderTask> getOrderTasks(String orderTaskId, String workOrderNo, String color) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderTaskId", orderTaskId);
		params.put("workOrderNo", workOrderNo);
		params.put("color", color);
		return orderTaskDAO.getOrderTasks(params);
	}

	/**
	 * 取消生产的:ORDER_TASK
	 * */
	@Override
	public void updateOrderTaskCANCELEDStatusByNo(Map<String, Object> param) {
		orderTaskDAO.updateOrderTaskCANCELEDStatusByNo(param);
	}

	/**
	 * 更新生产单状态：1、workOrder 2、orderTask 3、proDec
	 * 
	 * @param param workOrderNo:生产单号;status:状态
	 * */
	@Override
	public void updateWorkerOrderStatus(Map<String, Object> param) {
		orderTaskDAO.updateWorkerOrderStatus(param);
	}

	@Override
	public String getWorkOrderColors(String workOrderNo) {
		return orderTaskDAO.getWorkOrderColors(workOrderNo);
	}

	@Override
	public String getContractNo(String workOrderNo) {
		return orderTaskDAO.getContractNo(workOrderNo);
	}

	@Override
	public List<OrderTask> getInProgressTask(String workOrderNo, String equipCode) {
		return orderTaskDAO.getInProgressTask(workOrderNo, equipCode);
	}

	@Override
	public int countUnFinishTask(String workOrderNo) {
		return orderTaskDAO.countUnFinishTask(workOrderNo);
	}

	@Override
	public List<OrderTask> getFinishedTask(String workOrderNo, String equipCode) {
		return orderTaskDAO.getFinishedTask(workOrderNo,equipCode);
	}

	@Override
	public OrderTask getTaskIdByCustOrderItemId(String custOrderItemId) {
		return orderTaskDAO.getTaskIdByCustOrderItemId(custOrderItemId);
	}
}
