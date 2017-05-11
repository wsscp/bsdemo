package cc.oit.bsmes.interfacePLM.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.ProcessObjof;




/**
 * Created by RongYd on 2015/4/27 
 */
public interface ProcessObjofDAO extends BaseDAO<ProcessObjof>{
	
    public void insertProcessObjof(ProcessObjof param);
    
    public void deleteProcessObjofByPrcvNo(String param);
    
    public List<ProcessObjof> getByProcessId(String processId);
    
    public void insertBatch(@Param("param") List<ProcessObjof> param);
}
