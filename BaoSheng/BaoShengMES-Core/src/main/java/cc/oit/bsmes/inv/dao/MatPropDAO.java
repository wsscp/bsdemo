package cc.oit.bsmes.inv.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.inv.model.MatProp;

public interface MatPropDAO extends BaseDAO<MatProp> {
   
	public void deleteByMatId(String param);
	
	public List<MatProp> getByMatId(Map<String, Object> findParams);
	
	/**
	 * @Title: findByMatCode
	 * @Description: TODO(根据matCode去查询)
	 * @param: matCode 物料编码
	 * @return: List<MatProp>
	 * @throws
	 */
	public List<MatProp> findByMatCode(String matCode);
}
