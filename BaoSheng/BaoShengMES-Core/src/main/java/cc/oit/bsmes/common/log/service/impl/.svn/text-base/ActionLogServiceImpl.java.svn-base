package cc.oit.bsmes.common.log.service.impl;

import cc.oit.bsmes.common.log.dao.ActionLogDAO;
import cc.oit.bsmes.common.log.model.ActionLog;
import cc.oit.bsmes.common.log.service.ActionLogService;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 
 * 日志管理Service实现
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2014-2-14 上午11:21:08
 * @since
 * @version
 */
@Service
public class ActionLogServiceImpl extends BaseServiceImpl<ActionLog> implements ActionLogService {
	@Resource
	ActionLogDAO actionLogDAO;
	@Override
 	public ActionLog saveLog(ActionLog loninfo){
 		 actionLogDAO.insert(loninfo);
 		return loninfo;
 	}
}
