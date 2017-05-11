package cc.oit.bsmes.interfacePLM.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import cc.oit.bsmes.common.model.Base;


/**
 * 生产线
 * Created by JinHy on 2014/9/26 0026.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "SCX")
public class Scx extends Base{
    private String no;
    private String name;
    private Date  ctime;
    private Date  mtime;
    private String ename;

    /** 产品工序ID */
    @Transient
	private String processId;
}
