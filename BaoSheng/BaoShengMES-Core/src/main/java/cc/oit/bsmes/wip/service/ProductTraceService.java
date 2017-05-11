package cc.oit.bsmes.wip.service;

import java.util.List;
import java.util.Map;

public interface ProductTraceService {

	/**
	 * @Title:       getLiveTraceData
	 * @Description: TODO(生产追溯明细信息获取数据[实时库])
	 * @param:       orderItemId 订单明细ID
	 * @return:      Map<String, List<Map<String, String>>>
	 * @throws
	 */
	public Map<String, List<Map<String, String>>> getLiveTraceData(String orderItemId);
	
	/**
	 * @Title: getWorkOrderByCusOrderId
	 * @Description: TODO(获取订单的所有状态不是channel的生产单信息)
	 * @param: orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	//public List<Map<String, String>> getWorkOrderByCusOrderId(String orderItemId);

	/**
	 * @Title: getReportByCusOrderId
	 * @Description: TODO(获取生产单所有的报工信息：包括报工设备、报工人、报工长度)
	 * @param: orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	//public List<Map<String, String>> getReportByCusOrderId(String orderItemId);

	/**
	 * @Title: getQCByCusOrderId
	 * @Description: TODO(获取生产单所有的质检信息)
	 * @param: @param orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	//public List<Map<String, String>> getQCByCusOrderId(String orderItemId);

	/**
	 * @Title: getMesWWMapByCusOrderId
	 * @Description: TODO(设备采集映射参数)
	 * @param: @param orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	//public List<Map<String, String>> getMesWWMapByCusOrderId(String orderItemId);

	/**
	 * @Title: getDaHistory
	 * @Description: TODO((根据生产单获取当前设备工艺参数的历史记录)
	 * @param: workOrderNo 生产单编码
	 * @param: equipCode 设备编码
	 * @param: receiptCode 工艺参数
	 * @return: Map<String,List<Object[]>>
	 * @throws
	 */
	public Map<String, List<Object[]>> getDaHistory(String workOrderNo, String equipCode, String receiptCode);

}
