package cc.oit.bsmes.pro.model;

import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * 标准产品工艺流程
 * 
 * @author 陈翔
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PRODUCT_PROCESS_BZ")
public class ProductProcessBz extends Base {

	private static final long serialVersionUID = 1349993604L;

	private String processCode; // 加工工序代码
	private String nextProcessId; // 下一工序ID
	private String processName; // 加工工序名称
	private Boolean isDefaultSkip; // 是否默认跳过
	private Boolean sameProductLine; // 是否与上一道工序同一生产线
	private Integer seq; // 加工顺序
	private Integer setUpTime; // 前置时间
	private String fullPath; // 工序全路径
	private Boolean isOption; // 是否可选
	private Integer shutDownTime; // 后置时间
	private Double processTime; // 加工时间
	private String productCraftsId; // 产品工艺ID
	@Transient
	private String equipCodeArray; // 可选设备编码列表
	@Transient
	private String equipNameArray; // 可选设备编码列表
	@Transient
	private String nextProcessName; // 下一工序名称

	/**
	 * 获取编辑的设备编码数组
	 * */
	// public List<String> getEquipCodeArray() {
	// if (StringUtils.isNotEmpty(equipCodeArray)) {
	// return JSON.parseArray(equipCodeArray, String.class);
	// } else {
	// return null;
	// }
	// }

	@Transient
	@OrderColumn
	private String craftsName;

	// 是否跳过文本转换
	public String getIsDefaultSkipText() {
		if (isDefaultSkip == null) {
			return "";
		} else if (isDefaultSkip) {
			return "是";
		} else {
			return "否";
		}
	}

	// 是否可选转换
	public String getIsOptionText() {
		if (isOption == null) {
			return "";
		} else if (isOption) {
			return "是";
		} else {
			return "否";
		}
	}

	// 是否与上一道工序同一生产线文本转换
	public String getSameProductLineText() {
		if (sameProductLine == null) {
			return "";
		} else if (sameProductLine) {
			return "是";
		} else {
			return "否";
		}
	}

}