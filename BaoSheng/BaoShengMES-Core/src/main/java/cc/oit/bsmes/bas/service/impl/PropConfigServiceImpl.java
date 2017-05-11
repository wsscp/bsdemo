package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.PropConfigDAO;
import cc.oit.bsmes.bas.model.PropConfig;
import cc.oit.bsmes.bas.service.PropConfigService;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class PropConfigServiceImpl extends BaseServiceImpl<PropConfig> implements PropConfigService{

	@Resource
	private PropConfigDAO propConfigDAO;

	@Override
	public PropConfig getByPropKey(String key) {
		return propConfigDAO.getByPropKey(key);
	}

	@Override
	public List<PropConfig> findByRequestMap(Map<String, Object> requestMap,
			int start, int limit, List<Sort> sortList) {
		 SqlInterceptor.setRowBounds(new RowBounds(start, limit));
	     List<PropConfig> propConfigList = propConfigDAO.findByRequestMap(requestMap);
		return propConfigList;
	}

	@Override
	public Integer countByRequestMap(Map<String, Object> requestMap) {
		return propConfigDAO.countByRequestMap(requestMap);
	}
	

}
