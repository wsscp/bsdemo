package cc.oit.bsmes.job.service.impl;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.job.base.dao.SchedulerLogDAO;
import cc.oit.bsmes.job.base.model.SchedulerLog;
import cc.oit.bsmes.job.service.SchedulerLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwe
 * @date 2014-9-19 下午12:56:30
 * @since
 * @version
 */
@Service
public class SchedulerLogServiceImpl extends BaseServiceImpl<SchedulerLog> implements SchedulerLogService {
	@Resource 
	private SchedulerLogDAO schedulerLogDAO;
}
