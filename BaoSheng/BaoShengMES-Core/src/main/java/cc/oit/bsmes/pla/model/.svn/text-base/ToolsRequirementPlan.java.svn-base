package cc.oit.bsmes.pla.model;

import java.util.Date;

import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.model.Base;

/**
 * 
 * 辅助工具需求计划
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-1-21 上午10:27:51
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_TOOLES_RP")
public class ToolsRequirementPlan  extends Base{

	private static final long serialVersionUID = 4608010351044466574L;

	private String workOrderId;  // 生产单ID 
	/**
	 * 工具代码 
	 */
	private String tooles; 		  
	private Integer  quanyity;       //数量
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date planDate;        // 需求日期 
	private String processCode;   //工序代码
	@Transient
	@OrderColumn
	private String processName;	//工序名称
	private MaterialStatus status;  //状态
	/**
	 *加工工序所用生产线
	 */
	@Transient
	@OrderColumn
	private String equipCode;          //添加， 用于存放辅助设备
	private String orgCode;   //所属组织

    @Transient
	@OrderColumn
    private String workOrderNO;
}
