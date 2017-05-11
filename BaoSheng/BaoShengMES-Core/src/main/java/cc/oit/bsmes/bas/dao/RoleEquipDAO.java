package cc.oit.bsmes.bas.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.bas.model.RoleEquip;
import cc.oit.bsmes.common.dao.BaseDAO;

public interface RoleEquipDAO extends BaseDAO<RoleEquip> {

	/**
	 * 根据用户Code获取设备角色列表
	 * 
	 * @author DingXintao
	 * @date 2014年12月24日 17:5:33
	 * @param equipCode 设备代码
	 * @param userCode 员工号
	 * @return List<RoleEquip>
	 */
	public List<RoleEquip> getRoleEquipByUserCode(Map<String, String> param);

}
