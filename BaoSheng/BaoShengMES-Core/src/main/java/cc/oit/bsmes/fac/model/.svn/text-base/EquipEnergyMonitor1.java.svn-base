package cc.oit.bsmes.fac.model;

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
public class EquipEnergyMonitor1  extends Base{
	
		private static final long serialVersionUID = -1604192509332507462L;
		
		private String ID;
		
		private String equipName;
		
		private String equipCode;
		private String PI;
		private String PE;
		private String FL;
		private String VA;
		@Transient
		private String name;
		@Transient
		private String energyModifyTime;
		@Transient
		private int mshiftEnergy;
		@Transient
		private int ashiftEnergy;
		@Transient
		private int eshiftEnergy;
		@Transient
		private String eleFa;
		@Transient
		private String mShift;
		@Transient
		private String aShift;
		@Transient
		private String reMarks;
		
}
