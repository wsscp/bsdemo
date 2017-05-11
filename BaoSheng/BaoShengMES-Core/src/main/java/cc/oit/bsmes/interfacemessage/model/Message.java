package cc.oit.bsmes.interfacemessage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.MesStatus;
import cc.oit.bsmes.common.constants.MesType;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INT_EMAIL_MESSAGE")
public class Message extends Base{
	private static final long serialVersionUID = 8270718613984915449L;

	private String consignee;//收件人
	private String mesTitle;//标题
	private String mesContent;//内容
	private Integer sendTimes;//发送次数
	private MesStatus status;//状态
	private MesType mesType;//类型	 
	private String exceptionDescription;//异常描述
	private Date sendDate;//发送时间
	
}
