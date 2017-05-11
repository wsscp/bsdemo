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
package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.RoleResource;
import cc.oit.bsmes.common.dao.BaseDAO;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2013-12-12 下午12:03:54
 * @since
 * @version
 */
public interface RoleResourceDAO extends BaseDAO<RoleResource>{

	/**
	 * <p>根据资源ID删除资源角色关系</p> 
	 * @author QiuYangjun
	 * @date 2014-5-27 上午10:23:25
	 * @param resourceId
	 * @see
	 */
	void deleteByResourceId(String resourceId);

	void deleteByRoleId(String roleId);
	
}
