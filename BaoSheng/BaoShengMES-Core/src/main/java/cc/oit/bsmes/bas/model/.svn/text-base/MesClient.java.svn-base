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
@Table(name = "T_BAS_MES_CLIENT")
public class MesClient extends Base{

	private static final long serialVersionUID = -6577928389716137798L;
	private String clientIp;
    private String clientMac;
    private String clientName;
    private String orgCode;
    private Integer printNum;
    @Transient
    private String equipCode;
    @Transient
    private String processCode;
}