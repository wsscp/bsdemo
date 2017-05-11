package cc.oit.bsmes.inv.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;
/**
 * 
 * 物料属性
 * <p style="display:none">modifyRecord</p>
 * @author leiw
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_MAT_PROP")
public class MatProp extends Base {
	private static final long serialVersionUID = 1564758826910270270L;
	
	private String matId;	//物料表_ID 
	private String templetDetailId;		// 模板属性明细ID 
	private String propTargetValue;		//属性值
	private String propValueFormula;	//属性值计算公式 
	private String propMaxValue;		//属性最大值 
	private String propMinValue;		// 属性最小值 
	@Transient
	private String desc;				//描述
	@Transient
	private String propName;  //属性名称
	@Transient
	private String propCode;  //属性编码
	
	
	@Transient
	private String salesOrderItemId; // 客户订单id：用于保存特殊工艺的变更记录用
	@Transient
	private String processId; //工序id：用于保存特殊工艺的变更记录用
	@Transient
	private String inOrOut; // 投入产出类型：用于保存特殊工艺的变更记录用
	@Transient
	private String matCode; // 旧物料编码：用于保存特殊工艺的变更记录用
	@Transient
	private String matName; // 旧物料名称：用于保存特殊工艺的变更记录用
}
