package cc.oit.bsmes.wip.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.SemiFinishedProductsDAO;
import cc.oit.bsmes.wip.dao.WorkOrderDAO;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.SemiFinishedProducts;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ReportService;
import cc.oit.bsmes.wip.service.SemiFinishedProductsService;

@Service
public class SemiFinishedProductsServiceImpl extends BaseServiceImpl<SemiFinishedProducts> implements
		SemiFinishedProductsService {
	@Resource
	private SemiFinishedProductsDAO semiFinishedProductsDAO;
	@Resource
	private WorkOrderDAO workOrderDAO;
	@Resource
	private ReportService reportService;

	public List<SemiFinishedProducts> findResult(Map<String, Object> param, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return semiFinishedProductsDAO.findResult(param);
	}
	
	public void exportToXls(OutputStream os, String sheetName,Map<String,Object> map) throws RowsExceededException, WriteException, IOException {/*
		
		List<SemiFinishedProducts> list = semiFinishedProductsDAO.findResult(map);
		WritableWorkbook wwb = Workbook.createWorkbook(os);
	    WritableSheet sheet = wwb.createSheet(sheetName, 0);
	    
	    sheet.setColumnView(0, 20);
	    sheet.setColumnView(1, 20);
	    sheet.setColumnView(2, 20);
	    sheet.setColumnView(3, 20);
	    sheet.setColumnView(4, 20);
	    sheet.setColumnView(5, 20);
	    sheet.setColumnView(6, 20);
	    sheet.setColumnView(7, 20);
	    sheet.setColumnView(8, 20);
	    sheet.setColumnView(9, 20);
	    
	    sheet.addCell(new Label(0, 0, "班次"));
	    sheet.addCell(new Label(1, 0, "合同号"));
	    sheet.addCell(new Label(2, 0, "型号"));
	    sheet.addCell(new Label(3, 0, "规格"));
	    sheet.addCell(new Label(4, 0, "长度"));
	    sheet.addCell(new Label(5, 0, "盘具"));
	    sheet.addCell(new Label(6, 0, "所在工序"));
	    sheet.addCell(new Label(7, 0, "最后工序结束时间"));
	    sheet.addCell(new Label(8, 0, "存放位置"));
	    sheet.addCell(new Label(9, 0, "盘点人"));
	    
	    if (list.size() == 0) {
            wwb.write();
            wwb.close();
            return;
        }
	    int k = 1;
	    for (int i = 0; i< list.size(); i++) {
	    	SemiFinishedProducts semiFinishedProducts = list.get(i);
	    	sheet.addCell(new Label(0, k,semiFinishedProducts.getShift()));
	    	sheet.addCell(new Label(1, k,semiFinishedProducts.getContractNo()));
	    	sheet.addCell(new Label(2, k,semiFinishedProducts.getCheckType()));
	    	sheet.addCell(new Label(3, k,semiFinishedProducts.getCheckSpec()));
	    	sheet.addCell(new Label(4, k,semiFinishedProducts.getCheckLength()));
	    	sheet.addCell(new Label(5, k,semiFinishedProducts.getWireCoil()));
	    	sheet.addCell(new Label(6, k,semiFinishedProducts.getProcessName()));
	    	sheet.addCell(new Label(7, k,DateUtils.convert(semiFinishedProducts.getProcessFinishDate(),DateUtils.DATE_TIME_FORMAT)));
	    	sheet.addCell(new Label(8, k,semiFinishedProducts.getLocationName()));
	    	sheet.addCell(new Label(9, k,semiFinishedProducts.getCheckUserName()));
	    	k++;
	    }
	    wwb.write();
        wwb.close();
	*/}
	
	public Integer semiFinishedProductsCount(Map<String, Object> param) {
		return semiFinishedProductsDAO.semiFinishedProductsCount(param);
	}
	
	public List<SemiFinishedProducts> useProcedure(Map<String, Object> findParams){
		return semiFinishedProductsDAO.useProcedure(findParams);
	}
	
	public List<SemiFinishedProducts> getProcessName(){
		return semiFinishedProductsDAO.getProcessName();
	}

	@Override
	public int countList(Map<String, Object> findParams) {
		return semiFinishedProductsDAO.countList(findParams);
	}

	@Override
	public List<SemiFinishedProducts> searchProcessName() {
		return semiFinishedProductsDAO.searchProcessName();
	}

	@Override
	public List<SemiFinishedProducts> searchContractNo(String contractNo) {
		// TODO Auto-generated method stub
		return semiFinishedProductsDAO.searchContractNo(contractNo);
	}

	/**
	 * 半成品使用查询
	 * @param findParams
	 * @return
	 */
	@Override
	public List<SemiFinishedProducts> useProcedureUsing(
			Map<String, Object> findParams) {
		reportService.updateUseStatus();
		reportService.updateUseStatus2();
		List<SemiFinishedProducts> list = semiFinishedProductsDAO.useProcedureUsing(findParams);
//		for (SemiFinishedProducts semiFinishedProducts : list) {
//			if(StringUtils.isEmpty(semiFinishedProducts.getUseStatus()) && StringUtils.equals(semiFinishedProducts.getIsUsed(), "已使用"))
//			{// 报工表中使用状态为空，此时查询使用状体未【已使用】时，更新报工记录
//				Report report = reportService.getById(semiFinishedProducts.getReportId()); 
//				if(report != null)
//				{
//					report.setUseStatus(semiFinishedProducts.getIsUsed());
//					reportService.update(report);
//				}
//			}
//		}
		return list;
	}

	/**
	 * 半成品使用件数
	 * @param findParams
	 * @return
	 */
	@Override
	public int countListUsing(Map<String, Object> findParams) {
		return semiFinishedProductsDAO.countListUsing(findParams);
	}
	
	public List<Map<String,String>> getMatQuan(Map<String, Object> params){
		List<String> orderNoArr = semiFinishedProductsDAO.getOrderIdByTaskId(params);
		params.put("orderNoArr", orderNoArr);
		return semiFinishedProductsDAO.getMatQuan(params);
	}
	
	public List<Map<String,String>> getAllMatQuan(){
		return semiFinishedProductsDAO.getAllMatQuan();
	}
	
	public List<String> getAllWorkOrder(){
		return semiFinishedProductsDAO.getAllWorkOrder();
	}
	
	public void calculateWorkOrderCost(String workOrderNo){
		String workOrderId = "";
		Map<String, Object> params = new HashMap<String,Object>();
		List<String> flowIdArr = new ArrayList<String>();
		List<WorkOrder> thisflowArray = workOrderDAO.getWorkOrderFlowArray(workOrderNo);
		for(int i=0;i<thisflowArray.size();i++){
			flowIdArr.add(thisflowArray.get(i).getId());
			if(thisflowArray.get(i).getWorkOrderNo().equals(workOrderNo)){
				workOrderId = thisflowArray.get(i).getId();
			}
		}
		params.put("flowIdArr", flowIdArr);
		List<Map<String,String>> matCostList = semiFinishedProductsDAO.getAllMatCost(params);
		for(int i=0;i<matCostList.size();i++){
			params.put("thisWorkOrderNo", workOrderNo);
			params.put("thisWorkOrderId", workOrderId);
			params.put("matName", matCostList.get(i).get("MATNAME"));
			params.put("matCode", matCostList.get(i).get("MATCODE"));
			params.put("quantity", matCostList.get(i).get("QUANTITY"));
			semiFinishedProductsDAO.insertCostInfo(params);
		}
	}
}
