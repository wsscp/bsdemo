package cc.oit.bsmes.pla.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_STOCK_CHECK")
public class MaterialCheckReport extends Base{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6531634716693634359L;
	
	
	private String id;
	private String manuName;  //厂家
	private String matName;   //品名或品种
	private String warehouseNo;//库号
	private String spec;//型号
	private String stockComment;//备注
	private String matColor;//颜色
	private String sectionName;//工段名称
	private Date manuDate;// 生产日期
	private Date checkMonth;//盘点月份
	private Date createTime;//创建时间
	
	@Transient
	private String checkDay;
	@Transient
	private int seq;

}
