package cc.oit.bsmes.wip.service;

import java.text.ParseException;
import java.util.List;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.dto.MethodReturnDto;
import cc.oit.bsmes.wip.model.TurnOverReport;

public interface TurnOverReportService extends BaseService<TurnOverReport> {

	public List<TurnOverReport> getTurnOverReportByEquipCode(String equipCode,
			String beforeTime, String afterTime);

	public int countTurnOverReportByEquipCode(String equipCode,
			String beforeTime, String afterTime);

	public MethodReturnDto insertTurnOverReport(String orderItemDecId,
			String equipCode, String shiftName, String dbUserCode,
			String dbUserName, String fdbUserCode, String fdbUserName,
			String fzgUserCode, String fzgUserName, String workOrderNo,
			String contractNo, String custProductType, String custProductSpec,
			Double workOrderLength, Double reportLength, String realJsonData,
			String turnOverDate, String matCode, String matName,
			String quotaQuantity, String operator, String shiftDate,
			String processCode,String createTime) throws ParseException;

	public List<TurnOverReport> getTurnoverReport(String processCode,
			String shiftDate, String shiftName, String equipCode,
			String userCode, Integer start, Integer limit, List<Sort> sortArray);

	public int getCountTurnoverReport(String processCode, String shiftDate,
			String shiftName, String equipCode, String userCode);
	
	public List<TurnOverReport> getProcessName();
	
	public List<TurnOverReport> getEquipCode();
	
	public List<TurnOverReport> getUserName(String query);

	public String getProductOutput(String equipCode, String mShift,
			String currentTime);

}
