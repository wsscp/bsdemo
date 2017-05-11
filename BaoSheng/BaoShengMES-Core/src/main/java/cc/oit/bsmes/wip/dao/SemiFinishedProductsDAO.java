package cc.oit.bsmes.wip.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.SemiFinishedProducts;

public interface SemiFinishedProductsDAO extends BaseDAO<SemiFinishedProducts> {
	
	public List<SemiFinishedProducts> findResult(Map<String, Object> param);
	
	public Integer semiFinishedProductsCount(Map<String, Object> param);
	
	public List<SemiFinishedProducts> useProcedure(Map<String, Object> findParams);
	
	public List<SemiFinishedProducts> getProcessName();
	
	public int countList(Map<String, Object> findParams);
	
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
	
	public List<String> getOrderIdByTaskId(Map<String, Object> params);
	
	public List<Map<String,String>> getAllMatCost(Map<String, Object> params);
	
	public void insertCostInfo(Map<String, Object> params);
	
	public List<String> getAllWorkOrder();
}
