package cc.oit.bsmes.bas.action;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.UpdateResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典
 * @author jfliu
 *
 */
@Controller
@RequestMapping("/bas/dataDic")
public class DataDicController {
	
	@Resource
	private DataDicService dataDicService;

	@RequestMapping(method = RequestMethod.POST, consumes="application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		JSONObject  json = JSON.parseObject(jsonText);
		
		DataDic dic = new DataDic();
//		if(json.get("termsCode").toString().equals(TermsCodeType.DATA_PROP_CONFIG.toString())){
//			dic.setTermsCode("DATA_PROP_CONFIG");
//		}else if(json.get("termsCode").equals(TermsCodeType.DATA_PARAM_CONFIG.toString())){
//			dic.setTermsCode("DATA_PARAM_CONFIG");
//		}else if(json.get("termsCode").equals(TermsCodeType.EVENT_TYPE_DETAIL.toString())){
//			dic.setTermsCode("EVENT_TYPE_DETAIL");
//		}
		dic.setTermsCode(json.get("termsCode").toString());
		dic.setCode(json.get("code").toString());
		dic.setName(json.get("name").toString());
		dic.setSeq(Integer.valueOf(json.get("seq").toString()));
		dic.setLan(json.get("lan").toString());
		dic.setExtatt(json.get("extatt").toString());
		dic.setMarks(json.get("marks").toString());
		dic.setStatus(json.get("status").toString());
		dic.setCanModify(true);
		dataDicService.insert(dic);
		dic = dataDicService.getById(dic.getId());
		updateResult.addResult(dic);
		return updateResult;
	}
	
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes="application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
		User user = SessionUtils.getUser();
		UpdateResult updateResult = new UpdateResult();
		JSONObject  json = JSON.parseObject(jsonText);
		
		DataDic dic = new DataDic();
		dic.setId(json.get("id").toString());
		dic.setName(json.get("name").toString());
		dic.setSeq(Integer.valueOf(json.get("seq").toString()));
		dic.setLan(json.get("lan").toString());
		dic.setExtatt(json.get("extatt").toString());
		dic.setMarks(json.get("marks").toString());
		dic.setStatus(json.get("status").toString());
		dic.setModifyUserCode(user.getUserCode());
		dic.setCanModify(true);
		dataDicService.update(dic);
		dic = dataDicService.getById(dic.getId());
		updateResult.addResult(dic);
		return updateResult;
	}
	
	/**
	 * 验证词条编号唯一性
	 * @param code
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="checkDicCodeUnique/{termsCode}/{code}",method = RequestMethod.GET)
	@ResponseBody
	public boolean checkDicCodeUnique(@PathVariable String termsCode,@PathVariable String code) throws UnsupportedEncodingException{
		termsCode = URLDecoder.decode(termsCode,"UTF-8");
//		if(termsCode.equals(TermsCodeType.DATA_PROP_CONFIG.toString())){
//			termsCode = "DATA_PROP_CONFIG";
//		}else if(termsCode.equals(TermsCodeType.DATA_PARAM_CONFIG.toString())){
//			termsCode = "DATA_PARAM_CONFIG";
//		}else if(termsCode.equals(TermsCodeType.EVENT_TYPE_DETAIL.toString())){
//			termsCode = "EVENT_TYPE_DETAIL";
//		}
		boolean result = false;
		DataDic dic = dataDicService.getByDicCode(termsCode,code);
		if(dic!=null){
			result = true;
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="getTermsCode")		    
	public List<DataDic> getTermsCode(@RequestParam(required = false)    Boolean needALL){
		List<DataDic> result = new ArrayList<DataDic>();
		if(needALL!=null)
		{
			DataDic dataDic = new DataDic();
			dataDic.setTermsCode("");
			dataDic.setTermsName("所有");
			result.add(dataDic);			
		}
		TermsCodeType[] tc = TermsCodeType.values();
	
		for(TermsCodeType termsCodeType : tc){
			DataDic dataDic = new DataDic();
			dataDic.setTermsCode(termsCodeType.name());
			result.add(dataDic);
		}
	    
	    return result;
	}
}
