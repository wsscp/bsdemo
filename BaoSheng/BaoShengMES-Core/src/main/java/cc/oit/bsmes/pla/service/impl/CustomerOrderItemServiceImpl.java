package cc.oit.bsmes.pla.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.WorkTaskService;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.pla.dao.CustomerOrderItemDAO;
import cc.oit.bsmes.pla.dto.CraftDto;
import cc.oit.bsmes.pla.dto.ProcessDto;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.CustomerOrderItemInfo;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecOAService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderPlanService;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.OaMrpService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProcessInOutService;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.dto.SectionLength;
import cc.oit.bsmes.wip.model.Section;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.SectionService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class CustomerOrderItemServiceImpl extends BaseServiceImpl<CustomerOrderItem> implements CustomerOrderItemService {
	@Resource
	private CustomerOrderItemDAO customerOrderItemDAO;
	@Resource
	private SectionService sectionService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private CustomerOrderService customerOrderService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private CustomerOrderPlanService customerOrderPlanService;
	@Resource
	private CustomerOrderItemDecService customerOrderItemDecService;
	@Resource
	private CustomerOrderItemProDecOAService customerOrderItemProDecOAService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private SalesOrderItemService salesOrderItemService;
	@Resource
	private EquipCalendarService equipCalendarService;
	@Resource
	private WorkTaskService workTaskService;
	@Resource
	private OaMrpService oaMrpService;
	@Resource
	private ProcessInOutService processInOutService;
	@Resource
	private EventInformationService eventInformationService;

	@Override
	public List<CustomerOrderItem> getByCusOrderId(String cusOrderId) {
		CustomerOrderItem findParams = new CustomerOrderItem();
		findParams.setCustomerOrderId(cusOrderId);
		return customerOrderItemDAO.get(findParams);
	}

	@Override
	public List<CustomerOrderItem> getUnLocked(String orgCode) {
		return customerOrderItemDAO.getUnLocked(orgCode);
	}

	@Override
	public List<CustomerOrderItem> getFirstTime(String orgCode) {
		return customerOrderItemDAO.getFirstTime(orgCode);
	}

	@Override
	@Transactional(readOnly = false)
	public void cancel(String id) {
		List<WorkOrder> unfinishedWorkOrders = new ArrayList<WorkOrder>();
		customerOrderItemDAO.cancel(id, SessionUtils.getUser().getUserCode(), unfinishedWorkOrders);
		Date now = Calendar.getInstance().getTime();
		for (WorkOrder workOrder : unfinishedWorkOrders) {
			// 重新绑定生产中订单的goodlength - begin
			// 获取取消订单生产出的goodLength
			List<Section> sections = sectionService.getByOrderItemIdAndWoNo(id, workOrder.getWorkOrderNo());
			// 获取生产单未完成的订单项
			List<OrderTask> orderTasks = orderTaskService.getUncompletedByWoNo(workOrder.getWorkOrderNo());

			if (orderTasks.size() == 0) {
				finishWorkOrder(now, workOrder);
			} else {
				for (OrderTask orderTask : orderTasks) {
					matchForOrderTask(sections, orderTask);
				}
			}
			orderTasks = orderTaskService.getUncompletedByWoNo(workOrder.getWorkOrderNo());

			if (orderTasks.size() == 0) {
				// 已完成的WorkOrder
				finishWorkOrder(now, workOrder);
			}
			// 重新绑定生产中订单的goodlength - end
		}
	}

	private void finishWorkOrder(Date now, WorkOrder workOrder) {
		workOrder.setStatus(WorkOrderStatus.FINISHED);
		workOrder.setRealEndTime(now);
		workOrderService.update(workOrder);
	}

	private void matchForOrderTask(List<Section> sections, OrderTask orderTask) {
		Map<OrderTask, Map<String, List<Double>>> completedLengthsMap = new HashMap<OrderTask, Map<String, List<Double>>>();
		CustomerOrderItemProDec customerOrderItemProDec = customerOrderItemProDecService.getById(orderTask.getOrderItemProDecId());
		// 获取该客户订单所有已完成长度
		Map<String, List<Double>> completedLengths = completedLengthsMap.get(orderTask);
		if (completedLengths == null) {
			completedLengths = sectionService.getCompletedLengths(customerOrderItemProDec.getOrderItemDecId());
			completedLengthsMap.put(orderTask, completedLengths);
		}
		// 获取可以冲抵到该客户订单的长度
		List<? extends SectionLength> matchedSections = sectionService.getMatchedSections(sections, completedLengths,
				customerOrderItemProDec, true);
		for (SectionLength sectionLength : matchedSections) {
			Section section = (Section) sectionLength;
			section.setOrderItemProDecId(customerOrderItemProDec.getId());
			sectionService.update(section);

			sections.remove(section);// 从未绑定的集合中删除
		}
		if (customerOrderItemProDec.getUnFinishedLength() == 0) {
			customerOrderItemProDec.setStatus(ProductOrderStatus.FINISHED);
			orderTask.setStatus(WorkOrderStatus.FINISHED);
			orderTaskService.update(orderTask);
		}
		customerOrderItemProDecService.update(customerOrderItemProDec);
	}

	// @Override
	// public List<CustomerOrderItemInfo> findByOrderInfo(
	// CustomerOrderItemInfo findParams,int start, int limit) {
	// SqlInterceptor.setRowBounds(new RowBounds(start, limit));
	// return null;
	// }

	@Override
	public int countByOrderInfo(CustomerOrderItemInfo findParams) {
		return 0;
	}

	@Override
	public CustomerOrderItem getByOrderItemId(String orderItemId) {
		return customerOrderItemDAO.getBySalesOrderItemId(orderItemId);
	}

	@Override
	public JSONArray craftProcessHandle(String productCode) throws IllegalAccessException, InvocationTargetException {
		CraftDto craftDto = new CraftDto();
		JSONArray jsonArray = new JSONArray();
		ProductCrafts productCrafts = productCraftsService.getByProductCode(productCode);
		if (productCrafts != null) {
			List<ProductProcess> proProcessesList = productProcessService.getByProductCode(productCrafts.getProductCode());
			customerOrderPlanService.convertCraft(craftDto, productCrafts, proProcessesList, null);
			if (craftDto.getProcess().getNextProcess() == null) {
				JSONObject jsonObject = new JSONObject();
				convertProcessToTree(craftDto.getProcess(), jsonObject);
				jsonArray.add(jsonObject);
			} else {
				throw new MESException("pla.customerOrderItem.ProductProcessSeqWrong");
			}
		}
		return jsonArray;
	}

	/**
	 * 
	 * 递归工艺流程树，把数据放到JSON对象中
	 * 
	 * @author JinHanyun
	 * @date 2014-1-22 下午3:20:08
	 * @param processDto
	 * @param jsonObject
	 * @see
	 */
	private void convertProcessToTree(ProcessDto processDto, JSONObject jsonObject) {
		jsonObject.put("text", processDto.getProcessName() + "[" + processDto.getProcessCode() + "]");
		jsonObject.put("id", processDto.getId());
		jsonObject.put("leaf", processDto.getPreProcesses().size() == 0);

		if (!CollectionUtils.isEmpty(processDto.getPreProcesses())) {
			JSONArray array = new JSONArray();
			for (ProcessDto proProcessDto : processDto.getPreProcesses()) {
				JSONObject objct = new JSONObject();
				convertProcessToTree(proProcessDto, objct);
				array.add(objct);
			}
			jsonObject.put("children", array);
		} else {
			jsonObject.put("leaf", false);
		}
	}

	@Override
	public List<CustomerOrderItem> findByOrderIdAndSalesOrderItemInfo(CustomerOrderItem findParams) {
		return customerOrderItemDAO.findByOrderIdAndSalesOrderItemInfo(findParams);
	}

	@Override
	@Transactional(readOnly = false)
	public void itemSplit(String itemId, String subOrderLength) {
		if (StringUtils.isBlank(subOrderLength)) {
			throw new MESException("");
		}

		User user = SessionUtils.getUser();
		CustomerOrderItem orderItem = getById(itemId);
		SalesOrderItem salesOrderItem = orderItem.getSalesOrderItem();
		if (salesOrderItem == null) {
			salesOrderItem = salesOrderItemService.getById(salesOrderItem.getSalesOrderId());
		}

		List<CustomerOrderItemDec> itemDecs = orderItem.getCusOrderItemDesc();
		for (CustomerOrderItemDec itemDec : itemDecs) {
			customerOrderItemDecService.deleteCusOrderItemDecById(itemDec.getId(), user.getUserCode(), "1");
		}
		delete(orderItem);
		salesOrderItemService.delete(salesOrderItem);

		CustomerOrderItem newOrderItem = copyOrderItem(orderItem, salesOrderItem);

		String[] orderItemLengthArray = subOrderLength.split(",");
		for (String orderItemLength : orderItemLengthArray) {
			double itemLen = Double.parseDouble(orderItemLength);

			salesOrderItem.setId(null);
			salesOrderItem.setSaleorderLength(itemLen);
			salesOrderItemService.insert(salesOrderItem);

			newOrderItem.setId(null);
			newOrderItem.setOrderLength(itemLen);
			newOrderItem.setSalesOrderItemId(salesOrderItem.getId());
			insert(newOrderItem);

			customerOrderItemDecService.insertByItem(newOrderItem, salesOrderItem.getStandardLength(), salesOrderItem.getSaleorderLength(),
					salesOrderItem.getIdealMinLength());
		}
	}

	private CustomerOrderItem copyOrderItem(CustomerOrderItem orderItem, SalesOrderItem salesOrderItem) {
		CustomerOrderItem newOrderItem = new CustomerOrderItem();
		newOrderItem.setCustomerOrderId(orderItem.getCustomerOrderId());
		newOrderItem.setStatus(ProductOrderStatus.TO_DO);
		newOrderItem.setSubOaDate(orderItem.getSubOaDate());
		newOrderItem.setCraftsId(orderItem.getCraftsId());
		newOrderItem.setProductCode(orderItem.getProductCode());
		return newOrderItem;
	}

	@Override
	public CustomerOrderItem getByWorkOrderNoAndContractNo(String workOrderNo, String contractNo) {
		return customerOrderItemDAO.getByWorkOrderNoAndContractNo(workOrderNo, contractNo);
	}

	/**
	 * 下发生产单主列表
	 * 
	 * */
	public List<Map<String, String>> getHandScheduleOrder(Map<String, Object> param) {
		return customerOrderItemDAO.getHandScheduleOrder(param);
	}

	/**
	 * 下发生产单主列表:计数
	 * 
	 * */
	public Integer countHandScheduleOrder(Map<String, Object> param) {
		return customerOrderItemDAO.countHandScheduleOrder(param);
	}

	/**
	 * 获取未完成的订单明细：根据订单ID
	 * 
	 * @author DingXintao
	 * @param customerOrderId 订单ID
	 */
	@Override
	public List<CustomerOrderItem> getUncompleteOrderItemByOrderId(String customerOrderId) {
		return customerOrderItemDAO.getUncompleteOrderItemByOrderId(customerOrderId);
	}

	/**
	 * 根据生产单ID查看生产单明细 - 关于工序明细的 - 新通用方法
	 * 
	 * @param workOrderNo 生产单编号
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> showWorkOrderDetailCommon(String workOrderNo) {
		return customerOrderItemDAO.showWorkOrderDetailCommon(workOrderNo);
	}

	/**
	 * 获取可以手动排程的订单: 成缆的主列表 - 点击排生产单弹出编辑框时所需要的订单数据
	 * 
	 * @param param 生产单ID数组
	 * @return List<Map<String, String>>
	 * */
	@Override
	public List<Map<String, String>> getOrderData(Map<String, Object> param) {
		return customerOrderItemDAO.getOrderData(param);
	}

	/**
	 * 获取没有完成的订单产品：默认最新导入的300个有工序的订单
	 * 
	 * @param orgCode 组织编码
	 * @param limit 数量限制：默认300
	 * @return List<CustomerOrderItem>
	 */
	@Override
	public List<CustomerOrderItem> getUncompleted(String orgCode, Integer limit) {
		Map<String, Object> params = new HashMap<String, Object>();
		List<ProductOrderStatus> statusArray = new ArrayList<ProductOrderStatus>();
		statusArray.add(ProductOrderStatus.TO_DO);
		statusArray.add(ProductOrderStatus.IN_PROGRESS);
		if (null == limit) {
			limit = 300;
		}
		params.put("orgCode", orgCode);
		params.put("limit", limit);
		params.put("statusArray", statusArray);
		return customerOrderItemDAO.getUncompleted(params);
	}

	/**
	 * 校验是否已经下单：云母、挤出、火花
	 * 
	 * @param orderItemId 订单产品ID
	 * */
	@Override
	public Map<String, Object> validateHasAuditOrder(String orderItemId){
		return customerOrderItemDAO.validateHasAuditOrder(orderItemId);
	}

	@Override
	public List<Map<String, String>> getHandScheduleOrderTemp(
			Map<String, Object> param,Integer start,Integer limit) {
		
		 /* if (start != null && limit != null) {
	            SqlInterceptor.setRowBounds(new RowBounds(start, limit));
	        }	*/	
		return customerOrderItemDAO.getHandScheduleOrderTemp(param);
	}

	@Override
	public void tempSave(String orderItemIds) {
		String[] orderItemId=orderItemIds.split(",");
		for(String custId:orderItemId){
			List <CustomerOrderItem> lists=customerOrderItemDAO.checkExistsCustIdTemp(custId);
			if(lists.size()==0){
				String creatorUserCode=SessionUtils.getUser().getCreateUserCode();
				customerOrderItemDAO.insertCustIdTemp(custId,creatorUserCode);
			}else{
				
			}
		
		}
		
	}

	@Override
	public Integer countHandScheduleOrderTemp(Map<String, Object> param) {
		return customerOrderItemDAO.countHandScheduleOrderTemp(param);
	}
	
	/**
	 * 获取最新一条记录，时间最大的一条
	 * 
	 * @author DingXintao
	 * @date 2016-2-24
	 * @return CustomerOrderItem
	 */
	public CustomerOrderItem getLatestOrder(){
		return customerOrderItemDAO.getLatestOrder();
	}

	@Override
	public void clearTemp(String orderItemIds) {
		List<String> orderItemId=Arrays.asList(orderItemIds.split(","));
		Map<String,List<String>> param=new HashMap<String,List<String>>();
		param.put("orderItemId", orderItemId);
		customerOrderItemDAO.deleteTempInfo(param);
		
		
	}

	@Override
	public CustomerOrderItem getBySalesOrderItemId(String salesOrderItemId) {
		return customerOrderItemDAO.getBySalesOrderItemId(salesOrderItemId);
	}

	@Override
	public List getProductManageList(Map<String, Object> param) {
		return customerOrderItemDAO.getProductManageList(param);
	}

	@Override
	public Integer countProductManageList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return customerOrderItemDAO.countProductManageList(param);
	}

	@Override
	public void finishedOrderItem(String id,String userCode) {
		// TODO Auto-generated method stub
		customerOrderItemDAO.finishedOrderItem(id,userCode);
	}

	@Override
	public List<Map<String,String>> getGWInGrocessOrders() {
		// TODO Auto-generated method stub
		return customerOrderItemDAO.getGWInGrocessOrders();
	}

	@Override
	public List<Map<String, String>> getGWHisWorkOrders(String cusItemId) {
		// TODO Auto-generated method stub
		return customerOrderItemDAO.getGWHisWorkOrders(cusItemId);
	}
}
