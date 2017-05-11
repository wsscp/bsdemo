package cc.oit.bsmes.pla.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.Product;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * ProductDAO
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月31日 下午3:55:54
 * @since
 * @version
 */
public interface ProductDAO extends BaseDAO<Product> {

	public Product getByProcessId(String processId);

	public List<Product> getByWorkOrderNO(String workOrderNo);
    
    /**
     * <p>模糊查询列表：根据name或code</p> 
     * @author DingXintao
     * @date 2014-6-30 9:56:48
     * @param param 参数
     * @return List<Product>
     * @see
     */
    List<Product> findByCodeOrName(Map<String, Object> param);
    
    List<Product> findByType(Map<String, Object> param);

	public List<Product> getByProductCodeOrName(Map map);
	
	/**
	 * 获取mes中与plm工序下对应生产线数量不同的产品
	 * @return
	 */
	public List<Product> getUncompletedScxFromMes();
	
	public Product getByProductTypeAndSpec(@Param("productType")String productType,@Param("productSpec")String productSpec);
	
	public Product getProBySpecific(Map<String,String> param);
	
	public List<Product> getProductByPrcvNo(String prcvNo);
}
