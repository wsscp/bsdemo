package cc.oit.bsmes.pla.model;


import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import cc.oit.bsmes.common.model.Base;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderStatistics extends Base{

	//private static final long serialVersionUID = 650294784490041819L;
	private String id;
	private String section; //工段
	private String name; // 工序
	private Double sumLength; // 生产总量
	private String shiftName; // 班次
	private List<String> shiftNameList; // 班次详细
	private Date startTime; //故障开始时间
	private Date endTime;//故障结束时间
	
}
