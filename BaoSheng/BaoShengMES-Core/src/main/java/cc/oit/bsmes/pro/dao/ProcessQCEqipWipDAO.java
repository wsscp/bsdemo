package cc.oit.bsmes.pro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessQCEqip;
import cc.oit.bsmes.pro.model.ProcessQCEqipWip;

public interface ProcessQCEqipWipDAO extends BaseDAO<ProcessQCEqipWip>{

	public void insertAll(@Param("processQCEqipWipList")List<ProcessQCEqip> processQCEqipWip);
	
	public void deleteDate(String oldCraftsId);
}
