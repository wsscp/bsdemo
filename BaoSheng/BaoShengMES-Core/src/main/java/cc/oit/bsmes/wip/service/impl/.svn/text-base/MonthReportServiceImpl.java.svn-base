package cc.oit.bsmes.wip.service.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.MonthReportDAO;
import cc.oit.bsmes.wip.model.MonthReport;
import cc.oit.bsmes.wip.service.MonthReportService;
@Service
public class MonthReportServiceImpl extends BaseServiceImpl<MonthReport>
		implements MonthReportService {
	@Resource
	private MonthReportDAO monthReportDAO;

	@Override
	public List<MonthReport> getMonthReportByDate(Map<String, String> param) {
		// TODO Auto-generated method stub
		return monthReportDAO.getMonthReport(param);
	}

	@Override
	public List<MonthReport> getMonthUsersByDate(String yearMonth) {
		// TODO Auto-generated method stub
		return monthReportDAO.getMonthUsersByDate(yearMonth);
	}
	

}
