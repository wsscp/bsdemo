package cc.oit.bsmes.fac.model;

import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pro.model.EquipList;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Table;
import javax.persistence.Transient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 设备信息
 * 
 * @author leiwei
 * @version 2013-12-24 下午2:23:59
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_FAC_EQIP_INFO")
public class EquipInfo extends Base {

	private static final long serialVersionUID = -2168521990659057552L;
	private static final String dfPattern = DateUtils.DATE_CH_FORMAT;

	private String name; // 设备名称
	private String code; // 设备编号
	private EquipStatus status; // 设备状态
	private EquipType type; // 设备型号
	private String orgCode; // 所属组织
	private String specs;
	private String ename;
	private String bnum;
	private String model;
	private String center;
	private String useprices;
	private String factory;
	private String equipAlias;//设备别名
	private String equipCategory;//设备分類
	private String equipPrice;//设备价格
	private String equipFact;//设备厂商
	private Date equipPurtime;//采购日期
	private String processCode; // 工序：表示这个设备是哪道工序的，逗号分隔
	private String statusBasisWw; // 数采设备状态依据主要属性
	private String section;
	private String mrl;
	@DateTimeFormat(pattern = DateUtils.DATE_FORMAT)
	private Date nextMaintainDate; // 下次维修时间
	@DateTimeFormat(pattern = DateUtils.DATE_FORMAT)
	private Date nextMaintainDateFirst; // 一级下次维修时间
	@DateTimeFormat(pattern = DateUtils.DATE_FORMAT)
	private Date nextMaintainDateSecond; // 二级下次维修时间
	@DateTimeFormat(pattern = DateUtils.DATE_FORMAT)
	private Date nextMaintainDateOverhaul; // 大修下次维修时间
	@Transient
	private String nextMaintainDateFirstStr; // 一级下次维修时间
	@Transient
	private String nextMaintainDateSecondStr; // 二级下次维修时间
	@Transient
	private String nextMaintainDateOverhaulStr; // 大修下次维修时间
	private Integer maintainDate; // 固定维修日
	private Integer maintainDateFirst; // 一级固定维修日
	private Integer maintainDateSecond; // 二级固定维修日
	private Integer maintainDateOverhaul; // 大修固定维修日
	private String maintainer; // 维修负责人code
	private String subType;
	@Transient
	private String belongLine;
	@Transient
	private int total;
	@Transient
	private String productLineId;
	@Transient
	@JSONField(serialize = false)
	private List<EquipCalendar> equipCalendar; // 设备工作日历(get方法默认加载3个月)
	@Transient
	@JSONField(serialize = false)
	private List<OrderTask> orderTasks; // 三天内的加工任务
	@Transient
	@JSONField(serialize = false)
	private List<OrderTask> oriOrderTasks; // 最初加载的加工任务，防止缓存中内存变更
	@Transient
	@JSONField(serialize = false)
	private List<WorkTask> workTasks; // 设备计划加工任务负载
	@Transient
	@JSONField(serialize = false)
	private List<EquipList> equipLists;
	@Transient
	private String roleId;
	@Transient
	private String ecode; // 设备编码：不是生产线的
	@Transient
	private String equipCapacity; // 设备能力
	@Transient
	private Integer setUpTime = 100;  // 前置时间
	@Transient
	private Integer shutDownTime = 100; //后置时间
	
	public List<OrderTask> getOrderTasks() {
		if (oriOrderTasks == null && orderTasks != null) {
			oriOrderTasks = new ArrayList<OrderTask>(orderTasks);
		}
		if (orderTasks != null) {
			Collections.sort(orderTasks);
		}
		return orderTasks;
	}

	@Transient
	public OrderTask getLastOrderTask() {
		if (orderTasks == null || orderTasks.size() == 0) {
			return null;
		}
		return getOrderTasks().get(orderTasks.size() - 1);
	}

	public void initOrderTasks() {
		if (oriOrderTasks == null) {
			getOrderTasks();
		}
		if (oriOrderTasks != null) {
			orderTasks = new ArrayList<OrderTask>(oriOrderTasks);
		}
	}

	@Transient
	public String getStatusText() {
		if (status != null) {
			return "(" + status.toString() + ")";
		}

		return "";
	}

	@Transient
	public List<String> getMaintainers() {
		if (StringUtils.isEmpty(maintainer)) {
			return new ArrayList<String>();
		}

		String[] splits = maintainer.split(",");
		return Arrays.asList(splits);
	}

	public void setType(String type) {
		this.type = EquipType.valueOf(type);
	}

	public void setType(EquipType type) {
		this.type = type;
	}

	public Date getNextMaintainDate() {
		if (getIsNeedMaintain()) {
			return this.nextMaintainDate;
		}

		return null;
	}

	public void setNextMaintainDateFirst(Date nextMaintainDateFirst) {
		this.nextMaintainDateFirst = nextMaintainDateFirst;
		if (nextMaintainDateFirst != null) {
			DateFormat df = new SimpleDateFormat(dfPattern);
			this.nextMaintainDateFirstStr = df.format(this.nextMaintainDateFirst);
		} else {
			this.nextMaintainDateFirstStr = null;
		}
	}

	public void setNextMaintainDateSecond(Date nextMaintainDateSecond) {
		this.nextMaintainDateSecond = nextMaintainDateSecond;
		if (nextMaintainDateSecond != null) {
			DateFormat df = new SimpleDateFormat(dfPattern);
			this.nextMaintainDateSecondStr = df.format(this.nextMaintainDateSecond);
		} else {
			this.nextMaintainDateSecondStr = null;
		}
	}

	public void setNextMaintainDateOverhaul(Date nextMaintainDateOverhaul) {
		this.nextMaintainDateOverhaul = nextMaintainDateOverhaul;
		if (nextMaintainDateOverhaul != null) {
			DateFormat df = new SimpleDateFormat(dfPattern);
			this.nextMaintainDateOverhaulStr = df.format(this.nextMaintainDateOverhaul);
		} else {
			this.nextMaintainDateOverhaulStr = null;
		}
	}

	public boolean getIsNeedMaintain() {
		return this.type == EquipType.MAIN_EQUIPMENT || this.type == EquipType.ASSIT_EQUIPMENT;
	}

	
}
