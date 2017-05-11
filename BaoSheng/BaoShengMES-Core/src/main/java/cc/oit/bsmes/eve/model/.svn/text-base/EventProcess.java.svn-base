package cc.oit.bsmes.eve.model;

import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.ProcessType;
import cc.oit.bsmes.common.model.Base;

/**
 * 
 *事件处理流程定义
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-10 上午11:26:45
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_EVE_EVENT_PROCESS")
public class EventProcess extends Base {
	private static final long serialVersionUID = -2004333265920015250L;
	
	private String eventTypeId;      //事件类型ID 
	private int processSeq;			 //事件处理步骤
	private ProcessType processType;      //事件处理方式
	private double stepInterval;     //本步骤与上步骤间隔时间(分钟)
	private Boolean status;           //0(无效) 1(有效）
	@Transient
	private List<EventProcesser> eventProcesser;
}
