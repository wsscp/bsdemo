package cc.oit.bsmes.pla.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.model.Base;

/**
 * 计算OA最后更新数据的中间临时表 独立建表更新，目的是因为计算时间过程，更新常用的几张表锁表，导致其他程序操作死锁
 * 
 * @author DingXintao
 * @date 2015-12-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_OA_UPTEMP")
public class OaUptemp extends Base {

	private static final long serialVersionUID = -1256207406652956971L;
	private String tableName; // 更新表名
	private String tableUid; // 更新表ID
	private Date planStartDate; // 计划开工日期
	private Date planFinishDate; // 计划完成日期
	private Date lastOa; // 上次计算日期
	private String orgCode; // 组织机构
}
