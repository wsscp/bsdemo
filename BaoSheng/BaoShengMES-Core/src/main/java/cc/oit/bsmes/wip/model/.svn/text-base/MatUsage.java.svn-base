package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_MAT_USAGE")
public class MatUsage extends Base{

	private static final long serialVersionUID = 7156931186834695628L;

	private String equipCode;
	private String shiftName;
	private String dbUserCode;
	private String dbUserName;
	private String fdbUserCode;
	private String fdbUserName;
	private String fzgUserCode;
	private String fzgUserName;
	private String workOrderNo;
	private Date turnoverMatDate;
	
	@Transient
	private String matUsageId;
	@Transient
	private String matName;
	@Transient
	private String matCode;
	@Transient
	private String sbjl;
	@Transient
	private String bbll;
	@Transient
	private String bbtl;
	@Transient
	private String bbjl;
	
	
}