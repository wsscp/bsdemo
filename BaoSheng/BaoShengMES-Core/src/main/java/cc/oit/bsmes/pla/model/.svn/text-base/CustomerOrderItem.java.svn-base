package cc.oit.bsmes.pla.model;

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

/**
 * 客户订单明细。
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_CUSTOMER_ORDER_ITEM")
public class CustomerOrderItem extends Base {

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
	private Date customerOaDate; // 客户制定交货期
	private Date subOaDate;
	private Date lastOa; // 上次oa时间
	private Boolean useStock;
	private Boolean isFirstTime;
	private Boolean finishJy = false;  // 绝缘工段全部下发
	private Double finishedProduct;
	
	private String specialFlag; // 订单特殊状态[0:厂外计划;1:计划已报;2:手工单;3:库存生产]
	private Date specialOperateTime; // 订单特殊状态操作时间
	private Double usedStockLength;
	private String craftsId;
	@Transient
	private String conductorStruct;
	@Transient
	private String oiCraftsId;// 导体结构
	@Transient
	private String unreleasedLength;
	@Transient
	private String unreleasedLengthHidden;
	@Transient
	private String finishedPercent;
	@Transient
	@JSONField(serialize = false)
	private List<CustomerOrderItemDec> cusOrderItemDesc;
	@Transient
	@JSONField(serialize = false)
	private CustomerOrder customerOrder;
	@Transient
	private SalesOrderItem salesOrderItem;
	@Transient
	private String customerCompany; // 客户名称
	@Transient
	private String contractNo;
	@Transient
	private String productName;
	@Transient
	private String orderFileNum;
	@Transient
	private String operator; // 销售员
	@Transient
	private String craftsCname; // 工艺别名
	@Transient
	private String productType;
	@Transient
	private String productSpec;
	@Transient
	private String custProductType;
	@Transient
	private String custProductSpec;
	@Transient
	private String wiresStructure;
	@Transient
	private String section;
	@Transient
	private Date oaDate;
	@Transient
	private String customerOrderItemId; 
	@Transient
	private String numberOfWires; // 线芯数
	@Transient
	private String wireCoilCount; // 分盘数
	@Transient
	private String isTempSave; // 临时保存

}
