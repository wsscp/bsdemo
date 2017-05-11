package cc.oit.bsmes.job.jobs;

import java.util.Date;

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
import cc.oit.bsmes.job.tasks.PushFinshOrderToERPTask;

/**
 * 完成订单信息推动到ERP
 * <p style="display:none">modifyRecord</p>
 *
 * @author 宋前克
 * @date 2016-05-25
 */
@Service
public class PushFinshOrderToERPJob extends BaseJob implements StatefulJob, InterruptableJob {

    private PushFinshOrderToERPTask pushFinshOrderToERPTask;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("interrupt job...");
        pushFinshOrderToERPTask.setInterrupt(true);
    }

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
    	
		System.err.println("任务开始：" +(new Date()).toString());

        logger.info("entering job...");
        try {
        	pushFinshOrderToERPTask = (PushFinshOrderToERPTask) ContextUtils.getBean(PushFinshOrderToERPTask.class);
        	pushFinshOrderToERPTask.setInterrupt(false);
            Integer batchSize = NumberUtils.createInteger(this.getParam2());
            //执行业务操作
            JobParams param = new JobParams();
            param.setBatchSize(batchSize);
            param.setOrgCode(this.getParam1());
            pushFinshOrderToERPTask.process(param);

        } catch (Throwable e) {
            try {
                logger.error(e.getMessage(), e);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
