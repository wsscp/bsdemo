package cc.oit.bsmes.fac.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * 设备信息
 * 
 * @author leiwei
 * @version 2013-12-24 下午2:23:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_FAC_EQIP_IMAGE")
public class EquipImage extends Base {

	private static final long serialVersionUID = -2168521990659057552L;

	private String imagePath; // 图片路径
	private String eqipId; // 设备ID
	private String imageName; // 图片名称
	
	@Transient
	private String code;
	@Transient
	private String equipAlias;
	@Transient
	private String name;
	

	
}
