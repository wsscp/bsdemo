/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */ 
package cc.oit.bsmes.bas.dao;

import java.util.List;

import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.common.dao.BaseDAO;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2013-12-11 下午1:28:23
 * @since
 * @version
 */
public interface EmployeeDAO extends BaseDAO<Employee>{
	public Employee getByUserCode(String userCode);  
	public List<Employee> getByName(String name); 
	public List<Employee> getByNameSMS(String name); 
	public List<Employee> getEmployeeByRoleId(String roleId); 
	public List<Employee> getEmployeeByUserCodes(List<String> codes);
	public List<Employee> getEmployee(String name); 
	public List<Employee> findNotInUser(String query);
    public List<Employee> getOnWorkUserByEquip(String equipCode,String orgCode);

	/**
	 * 变更员工时判断员工号是否已经存在
	 */
	public Integer hasUserCodeForUpdate(Employee findParams);

	/**
	 * 验证用户是否有班组长权限
	 * @param userCode
	 * @param equipCode
	 * @return
	 */
	Employee validUserBZZPermissions(String userCode,String equipCode);
	
	List<Employee> getEventAccepter(String equipCode);
	public Employee validUserEquipPermissions(String userCode, String equipCode);

}
