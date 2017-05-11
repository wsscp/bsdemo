package cc.oit.bsmes.pro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.DataStatus;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.pro.dao.ProcessQcBzDAO;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProcessQcBz;
import cc.oit.bsmes.pro.service.ProcessQcBzService;

/**
 * @author 陈翔
 */
@Service
public class ProcessQcBzServiceImpl extends BaseServiceImpl<ProcessQcBz> implements ProcessQcBzService {
	
	@Resource
	private ProcessQcBzDAO processQcBzDAO;

	/**
	 * 更新状态
	 * 
	 * @param idArray id数组
	 * @param processId 工序ID
	 * @param status 状态
	 * 
	 * */
	@Override
	public void updateProcessQcBzStatue(List<String> idArray, String processId,
			DataStatus status) {
		if (null == idArray || idArray.size() == 0) {
			return;
		}
		Map<String, Object> findParams = new HashMap<String, Object>();
		findParams.put("idArray", idArray);
		findParams.put("processId", processId);
		findParams.put("status", status.name());
		processQcBzDAO.updateProcessQcBzStatue(findParams);
	}

	/**
	 * PLM同步：更新单位
	 * 
	 * @param idArray id数组
	 * @param status 状态
	 * 
	 * */
	@Override
	public void updateProcessQcBzDataUnit(List<ProcessQcBz> qcArray) {
		for (ProcessQcBz qc : qcArray) {
			processQcBzDAO.updateProcessQcBzDataUnit(qc);
		}
	}
	
	/**
	 * 
	 * <p>
	 * 根据工序ID获取标准工艺参数
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processId
	 */
	@Override
	public List<ProcessQcBz> getByProcessId(String processId) {
		ProcessQcBz findParams = new ProcessQcBz();
		findParams.setProcessId(processId);
		return processQcBzDAO.getByProcessBzId(processId);
	}
	
	/**
	 * 根据工艺参数删除所有的相关质量参数
	 * @param processId
	 */
	public void deleteProcessQCbzByProcessId(String processId){
		processQcBzDAO.deleteProcessQCbzByProcessId(processId);
	}

	@Override
	public void batchInsertProcessQcBz(List<ProcessQcBz> processQcBzs) {
		processQcBzDAO.batchInsertProcessQcBz(processQcBzs);
	}
}