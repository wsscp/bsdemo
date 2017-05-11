package cc.oit.bsmes.pro.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * 产品工艺定义
 * 
 * @author leiwei
 * @version 2013-12-24 下午3:19:41
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PRODUCT_CRAFTS")
public class ProductCrafts extends Base {

	private static final long serialVersionUID = -1680723354562463059L;

	private String craftsCode; // 工艺代码
	@Transient
	private String productName; // 产品名称
	private String craftsName; // 工艺名称
	private String craftsCname; // 工艺别名
	private Integer craftsVersion; // 工艺版本号
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate; // 工艺生效时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate; // 工艺失效时间
	private String productCode; // 产品代码
	private String orgCode; // 所属组织
	private Boolean isDefault;// 是否默认工艺

	public String getIsDefaultText() {
		if (isDefault == null) {
			return "";
		} else if (isDefault) {
			return "是";
		} else {
			return "否";
		}
	}

	@Transient
	private List<ProductProcess> processList; // 工序

	@Transient
	private String processSeq; // 工序顺序集
	@Transient
    private String salesOrderItemId;
	@Transient
	private String isModify;
	@Transient
	private String productCraftsId;
	@Transient
	private Boolean isSpecial;

}
