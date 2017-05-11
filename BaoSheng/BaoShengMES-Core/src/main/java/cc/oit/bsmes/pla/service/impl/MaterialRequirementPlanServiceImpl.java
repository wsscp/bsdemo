package cc.oit.bsmes.pla.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.JSONUtils;
import cc.oit.bsmes.pla.dao.MaterialRequirementPlanDAO;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import cc.oit.bsmes.pla.service.MaterialRequirementPlanService;

import com.alibaba.fastjson.JSONObject;

/**
 * MaterialRequirementPlanServiceImpl
 * 
 * @author leiwei
 * @date 2014-1-22 下午6:05:44
 */
@Service
public class MaterialRequirementPlanServiceImpl extends BaseServiceImpl<MaterialRequirementPlan> implements
		MaterialRequirementPlanService {

	@Resource
	private MaterialRequirementPlanDAO materialRequirementPlanDAO;

	@Override
	public List<MaterialRequirementPlan> findByOrgCode(String orgCode) {
		return materialRequirementPlanDAO.findByOrgCode(orgCode);
	}

	@Override
	public List<MaterialRequirementPlan> getworkorderNo(String workOrderNo) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("workOrderNo", "%"+workOrderNo+"%");
		return materialRequirementPlanDAO.getworkorderNo(param);
	}

	@Override
	public List<MaterialRequirementPlan> getByWorkOrderNo(String WorkOrderNo) {
		return materialRequirementPlanDAO.getByWorkOrderNo(WorkOrderNo);
	}

	@Override
	public List<MaterialRequirementPlan> findForExport(JSONObject queryFilter) {
		MaterialRequirementPlan findParams = (MaterialRequirementPlan) JSONUtils.jsonToBean(queryFilter,
				MaterialRequirementPlan.class);
		return materialRequirementPlanDAO.find(findParams);
	}

	@Override
	public int countForExport(JSONObject queryParams) {
		MaterialRequirementPlan findParams = (MaterialRequirementPlan) JSONUtils.jsonToBean(queryParams,
				MaterialRequirementPlan.class);
		return materialRequirementPlanDAO.count(findParams);
	}

	/**
	 * 终端查询设备的物料需求
	 * 
	 * @param equipCode 设备编号
	 * @return List<MaterialRequirementPlan>
	 */
	@Override
	public List<MaterialRequirementPlan> getMapRByEquipCode(String equipCode) {
		return materialRequirementPlanDAO.getMapRByEquipCode(equipCode);
	}

	/**
	 * 根据生产单ID查看生产单明细 - 关于物料的
	 * 
	 * @param workOrderNo 生产单编号
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> showWorkOrderDetailMat(String workOrderNo) {
		return materialRequirementPlanDAO.showWorkOrderDetailMat(workOrderNo);
	}

	/**
	 * 投入物料的数据补充，查询物料需求的物料明细
	 * 
	 * @param id 物料需求主键
	 * @param matCode 物料编号
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getPatchInAttrDesc(String id, String matCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("matCode", matCode);
		return materialRequirementPlanDAO.getPatchInAttrDesc(params);
	}

	@Override
	public List<MaterialRequirementPlan> getByWorkOrderNoId(String id) {
		// TODO Auto-generated method stub
		return materialRequirementPlanDAO.getByWorkOrderNoId(id);
	}
	
	public List<MaterialRequirementPlan> findSum(String planDate){
		return materialRequirementPlanDAO.findSum(planDate);
	};
	
	public Integer countSum(String planDate){
		return materialRequirementPlanDAO.countSum(planDate);
	};
}
