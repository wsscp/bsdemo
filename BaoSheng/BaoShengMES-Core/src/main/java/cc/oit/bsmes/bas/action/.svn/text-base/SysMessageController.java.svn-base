package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.SysMessage;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.SysMessageService;
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
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * System Message Management
 * @author ChenXiang
 * @data 2014年3月20日 上午10:02:28
 * @since
 * @version
 */

@Controller
@RequestMapping("/bas/sysMessage")
public class SysMessageController {
	
	@Resource
	private SysMessageService sysMessageService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "bas");
        model.addAttribute("submoduleName", "sysMessage");
        return "bas.sysMessage";
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
    	
    	//查询
		requestMap.put("messageReceiver", SessionUtils.getUser().getUserCode());
    	List<SysMessage> list = sysMessageService.findByRequestMap(requestMap, start, limit, JSONArray.parseArray(sort, Sort.class));
    	TableView tableView = new TableView();
    	tableView.setRows(list);
    	tableView.setTotal(sysMessageService.countByRequestMap(requestMap));
    	return tableView;
    }
	
	@RequestMapping(value="/getNewMessage", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getNewMessage(HttpServletRequest request) throws ClassNotFoundException {
        HttpSession session = request.getSession();
        User user = SessionUtils.getUser(session);
        JSONObject newMessage = sysMessageService.getNewMessage(user.getUserCode());
        return newMessage;
    }

	@RequestMapping(value="/readMessage/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void readMessage(@PathVariable String id) throws ClassNotFoundException {
        sysMessageService.readMessage(id);
    }

	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		SysMessage sysMessage = JSON.parseObject(jsonText, SysMessage.class);
		sysMessage.setModifyUserCode(SessionUtils.getUser().getUserCode());
		sysMessage.setHasread(true);
		if(sysMessage.getReadTime()==null){
			sysMessage.setReadTime(new Date());
		}
		sysMessageService.update(sysMessage);
		sysMessage = sysMessageService.getById(sysMessage.getId());
		updateResult.addResult(sysMessage);
		return updateResult;
	}
}
