package cc.oit.bsmes.wip.model;

import java.util.Date;

import javax.persistence.Table;

import cc.oit.bsmes.common.constants.OrderPushStatus;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_FINISHED_ORDER_ITEM")
public class FinishOrderItem extends Base  {
	private static final long serialVersionUID = 8873168584727177723L;
	
	private String salesOrderItemId;   //订单明细ID
	private String processCode;        //工序编码
	private OrderPushStatus pushStatus;         //推送状态
	private String pushMessage;        //推送信息
	private int    pushNum;            //推送次数
	private Date   pushTime;           //推送时间
	private String jjrid;    //交接人ID
	private String jjr;      //交接人
	private Double jjsl;     //交接数量
	private String jldw;     //计量单位
	private String zjbj;     //质检标记


}