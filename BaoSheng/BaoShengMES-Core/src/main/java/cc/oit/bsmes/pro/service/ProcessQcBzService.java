package cc.oit.bsmes.pro.service;

import java.util.List;

import cc.oit.bsmes.common.constants.DataStatus;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.pro.model.ProcessQcBz;

/**
 * @author 陈翔
 */
public interface ProcessQcBzService extends BaseService<ProcessQcBz> {
	
	/**
	 * 更新状态
	 * @param idArray
	 * @param processId
	 * @param status
	 */
	public void updateProcessQcBzStatue(List<String> idArray, String processId, DataStatus status);
	
	/**
	 * PLM 同步 : 更新单位
	 * @param qcArray
	 */
	public void updateProcessQcBzDataUnit(List<ProcessQcBz> qcArray);
	
	/**
	 * 
	 * <p>
	 * 根据工序ID获取标准工艺参数
	 * </p>
	 * 
	 * @author DingXintao
	 * @param processId
	 */
	public List<ProcessQcBz> getByProcessId(String processId);
	
	/**
	 * 根据工艺参数删除所有的相关质量参数
	 * @param processId
	 */
	public void deleteProcessQCbzByProcessId(String processId);
	
	/**
	 * 批量插入工艺参数
	 * @param processId
	 */
	public void batchInsertProcessQcBz(List<ProcessQcBz> processQcBzs);
}