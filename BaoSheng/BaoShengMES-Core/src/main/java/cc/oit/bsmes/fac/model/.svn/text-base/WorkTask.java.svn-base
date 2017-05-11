package cc.oit.bsmes.fac.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;

/**
 * 设备计划加工任务负载
 * @author leiwei
 * @version   
 * 2013-12-24 下午2:23:25
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_FAC_WORK_TASKS")
public class WorkTask extends Base {
	
	private static final long serialVersionUID = 4641363843135765236L;
	
	private String  equipCode; 
	private Date workStartTime;
	private Date workEndTime;
	private Boolean finishwork;
	private String  orderItemProDecId;
	private String description;
	private String orgCode;
//	private Product output; // 半成品或成品
	@Transient
	private String equipName; 
	@Transient
	private String processCode;
	@Transient
	private String halfProductCode;
    @Transient
    private Date createTime;
    @Transient
    private Date modifyTime;
    @Transient
    private String createUserCode;
    @Transient
    private String modifyUserCode;
	
	@Transient
	private CustomerOrderItemProDec order;
	@Transient
	private String woNo;
	
}
