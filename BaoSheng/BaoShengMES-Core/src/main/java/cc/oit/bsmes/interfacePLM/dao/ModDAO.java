package cc.oit.bsmes.interfacePLM.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.Desf2;
import cc.oit.bsmes.interfacePLM.model.Mod;

public interface ModDAO extends BaseDAO<Mod> {

	List<Mod> getByProductId(Map<String,Object> param);
	
	public void insertMod(Mod param);
	
	public void insertModObjof(Map<String,String> param);
	
	public List<Mod> getByProductNo(String no);
	
	public void deleteByProductNo(String no);
	
	public void deleteObjofByProductId(String id);
	


}
