package cc.oit.bsmes.job.tasks;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.service.EquipEnergyMonitorService;
import cc.oit.bsmes.bas.service.EquipEnergyMonthMonitorService;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;

@Service
public class EquipEnergyMonitorTask extends BaseSimpleTask {
	@Resource
	private EquipEnergyMonitorService equipEnergyMonitorService;
	
	@Resource
	private EquipEnergyMonthMonitorService equipEnergyMonthMonitorService;
	
	
	@Override
	public void process(JobParams parms) throws Exception {
		
		equipEnergyMonitorService.insertEquipEnergyMonitorInfo();
		
		
		equipEnergyMonthMonitorService.insertMonthEquipEnergyMonitorInfo();
	}

}
