package cc.oit.bsmes.common.log.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 *
 * @author CHEN XIANG
 * @date 2016-4-21
 * @since
 * @version
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_TEMP_SALES_ORDER_ITEM")
public class SaleLog extends  Base {
 	private static final long serialVersionUID = 560643451498010229L;
 	
	private String contractNo;
	private String custProductSpec;
	private String custProductType;
	private String customerOaDate;
	private String operateType;
	private String orgCode;
	private String processRequire;
	private String productCode;
	private String productSpec;
	private String saleorderLength;
	private String salesOrderId;
	private String itemId;
	private String contractAmount;

}