package cc.oit.bsmes.pla.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_PRODUCT_DELIVERY")
public class ManualManage extends Base{
	
	private static final long serialVersionUID = 736182168465063467L;
	
	private String salesOrderItemId;
	
	private Date ptFinishTime;//配套完成时间pt_finish_time
	
	private Date clFinishTime;//成缆完成时间
	
	private Date bzFinishTime;//编织完成时间
	
	private Date htFinishTime; //护套完成时间
	
	private Date coordinateTime; //排产时间
	 
	private String infoSources ; //信息来源
	

	
	private String remarks ;//备注
	
	private Date createTime;
	
	private Date modifyTime;
	@Transient
	private String contractLength;//交货长度
	@Transient
	private String contractNo; // 合同号

	@Transient
	private String custProductType;
	@Transient
	private String createUserCode;
	@Transient
	private String modifyUserCode;

}
