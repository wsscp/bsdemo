package cc.oit.bsmes.fac.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.MaintainTemplateType;
import cc.oit.bsmes.common.constants.MaintainTriggerType;
import cc.oit.bsmes.common.model.Base;

/**
 * 设备维护模版
 * @author chanedi
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_FAC_MAINTAIN_TEMPLATE")
public class MaintainTemplate extends Base {

	private static final long serialVersionUID = 271216487L;

    public static final String JSONKEY_CYCLE_TYPE = "cycleType";
    public static final String JSONKEY_CONFIG = "config";
    public static final String JSONKEY_CYCLE = "cycle";

	private String model; //设备型号
	private String orgCode; //数据所属组织
	private Integer triggerCycle;
	private MaintainTriggerType triggerType;
	private MaintainTemplateType type; //模版类型
	private String describe; //点检说明
    private Double time; //维修预计用时 单位：小时
    private String eqipCategory; //设备类型

    public void setType(String type) {
        this.type = MaintainTemplateType.valueOf(type);
    }

    public void setType(MaintainTemplateType type) {
        this.type = type;
    }

	@Transient
	private Integer triggerCycleH;
	@Transient
	private String triggerTypeH;

}