package cc.oit.bsmes.pla.model;


import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * 成品现货
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_FINISHED_PRODUCT")
public class FinishedProduct extends Base {

	private static final long serialVersionUID = -9055164650290350622L;
	
	private String serialNum;//序号
	private String num;//编号
	private String model; //型号
	private String spec;   //规格
	private Double length;  //长度
	private String dish;    //盘具
	private String region; //区域
	private String qualifying; //排位
	private Double usedLength; //使用长度
	private  String remarks;   //备注
	private String status;//0、表示成品现货被覆盖，1、成品现货被用完，2、成品现货使用之中

}
