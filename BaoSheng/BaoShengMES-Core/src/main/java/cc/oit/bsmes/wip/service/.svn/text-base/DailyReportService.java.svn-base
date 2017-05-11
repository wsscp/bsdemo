package cc.oit.bsmes.wip.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.wip.model.DailyReport;

public interface DailyReportService {

	List<DailyReport> getDailyReportByDate(Map<String, String> param, int start, int limit);

	List<DailyReport> getDailyReportByDate(Map<String, Object> param);

	List<DailyReport> getDailyEquipCodeByDate(Map<String, String> param);
	
	Integer countDailyReportByDate(Map<String, String> param);

	List<DailyReport> searchCreditCardList(Map<String, Object> param,
			int start, int limit);

	Integer countCreditCardList(Map<String, Object> param);

}
