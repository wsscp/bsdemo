package cc.oit.bsmes.job.base.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.job.base.model.SchedulerLog;
 
public interface SchedulerLogDAO extends BaseDAO<SchedulerLog> {
 public void deleteOldLOg();
}
