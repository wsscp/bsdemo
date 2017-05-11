package cc.oit.bsmes.fac.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cc.oit.bsmes.bas.model.DataDic;
import cc.oit.bsmes.bas.service.DataDicService;
import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.StandardParamCode;
import cc.oit.bsmes.common.constants.TermsCodeType;
import cc.oit.bsmes.common.model.EventStore;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.fac.dao.StatusHistoryDAO;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.LineChart;
import cc.oit.bsmes.fac.model.StatusHistory;
import cc.oit.bsmes.fac.model.StatusHistoryChart;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.StatusHistoryService;
import cc.oit.bsmes.interfaceWWAc.dao.EquipParamAcquisitionDAO;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamAcquisition;
import cc.oit.bsmes.interfaceWWAc.model.EquipParamHistoryAcquisition;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.wip.dao.WorkOrderDAO;
import cc.oit.bsmes.wip.model.GraphValue;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-6-3 下午5:34:58
 * @since
 * @version
 */
@Service
public class StatusHistoryServiceImpl extends BaseServiceImpl<StatusHistory> implements StatusHistoryService {

	@Resource
	private StatusHistoryDAO statusHistoryDAO;
	@Resource
	private DataAcquisitionService dataAcquisitionService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private WorkOrderDAO workOrderDAO;
	@Resource
	private DataDicService dataDicService;

	@Resource
	private EquipMESWWMappingService equipMESWWMappingService;

	@Resource
	private EquipParamAcquisitionDAO equipParamAcquisitionDAO;

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author leiwei
	 * @date 2014-6-3 下午5:35:10
	 * @param statusHistory
	 * @return
	 * @see cc.oit.bsmes.fac.service.StatusHistoryService#getByCode(cc.oit.bsmes.fac.model.StatusHistory)
	 */
	@Override
	public List<StatusHistory> getByCode(StatusHistory statusHistory) {
		return statusHistoryDAO.getByCode(statusHistory);
	}

	/**
	 * 设备状态监控历史: 时序图-getEvent
	 * 
	 * @author DingXintao
	 * 
	 * */
	@Override
	public List<EventStore> getEvent(StatusHistory statusHistory) {
		List<StatusHistory> totalList = statusHistoryDAO.getByTimeAndStatus(statusHistory);
		List<EventStore> storeList = new ArrayList<EventStore>();
		for (StatusHistory history : totalList) {
			if (history.getStartTime().getTime() < statusHistory.getStartTime().getTime()) {
				history.setStartTime(statusHistory.getStartTime());
			}
			if (history.getEndTime().getTime() > statusHistory.getEndTime().getTime()) {
				history.setEndTime(statusHistory.getEndTime());
			}
			if (history.getStartTime().getTime() == history.getEndTime().getTime()) {
				continue;
			}
			EventStore event = new EventStore();
			event.setResourceId(statusHistory.getEquipCode());
			event.setName(history.getStatus().name());
			event.setStartDate(DateUtils.convert(history.getStartTime(), DateUtils.DATE_TIME_FORMAT));
			event.setEndDate(DateUtils.convert(history.getEndTime(), DateUtils.DATE_TIME_FORMAT));
			storeList.add(event);
		}
		return storeList;
	}

	/**
	 * 设备状态监控历史: 状态历史百分比 - 根据设备code获取某段时间里，获取设备各种状态下的工作时间
	 * 
	 * @author DingXintao
	 * 
	 * */
	@Override
	public Map<EquipStatus, StatusHistoryChart> getStatusPercent(StatusHistory statusHistory) {
		// 0、返回的对象
		Map<EquipStatus, StatusHistoryChart> hisChartMap = new HashMap<EquipStatus, StatusHistoryChart>();
		// 1、查询出设备状态历史记录
		List<StatusHistory> hisArray = statusHistoryDAO.getByTimeAndStatus(statusHistory);
		// 2、遍历变更为三个记录
		for (StatusHistory his : hisArray) {
			Date start = his.getStartTime().before(statusHistory.getStartTime()) ?  statusHistory.getStartTime() : his.getStartTime();
			Date end = his.getEndTime().after(statusHistory.getEndTime()) ? statusHistory.getEndTime() : his.getEndTime();
			Double time = 0.0 + (end.getTime() - start.getTime());
			StatusHistoryChart hisChart = hisChartMap.get(his.getStatus());
			if (null == hisChart) {
				hisChart = new StatusHistoryChart();
				hisChart.setProcess(time);
				hisChart.setName(his.getStatus().toString());
				hisChartMap.put(his.getStatus(), hisChart);
			} else {
				hisChart.setProcess(hisChart.getProcess() + time);
			}
		}

		// 3、遍历状态，将没有的设置默认
		for (EquipStatus status : EquipStatus.values()) {
			if (null == hisChartMap.get(status)) {
				StatusHistoryChart hisChart = new StatusHistoryChart();
				hisChart.setName(status.toString());
				hisChart.setProcess(0.0);
				hisChartMap.put(status, hisChart);
			}
		}
		return hisChartMap;
	}
	
	
	/**
	 * @Title:       putHis2LineChartArray
	 * @Description: TODO(封装数据到返回对象)
	 * @param:       lineChartArray 返回的数据对象
	 * @param:       name x轴显示值
	 * @param:       timeStatus 当前添加对象的状态
	 * @param:       time 当前状态的时间
	 * @param:       oEE 是否OEE
	 * @return:      void   
	 * @throws
	 */
	private void putHis2LineChartArray(List<LineChart> lineChartArray, String name,
			EquipStatus timeStatus, Double time, Boolean oEE){
		// 1、循环所有的状态，否则页面状态没数据的时候会缺少数据
		for (EquipStatus status : EquipStatus.values()) {
		
			// 2、获取该状态的chart对象
			LineChart lineChart = null;
			for(LineChart chart : lineChartArray){
				if(chart.getStatus() == status){
					lineChart = chart;
					break;
				}
			}
			// 2.1、没有的时候new一个并赋值
			if(null == lineChart){
				lineChart = new LineChart();
				lineChart.setName(status.toString());
				lineChart.setStatus(status);
				lineChartArray.add(lineChart);
			}
			// 3、获取chart对象的data数据
			List<GraphValue> dataArray = lineChart.getData();
			GraphValue graphValue = null;
			for(GraphValue data : dataArray){
				if(data.getName().equals(name)){
					graphValue = data;
					break;
				}
			}
			// 3.1、没有的时候new一个data并赋值
			if(null == graphValue){
				graphValue = new GraphValue();
				graphValue.setName(name);
				dataArray.add(graphValue);
			}
			// 4、加工状态的时候往data里面补时间
			if(timeStatus == EquipStatus.IN_PROGRESS || timeStatus == EquipStatus.IN_DEBUG){
				graphValue.setOpenTime(graphValue.getOpenTime() + time);
			}
			// 4.1、设置总时间
			graphValue.setTotalTime(graphValue.getTotalTime() + time);
			// 5、判断是否OEE
			if(null != oEE && oEE && graphValue.getTotalTime() != 0){
				// 5.1、OEE设置百分比
				graphValue.setY(Double.parseDouble(String.format("%.2f", graphValue.getOpenTime() / graphValue.getTotalTime() * 100)));
			}else if(timeStatus == status){
				// 5.2、状态匹配的补充值显示y轴
				graphValue.setY(graphValue.getY()+this.millisecond2Hour(time));
			}
		}
	}

