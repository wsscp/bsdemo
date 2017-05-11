package cc.oit.bsmes.ord.model;

import cc.oit.bsmes.common.constants.SalesOrderStatus;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 * 销售订单。
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_ORD_SALES_ORDER")
public class SalesOrder extends Base {

	private static final long serialVersionUID = 3027026063563264676L;
	
	private String salesOrderNo; // 客户销售订单编号
	private String customerCompany; // 客户单位
	private String contractNo; // 合同号
	private String operator; // 经办人
	private String remarks; // 备注说明
	private SalesOrderStatus status;
    private int importance; // 客户重要程度
	private Date confirmDate; // 订单确认日期
	private String orgCode;   //所属组织
	private Date releaseDate;//下达日期
}
