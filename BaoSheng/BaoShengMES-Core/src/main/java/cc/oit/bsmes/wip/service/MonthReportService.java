package cc.oit.bsmes.wip.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.wip.model.MonthReport;

public interface MonthReportService {

	List<MonthReport> getMonthReportByDate(Map<String, String> param);

	List<MonthReport> getMonthUsersByDate(String yearMonth);

}
