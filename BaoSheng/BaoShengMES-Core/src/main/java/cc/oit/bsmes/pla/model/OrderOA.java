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
 * 订单OA结果查看 TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-1-23 上午10:50:06
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_ORD_SALES_ORDER_ITEM")
public class OrderOA extends Base {

	private static final long serialVersionUID = -1256207402652956971L;
	private String productType; // 产品型号
	private String productSpec; // 产品规格
	private String productCode; // 成品、半成品代码
	private String contractLength; // 合同长度
	private String custProductType; // 客户型号
	private String numberOfWires;// 线芯数

	private ProductOrderStatus status; // 订单状态
	private String section; // 截面
	private String wiresStructure; // 线芯结构
	private String orgCode; // 所属组织
	private Double orderLength; // 投产长度
	
	@Transient
	private Date latestStartDate; // 工序最晚开始时间
	@Transient
	private Date latestFinishDate; // 工序最晚结束时间
	@Transient
	private int totalVolumes; // 每个客户生产订单的卷数
	@Transient
	private String equipCode;
	@Transient
	private String[] queryStatus;
	@Transient
	List<OrderOA> subPage;
	private double contractAmount;
	@Transient
	private double finishedLength; // 已完成长度
	@Transient
	@OrderColumn
	private Date lastOa; // 上次计算时间
	@Transient
	@OrderColumn
	private Date planStartDate; // 计划开工日期
	@Transient
	@OrderColumn
	private Date planFinishDate; // 计划完工日期
	@Transient
	private Date oaDate; // 订单交货期
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Transient
	private Date planDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Transient
	private Date to;
	@Transient
	private String operator; // 销售员
	@Transient
	private String customerCompany; // 单位
	@Transient
	private String contractNo; // 合同号
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Transient
	@Column(name = "SUB_OA_DATE")
	private Date oa;
	@Transient
	private Double planWorkHours; // 计划用时

	public String getStatusText() {
		if (status == null) {
			return "";
		} else {
			return status.toString();
		}
	}
	
	
	@Transient
	private String id;
	@Transient
	private String itemId;
	@Transient
	private String processName; // 工序
	@Transient
	private String halfProductCode; // 半成品代码
	@Transient
	private String matName; // 物料名称
	@Transient
	private double usedLockLength; // 库存使用量
	@Transient
	private double unFinishedLength; // 库存使用量
	@Transient
	private String color;
	@Transient
	private String processId;
	@Transient
	private String processCode;
	@Transient
	private String orderItemId;
	@Transient
	private String optionalEquipCode;// 可选设备
	@Transient
	private int seq;
	@Transient
	private String equipName;
	@Transient
	private int toDoNum;
	@Transient
	private int inProgressNum;
	@Transient
	private int finishedNum;

}
