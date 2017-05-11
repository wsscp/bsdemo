package cc.oit.bsmes.wip.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.wip.dao.SpecialCraftsTraceDAO;
import cc.oit.bsmes.wip.model.SpecialCraftsTrace;
import cc.oit.bsmes.wip.service.SpecialCraftsTraceService;

@Service
public class SpecialCraftsTraceServiceImpl implements SpecialCraftsTraceService {
	
	@Resource
	private SpecialCraftsTraceDAO specialCraftsTraceDAO;
	
	@Override
	public List<SpecialCraftsTrace> getAllHistoryTrace(Map<String,Object> findParams) {
		//SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return specialCraftsTraceDAO.getAllHistoryTrace(findParams);
	}

	@Override
	public int countHistoryTrace(Map<String,Object> findParams) {
		return specialCraftsTraceDAO.countHistoryTrace(findParams);
	}

	@Override
	public List<SpecialCraftsTrace> searchContractNo(String contractNo) {		
		return specialCraftsTraceDAO.searchContractNo(contractNo);
	}

	@Override
	public List<Map<String, String>> searchProductType(String productType) {
		return specialCraftsTraceDAO.searchProductType(productType);
	}

	@Override
	public List<Map<String, String>> searchProductSpec(Map<String, String> param) {
		return specialCraftsTraceDAO.searchProductSpec(param);
	}
	

}
