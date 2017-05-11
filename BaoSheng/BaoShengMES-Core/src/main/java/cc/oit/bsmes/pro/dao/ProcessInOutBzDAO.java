package cc.oit.bsmes.pro.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessInOutBz;

/**
 * @author 陈翔
 */
public interface ProcessInOutBzDAO extends BaseDAO<ProcessInOutBz> {
	
	int countByProcessId(String processId);

	ProcessInOutBz getProcessOutByProcessId(String processId);
	
	List<ProcessInOutBz> getProcessInOutBzByProcessId(String processId);
	
	public void deleteByProcessId(String processId);
}