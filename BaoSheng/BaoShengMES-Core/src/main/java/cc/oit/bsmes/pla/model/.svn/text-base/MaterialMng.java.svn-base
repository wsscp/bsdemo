package cc.oit.bsmes.pla.model;

import java.util.Date;

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
@Table(name = "T_PLA_MRP")
public class MaterialMng extends Base{
	
	private static final long serialVersionUID = 5648927173623455245L;
	
	private String id;
	private String workOrderNo;
	private String equipName;
	private String name;
	private String workOrderId;
	private String quantity;
	private String matCode;
	private String matName;
	private String planAmount;
	private String unit;
	private Date planDate;
	private MaterialStatus status;
	@Transient
	private String color;
	@Transient
	private String demandTime;
	@Transient
    private String typeSpec;
    @Transient
    private String inAttrDesc;
	@Transient
    private String demandQuantity;
	@Transient
    private String materialName;
	@Transient
    private String faLiaoQuantity;
	@Transient
	private String equipCode;
	@Transient
	private String matQuantity;
	@Transient
	private String userCode;
	@Transient
	private String userName;
	@Transient
	private String buLiaoQuantity;
	@Transient
	private String yaoLiaoQuantity;
	@Transient
	private String faYaoLiaoQuantity;
	@Transient
	private String faBuLiaoQuantity;
	@Transient
	private String jiTaiQuantity;
}
