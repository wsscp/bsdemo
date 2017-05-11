/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */ 
package cc.oit.bsmes.bas.model;

import cc.oit.bsmes.common.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2013-12-11 下午1:20:44
 * @since
 * @version
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_EMPLOYEE")
public class Employee extends Base{
	private static final long serialVersionUID = 2657796355517415073L;
	private String name;
	private String userCode;
	private String telephone;
	private String email;
	private String topOrgCode;
	private String sources;
	private String orgCode;
	private String resumePath;
	private String certificate; //CERTIFICATE 人员资质
	
	@Transient
	private String orgName; // 组织机构名称
	@Transient
	private String certificateName; // 人员资质名称

	@Transient
	private String password;
}
