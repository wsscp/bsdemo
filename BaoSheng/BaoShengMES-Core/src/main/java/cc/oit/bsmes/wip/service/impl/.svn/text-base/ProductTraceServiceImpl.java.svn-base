package cc.oit.bsmes.wip.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamHistoryAcquisition;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.wip.dao.ProductTraceDAO;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ProductTraceService;
import cc.oit.bsmes.wip.service.WorkOrderService;

@Service
public class ProductTraceServiceImpl implements ProductTraceService {

	@Resource
	private ProductTraceDAO productTraceDAO;
	@Resource
	private DataAcquisitionService dataAcquisitionService;
	@Resource
	private WorkOrderService workOrderService;
	
	/**
	 * @Title:       getLiveTraceData
	 * @Description: TODO(生产追溯明细信息获取数据[实时库])
	 * @param:       orderItemId 订单明细ID
	 * @return:      Map<String, List<Map<String, String>>>  
	 * @throws
	 */
	public Map<String, List<Map<String, String>>> getLiveTraceData(String orderItemId){

		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
		// 1、获取生产单信息
		List<Map<String, String>> workOrderArray = this.getWorkOrderByCusOrderId(orderItemId);
		// 2、获取qc检测
		List<Map<String, String>> qcArray = this.getQCByCusOrderId(orderItemId);
		// 3、获取报工信息
		List<Map<String, String>> reportArray = this.getReportByCusOrderId(orderItemId);
		// 4、设备采集映射参数
		List<Map<String, String>> equipWWMapArray = this.getMesWWMapByCusOrderId(orderItemId);

		map.put("workOrderArray", workOrderArray);
		map.put("qcArray", qcArray);
		map.put("reportArray", reportArray);
		map.put("equipWWMapArray", equipWWMapArray);
		return map;
	}

	/**
	 * @Title: getWorkOrderByCusOrderId
	 * @Description: TODO(获取订单的所有状态不是channel的生产单信息)
	 * @param: orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	private List<Map<String, String>> getWorkOrderByCusOrderId(String orderItemId) {
		return productTraceDAO.getWorkOrderByCusOrderId(orderItemId);
	}

	/**
	 * @Title: getReportByCusOrderId
	 * @Description: TODO(获取生产单所有的报工信息：包括报工设备、报工人、报工长度)
	 * @param: orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	private List<Map<String, String>> getReportByCusOrderId(String orderItemId) {
		return productTraceDAO.getReportByCusOrderId(orderItemId);
	}

	/**
	 * @Title: getQCByCusOrderId
	 * @Description: TODO(获取生产单所有的质检信息)
	 * @param: @param orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	private List<Map<String, String>> getQCByCusOrderId(String orderItemId) {
		return productTraceDAO.getQCByCusOrderId(orderItemId);
	}

	/**
	 * @Title: getMesWWMapByCusOrderId
	 * @Description: TODO(设备采集映射参数)
	 * @param: @param orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	private List<Map<String, String>> getMesWWMapByCusOrderId(String orderItemId) {
		return productTraceDAO.getMesWWMapByCusOrderId(orderItemId);
	}

	/**
	 * @Title: getDaHistory
	 * @Description:(根据生产单获取当前设备工艺参数的历史记录)
	 * @param: workOrderNo 生产单编码
	 * @param: equipCode 设备编码
	 * @param: receiptCode 工艺参数
	 * @return: Map<String,List<Object[]>>
	 * @throws
	 */
	@Override
	public Map<String, List<Object[]>> getDaHistory(String workOrderNo, String equipCode, String receiptCode) {
		Map<String, List<Object[]>> result = new HashMap<String, List<Object[]>>();
		WorkOrder workOrder = workOrderService.getByWorkOrderNO(workOrderNo);
		int cycleCount = 30;
				// BusinessConstants.MAX_CYCLE_COUNT; // 取默认100，因为recepit表已经无用，直接设置，后期修改

		List<EquipParamHistoryAcquisition> list = new ArrayList<EquipParamHistoryAcquisition>();
		// 获取生产单该设备上的加工时间段
		List<Map<String, Object>> processTimeArray = productTraceDAO.getOrderProcessTime(workOrderNo,
				equipCode.replace("_EQUIP", ""));
		for (Map<String, Object> processTime : processTimeArray) {
			Date startTime = DateUtils.convert(processTime.get("START_TIME").toString(), DateUtils.TIMESTAMP_FORMAT);
			Date endTime = processTime.get("END_TIME") == null ? new Date() : DateUtils.convert(
					processTime.get("END_TIME").toString(), DateUtils.TIMESTAMP_FORMAT);
			list.addAll(dataAcquisitionService.findParamHistory(equipCode, receiptCode, startTime, endTime, cycleCount
					/ processTimeArray.size())); // 组装历史数据
		}
		if (CollectionUtils.isEmpty(list)) {
			return result;
		}
		List<Object[]> historyData = new ArrayList<Object[]>();
		List<Double> distinctList = new ArrayList<Double>();
		if("Respool".equals(workOrder.getProcessCode())&&"R_BreakDPos".equals(receiptCode)){
			for (int i=0;i<list.size();i++) {
				distinctList.add(list.get(i).getValue() == null ? 0 : list.get(i).getValue());
			}
			Set<Double> set=new HashSet<Double>(distinctList);
			distinctList =  new ArrayList<Double>(set);
			int k = 0;
			for (Double paramHis : distinctList) {
				Object[] array = new Object[2];
				array[0] = k++;
				array[1] = paramHis;
				historyData.add(array);
			}
			
		}else{
			for (EquipParamHistoryAcquisition paramHis : list) {
				Object[] array = new Object[2];
				array[0] = paramHis.getDatetime();
				array[1] = paramHis.getValue() == null ? 0 : paramHis.getValue();
				historyData.add(array);
			}
		}

		result.put("historyData", historyData);
		// result.put("maxList", maxDataList);
		// result.put("minList", minDataList);
		return result;
	}
}
