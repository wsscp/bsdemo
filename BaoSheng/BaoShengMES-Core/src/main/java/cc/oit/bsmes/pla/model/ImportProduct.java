package cc.oit.bsmes.pla.model;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * @author rongyd
 * @date 2015-8-4 下午4:26:53
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_PLM_PRO_IMPORT_LOG")
public class ImportProduct extends Base {
	private static final long serialVersionUID = -6976585657683014608L;
	
	private String id;			
	private String seriesName;				
	private String productStatus;			
	private String mpartStatus;
	private String insertMpartStatus;
	private String processStatus;
	private String scxStatus;
	private String location;
	
	//导入人员姓名
	@Transient
	private String name;

}
