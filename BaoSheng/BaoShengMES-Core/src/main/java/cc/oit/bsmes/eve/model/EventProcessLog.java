package cc.oit.bsmes.eve.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.EventStatus;
import cc.oit.bsmes.common.model.Base;

/**
 * 
 * 事件处理日志
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-6-23 下午2:20:16
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_EVE_EVENT_PROCESS_LOG")
public class EventProcessLog extends Base {
	private static final long serialVersionUID = -124336517069870077L;
	
	private String eventInfoId;
	private String orgCode;
	private EventStatus type;
}
