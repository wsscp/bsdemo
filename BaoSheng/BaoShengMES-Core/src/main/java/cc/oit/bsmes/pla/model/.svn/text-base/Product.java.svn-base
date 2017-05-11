package cc.oit.bsmes.pla.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * 产品基本信息
 * @author leiwei
 * @version   
 * 2013-12-24 下午3:20:43
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLA_PRODUCT")
public class Product extends Base {
	private static final long serialVersionUID = 660520974009860372L;
	
	private String id;
	private String productCode;  	//产品代码
	private String productName;		//产品名称
	private String productType;		//产品型号
	private String productSpec;		//产品规格
	private Boolean usedStock;		//允许使用库存
	private String craftsCode;  	//产品工艺代码 
	private String craftsVersion;	//工艺版本号
	private String orgCode;   //所属组织
	private String standardLength;  //标准长度
	private Boolean complex;   //复杂产品
	private String classifyId; // 分类ID
	@Transient
    private String contractNo;
	@Transient
    private String customerCompany;
	@Transient
    private Double orderLength;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        Product product = (Product) o;
        if (getId() != null ? !getId().equals(product.getId()) : product.getId() != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        return result;
    }

    @Transient
    public Double orderProLengthOnEquip;

    public String getComplexText(){
        if(complex == null){
            return "";
        }else if(complex){
            return "复杂产品";
        }else{
            return "非复杂产品";
        }
    }

    public String getUsedStockText(){
        if(usedStock == null){
            return "";
        }else if(usedStock){
            return "允许";
        }else{
            return "不允许";
        }
    }
}
