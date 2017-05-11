package cc.oit.bsmes.common.log.service.impl;

import cc.oit.bsmes.common.log.dao.BizLogDAO;
import cc.oit.bsmes.common.log.model.BizLog;
import cc.oit.bsmes.common.log.service.BizLogService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 
 * 日志管理Service接口实现
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author zhangdongping
 * @date 2014-2-14 上午11:21:08
 * @since
 * @version
 */
@Service
public class BizLogServiceImpl extends BaseServiceImpl<BizLog> implements
		BizLogService {
	@Resource
	BizLogDAO bizLogDAO;
	@Override
	public BizLog saveLog(BizLog loninfo) {
		 bizLogDAO.insert(loninfo); 
		 return loninfo;
	}
}
