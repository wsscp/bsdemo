package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class EmployeeDAOTest extends BaseTest{
	
	@Resource
	private EmployeeDAO employeeDAO;

	@Test
	public void testGetOne() {
		Employee findParams = new Employee();
		findParams.setUserCode("131002");
		Employee employee = employeeDAO.getOne(findParams);
		//assertEquals(employee.getName(), "5694735452");
	}

}
