package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 * 系统消息
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2013-12-16 上午9:25:52
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_SYS_MESSAGE")
public class SysMessage extends Base{
	
	private static final long serialVersionUID = -5972008894240878384L;
	
	private String messageTitle;
	private String messageContent;
	private Boolean hasread;
	private String messageReceiver; // usercode
    private Boolean isNew;
	private Date receiveTime;
	private Date readTime;
	private String orgCode;
}