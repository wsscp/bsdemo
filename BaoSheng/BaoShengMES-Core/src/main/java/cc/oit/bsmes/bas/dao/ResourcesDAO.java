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

import cc.oit.bsmes.bas.model.Resources;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2013-12-12 下午12:03:54
 * @since
 * @version
 */
public interface ResourcesDAO extends BaseDAO<Resources>{
	
	public List<Resources> getByParentId(String parentId);
	/**
	 * <p>根据福节点id统计子节点数量</p> 
	 * @author QiuYangjun
	 * @date 2014-3-24 下午3:44:41
	 * @param parentId
	 * @return
	 * @see
	 */
	public int countByParentId(String parentId);
	
	/**
	 * 
	 * <p>根据子节点ID查找父节点信息</p> 
	 * @author QiuYangjun
	 * @date 2014-4-30 下午1:52:02
	 * @param id
	 * @return
	 * @see
	 */
	public Resources getParentById(String id);

	/**
	 * 
	 * <p>根据角色ID列表获取资源信息</p> 
	 * @author QiuYangjun
	 * @date 2014-5-8 下午2:38:23
	 * @param roleIds
	 * @return
	 * @see
	 */
	public List<Resources> getByRoleIds(List<String> roleIds);
	/**
	 * <p>更新菜单顺序</p> 
	 * @author QiuYangjun
	 * @date 2014-5-23 上午11:14:52
	 * @param id
	 * @param seq
	 * @see
	 */
	public void updateSeqById(String id, Integer seq);
	/**
	 * <p>根据parentId查询子菜单</p> 
	 * @author QiuYangjun
	 * @date 2014-5-27 下午3:51:48
	 * @param parentId
	 * @return
	 * @see
	 */
	public List<Resources> getMenuByParentId(String parentId);
}
