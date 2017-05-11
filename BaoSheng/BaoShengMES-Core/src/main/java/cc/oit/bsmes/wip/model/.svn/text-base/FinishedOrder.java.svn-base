package cc.oit.bsmes.wip.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.OrderPushStatus;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_FINISHED_ORDER_ITEM")
public class FinishedOrder extends Base implements Serializable {

	private static final long serialVersionUID = -6899526630182018054L;
	
	private String salesOrderItemId;
	private String processCode;
	private OrderPushStatus pushStatus;
	private String pushMessage;
	private int pushNum;
	private String jjrid;
	private String jjr;
	private Double jjsl;
	private String jldw;
	private Date pushTime;

}
