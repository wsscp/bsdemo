package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

/**
 * @ClassName: ReportUser
 * @Description: TODO(报工用户:挡班副挡板信息)
 * @author: DingXintao
 * @date: 2016年4月9日 下午4:21:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_REPORT_USER")
public class ReportUser extends Base {
	private static final long serialVersionUID = 449099792655755725L;
	private String reportId; // 报工表外键
	private String onoffId; // 报工人打卡ID
	private String userCode; // 报工人员工号
	private String userName; // 报工人员工姓名

}
