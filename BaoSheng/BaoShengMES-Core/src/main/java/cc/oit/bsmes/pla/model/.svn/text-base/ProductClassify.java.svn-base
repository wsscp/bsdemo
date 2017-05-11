package cc.oit.bsmes.pla.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 产品分类树结构
 * @author DingXintao
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_PRODUCT_CLASSIFY")
public class ProductClassify extends Base {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1008796981833071427L;
	
	private String name;  	// 分类名称
	private String pid;		// 分类父键
}
