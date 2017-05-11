package cc.oit.bsmes.pro.service;

import java.util.List;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.model.ProductProcessBz;

/**
 * @author 陈翔
 */
public interface ProductProcessBzService extends BaseService<ProductProcessBz> {

	/**
	 * 获取产品的标准工序流程
	 * 
	 * @author DingXintao
	 * @param productCode 产品代码
	 */
	public List<ProductProcessBz> getByProductCode(String productCode);
	
	/**
	 * 查看工艺工序详情：升序
	 * 
	 * @author DingXintao
	 * @param productCraftsId 工艺ID
	 * @return List<ProductProcess>
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
	 * 页面选择工艺查看工序
	 * @param findParams
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 */
	public List<ProductProcessBz> findByCraftsIdAndParam(ProductProcessBz findParams, int start, int limit,
			List<Sort> sortList);
	
	/**
	 * 所选工艺的工序总数
	 * @param findParams
	 * @return
	 */
	public int countByCraftsIdAndParam(ProductProcessBz findParams);
	
	
}