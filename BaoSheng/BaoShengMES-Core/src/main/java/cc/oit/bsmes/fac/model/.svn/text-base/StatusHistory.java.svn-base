package cc.oit.bsmes.fac.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.model.Base;

/**
 * 设备状态历史
 * @author leiwei
 * @version   
 * 2013-12-24 下午2:24:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_FAC_STATUS_HISTORY")
public class StatusHistory extends Base {
	
	private static final long serialVersionUID = 6444026246081091984L;
	
	private String  equipId;  //设备编号
	private EquipStatus status; // 设备状态
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;			 //状态开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endTime;			 //状态结束时间
	private String reason;
	@Transient
	private String equipCode;
	@Transient
	private String equipName;
	@Transient
	private String orgCode;
	@Transient
	private String productName;
	@Transient
	private String isCompleted;
	@Transient
	private Long timeBet;
	
	@Transient
	private int reportLength;
}
