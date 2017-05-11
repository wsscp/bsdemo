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
package cc.oit.bsmes.bas.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.dao.EmployeeDAO;
import cc.oit.bsmes.bas.dao.RoleDAO;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dto.MethodReturnDto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2013-12-11 下午1:14:44
 * @since
 * @version
 */
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements
		EmployeeService {
	
	@Resource
	private EmployeeDAO employeeDAO;
	@Resource
	private RoleDAO roleDAO;

	@Override
	public Employee getByUserCode(String userCode){
		Employee findParams = new Employee();
		findParams.setUserCode(userCode);
		return employeeDAO.getOne(findParams);
	}

	@Override
	public List<Employee> getByName(String name,String status) {
		List<Employee> employees=null;
		if(StringUtils.equals("0", status)){
			employees=employeeDAO.getByName(name);
		}else{
			List<Role> list=roleDAO.getByName(name);
			employees=new ArrayList<Employee>();
			for(Role role:list){
				Employee employee=new Employee();
				employee.setId(role.getId());
				employee.setUserCode(role.getCode());
				employee.setName(role.getName());
				employees.add(employee);
			}
		}
		return employees;
	}

	@Override
	public List<Employee> getEmployee(String name) {
		return employeeDAO.getEmployee(name);
	}


    @Override
	public List<Employee> findNotInUser(String query) {
		return employeeDAO.findNotInUser(query);
    }

    @Override
    public JSONArray getOnWorkUserByEquip(String[] equipCodes, String orgCode) {
        JSONArray array = new JSONArray();
        for(String equipCode:equipCodes){
            JSONObject result = new JSONObject();
            result.put("Code",equipCode);
            List<Employee> employees = employeeDAO.getOnWorkUserByEquip(equipCode, orgCode);
            JSONArray subArray = new JSONArray();
            for(Employee employee:employees){
                JSONObject subResult = new JSONObject();
                subResult.put("EmplName",employee.getName());
                subResult.put("EmplCode",employee.getUserCode());
                subArray.add(subResult);
            }
            result.put("Employee",subArray);
            array.add(result);
        }
        return array;
    }

    /**
     * 新增员工
     * @return
     */
	public MethodReturnDto saveEmployee(Employee findParams) {
		Employee tmp = new Employee();
		tmp.setUserCode(findParams.getUserCode());//直接从数据库获取员工工号，并设置
		tmp.setOrgCode(findParams.getOrgCode());//设置部门同上
		if (this.getByObj(tmp).size() > 0) {//员工表查到数据，是否已经存在工号，也就会导致是否会大于0
			return new MethodReturnDto(false, "用户工号已经存在");
		}
		employeeDAO.insert(findParams);
		return new MethodReturnDto(true);
	}
    
    /**
     * 更新员工
     * @return
     */
	public MethodReturnDto updateEmployee(Employee findParams) {
//		在XML配置文件中查询，数量是否大于0
		if (employeeDAO.hasUserCodeForUpdate(findParams) > 0) {
			return new MethodReturnDto(false, "用户工号已经存在");
		}
		employeeDAO.update(findParams);
		return new MethodReturnDto(true);
	}

	/**
	 *
	 * @param userCode
	 * @param equipCode
	 * @return
	 */
	@Override
	public Employee validUserBZZPermissions(String userCode, String equipCode) {
		return employeeDAO.validUserBZZPermissions(userCode,equipCode);
	}
	/**
	 *
	 * @param userCode
	 * @param equipCode
	 * @return
	 */
	@Override
	public Employee validUserEquipPermissions(String userCode, String equipCode) {
		return employeeDAO.validUserEquipPermissions(userCode,equipCode);
	}
	@Override
	public List<Employee> getEventAccepter(String equipCode) {
		return employeeDAO.getEventAccepter(equipCode);
	}
}
