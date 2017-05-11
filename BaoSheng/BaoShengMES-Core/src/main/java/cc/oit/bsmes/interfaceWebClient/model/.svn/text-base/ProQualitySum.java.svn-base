package cc.oit.bsmes.interfaceWebClient.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * 工序质量问题发生统计
 * @author zhangdongping
 * @version   
 * 2015-5-8 下午2:24:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_RPT_PRO_QUALITY_SUM")
public class ProQualitySum extends Base { 
	 
	private static final long serialVersionUID = -781304475330277203L;
	private String  type;  //数据分类 
	private String  typeName;  //数据分类 
	private String processCode;//工序代码
	private String processName;//工序名称
	private String orgCode;
	private Integer times;//次数 
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date reportDate;			 //报表日期
	@Transient
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;			 //查询开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Transient
	private Date endTime;			 //查询结束时间
}
