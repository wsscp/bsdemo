package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.PropConfig;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;
import java.util.Map;

public interface PropConfigDAO extends BaseDAO<PropConfig>{

	/**
	 * 根据键查询详细信息
	 * @param key
	 * @return
	 */
	public PropConfig getByPropKey(String key);
	
	/**
	 * 根据页面查询条件查找数据
	 * @param requestMap
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<PropConfig> findByRequestMap(Map<String, Object> requestMap);
	
	/**
	 * 根据页面查询条件查找数据
	 * @param requestMap
	 * @return
	 */
	public Integer countByRequestMap(Map<String, Object> requestMap);
}
