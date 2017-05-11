package cc.oit.bsmes.inv.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_MAT_WIP")
public class MatWip extends Base{


	private static final long serialVersionUID = 446862940624936503L;
    private String matCode;
    private String matName;
    //原材料 半成品   成品
    private MatType matType;
    private Boolean hasPic;
    private Boolean isProduct;
    private String templetId;
    
    @Column(name="REMARK")	
    private String desc;
    private String color;
    @Column(name="MAT_SIZE")	 
    private String size;
    private String orgCode;
    private String matSpec;
    private String matSection;
    private String processWipId;
    private String processInOutWipId;
    private String salesOrderItemId;

    @Transient
    public String getMatSpec(){
        if(StringUtils.isBlank(matSpec)){
            return matSection;
        }
        return matSpec;
    }
}
