package cc.oit.bsmes.pro.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.UnitType;
import cc.oit.bsmes.common.model.Base;

/**
 * 标准流程投入产出
 * @author 陈翔
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PROCESS_IN_OUT_BZ")
public class ProcessInOutBz extends Base {

	private static final long serialVersionUID = -450305267L;

	private String quantityFormula; //单位投入用量计算公式
	@Transient
	private String matName;
	private UnitType unit; //用量单位
	private String productProcessId; //工艺流程ID
	private String useMethod; //用法
	private String matCode; //物料代码
	private Double quantity; //单位投入用量
	private InOrOut inOrOut; //投入还是产出

}