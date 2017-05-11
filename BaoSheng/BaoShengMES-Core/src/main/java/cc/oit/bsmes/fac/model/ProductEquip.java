package cc.oit.bsmes.fac.model;

import javax.persistence.Column;
import javax.persistence.Table;

import cc.oit.bsmes.common.model.Base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * 设备与生产线关系
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-5-27 上午11:18:25
 * @since
 * @version
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_FAC_PRODUCT_EQIP")
public class ProductEquip extends Base{
	private static final long serialVersionUID = 2583934111685286101L;
	
	private String productLineId;
	private String equipId;
	private String orgCode;
	private String status;
	@Column(name="IS_MAIN")
	private Boolean isMain;

}
