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
package cc.oit.bsmes.bas.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import cc.oit.bsmes.common.constants.WebConstants;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.Org;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.bas.service.OrgService;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.util.JSONArrayUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.wip.dto.MethodReturnDto;

import com.alibaba.fastjson.JSON;

/**
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2013-12-11 下午1:09:22
 * @since
 * @version
 */
@Controller
@RequestMapping("/bas/employeeTest")
public class EmployeeTestController {
	@Resource
	private EmployeeService employeeService;
	@Resource
	private OrgService orgService;
	@Resource
	private UserService userService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "bas");
		model.addAttribute("submoduleName", "employeeTest");
		return "bas.employeeTest";
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	@SuppressWarnings({ "rawtypes" })
	public TableView list(HttpServletRequest request, Employee findParams, @RequestParam int start, @RequestParam int limit) {
		// 查询
		Org org = orgService.getByCode(employeeService.getOrgCode());
		if (!WebConstants.ROOT_ID.equals(org.getParentCode())) {
			findParams.setTopOrgCode(org.getParentCode());
		}
		List list = employeeService.find(findParams, start, limit, null);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(employeeService.count(findParams));
		return tableView;
	}
	
	
	
	
	 

	/**
	 * <p>
	 * 下拉框组件
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-7-15 11:20:48
 	 * 
	 * 在添加按钮出现的界面有人员资质的部分
	 * */
	@ResponseBody
	@RequestMapping(value = "certificateCombo", method = RequestMethod.GET)
	public List<DataDic> getWorkOrderStatusCombo() {
		//DATA_EMPLOYEE_CERTIFICATE("人员资质"),
		//TermsCode条件代码
		List<DataDic> result = StaticDataCache.getByTermsCode(TermsCodeType.DATA_EMPLOYEE_CERTIFICATE.name());
		return result;
	}

	/**
	 * 所属部门，在添加按钮之后会有，所属部门和人员资质的下拉框组件
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getOrgCode", method = RequestMethod.GET)
	public List<Org> getOrgCode() {
		List<Org> result = orgService.getAll();//封装底层提供，查询所有数据，在泛型集合List中
		return result;
	}

	private void setname(String string) {
		// TODO Auto-generated method stub

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody Employee employee) {
		//随机ID
		Employee e = employeeService.getById(employee.getId());
//		查找org数据库orgCode设置employee表中TopOrgCode
		setTopOrgCode(employee);
//		查找是否大于0，看工号是否已经存在
		employeeService.updateEmployee(employee);
//		employee.getUserCode()，前台code，即员工code，也就是当前修改code，e.getUserCode则是修改之前的，也就是数据库code
//		如果工号不重复
		if (!employee.getUserCode().equals(e.getUserCode())) {
//			查找用户，看输入的员工工号（usercode）是否有对应员工
			User user = userService.checkUserCodeUnique(e.getUserCode());
			if (user != null) {
//				设置员工工号
				user.setUserCode(employee.getUserCode());
				user.setModifyUserCode(SessionUtils.getUser().getUserCode());
				userService.updateUserCode(user);
			}
		}
		
//		添加到list集合
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(employee);
		return updateResult;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody Employee employee) {
		setTopOrgCode(employee);
		employeeService.saveEmployee(employee);

//		添加到list集合
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(employee);
		return updateResult;
	}

//	设置TopOrgCode
	private void setTopOrgCode(Employee employee) {
//		orgService.getByCode在xml文件中有查询语句
		Org org = orgService.getByCode(employee.getOrgCode());//orgCode在编辑里面会有，也就是所属部门
		if (StringUtils.isBlank(org.getParentCode()) || WebConstants.ROOT_ID.equals(org.getOrgCode())) {
			employee.setTopOrgCode(WebConstants.ROOT_ID);//-1
		} else {
			employee.setTopOrgCode(org.getOrgCode());//数据库数据
		}
	}
}
