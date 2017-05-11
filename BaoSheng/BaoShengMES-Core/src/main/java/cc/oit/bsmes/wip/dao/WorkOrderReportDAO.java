package cc.oit.bsmes.wip.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.WorkOrderReport;

import java.util.Map;

/**
 * WorkOrderReportDAO
 * @author DingXintao
 * @date 2014-7-1 11:20:48
 * @since
 * @version
 */
public interface WorkOrderReportDAO extends BaseDAO<WorkOrderReport> {
	
	public void callProcedure(Map<String, Object> paramMap);
	
	/**
     * <p>导出报表</p> 
     * @author DingXintao
     * @date 2014-8-6 14:15:48
     * @param param 参数对象
     * @return int 工时
     * @see
     */
	public int totalworkhours(WorkOrderReport param);
	
}
