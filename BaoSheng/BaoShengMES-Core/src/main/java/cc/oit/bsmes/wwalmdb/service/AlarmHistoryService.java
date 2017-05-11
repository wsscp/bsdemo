package cc.oit.bsmes.wwalmdb.service;

import java.util.Date;
import java.util.List;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.wwalmdb.model.AlarmHistory;

public interface AlarmHistoryService {
	/**
	 * 
	 * <p>TODO(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author JinHanyun
	 * @date 2014-3-18 下午5:09:46
	 * @param lastExecuteDate
	 * @return
	 * @see
	 */
	public List<AlarmHistory> findByEventStamp(Date lastExecuteDate,Integer millisec,Integer batchSize);


	public int count(AlarmHistory findParams);

	public List<AlarmHistory> find(AlarmHistory findParams, Integer start, Integer limit, List<Sort> sortList);
}
