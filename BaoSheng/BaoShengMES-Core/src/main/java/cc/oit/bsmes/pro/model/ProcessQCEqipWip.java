package cc.oit.bsmes.pro.model;

import javax.persistence.Table;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PROCESS_QC_EQIP_WIP")
public class ProcessQCEqipWip extends ProcessQCEqip{


	private static final long serialVersionUID = -796165571797252756L;
	private String qcId;
	private String equipId;
	private String equipCode; // PLM设备编码
	private String acEquipCode; // 真实的设备编码/采集系统设备代码

}
