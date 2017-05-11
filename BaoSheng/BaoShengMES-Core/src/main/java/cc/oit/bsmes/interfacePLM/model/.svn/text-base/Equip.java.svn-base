package cc.oit.bsmes.interfacePLM.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 * 工装
 * Created by JinHy on 2014/9/28 0028.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "EQUIP")
public class Equip extends Base{
    private String no;
    private String name;
    private String ename;
    private String specs;
    private String bnum;
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
