package cc.oit.bsmes.job.base.parent;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.job.base.dao.SchedulerLogDAO;
import cc.oit.bsmes.job.base.model.SchedulerLog;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.net.InetAddress;
import java.util.Date;

public abstract class BaseJob extends QuartzJobBean{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static String JOB_RESULT_SUCCESS="SUCCESS";
	public static String JOB_RESULT_FAILURE="FAILURE";
	public static String JOB_RESULT_EXCEPTION="EXCEPTION";
	
	protected String id;
	

	//计划ID
	protected String schedulerId;
	//
	protected String jobName;
	//
	protected String jobDesc;
	
	protected String param1;
	
	protected String param2;
 
	protected SchedulerLogDAO scheduleJobDao;

	private String hostName;
	private String hostAddress;
	
	protected Date startTime;
	
	public BaseJob() {
		try {
			this.hostName = InetAddress.getLocalHost().getHostName();
			this.hostAddress = InetAddress.getLocalHost().getHostAddress();
			this.scheduleJobDao=(SchedulerLogDAO)ContextUtils.getBean("schedulerLogDAO");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String getSchedulerId() {
		return schedulerId;
	}

	public void setSchedulerId(String schedulerId) {
		this.schedulerId = schedulerId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public SchedulerLogDAO getScheduleJobDao() {
		return scheduleJobDao;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
 
	protected abstract void executeInternal(JobExecutionContext context) throws JobExecutionException;
	
	protected void beforeExecute(){
		this.startTime = new Date(System.currentTimeMillis());		
		SchedulerLog scheduleLog = new SchedulerLog();
		scheduleLog.setFlag(true);
		scheduleLog.setSchedulerId(schedulerId);
		scheduleLog.setJobName(jobName);
		scheduleLog.setJobDesc(jobDesc);
		scheduleLog.setHostName(hostName);
		scheduleLog.setHostAddress(hostAddress);
		scheduleLog.setPrevStartTime(startTime);	 
		scheduleLog.setCreateUserCode("job");
		scheduleLog.setModifyUserCode("job");
		scheduleLog.setOrgCode(this.param1);
		scheduleJobDao.insert(scheduleLog);		
	     User user = new User();
	     user.setUserCode("job");
	     user.setOrgCode(this.param1);
         SessionUtils.setUser(user) ;
	     
		this.id = scheduleLog.getId();
	}
	
	protected void afterExecute(String result){
		Date now = new Date(System.currentTimeMillis());
		
		SchedulerLog scheduleLog = new SchedulerLog();
		scheduleLog.setSchedulerId(schedulerId);
		scheduleLog.setJobName(jobName);
		scheduleLog.setJobDesc(jobDesc);
		scheduleLog.setHostName(hostName);
		scheduleLog.setHostAddress(hostAddress);		
		scheduleLog.setId(id);
		scheduleLog.setPrevStartTime(startTime);
		scheduleLog.setPrevEndTime(now);
		scheduleLog.setFlag(false);
		scheduleLog.setPrevResult(result);		
		scheduleJobDao.update(scheduleLog);
		scheduleJobDao.deleteOldLOg();
	}
	
	protected void afterExecute(String result,String errorMessage){
		Date now = new Date(System.currentTimeMillis());
		
		SchedulerLog scheduleLog = new SchedulerLog();
		scheduleLog.setSchedulerId(schedulerId);
		scheduleLog.setJobName(jobName);
		scheduleLog.setJobDesc(jobDesc);
		scheduleLog.setHostName(hostName);
		scheduleLog.setHostAddress(hostAddress);		
		scheduleLog.setId(id);
		scheduleLog.setPrevStartTime(startTime);
		scheduleLog.setPrevEndTime(now);
		scheduleLog.setFlag(false);
		scheduleLog.setPrevResult(result);	
		if(errorMessage!=null)
		{
			if(errorMessage.length()>1000)
			{
				errorMessage=errorMessage.substring(0, 1000);
			} 
			 
			
			
		}
		scheduleLog.setErrorMessage(errorMessage);
		scheduleJobDao.update(scheduleLog);
		scheduleJobDao.deleteOldLOg();
	}

	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this);
	}

}
