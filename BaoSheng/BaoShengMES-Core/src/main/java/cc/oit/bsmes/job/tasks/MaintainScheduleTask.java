package cc.oit.bsmes.job.tasks;

import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MaintainScheduleTask extends BaseSimpleTask {

	@Resource
	private EquipInfoService equipInfoService;

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void process(JobParams params) {
		String orgCode = params.getOrgCode();

        equipInfoService.createMaintainEvent(orgCode);
	}

}
