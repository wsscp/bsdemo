package cc.oit.bsmes.fac.model;


import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.util.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 
 * 设备维修统计
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-6-23 下午4:43:48
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class EquipMaintenance  extends Base{
	private static final long serialVersionUID = -7278989738012547363L;
	
	private String equipCode;
	private String equipName;
	private String eventContent;
	@DateTimeFormat(pattern= DateUtils.DATE_TIME_FORMAT)
	private Date createTime;     //事件创建时间
	private String responsed;
	@DateTimeFormat(pattern=DateUtils.DATE_TIME_FORMAT)
	private Date responseTime;    //响应时间
	private Long responseTimes;   //响应时长（分钟）
	private String complete;      //完成人
	@DateTimeFormat(pattern=DateUtils.DATE_TIME_FORMAT)
	private Date  completeTime;   //完成时间
	private Long  completeTimes;  //完成时长
	private String status;
	@Transient
	private String id;
	@Transient
	private String userName;
	@Transient
	private String equipModelStandard;
}
