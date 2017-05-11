package cc.oit.bsmes.interfacePLM.model;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;
/**
 * 
 * 二维图
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-9-29 上午11:18:34
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "DESF2")
public class Desf2 extends Base {
	private static final long serialVersionUID = 4270718613984915449L;
	
	private String  no;;
	private String  name;
	private String  fname;
	private String designno;
	private String bldesignno;
	private String  filever;
	private String  ptype;
	private String  tsize;
	private String  pagenum;
	private String  smemo;
	private String  mtlmark;
	private String  dsnweight;
	private String  scale;
	private String location;
	private int fsize;
}