	/**
	 * 设备状态监控历史: 加工时间分析/设备OEE - 根据设备code获取某段时间里，设备的工作情况
	 * @author DingXintao
	 * @return 返回数据格式 [{ name: 'IDLE', data : [{ name : '2011', y : 7.0 }, { name : '2012', y : 6.9 }, ...]}, 
	 * 						{ name: 'DEBUG', data : [{ name : '2011', y : 7.0 }, { name : '2012', y : 6.9 }, ..]}, ...]
	 * */
	@Override
	public List<LineChart> getByTimeAndStatus(StatusHistory statusHistory, String type, Boolean oEE) {
		// 0、返回的对象：[{name : 状态, data : [{name : 时间, y : 12.5, ...}]}, ...]
		List<LineChart> lineChartArray = new ArrayList<LineChart>();
		// 1、查询出设备状态历史记录
		List<StatusHistory> hisArray = statusHistoryDAO.getByTimeAndStatus(statusHistory);
		// 2、遍历：根据开始结束时间的区间变更对象到hisChartMap
		// A、开始结束时间在一天内；B、开始结束时间跨天。
		
		for (StatusHistory his : hisArray) {
			
			Date timePreNext = his.getStartTime().before(statusHistory.getStartTime()) ? statusHistory.getStartTime() : his.getStartTime();
			if (StringUtils.equalsIgnoreCase(type, "WEEK")) {
				timePreNext = DateUtils.getStartWeektime(timePreNext); // 当周周一00:00:00
			} else if (StringUtils.equalsIgnoreCase(type, "MONTH")) {
				timePreNext = DateUtils.getStartMonthtime(timePreNext); // 当月一号00:00:00
			} else {
				timePreNext = DateUtils.getStartDatetime(timePreNext); // 默认按天:当天00:00:00
			}

			/**
			 * 循环说明：取一天的开始和结束时间，然后根据开始结束时间判断做中间差； 循环条件为：每天的开始时间小于真正结束时间，就做差处理
			 * */
			while (timePreNext.before(his.getEndTime())) {
				Date start = timePreNext;
				Date end = null;
				if (StringUtils.equalsIgnoreCase(type, "WEEK")) {
					end = DateUtils.getEndWeektime(timePreNext); // 当周周日23:59:59
				} else if (StringUtils.equalsIgnoreCase(type, "MONTH")) {
					end = DateUtils.getEndMonthtime(timePreNext); // 当月最后天23:59:59
				} else{
					end = DateUtils.getEndDatetime(timePreNext); // 默认按天:当天23:59:59
				}

				String timeKey = DateUtils.convert(timePreNext, DateUtils.DATE_SHORT_FORMAT); // 默认按天:每天的mapKey
				if (StringUtils.equalsIgnoreCase(type, "MONTH")) {
					timeKey = DateUtils.convert(timePreNext, DateUtils.MONTH_SHORT_FORMAT);// 每月的mapKey
				}
				String name = timeKey; // 横坐标下标名称，日，周，月
				if (StringUtils.equalsIgnoreCase(type, "WEEK")) {
					name = (start.before(statusHistory.getStartTime()) ? DateUtils.convert(
							statusHistory.getStartTime(), DateUtils.DATE_SHORT_FORMAT) : DateUtils.convert(start,
							DateUtils.DATE_SHORT_FORMAT))
							+ "~"
							+ (end.after(statusHistory.getEndTime()) ? DateUtils.convert(statusHistory.getEndTime(),
									DateUtils.DATE_SHORT_FORMAT) : DateUtils.convert(end, DateUtils.DATE_SHORT_FORMAT));
				}

				if (start.before(his.getStartTime())) {
					start = his.getStartTime();
				}
				if (end.after(his.getEndTime())) {
					end = his.getEndTime();
				}
				Double time = 0.0 + end.getTime() - start.getTime(); // 计算时间差
				
				this.putHis2LineChartArray(lineChartArray,  name, his.getStatus(), time, oEE);
				
				//this.putHisChart2Map(hisChartMap, timeKey, name, his.getStatus(), time, oEE); // 将历史记录对象放入处理Map

				if (StringUtils.equalsIgnoreCase(type, "WEEK")) {
					timePreNext = DateUtils.addDayToDate(timePreNext, 7); // 加7天
				} else if (StringUtils.equalsIgnoreCase(type, "MONTH")) {
					timePreNext = DateUtils.addDayToDate(timePreNext, DateUtils.getMonthDayCount(timePreNext)); // 加当月的天数
				} else { // 默认按天
					timePreNext = DateUtils.addDayToDate(timePreNext, 1); // 加1天
				}
			}
		}
		//List<StatusHistoryChart> chartArray = this.sort(hisChartMap);
		
		
		// 转换到js所需要的格式
		// 返回数据格式 [{ name: 'IDLE', data : [{ name : '2011', y : 7.0 }, { name : '2012', y : 6.9 }, ...]}, 
		//   			{ name: 'DEBUG', data : [{ name : '2011', y : 7.0 }, { name : '2012', y : 6.9 }, ..]}, ...]
//		List<Map<String, Object>> statusDatasArray = new ArrayList<Map<String, Object>>();
//		for (EquipStatus status : EquipStatus.values()) {
//			Map<String, Object> statusDatas = new HashMap<String, Object>();
//			List<GraphValue> data = new ArrayList<GraphValue>();
//			for(StatusHistoryChart chart : chartArray){
//				GraphValue graphValue = new GraphValue();
//				graphValue.setY(chart.getValue());
//				graphValue.setName(chart.getName()); 
//				data.add(graphValue);
//			}
//			statusDatas.put("name", status.toString());
//			statusDatas.put("data", data);
//			statusDatasArray.add(statusDatas);
//		}
		
		return lineChartArray;
	}

