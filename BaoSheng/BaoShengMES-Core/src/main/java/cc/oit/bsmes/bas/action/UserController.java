package cc.oit.bsmes.bas.action;


import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cc.oit.bsmes.bas.dao.AttachmentDAO;
import cc.oit.bsmes.bas.dao.EmployeeResumeDAO;
import cc.oit.bsmes.bas.model.Attachment;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.EmployeeFamilyInfo;
import cc.oit.bsmes.bas.model.EmployeeResume;
import cc.oit.bsmes.bas.model.Resume;
import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.AttachmentService;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.bas.service.RoleService;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 用户管理
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2013-12-16 上午9:24:04
 * @since
 * @version
 */
@Controller
@RequestMapping("/bas/user")
public class UserController {
	
	@Resource
	private UserService userService;
	@Resource
	private AttachmentDAO attachmentDAO;
	@Resource
	private EmployeeResumeDAO employeeResumeDAO;
	@Resource
	private RoleService roleService;
	@Resource
	private AttachmentService attachmentService;
	
	@Resource
	private EmployeeService employeeService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model,HttpServletRequest request) {
		model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "user");
        String orgCode = request.getParameter("orgCode");
        String orgName = request.getParameter("orgName");
        model.addAttribute("orgCode", orgCode);
        model.addAttribute("orgName", orgName);
        return "bas.user";
    }
	
    @RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(@ModelAttribute User param, @RequestParam(required=false) Integer start, @RequestParam(required=false) Integer limit, @RequestParam(required = false) String sort) {
    	if(StringUtils.isNotBlank(param.getUserCode())){
			param.setUserCode("%"+param.getUserCode()+"%");
		}

		if (StringUtils.isNotBlank(param.getName())) {
			param.setName("%"+param.getName()+"%");
		}
		
		if (StringUtils.isNotBlank(param.getRole())) {
			param.setRole("%"+param.getRole()+"%");
		}
		
		if (StringUtils.isNotBlank(param.getOrgCode())) {
			param.setOrgCode("%"+param.getOrgCode()+"%");
		}
        List<User> list = userService.find(param, start, limit, null);
        for(User user : list){
        	List<Role> roles = roleService.getByUserId(user.getId());
        	user.setOrgName(userService.getOrgName(user.getOrgCode()));
        	String r = "";
        	if(roles != null){
	        	for(Role role : roles){
	        		r = r + role.getName() + "</br>";
	        	}
	        	user.setRole(r);
        	}
        }
        TableView tableView = new TableView();
        tableView.setRows(list);
        tableView.setTotal(userService.count(param));
        return tableView;
    }
	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		User user =  JSON.parseObject(jsonText, User.class);
		userService.insert(user);
		user = userService.getById(user.getId());
		updateResult.addResult(user);
		return updateResult;
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		User user =  JSON.parseObject(jsonText, User.class);
		user.setModifyUserCode(SessionUtils.getUser().getUserCode());
		userService.update(user);
		updateResult.addResult(user);
		return updateResult;
	}

	/**
	 *
	 * 验证员工号在用户表和员工表中是否存在
	 * @author JinHanyun
	 * @date 2013-12-17 下午2:12:31
	 * @return
	 * @throws ClassNotFoundException
	 * @see
	 */
	@RequestMapping(value="checkUserCodeExists/{userCode}",method = RequestMethod.GET)
	@ResponseBody
	public boolean checkUserCodeExists(@PathVariable String userCode) throws ClassNotFoundException {
		Employee employee = employeeService.getByUserCode(userCode);
		return employee != null;
	}

	@RequestMapping(value = "findEmployeeNotInUser/{query}", method = RequestMethod.GET)
    @ResponseBody
	public List<Employee> findEmployeeNotInUser(@PathVariable String query) {
		if (StringUtils.equalsIgnoreCase(WebConstants.ROOT_ID, query)) {
			query = "";
		}
		return employeeService.findNotInUser(query);
    }
	
	@RequestMapping(value = "/importUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject importUserInfo(HttpServletRequest request, @RequestParam MultipartFile importFile)
            throws Exception {
		Workbook workbook = null;
		JSONObject result = new JSONObject();
		try {
            workbook = new XSSFWorkbook(importFile.getInputStream());
        } catch (Exception e) {
            workbook = new HSSFWorkbook(importFile.getInputStream());
        }
		//人员信息
		Sheet sheet = workbook.getSheetAt(0);
		if(sheet == null){
			result.put("message", "导入文件sheet，请命名为'人员信息'");
            result.put("success", false);
		}else{
			result = userService.importResume(sheet,result);
		}
		//家庭信息
		Sheet sheet1 = workbook.getSheetAt(1);
		if(sheet1 == null){
			result.put("message", "导入文件sheet，请命名为'家庭信息'");
            result.put("success", false);
		}else{
			result = userService.importUserFamilyInfo(sheet1,result);
		}
		return result;
	}
	
	@RequestMapping(value = "/downLoadUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject downLoadUserInfo(HttpServletResponse response ,@RequestParam String userCode)
            throws Exception {
		JSONObject result = new JSONObject();
		Employee e = employeeService.getByUserCode(userCode);
		String resumePath = e.getResumePath();
		if(resumePath == null){
			result.put("message", "未上传简历");
			result.put("success", false);
			return result;
		}
		Attachment findParams = new Attachment();
		findParams.setRefId(resumePath);
		List<Attachment> attachments = attachmentDAO.get(findParams);
		Attachment attachment = attachments.get(0);
		String fileName = e.getUserCode() + "." + attachment.getContentType();
		response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8")+";charset=UTF-8");
		attachmentService.downLoadOne(response.getOutputStream(), resumePath);
		result.put("message", "下载简历成功");
		result.put("success", true);
		response.setContentType("text/html");
		return result;
	}
	
	@RequestMapping(value="/getAllOrgCode", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getAllOrgCode() {
		return userService.getAllOrg();
	}
	
	@RequestMapping(value = "/getResumeInfo",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getResumeInfo(@RequestParam String userCode){
		JSONObject result = new JSONObject();
		User user = userService.checkUserCodeUnique(userCode);
		Resume resume = userService.getResumeInfo(userCode);
		if(resume==null){
			result.put("success", false);
			result.put("message", "未上传简历.");
			return result;
		}
		resume.setUserName(user.getName());
		result.put("resume", resume);
		return result;
	}
	
	@RequestMapping(value = "/getFamilyInfo",method = RequestMethod.GET)
	@ResponseBody
	public List<EmployeeFamilyInfo> getFamilyInfo(@RequestParam String userCode){
		List<EmployeeFamilyInfo> list = userService.getFamilyInfo(userCode);
		return list;
	}
	
	@RequestMapping(value = "/addEmployeeResume",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addEmployeeResume(@RequestParam String userCode,@RequestParam String recordDate,@RequestParam String recordDetail){
		JSONObject result = new JSONObject();
		EmployeeResume employeeResume = new EmployeeResume();
		employeeResume.setUserCode(userCode);
		employeeResume.setRecordDate(recordDate);
		employeeResume.setRecordDetail(recordDetail);
		employeeResumeDAO.insert(employeeResume);
		result.put("success", true);
		result.put("message", "添加成功");
		return result;
	}
	
	@RequestMapping(value = "/getUserResumeInfo",method = RequestMethod.GET)
	@ResponseBody
	public List<EmployeeResume> getUserResumeInfo(@RequestParam String userCode){
		List<EmployeeResume> list = employeeResumeDAO.getUserResumeInfo(userCode);
		return list;
	}
	
	@RequestMapping(value = "/deleteEmployeeResume",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteEmployeeResume(@RequestParam String id){
		JSONObject result = new JSONObject();
		employeeResumeDAO.delete(id);
		result.put("success", true);
		result.put("message", "删除成功");
		return result;
	}
}