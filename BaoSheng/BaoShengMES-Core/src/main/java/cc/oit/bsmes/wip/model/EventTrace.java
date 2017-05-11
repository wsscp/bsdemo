package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 
 * TODO(事件追溯)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-4 下午3:25:57
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EventTrace extends Base {
	private static final long serialVersionUID = -1198738085345997098L;
	
	private String eventTitle;           	//事件类型
	private String eventContent;            //事件描述
	private String eventReason;             //事件原因
	private String eventResult;             //事件结果
	private String batchNo;                 //批号
	private String eventStatus;				//事件状态
	private String equipCode;             	//加工设备
	private String equipAlias;               //设备别名
	private Date triggerTime;				//触发时间
	private Date respondedTime;				//响应时间
	private Date completedTime;				//完成时间
	private String processName;				//工序名称
	private String processCode;				//工序代码
	private String processTime;				//处理时长
	private String triggerUser;				//触发人
	private String processUser;				//处理人
    private String orgCode;
}
