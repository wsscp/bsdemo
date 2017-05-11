package cc.oit.bsmes.pro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProcessInOutWip;

public interface ProcessInOutWipDAO extends BaseDAO<ProcessInOutWip>{

	public void insertAll(@Param("processInOutWip")List<ProcessInOut> processInOutWip);
	
	/**
	 * @Title:       getByProcessId
	 * @Description: TODO(工序ID获取投入产出)
	 * @param:       processId 工序ID
	 * @return:      List<ProcessInOut>   
	 * @throws
	 */
	public List<ProcessInOutWip> getByProcessId(String processId);
	
	
	/**
	 * <P>
	 * 根据工序ID获取工序的投入产出数据条数
	 * <P>
	 * @author 前克
	 * @date 2016-03-16
 	 * @param processId(工序ID)
	 * @return
	 */
	public int countByProcessId(String processId);
	
	public void deleteDate(String oldCraftsId);
}
