package cc.oit.bsmes.pla.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.pla.model.Product;

/**
 * 产品基本信息
 * @author leiwei
 * @version   
 * 2013-12-24 下午3:38:55
 */
public interface ProductService extends BaseService<Product> {
	
	/**
	 * 通过设备编码获取产品基本信息   
	 * @author leiwei
	 * @date 2013年12月31日 下午4:08:38
	 * @param productCode
	 * @return
	 * @see
	 */
	public Product getByProductCode(String productCode);

	/**
	 * 通过工序id获取产品基本信息   
	 * @author chanedi
	 * @date 2014年1月23日 下午7:05:44
	 * @param processId
	 * @return
	 * @see
	 */
	public Product getByProcessId(String processId);

	/**
	 * <p>通过生产单号查询产品信息列表</p> 
	 * @author QiuYangjun
	 * @date 2014-2-8 下午3:51:57
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<Product> getByWorkOrderNO(String workOrderNo);
	
	public List<Product> getByEquipCode(String equipCode);
    
    /**
     * <p>查询条件->产品信息下拉框：支持模糊查询</p> 
     * @author DingXintao
     * @date 2014-6-30 9:56:48
     * @return List<Product>
     * @see
     */
	public List<Product> productsCombo(String query);
	
	public List<Product> productsTypeCombo(String query);

	public List<Product> getByProductCodeOrName(String query);
	
	public Product sycAddData(String productNo);
	
	/**
	 * 通过产品型号和产品规格查询产品信息
	 * @param productType
	 * @param productSpec
	 * @return
	 */
	public Product getByProductTypeAndSpec(String productType,String productSpec);

}
