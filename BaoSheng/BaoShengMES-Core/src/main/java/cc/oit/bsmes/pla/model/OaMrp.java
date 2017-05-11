package cc.oit.bsmes.pla.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.constants.UnitType;
import cc.oit.bsmes.common.model.Base;

/**
 * 
 * OA物料需求清单
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-1-17 上午10:49:17
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_MRP_OA")
public class OaMrp extends Base {
	private static final long serialVersionUID = -3479935911510361479L;

	private String contractNo; // 合同号
	private String orderItemId;
	private String matCode; // 物料代码
	private Double quantity; // 数量
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date planDate; // 需求日期
	private MaterialStatus status; // 已审核 未审核 ，已下发
	private UnitType unit; // 单位
	private String processCode;
	private String productCode; // 工序
	private String equipCode; // 添加， 用于存放辅助设备
	private String orgCode; // 所属组织
	private String orderItemProDecId; // 订单明细工序分解ID
	@Transient
	private String[] queryStatus; // 查询状态
	@Transient
	private String matName; // 物料名称（列表显示用）
	@Transient
	private String equipName; // 设备名称（列表显示用）
	@Transient
	private String operator; // 经办人（列表显示用）
	@Transient
	private String custProductType; // 客户产品型号（列表显示用）
	@Transient
	private String productType; // 产品型号（列表显示用）
	@Transient
	private String productSpec; // 产品规格（列表显示用）
	@Transient
	private String customerCompany; // 单位（列表显示用）
}
