package cc.oit.bsmes.bas.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * 用户角色。
 * 
 * @author 季景瑜
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_USER_ROLE")
public class UserRole extends Base {

	private static final long serialVersionUID = -2543513527842142725L;
	private String roleId;
	private String userId;
	@Transient
	private String userName;
	@Transient
	private String code;
	@Transient
	private String userCode;
	@Transient
	private String name;
	@Transient
	private String description;
	@Transient
	private String orgCode;
	
}
