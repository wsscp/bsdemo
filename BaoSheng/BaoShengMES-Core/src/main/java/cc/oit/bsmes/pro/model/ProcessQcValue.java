package cc.oit.bsmes.pro.model;

import cc.oit.bsmes.common.constants.ProductCheckPhase;
import cc.oit.bsmes.common.constants.QADetectType;
import cc.oit.bsmes.common.model.Base;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PROCESS_QC_VALUE")
public class ProcessQcValue extends Base {

	private static final long serialVersionUID = -1357218314049484536L;
	private String contractNo; // 合同号
	private String salesOrderNo; // 客户销售订单编号
	private String customerOrderNo; // 客户生产单号
	private String custProductType; // 客户产品型号
	private String custProductSpec; // 客户产品规格
	private String productCode; // 产品代码
	private String productType; // 产品型号
	private String productSpec; // 产品规格
	private String craftsId; // 工艺id
	private String processId; // 工序id
	private String processCode; // 工序编号
	private String sampleBarcode; // 样品条码
	private String checkItemCode; // 采集参数
	private String eqipCode; // 生产设备编号
	private String qcValue; // 检测值
	private String qcResult; // 检测结论
	private String checkEqipCode; // 数据检测设备
	private QADetectType type;
	private Integer coilNum; // 线盘号
	private String checkItemName; // 检测项名称
	private String workOrderNo; // 生产单号
	private String processName; // 工序名称
	private String equipName; // 设备名称
	
	@Transient
	@JSONField(serialize = false)
	private ProductCheckPhase checkPhase;// 检验阶段 检验项目
	@Transient
	private String operator; // 经办人
	@Transient
	private String equipAlias; // 生产设备别名
	@Transient
	private String userName; // 操作工人
	@Transient
	private String itemTargetValue; // 参数目标值
	@Transient
	private String checkItemRange; // 参数范围
	@Transient
	private String dataUnit;// 单位
	@Transient
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime; // 查询开始时间
	@Transient
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTime; // 查询结束时间

}