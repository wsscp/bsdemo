package cc.oit.bsmes.wip.service;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.OnoffRecord;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

public interface OnoffRecordService extends BaseService<OnoffRecord> {

	public List<OnoffRecord> getHavePermissionRecord(String clientMac, String equipCode);

	/**
	 * 
	 * <p>
	 * 通过终端Mac地址查询当前终端员工姓名
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-3-6 下午5:59:38
	 * @param mac
	 * @return
	 * @see
	 */
	public String assOnWorkEmployeeNames(String mac);

	/**
	 * 
	 * <p>
	 * 查询当前刷卡用户
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-2-25 下午2:24:54
	 * @param userCode
	 * @param mac
	 * @return
	 * @see
	 */
	public OnoffRecord findbyUserCodeAndMac(String userCode, String mac);

	/**
	 * 
	 * <p>
	 * 员工刷卡，返回当前终端在线员工
	 * </p>
	 * 
	 * @author JinHanyun
	 * @param status
	 * @date 2014-3-6 下午6:04:24
	 * @return
	 * @see
	 */
	public String employeeCreditCard(String mac, OnoffRecord newRecord, User user,Map map);

	public ModelAndView loadUserCreditCardTypeAndWorkShift(String mac, String userCode);

	int validUserPermission(String userCode, String mac, String equipCode);

	JSONObject getOnWorkUserDetailByEquipCode(String equipCode, String orgCode);

	public List<OnoffRecord> getOnWorkUserRecord(String equipCode, String orgCode);

	/**
	 * @Title:       getUsers
	 * @Description: TODO(终端获取在线工人信息)
	 * @param:       request 请求
	 * @param:       response 响应
	 * @param:       role 角色:挡班/副挡板/辅助工
	 * @return:      List<OnoffRecord>   
	 * @throws
	 */
	public List<OnoffRecord> getByMesClientMacAndRole(String mac, String role);

	public List<OnoffRecord> getByMesClientUsers(String mac);

	public int checkIFCreditCard(String userCode, String equipCode);

	public String checkIfDB(String userCode);

	public String checkIfUsed(String equipCode);

}
