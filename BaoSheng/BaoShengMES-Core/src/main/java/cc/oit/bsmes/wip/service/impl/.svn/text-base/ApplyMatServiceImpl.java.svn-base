package cc.oit.bsmes.wip.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.model.ApplyMat;
import cc.oit.bsmes.wip.dao.ApplyMatDAO;
import cc.oit.bsmes.wip.service.ApplyMatService;
@Service
public class ApplyMatServiceImpl extends BaseServiceImpl<ApplyMat> implements ApplyMatService{
	
	@Resource
	private ApplyMatDAO applyMatDAO;

	@Override
	public List<ApplyMat> getMaterials(String processCode) {
		// TODO Auto-generated method stub
		return applyMatDAO.getMaterials(processCode);
	}

	@Override
	public List<ApplyMat> findApplyMat(ApplyMat findParams, int start, int limit) {
		// TODO Auto-generated method stub
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return applyMatDAO.findInfo(findParams);
	}

	@Override
	public Integer countApplyMat(ApplyMat findParams) {
		// TODO Auto-generated method stub
		return applyMatDAO.count(findParams);
	}

	@Override
	public List<ApplyMat> getMatName() {
		// TODO Auto-generated method stub
		return applyMatDAO.getMatName();
	}

}
