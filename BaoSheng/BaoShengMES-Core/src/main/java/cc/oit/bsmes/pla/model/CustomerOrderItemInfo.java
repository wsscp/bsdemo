package cc.oit.bsmes.pla.model;

import java.util.Date;

import cc.oit.bsmes.ord.dto.LengthConstraints;
import lombok.Data;
import lombok.EqualsAndHashCode;
import cc.oit.bsmes.common.constants.ProductOrderStatus;
import cc.oit.bsmes.common.model.Base;
import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @author JinHanyun
 * @date 2014-1-14 上午11:37:57
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerOrderItemInfo extends Base{

	private static final long serialVersionUID = 8823675917373365455L;
	
	private String  customerOrderId;
	private String  salesOrderId;
	private String  salesOrderItemId;
	private String 	customerCompany;
	private String 	contractNo;
	private String 	operator;
	private String 	custProductType;
	private Date 	customerOaDate;
	private Date 	oaDate;
	private String 	productType;
	private String 	productSpec;
	private Integer numberOfWires;
	private Double  orderLength;
	private Double  section;
	private String  wiresStructure;
	private String  itemRemarks;
	private Double  productLength;
	private Date  	productDate;
	private Double  wiresLength;
	private Double 	minLength;
	private String  salesOrderRemarks;
	private Double  contractAmount;
	private Date  	releaseDate;
	private String  status;
    private String  itemStatus;
	private Date  planStartDate;
	private Date  planFinishDate;
	//查看工艺信息需要显示
	private String productCode;

    private String lengthConstraints; // 长度约束

    public double getIdealMinLength() {
        if(lengthConstraints == null){
            return 0;
        }
        LengthConstraints _lengthConstraints = new LengthConstraints(lengthConstraints);
        return _lengthConstraints.getMaxMinLength();
    }
}
