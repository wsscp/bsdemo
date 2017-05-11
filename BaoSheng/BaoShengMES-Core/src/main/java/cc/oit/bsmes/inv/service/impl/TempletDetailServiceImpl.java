package cc.oit.bsmes.inv.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.inv.dao.TempletDetailDAO;
import cc.oit.bsmes.inv.model.TempletDetail;
import cc.oit.bsmes.inv.service.TempletDetailService;

@Service
public class TempletDetailServiceImpl extends BaseServiceImpl<TempletDetail>
		implements TempletDetailService {
	@Resource
	private TempletDetailDAO templetDetailDAO;
	
}
