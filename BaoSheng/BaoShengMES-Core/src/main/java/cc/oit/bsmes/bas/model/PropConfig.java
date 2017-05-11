package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_PROPERTIES")
public class PropConfig extends Base{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4138687810799090249L;

	private String keyK;   //KEY_K  键
	private String valueV;  //VALUE_V  值
	private String description;   //DESCRIPTION  描述
	private Boolean status;   //STATUS   状态 
}
