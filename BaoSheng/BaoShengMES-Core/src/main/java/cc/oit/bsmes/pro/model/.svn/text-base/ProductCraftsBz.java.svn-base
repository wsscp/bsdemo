package cc.oit.bsmes.pro.model;

import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.util.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

/**
 * 标准产品工艺定义
 * @author 陈翔
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PRODUCT_CRAFTS_BZ")
public class ProductCraftsBz extends Base {

	private static final long serialVersionUID = 568796821L;

	private Boolean isDefault; //是否默认工艺
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate; //工艺失效时间
	private Integer craftsVersion; //工艺版本号
	private String productCode; //产品代码
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate; //工艺生效时间
	private String craftsCode; //工艺代码
	private String craftsName; //工艺名称
	private String orgCode; //数据所属组织
	
	@Transient
	private String productName; // 产品名称

}