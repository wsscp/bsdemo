/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */ 
package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.Resources;
import cc.oit.bsmes.bas.service.ResourcesService;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * ResourcesController 资源
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2013-12-11 下午1:09:22
 * @since
 * @version
 */
@Controller
@RequestMapping("/bas/resources")
public class ResourcesController {

	@Resource
	private ResourcesService resourcesService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "resources");
        return WebConstants.TILES_VIEW_LAYOUT_W + "bas.resources"; 
    }
	
	@ResponseBody
	@RequestMapping(method=RequestMethod.GET)
	public TableView list(@RequestParam String parentId, @RequestParam(required = false) String sort,@RequestParam int page, @RequestParam int start, @RequestParam int limit){
		TableView tableView = new TableView();
		if(StringUtils.isBlank(parentId)){
			parentId = WebConstants.ROOT_ID;
		}
		List<Resources> list = resourcesService.getByParentId(parentId, start,limit, JSONArray.parseArray(sort, Sort.class));
		for(Resources resource : list){
			if(resource.getType().equals("MENU")){
				resource.setTypeName("菜单");
			}else if(resource.getType().equals("BUTTON")){
				resource.setTypeName("按钮");
			}
		}
		tableView.setRows(list);
		tableView.setTotal(resourcesService.countByParentId(parentId));
		return tableView;
	}

	@RequestMapping(value="tree",method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		Resources resources = JSON.parseObject(jsonText, Resources.class);
		if(resources.getType().equals("菜单")){
			resources.setType("MENU");
		}else if(resources.getType().equals("按钮")){
			resources.setType("BUTTON");
		}
		UpdateResult updateResult = new UpdateResult();
		resourcesService.insert(resources);
		updateResult.addResult(resources);
		return updateResult;
	}
	@RequestMapping(value="tree/{id}",method = RequestMethod.DELETE, consumes="application/json")
	@ResponseBody
	public UpdateResult remove(@PathVariable String id) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		resourcesService.deleteById(id);
		return updateResult;
	}
	
	
	@ResponseBody
	@RequestMapping(value="tree/{parentId}",method=RequestMethod.GET)
	public List<Resources> listByParentId(@PathVariable String parentId){
		List<Resources> list = resourcesService.getByParentId(parentId);
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="detail/{id}",method=RequestMethod.GET)
	public Resources getDetailById(@PathVariable String id){
		Resources resources = resourcesService.getById(id);
		return resources;
	}
	
	@ResponseBody
	@RequestMapping(value="tree/menu/{parentId}",method=RequestMethod.GET)
	public List<Resources> listMenuByParentId(@PathVariable String parentId){
		List<Resources> list = resourcesService.getMenuByParentId(parentId);
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="updateSeq",method=RequestMethod.POST)
	public void updateSeq(@RequestParam String updateSeq){
		List<Resources> resourcesList = JSONArray.parseArray(updateSeq, Resources.class);
		resourcesService.updateResourceSeq(resourcesList);
	}

}
