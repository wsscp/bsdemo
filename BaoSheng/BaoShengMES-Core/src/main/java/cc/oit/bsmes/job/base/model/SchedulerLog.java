/**
 * 定时任务信息
 */
package cc.oit.bsmes.job.base.model;


import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;
/**
 * 
 * 任务日志
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2013-12-24 下午4:26:17
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_JOB_LOG")
public class SchedulerLog extends Base {

	private static final long serialVersionUID = 8429755281229306640L;

	//id
	private String id;
	
	//与任务相关的ID
	private String schedulerId;
	
	//任务名称
	private String jobName;
	
	//任务介绍
	private String jobDesc;
	
	//服务器主机名
	private String hostName;
	
	//服务器地址
	private String hostAddress;
	
	//是否正在运行
	private Boolean flag;
	
	//上次开始运行时间
	private Date prevStartTime;
	
	//上次任务执行结果
	private String prevResult;
	
	//上次截止运行时间
	private Date prevEndTime;
	
	private String orgCode;
	
	private String errorMessage;
	
	 
	
}
