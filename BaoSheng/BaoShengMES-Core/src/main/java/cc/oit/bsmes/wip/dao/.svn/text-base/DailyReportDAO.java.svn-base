package cc.oit.bsmes.wip.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.DailyReport;

public interface DailyReportDAO extends BaseDAO<DailyReport>{
	
	public List<DailyReport> queryUsersHoursForDate(Map<String, String> param);

	public List<DailyReport> getDailyUsersByDate(Map<String, Object> param);

	public List<DailyReport> getDailyEquipCodeByDate(Map<String, String> param);

	Integer countDailyReportByDate(Map<String, String> param);

	public List<DailyReport> searchCreditCardList(Map<String, Object> param);

	public Integer countCreditCardList(Map<String, Object> param);
}
