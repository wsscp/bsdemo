package cc.oit.bsmes.fac.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.wip.model.GraphValue;

/**
 * highChart 图标前台对象：line图
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class LineChart {
	private String name; // 名称
	private List<GraphValue> data = new ArrayList<GraphValue>(); // 数据
	@Transient
	private EquipStatus status; // 状态
}