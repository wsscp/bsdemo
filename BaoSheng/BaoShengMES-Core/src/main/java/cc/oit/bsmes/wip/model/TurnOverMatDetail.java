package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 
 * @author rongyd
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_TURNOVER_MAT_DETAIL")
public class TurnOverMatDetail extends Base{

	private static final long serialVersionUID = 7156931186834695628L;

	private String matCode;
	private String matName;
	private Double quotaQuantity;	
    private Double realQuantity;
    private String turnoverRptId;
   
    
}