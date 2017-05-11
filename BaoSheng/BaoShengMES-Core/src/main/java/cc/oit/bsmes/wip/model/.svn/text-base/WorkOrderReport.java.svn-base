package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_WORK_ORDER_REPORT")
public class WorkOrderReport extends Base{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5247824624776041454L;
	
	// -------公共信息------
	private String contractNo; // 订单号/合同号
	private String operator; // 经办人
	private String equipName; // 设备名称
	private String workOrderNo; // 生产单号
	private String equipCode; // 机床
	private String shiftId; // 班次
	private String shiftName; // 班次名称
	private String productType; // 型号
	private String productSpec; // 规格
	private Double workHours; // 工时
	private String dbWorker; // 挡班
	private String fdbWorker; // 附挡班
	private String fzgWorker; // 辅助工
	private String reportDate; // 统计日期
	private String reportType; // 报表种类
	private String orgCode; // 直属机构编号
	
	// {色别或字码, 挤塑厚度/min, 挤塑厚度/max, 挤塑前外径/min, 挤塑前外径/max, 挤塑后外径/min, 挤塑后外径/max, 制造长度,  检验, 质量, 种类, 上班盘存, 本班领用, 本班退用}
	private String colorOrWord; // 色别或字码
	private Integer jsThicknessMin; // 挤塑厚度/min
	private Integer jsThicknessMax; // 挤塑厚度/max
	private Integer jsFrontOuterdiameterMin; // 挤塑前外径/min
	private Integer jsFrontOuterdiameterMax; // 挤塑前外径/max
	private Integer jsBackOuterdiameterMin; // 挤塑后外径/min
	private Integer jsBackOuterdiameterMax; // 挤塑后外径/max
	
	// {色别或字码, 试验电压, 击穿次数, 线速度m/s, 计划长度, 实际长度, 检验, 质量}
	private Integer testVoltage; // 试验电压
	private Integer punctureNum; // 击穿次数
	private Integer lineSpeed; // 线速度m/s
	
	// {外层方向, 包带方向, 铜丝覆盖率或铜带重叠率, 计划长度, 实际长度, 检验, 质量, 种类, 上班盘存, 本班领用, 本班退用}
	private String outerPosition; // 外层方向
	private String beltPosition; // 包带方向
	private Integer cuCoverLevel; // 铜丝覆盖率或铜带重叠率
	private Integer planLength; // 计划长度
	private Integer realLength; // 实际长度
	private String testing; // 检验
	private String quality; // 质量
	private String kind; // 种类
	private String preLeave; // 上班盘存
	private String thisTake; // 本班领用
	private String thisBack; // 本班退用
	
	// {成缆外径mm/上, 成缆外径mm/下, 屏蔽外径mm/上, 屏蔽外径mm/下, 节距, 屏蔽材料, 绕包质量, 搭盖率%, 产品自检, 长度}
	private Integer clOuterdiameterUp; // 成缆外径mm/上
	private Integer clOuterdiameterDown; // 成缆外径mm/下
	private Integer pbOuterdiameterUp; // 屏蔽外径mm/上
	private Integer pbOuterdiameterDown; // 屏蔽外径mm/下
	private Integer piceRange; // 节距
	private String pbMat; // 屏蔽材料
	private String rbQuality; // 绕包质量
	private Integer coverLevel; // 搭盖率%
	private String ownerTesting; // 产品自检
	private Integer finishedLength; // 长度 
	
	
}
