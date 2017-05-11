package cc.oit.bsmes.job.base.factory;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.job.base.dao.SchedulerSettingDAO;
import cc.oit.bsmes.job.base.model.SchedulerSetting;
import lombok.Setter;
import org.apache.commons.beanutils.BeanUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.*;

/**
 * 初始化job配置信息
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author zhangdongping
 * @date 2013-12-11 下午1:09:22
 * @since
 * @version
 */
 
public class InitSchedulerFactoryBean extends SchedulerFactoryBean {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * beans.xml文件中,InitSchedulerFactoryBean的beanId属性值.
	 */
	public static final String QUARTZ_SCHEDULER_BEAN_NAME = "quartzScheduler";
	public static final String SCHEDULERID = "schedulerId";
	public static final String TRIGGERNAME = "triggerName";
	public static final String TRIGGERGROUP = "triggerGroup";
	public static final String STARTTIME = "startTime";
	public static final String ENDTIME = "endTime";
	public static final String REPEATCOUNT = "repeatCount";
	public static final String REPEATINTERVEL = "repeatInterval";
	public static final String PARAM1 = "param1";
	public static final String PARAM2 = "param2";

	private static final Map<Integer, String> triggerStateMap = new HashMap<Integer, String>();
	static {
		triggerStateMap.put(Trigger.STATE_NONE, "停用");
		triggerStateMap.put(Trigger.STATE_NORMAL, "等待");
		triggerStateMap.put(Trigger.STATE_BLOCKED, "运行中");
		triggerStateMap.put(Trigger.STATE_COMPLETE, "完成");
		triggerStateMap.put(Trigger.STATE_ERROR, "异常状态");
		triggerStateMap.put(Trigger.STATE_PAUSED, "挂起");
	}
	@Setter
	private List<JobDetail> jobDetails;
	@Resource
	private SchedulerSettingDAO schedulerSettingDao;

	@Setter
	private String group = Scheduler.DEFAULT_GROUP;

	/**
	 * 根据TriggerState状态码得到状态名称.
	 * 
	 * @param triggerState
	 *            状态码. 引用Trigger中的状态码定义.
	 * @return 返回状态名称.
	 * @throws IllegalArgumentException
	 *             如果传入的triggerState不在Trigger中定义,抛出此异常.
	 * @see org.quartz.Trigger
	 */
	public static String getTriggerStateName(Integer triggerState) {
		if (!triggerStateMap.containsKey(triggerState)) {
			throw new IllegalArgumentException("不合法的TriggerState:"
					+ triggerState);
		}
		return triggerStateMap.get(triggerState);
	}

	public InitSchedulerFactoryBean() {

	}

	@Override
	protected void startScheduler(Scheduler arg0, int arg1)
			throws SchedulerException {
        if (jobDetails == null) {
            return;
        }
		// 根据设定的Name在数据库中查找定时任务设定，对于一个定时任务可以配置多个触发器
		for (JobDetail jobDetail : jobDetails) {
			SchedulerSetting schedulerSetting = new SchedulerSetting();
			schedulerSetting.setName(jobDetail.getName());
			List<SchedulerSetting> schedulerSettings = schedulerSettingDao
					.get(schedulerSetting);
			for (SchedulerSetting setting : schedulerSettings) {
				JobDetail newJobDetail = new JobDetail();
				try {
					BeanUtils.copyProperties(newJobDetail, jobDetail);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}

				logger.debug("Scheduler Setting->" + setting);
				try {
					startJob(setting, newJobDetail); 
				} catch (Exception e) {
					// TODO Auto-generated catch block
					throw new SchedulerException();
				}
			}
		}
		super.startScheduler(arg0, arg1);
	}

