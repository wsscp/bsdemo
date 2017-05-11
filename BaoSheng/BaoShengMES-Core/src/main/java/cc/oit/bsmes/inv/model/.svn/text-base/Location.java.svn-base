package cc.oit.bsmes.inv.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.LocationType;
import cc.oit.bsmes.common.model.Base;
/**
 * 
 * 库位
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-9-12 下午12:11:50
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_LOCATION")
public class Location extends Base {
	private static final long serialVersionUID = 3852146020047072457L;

	private String warehouseId; //所在仓库
	private String processCode; //所在工序
	private String locationName; //库位
	private String locationX;  //位置X
	private String locationY;  //位置Y
	private String locationZ;  //位置Z
	private LocationType type;
	private String orgCode;
	private String usedOrNot;
	private String kuaQu;
	@Transient
	private String warehouseName;
	@Transient
	private String processName;
}
