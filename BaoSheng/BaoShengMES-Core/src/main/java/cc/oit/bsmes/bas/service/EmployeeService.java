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
package cc.oit.bsmes.bas.service;

import java.util.List;

import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.dto.MethodReturnDto;

import com.alibaba.fastjson.JSONArray;

/**
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2013-12-11 下午1:10:14
 * @since
 * @version
 */
public interface EmployeeService extends BaseService<Employee>{

	/**
	 * <p>通过员工编号获取员工详细信息</p> 
	 * @author QiuYangjun
	 * @date 2013-12-17 上午10:20:12
	 * @param userCode
	 * @return
	 * @see
	 */
	public Employee getByUserCode(String userCode);
	
	/**
	 * 
	 * <p>通过员工姓名模糊查询员工信息</p> 
	 * @author leiwei
	 * @date 2014-2-14 上午10:54:57
	 * @param name
	 * @return
	 * @see
	 */
	public List<Employee> getByName(String name,String status);

	public List<Employee> getEmployee(String name);

    /**
     * 查询未注册的员工集合
     * @return
     */
	public List<Employee> findNotInUser(String query);

    public JSONArray getOnWorkUserByEquip(String[] equipCodes,String orgCode);
	
    /**
     * 新增员工
     * @return
     */
    public MethodReturnDto saveEmployee(Employee findParams);
    
    /**
     * 更新员工
     * @return
     */
	public MethodReturnDto updateEmployee(Employee findParams);

	/**
	 * 根据用户和设备编号查询用户是否班组长并且该用户是否有设备权限  在终端班组长调整订单设备时用到
	 * @param userCode
	 * @param equipCode
	 * @return
	 */
	Employee validUserBZZPermissions(String userCode,String equipCode);
	
	List<Employee> getEventAccepter(String equipCode);

	public Employee validUserEquipPermissions(String userCode, String equipCode);
}
