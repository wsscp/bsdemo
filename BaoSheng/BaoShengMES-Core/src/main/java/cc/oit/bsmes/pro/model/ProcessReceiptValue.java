package cc.oit.bsmes.pro.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PROCESS_RECEIPT_VALUE")
public class ProcessReceiptValue extends Base{

	private static final long serialVersionUID = -1390197987464890466L;
	private String contractNo; // 合同号
	private String salesOrderNo; // 客户销售订单编号
	private String customerOrderNo; // 客户生产单号
	private String custProductType; // 客户产品型号
	private String custProductSpec; // 客户产品规格
	private String productCode; // 产品代码	
	private String productType; // 产品规格
	private String productSpec; // 产品型号
	private String workOrderNo; // 生产单号
	private String craftsId; // 工艺id
	private String processId; // 工序id
	private String processCode; // 工序编号
	private String sampleBarcode; // 样品条码
	private String receiptCode; // 采集参数
	private String eqipCode; // 生产设备编号
	private String receiptValue; // 检测值
	private String qcResult; // 检测结论
	private String checkEqipCode; // 数据检测设备
	
}