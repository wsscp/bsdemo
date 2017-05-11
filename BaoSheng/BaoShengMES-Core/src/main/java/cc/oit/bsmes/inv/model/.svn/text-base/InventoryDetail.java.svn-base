package cc.oit.bsmes.inv.model;

import cc.oit.bsmes.common.constants.InventoryDetailStatus;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.wip.dto.SectionLength;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_INVENTORY_DETAIL")
public class InventoryDetail extends Base implements SectionLength {
	
	private static final long serialVersionUID = 6145562368338150526L;

	private String inventoryId;
    private Double length;
    private Integer seq;
    private InventoryDetailStatus status;
    @Transient
    private Inventory inventory;
    @Transient
    private String refId;
}