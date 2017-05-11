package cc.oit.bsmes.interfacePLM.model;

import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;


import cc.oit.bsmes.common.model.Base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "PRODUCT")
public class PLMProduct  extends Base{
	private static final long serialVersionUID = 2839024748457791327L;
	
	private String no;          //代号
	private String name;		//名称
	private String ename;		//英文名称
	private String series;		//产品型号
	private String originno;	//参考产品代号
	private String projno;		//所属项目编号
	private String describe;	//产品描述
	private String monitor;		//产品负责人
	private String smemo;		//备注
	private String srole;		//制造属性维护角色
	private  Date ctime;
	private  Date mtime;
	
	@Transient
	private String craftNo;
	@Transient
	private String catanid; // 关联的分类ID

	private  String asuser01;
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
