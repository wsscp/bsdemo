package cc.oit.bsmes.common.log.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;


/**
 *业务信息
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongpign
 * @date 2014-2-11 下午1:20:44
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_BIZ_INFO")

public class BizInfo extends Base {
	
   
	private static final long serialVersionUID = -4173779154271005447L;
	private String entityId; 
	private String appId;
	private String bizName;
	private String bizDesc;
	private String bizClass;
	private String bizMethod; 
	private Boolean status; 

}