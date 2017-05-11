package cc.oit.bsmes.pla.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * Created by JIN on 2015/6/30.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_ORDER_PROCESS_PR")
public class OrderProcessPR extends Base {

	private String salesOrderItemId; // 订单明细ID
	private String processId; // 工序ID
	private String type; // 参数类型
	private String code; // 参数代码
	private String name; // 参数名称
	private String targetValue; // 原始值
	private String newValue; // 新值
	private String matCode; // 物料代码
	private String matName; // 物料名称
	private String inOrOut; // 投入还是产出
	@Transient
	private String oldDesc; // 旧投入料描述
	@Transient
	private String newDesc; // 新投入料描述
	@Transient
	private String newMatName; // 新投入料名称

}
