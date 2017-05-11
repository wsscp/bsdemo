package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 设备参数代码WW与MES映射表
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2014-3-25 上午9:25:52
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INT_EQUIP_MES_WW_MAPPING")
public class ParmsMapping extends Base{ 
	private static final long serialVersionUID = -5972008894240878384L; 
	private String tagName;
	private String acEquipCode;  
	private String equipCode;
	private String parmCode;
 
}