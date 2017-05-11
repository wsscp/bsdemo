package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.bas.model.UserRole;
import cc.oit.bsmes.bas.service.RoleResourceService;
import cc.oit.bsmes.bas.service.RoleService;
import cc.oit.bsmes.bas.service.UserRoleService;
import cc.oit.bsmes.bas.service.UserService;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;
/**
 * 
 * 用户角色关联表
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-4-28 下午5:14:42
 * @since
 * @version
 */
@Controller
@RequestMapping("/bas/userRole")
public class UserRoleController {
	
	@Resource private UserRoleService userRoleService;
	@Resource private RoleService roleService;
	@Resource private UserService userService;
	@Resource private RoleResourceService roleResourceService;
	/**
	 * 
	 * 用户角色绑定
	 * @author leiwei
	 * @date 2014-4-28 下午5:15:59
	 * @param id
	 * @return
	 * @see
	 */
	@ResponseBody
    @RequestMapping(value = "/userRoleBind/{userId}",method = RequestMethod.GET)
    public TableView userRoleBind(@PathVariable String userId){
        List<UserRole> result =userRoleService.getByUserId(userId,SessionUtils.getUser().getOrgCode());
        TableView tableView = new TableView();
		tableView.setRows(result);
		return tableView;
    }
	
	@ResponseBody
    @RequestMapping(value = "insert",method = RequestMethod.GET)
    public void insert(@RequestParam UserRole userRole){
		
		userRoleService.insert(userRole);
    }
	
	@ResponseBody
    @RequestMapping(value = "delete",method = RequestMethod.GET)
    public void delete(@RequestParam String id,@RequestParam String roleId){
		userRoleService.deleteById(id);
//		roleResourceService.deleteByRoleId(roleId);
    }
	@RequestMapping(value="role",method=RequestMethod.GET)	
	@ResponseBody
	public List<Role> role(){
		List<Role> roles=roleService.getByOrgCode(SessionUtils.getUser().getOrgCode());
		return roles;
	}
	@ResponseBody
	@RequestMapping(value="insertOrUpdate",method = RequestMethod.POST)
	public void insertOrUpdate(@RequestParam String id,@RequestParam String userId,@RequestParam String roleId){
		UserRole userRole=new UserRole();
		if(StringUtils.isBlank(id)){
			userRole.setId(UUID.randomUUID().toString());
			userRole.setRoleId(roleId);
			userRole.setUserId(userId);
			userRoleService.insert(userRole);
		}else{
			userRole.setId(id);
			userRole.setRoleId(roleId);
			userRoleService.update(userRole);
		}
	}
}
