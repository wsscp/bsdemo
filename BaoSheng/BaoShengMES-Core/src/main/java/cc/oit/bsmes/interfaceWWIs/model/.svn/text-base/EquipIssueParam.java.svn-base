package cc.oit.bsmes.interfaceWWIs.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

/**
 * sql 中的数据
 * Created by chanedi on 14-3-17.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INT_EQIP_ISSUE_PARMS")
public class EquipIssueParam extends Base {

    
	private static final long serialVersionUID = -7703575725871481140L;
	private String equipCode;
    private String parmCode;
    private String targetValue;
    private String upValue;
    private String downValue;
    private Boolean needAlarm;
    private String workOrderId;
}
