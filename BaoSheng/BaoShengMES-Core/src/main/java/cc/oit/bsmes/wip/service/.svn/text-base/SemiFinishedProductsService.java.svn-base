package cc.oit.bsmes.wip.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.SemiFinishedProducts;

public interface SemiFinishedProductsService extends BaseService<SemiFinishedProducts> {
	
	public List<SemiFinishedProducts> findResult(Map<String, Object> param, int start, int limit, List<Sort> sortList);
	
	public void exportToXls(OutputStream os, String sheetName,Map<String, Object> map) throws RowsExceededException, WriteException, IOException ;
	
	public List<SemiFinishedProducts> useProcedure(Map<String, Object> findParams);
	
	public int countList(Map<String, Object> findParams);
	
	public Integer semiFinishedProductsCount(Map<String, Object> param);
	
	public List<SemiFinishedProducts> getProcessName();
	
	public List<SemiFinishedProducts> searchProcessName();
	
	public List<SemiFinishedProducts> searchContractNo(String contractNo);

	/**
	 * 半成品使用查询
	 * @param findParams
	 * @return
	 */
	public List<SemiFinishedProducts> useProcedureUsing(
			Map<String, Object> findParams);

	/**
	 * 半成品使用件数
	 * @param findParams
	 * @return
	 */
	public int countListUsing(Map<String, Object> findParams);
	
	public List<Map<String,String>> getMatQuan(Map<String, Object> params);
	
	public List<Map<String,String>> getAllMatQuan();
	
	public void calculateWorkOrderCost(String workOrderNo);
	
	public List<String> getAllWorkOrder();
}
