package cc.oit.bsmes.interfacePLM.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * CanShuKu
 * <p style="display:none">
 * 参数库
 * </p>
 * 
 * @author DingXintao
 * @date 2014-12-16 13:34:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "CANSHUKU")
public class CanShuKu extends Base {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3550732968593152314L;

	/** 变量 no . */
	private String no;
	/** 变量 name . */
	private String name;
	/** 变量 ctime . */
	private Date ctime;
	/** 变量 mtime . */
	private Date mtime;
	
	private String id;
	private String ptype;
	private String ztype;
	
	@Transient
	private String value; // 参数值
	@Transient
	private String processId; // 产品工序ID
	@Transient
	private String needFirstCheck = "0"; // 是否要首检
	@Transient
	private String needMiddleCheck = "0"; // 是否要中检
	@Transient
	private String needInCheck = "0"; // 是否要上车检
	@Transient
	private String needOutCheck = "0"; // 是否要下车检

}
