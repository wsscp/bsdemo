package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.OrgDAO;
import cc.oit.bsmes.bas.model.Org;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.OrgService;
import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class OrgServiceImpl extends BaseServiceImpl<Org> implements OrgService {

	@Resource private OrgDAO orgDao;

	@Override
	public List<Org> getByParentId(String parentId) {
		return orgDao.getByParentId(parentId);
	}

	@Override
	public Org getByName(String name) {
		return orgDao.getOrgByName(name);
	}

	@Override
	public Org getByCode(String code) {
		return orgDao.getByCode(code);
	}
	@Override
	public Org checkOrgCodeUnique(String orgCode) {
		return orgDao.checkOrgCodeUnique(orgCode);
	}
	@Override
	public void insert(Org org){
		org.setId(UUID.randomUUID().toString());
		User user = SessionUtils.getUser();
		org.setCreateUserCode(user.getUserCode());
		org.setModifyUserCode(user.getUserCode());
		String parentId=orgDao.getByParentCode(org.getParentCode());
		org.setParentId(StringUtils.isNotBlank(parentId)?parentId:"-1");
		orgDao.insert(org);
	}

	@Override
	public void update(Org org) throws DataCommitException {
		String parentId=orgDao.getByParentCode(org.getParentCode());
		org.setParentId(StringUtils.isNotBlank(parentId)?parentId:"-1");
		orgDao.update(org);
	}
}

