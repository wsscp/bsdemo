package cc.oit.bsmes.interfacePLM.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.MpartInOut;

/**
 * MpartInOutDAO
 * <p style="display:none">流程投入产出DAO</p>
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
public interface MpartInOutDAO extends BaseDAO<MpartInOut> {
	
    /**
	 * 获取PLM更新过的数据
	 * 
	 * @param processId
	 *            工序Id
	 * @return List<MpartInOut>
	 */
	public List<MpartInOut> getAsyncDataList(String processId);

	public List<MpartInOut> getRealMpart(Map<String, Object> map);
	
	public void updateCsvalue1(MpartInOut param);
	
	public List<MpartInOut> getAllMpartByProductId(String productId);
	
	public List<MpartInOut> getAllDaoti();
	
	public List<MpartInOut> getMpartByName(String name);
	
	public List<MpartInOut> getAllDaotiMpart();
	
	public Map<String,String> getFieldsByMpartNo(String mpartNo);
	
	public Map<String,String> getFieldsByMpartNoDT(String mpartNo);
	
	public List<MpartInOut> getMaterialJYByProcessId(String processId);
	
	public Map<String,String> getMatJYSpecById(String mpartId);

}
