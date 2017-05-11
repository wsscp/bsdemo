package cc.oit.bsmes.interfacePLM.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.CanShuKu;

/**
 * CanShuKuDAO
 * <p style="display:none">
 * 参数库DAO
 * </p>
 * 
 * @author DingXintao
 * @date 2014-12-16 13:34:34
 */
public interface CanShuKuDAO extends BaseDAO<CanShuKu> {
	
	/**
	 * 获取PLM更新过的数据
	 * 
	 * @param no 参数编码
	 * @return List<CanShuKu>
	 */
	public List<CanShuKu> getAsyncDataList(String no);
	
	/**
	 * 获取PLM工序参数信息
	 * 
	 * @param processId 工序ID
	 * @return List<CanShuKu>
	 */
	public List<CanShuKu> getParamArrayByProcessId(String processId);
	

	public List<CanShuKu> getRealCanShuKu(Map<String, Object> map);
	
	/**
	 * 获取PLM工序参数信息
	 * 
	 * @param processId 工序ID
	 * @return List<CanShuKu>
	 */
	public List<CanShuKu> getParamArrayByProcessId2(String processId);
	
	/**
	 * 获取PLM工序参数信息
	 * 
	 * @param processId 工序ID
	 * @return List<CanShuKu>
	 */
	public List<CanShuKu> getParamArrayByProcessId3(String processId);
	
	public List<CanShuKu> getParamArrayByScxId(String processId);

}
