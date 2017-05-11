package cc.oit.bsmes.eve.model;


import javax.persistence.Table;

import cc.oit.bsmes.common.model.Base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * 
 * 事件触发日志
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2014-2-21 下午4:28:00
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_EVE_TRIGGER_LOG")
public class EventTriggerLog extends Base{ 
	private static final long serialVersionUID = -1315322984358266070L;
	private String eventId;  // 事件ID
	private String processId;  //类型名称
	private String processContent;  //触发内容描述
	private String orgCode;//数据所属组织
	
}
