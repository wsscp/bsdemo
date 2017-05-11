package cc.oit.bsmes.pro.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pro.model.ProductCrafts;

/**
 * ProductCraftsDAO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author LeiWei
 * @date 2013年12月30日 下午1:30:34
 * @since
 * @version
 */
public interface ProductCraftsDAO extends BaseDAO<ProductCrafts> {

	public ProductCrafts getByProductCode(String productCode);

	public List<ProductCrafts> getLatest();

	/**
	 * <p>
	 * 模糊查询列表：根据name或code
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @param param
	 *            参数
	 * @return List<ProductCrafts>
	 * @see
	 */
	List<ProductCrafts> findByCodeOrName(Map<String, Object> param);

	/**
	 * <p>
	 * 获取产品的可选工艺列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @param productCode
	 *            产品编码
	 * @return List<ProductCrafts>
	 */
	public List<ProductCrafts> getChooseCraftsArray(String productCode);

	/**
	 * <p>
	 * 获取最近的一个该产品的订单所使用的工艺对象
	 * </p>
	 * 
	 * @author DingXintao
	 * @param productCode
	 *            产品编码
	 * @return ProductCrafts
	 */
	public ProductCrafts getLastOrderUserdCrafts(
			String productCode);
	
	/**
	 * 获取mes中与plm工序下对应生产线数量不同的产品
	 * @return
	 */
	public List<String> getUncompletedScxFromMes();
	
	/**
	 * 根据产品代码查询工艺id
	 * @param productCode
	 * @return
	 */
	public String getCraftIdByProductCode(String productCode);
	
	public void insertCaftsR(String productCode,String caftsId);
	
	/**
	 * 调用函数查看工艺是否有效：包括工序是否连贯，是否没道工序都有投入产出，产出投入是否连贯，产出是否有且只有一个，是否都有生产线
	 * 
	 * @param craftsId 工艺ID
	 * @return 1:合法, 0:不合法
	 * */
	public String validateProductCrafts(String craftsId);
	
	public List<ProductCrafts> getByPrcvByNo(String prcvNo);
	
	public ProductCrafts getLatestPrcvByNo(String craftsCode);
	
	public ProductCrafts getAllById(String id);
}
