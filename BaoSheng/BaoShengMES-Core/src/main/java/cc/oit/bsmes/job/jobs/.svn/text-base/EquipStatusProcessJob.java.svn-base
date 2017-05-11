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
import cc.oit.bsmes.job.tasks.EquipStatusProcessTask;

/**
 * 设备状态监控
 * <p style="display:none">modifyRecord</p>
 *
 * @author zhangdongping
 * @date 2014-12-18 下午5:07:03
 */
@Service
public class EquipStatusProcessJob extends BaseJob implements StatefulJob, InterruptableJob {

    private EquipStatusProcessTask equipStatusProcessTask;
    private static Object synObject= new Object();

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        logger.info("interrupt job...");
        equipStatusProcessTask.setInterrupt(true);
    }

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        logger.info("entering EquipStatusProcessJob job...");
    	synchronized (synObject) {
    		 try {
    	         equipStatusProcessTask = (EquipStatusProcessTask) ContextUtils.getBean(EquipStatusProcessTask.class);
    	         equipStatusProcessTask.setInterrupt(false); 
    	         Integer batchSize = NumberUtils.createInteger(this.getParam2());
    	            //执行业务操作
    	          JobParams param = new JobParams();
    	          param.setBatchSize(batchSize);
    	          param.setOrgCode(this.getParam1());
    	          equipStatusProcessTask.process(param);             
    	        } catch (Throwable e) {
    	            try {
    	                logger.error(e.getMessage(), e); 
    	            } catch (Exception ex) {
    	                logger.error(ex.getMessage(), ex);
    	            }
    	        } 
		}

       
    }
}
