package cc.oit.bsmes.wip.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.UserWorkHours;

/**
 * UserWorkHoursDAO
 * @author DingXintao
 * @date 2014-08-11 11:20:48
 * @since
 * @version
 */
public interface UserWorkHoursDAO extends BaseDAO<UserWorkHours> {
	
	/**
	 * 查询一个月的员工工时记录
	 * @param params 参数：startDate, endDate
	 * */
	public List<Map<String, Object>> queryUsersHoursForAMonth(Map<String, Object> params);
	
	public List<Map<String, String>> getUserName();
}
