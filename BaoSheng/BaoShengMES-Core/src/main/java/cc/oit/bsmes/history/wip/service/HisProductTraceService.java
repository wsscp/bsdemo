package cc.oit.bsmes.history.wip.service;

import java.util.List;
import java.util.Map;

public interface HisProductTraceService {

	/**
	 * @Title:       getHisTraceData
	 * @Description: TODO(生产追溯明细信息获取数据[实时库])
	 * @param:       orderItemId 订单明细ID
	 * @return:      Map<String, List<Map<String, String>>>
	 * @throws
	 */
	public Map<String, List<Map<String, String>>> getHisTraceData(String orderItemId);
	
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
