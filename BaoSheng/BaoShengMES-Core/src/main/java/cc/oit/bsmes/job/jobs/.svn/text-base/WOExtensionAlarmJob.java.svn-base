package cc.oit.bsmes.job.jobs;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.quartz.UnableToInterruptJobException;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.job.base.parent.BaseJob;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.tasks.WorkOrderExtensionAlarmTask;

/**
 * Created by JinHy on 2014/11/3 0003.
 */
@Service
public class WOExtensionAlarmJob extends BaseJob implements StatefulJob, InterruptableJob {

    private WorkOrderExtensionAlarmTask workOrderExtensionAlarmTask;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("entering job...");

        try {
            //得到Spring Context，然后拿到对应的Dao和Task bean
            workOrderExtensionAlarmTask = (WorkOrderExtensionAlarmTask) ContextUtils.getBean(WorkOrderExtensionAlarmTask.class);
            workOrderExtensionAlarmTask.setInterrupt(false);
            //开始执行之前先写定时任务日志记录，记录开始时间
           // super.beforeExecute();
            //将参数做相应的类型转化，然后调用对应Task的函数
            JobParams parm = new JobParams();
            parm.setOrgCode(this.getParam1());
            User user = new User();
   	        user.setUserCode("job");
   	        user.setOrgCode(this.param1);
            SessionUtils.setUser(user) ;
            //执行业务操作
            workOrderExtensionAlarmTask.process(parm);
            //业务执行完之后写定时任务日志记录，记录结束时间以及运行结果
          //  super.afterExecute(BaseJob.JOB_RESULT_SUCCESS);
        } catch (Throwable e) {
            try {
                logger.error(e.getMessage(), e);
              //  super.afterExecute(BaseJob.JOB_RESULT_FAILURE,e.getMessage());
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("interrupt job...");
        workOrderExtensionAlarmTask.setInterrupt(true);
    }
}
