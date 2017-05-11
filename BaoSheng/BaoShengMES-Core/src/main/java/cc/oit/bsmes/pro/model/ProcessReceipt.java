package cc.oit.bsmes.pro.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PROCESS_RECEIPT")
public class ProcessReceipt extends Base {

	private static final long serialVersionUID = -6445944057973609534L;

	private String receiptCode;
	private String receiptName;
	private String subReceiptCode; // 工艺参数子CODE
	private String subReceiptName; // 工艺参数子名称
	private String receiptTargetValue;
	private String receiptMaxValue;
	private String receiptMinValue;
	private String dataType;
	private String dataUnit;
	private Boolean hasPic;
	private String marks;
	private Boolean needDa; // 是否需要数采
	private Boolean needIs; // 是否需要下发
	private Boolean needShow; // 是否需要在终端显示
	private Boolean needAlarm; // 超差是否报警
	private String valueDomain; // 值域
	private String eqipListId;
	private String equipCode; // 设备编号
	private String acEquipCode; // 真实的设备编码/采集系统设备代码
	private Double frequence;
	@Transient
	private String setValue; // 设定值
	// 数据采集值
	@Transient
	private String daValue;

	private String emphShow;//在终端上重点显示
}