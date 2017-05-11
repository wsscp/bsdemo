package cc.oit.bsmes.pro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProcessQcWip;

public interface ProcessQcWipDAO extends BaseDAO<ProcessQcWip>{
	
	public void insertAll(@Param("processQcWipList")List<ProcessQc> processQcWipList);
	
	public void deleteDate(String oldCraftsId);

}
