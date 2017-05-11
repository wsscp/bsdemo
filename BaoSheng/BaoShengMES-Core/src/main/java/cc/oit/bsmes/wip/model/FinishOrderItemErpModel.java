package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class FinishOrderItemErpModel extends Base  {
	private static final long serialVersionUID = 8873168584727177723L;
	
	private String ztid;   //账套ID
	private String scbmid; //工序编码
	private String scbmmc; //推送状态
	private String jjrid;    //交接人ID
	private String jjr;      //交接人
	private String xsddid;   //销售订单ID
	private String hth;      //合同号
	private String xsddmxid; //销售订单明细ID
	private String cpid;     //产品ID
	private Double jjsl;     //交接数量
	private String jldw;     //计量单位
	private String zjbj;     //质检标记
	private String czlx;     //操作类型


}

