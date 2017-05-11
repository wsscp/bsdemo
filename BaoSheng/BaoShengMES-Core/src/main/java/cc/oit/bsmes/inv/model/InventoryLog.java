package cc.oit.bsmes.inv.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.InventoryLogType;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_INVENTORY_LOG")
public class InventoryLog extends Base{

	private static final long serialVersionUID = 8826989788882673737L;
	private String inventoryId;
    private Double quantity;
    private InventoryLogType type;
    private String orgCode;
    @Transient
    private double beforeQuantity;
    @Transient
    private double afterQuantity;
    @Transient
    private String userName;
}