package cc.oit.bsmes.wip.model;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.pro.model.ProductProcess;

import com.alibaba.fastjson.annotation.JSONField;


public class WorkOrderList extends Base {

	private static final long serialVersionUID = -794182163275063463L;

	private String workOrderNo;
	private BigDecimal numb;
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
	private Boolean isDispatch;
	@Transient 
	private String custProductType;
	@Transient 
	private String name;
	@Transient
	private String productLength;//投产长度
	@Transient 
	@OrderColumn
	private String contractNo;
	@Transient 
	private String typeSpec;
	@Transient
	@OrderColumn
	private String fixedEquipName;
	@Transient
	private Double percent;
	@Transient
	private String productCode;
	@Transient
	private String productName;
	@Transient
	@OrderColumn
	private String operator;
	
	@Transient
	public String getTimeByStatus() {
		DateFormat df = new SimpleDateFormat("MM月dd日 HH:mm:ss");
		if (status == null) {
			return "";
		}
		switch (status) {
		case FINISHED:
			return "完成时间: " + df.format(realEndTime);
		case IN_PROGRESS:
			return "预计完成时间: " + df.format(preEndTime);
		default:
			return "预计开始时间: " + df.format(preStartTime);
		}
	}

	@JSONField(deserialize = false)
	@Transient
	@OrderColumn
	private String color;

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
	// 备注
	@Transient
	@OrderColumn
	private String remarks;
	@Transient
	private String workOrderId;
	@Transient
	private String productType;		//产品型号
	@Transient
	private String productSpec;		//产品规格
	@Transient
	private String outProductColor;		//颜色
	@Transient
	private String matSection;

	@Transient
	public String getOutProductColor(){
		if(StringUtils.isBlank(outProductColor)){
			outProductColor =  color;
		}

		if(StringUtils.isNotBlank(outProductColor) && StringUtils.isNotBlank(matSection)){
			return outProductColor+"("+matSection+")";
		}

		return outProductColor;
	}
	
	@Transient
	private Double section; // 截面
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
	@JSONField(format="yyyy-MM-dd")
	private Date releaseDate; // 下达日期
	private Date requireFinishDate; // 要求完成日期
	private String userComment; // 备注
	private String specialReqSplit;// 特殊分盘要求
	private String cusOrderItemIds;// 当前生产单中下发的所有客户生产订单明细IDs
	@Transient
	private String conductorstruct;//导体结构
	
}




