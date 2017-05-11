package cc.oit.bsmes.inv.model;

import java.util.List;

import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * TODO库存
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2013-12-24 下午4:26:17
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_INVENTORY")
public class Inventory extends Base{

	private static final long serialVersionUID = 8079054016787885227L;
	private String locationId;
    private String warehouseId;
    private String materialCode;
    private String materialName;
    private String barCode;
    private Double quantity;
    private String materialDesc; // 物料信息描述
    private Double lockedQuantity = 0d;
    private String unit;
    @Transient
	@JSONField(serialize = false)
    private List<InventoryDetail> inventoryDetails;
    private String orgCode;
    
    @Transient
    private String workOrderNo; // 生产单号
    
    /**
     * 库存信息
     **/
    @Transient
	private String processCode; // 所在工序编码
    @Transient
    private String serialNum;
	@Transient
	@OrderColumn
	private String processName; // 所在工序名称
	@Transient
	@OrderColumn
    private String warehouseName; // 仓库名称
	@Transient
	@OrderColumn
    private String locationName; // 库位
	@Transient
	@OrderColumn
    private String locationX; // 位置X
	@Transient
	@OrderColumn
    private String locationY; // 位置Y
	@Transient
	@OrderColumn
    private String locationZ; // 位置Z
    
 }