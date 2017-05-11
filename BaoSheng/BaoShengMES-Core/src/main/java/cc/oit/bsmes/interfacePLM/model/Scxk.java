package cc.oit.bsmes.interfacePLM.model;

import java.util.Date;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * Created by Jinhy on 2015/3/31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "SCXK")
public class Scxk extends Base{

    private String no;
    private String name;
    private Date ctime;
    private Date  mtime;
    private String ename;
    private String csvalue1;
    private String csvalue2;
    private String csvalue3;
    private String cstype;
    private String designno;
    private String bldesignno;
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
