package cc.oit.bsmes.fac.model;

import cc.oit.bsmes.common.constants.MaintainTemplateType;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "T_FAC_EQUIP_MAINTAIN_STATE")
public class EquipMaintainState extends Base {

    private static final long serialVersionUID = -6797191737044154377L;

    private String equipCode; // 设备编码
    private String equipName; // 设备名称
    private Date startDate; // 维护预计开始时间 for nature
    private Double timeNeeded; // 维修用时 单位：小时
    private Integer triggerCycle; // 触发周期 for runtime
    private MaintainTemplateType maintainType; // 维护模版类型
    private String eventInfoId; // EVENT_INFO_ID
    private Date lastMaintainDate; // 设备上次维护时间 for runtime 用于计算已经运行的时间
    private Boolean completed; // 已完成
    private String orgCode;
    private String maintainer; //维修负责人，中文

}
