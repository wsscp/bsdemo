package cc.oit.bsmes.wip.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.wip.model.TurnOverMatDetail;

public interface TurnOverMatDetailDAO extends BaseDAO<TurnOverMatDetail>{
	
	public void deleteByTurnOverReportId(String turnoverReportId);

}
