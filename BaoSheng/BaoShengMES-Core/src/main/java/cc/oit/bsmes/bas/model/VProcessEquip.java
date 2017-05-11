package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "V_PROCESS_EQUIP")
public class VProcessEquip extends Base{
	private static final long serialVersionUID = 4138687810799090249L;
	private String itemCode;
	private String equipCode;
	
}
