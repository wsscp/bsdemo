package cc.oit.bsmes.pla.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.OaMrp;

/**
 * 
 * OA物料需求
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-4-15 下午2:22:27
 * @since
 * @version
 */
public interface OaMrpService extends BaseService<OaMrp> {

	/**
	 * 菜单首页主列表
	 * 
	 * @author DingXintao
	 * @date 2015-09-17
	 * @return List<OrderOA>
	 */
	public List<OaMrp> list(Map<String, Object> findParams);

	/**
	 * 菜单首页主列表
	 * 
	 * @author DingXintao
	 * @date 2015-09-17
	 * @return List<OrderOA>
	 */
	public int count(Map<String, Object> findParams);

	/**
	 * <p>
	 * 根据合同号,生产单明细ID,组织编码删除OA物料需求
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-4-15 下午2:46:43
	 * @param contractNo
	 * @param orderItemId
	 * @param orgCode
	 * @return
	 * @see
	 */
	int deleteByContractNoOrderItemIdOrgCode(String contractNo, String orderItemId, String orgCode);

	/**
	 * <p>
	 * 查询条件->物料信息下拉框：支持模糊查询
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @return List<Mat>
	 * @see
	 */
	public List<OaMrp> matsCombo(Map<String, Object> findParams);

	/**
	 * <p>
	 * 查询条件->生产线信息下拉框：支持模糊查询
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-6-30 9:56:48
	 * @return List<Mat>
	 * @see
	 */
	public List<OaMrp> equipCombo(Map<String, Object> findParams);

}
