package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.Org;
import cc.oit.bsmes.common.service.BaseService;

import java.util.List;

public interface OrgService extends BaseService<Org> {

	List<Org> getByParentId(String parentId);
	Org getByName(String name);
	Org getByCode(String code);
	Org checkOrgCodeUnique(String orgCode);
	
}
