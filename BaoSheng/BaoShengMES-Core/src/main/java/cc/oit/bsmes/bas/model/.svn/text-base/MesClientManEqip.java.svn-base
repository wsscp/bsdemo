package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_MES_CLIENT_MAN_EQIP")
public class MesClientManEqip extends Base{

	private static final long serialVersionUID = -4853948082957452032L;
	private String mesClientId;
    private String eqipId;

	@Transient
	private String equipCode;
	@Transient
	private String equipName;
	@Transient
	private String mesClientName;

	@Transient
	public String getEquipName(){
		return "["+equipCode+"]"+equipName;
	}

}