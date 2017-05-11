package cc.oit.bsmes.eve.model;

import java.util.Date;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import cc.oit.bsmes.common.model.Base;


@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_EVE_LAST_EXECUTE_TIME_RECORD")
public class LastExecuteTimeRecord extends Base{
	
	private static final long serialVersionUID = -6863790674842256321L;
	private Date lastExecuteTime;
	private Integer millisec;
	private String type;
}
