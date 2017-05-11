package cc.oit.bsmes.pla.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pla.model.MaterialCheckReportDetails;

public interface MaterialCheckReportDetailsDAO extends BaseDAO<MaterialCheckReportDetails> {

	List<MaterialCheckReportDetails> getByStockCheckId(String stockCheckId);


}
