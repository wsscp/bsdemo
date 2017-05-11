package cc.oit.bsmes.pro.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.model.ProductProcessBz;

/**
 * @author 陈翔
 */
public interface ProductProcessBzDAO extends BaseDAO<ProductProcessBz> {

	/**
	 * 获取产品的标准工序流程
	 * 
	 * @author DingXintao
	 * @param productCode 产品代码
	 * @return
	 */
	public List<ProductProcessBz> getByProductCode(String productCode);
	
	/**
	 * 查看工艺工序详情：升序
	 * 
	 * @author DingXintao
	 * @param productCraftsId 工艺ID
	 * @return List<ProductProcessBz>
	 */
	public List<ProductProcessBz> getByProductCraftsIdAsc(String productCraftsId);
	
	/**
	 * 获取工艺的最后一道工序
	 * 
	 * @author DingXintao
	 * @param craftsBzId 工艺ID
	 * @return
	 */
	public List<ProductProcessBz> getLastProcessList(String craftsBzId);
	
	/**
	 * 获取上一道工序列表
	 * 
	 * @author DingXintao
	 * @param processBzId 工序ID
	 * @return
	 */
	public List<ProductProcessBz> getPrvProcessList(String processBzId);
	
	/**
	 * 查询工序信息
	 * @param param
	 * @return
	 */
	public List<ProductProcessBz> findByCraftsIdAndParam(ProductProcessBz param);
	
	/**
	 * 查询工序总数
	 * @param param
	 * @return
	 */
	public int countByCraftsIdAndParam(ProductProcessBz param);
	
}