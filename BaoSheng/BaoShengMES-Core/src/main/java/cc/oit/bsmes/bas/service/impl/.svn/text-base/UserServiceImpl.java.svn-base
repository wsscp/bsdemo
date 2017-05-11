package cc.oit.bsmes.bas.service.impl;

import java.security.MessageDigest;
import java.util.List;

import cc.oit.bsmes.bas.dao.EmployeeFamilyInfoDAO;
import cc.oit.bsmes.bas.dao.ResumeDAO;
import cc.oit.bsmes.bas.dao.UserDAO;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.model.EmployeeFamilyInfo;
import cc.oit.bsmes.bas.model.Resume;
import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.bas.service.RoleService;
import cc.oit.bsmes.bas.service.UserRoleService;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	
	@Resource
	private UserDAO userDAO;
	@Resource
	private EmployeeService employeeService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private RoleService roleService;
	
	@Resource
	private ResumeDAO resumeDAO;
	@Resource
	private EmployeeFamilyInfoDAO familyInfoDAO;

	@Override
	public User checkUserCodeUnique(String userCode) {
        return userDAO.getByUserCode(userCode);
	}

	/** 
	 * <p>用户登录</p> 
	 * @author QiuYangjun
	 * @date 2014-5-6 下午4:05:54
	 * @param loginName
	 * @param password
	 * @return 
	 * @see cc.oit.bsmes.bas.service.UserService#login(java.lang.String, java.lang.String)
	 */
	@Override
	public User login(String loginName, String password) {
		if (!StringUtils.isEmpty(loginName) && !StringUtils.isEmpty(password)) {
			User user = checkUserCodeUnique(loginName);
			if ((user != null) && StringUtils.equals(password,user.getPassword())) {
				return user;
			}
		}
		return null;
	}
	
	/**
	 * <p>用户登录后,读取用户角色,用户资源</p> 
	 * @author QiuYangjun
	 * @date 2014-5-8 上午11:50:09
	 * @param user
	 * @param request
	 * @param response
	 * @see cc.oit.bsmes.bas.service.UserService#userLoginSuccess(User, HttpServletRequest, HttpServletResponse)
	 */
	@Override
	public boolean userLoginSuccess(User user,HttpServletRequest request,HttpServletResponse response){
		Employee employee=employeeService.getByUserCode(user.getUserCode());
		if(employee!=null){
			user.setOrgCode(employee.getTopOrgCode());
		}
		
		Cookie cookie = new Cookie(WebConstants.USER_SESSION_KEY, user.getUserCode());
		cookie.setMaxAge(WebConstants.COOKIES_MAX_AGE);
        cookie.setPath("/");
		response.addCookie(cookie);
		HttpSession session = request.getSession();
		session.setAttribute(WebConstants.USER_SESSION_KEY, user);
		return SessionUtils.login(session);
	}

	/**
	 * <p>
	 * 用户权限验证 是否组长级别
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-5-22 下午17:33:32
	 * @param userCode
	 * @param userCode
	 * @return
	 * @see cc.oit.bsmes.bas.service.UserService#skipOrderValid(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean isGroupUser(String userCode) {
		
		if (StringUtils.isNotBlank(userCode)) {
			User user = checkUserCodeUnique(userCode);
			if (user != null) {
				
				// 暂时直接返回true，后期对组织重新处理
				return true;
				
				/*
				// TODO 遍历用户角色，小组长以上具有次权限
				List<Role> roleList = roleService.getByUserId(user.getId());
				if (null != roleList) {
					for (Role role : roleList) {
						if (WebConstants.ROLE_TYPE_GROUPLEADER.equals(role
								.getOrgCode())) {
							return true;
						}
					}
				}
				return true;
				*/
			}
		}
		return false;
		
		
	}
	
	public boolean isPauseAuthority(User user){
		List<Role> roleList = roleService.getByUserId(user.getId());
		if (null != roleList) {
			for (Role role : roleList) {
				if (WebConstants.PAUSE_ROLE.equals(role
						.getCode())) {
					return true;
				}
			}
		}
		return false;
	}

    @Override
    public void deleteById(String id) throws DataCommitException {
        userRoleService.deleteByUserId(id);
        userDAO.delete(id);
    }

    @Override
    public int updateByUserCode(String userCode, String supplementary) {
        return userDAO.updateByUserCode(userCode,supplementary);
    }

	@Override
	public User validate(String userCode, String password) {
		// TODO Auto-generated method stub
		if (!StringUtils.isEmpty(userCode) && !StringUtils.isEmpty(password)) {
			User user = checkUserCodeUnique(userCode);
			
			if ((user != null) && StringUtils.equals(password,user.getPassword())) {
				return user;
			}
		}
		return null;
	}

	@Override
	public int updateUserCode(User user) {
		// TODO Auto-generated method stub
		return userDAO.updateUserCode(user);
	}

	@Override
	public String getOrgName(String orgCode) {
		// TODO Auto-generated method stub
		return userDAO.getOrgName(orgCode);
	}

	@Override
	public List<User> getAllOrg() {
		// TODO Auto-generated method stub
		return userDAO.getAllOrg();
	}

	@Override
	public JSONObject importResume(Sheet sheet, JSONObject result) {
		// TODO Auto-generated method stub
		int maxRow = sheet.getLastRowNum();
		for(int i=1;i<=maxRow;i++){
			Resume resume = new Resume();
			Row row = sheet.getRow(i);
			//获取身份证号码
			String idNumbers = row.getCell(6).getStringCellValue();
			if(StringUtils.isBlank(idNumbers)){
				result.put("success", false);
				result.put("message", "第" + (i + 1) + "行身份证号不能为空");
				return result;
			}else{
				resume.setIdNumbers(idNumbers);
				//截取身份证号码后八位作为用户的userCode，若身份证号码有字母，则向前推一位
				String userCode="";
				if(idNumbers.matches("[a-zA-Z]")){
					userCode = idNumbers.substring(idNumbers.length()-9, idNumbers.length()-1);
				}else{
					userCode = idNumbers.substring(idNumbers.length()-8);
				}
				User user = userDAO.getByUserCode(userCode);
				if(user == null){
					continue;
				}
				Resume param = resumeDAO.getOne(resume);
				if(param != null){
					resumeDAO.delete(param.getId());
				}
				resume.setUserCode(userCode);
			}
			//政治面貌
			resume.setPoliticalClimate(row.getCell(2).getStringCellValue());
			//籍贯
			resume.setOriginPlace(row.getCell(3).getStringCellValue());
			//出生地
			resume.setBirthPlace(row.getCell(4).getStringCellValue());
			//性别
			resume.setGender(row.getCell(5).getStringCellValue());
			//出生日期
			resume.setBirthDate(row.getCell(7).getStringCellValue());
			//入职日期
			resume.setEntryDate(row.getCell(8).getStringCellValue());
			//婚姻状况
			resume.setMaritalStatus(row.getCell(9).getStringCellValue());
			//户口性质
			resume.setAccountProperties(row.getCell(10).getStringCellValue());
			//家庭住址
			resume.setHomeAddress(row.getCell(11).getStringCellValue());
			//手机号码
			resume.setPhoneNumber(row.getCell(12).getStringCellValue());
			//入学日期
			resume.setAdmissionDate(row.getCell(13).getStringCellValue());
			//毕业日期
			resume.setGraduationDate(row.getCell(14).getStringCellValue());
			//学校
			resume.setSchool(row.getCell(15).getStringCellValue());
			//是否航空院校
			resume.setIsAviationCollege(row.getCell(16).getStringCellValue());
			//学历
			resume.setEducation(row.getCell(17).getStringCellValue());
			//学位
			resume.setDegree(row.getCell(18).getStringCellValue());
			//学习方式
			resume.setStudyWay(row.getCell(19).getStringCellValue());
			//备注
			resume.setRemarks(row.getCell(20).getStringCellValue());
			//插入
			resumeDAO.insert(resume);
		}
		result.put("success", true);
		result.put("message", "导入成功..");
		return result;
	}

	@Override
	public Resume getResumeInfo(String userCode) {
		// TODO Auto-generated method stub
		Resume param = new Resume();
		param.setUserCode(userCode);
		return resumeDAO.getOne(param);
	}

	@Override
	public JSONObject importUserFamilyInfo(Sheet sheet1, JSONObject result) {
		// TODO Auto-generated method stub
		int maxRow = sheet1.getLastRowNum();
		for(int i=1;i<=maxRow;i++){
			Row row = sheet1.getRow(i);
			EmployeeFamilyInfo familyInfo = new EmployeeFamilyInfo();
			//员工编码
			String userCode = this.getMergedRegionValue(sheet1, i, 1);
			if(StringUtils.isEmpty(userCode)){
				result.put("success", false);
				result.put("message", "第" + (i + 1) + "行工号不能为空");
				continue;
			}else{
				User user = this.checkUserCodeUnique(userCode);
				if(user == null){
					continue;
				}else{
					familyInfo.setUserCode(userCode);
				}
			}
			//与员工关系
			familyInfo.setRelationShip(row.getCell(3).getStringCellValue());
			//家庭成员姓名
			familyInfo.setName(row.getCell(4).getStringCellValue());
			//出生日期
			familyInfo.setBirthDate(row.getCell(5).getStringCellValue());
			//工作单位
			familyInfo.setWorkUnit(row.getCell(6).getStringCellValue());
			//联系方式
			familyInfo.setPhoneNumber(row.getCell(7).getStringCellValue());
			//性别
			familyInfo.setGender(row.getCell(8).getStringCellValue());
			familyInfoDAO.insert(familyInfo);
			result.put("success", true);
			result.put("message", "导入成功");
		}
		return result;
	}

	@Override
	public List<EmployeeFamilyInfo> getFamilyInfo(String userCode) {
		// TODO Auto-generated method stub
		EmployeeFamilyInfo findParams = new EmployeeFamilyInfo();
		findParams.setUserCode(userCode);
		return familyInfoDAO.find(findParams);
	}
	
	//获取合并单元格的值
	public String getMergedRegionValue(Sheet sheet,int row,int column){
		int sheetMergeCount = sheet.getNumMergedRegions();
		for(int i=0;i<sheetMergeCount;i++){
			CellRangeAddress ca = sheet.getMergedRegion(i);
			int firstColumn = ca.getFirstColumn();
			int lastColumn = ca.getLastColumn();
			int firstRow = ca.getFirstRow();
			int lastRow = ca.getLastRow();
			if(row>=firstRow && row <= lastRow){
				if(column >= firstColumn && column <=lastColumn){
					Row fRow = sheet.getRow(firstRow);
					return fRow.getCell(firstColumn).getStringCellValue();
				}
			}
		}
		//若不是合并单元格就返回正常值
		return sheet.getRow(row).getCell(column).getStringCellValue();
	}

}
