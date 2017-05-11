package cc.oit.bsmes.wip.service;


import java.text.ParseException;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.MatUsage;

import com.alibaba.fastjson.JSONObject;


public interface MatUsageService extends BaseService<MatUsage> {
        
	public void insertData(JSONObject realJs,String matUsageId,String operator) throws ParseException;
	
	public void insertMatDetail(Map<String,String> param);
	
	public void insertProAssistDetail(Map<String,String> param);
	
	public void insertAssistDetail(Map<String,String> map);
	
	public void checkData(JSONObject realJs,String operator) throws ParseException;
	

	
}
