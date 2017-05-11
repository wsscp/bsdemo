package cc.oit.bsmes.job.tasks;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cc.oit.bsmes.bas.service.SysMessageService;
import cc.oit.bsmes.job.base.dao.SchedulerSettingDAO;
import cc.oit.bsmes.job.base.model.SchedulerSetting;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.pla.oacalculator.OACalculatorHandler;

@Service
public class OaScheduleTask extends BaseSimpleTask {

	@Resource
	private OACalculatorHandler oACalculatorHandler;
	@Resource
	private SysMessageService sysMessageService;
	@Resource
	private SchedulerSettingDAO schedulerSettingDAO;
	@Resource
	private TaskExecutor taskExecutor;// 线程池

	@Override
	@Transactional(rollbackFor = { Exception.class }, readOnly = false)
	public void process(JobParams params) {
		String orgCode = params.getOrgCode();
		try {

			SchedulerSetting schedulerSetting = new SchedulerSetting();
			schedulerSetting.setName("PlanScheduleJob");
			List<SchedulerSetting> schedulerSettingArray = schedulerSettingDAO.find(schedulerSetting);
			if (schedulerSettingArray.size() > 0) {
				schedulerSetting = schedulerSettingArray.get(0);
			} else {
				return;
			}

			// 结束时间为空，且开始时间不为空，且开始时间距离当前相差比较久了2个小时
			if (null == schedulerSetting.getSimpleEndTime() && null != schedulerSetting.getSimpleStartTime()
					&& (new Date().getTime() - schedulerSetting.getSimpleStartTime().getTime()) < 1000 * 60 * 60 * 2) {
				return;
			} else { // 另起线程更新时间：多线程问题，另一个任务判断正确的时间
				schedulerSetting.setSimpleStartTime(new Date());
				schedulerSetting.setSimpleEndTime(null);
				taskExecutor.execute(new UpSchedulerSetting(schedulerSetting));
			}

			oACalculatorHandler.analysisOrderToProcess(orgCode);
			oACalculatorHandler.calculatorOA(orgCode);

			schedulerSetting.setSimpleEndTime(new Date());
			schedulerSettingDAO.update(schedulerSetting);
			sysMessageService.sendMessage("admin", "OA", "OA计算已完成！");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}

		// resourceCache.init();
		// String orgCode = params.getOrgCode();
		//
		// customerOrderItemProDecService.analysisOrderToProcess(resourceCache,
		// orgCode);
		// equipInfoService.initEquipLoad(orgCode);
		// try {
		// customerOrderPlanService.calculatorOA(resourceCache, orgCode);
		// } catch (Exception e) {
		// logger.error(e.getLocalizedMessage(), e);
		// }
	}

	public class UpSchedulerSetting implements Runnable {
		private SchedulerSetting schedulerSetting;

		public UpSchedulerSetting(SchedulerSetting schedulerSetting) {
			this.schedulerSetting = schedulerSetting;
		}

		public void run() {
			try {
				updateSchedulerSetting(schedulerSetting);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = { Exception.class }, readOnly = false)
		public void updateSchedulerSetting(SchedulerSetting schedulerSetting) {
			schedulerSettingDAO.updateSimpleTime(schedulerSetting);
		}
	}
}
