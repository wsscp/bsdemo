package cc.oit.bsmes.pla.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.CustomerOrderStatus;
import cc.oit.bsmes.common.model.Base;
/**
 * 客户订单。
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_CUSTOMER_ORDER")
public class CustomerOrder extends Base {
	
	private static final long serialVersionUID = 5648927173623455245L;
	
	private String customerOrderNo;
	@Transient
	private String productCode;//产品代码
	
	private String salesOrderId;
	
	private Date oaDate; // 内部指定交期用于试排oa，试排结果和客户确认后为客户指定交期
	
	private String remarks;
	
	private CustomerOrderStatus status;
	
	private Boolean fixedOa;
	
	private Integer shceduleOrder;
	
	private Date confirmDate;
	
	private Date customerOaDate; // 客户指定交期

    private Date planStartDate;//计划开始时间

    private Date planFinishDate;//计划完成时间

	private String orgCode;   //所属组织
    private Date lastOa; // 上次oa时间
	
	@Transient
	private List<CustomerOrderItem> orderItems;
	
	public void addCustomerOrderItem(CustomerOrderItem orderItem) {
		if (orderItems == null) {
			orderItems = new ArrayList<CustomerOrderItem>();
		}
		orderItems.add(orderItem);
	}
	
	//显示字段
	@Transient
	@OrderColumn
	private String contractNo;//合同号
	@Transient
	@OrderColumn
    private String customerCompany;//客户单位
	@Transient
	@OrderColumn
    private String operator;//经办人
	@Transient
	@OrderColumn
	private Integer seq;//优先级
    @Transient
    private Integer importance; //客户重要程度

    //查询条件字段
    @Transient
    private String productSpec;
    @Transient
    private String productType;
    @Transient
    private Double section;
    @Transient
    private String wiresStructure;
    @Transient
    private String highPriorityId;
    @Transient
    private Date startDate;
    @Transient
    private Date endDate;
    @Transient
    private int orderFileNum;

    public  String getFixedOaText(){
        if(null != fixedOa && fixedOa){
            return "是";
        }else{
            return "否";
        }
    }

}
