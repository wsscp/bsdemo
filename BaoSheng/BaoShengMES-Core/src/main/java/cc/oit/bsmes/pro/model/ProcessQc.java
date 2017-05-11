package cc.oit.bsmes.pro.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.DataStatus;
import cc.oit.bsmes.common.constants.QCDataType;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PROCESS_QC")
public class ProcessQc extends Base {

	private static final long serialVersionUID = -1091219251273855324L;
	private String processId;
	private String checkItemCode;
	private String checkItemName;
	private Double frequence;
	private Boolean needDa; // 是否需要数采
	private Boolean needIs; // 是否需要下发
	private QCDataType dataType;
	private String dataUnit;
	private String marks;
	private String itemTargetValue; // 参数目标值
	private String itemMaxValue; // 参数上限
	private String itemMinValue; // 参数下限
	private String hasPic; // 是否有附件
	private String needShow; // 是否需要在终端显示
	private String needFirstCheck; // 是否要首检
	private String needMiddleCheck; // 是否要中检
	private String needInCheck; // 是否要上车检
	private String needOutCheck; // 是否要下车检
	private String needAlarm; // 超差是否报警
	private String valueDomain; // 值域
	@Transient
	private String equipCode;

	// 数据采集值
	@Transient
	private String daValue;
	@Transient
	private String modifyRemarks;
	private String emphShow;// 在终端上重点显示
	private DataStatus dataStatus;// 数据状态
	
	@Transient
	private String salesOrderItemId; // 客户订单id：用于保存特殊工艺的变更记录用
	
}