package cc.oit.bsmes.wip.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.TurnOverMatDetailDAO;
import cc.oit.bsmes.wip.model.TurnOverMatDetail;
import cc.oit.bsmes.wip.service.TurnOverMatDetailService;

@Service
public class TurnOverMatDetailServiceImpl extends BaseServiceImpl<TurnOverMatDetail>
		implements TurnOverMatDetailService {

	@Resource
	private TurnOverMatDetailDAO turnOverMatDetailDAO;
	
	@Override
	public void deleteByTurnOverReportId(String turnoverReportId) {
		turnOverMatDetailDAO.deleteByTurnOverReportId(turnoverReportId);	
	}


}
