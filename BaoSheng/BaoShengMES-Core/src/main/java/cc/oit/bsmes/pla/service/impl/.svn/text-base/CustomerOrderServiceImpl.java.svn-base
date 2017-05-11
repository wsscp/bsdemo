package cc.oit.bsmes.pla.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.common.constants.CustomerOrderStatus;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.exception.IllegalArgumentException;
import cc.oit.bsmes.common.exception.UnsupportedOperationException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.NoGenerator;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.ord.dao.SalesOrderItemDAO;
import cc.oit.bsmes.ord.model.SalesOrder;
import cc.oit.bsmes.ord.model.SalesOrderItem;
import cc.oit.bsmes.ord.service.SalesOrderItemService;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.dao.CustomerOrderDAO;
import cc.oit.bsmes.pla.dao.CustomerOrderItemDAO;
import cc.oit.bsmes.pla.dao.ProductDAO;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.ImportProduct;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemService;
import cc.oit.bsmes.pla.service.CustomerOrderService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.service.ProductCraftsService;

import com.alibaba.fastjson.JSONObject;

@Service
public class CustomerOrderServiceImpl extends BaseServiceImpl<CustomerOrder> implements CustomerOrderService {

	@Resource
	private CustomerOrderDAO customerOrderDAO;
	@Resource
	private CustomerOrderItemDAO customerOrderItemDAO;
	@Resource
	private CustomerOrderItemDecService customerOrderItemDecService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private CustomerOrderItemService customerOrderItemService;
	@Resource
	private SalesOrderItemService salesOrderItemService;
	@Resource
	private ProductCraftsService productCraftsService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private EventInformationService eventInformationService;
	@Resource
	private SalesOrderService salesOrderService;
	@Resource
	private ProductDAO productDAO;
//	@Resource
//	private CustomerOrderService customerOrderService;
	@Resource
	private SalesOrderItemDAO salesOrderItemDAO;

	public List<CustomerOrder> getBySalesOrderId(String salesOrderId) {
		return customerOrderDAO.getBySalesOrderId(salesOrderId);
	}

	@Override
	@Transactional(readOnly = false)
	public CustomerOrder insert(SalesOrder salesOrder) {
		CustomerOrder customerOrder = null;
		List<CustomerOrder> cos= customerOrderDAO.getBySalesOrderId(salesOrder.getId());
		if(!CollectionUtils.isEmpty(cos)){
			customerOrder = cos.get(0);
		}else{
			customerOrder = new CustomerOrder();
			customerOrder.setSalesOrderId(salesOrder.getId());
			customerOrder.setCustomerOrderNo(NoGenerator.generateNoByDate());
			customerOrder.setRemarks(salesOrder.getRemarks());
			customerOrder.setStatus(CustomerOrderStatus.TO_DO);
			customerOrder.setOrgCode(salesOrder.getOrgCode());
			customerOrder.setFixedOa(false);
			if (customerOrderDAO.insert(customerOrder) != 1) {
				throw new DataCommitException();
			}
		}

		List<SalesOrderItem> salesOrderItems = salesOrderItemService.getBySalesOrderId(salesOrder.getId());
		for (SalesOrderItem salesOrderItem : salesOrderItems) {
			CustomerOrderItem customerOrderItem = new CustomerOrderItem();
			customerOrderItem.setSalesOrderItemId(salesOrderItem.getId());
			CustomerOrderItem item = customerOrderItemDAO.getOne(customerOrderItem);
			if(item != null){
				continue;
			}
			customerOrderItem.setCustomerOrderId(customerOrder.getId());
			customerOrderItem.setStatus(ProductOrderStatus.TO_DO);
			customerOrderItem.setProductCode(salesOrderItem.getProductCode());
			customerOrderItem.setOrderLength(salesOrderItem.getSaleorderLength());
			customerOrderItem.setContractLength(salesOrderItem.getContractLength());
			customerOrderItem.setRemarks(salesOrder.getRemarks());
			boolean isFirstTime = orderTaskService.checkFirstTime(salesOrderItem.getProductCode(),
					salesOrder.getContractNo());
			customerOrderItem.setIsFirstTime(isFirstTime);
			
			// 导入订单首选工艺为：最近的该种产品所使用的工艺，如果都没有则，选择默认工艺
			
			ProductCrafts craft = productCraftsService.getLastOrderUserdCrafts(salesOrderItem.getProductCode());
			if(craft == null){
				craft = productCraftsService.getByProductCode(salesOrderItem.getProductCode());
			}
			if(craft != null){
				customerOrderItem.setCraftsId(craft.getId());
			}
			if (customerOrderItemDAO.insert(customerOrderItem) != 1) {
				throw new DataCommitException();
			}

			double standardLength = salesOrderItem.getStandardLength();
			double leftLength = salesOrderItem.getSaleorderLength();
			double idealMinLength = salesOrderItem.getIdealMinLength();

			customerOrderItemDecService.insertByItem(customerOrderItem, standardLength, leftLength, idealMinLength);
			
			// 分解订单明细到工序
			customerOrderItemProDecService.splitOrderByOrderItemId(customerOrderItem.getId(),
					SessionUtils.getUser().getOrgCode());
		}

		return customerOrder;
	}

