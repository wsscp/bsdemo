package cc.oit.bsmes.wip.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * WorkCusorderRelation
 * <p>
 * 生产单与订单产品的关系表
 * </p>
 * 
 * @author DingXintao
 * @date 2015年09月14日 下午1:34:34
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_WO_CUSORDER_RELATION")
public class WorkCusorderRelation extends Base {
	private static final long serialVersionUID = -3952140555624141578L;
	private String workOrderId;
	private String cusOrderItemId;
	private String workOrderNo;
	private String processCode;
	private String orderTaskLength; // 该订单产品该生产单所下发的长度
	private String splitLengthRole; // 手动填写分盘要求：1500+1000*2+500*3
}
