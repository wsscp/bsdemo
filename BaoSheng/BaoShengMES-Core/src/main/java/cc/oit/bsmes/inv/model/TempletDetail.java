package cc.oit.bsmes.inv.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * TempletDetail
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-1-12 上午9:10:37
 * @since
 * @version
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_TEMPLET_DETAIL")
public class TempletDetail extends Base{
	
	private static final long serialVersionUID = -7113623564712049882L;
	
	private String templetId;  //模板类型
	private String propName;  //属性名称
	private String propCode;  //属性code
	private String propType;  //属性类型
	private int    propSeq;   //顺序
	private boolean propIsRange;  //属性是否范围

}