	@Override
	public List<CustomerOrder> split(CustomerOrder customerOrder, int targetCount) {
		if (targetCount <= 1) {
			throw new IllegalArgumentException("pla.split.targetCount");
		}
		// 检查状态
		if (!checkIfLocked(customerOrder)) {
			throw new UnsupportedOperationException("pla.split.unsupported");
		}

		List<CustomerOrder> splited = new ArrayList<CustomerOrder>();
		// 拆分订单
		customerOrderDAO.split(customerOrder.getId(), targetCount, SessionUtils.getUser().getUserCode(), splited);
		for (CustomerOrder order : splited) {
			order.setOrderItems(customerOrderItemService.getByCusOrderId(order.getId()));
		}

		return splited;
	}

	private boolean checkIfLocked(CustomerOrder customerOrder) {
		CustomerOrderStatus status = customerOrder.getStatus();
		if (status != CustomerOrderStatus.TO_DO) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteById(String id) throws DataCommitException {
		delete(customerOrderDAO.getById(id));
	}

	@Override
	public void deleteById(List<String> list) throws DataCommitException {
		throw new UnsupportedOperationException();
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(CustomerOrder customerOrderToDelete) throws DataCommitException {
		// 检查状态
		if (!checkIfLocked(customerOrderToDelete)) {
			throw new UnsupportedOperationException("pla.deleteUnsupported.status");
		}

		CustomerOrder orderToAddQuantity = null; // 查找合适的接收被删除数量的订单
		List<CustomerOrder> customerOrders = getBySalesOrderId(customerOrderToDelete.getSalesOrderId());
		int size = customerOrders.size();
		CustomerOrder lastOrder = customerOrders.get(size - 1);
		if (size <= 1) { // 只有一个订单不能再删除
			throw new UnsupportedOperationException("pla.deleteUnsupported.onlyOne");
		} else if (lastOrder.getId().equals(customerOrderToDelete.getId())) { // lastOrder就是要删除的订单
			// 获取倒数第二个
			orderToAddQuantity = customerOrders.get(size - 2);
			// 检查状态
			if (!checkIfLocked(orderToAddQuantity)) {
				throw new UnsupportedOperationException("pla.deleteUnsupported.onlyOne");
			}
		} else { // lastOrder就是合适接收的订单，不需要检查状态
			orderToAddQuantity = lastOrder;
		}

		// 合并数量、同时删除订单
		customerOrderDAO.deleteAndMerge(customerOrderToDelete.getId(), orderToAddQuantity.getId(), SessionUtils
				.getUser().getUserCode());
	}

	@Override
	public List<CustomerOrder> findByOrderInfo(Map<String, Object> findParams, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
        findParams.put("orgCode",getOrgCode());
		return customerOrderDAO.findByOrderInfo(findParams);
	}

	@Override
	public int countByOrderInfo(Map<String, Object> findParams) {
		return customerOrderDAO.countByOrderInfo(findParams);
	}

	@Override
	public int countForSetPriority(CustomerOrder findParams) {
		return customerOrderDAO.countForSetPriority(findParams);
	}

	@Override
	public void setCustomerOaDate(CustomerOrder customerOrder, Date customerOaDate) {
		customerOrder.setCustomerOaDate(customerOaDate);
		update(customerOrder);
		CustomerOrderItem findParams = new CustomerOrderItem();
		findParams.setCustomerOrderId(customerOrder.getId());
		List<CustomerOrderItem> items = customerOrderItemDAO.get(findParams);
		for (CustomerOrderItem item : items) {
			item.setSubOaDate(customerOaDate);
			customerOrderItemService.update(item);
		}
	}

	@Override
	public List<CustomerOrder> findForSetPriority(CustomerOrder findParams, int start, int limit, List<Sort> sortList) {
		if (findParams.getSeq() == null) {
			SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		}
        findParams.setOrgCode(getOrgCode());
		return customerOrderDAO.findForSetPriority(findParams);
	}

	@Override
	public List<CustomerOrder> findForExport(JSONObject parameterMap) {
		addLikeForQueryParams(parameterMap);
        parameterMap.put("orgCode",getOrgCode());
		return customerOrderDAO.findByOrderInfo(parameterMap);
	}

	@Override
	public int countForExport(JSONObject parameterMap) {
		addLikeForQueryParams(parameterMap);
		return customerOrderDAO.countByOrderInfo(parameterMap);
	}

	private void addLikeForQueryParams(JSONObject parameterMap) {
		if (parameterMap != null) {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			Iterator<String> it = parameterMap.keySet().iterator();
			String key = "";
			while (it.hasNext()) {
				key = it.next();
				if (parameterMap.get(key) != null) {
					if (StringUtils.containsIgnoreCase(key, "exceedOa")) {
						if (!"java.lang.String".equals(parameterMap.get(key).getClass().getName())) {
							parameterMap.put(key, null);
						}
					} else if (StringUtils.containsIgnoreCase(key, "startDate")
							|| StringUtils.containsIgnoreCase(key, "endDate")) {
						if (StringUtils.isNotBlank(parameterMap.getString(key))) {
							try {
								parameterMap.put(key, f.parse(parameterMap.getString(key)));
							} catch (ParseException e) {
							}
						} else {
							parameterMap.put(key, null);
						}
					} else if (StringUtils.containsIgnoreCase(key, "orderStatus")) {
						if ("java.lang.String".equals(parameterMap.get(key).getClass().getName())) {
							if (StringUtils.isNotBlank(parameterMap.getString(key))) {
								parameterMap.put(key, new String[] { parameterMap.getString(key) });
							} else {
								parameterMap.put(key, new String[] { "TO_DO", "IN_PROGRESS", "CANCELED", "FINISHED" });
							}
						}
					} else if (StringUtils.containsIgnoreCase(key, "section")) {
						if (StringUtils.isNotBlank(parameterMap.getString(key))) {
							parameterMap.put(key, parameterMap.getIntValue(key));
						} else {
							parameterMap.put(key, null);
						}
					} else {
						if (parameterMap.get(key) != null && StringUtils.isNotBlank(parameterMap.getString(key))) {
							parameterMap.put(key, "%" + parameterMap.getString(key) + "%");
						} else {
							parameterMap.put(key, null);
						}
					}
				}
			}
		}
	}

	/**
	 * 按钮确认交货期
	 * 
	 * @author DingXintao
	 * @date 2014-10-27 17:50:58
	 * @param customerOrderId
	 */
	public void confirmOrderDeliver(String customerOrderId) {
		// 确认后改生产单状态为TO_DO并且固定oa字段设置为true
		CustomerOrder cusOrder = new CustomerOrder();
		cusOrder.setFixedOa(true);
		cusOrder.setConfirmDate(new Date());
		cusOrder.setId(customerOrderId);
		customerOrderDAO.update(cusOrder);

		// 判断是否延期，延期则发生警报
		cusOrder = customerOrderDAO.getById(customerOrderId);
		if (null != cusOrder.getCustomerOaDate() && null != cusOrder.getPlanFinishDate()
				&& cusOrder.getPlanFinishDate().after(cusOrder.getCustomerOaDate())) {
			String salesOrderId = cusOrder.getSalesOrderId();
			SalesOrder salesOrder = salesOrderService.getById(salesOrderId);
			if (null != salesOrder) {
				EventInformation eventInformation = new EventInformation();
				eventInformation.setCode(EventTypeContent.OT.name());
				eventInformation.setEventTitle("合同超交期");
				eventInformation.setEventContent("合同号:" + salesOrder.getContractNo() + ",超指定交期");
				eventInformation.setEventStatus(EventStatus.UNCOMPLETED);
				eventInformationService.insertInfo(eventInformation);
			}
		}

	}
	
	
	/**
	 * 手动排程：查找可排序的订单
	 * 
	 * @author DingXintao
	 * @param findParams
	 *            参数
	 * @param productCode
	 * */
	@Override
	public List<CustomerOrder> findForHandSetPriority(CustomerOrder findParams) {
        findParams.setOrgCode(getOrgCode());
		return customerOrderDAO.findForHandSetPriority(findParams);
	}
	
	
	/**
	 * 获取未完成的订单：包含了排序
	 * 
	 * @author DingXintao
	 * @param orgCode 组织编码
	 */
	@Override
	public List<CustomerOrder> getUncompleteCustomerOrder(String orgCode){
		return customerOrderDAO.getUncompleteCustomerOrder(orgCode);
	}
	
	 /**
     * 新的导入方法： 不作分卷，分卷表只有一条数据，不拆分
     * 
     * @param salesOrder 订单列表
	 * @param orderItemArray 订单产品存放list，用于解决性能问题，减少数据库查询
	 * @param productCraftsCache 存放产品编码对应工艺ID的缓存，用于解决性能问题，减少数据库查询 
     * */
	@Override
	@Transactional(readOnly = false)
	public void insertToItemDec(SalesOrder salesOrder, List<SalesOrderItem> orderItemArray, Map<String, String> productCraftsCache) {
		CustomerOrder customerOrder = null;
		List<CustomerOrder> cos= customerOrderDAO.getBySalesOrderId(salesOrder.getId());
		if(!CollectionUtils.isEmpty(cos)){
			customerOrder = cos.get(0);
		}else{
			customerOrder = new CustomerOrder();
			customerOrder.setSalesOrderId(salesOrder.getId());
			customerOrder.setCustomerOrderNo(NoGenerator.generateNoByDate());
			customerOrder.setRemarks(salesOrder.getRemarks());
			customerOrder.setStatus(CustomerOrderStatus.TO_DO);
			customerOrder.setOrgCode(salesOrder.getOrgCode());
			customerOrder.setFixedOa(false);
			customerOrder.setOaDate(salesOrder.getConfirmDate());
			if (customerOrderDAO.insert(customerOrder) != 1) {
				throw new DataCommitException();
			}
		}

		for (SalesOrderItem salesOrderItem : orderItemArray) {
			CustomerOrderItem customerOrderItem = new CustomerOrderItem();
			customerOrderItem.setSalesOrderItemId(salesOrderItem.getId());
			CustomerOrderItem item = customerOrderItemDAO.getOne(customerOrderItem);
			if(item != null){
				continue;
			}
			customerOrderItem.setCustomerOrderId(customerOrder.getId());
			customerOrderItem.setStatus(ProductOrderStatus.TO_DO);
			customerOrderItem.setProductCode(salesOrderItem.getProductCode());
			customerOrderItem.setOrderLength(salesOrderItem.getSaleorderLength());
			customerOrderItem.setContractLength(salesOrderItem.getContractLength());
			customerOrderItem.setRemarks(salesOrder.getRemarks());
			boolean isFirstTime = orderTaskService.checkFirstTime(salesOrderItem.getProductCode(),
					salesOrder.getContractNo());
			customerOrderItem.setIsFirstTime(isFirstTime);
			
			// 导入订单首选工艺为：最近的该种产品所使用的工艺，如果都没有则，选择默认工艺
			String craftsId = productCraftsCache.get(salesOrderItem.getProductCode());
			String productType= salesOrderItem.getProductType(); // 产品型号
			String productSpec = salesOrderItem.getProductSpec(); // 产品规格
			if(StringUtils.isEmpty(craftsId)){
				ProductCrafts craft = productCraftsService.getLastOrderUserdCrafts(salesOrderItem.getProductCode());
				if(craft == null){
					craft = productCraftsService.getLastOrderUserdCrafts(salesOrderItem.getProductCode().replaceAll("(\\(.+\\))", ""));
				}
				if(craft == null){
//					craft = productCraftsService.getByProductCode(salesOrderItem.getProductCode());
					craftsId = productCraftsService.getCraftIdByProductCode(salesOrderItem.getProductCode());
					if(StringUtils.isEmpty(craftsId)){
						productType = productType.replaceAll("(\\(.+\\))", "");
						productSpec = productSpec.replaceAll("(\\(.+\\))", "");
						Product product = productDAO.getByProductTypeAndSpec(productType,productSpec);
						if(product!=null){
							craftsId = productCraftsService.getCraftIdByProductCode(product.getProductCode());
						}
					}
				}else{
					craftsId = craft.getId();
					productCraftsCache.put(salesOrderItem.getProductCode(), craftsId);
				}
//				if(StringUtils.isEmpty(craftsId)){
//					productCraftsCache.put(salesOrderItem.getProductCode(), craftsId);
//				}
			}
			customerOrderItem.setCraftsId(craftsId);
			salesOrderItem.setWiresStructure(getWiresStructure(craftsId));
			salesOrderItemDAO.update(salesOrderItem);
			if (customerOrderItemDAO.insert(customerOrderItem) != 1) {
				throw new DataCommitException();
			}

			double standardLength = salesOrderItem.getStandardLength();
			double leftLength = salesOrderItem.getSaleorderLength();
			double idealMinLength = salesOrderItem.getIdealMinLength();

			customerOrderItemDecService.insertByItemToItemDec(customerOrderItem, standardLength, leftLength, idealMinLength);
			
		}
	}

	@Override
	public String getWiresStructure(String productCode) {
		return customerOrderDAO.getWiresStructure(productCode);
	}

	@Override
	public List<ImportProduct> getImportProduct(Map<String, Object> param,
			int start, int limit, List<Sort> sortArray) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return customerOrderDAO.getImportProduct(param);
	}

	@Override
	public int countImportProduct(Map<String, Object> param) {
		return customerOrderDAO.countImportProduct(param);
	}
}
