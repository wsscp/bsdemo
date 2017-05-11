package cc.oit.bsmes.pro.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProductProcess;

/**
 * ProductProcessDAO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author LeiWei
 * @date 2013年12月30日 下午1:33:35
 * @since
 * @version
 */
public interface ProductProcessDAO extends BaseDAO<ProductProcess> {

	List<ProductProcess> getByProductCode(String productCode);

	double getEqipCapacityByProcessIdAndEqipCode(Map<String, Object> map);

	List<ProductProcess> getByProductCraftsId(String productCraftsId);

	/**
	 * 
	 * <p>
	 * 根据页面条件查询工序记录
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-11 下午6:12:17
	 * @param param
	 * @return
	 * @see
	 */
	public List<ProductProcess> findByCraftsIdAndParam(ProductProcess param);

	/**
	 * 
	 * <p>
	 * 根据页面条件统计工序记录
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-11 下午6:12:44
	 * @param param
	 * @return
	 * @see
	 */
	public int countByCraftsIdAndParam(ProductProcess param);

	/**
	 * 
	 * <p>
	 * 根据页面条件查询工序记录
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-11 下午6:12:17
	 * @param param
	 * @return
	 * @see
	 */
	public List<ProductProcess> findByParam(Map<String, Object> param);

	/**
	 * 
	 * <p>
	 * 根据页面条件统计工序记录
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-11 下午6:12:44
	 * @param param
	 * @return
	 * @see
	 */
	public int countByParam(Map<String, Object> param);

	List<ProductProcess> getByProductCraftsIdAndSeq(Map<String, Object> map);

	/**
	 * <p>
	 * 通过设备代码查询设备关联工序
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-28 下午3:21:03
	 * @param param
	 * @return
	 * @see
	 */
	public List<Map<String, Object>> findByEquipCode(Map<String, Object> param);

	/**
	 * <p>
	 * 通过设备代码查询设备关联工序
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-11-17 10:09:21
	 * @param equipCode
	 * @return List<ProductProcess>
	 */
	public List<ProductProcess> getByEquipCode(String equipCode);

	/**
	 * 查看工艺工序详情：升序
	 * 
	 * @author DingXintao
	 * @param productCraftsId 工艺ID
	 * @return List<ProductProcess>
	 */
	public List<ProductProcess> getByProductCraftsIdAsc(String productCraftsId);

	
	/**
	 * 查看工段的工序列表 ： 成缆工序重组： 产看订单产品的成缆工段工序
	 * 
	 * @author DingXintao
	 * @param orderItemId 订单产品ID
	 * @param section 工段
	 * @return List<ProductProcess>
	 */
	public List<ProductProcess> getSectionProcessArray(Map<String, Object> param);


	/**
	 * 根据下一工序Id 得到父工序
	 * @param nextProcessId
	 * @return
	 */
	public List<String> getParentProcessId(String nextProcessId);
	
	public void setProEqipList();
	
	public void updateOrderCraftsId();

	/**
	 * add by JinHanyun
	 *
	 * @param salesOrderItemId
	 * @return
	 */
	public List<Map<String, String>> getSaleOrderProcess(String salesOrderItemId);
	
	public List<Map<String,String>> isJYProcess(String custOrderItemId);
	
	public List<Map<String,String>> getRBMatPropsByProcessId(String processId);
}
