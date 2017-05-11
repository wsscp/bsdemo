/*
 * Copyright by Orientech and the original author or authors
 * 
 * This document only allow internal use.Any of your behaviors using the
 * file not internal will pay the legal responsibility.
 * 
 * You can learn more information about Orientech from 
 * 
 *       http://www.orientech.cc/
 */
package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.bas.service.MesClientService;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * MES CLIENT MANAGE
 * @author ChenXiang
 * @date 2014-3-13 下午5:25:15
 * @since
 * @version
 */
@Controller
@RequestMapping("/bas/mesClient")
public class MesClientController {
	
	@Resource
	private MesClientService mesClientService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "mesClient");
        return "bas.mesClient";
    }
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @SuppressWarnings("unchecked")
    public TableView list(HttpServletRequest request,
    		@RequestParam String sort,
    		@RequestParam int page, 
    		@RequestParam int start, 
    		@RequestParam int limit) throws UnsupportedEncodingException{

    	// 设置findParams属性
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String,Object> requestMap = new HashMap<String, Object>();
		for(String key:parameterMap.keySet()){
			if(parameterMap.get(key)!=null&&StringUtils.isNotBlank(parameterMap.get(key)[0])){
				
				if(StringUtils.equalsIgnoreCase(key, "keyV")){
					requestMap.put(key, parameterMap.get(key)[0]);
				}else if(StringUtils.equalsIgnoreCase(key, "valueV")){
					requestMap.put(key, parameterMap.get(key)[0]);
				}else{
					String parameter = URLDecoder.decode(parameterMap.get(key)[0],"UTF-8");
					requestMap.put(key, "%"+parameter+"%");
				}
			}
		}
		requestMap.put("orgCode", mesClientService.getOrgCode());
    	//查询
    	List<MesClient> list = mesClientService.findByRequestMap(requestMap, start, limit, JSONArray.parseArray(sort, Sort.class));
    	TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(mesClientService.countByRequestMap(requestMap));
    	return tableView;
    }
	
	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		MesClient mesClient =  JSON.parseObject(jsonText, MesClient.class);
		mesClientService.insert(mesClient);
		mesClient = mesClientService.getById(mesClient.getId());
		updateResult.addResult(mesClient);
		return updateResult;
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		MesClient mesClient =  JSON.parseObject(jsonText, MesClient.class);
		mesClient.setModifyUserCode(SessionUtils.getUser().getUserCode());
		mesClientService.update(mesClient);
		mesClient = mesClientService.getById(mesClient.getId());
		updateResult.addResult(mesClient);
		return updateResult;
	}
	
	@RequestMapping(value="checkUnique/{clientMac}",method = RequestMethod.GET)
	@ResponseBody
	public boolean checkUnique(@PathVariable String clientMac){
		boolean result = false;
		MesClient mesClient = mesClientService.getByClientMac(clientMac);
		if(mesClient!=null){
			result = true;
		}
		return result;
	}
}
