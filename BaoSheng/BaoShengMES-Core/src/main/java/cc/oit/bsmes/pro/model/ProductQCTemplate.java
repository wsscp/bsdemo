package cc.oit.bsmes.pro.model;

import javax.persistence.Column;
import javax.persistence.Table;
import cc.oit.bsmes.common.constants.ProductQCStatus;
import cc.oit.bsmes.common.model.Base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * 产品QC检验内容模板
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-12 上午10:48:25
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PRODUCT_QC_TEMP")
public class ProductQCTemplate extends Base {
	private static final long serialVersionUID = -55098789916947293L;
	
	private String code;
	private String productCode;				   //产品CODE
	private String name;
	@Column(name="WIRE_REQU")
	private String wireRequest;				   //样品线要求
	private String preProcess;				   //预处理
	@Column(name="ENV_PARM")
	private String environmentParameter;	   // 环境参数 
	@Column(name="ENV_VALUE")
	private String environmentValue;		   // 实验方法数值 
	@Column(name="MAT_REQU")
	private String matRequest;				   //所需材料/试剂 
	@Column(name="EQIP_REQU")
	private String equipRequest;			   //设备
	private String characterDesc;			   //性能要求特性 
	private String characterValue;			   //性能要求数值 
	private String refContent;                 //参考
	private String remarks;					   //备注  
	private ProductQCStatus     status;		   //状态   
	private String orgCode;
}
