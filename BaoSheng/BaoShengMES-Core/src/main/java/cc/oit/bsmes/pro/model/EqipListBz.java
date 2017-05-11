package cc.oit.bsmes.pro.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.model.Base;

/**
 * 标准工序使用设备清单
 * @author 陈翔
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_EQIP_LIST_BZ")
public class EqipListBz extends Base {

	private static final long serialVersionUID = -1909568585L;

	private String processId; //流程ID
	private Double equipCapacity; //设备能力
	private Integer shutDownTime; //后置时间
	private Boolean isDefault; //是否可选设备
	private String equipCode; //辅助设备CODE或者生产线
	private Integer setUpTime; //前置时间
	private EquipType type; //设备类型

	@Transient
	private String equipName; // 设备名称

}