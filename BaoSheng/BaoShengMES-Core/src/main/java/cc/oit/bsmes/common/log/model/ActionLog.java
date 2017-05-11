package cc.oit.bsmes.common.log.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 *HTTP层访问日志
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongpign
 * @date 2014-2-11 下午1:20:44
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_ACTION_LOG")

public class ActionLog extends  Base {
 	private static final long serialVersionUID = 560643451498010229L;
	private String userCode;
	private String userName;
	private String orgCode;
	private String orgName;
	private String serverName;
	private String serverAddr;
	private String clientName;
	private String clientAddr;
	private String clientMac;
	private String clientUserAgent;
	private String sessionId;
	private String locale;
	private Date requestTime;
	private Date responseTime;
	private String appId;
	private String menuId;
	private String url;
	private String method;
	private String params;
	private String actionClass;
	private String actionMethod;
	private String actionResult;
	private Boolean isException; 
}