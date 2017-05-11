package cc.oit.bsmes.common.log.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

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
@Table(name = "T_BAS_ENTITY_INFO")
public class EntityInfo extends Base{
 
	private static final long serialVersionUID = -6894822124744331745L;
	private String entityType;
	private String entityClass;	
	private String appId;
	private String entityName;
	private String entityDesc;
	private String codeProperty;
	private String viewUrl;

	 
}