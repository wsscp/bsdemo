package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2013-12-16 上午9:25:52
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_ROLE")
public class Role extends Base{
	
	private static final long serialVersionUID = -5972008894240878384L;
	
	//部门名称
	@NotNull
	private String name;
	
	private String code;
	
	private String defaultResource; // 角色默认首页Resource
	
	//描述
	private String description;
	
	private String orgName;
	
	private String orgCode;
}