	/**
	 * 将历史记录对象放入处理Map
	 * 
	 * @param hisChartMap 历史记录对象Map
	 * @param timeKey 时间mapKey
	 * @param name 横坐标名字
	 * @param status 状态
	 * @param time 时间差，状态维持时间
	 * @param oEE 是否OEE查询
	 * **/
	private void putHisChart2Map(Map<String, StatusHistoryChart> hisChartMap, String timeKey, String name,
			EquipStatus status, Double time, Boolean oEE) {
		StatusHistoryChart hisChart = hisChartMap.get(timeKey);
		if (null == hisChart) {
			hisChart = new StatusHistoryChart();
			hisChart.setName(name);
			hisChartMap.put(timeKey, hisChart);
		}

		if (status == EquipStatus.CLOSED) {
			hisChart.setClosed(hisChart.getClosed() + time);
		} else if (status == EquipStatus.ERROR) {
			hisChart.setError(hisChart.getError() + time);
		} else if (status == EquipStatus.IDLE) {
			hisChart.setIdle(hisChart.getIdle() + time);
		} else if (status == EquipStatus.IN_DEBUG) {
			hisChart.setDebug(hisChart.getDebug() + time);
		} else if (status == EquipStatus.IN_MAINTAIN) {
			hisChart.setMaint(hisChart.getMaint() + time);
		} else if (status == EquipStatus.IN_PROGRESS) {
			hisChart.setProcess(hisChart.getProcess() + time);
		}

		if (null != oEE && oEE) { // OEE 计算百分比
			Double rate = Double
					.parseDouble(String.format(
							"%.2f",
							(hisChart.getProcess() + hisChart.getDebug())
									/ (hisChart.getProcess() + hisChart.getDebug() + hisChart.getClosed()
											+ hisChart.getError() + hisChart.getIdle()) * 100));
			hisChart.setValue(rate);
		}else{
			hisChart.setValue(hisChart.getValue() + time);
		}
	}

	/**
	 * 排序并处理
	 * */
	private List<StatusHistoryChart> sort(Map<String, StatusHistoryChart> map) {
		List<StatusHistoryChart> hisChartArray = new ArrayList<StatusHistoryChart>();

		// 1、先排序
		List<Map.Entry<String, StatusHistoryChart>> mapEntryArray = new ArrayList<Map.Entry<String, StatusHistoryChart>>(
				map.entrySet());
		Collections.sort(mapEntryArray, new Comparator<Map.Entry<String, StatusHistoryChart>>() {
			public int compare(Map.Entry<String, StatusHistoryChart> o1, Map.Entry<String, StatusHistoryChart> o2) {
				return (Integer.parseInt(o1.getKey()) - Integer.parseInt(o2.getKey()));
			}
		});

		// 2、将毫秒转化成秒
		for (Entry<String, StatusHistoryChart> mapEntry : mapEntryArray) {
			StatusHistoryChart hisChart = mapEntry.getValue();
			hisChart.setClosed(this.millisecond2Hour(hisChart.getClosed()));
			hisChart.setError(this.millisecond2Hour(hisChart.getError()));
			hisChart.setIdle(this.millisecond2Hour(hisChart.getIdle()));
			hisChart.setDebug(this.millisecond2Hour(hisChart.getDebug()));
			hisChart.setMaint(this.millisecond2Hour(hisChart.getMaint()));
			hisChart.setProcess(this.millisecond2Hour(hisChart.getProcess()));
			hisChartArray.add(hisChart);
		}
		return hisChartArray;
	}

