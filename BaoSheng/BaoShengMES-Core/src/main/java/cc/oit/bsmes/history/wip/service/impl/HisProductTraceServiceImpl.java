package cc.oit.bsmes.history.wip.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.history.wip.dao.HisProductTraceDAO;
import cc.oit.bsmes.history.wip.service.HisProductTraceService;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamHistoryAcquisition;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;

@Service
public class HisProductTraceServiceImpl implements HisProductTraceService {

	@Resource
	private HisProductTraceDAO hisProductTraceDAO;
	@Resource
	private DataAcquisitionService dataAcquisitionService;
	
	/**
	 * @Title:       getHisTraceData
	 * @Description: TODO(生产追溯明细信息获取数据[实时库])
	 * @param:       orderItemId 订单明细ID
	 * @return:      Map<String, List<Map<String, String>>>  
	 * @throws
	 */
	public Map<String, List<Map<String, String>>> getHisTraceData(String orderItemId){

		Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
		// 1、获取生产单信息
		List<Map<String, String>> workOrderArray = hisProductTraceDAO.getWorkOrderByCusOrderId(orderItemId);
		// 2、获取qc检测
		List<Map<String, String>> qcArray = hisProductTraceDAO.getQCByCusOrderId(orderItemId);
		// 3、获取报工信息
		List<Map<String, String>> reportArray = hisProductTraceDAO.getReportByCusOrderId(orderItemId);
		// 4、设备采集映射参数
		List<Map<String, String>> equipWWMapArray = hisProductTraceDAO.getMesWWMapByCusOrderId(orderItemId);

		map.put("workOrderArray", workOrderArray);
		map.put("qcArray", qcArray);
		map.put("reportArray", reportArray);
		map.put("equipWWMapArray", equipWWMapArray);
		return map;
	}

	/**
	 * @Title: getDaHistory
	 * @Description: TODO((根据生产单获取当前设备工艺参数的历史记录)
	 * @param: workOrderNo 生产单编码
	 * @param: equipCode 设备编码
	 * @param: receiptCode 工艺参数
	 * @return: Map<String,List<Object[]>>
	 * @throws
	 */
	@Override
	public Map<String, List<Object[]>> getDaHistory(String workOrderNo, String equipCode, String receiptCode) {
		Map<String, List<Object[]>> result = new HashMap<String, List<Object[]>>();
		int cycleCount = 30;
				// BusinessConstants.MAX_CYCLE_COUNT; // 取默认100，因为recepit表已经无用，直接设置，后期修改

		List<EquipParamHistoryAcquisition> list = new ArrayList<EquipParamHistoryAcquisition>();
		// 获取生产单该设备上的加工时间段
		List<Map<String, Object>> processTimeArray = hisProductTraceDAO.getOrderProcessTime(workOrderNo,
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
		for (EquipParamHistoryAcquisition paramHis : list) {
			Object[] array = new Object[2];
			array[0] = paramHis.getDatetime();
			array[1] = paramHis.getValue() == null ? 0 : paramHis.getValue();
			historyData.add(array);
		}

		result.put("historyData", historyData);
		// result.put("maxList", maxDataList);
		// result.put("minList", minDataList);
		return result;
	}
}
