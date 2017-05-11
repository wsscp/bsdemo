package cc.oit.bsmes.bas.dao;


import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.common.dao.BaseDAO;

import java.util.List;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2013-12-16 上午9:24:51
 * @since
 * @version
 */
public interface RoleDAO extends BaseDAO<Role>{

 
	@Override
	public int count(Role role);
	
	@Override
	public Role getById(String id);

	public List<Role> getByOrgCode(String orgCode);

	/**
	 * <p>根据用户获取用户所对应的角色</p> 
	 * @author QiuYangjun
	 * @date 2014-5-8 下午2:01:12
	 * @param userId
	 * @return
	 * @see
	 */
	public List<Role> getByUserId(String userId);

	public List<Role> getByName(String name);

    public List<Role> getByUserCode(String userCode);

	public Role getByCode(String code, String orgCode);

}
