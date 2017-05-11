package cc.oit.bsmes.pla.model;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 产品sop估算
 * @author leiwei
 * @date 2014-1-22 上午10:53:49
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_PRODUCT_SOP")
public class ProductSOP extends Base {
	private static final long serialVersionUID = 2924367347809127034L;
	
	private String productCode;				 //产品代码 
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date earliestFinishDate;		 //最早完成时间 
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date lastFinishDate;			 // 最晚完成时间 
	private Date createTime;				 // 创建时间 	
	
	@Transient
	private String productType;				 //产品型号(添加字段)
	@Transient
	private String productSpec;				 //产品规格(添加字段)
	private String orgCode;   //所属组织
}
