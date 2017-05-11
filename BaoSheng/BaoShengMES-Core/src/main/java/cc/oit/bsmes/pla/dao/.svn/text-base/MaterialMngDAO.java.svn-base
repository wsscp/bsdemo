package cc.oit.bsmes.pla.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.pla.model.MaterialMng;

public interface MaterialMngDAO extends BaseDAO<MaterialMng>{

	List<MaterialMng> findMap(Map<String, Object> param);
	
	int countMap(Map<String, Object> param);

	List<MaterialMng> getByWorkOrderNo(Map<String, Object> param);

	List<MaterialMng> getByWorkOrderIdAndMatCode(String id, String matCode);

	List<MaterialMng> findFaLiaoMap(Map<String, Object> param);

	int countFaLiaoMap(Map<String, Object> param);

	List<MaterialMng> getByWorkOrderNo(String workOrderNo);

	List<MaterialMng> getBuLiaoByWorkOrderNo(String workOrderNo);

	List<MaterialMng> findBuLiaoMap(Map<String, Object> param);

	int countBuLiaoMap(Map<String, Object> param);

	List<MaterialMng> getReport(Map<String, Object> param);

	int getReportCount(Map<String, Object> param);
}
