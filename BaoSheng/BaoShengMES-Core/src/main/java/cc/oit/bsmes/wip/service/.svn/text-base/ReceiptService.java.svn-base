package cc.oit.bsmes.wip.service;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.fac.model.StatusHistoryChart;
import cc.oit.bsmes.wip.model.Receipt;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public interface ReceiptService extends BaseService<Receipt> {
	/**
	 * <p>
	 * 根据工单号查询下发参数(不包括归档的数据)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-3 下午5:09:51
	 * @param woNo
	 * @return
	 */
	public List<Receipt> getByWorkOrderNo(String woNo);

	/**
	 * 
	 * <p>
	 * 下发工艺参数
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-19 下午2:16:05
	 * @param receiptArray
	 * @param woNo
	 * @see
	 */
	public void issuedReceipt(List<Receipt> receiptArray, String woNo, Boolean isDebug, String operator);

	public Receipt getByReceiptCode(String receiptCode, String equipCode);

	/**
	 * <p>
	 * 根据tagName 查询下发参数
	 * </p>
	 * 
	 * @param tagName
	 * @return
	 */
	public Receipt getByTagName(String tagName);

	public List<Receipt> getByEquipCode(String equipCode, String processId, String orgCode);

	public Receipt getByProcessReceiptAndQA(String receiptCode, String equipCode);

	/**
	 * <p>
	 * 获取历史数据(工艺参数,质量检测)
	 * </p>
	 * 
	 * @author leiw
	 * @date 2014-12-17 上午10:00:17
	 * @return
	 */
	public Map<String, List<Object[]>> getHistoryTrace(String equipCode, String receiptCode, String startTime,
			String endTime);

	public void exportToXls(OutputStream os, String sheetName, Map<String, Object> map) throws RowsExceededException,
			WriteException, IOException;

	public Map<String, Object> realReceiptChart(String equipCode, String processCode, String receiptCode, String type);

	public Receipt getReceiptName(Map<String, Object> map);

	/**
	 * 获取下发参数的最新的一组数据
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单号
	 * */
	public List<Receipt> getLastReceiptList(String workOrderNo);

}
