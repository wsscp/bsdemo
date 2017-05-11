package cc.oit.bsmes.wip.service.impl;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.wip.dao.WorkOrderReportDAO;
import cc.oit.bsmes.wip.model.WorkOrderReport;
import cc.oit.bsmes.wip.service.WorkOrderReportService;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

@Service
public class WorkOrderReportServiceImpl extends BaseServiceImpl<WorkOrderReport> implements WorkOrderReportService {
	@Resource private WorkOrderReportDAO workOrderReportDAO;
	
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
    		String reportType, String queryDate, String fileName, String shiftId) throws Exception {
		
		// 1、初始化数据：（a）判断查询时间是否为当天，是：调用存储过程；否：无需;（b）判断是否有数据
		//this.initReportData(reportType, queryDate, shiftId);
		
		Map<String, Object> beans = new HashMap<String, Object>();
        beans.put("year", queryDate.split("-")[0]);
        beans.put("month", queryDate.split("-")[1]);
        beans.put("day", queryDate.split("-")[2]);
    
		// 2、查询
		WorkOrderReport param = new WorkOrderReport();
		param.setReportType(reportType);
		param.setReportDate(queryDate);
		param.setOrgCode(SessionUtils.getUser().getOrgCode());
		if(!StringUtils.equalsIgnoreCase(WebConstants.ROOT_ID, shiftId)){
			param.setShiftId(shiftId);
    	}
		List<WorkOrderReport> dataList = workOrderReportDAO.find(param);
		int totalworkhours = workOrderReportDAO.totalworkhours(param);
        beans.put("data", dataList);
        beans.put("totalworkhours", totalworkhours);
        
        
        XLSTransformer transformer = new XLSTransformer();  
        Workbook wb;  
        try {  
            //模板路径  
            String classPath = this.getClass().getResource("/").getPath()+"exportfile/" + reportType + ".xls";
            wb = transformer.transformXLS(new FileInputStream(classPath), beans);   //获得Workbook对象  
            fileName = fileName + queryDate + "_" + DateUtils.convert(new Date(), DateUtils.DATE_TIMESTAMP_LONG_FORMAT);
            fileName = URLEncoder.encode(fileName, "UTF8") + ".xls";
            String userAgent = request.getHeader("User-Agent").toLowerCase(); // 获取用户代理
            if (userAgent.indexOf("msie") != -1) { // IE浏览器
                fileName = "filename=\"" + fileName + "\"";
            } else if (userAgent.indexOf("mozilla") != -1) { // firefox浏览器
                fileName = "filename*=UTF-8''" + fileName;
            }
            response.reset();
            response.setContentType("application/ms-excel");
            response.setHeader("Content-Disposition", "attachment;" + fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            
        } catch (Exception e) {  
            throw e;  
        }  
	}
	
	/**
	 * <p>初始化数据：判断查询时间是否为当天，是：调用存储过程；否：无需</p>
	 * @author DingXintao
	 * @date 2014年8月5日 9:37:33
	 * @param eventInfoId 事件信息ID
	 * @throws ParseException 
	 */
	private void initReportData(String reportType, String queryDate, String shiftId) throws ParseException{
		
		// 初始化今天的开始时间和结束时间
		String[] strs = queryDate.split("-");
		Calendar today = Calendar.getInstance();
		
		WorkOrderReport param = new WorkOrderReport();
		param.setReportDate(queryDate);
		int count = workOrderReportDAO.count(param);
		
		// 查询为当天 || 查询无数据
		if((Integer.parseInt(strs[0]) == today.get(Calendar.YEAR) &&
				Integer.parseInt(strs[1]) == (today.get(Calendar.MONTH)+1) && 
				Integer.parseInt(strs[2]) == Calendar.DAY_OF_MONTH) || count == 0){
	        // 调用过程
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("vo_reporttype", reportType);
			paramMap.put("vo_querydate", queryDate);
			workOrderReportDAO.callProcedure(paramMap);
		}
	}
	
	/**
	 * 调用报表存储过程
	 * */
	@Override
	@Transactional(readOnly = false)
	public void callProcedure(Map<String, Object> paramMap){
		workOrderReportDAO.callProcedure(paramMap);
	}
}
