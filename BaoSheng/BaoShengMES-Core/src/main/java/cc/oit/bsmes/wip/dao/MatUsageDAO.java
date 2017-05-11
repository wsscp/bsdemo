package cc.oit.bsmes.wip.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.MatUsage;

public interface MatUsageDAO extends BaseDAO<MatUsage>{
	
	public void insertMatDetail(Map<String,String> param);
	
	public void insertProAssistDetail(Map<String,String> param);
	
	public void insertAssistDetail(Map<String,String> map);
	
	public List<MatUsage> findExists(MatUsage matUage);
	
	public void deleteByParam(MatUsage matUage);
	
	
}
