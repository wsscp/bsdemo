package cc.oit.bsmes.pla.model;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.model.Base;
import javax.persistence.Transient;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 将订单明细分解成卷，每卷定义长度，有利于平衡产能，加快生产
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2013-12-24 下午3:05:52
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_CUSTOMER_ORDER_ITEM_DEC")
public class CustomerOrderItemDec extends Base{
	
	private static final long serialVersionUID = 1460603803851857916L;
	
	private String orderItemId;
	private Double length;
	private Boolean useStock;
	private ProductOrderStatus status;
	@Transient
	@JSONField(serialize=false)
	private List<CustomerOrderItemProDec> cusOrderItemProDesList;
	@Transient
	@JSONField(serialize=false)
	private List<CustomerOrderItemProDecOA> cusOrderItemProDesOAList; // 获取明细，计算OA的工序明细分解表
}