package cc.oit.bsmes.wip.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.WorkOrderOperateType;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.WorkOrderOperateLogDAO;
import cc.oit.bsmes.wip.model.WorkOrderOperateLog;
import cc.oit.bsmes.wip.service.WorkOrderOperateLogService;
import cc.oit.bsmes.wip.service.WorkOrderService;

@Service
public class WorkOrderOperateLogServiceImpl extends BaseServiceImpl<WorkOrderOperateLog> implements
		WorkOrderOperateLogService {
	@Resource
	private WorkOrderOperateLogDAO workOrderOperateLogDAO;
	
	@Resource
	private WorkOrderService workOrderService;

	/**
	 * @Title: changeWorkOrderStatus
	 * @Description: TODO(改变生产单状态)
	 * @param workOrderNo 生产单号
	 * @param equipCode 设备编码
	 * @param status 状态
	 * @param operateType 操作类型
	 * @param operateReason 操作原因
	 * @param operator 操作人
	 * @return: void
	 * @throws
	 */
	@Override
	public void changeWorkOrderStatus(String workOrderNo, String equipCode, WorkOrderStatus status, WorkOrderOperateType operateType,
			String operateReason, String operator) {
		// 1、获取数据库该生产单记录：状态!=FINISHED && endTime == null
		// 2、获取为空，说明无数据，新增一条
		// 3、有结果，更新结束时间，新增一条
		WorkOrderOperateLog workOrderOperateLog = workOrderOperateLogDAO.getLastOperateLog(workOrderNo);
		if (null != workOrderOperateLog) {
//			workOrderOperateLog.setEquipCode(equipCode);
//			workOrderOperateLog.setStatus(WorkOrderStatus.TO_DO);
			//workOrderOperateLog.setOperateType(operateType.name());
			workOrderOperateLog.setEndTime(new Date());
			workOrderOperateLogDAO.update(workOrderOperateLog);
		}

		// if (status != WorkOrderStatus.FINISHED) { // 没完成，新增一条
			workOrderOperateLog = new WorkOrderOperateLog();
			workOrderOperateLog.setWorkOrderNo(workOrderNo);
			workOrderOperateLog.setEquipCode(equipCode);
			workOrderOperateLog.setStatus(status);
//			workOrderOperateLog.setStatus(WorkOrderStatus.TO_DO);
			workOrderOperateLog.setOperateReason(operateReason);
			workOrderOperateLog.setOperateType(operateType);
			workOrderOperateLog.setOrgCode(this.getOrgCode());
			workOrderOperateLog.setCreateUserCode(operator);
			workOrderOperateLog.setModifyUserCode(operator);
			workOrderOperateLog.setStartTime(new Date());
			workOrderOperateLogDAO.insert(workOrderOperateLog);
		// }
	}
}
