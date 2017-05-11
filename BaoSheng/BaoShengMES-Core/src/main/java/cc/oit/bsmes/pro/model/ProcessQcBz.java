package cc.oit.bsmes.pro.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.DataStatus;
import cc.oit.bsmes.common.constants.QCDataType;
import cc.oit.bsmes.common.model.Base;

/**
 * 标准产品质量检测参数(QA)
 * @author 陈翔
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PROCESS_QC_BZ")
public class ProcessQcBz extends Base {

	private static final long serialVersionUID = -1956421146L;

	private DataStatus dataStatus; //数据状态
	private String processId; //加工工序
	private String marks; //备注
	private Double frequence; //检测频率
	private String needInCheck; //是否要上车检
	private String checkItemName; //检测项名称
	private String valueDomain; //值域
	private Boolean needIs; //是否需要下发
	private QCDataType dataType; //参数数据类型
	private String itemMinValue; //参数下限
	private String needAlarm; //超差是否报警
	private String needFirstCheck; //是否要首检
	private String checkItemCode; //检测项CODE
	private String needShow; //是否需要在终端显示
	private String needMiddleCheck; //是否要中检
	private String itemMaxValue; //参数上限
	private Boolean needDa; //是否需要数采
	private String dataUnit; //参数单位
	private String itemTargetValue; //参数目标值
	private String needOutCheck; //是否要下车检
	private String hasPic; //是否有附件

}