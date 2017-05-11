package cc.oit.bsmes.wip.service;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.OnoffRecord;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.Section;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import jxl.write.WriteException;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 *
 */
public interface ReportService extends BaseService<Report> {

    public void insert(String workOrderNo);

    /**
	 * <p>员工姓名,工号统计报工记录</p>
	 * @author chanedi
	 * @date 2014-3-10
	 * @return
	 * @see
	 */
	public int countByWoNo(String workOrderNo);

	/**
	 *
	 * <p>员工姓名,工号统计报工记录</p>
	 * @author QiuYangjun
	 * @date 2014-2-25 上午11:44:51
	 * @param findParam
	 * @return
	 * @see
	 */
	public int countForUserProcessTrace(Map<String,Object> findParam);

	/**
	 * <p>员工姓名,工号查询报工记录</p> 
	 * @author QiuYangjun
	 * @date 2014-2-26 下午4:15:52
	 * @param findParam
	 * @param start
	 * @param limit
	 * @param sortList
     * @return
	 * @see
	 */
	public List<Map<String, Object>> findForUserProcessTrace(Map<String, Object> findParam, int start, int limit, List<Sort> sortList);

    /**
     * 报工。
     *
     * @author chanedi
     * @date 2014-3-7
     * @return
     * @see
     * @param equipCode
     * @param sectionsToReport
     */
    public void report(String equipCode, List<Section> sectionsToReport);

    public void deleteLast(String equipCode);

    /**
     * @author JinHanyun
     * @date 2014-5-27
     * @param workOrderNo
     * @return
     */
    public List<Report> getByWorkOrderNo(String workOrderNo);

    /**
     * @param diskNumber 
     * @param disk 
     * @Title:       report
     * @Description: TODO(终端报工提交事件)
	 * @param workOrderNo 生产单号
	 * @param reportLength 报工长度
	 * @param operator 操作人
	 * @param locationName 报工库位信息
	 * @param equipCode 设备编码
	 * @param reprotUser 报工归属人
     * @return:      JSONObject   
     * @throws
     */
    public JSONObject report(String workOrderNo,Double reportLength,String operator,String locationName,String equipCode, String[] reprotUser, String disk, Integer diskNumber);

    /**
     *
     * @param findParams
     * @param sortList
     * @return
     */
    public List<Report> getReportByEquipCode(Report findParams, int start, int limit, List<Sort> sortList);

    /**
     *
     * @param findParams
     * @return
     */
    public int countByEquipCode(Report findParams);

    public double getSumGoodLength(String woNo);

	public void export(OutputStream os, String sheetName, JSONArray columns,Map<String, Object> findParams) throws IOException, WriteException, InvocationTargetException, IllegalAccessException ;

    public List<Report> checkFinishWorkOrder(Map<String,String> paramMap);

    public Map<String,Object> createBarCode(String reportId);

    public Report getByBarCode(String barCode);

    public JSONObject calculateWasteLength(String woNo);

    public List<Report> getByWorkOrder(String workOrderNo);

    /**
     * 查询当前任务上一工序报工列表
     * @param workOrderNo
     * @param color
     * @return
     */
    public List<Report> getPutIn(String workOrderNo,String color,String orderTaskId);

    public Report getByWorkOrderNoAndBarCode(String barCode, String workOrderNo);
    
    public int countFind(Report params);

	public List<Report> getGoodLengthByWorkOrder(String workOrderNo);
	
	/**
	 * 创建生产单信息 by Zhaoxin 
	 * @param workOrderNo
	 * @return
	 */
	public List<Map<String,Object>> createWoNoInfo(String workOrderNo);

	public int count1(String workOrderNo);

	public List<Report> getReportOutput(Report findParams, int start,
			int limit, List<Sort> parseArray);

	public List<Report> getEquipEnergyInfo(Report findParams);

	public String getReMarks(String workOrderNo);
	
	public void updateUseStatus();
	public void updateUseStatus2();

}
