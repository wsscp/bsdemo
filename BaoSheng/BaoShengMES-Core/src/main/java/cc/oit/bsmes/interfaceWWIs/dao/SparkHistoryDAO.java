package cc.oit.bsmes.interfaceWWIs.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.interfaceWWIs.model.SparkHistory;

/**
 * Created by zhangdongping on 14-3-13.
 */
public interface SparkHistoryDAO extends BaseDAO<SparkHistory> {

	List<SparkHistory> findByEventStamp(Map<String, Object> findParams);
}
