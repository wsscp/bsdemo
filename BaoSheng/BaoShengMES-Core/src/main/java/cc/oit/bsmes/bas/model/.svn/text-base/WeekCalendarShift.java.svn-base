package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_WEEK_CALENDAR_SHIFT")
public class WeekCalendarShift extends Base{

	private static final long serialVersionUID = 7444439536377976070L;
	private String weekCalendarId;
    private String workShiftId;
    private String status;
    private String orgCode;
    //添加字段 
    private String shiftName; //班次名称
    private String shiftStartTime;
    private String shiftEndTime;
}