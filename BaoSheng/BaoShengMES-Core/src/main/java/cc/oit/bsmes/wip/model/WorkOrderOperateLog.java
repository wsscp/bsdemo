package cc.oit.bsmes.wip.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.WorkOrderOperateType;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.model.Base;

/**
 * WorkOrder
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2014年1月6日 下午1:34:34
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_WORK_ORDER_OPERATE_LOG")
public class WorkOrderOperateLog extends Base {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1008831118164882946L;

	private String workOrderNo; // 生产单号
	private String equipCode; // 设备编码
	private String operateReason; // 操作原因
	private WorkOrderStatus status; //
	private String orgCode;
	private Date startTime; // 状态开始时间
	private Date endTime; // 状态结束时间

	@Transient
	private String[] equipCodeParams; // 查询参数

	private WorkOrderOperateType OperateType;// 操作类型
	@Transient
	private String userName; // 用户姓名
	@Transient
	private String equipName; // 设备名称

}
