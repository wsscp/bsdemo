package cc.oit.bsmes.interfacePLM.model;

import java.util.Date;

import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;

/**
 * 标准工序
 * Created by Jinhy on 2014/9/25 0025.
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "BZPROCESS")
public class BZProcess extends Base{
    private  String no;
    private  String name;
    private  String state;
    private  String ptype;
    private  Date ctime;
    private  Date mtime;
    
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
