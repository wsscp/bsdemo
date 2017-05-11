package cc.oit.bsmes.bas.employee.dao;

import cc.oit.bsmes.bas.dao.EmployeeDAO;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.junit.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class EmployeeDAOTest extends BaseTest {
	
	@Resource
    private EmployeeDAO employeeDAO;

	@Test
	public void testGetAll() {
//		userDAO.find(new User(), new User(), 1, 3);
		System.out.println(employeeDAO.getAll().size());
	}
	
	@Test
	public void testInsert() {
		for(int i=0;i<100;i++){
			Employee emp=new Employee();
			emp.setName(RandomStringUtils.randomNumeric(10));
			emp.setEmail(RandomStringUtils.randomNumeric(10));
			emp.setTelephone(RandomStringUtils.randomNumeric(11));
			emp.setUserCode(RandomStringUtils.randomNumeric(10));
			emp.setOrgCode(RandomStringUtils.randomNumeric(10));
			emp.setTopOrgCode(RandomStringUtils.randomNumeric(10));
			employeeDAO.insert(emp);
		}
		
	List<Employee>	list = employeeDAO.find(new Employee());
	System.out.println(list.size());
	}

}
