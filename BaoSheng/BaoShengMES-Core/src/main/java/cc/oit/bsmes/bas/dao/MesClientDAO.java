package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;
import java.util.Map;

public interface MesClientDAO extends BaseDAO<MesClient> {
	public List<MesClient> getByClientIP(String clientIP);
	
	public List<MesClient> getByClientName(String clientName);
	
	public List<MesClient> getClientName();
	
	public MesClient getByClientMac(String clientMac,String orgCode);
	
	public List<MesClient> findByRequestMap(Map<String, Object> requestMap);
    
    public Integer countByRequestMap(Map<String, Object> requestMap);
    
    public List<MesClient> getEquipInfo(Map<String, Object> param);
    
    public List<MesClient> getProcessCode(Map<String, Object> param);
    
    public List<MesClient> getTaskStatueInEquip(Map<String, Object> param);

	/**
	 * 验证用户是否有终端上设备的权限
	 * @param userCode
	 * @param mac
	 * @return
	 */
	public int checkUserLoginMesClient(String userCode,String mac);
}
