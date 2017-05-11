package cc.oit.bsmes.pro.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessQcValue;

import java.util.List;
import java.util.Map;

/**
 * ProcessQcValueDAO
 * 
 * @author chanedi
 * @date 2014年2月21日 上午11:47:53
 * @since
 * @version
 */
public interface ProcessQcValueDAO extends BaseDAO<ProcessQcValue> {

	public List<ProcessQcValue> findDistinctByWorkOrderNo(String workOrderNo);

	public void saveProcessQcValue(String checkItemCode, String qcValue, String qcResult, String checkEqipCode,
			String eqipCode, String workOrderNo, String creater, String detectType, Integer coilNum);

	// 检查是否录入 首检 下车检 上车检数据
	public int checkExistsInputQcValueByWoNo(Map<String, Object> paramMap);

	public List<ProcessQcValue> getLastByWorkOrderNoAndType(String workOrderNo);

	public int updateDA(String serialNum, String workOrderNo);


	public List<ProcessQcValue> getQaList(ProcessQcValue findParams);

	public int countQaList(ProcessQcValue findParams);

	
	public String queryProcessQcValueCoilNum(String workOrderNo,String type,String equipCode);

}
