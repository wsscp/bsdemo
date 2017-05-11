package cc.oit.bsmes.wip.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_APPLY_MAT")
public class ApplyMat extends Base{
	
	private static final long serialVersionUID = 5648927173623455245L;
	private String equipCode;
	private String userCode;
	private String matName;
	private Double applyQuntity;
	private Double issueQuntity;
	private MaterialStatus status;
	
	@Transient
	private String material;
	@Transient
	private String userName;
}
