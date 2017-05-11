package cc.oit.bsmes.pro.service;

import java.util.List;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProcessQcValue;

import com.alibaba.fastjson.JSONObject;

public interface ProcessQcValueService extends BaseService<ProcessQcValue> {

	/**
	 * 
	 * <p>
	 * 根据订单号查询该订单质量检测数据
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-13 上午9:45:14
	 * @param workOrderNo
	 * @param processId
	 * @return
	 * @see
	 */
	public List<ProcessQcValue> findDistinctByWorkOrderNo(String workOrderNo);

	/**
	 * 
	 * <p>
	 * 根据订单号，查询出这个检测参数的所有数据
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-13 下午4:52:23
	 * @param workOrderNo
	 * @param checkItemCode
	 * @return
	 * @see
	 */
	public List<ProcessQcValue> findByWorkOrderNoAndCheckItemCode(String workOrderNo, String checkItemCode);

	/**
	 * 
	 * <p>
	 * QA检测手工录入数据保存
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-14 上午10:52:25
	 * @param processQcValue
	 * @see
	 */
	public void entryProcessQAValue(List<ProcessQcValue> list, Integer coilNum, String equipCode);

	/**
	 * 
	 * <p>
	 * QA检测项查询
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-14 上午10:52:21
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<ProcessQcValue> queryQACheckItems(String workOrderNo, String type, String equipCode);

	/**
	 * 
	 * <p>
	 * QA警报手工生成
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-20 下午4:32:55
	 * @param qcValue
	 * @see
	 */
	public void generateQAAlarm(ProcessQcValue qcValue);

	/**
	 * 
	 * @param woNo
	 * @param equipCode
	 * @param coilNum
	 * @return
	 */
	public JSONObject checkExistsInputQcValue(String woNo, String equipCode, int coilNum);

	/**
	 * 判断是否做上车检
	 * 
	 * @param woNo
	 * @return
	 */
	public boolean inCheck(String woNo);

	/**
	 * 根据生产单获取最近一次的首检和上车检的记录
	 * 
	 * @param equipCode
	 * @return
	 */
	public List<ProcessQcValue> getLastByWorkOrderNoAndType(String workOrderNo);

	/**
	 * 报工的时候 把serialNum更新到数据采集到的相关质量数据
	 * 
	 * @param serialNum
	 * @param workOrderNo
	 * @return
	 */
	public int updateDA(String serialNum, String workOrderNo);


	public List<ProcessQcValue> getQaList(ProcessQcValue findParams, int start, int limit, List<Sort> list);

	public int countQaList(ProcessQcValue findParams);

	
	/**
	 * 报工的时候 查询下车检中线盘号的最大值
	 * 
	 * @param workOrderNo
	 * @param type
	 * @param equipCode
	 * @return
	 */
	public String queryProcessQcValueCoilNum(String workOrderNo,String type,String equipCode);
}
