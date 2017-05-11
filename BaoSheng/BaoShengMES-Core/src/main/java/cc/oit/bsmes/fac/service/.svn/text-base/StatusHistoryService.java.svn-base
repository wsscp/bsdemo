package cc.oit.bsmes.fac.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.model.EventStore;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.fac.model.LineChart;
import cc.oit.bsmes.fac.model.StatusHistory;
import cc.oit.bsmes.fac.model.StatusHistoryChart;

/**
 * 
 * TODO(设备状态历史)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-6-3 下午5:30:30
 * @since
 * @version
 */

public interface StatusHistoryService extends BaseService<StatusHistory> {
	/**
	 * 
	 * <p>
	 * 根据设备code 和所属机构查询设备状态历史记录，默认查询当天的记录
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-6-3 下午5:32:15
	 * @param statusHistory
	 * @return
	 * @see
	 */
	public List<StatusHistory> getByCode(StatusHistory statusHistory);

	/**
	 * 设备状态监控历史: 加工时间分析/设备OEE - 根据设备code获取某段时间里，设备的工作情况
	 * 
	 * @author DingXintao
	 * 
	 * */
	public List<LineChart> getByTimeAndStatus(StatusHistory statusHistory, String type, Boolean isOEE);

	/**
	 * 设备状态监控历史: 状态历史百分比 - 根据设备code获取某段时间里，获取设备各种状态下的工作时间
	 * 
	 * @author DingXintao
	 * 
	 * */
	public Map<EquipStatus, StatusHistoryChart> getStatusPercent(StatusHistory statusHistory);

	/**
	 * <p>
	 * 查询设备运行时间
	 * </p>
	 * 
	 * @author zhangdongping
	 * @date 2014-7-18 上午11:19:42
	 * @param equipCode 设备编码
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 * @see
	 */
	public double getEquipTotalWorkHour(String equipCode, Date startTime, Date endTime);

	public List<EventStore> getEvent(StatusHistory statusHistory);

	public void exportToXls(OutputStream os, String sheetName, StatusHistory statusHistory) throws IOException,
			RowsExceededException, WriteException;

	/**
	 * <p>
	 * 查询停机设备
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2015-1-13 上午11:30:46
	 * @return
	 * @see
	 */
	public List<StatusHistory> findShutDownReason(StatusHistory param, Integer start, Integer limit, Object object,
			boolean ispage);

	public Integer countShutDownReason(StatusHistory param);

	public Map<String, Object> getIdleDataByEquipAndLimitTime(StatusHistory statusHistory);

	 public List<LineChart> getEquipYield(String equipCode,
			StatusHistory statusHistory, String type, Boolean oEE);

}
