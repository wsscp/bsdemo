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
@Table(name = "T_BAS_MONTH_EQUIPENERGY")
public class EquipEnergyMonthMonitor extends Base {
	
	private static final long serialVersionUID = -1604192509302500462L;
	
	private String id;
	
	private String equipName;
	
	private String pi;
	
	private String pe;
	
	private String fl;
	
	private String va;
	
	private String equipcode;
	
	private String sumto;
	
	private String maxPower;
	
	private String minPower;
	
	private String avgPower;
	
	private String difr;
	
	private String avgr;
	
	@Transient
	private String efc;
}
