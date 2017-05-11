package cc.oit.bsmes.interfacePLM.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 * 设备与工装关系
 * Created by JinHy on 2014/9/28 0028.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "MACH_OBJOF")
public class MachObjof extends Base{
    private String itemid1;
    private String itemtn1;
    private String itemid2;
    private String itemtn2;
    private Date ctime;
    private Date mtime;
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
