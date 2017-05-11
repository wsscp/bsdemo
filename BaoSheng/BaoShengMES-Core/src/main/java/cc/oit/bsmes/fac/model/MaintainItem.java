package cc.oit.bsmes.fac.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.Table;
import java.util.*;

/**
 * 设备维护项目
 * @author chanedi
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_FAC_MAINTAIN_ITEM")
public class MaintainItem extends Base {

	private static final long serialVersionUID = -1397508616L;

	private String tempId; //点检模版ID
	private String describe; //项目说明

}