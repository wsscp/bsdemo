package cc.oit.bsmes.pla.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.InventoryDetailStatus;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.StockUseLogStatus;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.inv.model.InventoryDetail;
import cc.oit.bsmes.inv.service.InventoryDetailService;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.pla.dao.CustomerOrderItemDecDAO;
import cc.oit.bsmes.pla.model.CustomerOrderItem;
import cc.oit.bsmes.pla.model.CustomerOrderItemDec;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.InvOaUseLog;
import cc.oit.bsmes.pla.service.CustomerOrderItemDecService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.InvOaUseLogService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class CustomerOrderItemDecServiceImpl extends BaseServiceImpl<CustomerOrderItemDec> implements
		CustomerOrderItemDecService {

	@Resource
	private CustomerOrderItemDecDAO customerOrderItemDecDAO;

	@Resource
	private InventoryService inventoryService;

	@Resource
	private InventoryDetailService inventoryDetailService;

	@Resource
	private InvOaUseLogService invOaUseLogService;

	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;

	@Override
	public void insertByItem(CustomerOrderItem customerOrderItem, double standardLength, double leftLength,
			double idealMinLength) {
		while (leftLength != 0) {
			double cLength = leftLength > standardLength ? standardLength : leftLength;
			if (leftLength > standardLength && leftLength - standardLength < idealMinLength) {
				cLength = leftLength / 2;
			}

			leftLength -= cLength;

			CustomerOrderItemDec customerOrderItemDec = new CustomerOrderItemDec();
			customerOrderItemDec.setLength(cLength);
			customerOrderItemDec.setOrderItemId(customerOrderItem.getId());
			customerOrderItemDec.setStatus(ProductOrderStatus.TO_DO);
			customerOrderItemDecDAO.insert(customerOrderItemDec);
		}
	}

	@Override
	public List<CustomerOrderItemDec> getByOrderItemId(String orderItemId) {
		return customerOrderItemDecDAO.getByOrderItemId(orderItemId);
	}

	/**
	 * 
	 * 成品库存冲抵信息保存
	 * 
	 * @author JinHanyun
	 * @date 2014-2-13 下午5:40:46
	 * @param itemDecId
	 * @param inventoryDetailId
	 * @see
	 */
	private void productInventoryOffset(String itemDecId, String inventoryDetailId) {
		InventoryDetail detail = inventoryDetailService.getById(inventoryDetailId);
		Inventory inventory = inventoryService.getById(detail.getInventoryId());
		// 插入库存使用日志
		InvOaUseLog useLog = new InvOaUseLog();
		useLog.setMatCode(inventory.getMaterialCode());
		useLog.setInventoryDetailId(inventoryDetailId);
		useLog.setMatBatchNo(inventory.getBarCode());
		useLog.setUsedStockLength(detail.getLength());
		useLog.setStatus(StockUseLogStatus.USED);
		useLog.setIsProduct("0");
		useLog.setRefId(itemDecId);

		// 修改库存冻结长度
		Double lockedQuantity = inventory.getLockedQuantity();
		inventory.setLockedQuantity(lockedQuantity + detail.getLength());
		// 修改库存明细状态
		detail.setStatus(InventoryDetailStatus.FREEZE);

		inventoryDetailService.update(detail);
		inventoryService.update(inventory);
		invOaUseLogService.insert(useLog);
	}

	@Override
	@Transactional(readOnly = false)
	public void splitCustomerOrderItem(String deleteJsonData, String updateJsonData, String offsetJsonData,
			String orderItemId, String offsetItemDecId, Double offsetLength) {

		User user = SessionUtils.getUser();
		if (StringUtils.isNotBlank(deleteJsonData)) {
			JSONArray deleteArray = JSON.parseArray(deleteJsonData);
			for (Object object : deleteArray) {
				JSONObject deleteDec = (JSONObject) object;
				customerOrderItemDecDAO.deleteCusOrderItemDecById(deleteDec.getString("id"), user.getUserCode(), "1");
			}
		}

		if (StringUtils.isNotBlank(updateJsonData)) {
			JSONArray updateArray = JSON.parseArray(updateJsonData);
			for (Object object : updateArray) {
				JSONObject updateDec = (JSONObject) object;
				CustomerOrderItemDec itemDec = new CustomerOrderItemDec();
				itemDec.setOrderItemId(orderItemId);
				itemDec.setLength(updateDec.getDoubleValue("decLength"));
				itemDec.setStatus(ProductOrderStatus.TO_DO);
				if (updateDec.get("id") == null) {
					customerOrderItemDecDAO.insert(itemDec);
				} else {
					itemDec.setId(updateDec.getString("id"));
					customerOrderItemDecDAO.update(itemDec);
				}
			}
		}

		//
		if (StringUtils.isNotBlank(offsetJsonData)) {
			if (StringUtils.isNotBlank(offsetItemDecId)) {
				customerOrderItemDecDAO.deleteCusOrderItemDecById(offsetItemDecId, user.getUserCode(), "1");
			}
			CustomerOrderItemDec itemDec = new CustomerOrderItemDec();
			itemDec.setOrderItemId(orderItemId);
			itemDec.setLength(offsetLength);
			itemDec.setStatus(ProductOrderStatus.FINISHED);
			itemDec.setUseStock(true);

			customerOrderItemDecDAO.insert(itemDec);

			String[] offsetArray = offsetJsonData.split(",");
			for (String detailId : offsetArray) {
				productInventoryOffset(itemDec.getId(), detailId);
			}
		}
	}

	/**
	 * 
	 * <p>
	 * 取消库存冲抵
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-31 下午4:54:58
	 * @param itemDecId
	 * @param updator
	 * @see
	 */
	private void cancelOffSet(String itemDecId, String updator) {
		CustomerOrderItemDec itemDec = customerOrderItemDecDAO.getById(itemDecId);
		List<CustomerOrderItemProDec> proDecList = itemDec.getCusOrderItemProDesList();
		for (CustomerOrderItemProDec proDec : proDecList) {
			customerOrderItemProDecService.cancelOffset(proDec.getId(), updator);
		}
	}

	@Override
	public CustomerOrderItemDec getCurrentByWoNo(String workOrderNo) {
		return customerOrderItemDecDAO.getCurrentByWoNo(workOrderNo);
	}

	/*
	 * private void resetItemDecInventoryOffset(String itemDecId) {
	 * CustomerOrderItemDec itemDec =
	 * customerOrderItemDecDAO.getById(itemDecId); if(itemDec.getUseStock() !=
	 * null && itemDec.getUseStock()){ List<InvOaUseLog> useLogList =
	 * invOaUseLogService.getByRefId(itemDecId); if(useLogList.size() > 0){
	 * //若为成品则在库存使用日志中只有一条记录 InvOaUseLog useLog = useLogList.get(0);
	 * //修改库存明细状态为可用 InventoryDetail detail = useLog.getInventoryDetail();
	 * detail.setStatus(InventoryDetailStatus.AVAILABLE); //修改库存冻结长度 Inventory
	 * inventory = detail.getInventory(); Double lockedQuantity =
	 * inventory.getLockedQuantity(); inventory.setLockedQuantity(lockedQuantity
	 * - detail.getLength());
	 * 
	 * //修改库存使用日志状态 useLog.setStatus(StockUseLogStatus.CANCELED);
	 * 
	 * inventoryService.update(inventory);
	 * inventoryDetailService.update(detail); invOaUseLogService.update(useLog);
	 * } }else{ List<CustomerOrderItemProDec> proDecList =
	 * itemDec.getCusOrderItemProDesList(); for(CustomerOrderItemProDec
	 * proDec:proDecList){ List<InvOaUseLog> useLogList =
	 * proDec.getInventoryUseLogs(); for(InvOaUseLog useLog:useLogList){
	 * InventoryDetail detail = useLog.getInventoryDetail(); if(detail != null){
	 * detail.setStatus(InventoryDetailStatus.AVAILABLE); Inventory inventory =
	 * detail.getInventory(); Double lockedQuantity =
	 * inventory.getLockedQuantity(); inventory.setLockedQuantity(lockedQuantity
	 * - detail.getLength()); inventoryService.update(inventory);
	 * inventoryDetailService.update(detail); }
	 * useLog.setStatus(StockUseLogStatus.CANCELED);
	 * invOaUseLogService.update(useLog); }
	 * proDec.setStatus(ProductOrderStatus.INVALID);
	 * customerOrderItemProDecService.update(proDec); } } }
	 */

	@Override
	@Transactional(readOnly = false)
	public void deleteCusOrderItemDecById(String itemDecId, String updator, String isDelItemDec) {
		customerOrderItemDecDAO.deleteCusOrderItemDecById(itemDecId, updator, isDelItemDec);
	}

	/**
	 * 获取未完成的订单明细分卷：根据订单明细ID
	 * 
	 * @author DingXintao
	 * @param customerOrderItemId 订单明细ID
	 */
	@Override
	public List<CustomerOrderItemDec> getUncompleteOrderItemDecByItemId(String customerOrderItemId) {
		return customerOrderItemDecDAO.getUncompleteOrderItemDecByItemId(customerOrderItemId);
	}

	@Override
	public String getCustomerOrderItemDecIdByOrderItemId(String orderItemId) {
		return customerOrderItemDecDAO.getCustomerOrderItemDecIdByOrderItemId(orderItemId);
	}

	/**
	 * 新的导入方法： 不作分卷，分卷表只有一条数据，不拆分
	 * */
	@Override
	public void insertByItemToItemDec(CustomerOrderItem customerOrderItem, double standardLength, double leftLength,
			double idealMinLength) {
		CustomerOrderItemDec customerOrderItemDec = new CustomerOrderItemDec();
		customerOrderItemDec.setLength(leftLength);
		customerOrderItemDec.setOrderItemId(customerOrderItem.getId());
		customerOrderItemDec.setStatus(ProductOrderStatus.TO_DO);
		customerOrderItemDecDAO.insert(customerOrderItemDec);
	}

}
