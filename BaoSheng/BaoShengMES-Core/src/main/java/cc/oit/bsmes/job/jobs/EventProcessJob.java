package cc.oit.bsmes.job.jobs;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.job.base.parent.BaseJob;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.tasks.EventScheduleTask;
import org.apache.commons.lang3.math.NumberUtils;
import org.quartz.*;
import org.springframework.stereotype.Service;

/**
 * 事件处理job
 * <p style="display:none">modifyRecord</p>
 *
 * @author zhangdongping
 * @date 2014-2-20 上午11:51:30
 */
@Service
public class EventProcessJob extends BaseJob implements StatefulJob, InterruptableJob {

    private EventScheduleTask eventScheduleTask;

    public void interrupt() throws UnableToInterruptJobException {
        logger.info("interrupt job...");
        eventScheduleTask.setInterrupt(true);
    }

    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        logger.info("entering job...");

        try {
            //得到Spring Context，然后拿到对应的Dao和Task bean
            eventScheduleTask = (EventScheduleTask) ContextUtils.getBean("eventScheduleTask");
            eventScheduleTask.setInterrupt(false);
            //开始执行之前先写定时任务日志记录，记录开始时间
           // super.beforeExecute();
            //将参数做相应的类型转化，然后调用对应Task的函数
            Integer batchSize = NumberUtils.createInteger(this.getParam2());
            JobParams parm = new JobParams();
            parm.setBatchSize(batchSize);
            parm.setOrgCode(this.getParam1());

            //执行业务操作
            eventScheduleTask.process(parm);
            //业务执行完之后写定时任务日志记录，记录结束时间以及运行结果
           // super.afterExecute(BaseJob.JOB_RESULT_SUCCESS);
        } catch (Throwable e) {
            try {
                logger.error(e.getMessage(), e);
             //   super.afterExecute(BaseJob.JOB_RESULT_FAILURE);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

    }

}
