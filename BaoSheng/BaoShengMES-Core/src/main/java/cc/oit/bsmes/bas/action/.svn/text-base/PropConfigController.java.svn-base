package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.model.PropConfig;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.bas.service.PropConfigService;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
 * 属性配置
 *
 * @author jfliu
 */

@Controller
@RequestMapping("/bas/propConfig")
public class PropConfigController {

    @Resource
    private PropConfigService propConfigService;
    @Resource
	private DataDicService dataDicService;

    @RequestMapping(produces = "text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "propConfig");
        return "bas.propConfig";
    }

    /**
     * 查询
     *
     * @param request
     * @param sort
     * @param page
     * @param start
     * @param limit
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public TableView list(HttpServletRequest request, 
    						@RequestParam String sort,
    						@RequestParam int page, 
    						@RequestParam int start, 
    						@RequestParam int limit,
    						@RequestParam(required = false) String keyK,
    			    		@RequestParam(required = false) String valueV) {

    	Map<String,Object> requestMap = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(keyK)){
			requestMap.put("keyK","%"+keyK+"%");
		}
		if(StringUtils.isNotBlank(valueV)){
			requestMap.put("valueV","%"+valueV+"%");
		}

        //查询
        List<PropConfig> list = propConfigService.findByRequestMap(requestMap, start, limit, JSONArray.parseArray(sort, Sort.class));
        TableView tableView = new TableView();
        tableView.setRows(list);
        tableView.setTotal(propConfigService.countByRequestMap(requestMap));
        return tableView;
    }

    @RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		JSONObject  json = JSON.parseObject(jsonText);
		
		PropConfig prop =  new PropConfig();
		prop.setKeyK(json.get("keyK").toString());
		prop.setValueV(json.get("valueV").toString());
		prop.setDescription(json.get("description").toString());
		prop.setStatus(json.get("status").toString().equals("1")?true:false);
		propConfigService.insert(prop);
		prop = propConfigService.getById(prop.getId());
		updateResult.addResult(prop);
		return updateResult;
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
		User user = SessionUtils.getUser();
		UpdateResult updateResult = new UpdateResult();
		JSONObject  json = JSON.parseObject(jsonText);
		
		PropConfig prop =  new PropConfig();
		prop.setId(json.get("id").toString());
		prop.setKeyK(json.get("keyK").toString());
		prop.setValueV(json.get("valueV").toString());
		prop.setDescription(json.get("description").toString());
		prop.setStatus(json.get("status").toString().equals("1")?true:false);
		prop.setModifyUserCode(user.getUserCode());
		propConfigService.update(prop);
		prop = propConfigService.getById(prop.getId());
		updateResult.addResult(prop);
		return updateResult;
	}
    
    /**
     * 验证唯一性
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "checkPropKeyUnique/{key}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkPropKeyUnique(@PathVariable String key) {
        JSONObject object = new JSONObject();
        PropConfig prop = propConfigService.getByPropKey(key);
        object.put("propExists", prop == null ? false : true);
        return object;
    }
    
    @ResponseBody
	@RequestMapping(value="getCodeByTermsCode")		    
	public List<DataDic> getCodeByTermsCode(){
	    List<DataDic> result = dataDicService.getCodeByTermsCode(TermsCodeType.DATA_PROP_CONFIG);
	    return result;
	}
    
    @ResponseBody
	@RequestMapping(value="getValueV/{keyK}")		    
	public DataDic getValueV(@PathVariable String keyK) throws UnsupportedEncodingException{
    	keyK = URLDecoder.decode(keyK,"UTF-8");
    	DataDic result = dataDicService.geByKeyK(keyK);
	    return result;
	}
}
