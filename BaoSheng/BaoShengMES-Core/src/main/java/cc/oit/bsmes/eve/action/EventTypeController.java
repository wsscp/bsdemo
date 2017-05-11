package cc.oit.bsmes.eve.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.eve.model.EventType;
import cc.oit.bsmes.eve.service.EventTypeService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 事件类型
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-10 下午3:06:22
 * @since
 * @version
 */
@Controller
@RequestMapping("/eve/eventType")
public class EventTypeController {
	@Resource private EmployeeService employeeService;
	@Resource private EventTypeService eventTypeService;
	@Resource
	private DataDicService dataDicService;
	
	
	@RequestMapping(value="getEmployeeOrRole/{query}/{status}",method=RequestMethod.GET)	
	@ResponseBody
	public List<Employee> getEmployeeOrRole(@PathVariable String query,@PathVariable String status){
		List<Employee> employee=employeeService.getByName(query,status);
		return employee;
	}
	
	@RequestMapping(value="checkCode/{code}",method = RequestMethod.GET)
	@ResponseBody
	public JSONObject checkCode(@PathVariable String code) throws ClassNotFoundException {
		JSONObject object = new JSONObject();
		EventType eve=new EventType();
		eve.setCode(code);
		List<EventType> list=eventTypeService.find(eve, 0, Integer.MAX_VALUE, null);
		object.put("checkCode", list.size()==0);
		return object;
	}

	/**
	 * 事件类型明细定义
	 * 
	 * */
	@RequestMapping(value = "eventTypeDesc", method = RequestMethod.GET)
	@ResponseBody
	public List<DataDic> eventTypeDesc(@RequestParam String extatt) {
		DataDic findParam = new DataDic();
		findParam.setExtatt(extatt);
		findParam.setTermsCode(TermsCodeType.EVENT_TYPE_DETAIL.name());
		List<DataDic> dataDics = dataDicService.findByObj(findParam);
		return dataDics;
	}

	/**
	 * 事件类型明细定义 新增
	 * 
	 * */
	@RequestMapping(value = "eventTypeDesc", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public UpdateResult eventTypeDescAdd(HttpServletRequest request, @RequestBody String jsonText) {
		UpdateResult updateResult = new UpdateResult();
		if (jsonText.startsWith("[")) {
			List<DataDic> params = JSON.parseArray(jsonText, DataDic.class);
			for (DataDic param : params) {
				param.setTermsCode(TermsCodeType.EVENT_TYPE_DETAIL.name());
			}
			dataDicService.insert(params);
			updateResult.addResult(params);
		} else {
			DataDic param = JSON.parseObject(jsonText, DataDic.class);
			param.setTermsCode(TermsCodeType.EVENT_TYPE_DETAIL.name());
			dataDicService.insert(param);
			updateResult.addResult(param);
		}
		return updateResult;
	}

	/**
	 * 事件类型明细定义 修改
	 * 
	 * */
	@RequestMapping(value = "eventTypeDesc/{id}", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseBody
	public UpdateResult eventTypeDescEdit(HttpServletRequest request, @RequestBody String jsonText) {
		UpdateResult updateResult = new UpdateResult();
		if (jsonText.startsWith("[")) {
			List<DataDic> params = JSON.parseArray(jsonText, DataDic.class);
			for (DataDic param : params) {
				param.setTermsCode(TermsCodeType.EVENT_TYPE_DETAIL.name());
			}
			dataDicService.update(params);
			updateResult.addResult(params);
		} else {
			DataDic param = JSON.parseObject(jsonText, DataDic.class);
			param.setTermsCode(TermsCodeType.EVENT_TYPE_DETAIL.name());
			dataDicService.update(param);
			updateResult.addResult(param);
		}
		return updateResult;
	}

}
