package cc.oit.bsmes.interfacePLM.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 * 
 * Created by RongYd on 2015/4/27 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "PROCESS_OBJOF")
public class ProcessObjof extends Base{
	private static final long serialVersionUID = 743328430982835252L;
	private String designno;
    private String itemid1;
    private String itemtn1;
    private String itemid2;
    private String itemtn2;
    private Date ctime;
    private Date mtime;
}
