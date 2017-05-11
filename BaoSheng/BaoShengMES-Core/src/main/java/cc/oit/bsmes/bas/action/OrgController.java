package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.Org;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.OrgService;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.view.UpdateResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * OrgController 机构
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2013-12-11 下午1:09:22
 * @since
 * @version
 */
@Controller
@RequestMapping("/bas/org")
public class OrgController {

	@Resource
	private OrgService orgService;

	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "org");
        return WebConstants.TILES_VIEW_LAYOUT_W + "bas.org"; 
    }
	
	
//	以下两个方法为树结构的方法
	
	/**
	 * 查询数据库表org，根据parentId查
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="{parentId}",method=RequestMethod.GET)
//	@PathVariable:获得请求Url中的动态参数，也即RequestMapping中{}里的
	public List<Org> listByParentId(@PathVariable String parentId){
		List<Org> list = orgService.getByParentId(parentId);
		return list;
	}
	
	
	/**
	 * 根据orgCode查询数据，检查orgCode是否唯一
	 * @param orgCode
	 * @return
	 */
	@RequestMapping(value="/checkOrgCodeUnique/{orgCode}",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject checkOrgCodeUnique(@PathVariable String orgCode){
		JSONObject obj = new JSONObject();
		System.out.println(orgCode);
		Org org= orgService.checkOrgCodeUnique(orgCode);
		if(org!=null){
			obj.put("checkReult", true);
		}
		return obj;
	}
	
	
	
	
	
	

	/**
	 * 添加与修改方法区域
	 * @param jsonText
	 * @return
	 * @throws ClassNotFoundException
	 */
	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		Org org =  JSON.parseObject(jsonText, Org.class);
		org.setStatus(true);
		orgService.insert(org);
		updateResult.addResult(org);
		return updateResult;
	}
}
