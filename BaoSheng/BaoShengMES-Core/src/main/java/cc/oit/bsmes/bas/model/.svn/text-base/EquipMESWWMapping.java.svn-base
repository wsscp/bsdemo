package cc.oit.bsmes.bas.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INT_EQUIP_MES_WW_MAPPING")
public class EquipMESWWMapping extends Base {

	private static final long serialVersionUID = 7444439536377976070L;
	private String acEquipCode; // 采集系统设备代码
	private String tagName; // 标签名
	private String equipCode; // 设备代码
	private String parmCode; // 设备参数
	private EventTypeContent eventType; // 报警类型
	private String parmName; // 设备参数名称
	private Boolean needDa; // 是否需要数采
	private Boolean needIs; // 是否需要下发
	private Boolean needShow; // 是否需要在终端显示
	private String dataType; // 数据类型

}