	/**
	 * 毫秒转化成小时，保留两位小数
	 * 
	 * @param millisecond 毫秒
	 * */
	private Double millisecond2Hour(Double millisecond) {
		return Double.parseDouble(new DecimalFormat("#0.00").format(millisecond / 1000 / 60 / 60));
	}

	@Override
	public double getEquipTotalWorkHour(String equipCode, Date startTime, Date endTime) {
		StatusHistory statusHistory = new StatusHistory();
		statusHistory.setOrgCode(SessionUtils.getUser().getOrgCode());
		statusHistory.setEquipCode(equipCode);
		statusHistory.setStartTime(startTime);
		statusHistory.setEndTime(endTime);
		List<StatusHistory> totalList = statusHistoryDAO.getByTimeAndStatus(statusHistory);
		List<StatusHistory> statusHistoryList = processRealStatus(statusHistory, totalList);
		if (CollectionUtils.isEmpty(statusHistoryList)) {
			return 0;
		}
		double totalTime = 0;
		for (StatusHistory history : statusHistoryList) {
			if (history.getStartTime().getTime() < statusHistory.getStartTime().getTime()) {
				history.setStartTime(statusHistory.getStartTime());
			}
			if (history.getEndTime().getTime() > statusHistory.getEndTime().getTime()) {
				history.setEndTime(statusHistory.getEndTime());
			}
			Long useTime = DateUtils.getMinuteDiff(history.getStartTime(), history.getEndTime());
			if (useTime != null && useTime > 0) {
				if (StringUtils.equals(history.getStatus().name(), EquipStatus.IN_PROGRESS.name())
						|| StringUtils.equals(history.getStatus().name(), EquipStatus.IN_DEBUG.name())
						|| StringUtils.equals(history.getStatus().name(), EquipStatus.IDLE.name())) {
					totalTime += useTime;
				}

			}
		}
		return totalTime / 60d;
	}

	private List<StatusHistory> processRealStatus(StatusHistory statusHistory, List<StatusHistory> mesList) {
		Date endTime = statusHistory.getEndTime();
		Date startTime = statusHistory.getStartTime();
		long time = (endTime.getTime() - startTime.getTime()) / 1000;
		int cycleCount = 0;
		BigDecimal frequence = new BigDecimal(120);
		BigDecimal timeFrequence = new BigDecimal(time);
		// if(time>0&&time<60){
		// //一分钟之内
		// timeFrequence = new BigDecimal(60);
		// }else if(time>=60&&time<3600){
		// // //一小时之内
		// timeFrequence = new BigDecimal(3600);
		// }else if(time>=3600&&time<86400){
		// //一天之内
		// timeFrequence = new BigDecimal(86400);
		// }else if(time>=86400&&time<604800){
		// //一星期之内
		// timeFrequence = new BigDecimal(604800);
		// }else if(time>=604800&&time<2678400){
		// //一个月之内
		// timeFrequence = new BigDecimal(2678400);
		// }else if(time>=2678400&&time<32140800){
		// //一年之内
		// timeFrequence = new BigDecimal(32140800);
		// }else{
		// //超过一年
		// timeFrequence = frequence.multiply(new BigDecimal(200));
		// }
		if (timeFrequence.divide(frequence, 0, BigDecimal.ROUND_HALF_EVEN).intValue() < 5000) {
			cycleCount = timeFrequence.divide(frequence, 0, BigDecimal.ROUND_HALF_EVEN).intValue();
		} else {
			cycleCount = 5000;
		}

		EquipInfo mainEquip = equipInfoService.getMainEquipByEquipLine(statusHistory.getEquipCode());
		if (mainEquip == null) {
			return mesList;
		}
		List<EquipParamHistoryAcquisition> list = dataAcquisitionService.findParamHistory(mainEquip.getCode(),
				StandardParamCode.R_Length.name(), startTime, endTime, cycleCount);

		if (!CollectionUtils.isEmpty(list)) {
			if (list.size() == 1) {

				processone(statusHistory, mesList, list.get(0));

			} else {
				List<StatusHistory> resultlist = new ArrayList<StatusHistory>();
				if (CollectionUtils.isEmpty(mesList)) {
					statusHistory.setStatus(EquipStatus.CLOSED);
					mesList.add(statusHistory);
				}
				int position = 0;
				// 处理第一条信息
				position = findRealStatus(mesList, position, list.get(0), resultlist);
				// 处理其余信息
				for (int i = 0; i < list.size() - 1; i++) {
					EquipParamHistoryAcquisition first = list.get(i);
					EquipParamHistoryAcquisition second = list.get(i + 1);
					if (second.getValue() == null || first.getValue() == null) {

						StatusHistory objectNew = new StatusHistory();
						objectNew.setStartTime(first.getDatetime());
						objectNew.setEndTime(second.getDatetime());
						StatusHistory matchStatus = findMesData(mesList, second);
						if (matchStatus == null) {
							objectNew.setStatus(EquipStatus.CLOSED);
						} else if (matchStatus.getStatus().equals(EquipStatus.IN_PROGRESS)
								|| matchStatus.getStatus().equals(EquipStatus.IN_DEBUG)
								|| matchStatus.getStatus().equals(EquipStatus.IDLE)) {
							objectNew.setStatus(EquipStatus.CLOSED);
						} else {
							objectNew.setStatus(matchStatus.getStatus());
						}
						if ((objectNew.getStatus() == EquipStatus.CLOSED || objectNew.getStatus() == EquipStatus.IDLE)
								&& StringUtils.isNotEmpty(mainEquip.getStatusBasisWw())) {
							objectNew = getEquipStatusByEquipProperties(objectNew, mainEquip);
						}
						resultlist.add(objectNew);
					} else {
						StatusHistory objectNew = new StatusHistory();
						objectNew.setStartTime(first.getDatetime());
						objectNew.setEndTime(second.getDatetime());
						StatusHistory matchStatus = findMesData(mesList, second);
						if (matchStatus == null) {
							objectNew.setStatus(EquipStatus.CLOSED);
						} else {
							if (EquipStatus.IN_PROGRESS.equals(matchStatus.getStatus())
									|| EquipStatus.IN_DEBUG.equals(matchStatus.getStatus())) {
								// 如果采集点之间的数据变化不大，认为是IDLE
								if (Math.abs(second.getValue() - first.getValue()) < 0.1) {
									objectNew.setStatus(EquipStatus.IDLE);
								} else {
									objectNew.setStatus(matchStatus.getStatus());
								}
							} else if (EquipStatus.IDLE.equals(matchStatus.getStatus())
									|| EquipStatus.ERROR.equals(matchStatus.getStatus())
									|| EquipStatus.IN_MAINTAIN.equals(matchStatus.getStatus())) {
								// 如果采集点之间的数据变化不大，认为是IDLE
								if (Math.abs(second.getValue().doubleValue() - first.getValue().doubleValue()) < 0.1) {
									objectNew.setStatus(matchStatus.getStatus());
								} else {
									objectNew.setStatus(EquipStatus.IN_PROGRESS);
								}

							} else if (EquipStatus.CLOSED.equals(matchStatus.getStatus())) {
								if (Math.abs(second.getValue() - first.getValue()) < 0.1) {
									objectNew.setStatus(EquipStatus.IDLE);
								} else {
									objectNew.setStatus(EquipStatus.IN_PROGRESS);
								}

							}
						}
						if ((objectNew.getStatus() == EquipStatus.CLOSED || objectNew.getStatus() == EquipStatus.IDLE)
								&& StringUtils.isNotEmpty(mainEquip.getStatusBasisWw())) {
							objectNew = getEquipStatusByEquipProperties(objectNew, mainEquip);
						}
						resultlist.add(objectNew);
					}

				}
				mesList = resultlist;
			}

		} else {
			mesList.clear();
			return mesList;
		}

		return mergeList(mesList);
	}

