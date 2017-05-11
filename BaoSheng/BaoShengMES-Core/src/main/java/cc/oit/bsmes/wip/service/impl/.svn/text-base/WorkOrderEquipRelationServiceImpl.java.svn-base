package cc.oit.bsmes.wip.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.WorkOrderEquipRelationDAO;
import cc.oit.bsmes.wip.model.WorkOrderEquipRelation;
import cc.oit.bsmes.wip.service.WorkOrderEquipRelationService;

@Service
public class WorkOrderEquipRelationServiceImpl extends BaseServiceImpl<WorkOrderEquipRelation> implements WorkOrderEquipRelationService {
	@Resource
	private WorkOrderEquipRelationDAO workOrderEquipRelationDAO;

	/**
	 * 删除生产单关联的设备关系
	 * 
	 * @author DingXintao
	 * @param workOrderNo 生产单编号
	 * @return int
	 * */
	@Override
	public int deleteByWorkOrderNo(String workOrderNo){
		return workOrderEquipRelationDAO.deleteByWorkOrderNo(workOrderNo);
	}

	@Override
	public void updateSeqByWorkOrderNoAndEquipCode(String v_oldWorkOrderNo, String v_newWorkOrderNo, String v_equipCode, String v_operator){
		workOrderEquipRelationDAO.updateSeqByWorkOrderNoAndEquipCode(v_oldWorkOrderNo, v_newWorkOrderNo, v_equipCode, v_operator);
	}

}
