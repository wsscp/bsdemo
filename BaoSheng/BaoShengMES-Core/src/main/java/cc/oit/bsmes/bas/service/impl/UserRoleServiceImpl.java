package cc.oit.bsmes.bas.service.impl;

import cc.oit.bsmes.bas.dao.UserRoleDAO;
import cc.oit.bsmes.bas.model.UserRole;
import cc.oit.bsmes.bas.service.UserRoleService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-4-28 下午5:09:49
 * @since
 * @version
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole> implements UserRoleService {
	@Resource private UserRoleDAO userRoleDAO;
	
	@Override
	public List<UserRole> getByUserId(String userId, String orgCode) {
		Map<String ,Object> map=new HashMap<String ,Object>();
		map.put("userId", userId);
		map.put("orgCode", orgCode);
		return userRoleDAO.getByUserId(map);
	}

    @Override
    public void deleteByUserId(String userId) {
        userRoleDAO.deleteByUserId(userId);
    }
}
