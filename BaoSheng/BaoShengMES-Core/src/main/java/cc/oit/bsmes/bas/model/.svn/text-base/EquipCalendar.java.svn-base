package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.List;

/**
 * 设备工作日历
 * 
 * @author leiwei
 * @version 2013-12-31 下午5:03:48
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_EQIP_CALENDAR")
public class EquipCalendar extends Base {
	private static final long serialVersionUID = 872199959425358397L;

	private String equipCode; // 设备
	private String dateOfWork; // 工作日，如20130102
	@Transient
	private List<EquipCalShift> equipCalShift; // 设备班次
	private String orgCode;

	@Transient
	private String shiftStartTime; // 班次开始时间
	@Transient
	private String shiftEndTime; // 班次结束时间

}
