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
package cc.oit.bsmes.wip.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.TurnOverReport;

/**
 * 
 * @author rongyd
 *
 */
public interface TurnOverReportDAO extends BaseDAO<TurnOverReport>{
	
	public List<TurnOverReport> getTurnOverReportByEquipCode(String equipCode,String beforeTime,String afterTime);
	
	public int countTurnOverReportByEquipCode(String equipCode,String beforeTime,String afterTime);
	
	public List<TurnOverReport> findByParam(TurnOverReport param);
	
	public void deleteById(String id);

	public List<TurnOverReport> getTurnoverReport(String processCode,
			String shiftDate, String shiftName, String equipCode,
			String userCode);
	
	public int getCountTurnoverReport(String processCode,
			String shiftDate, String shiftName, String equipCode,
			String userCode);
	
	public List<TurnOverReport> getProcessName();
	
	public List<TurnOverReport> getEquipCode();
	
	public List<TurnOverReport> getUserName(String query);

	public String getProductOutput(String equipCode, String beforeTime,
			String afterTime);

}
