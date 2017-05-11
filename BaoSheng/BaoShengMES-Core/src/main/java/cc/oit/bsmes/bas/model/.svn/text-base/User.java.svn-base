package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2013-12-16 上午9:26:01
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_USER")
public class User extends Base {

	private static final long serialVersionUID = -1195692232172370231L;
	
	@NotNull
	@Size(max=50,min=1)
	private String userCode;
	
	@NotNull
	@Size(max=50,min=5)
	private String password;
	
	private String status;
	
	@Transient
	@OrderColumn
	private String name;
	
	@Transient
	private String orgCode;
	
	@Transient
	private String orgName;
	
	@Transient
	private List<Role> roleList;

    private String supplementary;
    @Transient
    private String role;
}
