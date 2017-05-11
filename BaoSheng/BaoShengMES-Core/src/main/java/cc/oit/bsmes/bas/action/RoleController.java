package cc.oit.bsmes.bas.action;


import cc.oit.bsmes.bas.dto.RoleResourceTreeDto;
import cc.oit.bsmes.bas.model.*;
import cc.oit.bsmes.bas.service.*;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 角色维护
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2013-12-16 上午9:24:25
 * @since
 * @version
 */
@Controller
@RequestMapping("/bas/role")
public class RoleController {
	@Resource
	private ResourcesService resourcesService;
	@Resource
	private RoleService roleService;
	@Resource
	private RoleResourceService roleResourceService;
	@Resource
	private OrgService orgService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource 
	private RoleEquipService roleEquipService;
	private static int TOTAL_LIMIT=14;
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "role");
        return "bas.role";
    }
	
    @RequestMapping
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public TableView list(@ModelAttribute Role param, @RequestParam(required=false) Integer start, @RequestParam(required=false) Integer limit, @RequestParam(required = false) String sort) {
    	param.setOrgCode(roleService.getOrgCode());
        List list = roleService.find(param, start, limit,null);
        TableView tableView = new TableView();
        tableView.setRows(list);
        tableView.setTotal(roleService.count(param));
        return tableView;
    }
    

	@RequestMapping(value="checkOrgExists/{orgCode}",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject checkOrgExists(@PathVariable String orgCode) throws ClassNotFoundException {
		JSONObject object = new JSONObject();
		Org org = orgService.getByName(orgCode);
		object.put("orgExists", org != null);
		return object;
	}
	
	@RequestMapping(value="checkRole/{code}",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject checkRole(@PathVariable String code) throws ClassNotFoundException {
		JSONObject object = new JSONObject();
		Role role =roleService.getByCode(code,orgService.getOrgCode());
		object.put("checkRole", role == null);
		return object;
	}
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		Role role =  JSON.parseObject(jsonText, Role.class);
		
		String orgCode = role.getOrgCode();
		Org org = orgService.getByCode(orgCode);
		role.setOrgCode(org.getOrgCode());
		roleService.insert(role);

		role = roleService.getById(role.getId());
		updateResult.addResult(role);
		return updateResult;
	}
	
	
	@ResponseBody
	@RequestMapping(value="resourcesTree/{roleId}/{parentId}",method=RequestMethod.GET)
	public List<RoleResourceTreeDto> listByParentId(@PathVariable String roleId,@PathVariable String parentId){
		List<Resources> list = resourcesService.getByParentId(parentId);
		List<RoleResource> roleResourceList = roleResourceService.getByRoleId(roleId);
		List<RoleResourceTreeDto> result = new ArrayList<RoleResourceTreeDto>();
		for(Resources resource:list){
			RoleResourceTreeDto dest = new RoleResourceTreeDto();
			BeanUtils.copyProperties(resource, dest);
			for(RoleResource rr:roleResourceList){
				if(StringUtils.equalsIgnoreCase(rr.getResourceId(), resource.getId())){
					BeanUtils.copyProperties(rr, dest,"id");
				}
			}
			dest.setRoleId(roleId);
			result.add(dest);
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="saveRoleResource/{id}",method=RequestMethod.PUT)
	public UpdateResult saveRoleResource(@RequestBody String jsonText){
		System.out.println(jsonText);
		RoleResourceTreeDto dto = JSONObject.parseObject(jsonText, RoleResourceTreeDto.class);
		// TODO 判断保存Resource是否叶子节点（不做判断hasChild为null，return转换leaf时报错）
		dto.setHasChild(resourcesService.hasChild(dto.getId()));
		roleResourceService.saveRoleResource(dto);
		

		UpdateResult result = new UpdateResult();
		result.addResult(dto);
		return result;
//		List<Receipt> receiptArray = JSONArray.parseArray(roleResourcesList,Receipt.class);
//		List<Resources> list = resourcesService.getByParentId(parentId);
	}
	
	@RequestMapping(value="getRoleEquip",method=RequestMethod.GET)	
	@ResponseBody
	public TableView getRoleEquip(HttpServletRequest request, EquipInfo findParams, @RequestParam String sort, 
    		@RequestParam int page, @RequestParam int start, @RequestParam int limit){
		findParams.setOrgCode(SessionUtils.getUser().getOrgCode());
		findParams.setRoleId(request.getParameter("roleId"));
		List<EquipInfo> rows=equipInfoService.getByRoleId(findParams,start,TOTAL_LIMIT, JSONArray.parseArray(sort, Sort.class));
		int totals=equipInfoService.countByRoleId(findParams);
		TableView tableView = new TableView();
	    tableView.setRows(rows);
	    tableView.setTotal(totals);
		return tableView;
	}
	@RequestMapping(value="equip/{query}",method=RequestMethod.GET)	
	@ResponseBody
	public List<EquipInfo> equip(@PathVariable String query){
		  return equipInfoService.getByNameOrCode(query, SessionUtils.getUser().getOrgCode(),EquipType.PRODUCT_LINE);
	}
	@ResponseBody
	@RequestMapping(value="insertRoleEquip",method = RequestMethod.POST)
	public String insert(@RequestParam String roleId,@RequestParam String equipId){
		RoleEquip roleEquip=new RoleEquip();
		roleEquip.setRoleId(roleId);
		roleEquip.setEqipInfoId(equipId);
		List<RoleEquip> list=roleEquipService.findByObj(roleEquip);
		if(list.size()>0){
			return "equipExtist";
		}else{
			roleEquipService.insert(roleEquip);
			return "insert";
		}
	}
	@ResponseBody
	@RequestMapping(value="deleteRoleEquip",method = RequestMethod.GET)
	public void deleteRoleEquip(@RequestParam String id){
		roleEquipService.deleteById(id);
	}
}
