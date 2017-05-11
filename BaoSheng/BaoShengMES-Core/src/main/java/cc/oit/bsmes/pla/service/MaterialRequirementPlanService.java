package cc.oit.bsmes.pla.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;

/**
 * 
 * 物料需求清单
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-1-17 上午11:32:35
 * @since
 * @version
 */
public interface MaterialRequirementPlanService extends BaseService<MaterialRequirementPlan> {

	/**
	 * 
	 * 根据机构代码获取物料需求清单
	 * 
	 * @author leiwei
	 * @date 2014-1-20 下午5:04:01
	 * @param orgCode
	 * @return
	 * @see
	 */
	List<MaterialRequirementPlan> findByOrgCode(String orgCode);

	List<MaterialRequirementPlan> getworkorderNo(String workOrderNo);

	/**
	 * <p>
	 * 根据生产单号查询物料需求清单
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-2-10 上午10:46:36
	 * @param WorkOrderNo
	 * @return
	 * @see
	 */
	List<MaterialRequirementPlan> getByWorkOrderNo(String WorkOrderNo);

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
	 * @param id 物料需求主键
	 * @param matCode 物料编号
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getPatchInAttrDesc(String id, String matCode);

	List<MaterialRequirementPlan> getByWorkOrderNoId(String id);

	public List<MaterialRequirementPlan> findSum(String planDate);

	public Integer countSum(String planDate);

}
