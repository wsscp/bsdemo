package cc.oit.bsmes.pla.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pla.model.MaterialCheckReport;

public interface MaterialCheckReportService extends BaseService<MaterialCheckReport> {
	public void importMaterialCheckReport(Sheet sheet, JSONObject result, Date date);

	public List<MaterialCheckReport> getCheckDays(String yearMonth);

	public List<Map> getMaterialStockByMonth(Map<String, String> param, Integer start, Integer limit, List<Sort> sortList);

	public void importMaterialCheckReportGXJ(Sheet sheet, JSONObject result,
			Date date);

	public void importMaterialCheckReportCL(Sheet sheetCL, JSONObject result,
			Date date);

	public List<Map> getCheckMonth();
	public List<String> getSheetHeaders(Sheet sheet);
	public boolean verdictFormat(Sheet sheet,List<String> list,JSONObject result);

	public List<String> getCheckSectionByMonth(String yearMonth);

	public Integer countMaterialStockByMonth(Map<String, String> param);
}
