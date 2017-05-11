package cc.oit.bsmes.wwalmdb.service;

import java.util.Date;
import java.util.List;

import cc.oit.bsmes.interfaceWWIs.model.SparkHistory;

public interface SparkHistoryService {
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author JinHanyun
	 * @date 2014-3-18 下午5:09:46
	 * @param lastExecuteDate
	 * @return
	 * @see
	 */
	public List<SparkHistory> findByEventStamp(Date lastExecuteDate,Integer batchSize);


	 
}
