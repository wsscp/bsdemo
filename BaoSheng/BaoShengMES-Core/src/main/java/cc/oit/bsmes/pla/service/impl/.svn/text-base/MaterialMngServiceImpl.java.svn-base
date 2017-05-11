package cc.oit.bsmes.pla.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pla.dao.BorrowMatDAO;
import cc.oit.bsmes.pla.dao.SupplementMaterialDAO;
import cc.oit.bsmes.pla.dao.MaterialMngDAO;
import cc.oit.bsmes.pla.model.BorrowMat;
import cc.oit.bsmes.pla.model.SupplementMaterial;
import cc.oit.bsmes.pla.model.MaterialMng;
import cc.oit.bsmes.pla.service.MaterialMngService;

@Service
public class MaterialMngServiceImpl extends BaseServiceImpl<MaterialMng> implements MaterialMngService {

	@Resource
	private MaterialMngDAO materialMngDAO;
	@Resource
	private SupplementMaterialDAO supplementMaterialDAO;
	@Resource
	private BorrowMatDAO borrowMatDAO;
	
	@Override
	public List<MaterialMng> findMap(Map<String, Object> param, int start,
			int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start,limit));
		return materialMngDAO.findMap(param);
	}
	
	@Override
	public int countMap(Map<String, Object> param) {
		return materialMngDAO.countMap(param);
	}
	
	@Override
	public void updateEquipMat(SupplementMaterial supplementMaterial) {
		supplementMaterialDAO.update(supplementMaterial);
	}

	@Override
	public List<SupplementMaterial> findBorrowMat(SupplementMaterial supplementMaterial) {
		return supplementMaterialDAO.find(supplementMaterial);
	}

	@Override
	public List<MaterialMng> getByWorkOrderNo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		String equipCode = (String) param.get("equipCode");
		param.remove("equipCode");
		List<MaterialMng> list = materialMngDAO.getByWorkOrderNo(param);
		for(MaterialMng m : list){
//			SupplementMaterial b = supplementMaterialDAO.getByCode(equipCode, m.getMatCode());
//			if(null == b){
//				m.setMoreOrLess("1");
//				m.setQuantity("0");
//			}else{
//				m.setMoreOrLess(b.getMoreOrLess());
//				m.setQuantity(Double.toString(b.getMatQuantity()));
//			}
		}
		return list;
	}

	@Override
	public SupplementMaterial getEquipMatBycode(String equipCode, String matCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertEquipMat(SupplementMaterial mat) {
		// TODO Auto-generated method stub
		supplementMaterialDAO.insert(mat);
	}

	@Override
	public List<MaterialMng> getByWorkOrderIdAndMatCode(String id,
			String matCode) {
		// TODO Auto-generated method stub
		return materialMngDAO.getByWorkOrderIdAndMatCode(id,matCode);
	}

	@Override
	public BorrowMat getByBorrowMatId(String id) {
		// TODO Auto-generated method stub
		return borrowMatDAO.getById(id);
	}

	@Override
	public void updateBorrowMat(BorrowMat b) {
		// TODO Auto-generated method stub
		borrowMatDAO.update(b);
	}

	@Override
	public List<MaterialMng> findFaLiaoMap(Map<String, Object> param,
			Integer start, Integer limit, List<Sort> parseArray) {
		// TODO Auto-generated method stub
		SqlInterceptor.setRowBounds(new RowBounds(start,limit));
		return materialMngDAO.findFaLiaoMap(param);
	}

	@Override
	public int countFaLiaoMap(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return materialMngDAO.countFaLiaoMap(param);
	}

	@Override
	public List<MaterialMng> getByWorkOrderNo(String workOrderNo) {
		// TODO Auto-generated method stub
		return materialMngDAO.getByWorkOrderNo(workOrderNo);
	}

	@Override
	public List<MaterialMng> getBuLiaoByWorkOrderNo(String workOrderNo) {
		// TODO Auto-generated method stub
		return materialMngDAO.getBuLiaoByWorkOrderNo(workOrderNo);
	}

	@Override
	public List<MaterialMng> findBuLiaoMap(Map<String, Object> param,
			Integer start, Integer limit, List<Sort> parseArray) {
		// TODO Auto-generated method stub
		SqlInterceptor.setRowBounds(new RowBounds(start,limit));
		return materialMngDAO.findBuLiaoMap(param);
	}

	@Override
	public int countBuLiaoMap(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return materialMngDAO.countBuLiaoMap(param);
	}

	@Override
	public SupplementMaterial getSumpById(String id) {
		// TODO Auto-generated method stub
		return supplementMaterialDAO.getById(id);
	}

	@Override
	public List<MaterialMng> getReport(Map<String, Object> param, Integer start, Integer limit,
			List<Sort> parseArray) {
		// TODO Auto-generated method stub
		SqlInterceptor.setRowBounds(new RowBounds(start,limit));
		return materialMngDAO.getReport(param);
	}

	@Override
	public Integer getReportCount(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return materialMngDAO.getReportCount(param);
	}

}
