package cc.oit.bsmes.job.jobs;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.job.base.parent.BaseJob;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.tasks.AlarmProcessTask;
import org.apache.commons.lang3.math.NumberUtils;
import org.quartz.*;
import org.springframework.stereotype.Service;

/**
 * 生产质量监控异常处理
 * <p style="display:none">modifyRecord</p>
 *
 * @author JinHanyun
 * @date 2014-3-18 下午5:07:03
 */
@Service
public class AlarmProcessJob extends BaseJob implements StatefulJob, InterruptableJob {

    private AlarmProcessTask alarmProcessTask;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("interrupt job...");
        alarmProcessTask.setInterrupt(true);
    }

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        logger.info("entering job...");
        try {
            alarmProcessTask = (AlarmProcessTask) ContextUtils.getBean(AlarmProcessTask.class);
            alarmProcessTask.setInterrupt(false);
            //super.beforeExecute();
            Integer batchSize = NumberUtils.createInteger(this.getParam2());
            //执行业务操作
            JobParams param = new JobParams();
            param.setBatchSize(batchSize);
            param.setOrgCode(this.getParam1());
            alarmProcessTask.process(param);

            //super.afterExecute(BaseJob.JOB_RESULT_SUCCESS);
        } catch (Throwable e) {
            try {
                logger.error(e.getMessage(), e);
                //super.afterExecute(BaseJob.JOB_RESULT_FAILURE,e.getMessage());
                //super.afterExecute(BaseJob.JOB_RESULT_FAILURE);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
