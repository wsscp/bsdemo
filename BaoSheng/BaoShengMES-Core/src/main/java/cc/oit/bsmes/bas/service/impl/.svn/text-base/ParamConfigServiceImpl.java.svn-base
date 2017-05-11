package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.ParamConfigDAO;
import cc.oit.bsmes.bas.model.ParamConfig;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.ParamConfigService;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ParamConfigServiceImpl extends BaseServiceImpl<ParamConfig> implements ParamConfigService{

	@Resource
	private ParamConfigDAO paramConfigDAO;
	
	public void insert(ParamConfig paramConfig){
		paramConfig.setId(UUID.randomUUID().toString());
		User user = SessionUtils.getUser();
		paramConfig.setCreateUserCode(user.getUserCode());
		paramConfig.setModifyUserCode(user.getUserCode());
		paramConfigDAO.insert(paramConfig);
	}

	@Override
	public List<ParamConfig> findByRequestMap(Map<String, Object> requestMap,
			int start, int limit, List<Sort> sortList) {
        SqlInterceptor.setRowBounds(new RowBounds(start, limit));
        List<ParamConfig> paramConfigList = paramConfigDAO.findByRequestMap(requestMap);
		return paramConfigList;
	}

	@Override
	public Integer countByRequestMap(Map<String, Object> requestMap) {
		return paramConfigDAO.countByRequestMap(requestMap);
	}

	@Override
	public ParamConfig getByParamCode(String code) {
		return paramConfigDAO.getByParamCode(code);
	}
}
