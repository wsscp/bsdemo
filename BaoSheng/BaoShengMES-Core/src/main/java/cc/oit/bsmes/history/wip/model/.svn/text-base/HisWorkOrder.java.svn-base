package cc.oit.bsmes.history.wip.model;

import java.util.Date;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_WORK_ORDER")
public class HisWorkOrder extends Base {

	private static final long serialVersionUID = -794182168275063469L;

	private String workOrderNo;
	private String equipCode;
	private Date preStartTime;
	private Date preEndTime;
	private Date realStartTime;
	private Date realEndTime;
	private Date auditTime;
	private String auditUserCode;
	private Double orderLength;
	private Double cancelLength;
	private WorkOrderStatus status;
	private String orgCode; // 所属组织
	private String processId; // 工序ID
	private String processCode; // 工序代码
	private String processName; // 工序名称
	private String halfProductCode;
	private Boolean isDelayed;
	private String fixedEquipCode;
	private String equipName;
	private Boolean isDispatch; // 是否急件
	private Boolean isOldLine; // 是否陈线
	private Boolean isAbroad; // 是否出口线
	private MaterialStatus matStatus; //
	private String matIsUsed;

	// 为成缆生产单添加
	private String processGroupRespool; // 上一道生产单单号(用于形成生产单流程)
	private String processGroup;
	private String processesMerged; // 成缆生产单所包含的工序组合情况/JSON
	private String workOrderSection; // 生产单所属工段 1:绝缘工段 2:成缆工段 3:护套工段(用于查看生产单所用)
	private String nextSection; // 生产单下一个加工工段 1:绝缘工段 2:成缆工段 3:护套工段(用于显示工段列表所用)
	private String docMakerUserCode; // 制单人
	private String receiverUserCode; // 接收人
	private Date releaseDate; // 下达日期
	private Date requireFinishDate; // 要求完成日期
	private String userComment; // 备注
	private String specialReqSplit;// 特殊分盘要求
	private String cusOrderItemIds;// 当前生产单中下发的所有客户生产订单明细IDs
	private String completeCusOrderItemIds; // 该生产单已经完成该工段下发的客户生产订单明细IDs：针对NEXT_SECTION=5(2+3)，处于成缆和护套都需要下发该生产单的情况
	private String auditedCusOrderItemIds; // 该生产单已经下发的客户生产订单明细IDs

}
