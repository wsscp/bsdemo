package cc.oit.bsmes.pro.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.constants.UnitType;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.inv.model.Mat;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PROCESS_IN_OUT_WIP")
public class ProcessInOutWip extends Base{

	
	private static final long serialVersionUID = -294041171664401193L;
	private String productProcessId;
    private String matCode;
    private InOrOut inOrOut;
    private Double quantity;
    private String quantityFormula;
    private UnitType unit;
    private String useMethod;
	private String modifyRemarks;       //字段修改备注
//    @Transient
//    @JSONField(serialize = false)
//    private Mat mat;

    @Transient
    private String matName;
    @Transient
    private String imagePath;
    @Transient
    private String inAttrDesc;
    @Transient
    private Boolean hasPutIn;

    @Transient
    private String size;
    @Transient
    private String matSpec;
//    @Transient
//    private String inattrDesc;
    @Transient
    private String matSection;
    @Transient
    private String color;
    @Transient
    private String planAmount;
    @Transient
    private String batchNo;
    @Transient
    private Date putTime;
//    @Transient
//    private int rowSpanSize;
	@Transient
	private String remark;
	@Transient
	private String wireCoil;

    @Transient
    @JSONField(serialize = false)
    public Mat getMat(){
        if(StringUtils.isBlank(matCode)){
            return null;
        }
        StaticDataCache staticDataCache = (StaticDataCache) ContextUtils.getBean(StaticDataCache.class);
        return staticDataCache.getMatMap().get(matCode);
    }

    @Transient
    private MatType matType;

    @Transient
    public String getMatSpec(){
        if(StringUtils.isBlank(matSpec)){
            return matSection;
        }
        return matSpec;
    }
    
	
	@Transient
	private String salesOrderItemId; // 客户订单id：用于保存特殊工艺的变更记录用
	@Transient
	private String oldMatCode; // 旧物料编码：用于保存特殊工艺的变更记录用
	@Transient
	private String oldMatName; // 旧物料名称：用于保存特殊工艺的变更记录用

}
