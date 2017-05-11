package cc.oit.bsmes.bas.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.dao.RoleEquipDAO;
import cc.oit.bsmes.bas.model.RoleEquip;
import cc.oit.bsmes.bas.service.RoleEquipService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;

@Service
public class RoleEquipServiceImpl extends BaseServiceImpl<RoleEquip> implements RoleEquipService {
	@Resource
	private RoleEquipDAO roleEquipDAO;

	/**
	 * 根据用户Code获取设备角色列表
	 * 
	 * @author DingXintao
	 * @date 2014年12月24日 17:5:33
	 * @param equipCode 设备代码
	 * @param userCode 员工号
	 * @return List<RoleEquip>
	 */
	public List<RoleEquip> getRoleEquipByUserCode(String equipCode, String userCode) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("equipCode", equipCode);
		param.put("userCode", userCode);
		return roleEquipDAO.getRoleEquipByUserCode(param);
	}
}
