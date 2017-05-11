package cc.oit.bsmes.pla.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.BorrowMat;
import cc.oit.bsmes.pla.model.SupplementMaterial;
import cc.oit.bsmes.pla.model.MaterialMng;

public interface MaterialMngService extends BaseService<MaterialMng>{
	
	List<MaterialMng> findMap(Map<String, Object> param,int start, int limit, List<Sort> sortList);

	int countMap(Map<String, Object> param);
	
	void updateBorrowMat(BorrowMat b);
	
	List<SupplementMaterial> findBorrowMat(SupplementMaterial supplementMaterial);

	List<MaterialMng> getByWorkOrderNo(Map<String, Object> param);

	SupplementMaterial getEquipMatBycode(String equipCode, String matCode);

	void insertEquipMat(SupplementMaterial mat);

	List<MaterialMng> getByWorkOrderIdAndMatCode(String id, String matCode);

	BorrowMat getByBorrowMatId(String id);

	void updateEquipMat(SupplementMaterial b);

	List<MaterialMng> findFaLiaoMap(Map<String, Object> param, Integer start,
			Integer limit, List<Sort> parseArray);

	int countFaLiaoMap(Map<String, Object> param);

	List<MaterialMng> getByWorkOrderNo(String workOrderNo);

	List<MaterialMng> getBuLiaoByWorkOrderNo(String workOrderNo);

	List<MaterialMng> findBuLiaoMap(Map<String, Object> param, Integer start,
			Integer limit, List<Sort> parseArray);

	int countBuLiaoMap(Map<String, Object> param);

	SupplementMaterial getSumpById(String id);

	List<MaterialMng> getReport(Map<String, Object> param, Integer start, Integer limit,
			List<Sort> parseArray);

	Integer getReportCount(Map<String, Object> param);
	}
