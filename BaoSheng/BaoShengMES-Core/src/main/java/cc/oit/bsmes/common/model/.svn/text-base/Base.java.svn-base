package cc.oit.bsmes.common.model;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
public abstract class Base implements Serializable {

	private static final long serialVersionUID = 4333766667237671587L;

	public static final String ID_COLUMN = "ID";
	
	@Id
	private String id;
	
	private Date createTime;
	
	private Date modifyTime;
	
	private String createUserCode;
	
	private String modifyUserCode;

}