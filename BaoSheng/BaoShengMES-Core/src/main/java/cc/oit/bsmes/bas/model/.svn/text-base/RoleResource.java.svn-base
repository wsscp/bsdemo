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

/**
 * <p style="display:none">modifyRecord</p>
 *
 * @author QiuYangjun
 * @date 2013-12-12 下午12:03:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "T_BAS_ROLE_RESOURCE")
public class RoleResource extends Base {

	private static final long serialVersionUID = -4410307238865072793L;
	
	private String roleId;
    private String resourceId;
    private Boolean roleQuery;
    private Boolean roleCreate;
    private Boolean roleDelete;
    private Boolean roleEdit;
    private Boolean roleAdvanced;
}
