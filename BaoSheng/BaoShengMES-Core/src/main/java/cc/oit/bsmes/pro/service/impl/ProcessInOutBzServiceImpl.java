package cc.oit.bsmes.pro.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pro.dao.ProcessInOutBzDAO;
import cc.oit.bsmes.pro.model.ProcessInOutBz;
import cc.oit.bsmes.pro.service.ProcessInOutBzService;

/**
 * @author 陈翔
 */
@Service
public class ProcessInOutBzServiceImpl extends BaseServiceImpl<ProcessInOutBz> implements ProcessInOutBzService {

	@Resource
	private ProcessInOutBzDAO processInOutBzDAO;

	/**
	 * 
	 * <p>
	 * 根据工序ID获取标准投入产出
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processId
	 */
	public List<ProcessInOutBz> getByProcessId(String processId) {
		return processInOutBzDAO.getProcessInOutBzByProcessId(processId);
	}

	@Override
	public List<ProcessInOutBz> getByProcessId(String processId, int start,
			int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return getByProcessId(processId);
	}

	@Override
	public int countByProcessId(String processId) {
		return processInOutBzDAO.countByProcessId(processId);
	}

	@Override
	public ProcessInOutBz getProcessOutByProcessId(String processId) {
		return processInOutBzDAO.getProcessOutByProcessId(processId);
	}
	@Override
	public void deleteByProcessId(String processId){
		processInOutBzDAO.deleteByProcessId(processId);
	}
}