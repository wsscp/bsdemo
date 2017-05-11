package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.RoleDAO;
import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.bas.service.RoleService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{

	@Resource
	private RoleDAO roleDAO;

	@Override
	public List<Role> getByOrgCode(String orgCode) {
		return roleDAO.getByOrgCode(orgCode);
	} 
	/** 
	 * <p>根据用户获取用户所对应的角色</p> 
	 * @author QiuYangjun
	 * @date 2014-5-8 下午1:59:20
	 * @param userId 
	 * @return 
	 * @see cc.oit.bsmes.bas.service.RoleService#getByUserId(java.lang.String)
	 */
	@Override
	public List<Role> getByUserId(String userId) {
		return roleDAO.getByUserId(userId);
	}


    @Override
    public List<Role> getByUserCode(String userCode) {
        return roleDAO.getByUserCode(userCode);
    }
	@Override
	public Role getByCode(String code, String orgCode) {
		return roleDAO.getByCode(code,orgCode);
	}
}
