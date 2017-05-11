package cc.oit.bsmes.bas.user.dao;

import cc.oit.bsmes.bas.dao.UserDAO;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

public class UserDAOTest extends BaseTest {
	
	@Resource
    private UserDAO userDAO;

	@Test
	public void testGetAll() {
		logger.info(userDAO.getAll().size() + "");
	}
	
	@Test
	public void testInsert() {
//		User user = new User();
//		user.setName("fe");
//		System.out.println(userDAO.insert(user));
//		System.out.println(user.getId());
	}
	
	@Test
	@Rollback(false)
	public void testCountByUserCode(){
		User user = new User();
		user.setId("0fc0699f-6071-48c9-a11b-5ccf07842be1");
		user.setCreateUserCode("22");
		user.setModifyUserCode("aa");
		userDAO.update(user);




		
	}

}
