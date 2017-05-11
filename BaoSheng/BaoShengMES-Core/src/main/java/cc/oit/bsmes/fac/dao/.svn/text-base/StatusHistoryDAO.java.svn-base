/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */ 
package cc.oit.bsmes.fac.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.fac.model.StatusHistory;

/**
 * FacilityStatusHistoryDAO
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月31日 下午3:44:31
 * @since
 * @version
 */
public interface StatusHistoryDAO extends BaseDAO<StatusHistory> {

	/**
	 * 
	 * <p>根据设备ID和状态结束时间为空查找记录</p> 
	 * @author QiuYangjun
	 * @date 2014-3-4 下午4:14:38
	 * @param equipId
	 * @return
	 * @see
	 */
	public StatusHistory getByEquipIdAndNullEndTime(String equipId);

    public List<StatusHistory> getByCode(StatusHistory statusHistory);

	public List<StatusHistory> getByTimeAndStatus(StatusHistory statusHistory);

    public StatusHistory getLatestEndByEquipId(String equipId);

	public List<StatusHistory> getByLimitTime(Map<String, Object> map);

	public List<StatusHistory> getIdleDataByEquipAndLimitTime(StatusHistory statusHistory);

	public List<StatusHistory> findByParam(StatusHistory param);
	public int countByParam(StatusHistory param);
	
	public String getOutput(String equipCode);

	public int getEquipYield(StatusHistory statusHistory);
}
