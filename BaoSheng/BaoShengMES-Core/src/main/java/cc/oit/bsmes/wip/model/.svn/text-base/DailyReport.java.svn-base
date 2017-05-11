package cc.oit.bsmes.wip.model;

import java.io.Serializable;

import javax.persistence.Transient;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class DailyReport extends Base implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3121891107136645155L;
	private String equipAlias;
	private String shiftName;
	private String userName;
	private String code;
	private String shiftId;
	
	@Transient
	private String userCode;
	@Transient
	private String onTime;
	@Transient
	private String offTime;
	@Transient
	private String remark;
	@Transient
	private String shutDownReason;

}
