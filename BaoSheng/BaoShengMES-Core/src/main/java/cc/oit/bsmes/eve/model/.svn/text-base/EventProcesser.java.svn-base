package cc.oit.bsmes.eve.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.EventProcesserType;
import cc.oit.bsmes.common.model.Base;

/**
 * 
 * 事件关联处理人
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-13 下午2:52:16
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_EVE_EVENT_PROCESSER")
public class EventProcesser extends Base {
	private static final long serialVersionUID = -2322237790017699577L;
	
	private String processer;      //事件处理人 
	private EventProcesserType type;			 //处理人类型 
	private String eventProcessId;      //事件处理流程ID 
	private String eventTypeId;
	@Transient
	@Column(name = "NAME")
	private String name;
}
