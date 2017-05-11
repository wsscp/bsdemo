package cc.oit.bsmes.inv.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;
import javax.persistence.Table;

/**
 * Templet
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2014-1-8 下午3:47:37
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_TEMPLET")
public class Templet extends Base{
	
	private static final long serialVersionUID = 4565867359217235179L;
	private String name;
    private String desc;
    private String orgCode;
}