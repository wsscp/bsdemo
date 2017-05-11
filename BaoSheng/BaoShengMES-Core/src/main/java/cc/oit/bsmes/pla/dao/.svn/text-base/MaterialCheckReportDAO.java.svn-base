package cc.oit.bsmes.pla.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.MaterialCheckReport;

public interface MaterialCheckReportDAO extends BaseDAO<MaterialCheckReport> {
	

	public List<MaterialCheckReport> getCheckDays(String yearMonth);

	public List<MaterialCheckReport> getByCheckMonth(Map<String, String> param);

	public List<String> getCheckMonth();
	
	public List<String> getCheckSectionByMonth(String yearMonth);

	public Integer countMaterialStockByMonth(Map<String, String> param);

}
