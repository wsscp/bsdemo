package cc.oit.bsmes.interfaceWWAc.model;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
 

/**
 * 系统采集参数  历史数据
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2014-3-24 下午5:39:42
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "v_History")
public class EquipParamHistoryAcquisition extends Base {
    
	private static final long serialVersionUID = 3037496288867852501L;
	@Transient
    private String equipCode;
    private String tagname;
    private Double value;    
    private String vvalue;
    private int wwcyclecount; 
    private Date datetime;
    @Transient
    private Date startTime;
    @Transient
    private Date endTime; 
	@Transient
     private Date createTime;
	@Transient
	private Date modifyTime;
	@Transient
	private String createUserCode;
	@Transient
	private String modifyUserCode;
	@Transient
	private String id;
	@Transient
	private String productCode;
	
	 public Double  getValue(){
	        if(value == null){
	            return null;
	        }

	        if(value < 0){
	            value = value * -1;
	        }
	        return Double.parseDouble(WebConstants.DOUBLE_DF.format(value));
	    }

	    public String getVvalue(){
	        if(StringUtils.isBlank(vvalue)){
	            return "";
	        }

	        if(vvalue.contains("-")){
	            vvalue = vvalue.replaceFirst("-","");
	        }

	        int index = vvalue.indexOf(".");
	        if(index > 0 ){
	            if(index + 4 < vvalue.length()){
	                return vvalue.substring(0,index + 4);
	            }
	        }
	        return  vvalue;
	    }
}
