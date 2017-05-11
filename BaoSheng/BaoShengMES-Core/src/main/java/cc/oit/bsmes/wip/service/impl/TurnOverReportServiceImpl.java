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
package cc.oit.bsmes.wip.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.ProcessInfoType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.TurnOverReportDAO;
import cc.oit.bsmes.wip.dto.MethodReturnDto;
import cc.oit.bsmes.wip.model.TurnOverMatDetail;
import cc.oit.bsmes.wip.model.TurnOverReport;
import cc.oit.bsmes.wip.service.TurnOverMatDetailService;
import cc.oit.bsmes.wip.service.TurnOverReportService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * <p style="display:none">modifyRecord</p>
 * @author QiuYangjun
 * @date 2014-2-20 下午3:44:31
 * @since
 * @version
 */
@Service
public class TurnOverReportServiceImpl extends BaseServiceImpl<TurnOverReport> implements TurnOverReportService {
    
	@Resource
	private TurnOverReportDAO turnOverReportDAO;
	@Resource
	private TurnOverMatDetailService turnOverMatDetailService;

	
	@Override
	public List<TurnOverReport> getTurnOverReportByEquipCode(String equipCode,String beforeTime,String afterTime) {
		return turnOverReportDAO.getTurnOverReportByEquipCode(equipCode,beforeTime,afterTime);
	}
	
	public List<TurnOverReport> findByParam(TurnOverReport param){
		return turnOverReportDAO.findByParam(param);
	}
    
	public void deleteById(String id){
		turnOverReportDAO.deleteById(id);
	}
	
	@Override
	public int countTurnOverReportByEquipCode(String equipCode,String beforeTime,String afterTime) {
		return turnOverReportDAO.countTurnOverReportByEquipCode(equipCode,beforeTime,afterTime);
	}

