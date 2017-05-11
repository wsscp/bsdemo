package cc.oit.bsmes.interfaceWebClient.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import cc.oit.bsmes.common.constants.FacSummaryType;
import cc.oit.bsmes.common.model.Base;

/**
 * 车间概况
 * @author zhangdongping
 * @version   
 * 2015-5-8 下午2:24:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_RPT_FAC_SUMMARY")
public class FacSummary extends Base {
 
	private static final long serialVersionUID = 6295477416661513526L;
	private FacSummaryType  type;  //数据分类 
	@Transient
	private String typeName;
	private String orgCode;
	private Double typeValue;//数据值
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date reportDate;			 //报表日期
	@Transient
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startTime;			 //查询开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Transient
	private Date endTime;			 //查询结束时间
	
	private String memo;
	
	public String getTypeName()
	{
		if(type==null)
		{
			return "";
		}
		return type.toString().replaceFirst(type.name(), typeValue+"");
	}
	
		 

}
