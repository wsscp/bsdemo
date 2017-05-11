package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_REPORT")
public class Report extends Base implements Serializable {

	private static final long serialVersionUID = 4073168584727177723L;
	
	private String workOrderNo;// 生产单号
	private String serialNum;// 产出条码
	private Double weight;// 重量
	private String status; // 已报工 未报工
	private String reportUserCode; // 报工人
	private Date reportTime; // 报工时间
	private String orgCode;
    private Integer coilNum; //线盘号
    private String color;
    private String useStatus; // 半成品使用状态
    
    @Transient
    public String getStatusText(){
        if(status == null){
            return "";
        }
        return status.toString();
    }

    private Double reportLength;
    private Double goodLength;

    @Transient
    public Double getWasteLength(){
        if(reportLength == null){
            return 0.0;
        }
        return Double.parseDouble(WebConstants.DOUBLE_DF.format(reportLength - goodLength));
    }

    @Transient
    private String reportUserName;
    private String equipCode;
    @Transient
    private String halfProductCode;
    @Transient
    private String productColor;
    @Transient
    private String custProductType;		//客户产品型号
    @Transient
    private String custProductSpec;
    @Transient
    private String productSpec;		//产品规格
    @Transient
    private String productType;   //产品型号
    @Transient
    private String contractNo;
    @Transient
    private String reportEquip;
    @Transient
    private String name;
    @Transient
    private Date onTime;	
    @Transient
    private Date offTime;	
    @Transient
    private String mShift;
    @Transient
    private String morShift;
    @Transient
    private String aftShift;
    @Transient
    private String eveShift;
    @Transient
    private String currentTime;
    @Transient
    private String rMarks;

}
