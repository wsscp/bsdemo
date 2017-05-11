package cc.oit.bsmes.interfacePLM.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.Desf2;

public interface Desf2DAO extends BaseDAO<Desf2> {

	List<Desf2> getByProductId(Map<String,Object> map);
	
	public void insertDesf2(Desf2 param);
	
	public void insertDesf2Objof(Map<String,String> param);

	public List<Desf2> getByProductNo(String no);
	
	public void deleteByProductNo(String no);
	
	public void deleteObjofByProductId(String id);
}
