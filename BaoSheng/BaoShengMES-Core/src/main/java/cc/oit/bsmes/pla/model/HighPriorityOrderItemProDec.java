package cc.oit.bsmes.pla.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_HIGH_PRIORITY_PRO_DEC")
public class HighPriorityOrderItemProDec extends Base{
	private static final long serialVersionUID = -3908631412759663758L;

	private Integer seq;
	private String equipCode;
}
