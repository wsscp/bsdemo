package cc.oit.bsmes.fac.model;

import cc.oit.bsmes.common.constants.MaintainStatus;
import cc.oit.bsmes.common.constants.MaintainTemplateType;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.util.*;

/**
 * 维护记录表
 * @author chanedi
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_FAC_MAINTAIN_RECORD")
public class MaintainRecord extends Base {

	private static final long serialVersionUID = -1957488168L;

	private String tmplId; //模版ID
	private String equipCode; //设备编码
	private Date startTime; //维修完成时间
	private Date finishTime; //维修完成时间
	private MaintainStatus status; //维修状态
    private MaintainTemplateType type; //模版类型
    @Transient
    private String describe;
    @Transient
    private String value;
    @Transient
    private Boolean isPassed;
    @Transient
    private String  remarks;
   
}