/**
 * 定时任务信息
 */
package cc.oit.bsmes.job.base.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 
 * 任务日志
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author zhangdongping
 * @date 2013-12-24 下午4:26:17
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "T_JOB_CONFIG")
public class SchedulerSetting extends Base {

	private static final long serialVersionUID = 1322454477015596134L;

	public static String CRON_TYPE = "1";
	public static String SIMPLE_TYPE = "2";

	// id
	private String id;

	// id
	private String schedulerId;

	// 应用war名称
	private String schedulerWar;

	// 任务名称
	private String name;

	// 任务描述
	private String description;

	// 类型：Cron/Simple
	private String type;

	// 是否启用
	private Boolean enable;

	// Cron Express
	private String cronExpress;

	// 开始时间
	private Date simpleStartTime;

	// 结束时间
	private Date simpleEndTime;

	// 重复次数
	private Integer simpleRepeatCount;

	// 重复间隔
	private Integer simpleRepeatIntervel;

    /**
     * 组织
     */
	private String param1;

    /**
     * 处理数据的个数（批处理时用）
     */
	private String param2;

	// 当前任务所处的状态.
	private String currentStatus;

	//所属组织
	private String orgCode;

	@Transient
	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}

	@Transient
	public boolean isCronType() {
		return StringUtils.equals(CRON_TYPE, this.type);
	}

	@Transient
	public boolean isSimpleType() {
		return StringUtils.equals(SIMPLE_TYPE, this.type);
	}

	@Override
	public String toString() {
		return "SchedulerSetting [id=" + id + ", schedulerId=" + schedulerId
				+ ", name=" + name + ", description=" + description + ", type="
				+ type + ", enable=" + enable + ", cronExpress=" + cronExpress
				+ ", param1=" + param1 + ", param2=" + param2
				+ ", simpleEndTime=" + simpleEndTime + ", simpleRepeatCount="
				+ simpleRepeatCount + ", simpleRepeatIntervel="
				+ simpleRepeatIntervel + ", simpleStartTime=" + simpleStartTime
				+ "]" + super.toString();
	}

	public void setMinute(int minute) {
		this.simpleRepeatIntervel = minute * 60 * 1000;
	}

	@Transient
	public int getMinute() {
		return (this.simpleRepeatIntervel == null ? 0
				: this.simpleRepeatIntervel) / (60 * 1000);

	}

	public void setSeconds(int seconds) {
		this.simpleRepeatIntervel = seconds * 1000;
	}

	@Transient
	public long getSeconds() {
		return (this.simpleRepeatIntervel == null ? 0
				: this.simpleRepeatIntervel) / (1000);
	}

}
