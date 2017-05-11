package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class SpecialCraftsTrace extends Base {
	private static final long serialVersionUID = 7034309314004158727L;
	
	private String contractNo;       //合同号
	private String productInfo;       //产品信息
	private String craftsId;			
	private String craftsCode;       //工艺代号
	private String processName;       	 //工序名称
	private String modifyValue;       		 //修改信息
	private String type;       			//修改类型
	private String code ;       	 //修改属性代码
	private String name;			//修改属性名称
    private String salesOrderItemId; 
    private String processId;
    private String targetValue;
    private String matCode;         //对应物料代码
    private String operator;
    private String saleorderLength;
    private String customerCompany;
}
