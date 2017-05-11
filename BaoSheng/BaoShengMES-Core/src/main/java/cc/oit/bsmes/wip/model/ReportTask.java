package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

/**
 * Created by JIN on 2015/5/6.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_REPORT_TASK")
public class ReportTask extends Base{
    private String reportId;
    private String orderTaskId;
    private String color;
    private Double reportLength;
    private String matCode; // 报工半成品编码
    private String diskNumber;
}
