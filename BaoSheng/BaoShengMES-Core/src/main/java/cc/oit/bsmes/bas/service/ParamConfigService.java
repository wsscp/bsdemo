package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.ParamConfig;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;

import java.util.List;
import java.util.Map;

public interface ParamConfigService extends BaseService<ParamConfig>{

	/**
	 * 根据页面查询条件查找数据
	 * @param requestMap
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ParamConfig> findByRequestMap(Map<String, Object> requestMap, int start,int limit, List<Sort> sortList);
	
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
