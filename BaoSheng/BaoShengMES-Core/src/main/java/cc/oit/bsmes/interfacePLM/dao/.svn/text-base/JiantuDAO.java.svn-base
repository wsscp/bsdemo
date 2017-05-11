package cc.oit.bsmes.interfacePLM.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.Desf2;
import cc.oit.bsmes.interfacePLM.model.Jiantu;

public interface JiantuDAO extends BaseDAO<Jiantu> {
	
	public List<Jiantu> getJiantuByMpartId(String mpartId);

	public List<Jiantu> getAllMaterialJiantu();
	
	public List<Jiantu> getJiantuByMatName(String name);
	
	public void insertJiantu(Jiantu param);
	
	public void insertJiantuObjof(Map<String,String> param);
	
	public void deleteById(String id);
	
	public void deleteObjofByJiantuId(String id);
	

}
