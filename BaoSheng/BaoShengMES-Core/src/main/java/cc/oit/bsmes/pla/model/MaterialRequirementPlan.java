package cc.oit.bsmes.pla.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.constants.UnitType;
import cc.oit.bsmes.common.model.Base;

/**
 * 
 * 物料需求清单
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-1-17 上午10:49:17
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_MRP")
public class MaterialRequirementPlan extends Base {
	private static final long serialVersionUID = -3725276471720625061L;

	private String workOrderId; // 生产单ID
	private String demandQuantity; // 物料用量
	private String matCode; // 物料代码
	private Double quantity; // 数量
	private String equipName; // 设备名称
	private String userCode; // 操作人
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date planDate; // 需求日期
	private MaterialStatus status; // 已审核 未审核 ，已下发
	@Transient
	private String statusCode;
	@Transient
	private String statusName;
	private String unFinishedLength; // 未完成长度 、加工长度
	private UnitType unit; // 单位
	private String processCode;
	private String processId;
	// 工序
	// @Transient
	private String equipCode; // 添加， 用于存放辅助设备
	private String orgCode; // 所属组织
	private String color; // 颜色
	private String inAttrDesc; // 投入JSON描述字符串
	private String matName; // 物料名称
	@Transient
	private String processName; // 工序名称
	@Transient
	private String matSpec; // 规格
	@Transient
	private String matSize; // 大小
	@Transient
	private String contractNo; // 大小
	@Transient
	private String productCode; // 大小
	@Transient
	private String workOrderNo;
	@Transient
	private String planAmount;
	@Transient
	private String weight;
	@Transient
	private MatType matType; // 原材料 半成品 成品

	@Transient
	public String getStatusName() {
		if (status == null) {
			return "";
		}
		return status.toString();
	}
}
