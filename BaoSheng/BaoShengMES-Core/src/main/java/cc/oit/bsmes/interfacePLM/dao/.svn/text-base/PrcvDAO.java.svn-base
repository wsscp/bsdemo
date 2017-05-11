package cc.oit.bsmes.interfacePLM.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfacePLM.model.Prcv;

/**
 * PrcvDAO
 * <p style="display:none">产品工艺基本信息DAO</p>
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
public interface PrcvDAO extends BaseDAO<Prcv> {
	
    /**
     * 获取PLM更新过的数据
     * @param lastDate 上次更新时间
     * @return List<Prcv>
     */
	public List<Prcv> getAsyncDataList(Map<String, Date> findParams);
	
	/**
	 * MES 同步 PLM 根据产品编码获取PLM工艺信息
	 * 
	 * @param lastDate
	 *            更新时间
	 */
	public List<Prcv> getPrcvArrayByProductCodeArray(Map<String, Object> findParams);
	
	public List<Prcv> getPrcvByNo(String param);
	
	public List<Prcv> checkNoUseData(String param);
	
	public List<Prcv> checkPrcvByNo(String param);
	
	public List<Prcv> getAllMesPrcv();
	
	public List<Prcv> getSingleMesPrcv(String prcvNo);
	
	public List<Prcv> getNoNotExistsInMes();
	
	public List<Prcv> checkExistPrcvObjof(Map<String,String> param);
	
	public List<Prcv> getPrcvByPrcvNoAndProNo(Map<String,String> param);
	
	public void insertPrcvByCopy(Map<String,String> param);
	
	public void updateLoNameFsize(Map<String,String> param);
	
	public void deletePrcvByPrcvNo(String param);

	public List<Prcv> getPrcvByProductNo(String param);
	
	public List<Prcv> checkPrcvContain(String prcvNo);
	
	public void updatePrcvModifyTime(String prcvNo);
	
	public void changePrcvFname(String productCode);
	
	public void updateModifyTimeByPrcvId(String prcvId);
	
	public void updateSmemo(String prcvId);
	
	public List<Map<String,Object>> getAllUnSynPrcv();
	
	public List<Prcv> getRelatedPrcvByMpart(String mpartNo);
	
	public List<Prcv> getAllVersionPrcv(String prcvNo);
	
}
