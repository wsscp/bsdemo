package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_USER_WORK_HOURS")
public class UserWorkHours extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userCode; // 员工工号
	private String userName; // 员工名称
	private String contractNo; // 订单号/合同号
	private String operator; // 经办人
	private String workOrderNo; // 生产单号
	private String equipCode; // 设备编码
	private String equipName; // 设备名称
	private String shiftId; // 班次
	private String shiftName; // 班次名称
	private String processCode; // 工序编码
	private String processName; // 工序名称
	private String productType; // 产品型号
	private String productSpec; // 产品规格
	private String custProductType; // 客户产品型号
	private String custProductSpec; // 客户产品规格
	private Double productWorkHours; // 生产工时
	private Double productSupportHours; // 生产辅助工时
	private Double overtimeHours; // 加班工时
	private Double supportHours; // 辅助工时
	private Double finishedLength; // 长度
	private Double coefficient; // 定额系数
	private String reportDate; // 查询日期
	private String reportType; // 报表类型
	private String orgCode; // 组织机构
	private String reportId; // 报功id
	private String color; // 颜色
	private Double quota; // 定额

}
