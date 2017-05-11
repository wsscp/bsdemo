package cc.oit.bsmes.wip.dao;

import java.util.List;
import java.util.Map;

public interface ProductTraceDAO {

	/**
	 * @Title: getWorkOrderByCusOrderId
	 * @Description: TODO(获取订单的所有状态不是channel的生产单信息)
	 * @param: orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	public List<Map<String, String>> getWorkOrderByCusOrderId(String orderItemId);

	/**
	 * @Title: getReportByCusOrderId
	 * @Description: TODO(获取生产单所有的报工信息：包括报工设备、报工人、报工长度)
	 * @param: orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	public List<Map<String, String>> getReportByCusOrderId(String orderItemId);

	/**
	 * @Title: getQCByCusOrderId
	 * @Description: TODO(获取生产单所有的质检信息)
	 * @param: orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	public List<Map<String, String>> getQCByCusOrderId(String orderItemId);

	/**
	 * @Title: getMesWWMapByCusOrderId
	 * @Description: TODO(设备采集映射参数)
	 * @param: orderItemId 订单ID
	 * @return: List<Map<String,String>>
	 * @throws
	 */
	public List<Map<String, String>> getMesWWMapByCusOrderId(String orderItemId);

	/**
	 * @Title: getMesWWMapByCusOrderId
	 * @Description: TODO(设备采集映射参数)
	 * @param: workOrderNo 生产单号
	 * @param: equipCode 设备编码
	 * @return: List<Map<String,Date>>
	 * @throws
	 */
	public List<Map<String, Object>> getOrderProcessTime(String workOrderNo, String equipCode);

}