	private List<StatusHistory> mergeList(List<StatusHistory> mesList) {
		List<StatusHistory> result = new ArrayList<StatusHistory>();
		if (mesList == null) {
			return result;
		} else if (mesList.size() == 1) {
			result.add(mesList.get(0));
		} else {
			StatusHistory first = mesList.get(0);
			result.add(first);
			for (int j = 1; j < mesList.size(); j++) {

				if (first.getStatus().equals(mesList.get(j).getStatus())) {
					first.setEndTime(mesList.get(j).getEndTime());
				} else {
					first = mesList.get(j);
					result.add(first);
				}

			}
		}

		return result;
	}

	public static void main(String args[]) {
		StatusHistoryServiceImpl o = new StatusHistoryServiceImpl();
		List<StatusHistory> mesList = new ArrayList<StatusHistory>();
		Date startTime = new Date();

		StatusHistory e1 = new StatusHistory();
		e1.setStartTime(startTime);
		e1.setEndTime(DateUtils.addDayToDate(startTime, 1));
		e1.setStatus(EquipStatus.IDLE);

		StatusHistory e2 = new StatusHistory();
		e2.setStartTime(DateUtils.addDayToDate(startTime, 1));
		e2.setEndTime(DateUtils.addDayToDate(startTime, 2));
		e2.setStatus(EquipStatus.IDLE);

		StatusHistory e3 = new StatusHistory();
		e3.setStartTime(DateUtils.addDayToDate(startTime, 2));
		e3.setEndTime(DateUtils.addDayToDate(startTime, 3));
		e3.setStatus(EquipStatus.CLOSED);

		mesList.add(e1);
		mesList.add(e2);
		mesList.add(e3);

		List<StatusHistory> b = o.mergeList(mesList);
		System.out.println(b);
	}

	private StatusHistory findMesData(List<StatusHistory> mesList, EquipParamHistoryAcquisition realStatus) {
		for (int i = 0; i < mesList.size(); i++) {
			StatusHistory objectHis = mesList.get(i);
			if (realStatus.getDatetime().getTime() >= objectHis.getStartTime().getTime()
					&& realStatus.getDatetime().getTime() <= objectHis.getEndTime().getTime()) {
				return objectHis;
			}
		}
		return null;
	}

	private int findRealStatus(List<StatusHistory> mesList, int position, EquipParamHistoryAcquisition realStatus,
			List<StatusHistory> resultlist) {

		for (int i = position; i < mesList.size(); i++) {
			StatusHistory objectHis = mesList.get(i);
			if (realStatus.getDatetime().getTime() >= objectHis.getStartTime().getTime()
					&& realStatus.getDatetime().getTime() <= objectHis.getEndTime().getTime()) {
				// 找到匹配
				StatusHistory objectNew = new StatusHistory();
				objectNew.setStartTime(objectHis.getStartTime());
				objectNew.setEndTime(realStatus.getDatetime());
				if (realStatus.getValue() == null) {
					objectNew.setStatus(EquipStatus.CLOSED);
				} else {
					objectNew.setStatus(EquipStatus.IN_PROGRESS);
				}
				resultlist.add(objectNew);
				position = i;
				break;
			}
		}
		return position;
	}

