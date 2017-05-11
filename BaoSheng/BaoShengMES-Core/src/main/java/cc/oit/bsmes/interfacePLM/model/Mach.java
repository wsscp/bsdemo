package cc.oit.bsmes.interfacePLM.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 * 设备
 * Created by JinHy on 2014/9/26 0026.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "MACH")
public class Mach extends Base{
    private String  no;
    private String  name;
    private String  ename;
    private Date    ctime;
    private Date    mtime;
    private String  model;
    private String  specs;
    private String  center;
    private String  factory;
    private String  useprices;
    private String  ptype;
    public Date getCtime() {
		if(ctime==null)
		{
			return new Date();
		}
		return ctime;
	} 
	public Date getMtime() {
		if(mtime==null)
		{
			return getCtime();
		}
		return mtime;
	}
}
