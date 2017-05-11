package cc.oit.bsmes.pro.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessQCEqip;

public interface ProcessQCEqipDAO extends BaseDAO<ProcessQCEqip> {
	
	/**
	 * 
	 * <p>根据质量参数、生产单号查询相关生产设备</p> 
	 * @author JinHanyun
	 * @date 2014-3-20 下午3:46:36
	 * @param checkItemCode
	 * @param workOrderNo
	 * @return
	 * @see
	 */
	public List<String> getEquipCodeByWorkOrderNo(String checkItemCode,String workOrderNo);
	
	public void insertBatch( @Param("list") List<ProcessQCEqip> list);
	
	public List<ProcessQCEqip> getAllByOcId(Map<String, Object> findParams);

}
