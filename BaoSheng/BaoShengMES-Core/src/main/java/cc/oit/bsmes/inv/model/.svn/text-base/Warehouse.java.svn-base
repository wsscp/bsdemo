package cc.oit.bsmes.inv.model;

import javax.persistence.Table;

import cc.oit.bsmes.common.constants.WarehouseType;
import cc.oit.bsmes.common.model.Base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * 
 * 仓库
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-9-12 下午12:00:41
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_WAREHOUSE")
public class Warehouse extends Base{
	private static final long serialVersionUID = -1895731199948890520L;
	
	private String warehouseCode; //仓库代码
	private String warehouseName; //仓库名称
	private String address; //所在地
	private String orgCode;
	private WarehouseType type;
}
