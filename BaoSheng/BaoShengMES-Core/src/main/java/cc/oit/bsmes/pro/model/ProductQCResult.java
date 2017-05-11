package cc.oit.bsmes.pro.model;

import java.util.Date;

import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.ProductQCStatus;
import cc.oit.bsmes.common.model.Base;
/**
 * 
 * 产品QC检验结果
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-13 下午2:51:28
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PRODUCT_QC_RES")
public class ProductQCResult extends Base {
	private static final long serialVersionUID = -8727442588649062083L;
	
	private String conclusion;           //检验结论
	private String sampleBarcode;		 //送检条码
	private String productCode;			 //所属产品
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime;             //录入时间
	private String orgCode;
	private ProductQCStatus     status;		   //状态   
}
