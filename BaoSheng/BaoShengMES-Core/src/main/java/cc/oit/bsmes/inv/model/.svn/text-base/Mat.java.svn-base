package cc.oit.bsmes.inv.model;

import cc.oit.bsmes.common.constants.MatType;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 物料表
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2013-12-24 下午4:19:34
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_INV_MAT")
public class Mat extends Base{

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
    @Transient
    private String processWipId;
    @Transient
    private String salesOrderItemId;
    @Transient
    private String processInOutWipId;
    @Transient
    private String productCode;
    @Transient
    private String remark;
    @Transient
    private String matSize;
    @Transient
    private String unFinishedLength;
    @Transient
    private String imagePath;
    @Transient
    private String templetName;
    @Transient
    private String quantity;
    

    @Transient
    public String getMatSpec(){
        if(StringUtils.isBlank(matSpec)){
            return matSection;
        }
        return matSpec;
    }
 }