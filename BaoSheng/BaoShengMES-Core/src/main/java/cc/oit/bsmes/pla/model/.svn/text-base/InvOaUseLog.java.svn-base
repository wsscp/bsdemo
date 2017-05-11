package cc.oit.bsmes.pla.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.StockUseLogStatus;
import cc.oit.bsmes.common.model.Base;
import javax.persistence.Table;
import javax.persistence.Transient;

import cc.oit.bsmes.inv.model.InventoryDetail;

/**
 * 
 * 库存冲抵日志
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2013-12-24 下午3:35:14
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_INV_OA_USE_LOG")
public class InvOaUseLog extends Base{
	
	private static final long serialVersionUID = -7940281713067756932L;

	private String matCode;
    private String inventoryDetailId;
    private String matBatchNo;
    private Double usedStockLength;
    /**
     * status 未使用 已使用 已取消
     */
    private StockUseLogStatus status;
    private String isProduct;
    private String refId;
    @Transient
    private InventoryDetail inventoryDetail;
    
}