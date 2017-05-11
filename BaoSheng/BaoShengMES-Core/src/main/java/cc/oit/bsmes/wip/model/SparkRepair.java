package cc.oit.bsmes.wip.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_Spark_Repair")
public class SparkRepair extends Base implements Serializable {

	private static final long serialVersionUID = 4073168584727177723L;
	
	private String contractNo;// 合同号
	private String workOrderNo; //生产单号
	private String productCode; // 产品代码
	private Double sparkPosition; // 火花位置
	private String equipCode;//设备代码
	private String status; // 修复状态
	private String repairUserName;//修复人
	private String repairType;//修复方式
	private Date repairTime;//修复时间
	
	@Transient
	private Boolean isNotice; // 是否提示
	@Transient
	private String[] statusFindParam; // 处理方式
	
	
}
