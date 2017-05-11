package cc.oit.bsmes.pro.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.ProductQCStatus;
import cc.oit.bsmes.common.model.Base;

/**
 * 
 * 产品QC检验结果明细
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-13 下午3:01:54
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PRODUCT_QC_DET")
public class ProductQCDetail extends Base {
	private static final long serialVersionUID = -6976585657683014608L;
	
	private String qcTempId;			//检验模板ID
	private String qcResId;				//检测结果ID
	private String qcResult;			//结论
	private String orgCode;
	
	//产品QC检验内容模板表字段
	@Transient
	private String code;
	@Transient
	private String productCode;				   //产品CODE
	@Transient
	private String name;
	@Transient
	@Column(name="WIRE_REQU")
	private String wireRequest;				   //样品线要求
	@Transient
	private String preProcess;				   //预处理
	@Transient
	@Column(name="ENV_PARM")
	private String environmentParameter;	   // 环境参数 
	@Transient
	@Column(name="ENV_VALUE")
	private String environmentValue;		   // 实验方法数值 
	@Transient
	@Column(name="MAT_REQU")
	private String matRequest;				   //所需材料/试剂 
	@Transient
	@Column(name="EQIP_REQU")
	private String equipRequest;			   //设备
	@Transient
	private String characterDesc;			   //性能要求特性 
	@Transient
	private String characterValue;			   //性能要求数值 
	@Transient
	private String refContent;                 //参考
	@Transient
	private String remarks;					   //备注  
	private ProductQCStatus     status;		   //状态   
}
