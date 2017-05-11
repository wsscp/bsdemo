package cc.oit.bsmes.job.tasks;

import cc.oit.bsmes.bas.service.SysMessageService;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.ProductSOPService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SOPScheduleTask extends BaseSimpleTask {

	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private ProductSOPService productSOPService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private ResourceCache resourceCache;
    @Resource
    private SysMessageService sysMessageService;

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void process(JobParams params) {
        resourceCache.init();
		String orgCode = params.getOrgCode();

		customerOrderItemProDecService.analysisOrderToProcess(resourceCache,
				orgCode);
        equipInfoService.initEquipLoad(orgCode);
		try {
			productSOPService.calculateSOP(resourceCache, orgCode);
        } catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}

        sysMessageService.sendMessage("admin", "SOP", "SOP计算已完成！");
	}

}
