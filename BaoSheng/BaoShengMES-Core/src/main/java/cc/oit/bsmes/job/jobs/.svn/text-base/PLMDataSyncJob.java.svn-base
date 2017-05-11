package cc.oit.bsmes.job.jobs;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.job.base.parent.BaseJob;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.tasks.BZProcessTask;
import cc.oit.bsmes.job.tasks.CanShukuTask;
import cc.oit.bsmes.job.tasks.EquipUpdateTask;
import cc.oit.bsmes.job.tasks.PLMProductTask;
import cc.oit.bsmes.job.tasks.PLMPrcvAsyncTask;

/**
 * Created by joker on 2014/11/19 0019.
 */
@Service
public class PLMDataSyncJob extends BaseJob implements StatefulJob, InterruptableJob {

	private BZProcessTask bzProcessTask;
	private EquipUpdateTask equipUpdateTask;
	private PLMPrcvAsyncTask pLMPrcvAsyncTask;
	private PLMProductTask pLMProductTask;
	private CanShukuTask canShukuTask;
	@Override
	public void interrupt() throws UnableToInterruptJobException {
		  logger.info("interrupt job...");
		bzProcessTask.setInterrupt(true);
	}

	@Override
	  protected void executeInternal(JobExecutionContext context)
	            throws JobExecutionException {

		try {
			// 得到Spring Context，然后拿到对应的Dao和Task bean
			bzProcessTask = (BZProcessTask) ContextUtils.getBean(BZProcessTask.class);
			bzProcessTask.setInterrupt(false);

			equipUpdateTask = (EquipUpdateTask) ContextUtils.getBean(EquipUpdateTask.class);
			equipUpdateTask.setInterrupt(false);
			
			pLMPrcvAsyncTask = (PLMPrcvAsyncTask) ContextUtils.getBean(PLMPrcvAsyncTask.class);
			pLMPrcvAsyncTask.setInterrupt(false);
			
			pLMProductTask = (PLMProductTask) ContextUtils.getBean(PLMProductTask.class);
			pLMProductTask.setInterrupt(false);
			
			canShukuTask = (CanShukuTask) ContextUtils.getBean(CanShukuTask.class);
			canShukuTask.setInterrupt(false);


			// 开始执行之前先写定时任务日志记录，记录开始时间
			super.beforeExecute();
			// 将参数做相应的类型转化，然后调用对应Task的函数
			JobParams parm = new JobParams();
			parm.setOrgCode(this.getParam1());
			// 执行业务操作
			bzProcessTask.process(parm);
			equipUpdateTask.process(parm);
			pLMProductTask.process(parm);
			canShukuTask.process(parm);
			pLMPrcvAsyncTask.process(parm); // [产品工艺定义、产品工艺流程、流程投入产出、工序使用设备清单、产品工艺参数]
			// 业务执行完之后写定时任务日志记录，记录结束时间以及运行结果
			super.afterExecute(BaseJob.JOB_RESULT_SUCCESS);
		} catch (Throwable e) {
			try {
				logger.error(e.getMessage(), e);
				super.afterExecute(BaseJob.JOB_RESULT_FAILURE, e.getMessage());
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			}
		}
	}
}
