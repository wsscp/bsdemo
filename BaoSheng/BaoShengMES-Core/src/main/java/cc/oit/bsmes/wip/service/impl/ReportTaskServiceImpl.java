package cc.oit.bsmes.wip.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.ReportTaskDAO;
import cc.oit.bsmes.wip.model.ReportTask;
import cc.oit.bsmes.wip.service.ReportTaskService;

/**
 * @ClassName:   ReportTaskServiceImpl
 * @Description: TODO(报工明细关联表)
 * @author:      DingXintao
 * @date:        2016年4月9日 下午4:25:41
 */
@Service
public class ReportTaskServiceImpl extends BaseServiceImpl<ReportTask> implements ReportTaskService {

	@Resource
	private ReportTaskDAO reportTaskDAO;
	
}
