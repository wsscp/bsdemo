package cc.oit.bsmes.pla.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JSONUtils;
import cc.oit.bsmes.pla.dao.OrderOADAO;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.pla.service.OrderOAService;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-1-23 下午5:58:06
 * @since
 * @version
 */
@Service
public class OrderOAServiceImpl extends BaseServiceImpl<OrderOA> implements OrderOAService {

	@Resource
	private OrderOADAO orderOADAO;

	/**
	 * 菜单首页主列表
	 * 
	 * @author DingXintao
	 * @date 2015-09-17
	 * @return List<OrderOA>
	 */
	@Override
	public List<OrderOA> list(Map<String, Object> findParams) {
		return orderOADAO.list(findParams);
	}

	/**
	 * 菜单首页主列表
	 * 
	 * @author DingXintao
	 * @date 2015-09-17
	 * @return List<OrderOA>
	 */
	public int count(Map<String, Object> findParams) {
		return orderOADAO.count(findParams);
	}
	
	public void setOaDate(Map<String, Object> findParams) {
		orderOADAO.setOaDate(findParams);
	}

	@Override
	public List<OrderOA> getSectionOrStructure() {
		return orderOADAO.getSectionOrStructure(getOrgCode());
	}

	/**
	 * OA查看子列表：工序分解明细/根据订单产品ID获取分解明细
	 * 
	 * @author DingXintao
	 * @date 2014-1-24 下午4:42:52
	 * @param orderItemId 客户订单明细ID
	 * @return List<OrderOA>
	 */
	@Override
	public List<OrderOA> getSubListByOrderItemId(String orderItemId) {
		return orderOADAO.getSubListByOrderItemId(orderItemId);
	}

	@Override
	public List<OrderOA> getStartAndEndDateByCustOrderItemId(String customerOrderItemId, String equipCode, String processId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerOrderItemId", customerOrderItemId);
		map.put("equipCode", equipCode);
		map.put("processId", processId);
		return orderOADAO.getStartAndEndDateByCustOrderItemId(map);
	}

	/**
	 * 获取合同产品
	 * */
	@Override
	public Multimap<String, OrderOA> getContractNo(OrderOA parm) {
		parm.setOrgCode(this.getOrgCode());
		// 1、查询出符合条件的订单任务
		List<OrderOA> workOrderTaskArray = orderOADAO.getOrderOASubPageResult(parm);
		// 2、按合同号分组
		Multimap<String, OrderOA> workOrderTaskMultimap = ArrayListMultimap.create();
		for (OrderOA workOrderTask : workOrderTaskArray) {
			workOrderTaskMultimap.put(workOrderTask.getContractNo(), workOrderTask);
		}
		return workOrderTaskMultimap;
	}

	@Override
	public List<OrderOA> getProductCodeByContractNo(String contractNo) {
		return orderOADAO.getProductCodeByContractNo(contractNo, getOrgCode());
	}

	@Override
	public List<OrderOA> findForExport(JSONObject queryParams) {
		OrderOA findParams = getOrderOA(queryParams);
		findParams.setOrgCode(getOrgCode());
		return orderOADAO.find(findParams);
	}

	private OrderOA getOrderOA(JSONObject queryParams) {
		OrderOA findParams = (OrderOA) JSONUtils.jsonToBean(queryParams, OrderOA.class);
		if (null != findParams.getTo()) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(findParams.getTo());
			calendar.add(Calendar.DATE, 1);
			findParams.setTo(calendar.getTime());
		}

		if (findParams.getQueryStatus() == null) {
			findParams.setQueryStatus(new String[] { "TO_DO", "IN_PROGRESS" });
		}
		return findParams;
	}

	@Override
	public int countForExport(JSONObject queryParams) {
		OrderOA findParams = getOrderOA(queryParams);
		findParams.setOrgCode(getOrgCode());
		return orderOADAO.count(findParams);
	}

	/**
	 * 获取手动排程订单产品下的工序
	 */
	// public List<OrderOASubPageResult> getHandScheduleOrderProcess(String
	// orderItemId) {
	// return orderOADAO.getHandScheduleOrderProcess(orderItemId);
	// }
}
