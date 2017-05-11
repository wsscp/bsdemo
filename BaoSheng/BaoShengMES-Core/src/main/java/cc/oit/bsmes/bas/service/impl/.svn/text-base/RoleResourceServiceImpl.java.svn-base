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
package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.RoleResourceDAO;
import cc.oit.bsmes.bas.dto.RoleResourceTreeDto;
import cc.oit.bsmes.bas.model.Resources;
import cc.oit.bsmes.bas.model.RoleResource;
import cc.oit.bsmes.bas.service.ResourcesService;
import cc.oit.bsmes.bas.service.RoleResourceService;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2013-12-11 下午1:14:44
 * @since
 * @version
 */
@Service
public class RoleResourceServiceImpl extends BaseServiceImpl<RoleResource>
		implements RoleResourceService {

	@Resource
	private RoleResourceDAO roleResourceDAO;
	@Resource
	private ResourcesService resourcesService;

	/**
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author QiuYangjun
	 * @date 2014-4-29 下午5:54:22
	 * @param roleId
	 * @return
	 * @see cc.oit.bsmes.bas.service.RoleResourceService#getByRoleId(java.lang.String)
	 */
	@Override
	public List<RoleResource> getByRoleId(String roleId) {
		RoleResource findParams = new RoleResource();
		findParams.setRoleId(roleId);
		return roleResourceDAO.get(findParams);
	}

	@Override
	@Transactional(readOnly = false)
	public void saveRoleResource(RoleResourceTreeDto dto) {
		RoleResource rr = getByRoleIdAndResourceId(dto.getRoleId(), dto.getId());
		if (rr == null) {
			rr = new RoleResource();
			rr.setResourceId(dto.getId());
			rr.setRoleId(dto.getRoleId());
			rr.setRoleAdvanced(dto.getRoleAdvanced());
			rr.setRoleCreate(dto.getRoleCreate());
			rr.setRoleDelete(dto.getRoleDelete());
			rr.setRoleEdit(dto.getRoleEdit());
			rr.setRoleQuery(dto.getRoleQuery());
			insert(rr);
		} else {
			rr.setRoleAdvanced(dto.getRoleAdvanced());
			rr.setRoleCreate(dto.getRoleCreate());
			rr.setRoleDelete(dto.getRoleDelete());
			rr.setRoleEdit(dto.getRoleEdit());
			rr.setRoleQuery(dto.getRoleQuery());
			if((rr.getRoleAdvanced()==null||!rr.getRoleAdvanced()) 
				&&(rr.getRoleCreate()==null||!rr.getRoleCreate())
				&&(rr.getRoleDelete()==null||!rr.getRoleDelete())
				&&(rr.getRoleEdit()==null||!rr.getRoleEdit())
				&&(rr.getRoleQuery()==null||!rr.getRoleQuery())){
				delete(rr);
			}else{
				update(rr);
			}
		}
		saveParentRoleResource(rr);
	}

	@Transactional(readOnly = false)
	public void saveParentRoleResource(RoleResource roleResource) {
		if (roleResource.getRoleAdvanced() || roleResource.getRoleCreate()
				|| roleResource.getRoleDelete() || roleResource.getRoleEdit()
				|| roleResource.getRoleQuery()) {
			Resources resources = resourcesService.getParentById(roleResource.getResourceId());
			if(resources==null||StringUtils.equalsIgnoreCase(resources.getId(),WebConstants.ROOT_ID)){
				return;
			}
			RoleResource rr = getByRoleIdAndResourceId(roleResource.getRoleId(),resources.getId());
			if(rr ==null){
				rr = new RoleResource();
				rr.setResourceId(resources.getId());
				rr.setRoleId(roleResource.getRoleId());
				rr.setRoleAdvanced(rr.getRoleAdvanced()?true:roleResource.getRoleAdvanced());
				rr.setRoleCreate(rr.getRoleCreate()?true:roleResource.getRoleCreate());
				rr.setRoleDelete(rr.getRoleDelete()?true:roleResource.getRoleDelete());
				rr.setRoleEdit(rr.getRoleEdit()?true:roleResource.getRoleEdit());
				rr.setRoleQuery(rr.getRoleQuery()?true:roleResource.getRoleQuery());
				insert(rr);
			}else{
				rr.setRoleAdvanced(rr.getRoleAdvanced()?true:roleResource.getRoleAdvanced());
				rr.setRoleCreate(rr.getRoleCreate()?true:roleResource.getRoleCreate());
				rr.setRoleDelete(rr.getRoleDelete()?true:roleResource.getRoleDelete());
				rr.setRoleEdit(rr.getRoleEdit()?true:roleResource.getRoleEdit());
				rr.setRoleQuery(rr.getRoleQuery()?true:roleResource.getRoleQuery());
				
				if((rr.getRoleAdvanced()==null||!rr.getRoleAdvanced())
					&&(rr.getRoleCreate()==null||!rr.getRoleCreate())
					&&(rr.getRoleDelete()==null||!rr.getRoleDelete())
					&&(rr.getRoleEdit()==null||!rr.getRoleEdit())
					&&(rr.getRoleQuery()==null||!rr.getRoleQuery())){
					delete(rr);
				}else{
					update(rr);
				}
			}
			if(StringUtils.equalsIgnoreCase(resources.getParentId(),WebConstants.ROOT_ID)){
				return;
			}
			saveParentRoleResource(rr);
		}
	}

	public RoleResource getByRoleIdAndResourceId(String roleId,
			String resourceId) {
		RoleResource findParams = new RoleResource();
		findParams.setRoleId(roleId);
		findParams.setResourceId(resourceId);
		return roleResourceDAO.getOne(findParams);
	}

	/** 
	 * <p>根据资源ID删除角色资源关系</p> 
	 * @author QiuYangjun
	 * @date 2014-5-27 上午10:09:20
	 * @param resourceId 
	 * @see cc.oit.bsmes.bas.service.RoleResourceService#deleteByResourceId(java.lang.String)
	 */
	@Override
	public void deleteByResourceId(String resourceId) {
		roleResourceDAO.deleteByResourceId(resourceId);
	}

	@Override
	public void deleteByRoleId(String roleId) {
		roleResourceDAO.deleteByRoleId(roleId);
	}
}
