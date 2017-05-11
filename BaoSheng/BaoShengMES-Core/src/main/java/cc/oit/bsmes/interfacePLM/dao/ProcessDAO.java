package cc.oit.bsmes.interfacePLM.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.Process;

/**
 * ProcessDAO
 * <p style="display:none">产品工艺流程DAO</p>
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
public interface ProcessDAO extends BaseDAO<Process> {
	
    /**
	 * 获取PLM更新过的数据
	 * 
	 * @param @param craftsId 工艺ID
	 * @return List<Process>
	 */
	public List<Process> getAsyncDataList(String craftsId);
	
    /**
	 * 获取最后一道工序的工序列表
	 * 
	 * @param @param craftsId 工艺ID
	 * @return List<Process>
	 */
	public List<Process> getLastProcessList(String craftsId);

	/**
	 * 数据导入
	 * @param params
	 * @return
	 */
	
	public List<Process> getProcessByPrcvId(String param);
	
	public List<Process> getByProductAndPrcv(Map<String,String> params);
	


	public void updateCsValue1(Process process);
	
	public void updateMpartObj(Map<String,String> map);
	
	public void updateMpartObj2(Map<String,String> map);
	
	public void insertMpartObj(Map<String,String> map);
	
	public void insertMpartObj2(Map<String,String> map);
	
	public void insertProcessByCopy(Map<String,String> map);
	
	public Process checkExists(Map<String,String> map);
	
	public Process checkOutExists(Map<String,String> map);
	
	public Process getByPrcvNoAndGno(Map<String,String> params);
	
	public void deleteProcessByPrcvNo(String param);
	
	public void deleteMpartByProcessId(String processId); 
	
	public void deleteMpart2ByProcessId(String processId); 
	
	public List<Process> getCLProcessByProductId(String productId);
	
	public List<Process> getExactCLProcessByName(Map<String,String> param);
	
	public void insertBatch(@Param("param") List<Process> param);
	
	public void updateCsvalueYm2(Map<String,Object> param);
	
	public Process getProcessById(String processId);
	
	public Map<String,String> getJueyuanPropById(String processId);
}
