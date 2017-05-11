package cc.oit.bsmes.pla.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.OaMrp;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-4-15 下午1:46:18
 * @since
 * @version
 */
public interface OaMrpDAO extends BaseDAO<OaMrp> {

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

	public int deleteByContractNoOrderItemIdOrgCode(String contractNo, String orderItemId, String orgCode);
}
