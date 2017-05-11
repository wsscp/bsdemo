package cc.oit.bsmes.pla.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_BU_LIAO")
public class SupplementMaterial extends Base{
	
	private static final long serialVersionUID = 5648927173623455245L;
	
	private String equipCode;
	private String userCode;
	private String matCode;
	private String workOrderNo;
	private Double buLiaoQuantity;
	private Double faLiaoQuantity;
	private MaterialStatus status;
}
