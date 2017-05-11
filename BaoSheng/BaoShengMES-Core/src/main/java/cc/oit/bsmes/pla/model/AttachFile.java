package cc.oit.bsmes.pla.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_ORD_ATTACH_FILE")
public class AttachFile extends Base{
	
	private static final long serialVersionUID = 5648927173623455245L;
	
	private String id;
	private String salesOrderId; //订单id
	private String realFileName; //附件实际名称
	private String sysFileName;  //附件系统名称
	private String uploadTime;   //上传时间
	@Transient
	private String contractNo; // 合同号
	@Transient
	private String userName; // 合同号
}
