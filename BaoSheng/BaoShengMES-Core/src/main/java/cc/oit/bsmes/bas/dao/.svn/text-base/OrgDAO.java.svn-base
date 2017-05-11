package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.Org;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;

public interface OrgDAO extends BaseDAO<Org> {

	public List<Org> getByParentId(String parentId);

	public Org getOrgByName(String orgCode);

	public Org checkOrgCodeUnique(String orgCode);

	public String getByParentCode(String parentCode);
	
	/**
	 * 获取组织机构：封装top组织，最顶层的机构代码
	 * 
	 * @param orgCode 组织机构代码
	 * */
	public Org getByCode(String orgCode);

}