	private void schedule(String cronExpression, JobDetail jobDetail) {

		// 为了集群中各个节点中的触发器名字统一，统一使用配置的schedulerID来做触发器名称
		String name = jobDetail.getName();
		try {
			this.getScheduler().addJob(jobDetail, true);

			CronTrigger cronTrigger = new CronTrigger(name,
					jobDetail.getGroup(), jobDetail.getName(),
					jobDetail.getGroup());
			cronTrigger.setCronExpression(cronExpression);

			addOrUpdateTrigger(cronTrigger.getName(), cronTrigger.getGroup(),
					cronTrigger);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void schedule(Map<String, Object> map, JobDetail jobDetail) {

		// 实例化SimpleTrigger
		SimpleTrigger simpleTrigger = new SimpleTrigger();

		// 这些值的设置也可以从外面传入，这里采用默放值
		simpleTrigger.setJobName(jobDetail.getName());
		simpleTrigger.setJobGroup(jobDetail.getGroup());

		// 设置Trigger分组
		simpleTrigger.setGroup(jobDetail.getGroup());
		simpleTrigger.setRepeatInterval(1000L);

		// 为了集群中各个节点中的触发器名字统一，统一使用配置的schedulerID来做触发器名称
		simpleTrigger.setName(jobDetail.getName());

		// 设置开始时间
		Date now = Calendar.getInstance().getTime();
		Date tempDate = (Date) map.get(InitSchedulerFactoryBean.STARTTIME);
		if (tempDate != null) {
			if (tempDate.getTime() < now.getTime()) {
				// long ts = TriggerUtils.getNextGivenMinuteDate(null,
				// 1).getTime();
				// 一分钟以后启动job
				// 由于cluster环境启动有先后顺序，导致所有job都在同一台机器上执行
				// simpleTrigger.setStartTime(new Date(now.getTime() + 10000));
				simpleTrigger.setStartTime(tempDate);
			} else {
				simpleTrigger.setStartTime(tempDate);
			}
		}

		// 设置结束时间
		tempDate = (Date) map.get(InitSchedulerFactoryBean.ENDTIME);
		if (tempDate != null) {
			simpleTrigger.setEndTime(tempDate);
		}

		// 设置执行次数
		Integer tempInt = (Integer) map
				.get(InitSchedulerFactoryBean.REPEATCOUNT);
		if (tempInt != null && tempInt > 0) {
			simpleTrigger.setRepeatCount(tempInt);
		} else {
			simpleTrigger.setRepeatCount(0); 
		}

		// 设置执行时间间隔
		tempInt = (Integer) map.get(InitSchedulerFactoryBean.REPEATINTERVEL);
		if (tempInt != null && tempInt > 0) {
			simpleTrigger.setRepeatInterval(tempInt);
		}

		try {
			this.getScheduler().addJob(jobDetail, true);

			addOrUpdateTrigger(simpleTrigger.getName(),
					simpleTrigger.getGroup(), simpleTrigger);

		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unused")
	private void pauseTrigger(String triggerName, String group) {
		try {
			this.getScheduler().pauseTrigger(triggerName, group);// 停止触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unused")
	private void resumeTrigger(String triggerName, String group) {
		try {
			this.getScheduler().resumeTrigger(triggerName, group);// 重启触发器
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean removeTrigger(String triggerName, String group) {
		try {
			this.getScheduler().pauseTrigger(triggerName, group);// 停止触发器
			boolean result = this.getScheduler().unscheduleJob(triggerName,
					group);// 移除触发器
			return result;
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	private void addOrUpdateTrigger(String triggerName, String group,
			Trigger trigger) {
		try {
			Date theDate = this.getScheduler().rescheduleJob(triggerName,
					group, trigger);

			// 如果该触发器已经存在，则首先添加该触发器，然后再做rescheduleJob
			if (theDate == null) {
				this.getScheduler().scheduleJob(trigger);
				this.getScheduler().rescheduleJob(triggerName, group, trigger);
			}
		} catch (SchedulerException e) {
			throw new RuntimeException(e);
		}
	}

	private JobDetail getJobDetail(String jobName) {
        if (jobDetails == null) {
            return null;
        }
		for (JobDetail jobDetail : jobDetails) {
			if (jobName.equals(jobDetail.getName())) {
				JobDetail newJobDetail = new JobDetail();
				try {
					BeanUtils.copyProperties(newJobDetail, jobDetail);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				return newJobDetail;
			}
		}

		return null;
	}

	private JobDetail getRunningJobDetail(String schedulerId) {
		try {
			return this.getScheduler().getJobDetail(schedulerId, this.group);
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	
	public void startJob(SchedulerSetting setting, JobDetail jobDetail)
			throws Exception {
		logger.debug("start job: " + setting.getSchedulerId());
		String jobGroup = this.group;

		if (setting.getEnable()) {

			// Cron触发器
			if (setting.isCronType()) {
				jobDetail.getJobDataMap().put(SCHEDULERID,
						setting.getSchedulerId());
				jobDetail.getJobDataMap().put(PARAM1, setting.getParam1());
				jobDetail.getJobDataMap().put(PARAM2, setting.getParam2());
				jobDetail.setName(setting.getSchedulerId());
				jobDetail.setGroup(jobGroup);
				schedule(setting.getCronExpress(), jobDetail);
				// Simple触发器
			} else if (setting.isSimpleType()) {
 
				jobDetail.getJobDataMap().put(SCHEDULERID,
						setting.getSchedulerId());
				jobDetail.getJobDataMap().put(PARAM1, setting.getParam1());
				jobDetail.getJobDataMap().put(PARAM2, setting.getParam2());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(STARTTIME, setting.getSimpleStartTime());
				map.put(ENDTIME, setting.getSimpleEndTime());
				map.put(REPEATCOUNT, setting.getSimpleRepeatCount());
				map.put(REPEATINTERVEL, setting.getSimpleRepeatIntervel());
				jobDetail.setName(setting.getSchedulerId());
				jobDetail.setGroup(jobGroup);
				schedule(map, jobDetail);
				setting.setEnable(false);
				schedulerSettingDao.update(setting);				 
				 
			}
			// 如果定时器已经被禁用，则从将定时器移除
		} else {
			removeTrigger(setting.getSchedulerId(), jobGroup);
		}
	}

	/**
	 * user resume job
	 *
	 * @param id
	 * @throws Exception
	 */
	public void startJob(String id) throws Exception {
		// 更新SchedulerSetting状态.
		SchedulerSetting setting = this.schedulerSettingDao
				.getById(id);
		if (setting == null)
			throw new RuntimeException("no schedule info");
//		JobDetail job = this.getScheduler().getJobDetail(setting.getName(), this.group);
//		if(job!=null)
//		{
//			 //
//		}
		int state=this.getScheduler().getTriggerState(setting.getName(),
				this.group);
		if(state!=-1)
		{
			throw new RuntimeException("已经有相同的任务在运行中，请稍后再试");
		}
		
		setting.setEnable(true);
		JobDetail jobDetail = getJobDetail(setting.getName());
		if (jobDetail == null) {
            jobDetail = (JobDetail) ContextUtils.getBean(id + "Detail");
        }
        if (jobDetail == null)
            throw new RuntimeException("no job info");
		startJob(setting, jobDetail);
	}

	/**
	 * user pause job
	 * 
	 * @param schedulerId
	 */
	public void stopJob(String schedulerId) {
		JobDetail jobDetail = getRunningJobDetail(schedulerId);
		if (jobDetail == null)
			throw new RuntimeException("no job detail");
		removeTrigger(jobDetail.getName(), jobDetail.getGroup());
	}

	public void removeJob(String schedulerId) throws SchedulerException {
		JobDetail jobDetail = getRunningJobDetail(schedulerId);
		this.getScheduler()
				.deleteJob(jobDetail.getName(), jobDetail.getGroup());
	}

	/**
	 * get job state
	 * 
	 * @param schedulerId
	 * @return
	 * @throws SchedulerException
	 */
	public int getJobState(String schedulerId) throws SchedulerException {
		JobDetail jobDetail = getRunningJobDetail(schedulerId);
		if (jobDetail == null)
			throw new RuntimeException("no job detail");
		return this.getScheduler().getTriggerState(jobDetail.getName(),
				jobDetail.getGroup());
	}

	// /**
	// * start job immediately
	// *
	// * @param schedulerId
	// */
	// public void startJobImmediate(String schedulerId) {
	// SchedulerSetting setting = this.schedulerSettingDao
	// .getById(schedulerId);
	// if (setting == null)
	// throw new RuntimeException("no schedule info");
	//
	// Trigger trigger = TriggerUtils.makeImmediateTrigger(1, 1000L);
	// trigger.setName(setting.getSchedulerId());
	// trigger.setJobName(setting.getSchedulerId());
	// trigger.setGroup(this.group);
	//
	// addOrUpdateTrigger(trigger.getName(), trigger.getGroup(), trigger);
	// }

	public void destroy() {
		logger.info("destroy quartz schedule...");

		try {
			this.getScheduler().shutdown();
			super.destroy();
		} catch (SchedulerException e) {
			logger.error(e.getMessage(), e);
		}

	}
}
