package cc.oit.bsmes.pro.service;

import java.util.List;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProcessInOutBz;

/**
 * @author 陈翔
 */
public interface ProcessInOutBzService extends BaseService<ProcessInOutBz> {
	/**
	 * 
	 * <p>
	 * 根据工序ID获取标准投入产出
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processId
	 */
	public List<ProcessInOutBz> getByProcessId(String processId);
	
	public List<ProcessInOutBz> getByProcessId(String processId, int start,int limit, List<Sort> sortList);
	
	public int countByProcessId(String processId);
	
	ProcessInOutBz getProcessOutByProcessId(String processId);

	public void deleteByProcessId(String processId);
}