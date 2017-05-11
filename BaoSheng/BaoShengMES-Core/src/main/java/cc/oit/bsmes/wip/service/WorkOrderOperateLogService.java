package cc.oit.bsmes.wip.service;

import cc.oit.bsmes.common.constants.WorkOrderOperateType;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.WorkOrderOperateLog;

public interface WorkOrderOperateLogService extends BaseService<WorkOrderOperateLog> {

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
	public void changeWorkOrderStatus(String workOrderNo, String equipCode, WorkOrderStatus status, WorkOrderOperateType operateType,
			String operateReason, String operator);
	
}
