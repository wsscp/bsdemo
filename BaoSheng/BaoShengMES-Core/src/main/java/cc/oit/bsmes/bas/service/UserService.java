package cc.oit.bsmes.bas.service;

import java.util.List;

import cc.oit.bsmes.bas.model.EmployeeFamilyInfo;
import cc.oit.bsmes.bas.model.Resume;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * TODO用户管理Service接口
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2013-12-17 上午11:21:08
 * @since
 * @version
 */
public interface UserService extends BaseService<User> {
	
	/**
	 * 
	 * 根据用户编号查询用户
	 * @author JinHanyun
	 * @date 2013-12-17 上午11:21:55
	 * @param userCode
	 * @return
	 * @see
	 */
	public User checkUserCodeUnique(String userCode);

	/**
	 * <p>用户登录</p> 
	 * @author QiuYangjun
	 * @date 2014-5-6 下午4:04:47
	 * @param loginName
	 * @param password
	 * @return
	 * @see
	 */
	public User login(String loginName, String password);
	
	/**
	 * 验证用户
	 */
	public User validate(String userCode,String password);
	/**
	 * <p>用户登录后,读取用户角色,用户资源</p> 
	 * @author QiuYangjun
	 * @date 2014-5-8 上午11:50:09
	 * @param user
	 * @param request
	 * @param response
	 * @return 
	 * @see
	 */
	public boolean userLoginSuccess(User user, HttpServletRequest request,
			HttpServletResponse response);
	
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
	 * @see
	 */
	public boolean isGroupUser(String userCode);
	
	
	public boolean isPauseAuthority(User user);


    public int updateByUserCode(String userCode,String supplementary);

	public int updateUserCode(User user);
	
	public String getOrgName(String orgCode);

	public List<User> getAllOrg();

	public JSONObject importResume(Sheet sheet, JSONObject result);

	public Resume getResumeInfo(String userCode);

	public JSONObject importUserFamilyInfo(Sheet sheet1, JSONObject result);

	public List<EmployeeFamilyInfo> getFamilyInfo(String userCode);



}
