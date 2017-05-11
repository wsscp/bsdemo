package cc.oit.bsmes.pro.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pro.dao.ProductProcessDAO;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProductProcessService;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author LeiWei
 * @date 2013-12-31 下午1:45:02
 * @since
 * @version
 */
@Service
public class ProductProcessServiceImpl extends BaseServiceImpl<ProductProcess> implements ProductProcessService {

	@Resource
	private ProductProcessDAO productProcessDAO;

	@Override
	public List<ProductProcess> getByProductCode(String productCode) {
		return productProcessDAO.getByProductCode(productCode);
	}

	@Override
	public double getEqipCapacityByProcessIdAndEqipCode(String processId, String equipCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("processId", processId);
		map.put("eqipCode", equipCode);
		return productProcessDAO.getEqipCapacityByProcessIdAndEqipCode(map);
	}

	@Override
	public List<ProductProcess> getByProductCraftsId(String productCraftsId) {
		return productProcessDAO.getByProductCraftsId(productCraftsId);
	}

	/**
	 * 
	 * <p>
	 * 根据页面条件查询工序记录
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-12 上午10:36:40
	 * @param findParams
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProductProcessService#findByParam(java.util.Map,
	 *      int, int, java.util.List)
	 */
	@Override
	public List<ProductProcess> findByCraftsIdAndParam(ProductProcess findParams, int start, int limit,
			List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return productProcessDAO.findByCraftsIdAndParam(findParams);
	}

	/**
	 * 
	 * <p>
	 * 根据页面条件统计工序记录
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-12 上午10:37:11
	 * @param findParams
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProductProcessService#countByParam(java.util.Map)
	 */
	@Override
	public int countByCraftsIdAndParam(ProductProcess findParams) {
		int count = productProcessDAO.countByCraftsIdAndParam(findParams);
		return count;
	}

	/**
	 * 
	 * <p>
	 * 根据页面条件查询工序记录
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-12 上午10:36:40
	 * @param findParams
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProductProcessService#findByParam(java.util.Map,
	 *      int, int, java.util.List)
	 */
	@Override
	public List<ProductProcess> findByParam(Map<String, Object> findParams, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return productProcessDAO.findByParam(findParams);
	}

	/**
	 * 
	 * <p>
	 * 根据页面条件统计工序记录
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-3-12 上午10:37:11
	 * @param findParams
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProductProcessService#countByParam(java.util.Map)
	 */
	@Override
	public int countByParam(Map<String, Object> findParams) {
		int count = productProcessDAO.countByParam(findParams);
		return count;
	}

	@Override
	public List<ProductProcess> getByProductCraftsCodeAndSeq(String craftsCode, int seq) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("craftsCode", craftsCode);
		map.put("seq", seq);
		return productProcessDAO.getByProductCraftsIdAndSeq(map);
	}

	/**
	 * <p>
	 * 通过设备代码查询设备关联工序
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-5-28 下午3:12:50
	 * @param equipCode
	 * @param query
	 * @return
	 * @see cc.oit.bsmes.pro.service.ProductProcessService#findByEquipCode(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<Map<String, Object>> findByEquipCode(String equipCode, String query) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("equipCode", equipCode);
		map.put("query", query);
		return productProcessDAO.findByEquipCode(map);
	}

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
	public List<ProductProcess> getByEquipCode(String equipCode) {
		return productProcessDAO.getByEquipCode(equipCode);
	}

	@Override
	public List<ProductProcess> getByProcessCode(String processCode) {
		ProductProcess findParams = new ProductProcess();
		findParams.setProcessCode(processCode);
		return productProcessDAO.get(findParams);
	}

	@Override
	public List<ProductProcess> findForExport(JSONObject queryFilter) throws InvocationTargetException,
			IllegalAccessException, NoSuchMethodException {
		ProductProcess findParams = new ProductProcess();
		findParams.setProductCraftsId(queryFilter.getString("productCraftsId"));
		return productProcessDAO.find(findParams);
	}

	/**
	 * 查看工艺工序详情：升序
	 * 
	 * @author DingXintao
	 * @param productCraftsId 工艺ID
	 * @return List<ProductProcess>
	 */
	public List<ProductProcess> getByProductCraftsIdAsc(String productCraftsId){
		return productProcessDAO.getByProductCraftsIdAsc(productCraftsId);
	}

	
	
	/**
	 * 查看工段的工序列表 ： 成缆工序重组： 产看订单产品的成缆工段工序
	 * 
	 * @author DingXintao
	 * @param orderItemId 订单产品ID
	 * @param section 工段
	 * @return List<ProductProcess>
	 */
	public List<ProductProcess> getSectionProcessArray(String[] orderItemId, String section){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orderItemId", orderItemId);
		param.put("section", section);
		return productProcessDAO.getSectionProcessArray(param);
	}


	@Override
	public List<String> getParentProcessId(String nextProcessId) {
		return productProcessDAO.getParentProcessId(nextProcessId);
	}
	
	public void setProEqipList(){
		productProcessDAO.setProEqipList();
//		productProcessDAO.updateOrderCraftsId();
	}

	@Override
	public List<Map<String, String>> getSaleOrderProcess(String salesOrderItemId) {
		return productProcessDAO.getSaleOrderProcess(salesOrderItemId);
	}
}
