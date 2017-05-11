package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 
 * @author jfliu
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_SYSTEM_PARAM_CONFIG")
public class ParamConfig extends Base{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1017381536198874263L;
	
	private String code;  //CODE 参数CODE
	private String name;  //NAME 参数名
	private String value;    //VALUE  参数值
	private String type;     //TYPE 参数值数据类型
	private String description;   //DESCRIPTION 描述
	private Boolean status;      //STATUS  数据状态    1:正常；0：冻结
	private String orgCode;    //ORG_CODE 数据所属组织

}
