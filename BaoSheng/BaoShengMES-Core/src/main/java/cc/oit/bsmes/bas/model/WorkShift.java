package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_WORK_SHIFT")
public class WorkShift extends Base{

	private static final long serialVersionUID = 1060335178057630103L;
	private String shiftName;
    private String shiftStartTime;
    private String shiftEndTime;
}