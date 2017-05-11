package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.constants.SectionType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.wip.dto.SectionLength;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Section
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月23日 上午11:35:34
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_SECTION")
public class Section extends Base implements SectionLength {

	private static final long serialVersionUID = -6896612277430312184L;
	
	@Column(name = "R_ID")
	private String reportId;

	private String orderItemProDecId;
	
	private String processPath;

	private Double productLength;
	
	private Double sectionLength;
	
	private Double goodLength;
	
	private SectionType sectionType;
	
	private String orgCode;   //所属组织

    @Transient
	@Override
	public Double getLength() {
		return goodLength;
	}

    private String workOrderNo;

    private Double sectionLocal;//分段位置

    @Transient
    public Double getStartLocal(){
        if(sectionLocal == null){
            return 0.0;
        }
        if(sectionLength == null){
            return sectionLocal;
        }
        return Double.parseDouble(WebConstants.DOUBLE_DF.format(sectionLocal - sectionLength));
    }

    @Transient
    private int rowSpanSize;

    @Transient
    private String contractNo;

    @Transient
    private String coilNum;

    @Transient
    public String getSectionTypeText(){
        if(sectionType == null){
            return "";
        }
        return sectionType.toString();
    }

}
