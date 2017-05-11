package cc.oit.bsmes.pro.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.model.ProductProcessWip;

public interface ProductProcessWipDAO extends BaseDAO<ProductProcessWip>{
	
	// 批量插入：形成批量插入sql，提高insert效率
	public void insertAll(@Param("productProcessList")List<ProductProcess> productProcessList);
	
	/**
	 * 
	 * <p>
	 * 根据页面条件查询工序记录
	 * </p>
	 * 
	 * @author 宋前克
	 * @date 2016-03-15 16:00
	 * @param param
	 * @return
	 * @see
	 */
	public List<ProductProcessWip> findByCraftsIdAndParam(ProductProcess param);
	
	/**
	 * 
	 * <p>
	 * 根据页面条件统计工序记录
	 * </p>
	 * 
	 * @author 宋前克
	 * @date 2016-03-15 16:00
	 * @param param
	 * @return
	 * @see
	 */
	public int countByCraftsIdAndParam(ProductProcessWip param);
	
	/**
	 * <P>
	 * 设置修改备注信息
	 * <P>
	 * @author 前克
	 * @date 2016-03-21
	 * @param params
	 */
	public void updateModifyRemarks(Map<String,Object> params);
	
	public void deleteDate(String oldCraftsId);
	
	public String getOldPorcessIdById(String id);
	
	/**
	 * 查看工艺工序详情：升序
	 * 
	 * @author 宋前克
	 * @param productCraftsId 工艺ID
	 * @return List<ProductProcess>
	 */
	public List<ProductProcessWip> getByProductCraftsIdAsc(String productCraftsId);

}
