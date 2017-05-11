package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_DATA_DICT")
public class DataDic extends Base{
	
	private static final long serialVersionUID = -2832305094941910213L;

	private String termsCode;   //TERMS_CODE  词条类型CODE
	@Transient
	private String termsName;   //TERMS_CODE  词条类型CODE
	private String code;    //CODE  词条编号
	private String name;    //NAME   词条名称
	private Integer seq;    //SEQ   顺序号
	private String lan;    //LAN    语言
	private String extatt;  //EXTATT  扩展属性 
	private String marks;   //MARKS    备注
	private String status;  //STATUS   数据状态 
	private Boolean canModify; //CAN_MODIFY 是否可以修改
	public String getTermsName()
	{ 
		if(StringUtils.isEmpty(termsName))
		{
			if (StringUtils.isEmpty(termsCode))
			{
				return "";
			}
			return TermsCodeType.valueOf(termsCode).toString(); 
		}else
		{
			return termsName;
		}
	 
	}
	 
}
