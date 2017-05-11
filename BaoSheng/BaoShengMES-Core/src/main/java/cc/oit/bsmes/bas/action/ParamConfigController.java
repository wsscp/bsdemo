package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.model.ParamConfig;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.bas.service.ParamConfigService;
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
 * 参数配置
 * @author jfliu
 *
 */
@Controller
@RequestMapping("/bas/paramConfig")
public class ParamConfigController {
	
	@Resource
	private ParamConfigService paramConfigService;
	@Resource
	private DataDicService dataDicService;

	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "paramConfig");
        return "bas.paramConfig"; 
    }
	
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public TableView list(HttpServletRequest request,
    		@RequestParam String sort,
    		@RequestParam int page, 
    		@RequestParam int start, 
    		@RequestParam int limit,
    		@RequestParam(required = false) String code,
    		@RequestParam(required = false) String name) throws UnsupportedEncodingException{
		User user = SessionUtils.getUser();
		Map<String,Object> requestMap = new HashMap<String, Object>();
		requestMap.put("orgCode",user.getOrgCode());
		
		if(StringUtils.isNotBlank(code)){
			requestMap.put("code","%"+code+"%");
		}
		if(StringUtils.isNotBlank(name)){
			requestMap.put("name","%"+name+"%");
		}
		
    	List<ParamConfig> list = paramConfigService.findByRequestMap(requestMap, start, limit, JSONArray.parseArray(sort, Sort.class));
    	TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(paramConfigService.countByRequestMap(requestMap));
    	return tableView;
    }
	
	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		User user = SessionUtils.getUser();
		UpdateResult updateResult = new UpdateResult();
		JSONObject  json = JSON.parseObject(jsonText);
		
		ParamConfig param =  new ParamConfig();
		param.setCode(json.get("code").toString());
		param.setName(json.get("name").toString());
		param.setValue(json.get("value").toString());
		param.setType(json.get("type").toString());
		param.setDescription(json.get("description").toString());
		param.setStatus(json.get("status").toString().equals("1")?true:false);
		param.setOrgCode(user.getOrgCode());
		paramConfigService.insert(param);
		param = paramConfigService.getById(param.getId());
		updateResult.addResult(param);
		return updateResult;
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
		User user = SessionUtils.getUser();
		UpdateResult updateResult = new UpdateResult();
		JSONObject  json = JSON.parseObject(jsonText);
		
		ParamConfig param =  new ParamConfig();
		param.setId(json.get("id").toString());
		param.setValue(json.get("value").toString());
		param.setType(json.get("type").toString());
		param.setDescription(json.get("description").toString());
		param.setStatus(json.get("status").toString().equals("1")?true:false);
		param.setModifyUserCode(user.getUserCode());
		paramConfigService.update(param);
		param = paramConfigService.getById(param.getId());
		updateResult.addResult(param);
		return updateResult;
	}
	
	/**
	 * 验证参数号唯一性
	 * @param code
	 * @return
	 */
	@RequestMapping(value="checkParamCodeUnique/{code}",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject checkParamCodeUnique(@PathVariable String code){
		JSONObject object = new JSONObject();
		ParamConfig param = paramConfigService.getByParamCode(code);
		object.put("paramExists", param == null?false:true);
		return object;
	}
	
	@ResponseBody
	@RequestMapping(value="getCodeByTermsCode")		    
	public List<DataDic> getCodeByTermsCode(){
	    List<DataDic> result = dataDicService.getCodeByTermsCode(TermsCodeType.DATA_PARAM_CONFIG);
	    return result;
	}
	
	@ResponseBody
	@RequestMapping(value="getName/{code}")		    
	public DataDic getName(@PathVariable String code) throws UnsupportedEncodingException{
		code = URLDecoder.decode(code,"UTF-8");
    	DataDic result = dataDicService.geByKeyK(code);
	    return result;
	}
}
