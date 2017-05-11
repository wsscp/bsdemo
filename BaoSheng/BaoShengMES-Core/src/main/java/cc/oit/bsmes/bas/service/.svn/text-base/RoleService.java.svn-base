package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.common.service.BaseService;

import java.util.List;

public interface RoleService extends BaseService<Role>{

	List<Role> getByOrgCode(String orgCode);

	/**
	 * <p>根据用户获取用户所对应的角色</p> 
	 * @author QiuYangjun
	 * @date 2014-5-8 下午1:58:44
	 * @param userId
	 * @return 
	 * @see
	 */
	List<Role> getByUserId(String userId);

    public List<Role> getByUserCode(String userCode);

	Role getByCode(String code, String orgCode);
}
