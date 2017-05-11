package cc.oit.bsmes.wip.model;


import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 
 *生产状态报表
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-3 上午10:57:19
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductStatus extends Base {
	private static final long serialVersionUID = 434248754504858446L;
	
	private String color;
	private String contractNo;				//合同号
	private String workOrderNo;				//生产单号
	private String halfProductCode;
	private String batchNo;					//批次号(盘号)
	private String matCode ;                //物料号
	private WorkOrderStatus status;			//状态
	private String processCode;		        //工序编号
	private String processName;				//工序名称
	private String productType;             //产品类型
	private String productSpec;             //产品规格
	private String equipCode;		        //设备编号
	private String reportUserCode;		    //操作工工号
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date realStartTime;		    	//开始开始时间
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date realEndTime;				//实际结束时间
	private Double orderlength;				//总长度
	private Double completedLength;			//完成长度
	private String col;
	private String specification;			//规格
	
	//---------------------生产过程追溯
	@Transient
	private Long  processTime;             //加工时长
	private String orgCode;
	@Transient
	private String productCode;
}
