package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.MesClientDAO;
import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.bas.service.MesClientService;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.apache.ibatis.session.RowBounds;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

@Service
public class MesClientServiceImpl extends BaseServiceImpl<MesClient> implements
		MesClientService {

	@Resource
	private MesClientDAO mesClientDAO;

	@Override
	public List<MesClient> getByClientIP(String clientIP) {
		return mesClientDAO.getByClientIP(clientIP);
	}

	@Override
	public List<MesClient> getByClientName(String clientName) {
		return mesClientDAO.getByClientName(clientName);
	}

	@Override
	public List<MesClient> getClientName() {
		return mesClientDAO.getClientName();
	}

	@Override
	public MesClient getByClientMac(String clientMac) {
		return mesClientDAO.getByClientMac(clientMac,getOrgCode());
	}

	@Override
	public List<MesClient> findByRequestMap(Map<String, Object> requestMap,
			int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start,limit));
		return mesClientDAO.findByRequestMap(requestMap);
	}

	@Override
	public Integer countByRequestMap(Map<String, Object> requestMap) {
		return mesClientDAO.countByRequestMap(requestMap);
	}

	@Override
	public int checkUserLoginMesClient(String userCode, String mac) {
		return mesClientDAO.checkUserLoginMesClient(userCode,mac);
	}
}
