package cc.oit.bsmes.common.log.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.log.dao.SaleLogDAO;
import cc.oit.bsmes.common.log.model.SaleLog;
import cc.oit.bsmes.common.log.service.SaleLogService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;

/**
 * 
 * @author chenxiang
 * @date 2016-4-21
 * @since
 * @version
 */
@Service
public class SaleLogServiceImpl extends BaseServiceImpl<SaleLog> implements SaleLogService {
	@Resource
	SaleLogDAO saleLogDAO;
	
}
