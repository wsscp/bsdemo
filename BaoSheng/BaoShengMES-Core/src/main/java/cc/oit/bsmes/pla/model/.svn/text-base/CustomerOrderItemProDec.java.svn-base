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

/**
 * 用作计算OA的时候使用，不用每次都分解改数据，只在工艺变化或者是新订单的时候分解该数据
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author JinHanyun
 * @date 2013-12-24 下午3:11:47
 */

// @Data
// @EqualsAndHashCode(callSuper = false, doNotUseGetters = true, exclude =
// {"orderTaskStatus","orderTask", "processOut", "nextOrder",
// "lastOrders","productProcess", "inventoryUseLogs", "parrelCount",
// "processSeq"})
@Setter
// 为属性提供 setting 方法
@Getter
// 为属性提供 getting 方法
@NoArgsConstructor
// 为类提供一个无参的构造方法
@AllArgsConstructor
// 为类提供一个全参的构造方法
@ToString(callSuper = true)
@Table(name = "T_PLA_CU_ORDER_ITEM_PRO_DEC")
public class CustomerOrderItemProDec extends Base implements IScheduleable {

	private static final long serialVersionUID = -1307586852286885908L;

	private String orderItemDecId;
	private String craftsId;
	private String processId;
	private String equipCode;
	private Double planWorkHours;
	private Boolean useStock;
	private Boolean isLocked;
	private Double usedStockLength;
	private String halfProductCode;
	private Date earliestStartDate;
	private Date earliestFinishDate;
	private Date latestStartDate;
	private Date latestFinishDate;
	private Double unFinishedLength;
	private Double finishedLength;
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
	private String wireCoil; // 线盘/盘具要求
	private Integer wireCoilCount; // 分盘数量
	private String outMatDesc; // 半成品说明
	private String color; // 物料颜色
	private String outAttrDesc; // 产出JSON描述字符串
	private String relateOrderIds; // 相关订单的ID
	
	// 新添加字段，减少关联查询，提高性能
	private String productType; // 产品型号
	private String custProductType; // 客户产品型号
	private String custProductSpec; // 客户产品规格
	
	private String operator; // 经办人
	private String workOrderNo; // 生产单号
	private String oldProcessId;//实例表工序ID
	
	@Transient
	private String material; // 材料
	@Transient
	private String code;
	@Transient
	private String name;
	@Transient
	private String equipAlias;
	@Transient
	private String operateReason;
	@Transient
	private String createUserName;
	@Transient
	private String taskLength;
	@Transient
	private Integer processSeq;
	@Transient
	private String splitLengthRole; // 分盘要求
	@Transient
	private int parrelCount = 1; // 此工序产出长度/最终产品卷长度
	@Transient
	private List<InvOaUseLog> inventoryUseLogs;
	@Transient
	@JSONField(serialize = false)
	private ProductProcess productProcess;
	@Transient
	private List<CustomerOrderItemProDec> lastOrders;
	@Transient
	private CustomerOrderItemProDec nextOrder;
	// @Transient
	// private ProcessInOut processOut;
	@Transient
	private OrderTask orderTask;
	@Transient
	private WorkOrderStatus orderTaskStatus;

	public void setLastOrders(Collection<CustomerOrderItemProDec> lastOrders) {
		this.lastOrders = (List<CustomerOrderItemProDec>) lastOrders;
		for (CustomerOrderItemProDec lastOrder : lastOrders) {
			lastOrder.setNextOrder(this);
		}
	}

	@Transient
	@JSONField(serialize = false)
	public ProcessInOut getProcessOut() {
		if (StringUtils.isBlank(processId)) {
			return null;
		}
		List<ProcessInOut> inOuts = StaticDataCache.getByProcessId(processId);
		for (ProcessInOut inOut : inOuts) {
			if (inOut.getInOrOut() == InOrOut.OUT) {
				return inOut;
			}
		}
		return null;
	}

	@Override
	public ProductProcess getProductProcess() {
		if (StringUtils.isBlank(processId)) {
			return null;
		}
		return StaticDataCache.getProcessByProcessId(processId);
	}

	@Transient
	public String[] getOptionalEquipCodes() {
		if (StringUtils.isEmpty(optionalEquipCode)) {
			return new String[] {};
		}
		return optionalEquipCode.split(",");
	}

	public Long getLatestStartTime(long orderDurationTime) {
		if (orderTask != null) {
			Range nextRange = orderTask.getRange().getNextRange();
			if (nextRange != null) { // 推算最晚开始
				return nextRange.getMinimum() - orderDurationTime;
			}
		}
		return latestStartDate == null ? null : latestStartDate.getTime();
	}

	// for sop的非精确排程，这里程序运行后不管是否有并行工序都只有一个lastOrder
	public void addLastOrder(CustomerOrderItemProDec lastOrder) {
		if (lastOrders == null) {
			lastOrders = new ArrayList<CustomerOrderItemProDec>();
		}
		lastOrders.add(lastOrder);
	}

	@Override
	@Transient
	@JSONField(serialize = false)
	public List<CustomerOrderItemProDec> getOrders() {
		List<CustomerOrderItemProDec> orders = new ArrayList<CustomerOrderItemProDec>();
		orders.add(this);
		return orders;
	}

	public String printLastOrders() {
		if (lastOrders.size() == 0) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (CustomerOrderItemProDec lastOrder : lastOrders) {
			sb.append(lastOrder.getId());
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	@Transient
	private InOrOut inOrOut; // 投入产出标识
	@Transient
	private Double quantity; // 单位投入用量
	@Transient
	private String matName; // 物料名称
	@Transient
	private String orderItemId; // 订单产品ID
	@Transient
	private Double totalQuantity; // 订单产品ID
	@Transient
	private String matCode; // 物料编码
	@Transient
	private String unit; // 单位
	@Transient
	private String wiresStructure; // 线芯结构
	@Transient
	private String moldCoreSleeve; // 模芯模套
	@Transient
	private String discrColor; // 辨别色，1#-10#/黑 此类颜色拆解工序辨别色
	@Transient
	private Integer seq; // 工序排序
	@Transient
	private String matType; // 物料类型
	@Transient
	private String kuandu; // 宽度
	@Transient
	private String houdu; // 厚度
	@Transient
	private String caizhi; // 材质
	@Transient
	private String chicun; // 尺寸
	@Transient
	private String guige; // 规格
	@Transient
	private String dansizhijing; // 单丝直径
	@Transient
	private String disk; // 库位
	@Transient
	private String preProcessCode; // 上一道工序
	// @Transient
	// private String propMaxValue; // 标称厚度最大值
	// @Transient
	// private String propMinValue; // 标称厚度最小值
	// @Transient
	// private String propTargetValue; // 标称厚度
	@Transient
	private String outsideValue; // 标准外径
	@Transient
	private String frontOutsideValue; // 前外径
	@Transient
	private String outsideMinValue; // 最小外径
	@Transient
	private String outsideMaxValue; // 最大外径
	@Transient
	private String standardPly; // 标准厚度
	@Transient
	private String standardMaxPly; // 标称厚度最大值
	@Transient
	private String standardMinPly; // 标称厚度最小值
	@Transient
	private String guidePly; // 指导厚度
	@Transient
	private String coverRate; // 包带搭盖率
	@Transient
	private String minPly; // 最小厚度
}