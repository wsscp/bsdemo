package cc.oit.bsmes.interfacePLM.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.ScxObjof;
import cc.oit.bsmes.interfacePLM.model.Scxk;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by JinHy on 2014/9/28 0028.
 */
public interface ScxObjofDAO extends BaseDAO<ScxObjof>{
    List<ScxObjof> lastUpdateData( Map<String,Date> findParams);
    
    List<ScxObjof> findByProcessId(String params);
    
    public void insertObjof(ScxObjof param);
    
    public void deleteScxObjofByPrcvNo(String param);
    
}
