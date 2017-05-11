package cc.oit.bsmes.bas.service;

import java.util.List;

import cc.oit.bsmes.bas.model.RoleEquip;
import cc.oit.bsmes.common.service.BaseService;
/**
 * 
 * 角色拥有的生产线
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-9-16 下午3:33:14
 * @since
 * @version
 */
public interface RoleEquipService extends BaseService<RoleEquip> {

	/**
	 * 根据用户Code获取设备角色列表
	 * 
	 * @author DingXintao
	 * @date 2014年12月24日 17:5:33
	 * @param equipCode 设备代码
	 * @param userCode 员工号
	 * @return List<RoleEquip>
	 */
	public List<RoleEquip> getRoleEquipByUserCode(String equipCode, String userCode);

}
