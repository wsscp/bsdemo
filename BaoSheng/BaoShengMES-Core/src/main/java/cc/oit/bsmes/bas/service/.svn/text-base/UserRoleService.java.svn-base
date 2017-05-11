package cc.oit.bsmes.bas.service;

import cc.oit.bsmes.bas.model.UserRole;
import cc.oit.bsmes.common.service.BaseService;

import java.util.List;
/**
 * 
 * 用户角色关联表
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-4-28 下午5:07:53
 * @since
 * @version
 */
public interface UserRoleService extends BaseService<UserRole> {
	
	/**
	 * 
	 * <p>根据用户ID获取该用户下的角色</p> 
	 * @author leiwei
	 * @date 2014-4-28 下午5:08:08
	 * @param userId
	 * @param orgCode
	 * @return
	 * @see
	 */
	List<UserRole> getByUserId(String userId,String orgCode);

    void deleteByUserId(String userId);
}
