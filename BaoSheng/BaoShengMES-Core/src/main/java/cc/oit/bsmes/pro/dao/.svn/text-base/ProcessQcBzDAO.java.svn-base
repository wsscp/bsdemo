package cc.oit.bsmes.pro.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessQcBz;

/**
 * @author 陈翔
 */
public interface ProcessQcBzDAO extends BaseDAO<ProcessQcBz> {
	
	/**
	 * 更新状态
	 * @param idArray,processId,status
	 */
	public void updateProcessQcBzStatue(Map<String, Object> findParams);
	
	/**
	 * PLM同步：更新单位
	 * @param qc
	 */
	public void updateProcessQcBzDataUnit(ProcessQcBz qc);
	
	/**
	 * 根据工艺参数删除所有的相关质量参数
	 * @param processId
	 */
	public void deleteProcessQCbzByProcessId(String processId);
	
	/**
	 * 批量插入工艺参数
	 * @param processId
	 */
	public void batchInsertProcessQcBz(@Param("processQcBzs")List<ProcessQcBz> processQcBzs);
	
	public List<ProcessQcBz> getByProcessBzId(String processId);
}