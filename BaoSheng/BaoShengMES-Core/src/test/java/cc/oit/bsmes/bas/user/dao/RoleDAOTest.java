package cc.oit.bsmes.bas.user.dao;

import cc.oit.bsmes.bas.dao.RoleDAO;
import cc.oit.bsmes.bas.dao.UserDAO;
import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

public class RoleDAOTest  extends BaseTest {
	@Resource
    private UserDAO userDAO;
	@Resource
    private RoleDAO roleDAO;
	@Test
	@Rollback(false)
	public void testInsert() {
//		User user = new User();
//		user.setPassword("6666666");
//		user.setUserCode("ls1");
//		user.setName("ls1");
//		user.setStatus("1");
//		System.out.println(userDAO.insert(user));
//		System.out.println(user.getId());
//		
		Role role=new Role();
		role.setOrgCode("1");
		role.setName("生产3");
		role.setDescription("生产3");
		roleDAO.insert(role);
	}
	
}
