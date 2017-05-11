package cc.oit.bsmes.wwalmdb.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.wwalmdb.model.AlarmHistory;

public interface AlarmHistoryDAO {
	
	/**
	 * 
	 * <p>查询当前未处理的生产监控异常</p> 
	 * @author JinHanyun
	 * @date 2014-3-18 下午4:06:55
	 * @return
	 * @see
	 */
	public List<AlarmHistory> findByEventStamp(Map<String,Object> findParams);

	public int count(Map<String, Object> findParams);

	public List<AlarmHistory> find(Map<String, Object> findParams);

}
