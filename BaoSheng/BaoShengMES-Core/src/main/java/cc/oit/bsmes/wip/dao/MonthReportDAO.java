package cc.oit.bsmes.wip.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.MonthReport;

public interface MonthReportDAO extends BaseDAO<MonthReport>{

	List<MonthReport> getMonthReport(Map<String, String> param);

	List<MonthReport> getMonthUsersByDate(String yearMonth);

}