	@Override
	public MethodReturnDto insertTurnOverReport(String orderItemDecId,
			String equipCode, String shiftName, String dbUserCode,
			String dbUserName, String fdbUserCode, String fdbUserName,
			String fzgUserCode, String fzgUserName, String workOrderNo,
			String contractNo, String custProductType, String custProductSpec,
			Double workOrderLength, Double reportLength, String realJsonData,String turnOverDate,
			String matCode,String matName,String quotaQuantity,String operator,String shiftDate,
			String processCode,String createTime) throws ParseException {
		TurnOverReport param=new TurnOverReport();
		if(processCode.contains("-")){
			processCode=processCode.replace("-", "");
		}
		String processName=ProcessInfoType.valueOf(processCode.toUpperCase()).toString();
	    Date sd=new SimpleDateFormat("yyyy-MM-dd").parse(shiftDate);
	    Date ct=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createTime);
		  if(shiftName.equals("mShift")){
		    	shiftName="早班";
		    }
		    else if(shiftName.equals("aShift")){
		    	shiftName="中班";
		    }
		    else{
		    	shiftName="晚班";
		    }
		param.setShiftName(shiftName);
		param.setShiftDate(sd);
		List<TurnOverReport> findlists=this.findByParam(param);
		if(null!=findlists&&findlists.size()>0){
			for(TurnOverReport t:findlists){
				if(ct.getTime()!=t.getCreateTime().getTime()){
					turnOverMatDetailService.deleteByTurnOverReportId(t.getId());
					this.deleteById(t.getId());			
				}				
			}
    	}
	    List<String> matCodeList=new ArrayList<String>();
	    List<String> matNameList=new ArrayList<String>();
	    List<String> quotaQuantityList=new ArrayList<String>();  
	    Date d=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(turnOverDate);	
	    String turnOverReportId=UUID.randomUUID().toString();
		TurnOverReport turnOverReport=new TurnOverReport();
		turnOverReport.setId(turnOverReportId);
		turnOverReport.setEquipCode(equipCode);
		turnOverReport.setShiftName(shiftName);
		turnOverReport.setDbUserCode(dbUserCode);
		turnOverReport.setDbUserName(dbUserName);
		turnOverReport.setProcessCode(processCode);
		turnOverReport.setProcessName(processName);
		if(StringUtils.isNotEmpty(fdbUserCode)){
			turnOverReport.setFdbUserCode(fdbUserCode);
			turnOverReport.setFdbUserName(fdbUserName);
		}
		if(StringUtils.isNotEmpty(fzgUserCode)){
			turnOverReport.setFzgUserCode(fzgUserCode);
			turnOverReport.setFzgUserName(fzgUserName);
		}
		turnOverReport.setWorkOrderNo(workOrderNo);
		turnOverReport.setContractNo(contractNo);
		turnOverReport.setCustProductType(custProductType);
		turnOverReport.setCustProductSpec(custProductSpec);
		turnOverReport.setWorkOrderLength(workOrderLength);
		turnOverReport.setReportLength(reportLength);
		turnOverReport.setCreateUserCode(operator);
		turnOverReport.setCreateTime(d);
		turnOverReport.setTurnoverDate(d);
		turnOverReport.setShiftDate(sd);
		turnOverReport.setCreateTime(ct);
		this.insert(turnOverReport);
		if(StringUtils.isNotEmpty(matCode)){		
			if(matCode.contains(",")){
				String[] codeItem=matCode.split(",");
				String[] nameItem=matName.split(",");
				String[] quotaItem=quotaQuantity.split(",");
				matCodeList=Arrays.asList(codeItem);
				matNameList=Arrays.asList(nameItem);
				quotaQuantityList=Arrays.asList(quotaItem);
			}
			else{
				matCodeList.add(matCode);
				matNameList.add(matName);
				quotaQuantityList.add(quotaQuantity);
			}
			for(int i=0;i<matCodeList.size();i++){
				TurnOverMatDetail turnOverMatDetail=new TurnOverMatDetail();
				turnOverMatDetail.setId(UUID.randomUUID().toString());
				turnOverMatDetail.setTurnoverRptId(turnOverReportId);
				turnOverMatDetail.setMatCode(matCodeList.get(i));
				turnOverMatDetail.setMatName(matNameList.get(i));
				turnOverMatDetail.setQuotaQuantity(Double.parseDouble(quotaQuantityList.get(i)));
				turnOverMatDetail.setCreateUserCode(operator);
				turnOverMatDetail.setCreateTime(d);
				if(null!=realJsonData&&!realJsonData.equals("null")){
					JSONObject realJs=JSON.parseObject(realJsonData);
					if(StringUtils.isNotEmpty(realJs.getString(matNameList.get(i)))){
						turnOverMatDetail.setRealQuantity(realJs.getDouble(matNameList.get(i)));
						}			
				}		
				turnOverMatDetailService.insert(turnOverMatDetail);
			}
			}	
		return  new MethodReturnDto(true, "成功！");
	}

	@Override
	public List<TurnOverReport> getTurnoverReport(String processCode,
			String shiftDate, String shiftName, String equipCode,
			String userCode,Integer start,Integer limit,List<Sort> sortArray) {
		if (start != null && limit != null) {
            SqlInterceptor.setRowBounds(new RowBounds(start, limit));
        }

		return turnOverReportDAO.getTurnoverReport(processCode,shiftDate,
    			shiftName,equipCode,userCode);
	}

	@Override
	public int getCountTurnoverReport(String processCode, String shiftDate,
			String shiftName, String equipCode, String userCode) {
		return turnOverReportDAO.getCountTurnoverReport(processCode,shiftDate,
    			shiftName,equipCode,userCode);
	}

	@Override
	public List<TurnOverReport> getProcessName() {
		return turnOverReportDAO.getProcessName();
	}

	@Override
	public List<TurnOverReport> getEquipCode() {
		return turnOverReportDAO.getEquipCode();
	}

	@Override
	public List<TurnOverReport> getUserName(String query) {
		return turnOverReportDAO.getUserName(query);
	}

	@Override
	public String getProductOutput(String equipCode, String beforeTime,
			String afterTime) {
		return turnOverReportDAO.getProductOutput(equipCode,beforeTime,afterTime);
	}

	}