	private void processone(StatusHistory statusHistory, List<StatusHistory> alist, EquipParamHistoryAcquisition first) {
		if (first.getValue() == null) {

			// 没有采集值，直接状态修改为 CLOSED("关机"), ERROR("故障"), IN_MAINTAIN("维修中");
			if (CollectionUtils.isEmpty(alist)) {
				// 有采集值，直接状态修改为 CLOSED("关机"),
				statusHistory.setStatus(EquipStatus.CLOSED);
				alist.add(statusHistory);
				return;
			} else {
				for (int j = 0; j < alist.size(); j++) {
					StatusHistory objectget = alist.get(j);
					// 如果MES设备状态在运行，调试，或者待机，直接设置为CLOSED 其他状态保留不变
					if (objectget.getStatus().equals(EquipStatus.IN_PROGRESS)
							|| objectget.getStatus().equals(EquipStatus.IN_DEBUG)
							|| objectget.getStatus().equals(EquipStatus.IDLE)) {
						objectget.setStatus(EquipStatus.CLOSED);
					}
				}

			}

		} else {
			// 有采集值，调整状态;
			// 没有采集值，直接状态修改为 CLOSED("关机"), ERROR("故障"), IN_MAINTAIN("维修中");
			if (CollectionUtils.isEmpty(alist)) {
				// 待机-长度不变
				statusHistory.setStatus(EquipStatus.IDLE);
				alist.add(statusHistory);
				return;
			} else {
				for (int j = 0; j < alist.size(); j++) {
					StatusHistory objectget = alist.get(j);
					// 如果MES设备状态在运行，调试，直接设置为IDLE 其他状态保留不变
					if (objectget.getStatus().equals(EquipStatus.IN_PROGRESS)
							|| objectget.getStatus().equals(EquipStatus.IN_DEBUG)) {
						objectget.setStatus(EquipStatus.IDLE);
					}
				}

			}

		}
	}

	@Override
	public void exportToXls(OutputStream os, String sheetName, StatusHistory statusHistory) throws IOException,
			RowsExceededException, WriteException {
		List<StatusHistory> list = processRealStatus(statusHistory, statusHistoryDAO.getByTimeAndStatus(statusHistory));
		List<WorkOrder> workOrderList = workOrderDAO.getProductNameByEquipCode(statusHistory.getEquipCode());
		String equipName = sheetName.replace("状态历史", "");
		for (WorkOrder workOrder : workOrderList) {
			for (int i = 0; i < list.size(); i++) {
				StatusHistory status = list.get(i);
				if (workOrder.getRealStartTime().getTime() <= status.getStartTime().getTime()
						&& workOrder.getRealEndTime().getTime() >= status.getStartTime().getTime()) {
					list.get(i).setProductName(workOrder.getProductName());
				}
			}
		}
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet sheet = wwb.createSheet(sheetName, 0);
		sheet.setColumnView(0, 20);
		sheet.setColumnView(1, 30);
		sheet.setColumnView(2, 20);
		sheet.setColumnView(3, 30);
		sheet.setColumnView(4, 30);
		sheet.addCell(new Label(0, 0, "设备名称"));
		sheet.addCell(new Label(1, 0, "生产产品"));
		sheet.addCell(new Label(2, 0, "设备状态"));
		sheet.addCell(new Label(3, 0, "开始时间"));
		sheet.addCell(new Label(4, 0, "结束时间"));
		if (list.size() == 0) {
			wwb.write();
			wwb.close();
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			StatusHistory status = list.get(i);
			sheet.addCell(new Label(0, i + 1, equipName));
			sheet.addCell(new Label(1, i + 1, status.getProductName() == null ? "" : status.getProductName()));
			sheet.addCell(new Label(2, i + 1, status.getStatus().toString()));
			sheet.addCell(new Label(3, i + 1, DateUtils.convert(status.getStartTime(), DateUtils.DATE_TIME_FORMAT)));
			sheet.addCell(new Label(4, i + 1, DateUtils.convert(status.getEndTime(), DateUtils.DATE_TIME_FORMAT)));
		}
		wwb.write();
		wwb.close();
	}

	@Override
	public List<StatusHistory> findShutDownReason(StatusHistory param, Integer start, Integer limit, Object object,
			boolean isPage) {
		if (isPage) {
			SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		}
		List<StatusHistory> statusHistoryList = statusHistoryDAO.findByParam(param);
		List<String> equipList = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (StatusHistory status : statusHistoryList) {
			Date endParm = param.getEndTime();
			if (endParm != null && status.getEndTime().after(endParm)) {
				status.setEndTime(endParm);
			}

			Date startParm = param.getStartTime();
			if (startParm != null && status.getStartTime().before(startParm)) {
				status.setStartTime(startParm);
			}

			if (map.get(status.getEquipCode()) == null) {
				equipList.add(status.getEquipCode());
				map.put(status.getEquipCode(), status.getEquipCode());
			}
		}
		map = new HashMap<String, Object>();
		map.put("equipList", equipList.size() > 0 ? equipList : null);
//		List<WorkOrder> workOrderList = workOrderService.getWorkOrderByEquipList(map);
//		for (WorkOrder work : workOrderList) {
//			if (work.getRealEndTime() == null) {
//				work.setRealEndTime(new Date());
//			}
//			for (int i = 0; i < statusHistoryList.size(); i++) {
//				StatusHistory statusHistory = statusHistoryList.get(i);
//				if (StringUtils.equals(work.getEquipCode(), statusHistory.getEquipCode())) {
//					if (statusHistory.getStartTime().getTime() >= work.getRealStartTime().getTime()
//							&& statusHistory.getStartTime().getTime() <= work.getRealEndTime().getTime()) {
//						statusHistoryList.get(i).setProductName(work.getProductName());
//					}
//				}
//			}
//
//		}
		for (int i = 0; i < statusHistoryList.size(); i++) {
			StatusHistory hist = statusHistoryList.get(i);
			if (StringUtils.isBlank(hist.getProductName()) && StringUtils.isBlank(hist.getReason())) {
				statusHistoryList.get(i).setReason("空闲");
			}
			Long dateBet = DateUtils.getMinuteDiff(hist.getStartTime(), hist.getEndTime());
			statusHistoryList.get(i).setTimeBet(dateBet);
		}
		return statusHistoryList;

	}

