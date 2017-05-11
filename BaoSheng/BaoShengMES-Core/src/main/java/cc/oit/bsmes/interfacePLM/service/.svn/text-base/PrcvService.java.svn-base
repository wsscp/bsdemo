package cc.oit.bsmes.interfacePLM.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.interfacePLM.model.Prcv;

/**
 * PrcvService
 * <p style="display:none">
 * 产品工艺基本信息Service
 * </p>
 * 
 * @author DingXintao
 * @date 2014-09-28 14:34:34
 */
public interface PrcvService extends BaseService<Prcv> {

	
	/**
	 * MES 同步 PLM 工艺数据
	 * 
	 * @param lastDate
	 *            更新时间
	 */
	public List<Prcv> getAsyncDataList(Map<String, Date> findParams);
	
	
	/**
	 * MES 同步 PLM 根据产品编码获取PLM工艺信息
	 * 
	 * @param lastDate
	 *            更新时间
	 */
	public List<Prcv> getPrcvArrayByProductCodeArray(String[] productCodeArray);
	
	/**
	 * MES 同步 PLM 工艺数据
	 * 
	 * @param lastDate
	 *            更新时间
	 */
	public Date asyncData(Map<String, Date> findParams);
	
	public void asyncData(Prcv prcv);
	
	public List<Prcv> getNoNotExistsInMes();
	
	public List<Prcv> getPrcvByProductNo(String param);
	
	public List<Map<String,Object>> getAllUnSynPrcv();
	
	//根据物料编码找到所涉及到的工艺路线
	public List<Prcv> getRelatedPrcvByMpart(String mpartNo);
	//获取所有版本的prcv
	public List<Prcv> getAllVersionPrcv(String prcvNo);
}
