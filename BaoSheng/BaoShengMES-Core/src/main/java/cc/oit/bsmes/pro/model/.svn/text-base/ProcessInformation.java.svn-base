package cc.oit.bsmes.pro.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;
/**
 * 
 * 工序信息
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-26 下午4:06:12
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PRO_PROCESS_INFO")
public class ProcessInformation extends Base {
	private static final long serialVersionUID = 1930507251343186044L;
	public static final String JY = "绝缘";
	public static final String CL = "成缆";
	public static final String HT = "护套";
	
	private String code;                  //工序代码 
	private String name;                  //工序名称 
	private String section;               //所属工段 
	private String sectionSeq;            //工段顺序 
	private String orgCode;               //数据所属组织
	@Transient
	private Integer  seq; // 加工顺序
}
