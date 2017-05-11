package cc.oit.bsmes.wip.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class MaterialTrace extends Base {
	private static final long serialVersionUID = 7034309314004158726L;
	
	private String matCode;       //原材料号
	private String batchNo;       //原材批号
	private String productBatches;       //产品批号
	private String equipCode;       	 //消耗机台
	private String operator;       		 //操作人
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date realStartTime;       //消耗时间
	private Double quantity ;       	 //消耗数量
	private String workOrderNo;
    private String orgCode;
}
