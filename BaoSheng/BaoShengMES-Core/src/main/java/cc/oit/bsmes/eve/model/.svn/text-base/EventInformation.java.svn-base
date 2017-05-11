package cc.oit.bsmes.eve.model;

import java.util.Date;

import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.util.DateUtils;

/**
 * 
 * 异常事件信息
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-2-10 上午11:11:32
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_EVE_EVENT_INFO")
public class EventInformation extends Base {
	private static final long serialVersionUID = 9171689218259402472L;
	
	
	private String maintainPeople;//维修班负责人
	private String eventTitle; // 事件标题
	private String eventContent; // 事件内容
	private String eventTypeId; // 事件类型ID
	private EventStatus eventStatus; // 事件状态
	private Date processTriggerTime;// 事件升级触发时间
	private String eventReason; // 事件原因
	private String eventResult; // 事件结果
	private String processId; // 工序ID
	private String batchNo; // 批次号
	private String equipCode; // 设备CODE
	private String orgCode;// 数据所属组织
	private String eventProcessId;// 事件处理流程ID
	private Boolean pendingProcessing; // 是否拦截在制品
	private String productCode;
	private String status;
	private String evaluate;
	private String eqipEventCode;
	// -----添加字段
	@Transient
	private String code; // 类型代码
	@Transient
	private String equipAlias; // 类型代码
	@Transient
	@OrderColumn
	private String name; // 类型名称
	@Transient
	@OrderColumn
	private String processType; // 处理方式
	
	@Transient
	@OrderColumn
	private int processSeq; // 处理方式
	
	@Transient
	private String[] eventStatusFindParam; // 处理方式
	@Transient
	@OrderColumn
	@DateTimeFormat(pattern = DateUtils.DATE_TIME_FORMAT)
	private Date responseTime; // 响应时间
	@Transient
	@OrderColumn
	@DateTimeFormat(pattern = DateUtils.DATE_TIME_FORMAT)
	private Date completeTime; // 完成时间
	// ---------------
	@DateTimeFormat(pattern = DateUtils.DATE_TIME_FORMAT)
	private Date createTime;
	@Transient
	@OrderColumn
	private String responsible;
	@Transient
	private String userCode;
	@Transient
	private String eventProcessor; // 事件处理人
	@Transient
	private String userName;
	@Transient
	private String equipModelStandard;

}
