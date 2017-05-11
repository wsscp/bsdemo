package cc.oit.bsmes.interfacePLM.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.ProcessProcessObjof;




/**
 * Created by RongYd on 2015/4/29 
 */
public interface ProcessProcessObjofDAO extends BaseDAO<ProcessProcessObjof>{
    List<ProcessProcessObjof> getByPrcvNo(String param);
    
    List<ProcessProcessObjof> getByPrcvId(String param);
    
    public void insertNewProcessProcessObjof(ProcessProcessObjof param);
    
    public void updateItemId1(Map<String,String> map);
    
    public void updateItemId2(Map<String,String> map);
    
    List<ProcessProcessObjof> checkProcess(Map<String,String> map);
    
    public void deletePPObjofByPrcvNo(String param);
    
    public void insertBatch(@Param("param") List<ProcessProcessObjof> param);
}
