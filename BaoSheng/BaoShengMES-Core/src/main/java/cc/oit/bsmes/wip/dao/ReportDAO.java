/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */ 
package cc.oit.bsmes.wip.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.OnoffRecord;
import cc.oit.bsmes.wip.model.Report;

import java.util.List;
import java.util.Map;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-2-20 下午3:38:31
 * @since
 * @version
 */
public interface ReportDAO extends BaseDAO<Report>{
	/**
	 * 
	 * <p>员工姓名,工号查询报工记录</p> 
	 * @author QiuYangjun
	 * @date 2014-2-25 上午11:44:51
	 * @param findParam
	 * @return
	 * @see
	 */
	public List<Map<String,Object>> findForUserProcessTrace(Map<String,Object> findParam); 
	
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

    public Report getCurrent(String equipCode);

    public Double sumGoodLengthByWoNo(String woNo);

    /**
     *
     * @param findParams
     * @return
     */
    public List<Report> getByEquipCode(Report findParams);

    /**
     *
     * @param findParams
     * @return
     */
    public int countByEquipCode(Report findParams);

    /**
     *
     * @param woNo
     * @return
     */
    public double getSumGoodLength(String woNo);


    public List<Report> checkFinishWorkOrder(Map<String,String> paramMap);
    
    public List<Report> getWorkOrderReportHistory(Map<String, Object> param);

    public Report getPrintInfoById(String reportId);
    
    //新增方法:根据生产单号查询生产单的合同号、工序、颜色、投产长度。
    public List<Map<String,Object>> getInfoByWorkOrderNo(String workOrderNo);
    
    //该生产单下一道工序是否已下发，并返回下道工序生产单号。
    public String hasNextProcess(String workOrderNo);

    public Report getByBarCode(String barCode);

    public List<Report> getByWorkOrder(String workOrderNo);

    public List<Report> getPutIn(Map<String,Object> params);

    public Report getByWorkOrderNoAndBarCode(String barCode, String workOrderNo);
    
    public int countFind(Report params);

	public List<Report> getGoodLengthByWorkOrder(String workOrderNo);
	
	/**
	 * @Title:       getShortColor
	 * @Description: TODO(获取颜色：1#黄,2#黄,3#黄,4#黄->1-4#黄,)
	 * @param:       color 颜色
	 * @return:      String   
	 * @throws
	 */
	public String getShortColor(String color);

	public int getReportCount1(String workOrderNo);

	public List<Report> getReportByEquipCodes(Report findParams);

	public List<Report> getReportByUserCode(OnoffRecord newRecord);

	public List<Report> getReportOutput(Report findParams);

	public List<Report> getEquipEnergyInfo(Report findParams);

	public String getReMarks(String workOrderNo);
	
	public void updateUseStatus();
	public void updateUseStatus2();

	
}
