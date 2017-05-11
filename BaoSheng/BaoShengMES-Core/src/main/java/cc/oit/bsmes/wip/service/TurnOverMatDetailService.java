package cc.oit.bsmes.wip.service;


import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.wip.model.TurnOverMatDetail;


public interface TurnOverMatDetailService extends BaseService<TurnOverMatDetail> {
        
	public void deleteByTurnOverReportId(String turnoverReportId);
	
}
