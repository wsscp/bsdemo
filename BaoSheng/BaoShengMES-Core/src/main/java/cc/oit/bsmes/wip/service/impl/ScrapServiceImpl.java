package cc.oit.bsmes.wip.service.impl;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.ScrapDAO;
import cc.oit.bsmes.wip.model.Scrap;
import cc.oit.bsmes.wip.service.ScrapService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ScrapServiceImpl extends BaseServiceImpl<Scrap> implements ScrapService {
	
	@Resource
	private ScrapDAO scrapDAO;

}
