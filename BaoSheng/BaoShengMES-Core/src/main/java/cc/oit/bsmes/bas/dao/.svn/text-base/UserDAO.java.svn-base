package cc.oit.bsmes.bas.dao;

import java.util.List;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.dao.BaseDAO;

public interface UserDAO extends BaseDAO<User> {
	
	/**
	 *
	 * 验证用户是否存在
	 * @author JinHanyun
	 * @date 2013-12-17 上午9:40:51
	 * @param userCode
	 * @return
	 * @see
	 */
	public User getByUserCode(String userCode);

    public int updateByUserCode(String userCode,String supplementary);

	public int updateUserCode(User user);
	
	
	public String getOrgName(String orgCode);

	public List<User> getAllOrg();

}
