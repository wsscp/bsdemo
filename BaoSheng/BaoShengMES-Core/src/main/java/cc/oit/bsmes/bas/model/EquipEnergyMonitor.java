package cc.oit.bsmes.bas.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_EQUIPENERGY")
public class EquipEnergyMonitor extends Base {
	
	private static final long serialVersionUID = -1604192509302500463L;
	
	private String equipName;
	
	private String eleFa;
	
	private String pi;
	
	private String pe;
	
	private String fl;
	
	private String va;
	
	private String dt;
	
	private String equipcode;
	
	private String powAt;
	
	private String sumto;
	
	private String powAa;
	
	private String powAb;
	
	private String powAc;
	
	@Transient
	private String historyStartTime;
	
	@Transient
	private String historyEndTime;
	
	@Transient
	private String efc;

}