	@Override
	public Integer countShutDownReason(StatusHistory param) {

		return statusHistoryDAO.countByParam(param);
	}

	// private List<StatusHistory> getDataLimitByTime(List<StatusHistory> list){
	// List<StatusHistory> resultList=new ArrayList<StatusHistory>();
	// for(StatusHistory statusHistory:list){
	// if(DateUtils.getMinuteDiff(statusHistory.getStartTime(),
	// statusHistory.getEndTime())>=this.TIME_BETWEEN_TIME){
	// resultList.add(statusHistory);
	// }
	// }
	// return resultList;
	// }

	@Override
	public Map<String, Object> getIdleDataByEquipAndLimitTime(StatusHistory statusHistorys) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<GraphValue> pieChartList = new ArrayList<GraphValue>();
		statusHistorys.setIsCompleted("all");
		// List<StatusHistory>
		// statusHistoryList=statusHistoryDAO.findByParam(statusHistorys);
		List<StatusHistory> statusHistoryList = this.findShutDownReason(statusHistorys, 0, Integer.MAX_VALUE, null,
				false);
		List<DataDic> statusReasonList = dataDicService.getCodeByTermsCode(TermsCodeType.SHUT_DOWN_REASON);
		if (CollectionUtils.isEmpty(statusHistoryList)) {
			return resultMap;
		}
		for (DataDic dataDic : statusReasonList) {
			GraphValue graphValue = new GraphValue();
			long totalTime = 0l;
			for (StatusHistory statusHistoy : statusHistoryList) {
				if (StringUtils.isBlank(statusHistoy.getReason())) {
					statusHistoy.setReason("未知原因");
				}
				if (StringUtils.equals(dataDic.getName(), statusHistoy.getReason())) {
					totalTime += DateUtils.getMinuteDiff(statusHistoy.getStartTime(),statusHistoy.getEndTime());
				}
			}
			if (totalTime > 0) {
				graphValue.setY((double) totalTime);
				graphValue.setName(dataDic.getName() + ": (" + totalTime + "分钟)");
				pieChartList.add(graphValue);
			}
		}
		resultMap.put("pieData", pieChartList);
		return resultMap;
	}

	@Override
	public List<StatusHistory> findForExport(JSONObject queryParams) throws InvocationTargetException,
			IllegalAccessException, NoSuchMethodException {
		StatusHistory param = JSONObject.parseObject(queryParams.toJSONString(), StatusHistory.class);
		List<StatusHistory> statusHistoryList = statusHistoryDAO.findByParam(param);
		List<String> equipList = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		for (StatusHistory status : statusHistoryList) {
			if (map.get(status.getEquipCode()) == null) {
				equipList.add(status.getEquipCode());
				map.put(status.getEquipCode(), status.getEquipCode());
			}
		}
		map = new HashMap<String, Object>();
		map.put("equipList", equipList);
		List<WorkOrder> workOrderList = workOrderService.getWorkOrderByEquipList(map);
		for (WorkOrder work : workOrderList) {
			if (work.getRealEndTime() == null) {
				work.setRealEndTime(new Date());
			}
			for (int i = 0; i < statusHistoryList.size(); i++) {
				StatusHistory statusHistory = statusHistoryList.get(i);
				if (StringUtils.equals(work.getEquipCode(), statusHistory.getEquipCode())) {
					if (statusHistory.getStartTime().getTime() >= work.getRealStartTime().getTime()
							&& statusHistory.getStartTime().getTime() <= work.getRealEndTime().getTime()) {
						statusHistoryList.get(i).setProductName(work.getProductName());
					}
				}
			}

		}
		return statusHistoryList;
	}

	private StatusHistory getEquipStatusByEquipProperties(StatusHistory objectNew, EquipInfo mainEquip) {
		// 2.1、获取设备对应的标签
		List<String> targetArray = Arrays.asList(mainEquip.getStatusBasisWw().split(","));
		Map<String, String> paramsMap = equipMESWWMappingService.getTagNameByEquipCodeParams(mainEquip.getCode(),
				targetArray);
		if (null != paramsMap) {
			// 2.2、查询标签对应的实时参数
			try {
				List<EquipParamAcquisition> liveParamValues = equipParamAcquisitionDAO.findLiveValue(
						new ArrayList<String>(Arrays.asList(paramsMap.get("TAGNAME").split(","))), null, null);
				for (EquipParamAcquisition param : liveParamValues) {
					if (null == param.getValue()) { // 空就是关机
						objectNew.setStatus(EquipStatus.CLOSED);
					} else if (param.getValue() == 0) { // 0就是空闲
						objectNew.setStatus(EquipStatus.IDLE);
					} else if (param.getValue() > 0) { // 大于零就是开机
						objectNew.setStatus(EquipStatus.IN_PROGRESS);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objectNew;
	}

	@Override
	public List<LineChart> getEquipYield(String equipCode,StatusHistory statusHistory,
			String type, Boolean oEE) {

		// 0、返回的对象：[{name : 状态, data : [{name : 时间, y : 12.5, ...}]}, ...]
		List<LineChart> lineChartArray = new ArrayList<LineChart>();
		// 1、查询出设备状态历史记录
		List<StatusHistory> hisArray = statusHistoryDAO.getByTimeAndStatus(statusHistory);
		
		// 2、遍历：根据开始结束时间的区间变更对象到hisChartMap
		// A、开始结束时间在一天内；B、开始结束时间跨天。
		Date x = statusHistory.getEndTime();
		for (StatusHistory his : hisArray) {
			
			Date timePreNext = his.getStartTime().before(statusHistory.getStartTime()) ? statusHistory.getStartTime() : his.getStartTime();
			if (StringUtils.equalsIgnoreCase(type, "WEEK")) {
				timePreNext = DateUtils.getStartWeektime(timePreNext); // 当周周一00:00:00
			} else if (StringUtils.equalsIgnoreCase(type, "MONTH")) {
				timePreNext = DateUtils.getStartMonthtime(timePreNext); // 当月一号00:00:00
			} else {
				timePreNext = DateUtils.getStartDatetime(timePreNext); // 默认按天:当天00:00:00
			}

			/**
			 * 循环说明：取一天的开始和结束时间，然后根据开始结束时间判断做中间差； 循环条件为：每天的开始时间小于真正结束时间，就做差处理
			 * */
			while (timePreNext.before(x)) {
				Date start = timePreNext;
				Date end = null;
				if (StringUtils.equalsIgnoreCase(type, "WEEK")) {
					end = DateUtils.getEndWeektime(timePreNext); // 当周周日23:59:59
				} else if (StringUtils.equalsIgnoreCase(type, "MONTH")) {
					end = DateUtils.getEndMonthtime(timePreNext); // 当月最后天23:59:59
				} else{
					end = DateUtils.getEndDatetime(timePreNext); // 默认按天:当天23:59:59
				}

				String timeKey = DateUtils.convert(timePreNext, DateUtils.DATE_SHORT_FORMAT); // 默认按天:每天的mapKey
				if (StringUtils.equalsIgnoreCase(type, "MONTH")) {
					timeKey = DateUtils.convert(timePreNext, DateUtils.MONTH_SHORT_FORMAT);// 每月的mapKey
				}
				String name = timeKey; // 横坐标下标名称，日，周，月
				
				if(start.before(statusHistory.getStartTime())&&StringUtils.equalsIgnoreCase(type, "WEEK")){
					start = statusHistory.getStartTime();
				}
				
				if (end.after(his.getEndTime())&&StringUtils.equalsIgnoreCase(type, "WEEK")) {
					if(end.after(x)){
						statusHistory.setEndTime(x);
						end = statusHistory.getEndTime();
					}
				}
				statusHistory.setStartTime(start);
				statusHistory.setEndTime(end);
				
				if (StringUtils.equalsIgnoreCase(type, "WEEK")) {
					name = (start.before(statusHistory.getStartTime()) ? DateUtils.convert(
							statusHistory.getStartTime(), DateUtils.DATE_SHORT_FORMAT) : DateUtils.convert(start,
							DateUtils.DATE_SHORT_FORMAT))
							+ "~"
							+ (end.after(statusHistory.getEndTime()) ? DateUtils.convert(statusHistory.getEndTime(),
									DateUtils.DATE_SHORT_FORMAT) : DateUtils.convert(end, DateUtils.DATE_SHORT_FORMAT));
					
				}
//				if(start.before(statusHistory.getStartTime())&&StringUtils.equalsIgnoreCase(type, "WEEK")){
//					start = statusHistory.getStartTime();
//				}
//				
//				if (end.after(his.getEndTime())) {
//					statusHistory.setEndTime(x);
//					end = statusHistory.getEndTime();
//				}
//				statusHistory.setStartTime(start);
//				statusHistory.setEndTime(end);
				
				
				this.getEquipOutput(lineChartArray,  name, his.getStatus(), statusHistory,end);
				

				if (StringUtils.equalsIgnoreCase(type, "WEEK")) {
					timePreNext = DateUtils.addDayToDate(timePreNext, 7); // 加7天
				} else if (StringUtils.equalsIgnoreCase(type, "MONTH")) {
					timePreNext = DateUtils.addDayToDate(timePreNext, DateUtils.getMonthDayCount(timePreNext)); // 加当月的天数
				} else { // 默认按天
					timePreNext = DateUtils.addDayToDate(timePreNext, 1); // 加1天
				}
			}
		}
		
		return lineChartArray;
	
	}
	
	private void getEquipOutput(List<LineChart> lineChartArray, String name,
			EquipStatus timeStatus, StatusHistory statusHistory,Date end){
		
		for (EquipStatus status : EquipStatus.values()) {
		
			// 2、获取该状态的chart对象
			LineChart lineChart = null;
			for(LineChart chart : lineChartArray){
				if(chart.getStatus() == status){
					lineChart = chart;
					break;
				}
			}
			// 2.1、没有的时候new一个并赋值
			if(null == lineChart){
				lineChart = new LineChart();
				lineChart.setName(status.toString());
				lineChart.setStatus(status);
				lineChartArray.add(lineChart);
			}
			// 3、获取chart对象的data数据
			List<GraphValue> dataArray = lineChart.getData();
			GraphValue graphValue = null;
			for(GraphValue data : dataArray){
				if(data.getName().equals(name)){
					graphValue = data;
					break;
				}
			}
			// 3.1、没有的时候new一个data并赋值
			if(null == graphValue){
				graphValue = new GraphValue();
				graphValue.setName(name);
				dataArray.add(graphValue);
			}
			Integer  reportLength = statusHistoryDAO.getEquipYield(statusHistory);
			
			if("加工中".equals(status.toString())){
				graphValue.setY(reportLength.doubleValue());
			}
		}
	}
	

}
