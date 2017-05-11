package cc.oit.bsmes.pro.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pro.dao.ProductProcessBzDAO;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.model.ProductProcessBz;
import cc.oit.bsmes.pro.service.ProductProcessBzService;

/**
 * @author 陈翔
 */
@Service
public class ProductProcessBzServiceImpl extends BaseServiceImpl<ProductProcessBz> implements ProductProcessBzService {

	@Resource
	ProductProcessBzDAO productProcessBzDAO;
	/**
	 * 获取产品的标准工序流程
	 * 
	 * @author DingXintao
	 * @param productCode 产品代码
	 */
	@Override
	public List<ProductProcessBz> getByProductCode(String productCode){
		return productProcessBzDAO.getByProductCode(productCode);
	}
	
	/**
	 * 查看工艺工序详情：升序
	 * 
	 * @author DingXintao
	 * @param productCraftsId 工艺ID
	 * @return List<ProductProcessBz>
	 */
	public List<ProductProcessBz> getByProductCraftsIdAsc(String productCraftsId){
		return productProcessBzDAO.getByProductCraftsIdAsc(productCraftsId);
	}
	
	/**
	 * 获取标准工艺的最后一道工序
	 * 
	 * @author DingXintao
	 * @param craftsBzId 工艺ID
	 * @return
	 */
	@Override
	public List<ProductProcessBz> getLastProcessList(String craftsBzId){
		return productProcessBzDAO.getLastProcessList(craftsBzId);
	}
	
	/**
	 * 获取标准库上一道工序列表
	 * 
	 * @author DingXintao
	 * @param processBzId 工序ID
	 * @return
	 */
	@Override
	public List<ProductProcessBz> getPrvProcessList(String processBzId){
		return productProcessBzDAO.getPrvProcessList(processBzId);
	}

	@Override
	public List<ProductProcessBz> findByCraftsIdAndParam(
			ProductProcessBz findParams, int start, int limit,
			List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return productProcessBzDAO.findByCraftsIdAndParam(findParams);
	}

	@Override
	public int countByCraftsIdAndParam(ProductProcessBz findParams) {
		int count = productProcessBzDAO.countByCraftsIdAndParam(findParams);
		return count;
	}
}