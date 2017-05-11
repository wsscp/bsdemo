package cc.oit.bsmes.job.base.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.job.base.model.SchedulerSetting;

/**
 * ISchedulerSettingDao
 * <p style="display:none">modifyRecord</p>
 * @author zhangdogping
 * @date 2013年12月31日 下午1:59:12
 * @since
 * @version
 */
public interface SchedulerSettingDAO extends BaseDAO<SchedulerSetting> {
	
	/**
	 * 更新开始结束时间
	 * */
	public void updateSimpleTime(SchedulerSetting schedulerSetting);

}
