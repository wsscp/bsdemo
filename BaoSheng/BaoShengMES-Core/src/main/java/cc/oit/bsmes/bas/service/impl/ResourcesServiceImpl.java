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


import cc.oit.bsmes.bas.dao.ResourcesDAO;
import cc.oit.bsmes.bas.model.Resources;
import cc.oit.bsmes.bas.service.ResourcesService;
import cc.oit.bsmes.bas.service.RoleResourceService;
import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2013-12-11 下午1:14:44
 * @since
 * @version
 */
@Service
public class ResourcesServiceImpl extends BaseServiceImpl<Resources> implements ResourcesService {
	
	@Resource
	private ResourcesDAO resourceDAO;
	@Resource
	private RoleResourceService roleResourceService;
	@Override
	public List<Resources> getByParentId(String parentId) {
		return resourceDAO.getByParentId(parentId);
	}
	@Override
	public List<Resources> getByParentId(String parentId, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return getByParentId(parentId);
	}
	@Override
	public int countByParentId(String parentId) {
		return resourceDAO.countByParentId(parentId);
	}
	
	/**
	 * 
	 * <p>根据子节点ID查找父节点信息</p> 
	 * @author QiuYangjun
	 * @date 2014-4-30 下午1:53:33
	 * @param id
	 * @return 
	 * @see cc.oit.bsmes.bas.service.ResourcesService#getParentById(java.lang.String)
	 */
	@Override
	public Resources getParentById(String id){
		return resourceDAO.getParentById(id);
	}
	
	@Override
	public List<Resources> getByRoleIds(List<String> roleIds){
		return resourceDAO.getByRoleIds(roleIds);
	}

	@Override
	@Transactional(readOnly=false)
	public void updateResourceSeq(List<Resources> resourcesList){
		if(resourcesList!=null&&!resourcesList.isEmpty()){
			for(Resources r:resourcesList){
				resourceDAO.updateSeqById(r.getId(),r.getSeq());
			}
		}
	}

	/**
	 * <p>
	 * 判断当前菜单是否有子菜单
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-5-26 下午15:11:24
	 * @param parentId
	 * @return boolean
	 * @see
	 */
	@Override
	public boolean hasChild(String parentId) {
		List<Resources> resourcesList = resourceDAO.getByParentId(parentId);
		if (resourcesList != null && resourcesList.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * <p>根据ID删除资源 ,如果有子资源不允许删除</p> 
	 * @author Administrator
	 * @date 2014-5-27 上午9:45:52
	 * @param id 
	 * @see cc.oit.bsmes.common.service.impl.BaseServiceImpl#deleteById(java.lang.String)
	 */
	@Override		
	public void deleteById(String resourceId){
		int count = countByParentId(resourceId);
		if(count>0){
			throw new MESException("bas.resources.delete.hasChild");
		}else{
			roleResourceService.deleteByResourceId(resourceId);
			resourceDAO.delete(resourceId);
		}
	}
	/** 
	 * <p>根据parentId查询子菜单</p> 
	 * @author QiuYangjun
	 * @date 2014-5-27 下午3:50:51
	 * @param parentId
	 * @return 
	 * @see cc.oit.bsmes.bas.service.ResourcesService#getMenuByParentId(java.lang.String)
	 */
	@Override
	public List<Resources> getMenuByParentId(String parentId) {
		return resourceDAO.getMenuByParentId(parentId);
	}
}
