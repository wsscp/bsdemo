package cc.oit.bsmes.pro.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductCraftsWip;

public interface ProductCraftsWipDAO extends BaseDAO<ProductCraftsWip>{

	/**
	 * 设置为特殊工艺
	 * @author 前克
	 * @Date 216-03-18
	 * @param params
	 */
	public void updateToSpecial(Map<String,Object> params);
	
	
	/**
	 * <P>
	 * 根据工艺参数id获取工艺信息
	 * <P>
	 * @author 前克
	 * @date 2016-3-21
	 * @param processReceiptId
	 * @return
	 */
	public ProductCraftsWip getByProcessReceiptId(String processReceiptId);
	
	/**
	 * <P>
	 * 根据物料参数id获取工艺信息
	 * <P>
	 * @author 前克
	 * @date 2016-3-21
	 * @param matPropId
	 * @return
	 */
	public ProductCraftsWip getByMatPropId(String matPropId);
	
	public void insertNewDate(@Param("productCraftsWipList")List<ProductCrafts> productCraftsWipList);
	
	public void deleteDate(String oldCraftsId);
	
	public int getCountById(String id);
	
	public int getCountExists(Map<String, Object> existsfindParams);
	
	public ProductCraftsWip getExistsId(Map<String, Object> existsfindParams);
	
	public void dataSeparationFunctionNew(Map<String, List<ProcessQc>> params);
}
