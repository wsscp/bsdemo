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
package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.dto.RoleResourceTreeDto;
import cc.oit.bsmes.bas.model.RoleResource;
import cc.oit.bsmes.common.service.BaseService;

import java.util.List;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2013-12-11 下午1:10:14
 * @since
 * @version
 */
public interface RoleResourceService extends BaseService<RoleResource> {

	/**
	 * 
	 * <p>根据ROLE_ID查询角色关联的所有资源</p> 
	 * @author Administrator
	 * @date 2014-4-29 下午5:54:34
	 * @param roleId
	 * @return
	 * @see
	 */
	public List<RoleResource> getByRoleId(String roleId);

	/**
	 * <p>根据页面信息更新角色资源关系</p> 
	 * @author QiuYangjun
	 * @date 2014-4-30 上午11:03:30
	 * @param dto
	 * @see
	 */
	public void saveRoleResource(RoleResourceTreeDto dto);

	/**
	 * <p>根据资源ID删除角色资源关系</p> 
	 * @author QiuYangjun
	 * @date 2014-5-27 上午10:08:53
	 * @param resourceId
	 * @see
	 */
	public void deleteByResourceId(String resourceId);
	/**
	 * 
	 * <p>根据角色ID删除角色资源关系</p> 
	 * @author leiwei
	 * @date 2014-7-17 上午9:45:45
	 * @param roleId
	 * @see
	 */
	public void deleteByRoleId(String roleId);
}
