package cc.oit.bsmes.job.jobs;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.job.base.parent.BaseJob;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.tasks.OaScheduleTask;
import org.apache.commons.lang3.math.NumberUtils;
import org.quartz.*;
import org.springframework.stereotype.Service;

@Service
public class OaScheduleJob extends BaseJob implements StatefulJob, InterruptableJob {


    private OaScheduleTask oaScheduleTask;

    public void interrupt() throws UnableToInterruptJobException {
        logger.info("interrupt job...");
        oaScheduleTask.setInterrupt(true);
    }


    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        logger.info("entering job...");

        try {
            //得到Spring Context，然后拿到对应的Dao和Task bean
            oaScheduleTask = (OaScheduleTask) ContextUtils.getBean(OaScheduleTask.class);
            oaScheduleTask.setInterrupt(false);
            //开始执行之前先写定时任务日志记录，记录开始时间
            super.beforeExecute();
            //将参数做相应的类型转化，然后调用对应Task的函数
            Integer batchSize = NumberUtils.createInteger(this.getParam2());
            JobParams parm = new JobParams();
            parm.setBatchSize(batchSize);
            parm.setOrgCode(this.getParam1());
            //执行业务操作
            oaScheduleTask.process(parm);
            //业务执行完之后写定时任务日志记录，记录结束时间以及运行结果
            super.afterExecute(BaseJob.JOB_RESULT_SUCCESS);
        } catch (Throwable e) {
            try {
                logger.error(e.getMessage(), e);
                super.afterExecute(BaseJob.JOB_RESULT_FAILURE,e.getMessage());
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

    }


}
