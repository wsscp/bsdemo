package cc.oit.bsmes.interfacePLM.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.PrcvObjof;




/**
 * Created by RongYd on 2015/4/27 
 */
public interface PrcvObjofDAO extends BaseDAO<PrcvObjof>{
    public void insertPrcvObjof(PrcvObjof param);
    
    public PrcvObjof getByProductId(String param);
    
    public void deleteByProuductId(String param);
    
    public void deletePrcvObjofByPrcvNo(String param);
    
    public List<PrcvObjof> getByPrcv(String prcvId);
}
