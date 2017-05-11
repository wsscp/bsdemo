package cc.oit.bsmes.interfacePLM.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * Prcv
 * <p style="display:none">产品工艺基本信息</p>
 * @author DingXintao
 * @date 2014-09-26 13:34:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "PRCV")
public class Prcv extends Base {
	
    private static final long serialVersionUID = -2081629262L;
	
	/** 变量 del . */
	private Boolean del;
	/** 变量 msym . */
	private String msym;
	/** 变量 wkaid . */
	private String wkaid;
	/** 变量 designno . */
	private String designno;
	/** 变量 bldesignno . */
	private String bldesignno;
	/** 变量 no . */
	private String no;
	/** 变量 name . */
	private String name;
	/** 变量 ename . */
	private String ename;
	/** 变量 drtname . */
	private String drtname;
	/** 变量 ver . */
	private Integer ver;
	/** 变量 ptype . */
	private String ptype;
	/** 变量 creator . */
	private String creator;
	/** 变量 ctime . */
	private Date ctime;
	/** 变量 muser . */
	private String muser;
	/** 变量 mtime . */
	private Date mtime;
	/** 变量 chkusr . */
	private String chkusr;
	/** 变量 chktime . */
	private Date chktime;
	/** 变量 duser . */
	private String duser;
	/** 变量 deltime . */
	private Date deltime;
	/** 变量 alteruser . */
	private String alteruser;
	/** 变量 owner . */
	private String owner;
	/** 变量 state . */
	private String state;
	/** 变量 smemo . */
	private String smemo;
	/** 变量 sccc . */
	private Integer sccc;
	/** 变量 czzt . */
	private Boolean czzt;
	/** 变量 sel . */
	private Boolean sel;
	/** 变量 modname . */
	private String modname;
	/** 变量 location . */
	private String location;
	/** 变量 fname . */
	private String fname;
	/** 变量 filever . */
	private String filever;
	/** 变量 fsize . */
	private BigDecimal fsize;
	/** 变量 fmtime . */
	private Date fmtime;
	/** 变量 fmstate . */
	private Boolean fmstate;
	/** 变量 tfname . */
	private String tfname;
	/** 变量 tlocation . */
	private String tlocation;
	/** 变量 tfmstate . */
	private Boolean tfmstate;
	/** 变量 pfname . */
	private String pfname;
	/** 变量 plocation . */
	private String plocation;
	
	private Boolean pfmstate;
	
	/** 产品代码 */
	@Transient
    private String productNo;
	
	public Date getMtime()
	{
		if(mtime==null)
		{
			return ctime;
		}else
		{
			return mtime;
		}

	}
	
}
