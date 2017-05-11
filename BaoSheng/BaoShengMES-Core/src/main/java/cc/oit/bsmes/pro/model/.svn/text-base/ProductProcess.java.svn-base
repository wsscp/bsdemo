package cc.oit.bsmes.pro.model;

import java.util.List;

import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.fac.model.WorkTask;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 产品工艺流程
 * @author leiwei
 * @version   
 * 2013-12-24 下午3:23:47
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PRODUCT_PROCESS")
public class ProductProcess extends Base{
	
	private static final long serialVersionUID = 9021216760745694566L;
	
	public static final String PROCESS_SECTION_JY = "绝缘";
	public static final String PROCESS_SECTION_CL = "成缆";
	public static final String PROCESS_SECTION_HT = "护套";
	
	private String processCode; 				//加工工序代码
	private String processName;
	private Integer  seq; // 加工顺序
	private String productCraftsId;			    //产品工艺ID
	private Double processTime;					//加工时间
	private Integer setUpTime;					//前置时间
	private Integer shutDownTime;				//后置时间
	private String fullPath;                     //工序全路径 工序id;工序id
	private String nextProcessId;               //下一工序ID
    @Transient
	private String craftsVersion;				//工艺版本号
	private Boolean sameProductLine;            //是否与上一道工序同一生产线
	private Boolean isOption;                   // 是否可选
	private Boolean isDefaultSkip;                   // 是否默认跳过
	@Transient
	private String oldProcessId;
    @Transient
    @JSONField(serialize=false)
	private List<ProcessInOut> processInOutList; //工序投入及产出
    @Transient
    @JSONField(serialize=false)
	private List<WorkTask> workTask;  //  设备计划加工任务负载

    @Transient
    public ProcessInOut getProcessOut() {
        ProcessInOut processInOut = null;
        List<ProcessInOut> processInOutList = StaticDataCache.getByProcessId(this.getId());
		if (null != processInOutList) {
			for (ProcessInOut _processInOut : processInOutList) {
				if (_processInOut.getInOrOut() == InOrOut.OUT) {
					processInOut = _processInOut;
					break;
				}
			}
		}
        return processInOut;
    }

    @Transient
    public boolean isSkippable() {
        return isOption && isDefaultSkip;
    }

    @Transient
    private Double quantity;
	@Transient
	@OrderColumn
    private String  craftsName;
	@Transient
    private String  nextProcessName;
    @Transient
    private String outColor;
    
    // 是否跳过文本转换
    public String getIsDefaultSkipText(){
        if(isDefaultSkip == null){
            return "";
        }else if(isDefaultSkip){
            return "是";
        }else{
            return "否";
        }
    }
    // 是否可选转换
    public String getIsOptionText(){
        if(isOption == null){
            return "";
        }else if(isOption){
            return "是";
        }else{
            return "否";
        }
    }
    // 是否与上一道工序同一生产线文本转换
    public String getSameProductLineText(){
        if(sameProductLine == null){
            return "";
        }else if(sameProductLine){
            return "是";
        }else{
            return "否";
        }
    }

	@Transient
	private List<ProcessQc> processQcs; // 质量参数
	@Transient
	private List<ProcessReceipt> processReceipts; // 工艺参数

	@Transient
	private String equipCodeArray; // 可选设备编码列表
	@Transient
	private String equipNameArray; // 可选设备编码列表
	@Transient
	private String orderItemId; // 订单产品ID
	

}
