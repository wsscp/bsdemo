package cc.oit.bsmes.ord.model;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.constants.SalesOrderStatus;
import cc.oit.bsmes.common.model.Base;
import cc.oit.bsmes.ord.dto.LengthConstraints;

/**
 * 销售订单明细。
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_ORD_SALES_ORDER_ITEM")
public class SalesOrderItem extends Base {

	private static final long serialVersionUID = -74786142921076970L;
	public static final String ORDER_ITEM_TYPE_A = "A";
	public static final String ORDER_ITEM_TYPE_B = "B";
	public static final String ORDER_ITEM_TYPE_C = "C";
	
	private String salesOrderId; // 客户销售订单ID
	private String productId;    //产品ID
	private String departmentId; //部门ID
	private String custProductType; // 客户产品型号
	private String custProductSpec; // 客户产品规格
	private String productCode; // 产品代码
	private String productType; // 产品型号
	private String productSpec; // 产品规格
	private Integer numberOfWires; // 芯数
	private String section; // 截面
	private String wiresStructure; // 线芯结构
	private Double wiresLength; // 线芯长度
	private Double contractAmount; // 合同金额
	private String minLength;//最短长度
	private Double saleorderLength; // 订单长度
	private Double standardLength; // 每卷标准长度
	private String processRequire; // 加工要求
	private SalesOrderStatus status;
	private String remarks; // 备注说明
    @Deprecated
	private String craftsCode; // 工艺代码
    @Deprecated
	private Integer craftsVersion; // 工艺版本号
    @Transient
	private LengthConstraints lengthConstraints; // 长度约束
    @Column(name = "LENGTH_CONSTRAINTS")
	private String lengthConstraintsStr; // 长度约束
	private String orgCode;   //所属组织
	
	/**
	 * 将格式为,100:0;,200:15;,300:20的字符串转换为{@link LengthConstraints}集合
	 * @author chanedi
	 * @date 2013年12月25日 上午10:46:41
	 * @param lengthConstraints
	 * @see
	 */
	public void setLengthConstraints(String lengthConstraints) {
		setLengthConstraintsStr(lengthConstraints);
	}

	public void setLengthConstraintsStr(String lengthConstraints) {
		this.lengthConstraints = new LengthConstraints(lengthConstraints);
        lengthConstraintsStr = lengthConstraints;
	}

    /**
     * 理想的最小长度，即长度约束中最大的最小长度
     */
    @Transient
    public double getIdealMinLength() {
        if (lengthConstraints == null) {
            return 0;
        }
        return lengthConstraints.getMaxMinLength();
    }

	private Double contractLength; // 生产单长度/投产长度
}
