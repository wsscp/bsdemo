package cc.oit.bsmes.pro.service;

import java.util.List;
import java.util.Map;

import jxl.Cell;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.wip.dto.MesClientEqipInfo;

public interface ProcessQcService extends BaseService<ProcessQc> {

	/**
	 * 
	 * 查询工序的质量检测参数
	 * 
	 * @author JinHanyun
	 * @date 2014-1-22 下午6:02:44
	 * @param processId
	 * @return
	 * @see
	 */
	public List<ProcessQc> getByProcessId(String processId);

	/**
	 * 
	 * <p>
	 * 产品/半成品称重,根据生产单号插入数据
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-19 下午3:42:21
	 * @param workOrderNO
	 * @param weight
	 * @param checkItemCode
	 * @param userCode
	 * @see
	 */
	public void insertByWorkOrderNO(String workOrderNO, String weight, String checkItemCode, String userCode);

	/**
	 * @author chanedi
	 * @date 2014年2月24日 下午2:45:05
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<ProcessQc> getByWorkOrderNo(String workOrderNo);

	/**
	 * <p>
	 * 根据设备code和工序code追溯产品质量检测参数
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2014-3-10 上午11:28:05
	 * @param equipCode
	 * @param processCode
	 * @return
	 * @see
	 */
	public List<ProcessQc> traceByEquipCodeAndProcessCode(String equipCode, String processCode);

	/**
	 * 
	 * <p>
	 * 根据设备生产线，和工艺流程Id 查询工艺质量参数
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-19 下午4:06:43
	 * @param equipLineCode
	 * @param processId
	 * @return
	 * @see
	 */
	public List<ProcessQc> getByEquipLineAndProcessId(String equipLineCode, String processId);

	/**
	 * 
	 * <p>
	 * 根据生产单号，查询当前生产单设备的质量参数
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-20 下午4:17:03
	 * @param workOrderNo
	 * @param equipCode 设备编码
	 * @return
	 * @see
	 */
	public List<ProcessQc> getByWorkOrderNoAndDistinctEqipCode(String workOrderNo, String equipCode, String type);

	/**
	 * ProcessQc导入
	 * 
	 * @param rows
	 * @param equipCode 设备编码
	 * @param acEquipCode 真实设备编码
	 */
	public void importProcessQc(List<Cell[]> rows, String equipCode, String acEquipCode, String or);

	public ProcessQc getByEquipCodeAndProcessCodeAndReceiptCode(String equipCode, String processCode, String receiptCode);

	public List<Map<String, String>> getEmphShow(String processId, String productLineCode);

	public void insertBatch(List<ProcessQc> list);

	public void insertBackGround(String newProcessId, List<String> processIds);
	
	public List<Map<String,Object>> getQcInfoByTaskId(String taskId);

}
