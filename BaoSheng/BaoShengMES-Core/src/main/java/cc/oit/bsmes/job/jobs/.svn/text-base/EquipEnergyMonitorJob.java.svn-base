package cc.oit.bsmes.job.jobs;

import org.apache.commons.lang3.math.NumberUtils;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.job.base.parent.BaseJob;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.tasks.EquipEnergyMonitorTask;

@Service
public class EquipEnergyMonitorJob extends BaseJob implements StatefulJob, InterruptableJob{

	private EquipEnergyMonitorTask equipEnergyMonitorTask;
	
	@Override
	public void interrupt() throws UnableToInterruptJobException {
		 logger.info("interrupt job...");
		 equipEnergyMonitorTask.setInterrupt(true);
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		 logger.info("entering EquipEnergyMonitorJob job...");
		 
		 try{
			 equipEnergyMonitorTask = (EquipEnergyMonitorTask) ContextUtils.getBean(EquipEnergyMonitorTask.class);
			 equipEnergyMonitorTask.setInterrupt(false);
			 Integer batchSize = NumberUtils.createInteger(this.getParam2());
	         JobParams parm = new JobParams();
	         parm.setBatchSize(batchSize);
	         parm.setOrgCode(this.getParam1());
	         equipEnergyMonitorTask.process(parm);
         }catch(Throwable e) {
	            try {
	                logger.error(e.getMessage(), e); 
	            } catch (Exception ex) {
	                logger.error(ex.getMessage(), ex);
	            }
         }
	}
}
