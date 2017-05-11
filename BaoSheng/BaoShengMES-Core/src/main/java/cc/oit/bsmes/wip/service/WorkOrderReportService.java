package cc.oit.bsmes.wip.service;

import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.WorkOrderReport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WorkOrderReportService extends BaseService<WorkOrderReport> {
	
	/**
     * <p>导出报表</p> 
     * @author DingXintao
     * @date 2014-8-4 16:15:48
     * @param request 请求
     * @param response 响应
     * @param reportType 报表类型
     * @param queryDate 生产日期
     * @param fileName 导出文件名
     * @param shiftId 班次
     * @throws Exception 
     * @see
     */
	public void reportExport(HttpServletRequest request, HttpServletResponse response,
    		String reportType, String queryDate, String fileName, String shiftId) throws Exception;
	
	/**
	 * 调用报表存储过程
	 * */
	public void callProcedure(Map<String, Object> paramMap);
	
}
