package cc.oit.bsmes.common.log.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 *系统实体信息
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongpign
 * @date 2014-2-11 下午1:20:44
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_BIZ_LOG")
public class BizLog extends Base {
 
	private static final long serialVersionUID = 2923451097163900871L;
	private String bizId;
	private String actionId;
	private String appId;
	private String userCode;
	private String userName;
	private String orgCode;
	private String orgName;
	private Date bizTime;
	private String entityId;
	private String entityCode;
	private String entityName;
	private Boolean isException; 

}