package cc.oit.bsmes.interfacePLM.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.MpartObjof;


/**
 * Created by RongYd on 2015/4/27 
 */
public interface MpartObjofDAO extends BaseDAO<MpartObjof>{
	
   public void deleteByProcessId(String param);
   
   List<MpartObjof> findByProcessId(String param);
   
   public void deleteMpartObjofByPrcvNo(String param);
   
   public void deleteMpartObjofByPrcvNo2(String param);
   
   List<MpartObjof> findMpartoutByProcessId(String param);
   
   public void insertBatchIn(@Param("param") List<MpartObjof> param);
   
   public void insertBatchOut(@Param("param") List<MpartObjof> param);
   
}
