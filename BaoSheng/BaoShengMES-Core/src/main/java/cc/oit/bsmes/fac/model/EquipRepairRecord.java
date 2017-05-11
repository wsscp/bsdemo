package cc.oit.bsmes.fac.model;

import cc.oit.bsmes.common.model.Base;


import cc.oit.bsmes.common.util.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;





import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.*;

/**
 * 设备信息
 * 
 * @author leiwei
 * @version 2013-12-24 下午2:23:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_EVE_EQUIP_REPAIR_RECORD")
public class EquipRepairRecord extends Base {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String eventInfoId; // 事件ID
	@JSONField(format=DateUtils.DATE_TIME_FORMAT)
	private Date startRepairTime; // 开始维修时间
	@JSONField(format=DateUtils.DATE_TIME_FORMAT)
	private Date finishRepairTime; // 维修完成时间
	private String repairMan; // 维修人
	private String failureModel; // 设备类型
	private String equipTroubleDescribetion; //设备故障描述
	private String equipTroubleAnalyze;//设备故障分析
	private String repairMeasures;// 维修措施
	private int seq;
	
	@Transient
	private String equipName;
	@Transient
	private String equipAlias;
	@Transient
	private String equipCode;
	@Transient
	private String userName;
	@Transient
	private String equipModelStandard;
	@Transient
	private String protectMan;
	@Transient
	private String evaluate;

	
}
