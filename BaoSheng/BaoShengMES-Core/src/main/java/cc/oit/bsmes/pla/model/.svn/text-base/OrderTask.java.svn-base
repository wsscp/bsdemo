/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */
package cc.oit.bsmes.pla.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.util.NoGenerator;
import cc.oit.bsmes.common.util.Range;

/**
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-1-3 下午2:56:05
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_ORDER_TASK")
public class OrderTask extends Base implements Comparable<OrderTask> {
	private static final long serialVersionUID = 4620923396533858536L;

	private String orderItemProDecId;
	private String processId;
	private String processPath;
	private String equipCode;
	private String contractNo;
	private String operator; // 经办人
	private String productCode;
	private Date planStartDate;
	private Date planFinishDate;
	private Boolean isDelayed;
	private String workOrderNo;
	private String shift;
	private Double taskLength;
	private WorkOrderStatus status;
	private String orgCode; // 所属组织
	private String color;
	private String oldProcessId;
	@Transient
	@Column(name = "PROCESS_CODE")
	private String processCode;
	@Transient
	private Range range;
	@Transient
	private Double progress;
	private Boolean isLocked;

	@Transient
	private Double percent;
	@Transient
	private CustomerOrderItemProDec order;

	public OrderTask() {
		this(false);
	}

	public OrderTask(boolean generateWoNo) {
		super();
		if (generateWoNo) {
			workOrderNo = NoGenerator.generateNoByDate();
		}
	}

	@Transient
	private String equipName;
	@Transient
	private String customerOrderNo;
	@Transient
	private String halfProductCode;
	@Transient
	private String processName;
	@Transient
	private Date subOaDate;
	@Transient
	private Date latestStartDate;
	@Transient
	private Date latestFinishDate;
	@Transient
	private Double reportNum;

	@Override
	public int compareTo(OrderTask o) {
		if (this.getPlanFinishDate().getTime() > o.getPlanStartDate().getTime()) {
			return 2;
		} else if (this.getPlanStartDate().getTime() < o.getPlanStartDate().getTime()) {
			return -2;
		} else if (this.getPlanFinishDate().getTime() == o.getPlanStartDate().getTime()) {
			return 1;
		} else if (this.getPlanStartDate().getTime() == o.getPlanStartDate().getTime()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Transient
	public Range getRange() {
		if (range == null) {
			if (planStartDate == null && planFinishDate == null) {
				return null;
			} else {
				return new Range(planStartDate.getTime(), planFinishDate.getTime());
			}
		} else {
			return range;
		}
	}

	public void fixPlan() {
		planStartDate = new Date(range.getMinimum());
		planFinishDate = new Date(range.getMaximum());
	}

	@Transient
	public String getStatusText() {
		if (status == null) {
			return "";
		}
		return status.toString();
	}

	@Transient
	private Integer wirecoilcount;
}
