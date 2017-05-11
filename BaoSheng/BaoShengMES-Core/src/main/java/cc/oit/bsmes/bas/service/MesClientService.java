package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;

import java.util.List;
import java.util.Map;

public interface MesClientService extends BaseService<MesClient> {
	public List<MesClient> getByClientIP(String clientIP);
	
	public List<MesClient> getByClientName(String clientName);
	
	public List<MesClient> getClientName();
	
	public MesClient getByClientMac(String clientMac);
	
	public List<MesClient> findByRequestMap(Map<String, Object> requestMap, int start,int limit, List<Sort> sortList);
    
    public Integer countByRequestMap(Map<String, Object> requestMap);

	/**
	 * 验证用户是否有终端设备的权限
	 * @param userCode
	 * @param mac
	 * @return
	 */
	public int checkUserLoginMesClient(String userCode,String mac);
} 
