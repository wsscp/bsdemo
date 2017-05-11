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
 * Process
 * <p style="display:none">产品工艺流程（单元操作）</p>
 * @author DingXintao
 * @date 2014-09-26 13:34:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "PROCESS")
public class Process extends Base {
	
    private static final long serialVersionUID = -2080971848L;
	
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
	/** 变量 ver . */
	private Integer ver;
	/** 变量 ptype . */
	private String ptype;
	/** 变量 ename . */
	private String ename;
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
	/** 变量 bnum . */
	private String bnum;
	/** 变量 opnum . */
	private String opnum;
	/** 变量 stepno . */
	private String stepno;
	/** 变量 stepname . */
	private String stepname;
	/** 变量 deptno . */
	private String deptno;
	/** 变量 deptname . */
	private String deptname;
	/** 变量 pret . */
	private BigDecimal pret;
	/** 变量 macht . */
	private BigDecimal macht;
	/** 变量 gno . */
	private Integer gno;
	/** 变量 details . */
	private String details;
	/** 变量 deviceno . */
	private String deviceno;
	/** 变量 devicename . */
	private String devicename;
	/** 变量 devicmode . */
	private String devicmode;
	/** 变量 stepnum . */
	private String stepnum;
	/** 变量 czzt . */
	private Boolean czzt;
	/** 变量 sccc . */
	private Boolean sccc;
	/** 变量 sfscc . */
	private Boolean sfscc;
	/** 变量 kxzggx . */
	private Integer kxzggx;
	/** 变量 srcs1 . */
	private String srcs1;
	/** 变量 srcs2 . */
	private String srcs2;
	/** 变量 scjg1 . */
	private String scjg1;
	/** 变量 scjg2 . */
	private String scjg2;
	/** 变量 dstate . */
	private String dstate;
	/** 变量 isbx . */
	private String isbx;
	/** 变量 tstate . */
	private String tstate;
	/** 变量 execstate . */
	private String execstate;
	/** 变量 csvalue1 . */
	private String csvalue1;
	/** 变量 csvalue2 . */
	private String csvalue2;
	/** 变量 csvalue3 . */
	private String csvalue3;
	/** 变量 cstype . */
	private String cstype;
	
	private String suser104;
	
	private String gnopx;
	
	/** 产品工艺ID */
	@Transient
    private String prcvId;

	/** 下一道工序ID */
	@Transient
	private String nextProcessId;

	/** 工序完整路径 */
	@Transient
	private String fullPath;

}
