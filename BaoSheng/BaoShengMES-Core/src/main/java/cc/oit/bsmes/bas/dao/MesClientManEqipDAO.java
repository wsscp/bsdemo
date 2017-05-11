package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.MesClientManEqip;
import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.dto.MesClientEqipInfo;

import java.util.List;
import java.util.Map;

public interface MesClientManEqipDAO extends BaseDAO<MesClientManEqip> {
	
	public List<MesClientEqipInfo> getInfoByMesClientMac(String mac, String ip);

    public MesClientEqipInfo getInfoByEquipCode(String equipCode);
    
    public MesClientManEqip getById(String id);
    
    public List<MesClientManEqip> findByRequestMap(String mesClientId);
    
    public Integer countByRequestMap(String mesClientId);
    
    public MesClientManEqip getByMesClientIdAndEqipId(String mesClientId,String eqipId);

}
