package cc.oit.bsmes.pro.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessQc;

public interface ProcessQcDAO extends BaseDAO<ProcessQc> {

	public void insertByWorkOrderNO(String workOrderNO, String weight, String checkItemCode, String userCode);

	public List<ProcessQc> getByWorkOrderNo(String workOrderNo);
	
	public List<ProcessQc> getByProcessId(Map<String, Object> findParams);

	public List<ProcessQc> getByProcessCode(Map<String, Object> findParams);

	public List<ProcessQc> getByEquipLineAndProcessId(String equipLineCode, String processId);

	public List<ProcessQc> getByWorkOrderNoAndDistinctEqipCode(String workOrderNo, String equipCode, String type);

	public List<ProcessQc> getByEquipCodeAndProcessCodeAndReceiptCode(Map<String, Object> findParams);

	public List<Map<String, String>> getEmphShow(String processId, String productLineCode);

	public void insertBatch(@Param("list") List<ProcessQc> list);

	public void insertBackGround(String newProcessId, List<String> ids);
	
	public List<Map<String,Object>> getQcInfoByTaskId(String taskId);
}
