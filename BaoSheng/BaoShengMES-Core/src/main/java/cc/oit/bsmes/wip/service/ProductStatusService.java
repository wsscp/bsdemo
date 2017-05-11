package cc.oit.bsmes.wip.service;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.ProductStatus;

import java.util.List;

/**
 * 
 * 生产状态报表
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-3 上午11:43:25
 * @since
 * @version
 */
public interface ProductStatusService extends BaseService<ProductStatus> {
	
	/**
	 * 
	 * <p>生产过程追溯</p> 
	 * @author leiwei
	 * @date 2014-3-4 上午11:02:04
	 * @param param
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<ProductStatus> getProductionProcess(ProductStatus param, int start,int limit, List<Sort> sortList);
	/**
	 * 
	 * <p>获取生产过程追溯记录数</p> 
	 * @author leiwei
	 * @date 2014-3-4 上午11:57:47
	 * @param param
	 * @return
	 * @see
	 */
	Integer countTotalProcess(ProductStatus param);
	List<ProductStatus> getProcessReport(ProductStatus productStatus);
	Integer countTotalProcessReport(ProductStatus productStatus);
	
}
