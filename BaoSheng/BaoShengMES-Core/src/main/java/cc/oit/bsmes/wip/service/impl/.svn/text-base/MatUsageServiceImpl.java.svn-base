package cc.oit.bsmes.wip.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.MatUsageDAO;
import cc.oit.bsmes.wip.model.MatUsage;
import cc.oit.bsmes.wip.service.MatUsageService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class MatUsageServiceImpl extends BaseServiceImpl<MatUsage>
		implements MatUsageService{

	@Resource
	private MatUsageDAO matUsageDAO;

	@Override
	public void insertData(JSONObject realJs,String matUsageId,String operator) throws ParseException {	 					
    	String equipCode=realJs.getString("equipCode");
    	String shiftName=realJs.getString("shiftName");
    	String dbUserCode=realJs.getString("DB");
    	String dbUserName=realJs.getString("dbUserName");
    	String fdbUserCode=realJs.getString("FDB");
    	String fdbUserName=realJs.getString("fdbUserName");
    	String fzgUserCode=realJs.getString("FZG");
    	String fzgUserName=realJs.getString("fzgUserName");
    	String workOrderNo=realJs.getString("workOrderNo");
    	String turnverMatDate=realJs.getString("shiftDate").substring(0, 10); 
    	SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
    	Date date=sf.parse(turnverMatDate);
    	MatUsage matUsage=new MatUsage();
    	matUsage.setId(matUsageId);
    	matUsage.setEquipCode(equipCode);
    	if(shiftName.equals("mShift")){
    		shiftName="早班";
    	}
    	if(shiftName.equals("aShift")){
    		shiftName="中班";
    	}
    	if(shiftName.equals("eShift")){
    		shiftName="晚班";
    	}
    	matUsage.setShiftName(shiftName);
    	matUsage.setDbUserCode(dbUserCode);
    	matUsage.setDbUserName(dbUserName);
    	if(StringUtils.isNotBlank(fdbUserCode)){
    	   	matUsage.setFdbUserCode(fdbUserCode);
    	   	matUsage.setFdbUserName(fdbUserName);
    	}
    	if(StringUtils.isNotBlank(fzgUserCode)){
    		matUsage.setFzgUserCode(fzgUserCode);
    		matUsage.setFzgUserName(fzgUserName);
    	}
    	matUsage.setWorkOrderNo(workOrderNo);
    	matUsage.setTurnoverMatDate(date);
    	matUsage.setCreateUserCode(operator);
    	matUsage.setModifyUserCode(operator);
    	matUsage.setCreateTime(new Date());
    	matUsageDAO.insert(matUsage);
	}

	@Override
	public void insertMatDetail(Map<String, String> param) {
		matUsageDAO.insertMatDetail(param);
		
	}

	@Override
	public void insertProAssistDetail(Map<String, String> param) {
		matUsageDAO.insertProAssistDetail(param);
		
	}

	@Override
	public void insertAssistDetail(Map<String, String> map) {
		matUsageDAO.insertAssistDetail(map);		
	}

	@Override
	public void checkData(JSONObject realJs, String operator) throws ParseException {
		MatUsage matUsage=new MatUsage();
		String equipCode=realJs.getString("equipCode");
		String shiftName=realJs.getString("shiftName");
		String workOrderNo=realJs.getString("workOrderNo");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String turnverMatDate=realJs.getString("shiftDate");
		Date date=sf.parse(turnverMatDate);
		if(shiftName.equals("mShift")){
    		shiftName="早班";
    	}
    	if(shiftName.equals("aShift")){
    		shiftName="中班";
    	}
    	if(shiftName.equals("eShift")){
    		shiftName="晚班";
    	}
    	matUsage.setEquipCode(equipCode);
    	matUsage.setShiftName(shiftName);
    	matUsage.setTurnoverMatDate(date);
    	matUsage.setCreateUserCode(operator);
    	matUsage.setWorkOrderNo(workOrderNo);
    	if(matUsageDAO.findExists(matUsage)!=null && matUsageDAO.findExists(matUsage).size()>0){
    		matUsageDAO.deleteByParam(matUsage);;
    	}
	}
	

}
