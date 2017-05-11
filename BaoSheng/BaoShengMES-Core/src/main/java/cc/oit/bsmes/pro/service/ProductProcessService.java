package cc.oit.bsmes.pro.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProductProcess;

/**
 * ProductProcessService
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author LeiWei
 * @date 2013年12月24日 下午15:38:55
 * @since
 * @version
 */

public interface ProductProcessService extends BaseService<ProductProcess> {

	/**
	 * 根据根据产品代码获取产品工艺流程工序，包含该 工序所需要的物料投入产出
	 * 
	 * @author LeiWei
	 * @date 2013-12-31下午14:00:35
	 * @param productCode 产品代码
	 * @see
	 */
	public List<ProductProcess> getByProductCode(String productCode);

	/**
	 * 根据流程id和设备编码获取设备能力
	 * 
	 * @author leiwei
	 * @date 2014-1-9 下午5:10:50
	 * @param processId 流程id
	 * @param equipCode 设备编码
	 * @return (单位:米/秒)
	 * @see
	 */
	public double getEqipCapacityByProcessIdAndEqipCode(String processId, String equipCode);

	/**
	 * 
	 * <p>
	 * 根据工艺id获取工序流程
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-2-17 下午3:49:46
	 * @param productCraftsId
	 * @return
	 * @see
	 */
	public List<ProductProcess> getByProductCraftsId(String productCraftsId);

	/**
	 * <p>
	 * 根据页面条件查询工序记录)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-12 上午10:36:06
	 * @param findParams
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see
	 */
	public List<ProductProcess> findByCraftsIdAndParam(ProductProcess findParams, int start, int limit,
			List<Sort> sortList);

	/**
	 * <p>
	 * 根据页面条件统计工序记录
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-12 上午10:36:54
	 * @param findParams
	 * @return
	 * @see
	 */
	public int countByCraftsIdAndParam(ProductProcess findParams);

	/**
	 * <p>
	 * 根据页面条件查询工序记录)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-12 上午10:36:06
	 * @param findParams
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see
	 */
	public List<ProductProcess> findByParam(Map<String, Object> findParams, int start, int limit, List<Sort> sortList);

	/**
	 * <p>
	 * 根据页面条件统计工序记录
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-12 上午10:36:54
	 * @param findParams
	 * @return
	 * @see
	 */
	public int countByParam(Map<String, Object> findParams);

	/**
	 * 
	 * <p>
	 * 根据工艺code和加工顺序获取工序
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-5-9 上午10:55:37
	 * @param craftsCode
	 * @param seq
	 * @return
	 * @see
	 */
	@Deprecated
	public List<ProductProcess> getByProductCraftsCodeAndSeq(String craftsCode, int seq);

	/**
	 * <p>
	 * 通过设备代码查询设备关联工序
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-28 下午2:56:45
	 * @param equipCode
	 * @param query
	 * @return
	 * @see
	 */
	public List<Map<String, Object>> findByEquipCode(String equipCode, String query);

	/**
	 * <p>
	 * 通过设备代码查询设备关联工序
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-11-17 10:09:21
	 * @param equipCode 设备编码
	 * @return List<ProductProcess>
	 */
	public List<ProductProcess> getByEquipCode(String equipCode);

	/**
	 * 
	 * @param processCode
	 * @return
	 */
	public List<ProductProcess> getByProcessCode(String processCode);

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
	public List<ProductProcess> getSectionProcessArray(String[] orderItemId, String section);



	public List<String> getParentProcessId(String nextProcessId);

	
	public void setProEqipList();


	/**
	 * 根据订单明细ID 查询工艺流程信息
	 * @param salesOrderItemId
	 * @return
	 */
	public List<Map<String,String>> getSaleOrderProcess(String salesOrderItemId);

}
