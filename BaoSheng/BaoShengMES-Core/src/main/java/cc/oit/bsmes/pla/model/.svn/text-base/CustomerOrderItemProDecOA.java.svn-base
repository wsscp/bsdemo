package cc.oit.bsmes.pla.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;

import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.util.Range;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.pla.schedule.model.IScheduleable;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProductProcess;

import com.alibaba.fastjson.annotation.JSONField;

@Setter
// 为属性提供 setting 方法
@Getter
// 为属性提供 getting 方法
@NoArgsConstructor
// 为类提供一个无参的构造方法
@AllArgsConstructor
// 为类提供一个全参的构造方法
@ToString(callSuper = true)
@Table(name = "T_PLA_CU_ORDER_ITEM_PRO_DEC_OA")
public class CustomerOrderItemProDecOA extends Base {

	private static final long serialVersionUID = -1307586852286885908L;

	private String orderItemDecId;
	private String craftsId;
	private String processId;
	private String equipCode;
	private Double planWorkHours;
	private Boolean useStock;
	private Boolean isLocked;
	private Double usedStockLength = 0d;
	private String halfProductCode;
	private Date earliestStartDate;
	private Date earliestFinishDate;
	private Date latestStartDate;
	private Date latestFinishDate;
	private Double unFinishedLength;
	private Double finishedLength = 0d;
	private ProductOrderStatus status;
	private String processPath;
	private String processName;
	private String processCode;
	private String optionalEquipCode;
	private String fixedEquipCode;
	private String contractNo;
	private String productCode;
	private String productSpec;
	private String nextOrderId;
	private String orgCode; // 所属组织
	
}