package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.ParamConfig;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;
import java.util.Map;

public interface ParamConfigDAO extends BaseDAO<ParamConfig>{

	/**
	 * 根据页面查询条件查找数据
	 * @param requestMap
	 * @return
	 */
	public List<ParamConfig> findByRequestMap(Map<String, Object> requestMap);

	/**
	 * 根据页面查询条件查找数据
	 * @param requestMap
	 * @return
	 */
	public Integer countByRequestMap(Map<String, Object> requestMap);
	
	/**
	 * 根据参数号查询参数详细信息
	 * @param code
	 * @return
	 */
	public ParamConfig getByParamCode(String code);

}
