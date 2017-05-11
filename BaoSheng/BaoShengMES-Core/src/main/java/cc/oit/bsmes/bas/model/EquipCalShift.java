package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_EQIP_CAL_SHIFT")
public class EquipCalShift extends Base implements Comparable<EquipCalShift> {
	private static final long serialVersionUID = -4003443619419280442L;
	private String workShiftId;          //班次ID
	@Column(name="EQIP_CALENDAR_ID")
	private String equipCalendarId;      //设备工作日历ID

    @Transient
	private String dateOfWork;	   		 //工作日，如20130102
    @Transient
	private String equipCode;
    @Transient
	private String name;
    @Transient
	private String orgCode;
    @Transient
	private String shiftName; 		     //班次名称
    @Transient
	private String shiftStartTime;		 //班次开始时间
    @Transient
	private String shiftEndTime;		 //班次结束时间

    @Override
    public int compareTo(EquipCalShift o) {
        if (shiftStartTime == null) {
            return -1;
        }
        if (o.getShiftStartTime() == null) {
            return 1;
        }
        return shiftStartTime.compareTo(o.getShiftStartTime());
    }
}
