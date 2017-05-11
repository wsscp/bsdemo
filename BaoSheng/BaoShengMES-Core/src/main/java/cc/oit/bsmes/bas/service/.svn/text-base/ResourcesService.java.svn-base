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

import cc.oit.bsmes.bas.model.Resources;
import cc.oit.bsmes.common.mybatis.Sort;
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
public interface ResourcesService extends BaseService<Resources> {

	/**
	 * <p>根据parentId查询子资源</p> 
	 * @author QiuYangjun
	 * @date 2014-3-24 下午3:41:11
	 * @param parentId
	 * @param start
	 * @param limit
	 * @param sortList
     * @return
	 * @see
	 */
	List<Resources> getByParentId(String parentId, int start, int limit, List<Sort> sortList);

	/**
	 * <p>根据parentId查询子资源</p> 
	 * @author QiuYangjun
	 * @date 2014-3-24 下午3:43:00
	 * @param parentId
	 * @return
	 * @see
	 */
	List<Resources> getByParentId(String parentId);

	/**
	 * <p>根据福节点id统计子节点数量</p> 
	 * @author QiuYangjun
	 * @date 2014-3-24 下午3:45:25
	 * @param parentId
	 * @return
	 * @see
	 */
	int countByParentId(String parentId);

	/**
	 * 
	 * <p>根据子节点ID查找父节点信息</p> 
	 * @author QiuYangjun
	 * @date 2014-4-30 下午1:52:02
	 * @param id
	 * @return
	 * @see
	 */
	Resources getParentById(String id);

	/**
	 * <p>根据角色ID列表获取资源信息</p> 
	 * @author Administrator
	 * @date 2014-5-8 下午2:40:19
	 * @param roleIds
	 * @return
	 * @see
	 */
	List<Resources> getByRoleIds(List<String> roleIds);

	/**
	 * <p>更新顺序</p> 
	 * @author QiuYangjun
	 * @date 2014-5-23 上午11:54:24
	 * @param resourcesList
	 * @return
	 * @see
	 */
	void updateResourceSeq(List<Resources> resourcesList);
	
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
	boolean hasChild(String parentId);

	/**
	 * <p>根据parentId查询子菜单</p> 
	 * @author QiuYangjun
	 * @date 2014-5-27 下午3:49:43
	 * @param parentId
	 * @return
	 * @see
	 */
	List<Resources> getMenuByParentId(String parentId);
}
