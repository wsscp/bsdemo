package cc.oit.bsmes.wip.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.pro.model.ProductProcess;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * WorkOrder
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author chanedi
 * @date 2014年1月6日 下午1:34:34
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_WIP_WORK_ORDER")
public class WorkOrder extends Base {

	private static final long serialVersionUID = -794182168275063469L;

	private String workOrderNo;
	private String equipCode;
	private Date preStartTime;
	private Date preEndTime;
	private Date realStartTime;
	private Date realEndTime;
	private Date auditTime;
	private String auditUserCode;
	private Double orderLength;
	private Double cancelLength;
	private WorkOrderStatus status;
	private String orgCode; // 所属组织
	private String processId; // 工序ID
	private String processCode; // 工序代码
	private String processName; // 工序名称
	private String halfProductCode;
	private Boolean isDelayed;
	private String fixedEquipCode;
	private String equipName;
	private Boolean isDispatch; // 是否急件
	private Boolean isOldLine; // 是否陈线
	private Boolean isAbroad; // 是否出口线
	private MaterialStatus matStatus; //
	private String matIsUsed;
	private Double percent; // 生产单完成进度
	
	@Transient
	private String custProductType;
	@Transient
	private String productLength;// 投产长度
	@Transient
	@OrderColumn
	private String contractNo;
	@Transient
	private String contractLength;
	@Transient
	private String typeSpec;
	@Transient
	@OrderColumn
	private String fixedEquipName;
	@Transient
	private String productCode;
	@Transient
	private String productName;
	@Transient
	@OrderColumn
	private String operator;
	@Transient
	@JSONField(serialize = false)
	private ProductProcess productProcess;

//	@Transient
//	@JSONField(serialize = false)
//	private List<WorkOrderOperateLog> operateLogList;

	@Transient
	public String getTimeByStatus() {
		DateFormat df = new SimpleDateFormat("MM月dd日 HH:mm:ss");
		if (status == null) {
			return "";
		}
		switch (status) {
		case FINISHED:
			return  realEndTime == null ? "" : "完成时间: " + df.format(realEndTime);
		case IN_PROGRESS:
			return  preEndTime == null ? "" : "预计完成时间: " + df.format(preEndTime);
		default:
			return  preStartTime == null ? "" : "预计开始时间: " + df.format(preStartTime);
		}
	}

	@JSONField(deserialize = false)
	@Transient
	@OrderColumn
	private String color;

	@Transient
	public String getMatStatusText() {
		if (matStatus != null) {
			return matStatus.toString();
		} else {
			return "";
		}
	}

	@Transient
	public String getColor() {
		if (color != null) {
			DataDic dataDic = StaticDataCache.getByColorCode(color);
			if (dataDic != null) {
				return dataDic.getName();
			}
		}
		return "";
	}

	@Transient
	private Integer seq;
	// 备注
	@Transient
	@OrderColumn
	private String remarks;
	@Transient
	private String workOrderId;
	@Transient
	private String productType; // 产品型号
	@Transient
	private String productSpec; // 产品规格
	@Transient
	private String outProductColor; // 颜色
	@Transient
	private String matSection;

	@Transient
	public String getOutProductColor() {
		if (StringUtils.isBlank(outProductColor)) {
			outProductColor = color;
		}

		if (StringUtils.isNotBlank(outProductColor) && StringUtils.isNotBlank(matSection)) {
			return outProductColor + "(" + matSection + ")";
		}

		return outProductColor;
	}

	@Transient
	private Double section; // 截面
	@Transient
	private String customerCompany; // 单位
	@Transient
	private String wiresStructure; // 线芯结构
	@Transient
	private Double standardLength;
	@Transient
	private Double taskLength;
	@Transient
	private String finishedPercent;
	@Transient
	private String wireColor;
	@Transient
	private String orderItemId;
	@Transient
	private String unFinishedLength;
	@Transient
	private String finishedLength;

	// 为成缆生产单添加
	private String processGroupRespool; // 上一道生产单单号(用于形成生产单流程)
	private String processGroup;
	private String processesMerged; // 成缆生产单所包含的工序组合情况/JSON
	private String workOrderSection; // 生产单所属工段 1:绝缘工段 2:成缆工段 3:护套工段(用于查看生产单所用)
	private String nextSection; // 生产单下一个加工工段 1:绝缘工段 2:成缆工段 3:护套工段(用于显示工段列表所用)
	private String docMakerUserCode; // 制单人
	private String receiverUserCode; // 接收人
	// @JSONField(format="yyyy-MM-dd")
	private Date releaseDate; // 下达日期
	private Date requireFinishDate; // 要求完成日期
	private String userComment; // 备注
	private String specialReqSplit;// 特殊分盘要求
	private String cusOrderItemIds;// 当前生产单中下发的所有客户生产订单明细IDs
	private String completeCusOrderItemIds; // 该生产单已经完成该工段下发的客户生产订单明细IDs：针对NEXT_SECTION=5(2+3)，处于成缆和护套都需要下发该生产单的情况
	private String auditedCusOrderItemIds; // 该生产单已经下发的客户生产订单明细IDs
	@Transient
	private String conductorstruct;// 导体结构

	@Transient
	private String colorProductLength;// 颜色-投产长度 终端显示

	@Transient
	private String operateReason;// 切换生产单原因
	@Transient
	private String outAttrDesc; // 产出属性描述
	@Transient
	private String orderfilenum; // 生产单关联的订单附件数量
	

	// 终端界面查询
	@Transient
	public String getWiresStructureStr() {
		if (StringUtils.isNotBlank(outAttrDesc)) {
			return outAttrDesc;
		}
		return "";
	}
}
