package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.constants.DebugType;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_DEBUG")
public class Debug extends Base implements Serializable {

	private static final long serialVersionUID = 4073168584727177723L;
	
	private String workOrderNo;// 生产单号
	private DebugType debugType; // 调试类型
	private Date startTime; // 开始调试时间
	private Date endTime; // 结束调试时间

}
