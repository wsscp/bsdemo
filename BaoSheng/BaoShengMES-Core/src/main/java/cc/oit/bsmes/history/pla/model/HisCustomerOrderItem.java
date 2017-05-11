package cc.oit.bsmes.history.pla.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.ord.model.SalesOrderItem;

import com.alibaba.fastjson.annotation.JSONField;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_CUSTOMER_ORDER_ITEM")
public class HisCustomerOrderItem extends Base {

	private static final long serialVersionUID = -9055164650290350622L;

	private String customerOrderId;
	private String salesOrderItemId;
	private Double orderLength;
	private Double contractLength;
	private Date releaseDate;
	private Date productDate;
	private String productCode;
	private ProductOrderStatus status;
	private Date planStartDate;// 计划开始时间
	private Date planFinishDate;// 计划完成时间
	private String remarks;
	private Date subOaDate;
	private Date lastOa; // 上次oa时间
	private Boolean useStock;
	private Boolean isFirstTime;
	private Boolean finishJy = false;  // 绝缘工段全部下发
	
	private String specialFlag; // 0:厂外计划;1:计划已报
	private Double usedStockLength;
	private String craftsId;

}
