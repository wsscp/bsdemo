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
package cc.oit.bsmes.wip.dto;

import cc.oit.bsmes.common.constants.WorkOrderStatus;
import lombok.Data;

import java.util.Date;

/**
 * 
 * <p style="display:none">生产单查询页面返回数据</p>
 * @author QiuYangjun
 * @date 2014-1-20 下午2:37:59
 * @since
 * @version
 */
@Data
public class WorkOrderSearchPageResult {
	private String id;
	private String workOrderNo; // 生产单号
	private Date oaDate;		//订单最迟完工日期
	private String customerOrderNO;	//合同号
	private String productType;		//产品型号
	private String productSpec;		//产品规格
	private String processId;	//工序
	private String processName;	//工序
	private String equipName;	//机台
	private Date preStartTime;		//预计开始日期
	private Date preEndTime;		//预计结算日期
	private String shift;		//班次
	private WorkOrderStatus status;		//生产单状态
	private Double taskCount;		//总任务数
	private Date auditTime;		//审核日期
	private String color;		//颜色
	private String reqTec;		//技术要求
	private String remarks;		//备注
	private Boolean hasChild;
}
