package cc.oit.bsmes.pla.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.pro.model.ProductCraftsBz;

/**
 * 
 * OrderOADAO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-1-24 下午5:53:07
 * @since
 * @version
 */
public interface OrderOADAO extends BaseDAO<OrderOA> {

	/**
	 * 菜单首页主列表
	 * 
	 * @author DingXintao
	 * @date 2015-09-17
	 * @return List<OrderOA>
	 */
	public List<OrderOA> list(Map<String, Object> findParams);

	/**
	 * 菜单首页主列表
	 * 
	 * @author DingXintao
	 * @date 2015-09-17
	 * @return List<OrderOA>
	 */
	public int count(Map<String, Object> findParams);
	
	public void setOaDate(Map<String, Object> findParams);

	/**
	 * OA查看子列表：工序分解明细/根据订单产品ID获取分解明细
	 * 
	 * @author DingXintao
	 * @date 2014-1-24 下午4:42:52
	 * @param orderItemId 客户订单明细ID
	 * @return List<OrderOA>
	 */
	public List<OrderOA> getSubListByOrderItemId(String orderItemId);

	public List<OrderOA> getSectionOrStructure(String orgCode);

	public List<OrderOA> getStartAndEndDateByCustOrderItemId(Map map);

	public List<OrderOA> getProductCodeByContractNo(String contractNo, String orgCode);

	public List<OrderOA> getOrderOASubPageResult(OrderOA parm);

	/**
	 * 获取手动排程订单产品下的工序
	 */
//	public List<OrderOASubPageResult> getHandScheduleOrderProcess(String orderItemId);

	/**
	 * 获取查询下拉框：合同号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getContractNo(Map<String, Object> param);

	/**
	 * 获取查询下拉框：单位
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getCustomerCompany(Map<String, Object> param);

	/**
	 * 获取查询下拉框：经办人
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getOperator(Map<String, Object> param);

	/**
	 * 获取查询下拉框：客户型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getCustproductType(Map<String, Object> param);

	/**
	 * 获取查询下拉框：产品型号
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getProductType(Map<String, Object> param);

	/**
	 * 获取查询下拉框：产品规格
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getProductSpec(Map<String, Object> param);

	/**
	 * 获取查询下拉框：线芯结构
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getWiresStructure(Map<String, Object> param);

	/**
	 * 获取查询下拉框：线芯数
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getnumberOfWires(Map<String, Object> param);

	/**
	 * 获取查询下拉框：状态
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getstatus(Map<String, Object> param);

	/**
	 * 获取查询下拉框：截面
	 * 
	 * @edit DingXintao
	 * @param param 查询条件：页面form中所有内容
	 */
	public List<OrderOA> getSection(Map<String, Object> param);
}
