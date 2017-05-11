package cc.oit.bsmes.pro.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import jxl.Sheet;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductCraftsBz;
import cc.oit.bsmes.pro.model.ProductProcessBz;

/**
 * ProductCraftsService
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author LeiWei
 * @date 2013年12月31日 下午13:33:55
 * @since
 * @version
 */

public interface ProductCraftsService extends BaseService<ProductCrafts> {

	/**
	 * 根据根据产品代码获取产品工艺。
	 * 
	 * @author LeiWei
	 * @date 2013-12-31下午14:10:35
	 * @param productCode
	 *            产品代码
	 * @see
	 */
	public ProductCrafts getByProductCode(String productCode);

	/**
	 * 获取最新版本的全部工艺。
	 * 
	 * @author chanedi
	 * @date 2014年1月22日 下午4:16:59
	 * @return
	 * @see
	 */
	public List<ProductCrafts> getLatest();

	/**
	 * 根据工艺编号查询工艺对象。
	 * 
	 * @author JinHanyun
	 * @date 2014-1-22 下午4:12:01
	 * @param craftsCode
	 * @return
	 * @see
	 */
	public ProductCrafts getByCraftsCode(String craftsCode);

	/**
     *
     */
	public void importCrafts(Sheet sheet, String orgCode);

	/**
	 * <p>
	 * 查询条件->工艺信息下拉框：支持模糊查询
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @return List<ProductCrafts>
	 * @see
	 */
	public List<ProductCrafts> craftsCombo(String query);

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
	 * 实例化工艺信息：包括下面工序、投入产出、生产线、参数<br/>
	 * 1、查询出产品的标准工艺信息；<br/>
	 * 2、初始化工艺信息：工序与合并工序的map映射关系；<br/>
	 * 3、复制到实表；<br/>
	 * 4、复制工艺下工序信息到实例表；<br/>
	 * 
	 * @author DingXintao
	 * @param craftsId
	 *            工艺ID/主键
	 * @param craftsCname
	 *            工艺别名
	 * @param productCraftsBz
	 *            工艺信息
	 * @param editProcessBzArray
	 *            编辑的工序列表
	 * @param isDefault
	 *            是否默认
	 * @return
	 * */
	public void instanceCrafts(String craftsId, String craftsCname,
			ProductCraftsBz productCraftsBz,
			List<ProductProcessBz> editProcessBzArray, boolean isDefault);

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
	 * 根据产品代码查询工艺id
	 * @param productCode
	 * @return
	 */
	public String getCraftIdByProductCode(String productCode);
	
	/**
	 * 调用函数查看工艺是否有效：包括工序是否连贯，是否没道工序都有投入产出，产出投入是否连贯，产出是否有且只有一个，是否都有生产线
	 * 
	 * @param craftsId 工艺ID
	 * @return 1:合法, 0:不合法
	 * */
	public String validateProductCrafts(String craftsId);
	
}
