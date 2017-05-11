package cc.oit.bsmes.pla.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;

/**
 * 
 * MaterialRequirementPlanDAO
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-1-17 上午11:31:32
 * @since
 * @version
 */
public interface MaterialRequirementPlanDAO extends BaseDAO<MaterialRequirementPlan> {
	/**
	 * 
	 * 根据工序代码获取物料需求清单
	 * 
	 * @author leiwei
	 * @date 2014-1-20 下午5:07:39
	 * @param orgCode
	 * @return
	 * @see
	 */
	List<MaterialRequirementPlan> findByOrgCode(String orgCode);

	List<MaterialRequirementPlan> getworkorderNo(Map<String,Object> param);

	/**
	 * 根据生产单好查询物料需求清单
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-10 上午10:45:21
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	List<MaterialRequirementPlan> getByWorkOrderNo(String workOrderNo);

	/**
	 * 重新计算更新物料需求计划
	 * 
	 * @author DingXintao
	 * @date 2014-10-27 14:45:21
	 * @param MaterialRequirementPlan
	 * @return int
	 */
	int updateForCalculatorOA(MaterialRequirementPlan mrp);

	/**
	 * 终端查询设备的物料需求
	 * 
	 * @param equipCode 设备编号
	 * @return List<MaterialRequirementPlan>
	 */
	List<MaterialRequirementPlan> getMapRByEquipCode(String equipCode);

	/**
	 * 根据生产单ID查看生产单明细 - 关于物料的
	 * 
	 * @param workOrderNo 生产单编号
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> showWorkOrderDetailMat(String workOrderNo);

	/**
	 * 投入物料的数据补充，查询物料需求的物料明细
	 * 
	 * @param params 参数: {id 物料需求主键, matCode 物料编号}
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getPatchInAttrDesc(Map<String, String> params);

	public List<MaterialRequirementPlan> getMaterialInfo(Map<String, Object> params);

	public void insertMoreMaterial(Map<String, Object> params);

	List<MaterialRequirementPlan> getByWorkOrderNoId(String id);

	public List<MaterialRequirementPlan> findSum(String planDate);
	
	public Integer countSum(String planDate);

}
