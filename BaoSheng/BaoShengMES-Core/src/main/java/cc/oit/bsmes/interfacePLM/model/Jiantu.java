package cc.oit.bsmes.interfacePLM.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;
/**
 * 
 * 简图
 * <p style="display:none">modifyRecord</p>
 * @author rongyidong
 * @date 
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "JIANTU")
public class Jiantu extends Base {
	private static final long serialVersionUID = 27349279234L;
	
	private String  no;
	private String del;
	private String msym;
	private String wkaid;
	private String designno;
	private String bldesignno;
	private String ver;
	private String owner;
	private String  name;
	private String  fname;
	private String  filever;
	private String  ptype;
	private String location;
	private String state;
	private String  tsize;
	private int fsize;
	private String  pagenum;
	private String  smemo;
	
}
