package cc.oit.bsmes.pla.service;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.OrderOA;

/**
 * 
 * 订单OA结果查看
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-1-23 下午5:55:52
 * @since
 * @version
 */
public interface OrderOAService extends BaseService<OrderOA> {
	/**
	 * 菜单首页主列表
	 * 
	 * @author DingXintao
	 * @date 2015-09-17
	 * @return List<OrderOA>
	 */
	public List<OrderOA> list(Map<String, Object> findParams);
	
	public void setOaDate(Map<String, Object> findParams);
	/**
	 * 菜单首页主列表
	 * 
	 * @author DingXintao
	 * @date 2015-09-17
	 * @return List<OrderOA>
	 */
	public int count(Map<String, Object> findParams);

	/**
	 * OA查看子列表：工序分解明细/根据订单产品ID获取分解明细
	 * 
	 * @author DingXintao
	 * @date 2014-1-24 下午4:42:52
	 * @param orderItemId 客户订单明细ID
	 * @return List<OrderOA>
	 */
	public List<OrderOA> getSubListByOrderItemId(String orderItemId);

	/**
	 * 
	 * 获取客户销售订单明细的截面和线芯结构
	 * 
	 * @author leiwei
	 * @date 2014-1-23 下午5:56:18
	 * @return
	 * @see
	 */
	List<OrderOA> getSectionOrStructure();

	/**
	 * 
	 * <p>
	 * 根据客户生产订单明细id获取每一工序的最早结束时间和最晚结束时间
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-4-1 下午4:11:59
	 * @param customerOrderItemId 客户生产订单明细id
	 * @param equipCode 生产线
	 * @param processId 工序
	 * @return
	 * @see
	 */
	List<OrderOA> getStartAndEndDateByCustOrderItemId(String customerOrderItemId, String equipCode, String processId);

	/**
	 * 
	 * <p>
	 * 获取客户生产订单明细合同号(该合同下的产品工序开始时间>=当前时间)
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-4-10 下午3:00:13
	 * @see
	 */
	public Multimap<String, OrderOA> getContractNo(OrderOA parm);

	/**
	 * 
	 * <p>
	 * 获取该合同号下的产品code
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-4-10 下午3:18:23
	 * @param contractNo
	 * @return
	 * @see
	 */
	List<OrderOA> getProductCodeByContractNo(String contractNo);

	// /**
	// *
	// * <p>根据客户生产订单明细获id获取订单明细工序用时分解信息</p>
	// * @author leiwei
	// * @date 2014-4-15 下午4:34:27
	// * @param id
	// * @return
	// * @see
	// */
	// List<OrderOASubPageResult> getByOrderItemId(String id);

	/**
	 * 获取手动排程订单产品下的工序
	 */
//	public List<OrderOASubPageResult> getHandScheduleOrderProcess(String orderItemId);

}
