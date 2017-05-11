package cc.oit.bsmes.interfaceWebClient.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.model.Base;

/**
 * 设备状态统计
 * @author zhangdongping
 * @version   
 * 2015-5-8 下午2:24:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_RPT_EQUIP_SUMMARY")
public class EquipSummary extends Base {
 
	 
	 
	private static final long serialVersionUID = -781304475330277203L;
	private EquipStatus  type;  //状态类型  
	private String orgCode;
	private Double times;//持续时间
	@Transient
	private double total;// 
	@Transient
	private double used;//  
	@Transient
	private Double oee;//设备OEE
	@Transient
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;			 //查询开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Transient
	private Date endTime;			 //查询结束时间
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date reportDate;			 //报表日期
	
	
 

}
