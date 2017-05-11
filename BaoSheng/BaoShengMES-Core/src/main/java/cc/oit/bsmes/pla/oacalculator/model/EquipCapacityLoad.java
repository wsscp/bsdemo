package cc.oit.bsmes.pla.oacalculator.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.common.util.Range;
import cc.oit.bsmes.common.util.RangeList;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.WorkTask;

/**
 * 设备能力负载
 * 
 * @author DingXintao
 * @date 2015-09-01
 * @param rangeList 剩余时间范围：一段段的时间段对象
 * @method
 * */
public class EquipCapacityLoad {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String FORMAT_STRING = "yyyyMMddHHmm";
	@Getter
	private RangeList rangeList = new RangeList(); // 设备可用时间段列表
	@Getter
	private String equipCode; // 设备编码

	/**
	 * 初始化容量
	 * 
	 * @param equipInfo 设备信息：设备编码和占用产能信息
	 * @param equipCalendar 设备日历
	 * @param workTaskArray 设备固定占用能力
	 * @throws java.text.ParseException
	 */
	public EquipCapacityLoad(EquipInfo equipInfo, List<EquipCalendar> equipCalendarArray, List<WorkTask> workTaskArray)
			throws ParseException {
		this.equipCode = equipInfo.getCode();
		DateFormat dateFormat = new SimpleDateFormat(FORMAT_STRING);
		// 1、循环设备日历
		for (EquipCalendar equipCalendar : equipCalendarArray) {
			String dateOfWork = equipCalendar.getDateOfWork();

			// 2、循环每天班次，将班次转换成时间段插入设备负载：rangeList
			Date shiftStart = dateFormat.parse(dateOfWork + equipCalendar.getShiftStartTime()); // 班次开始时间
			Date shiftEnd = dateFormat.parse(dateOfWork + equipCalendar.getShiftEndTime()); // 班次结束时间
			// 注：如果班次中的结束时间小于了开始的时间，说明结束时间是第二天，故结束时间加一天
			if (shiftEnd.before(shiftStart)) {
				shiftEnd.setTime(shiftEnd.getTime() + 24 * 60 * 60 * 1000);
			}
			rangeList.add(shiftStart, shiftEnd); // 相邻区间会自动合并，此处直接add就可以
		}
		// 把固定占用的产能更新上去
		for (WorkTask workTask : workTaskArray) {
			Long processTime = workTask.getWorkEndTime().getTime() - workTask.getWorkStartTime().getTime();
			this.updateRange(processTime, workTask.getWorkStartTime(), null);
		}
	}

	/**
	 * 初始化容量
	 * 
	 * @param startDate 容量整体区间：开始时间
	 * @param endDate 容量整体区间：结束时间
	 * @param equipInfo 设备信息：设备编码和占用产能信息
	 * @param equipCalendar 设备日历
	 * @param workTaskArray 设备固定占用能力
	 * @throws java.text.ParseException
	 */
	public EquipCapacityLoad(Date startDate, Date endDate, EquipInfo equipInfo, List<EquipCalendar> equipCalendarArray,
			List<WorkTask> workTaskArray) throws ParseException {
		this.equipCode = equipInfo.getCode();
		DateFormat dateFormat = new SimpleDateFormat(FORMAT_STRING);
		// 1、循环设备日历
		calendar: for (EquipCalendar equipCalendar : equipCalendarArray) {
			String dateOfWork = equipCalendar.getDateOfWork();

			// 2、循环每天班次，将班次转换成时间段插入设备负载：rangeList
			Date shiftStart = dateFormat.parse(dateOfWork + equipCalendar.getShiftStartTime()); // 班次开始时间
			Date shiftEnd = dateFormat.parse(dateOfWork + equipCalendar.getShiftEndTime()); // 班次结束时间
			// 注：如果班次中的结束时间小于了开始的时间，说明结束时间是第二天，故结束时间加一天
			if (shiftEnd.before(shiftStart)) {
				shiftEnd.setTime(shiftEnd.getTime() + 24 * 60 * 60 * 1000);
			}

			if (endDate != null && endDate.before(shiftStart)) { // 超过了结束时间
				break calendar;
			}
			if (startDate != null && shiftEnd.before(startDate)) { // 不到开始时间
				continue calendar;
			}
			rangeList.add(shiftStart, shiftEnd); // 相邻区间会自动合并，此处直接add就可以
		}
		// 把固定占用的产能更新上去
		for (WorkTask workTask : workTaskArray) {
			Long processTime = workTask.getWorkEndTime().getTime() - workTask.getWorkStartTime().getTime();
			this.updateRange(processTime, workTask.getWorkStartTime(), null);
		}
	}

	/**
	 * 添加一个时间段
	 * 
	 * @param workTask 设备占用产能任务对象
	 */
	public void addRange(WorkTask workTask) {
		rangeList.add(workTask.getWorkStartTime(), workTask.getWorkEndTime());
	}

	/**
	 * 添加一个时间段
	 * 
	 * @param startTime 时间段对象的开始时间
	 * @param endTime 时间段对象的结束时间
	 */
	public void addRange(Date startTime, Date endTime) {
		rangeList.add(startTime, endTime);
	}

	/**
	 * 添加一个时间段
	 * 
	 * @param startTime 时间段对象的开始时间
	 * @param endTime 时间段对象的结束时间
	 */
	public void addRange(Long startTime, Long endTime) {
		rangeList.add(startTime, endTime);
	}

	/**
	 * 更新设备负载：修改时间段
	 * 
	 * @param userdTime 加工时间
	 * @param startTime 时间段对象的开始时间
	 * @param endTime 时间段对象的结束时间
	 * @return long[] 占用的实际开始和结束区间
	 */
	public long[] updateRange(Long userdTime, Date startTime, Date endTime) {
		return this.updateRange(userdTime, startTime, endTime, null, null);
	}

	/**
	 * 从前向后更新设备负载：修改时间段
	 * 
	 * @param userdTime 加工时间
	 * @param startTime 时间段对象的开始时间
	 * @return long[] 占用的实际开始和结束区间
	 */
	public long[] updateRangeForward(Long userdTime, Long startTime) {
		return this.updateRangeForward(userdTime, startTime, null, null);
	}

	/**
	 * 从后往前更新设备负载：修改时间段
	 * 
	 * @param userdTime 加工时间
	 * @param endTime 时间段对象的结束时间
	 * @return long[] 占用的实际开始和结束区间
	 */
	public long[] updateRangeReverse(Long userdTime, Long endTime) {
		return this.updateRangeReverse(userdTime, endTime, null, null);
	}

	/**
	 * 更新设备负载：修改时间段
	 * 
	 * @param userdTime 加工时间
	 * @param startTime 时间段对象的开始时间
	 * @param endTime 时间段对象的结束时间
	 * @param start 设备产能大区间：开始
	 * @param end 设备产能大区间：结束
	 * @return long[] 占用的实际开始和结束区间
	 */
	public long[] updateRange(Long userdTime, Date startTime, Date endTime, Long start, Long end) {
		if (null == userdTime || (null == startTime && null == endTime)) {
			return null;
		}
		if (null != start && null != end) {
			if (end < start) { // 无效的大区间
				return null;
			}
			if ((end - start) < userdTime) { // 大区间小于加工时间，不用比了
				return null;
			}
		}
		if (null == startTime) {
			return this.updateRangeReverse(userdTime, endTime.getTime(), start, end);
		}
		if (null == endTime) {
			return this.updateRangeForward(userdTime, startTime.getTime(), start, end);
		}
		return null;
	}

	/**
	 * 该段能力是否在所规定的大区间内
	 * 
	 * @param range 区间段
	 * @param start 设备产能大区间：开始
	 * @param end 设备产能大区间：结束
	 * */
	private Long[] getInterval(Range range, Long start, Long end) {
		Long[] mums = { range.getMinimum(), range.getMaximum() };
		if (null == start && null == end) { // 空：默认全部
			return mums;
		}
		if (range.getMaximum() < start) { // 区间的在约束范围前
			return null;
		}
		if (range.getMinimum() > end) { // 区间的在约束范围后
			return null;
		}
		if (range.getMaximum() > end && range.getMinimum() < start) { // 约束范围在区间段内
			mums[0] = start;
			mums[1] = end;
		} else if (range.getMinimum() > start && range.getMaximum() > end) { // 前交叉
			mums[0] = range.getMinimum();
			mums[1] = end;
		} else if (range.getMinimum() < start && range.getMaximum() < end) { // 后交叉
			mums[0] = start;
			mums[1] = range.getMaximum();
		}
		return mums;
	}

	/**
	 * 从前向后更新设备负载：修改时间段
	 * 
	 * @param userdTime 加工时间
	 * @param startTime 时间段对象的开始时间
	 * @param start 设备产能大区间：开始
	 * @param end 设备产能大区间：结束
	 * @return long[] 占用的实际开始和结束区间
	 */
	public long[] updateRangeForward(Long userdTime, Long startTime, Long start, Long end) {
		// 循环负载，按规则(正/反)寻找一个足够大的空档
		for (int i = 0; i < rangeList.size(); i++) {
			Range range = rangeList.get(i);
			// 1、根据大区间限制获取起止时间
			Long[] mums = this.getInterval(range, start, end);
			if (null == mums) {
				continue;
			}
			// 2、先行过滤：不能提前，可以延后（提前因为上道工序还没完成，延后是看设备实际状况）
			// 2.1、区间结束时间小于指定加工开始时间，太早了
			if (mums[1] < startTime) {
				continue;
			}
			long realStartTime = startTime; // 实际开始时间
			// 2.2、区间开始时间大于指定加工开始时间，实际开始时间就是区间的开始时间
			if (mums[0] > startTime) {
				realStartTime = mums[0];
			}
			// 2.3、在指定区间内，时间小于加工时间，太短了
			if ((mums[1] - realStartTime) < userdTime) {
				continue;
			}
			long realEndTime = realStartTime + userdTime; // 实际结束时间

			// 3、满足条件，处理时间区间段，并返回。
			// 3.1、处理时间段
			// 3.1.1、当区间开始时间和结束时间与加工时间完全重合：remove
			if (range.getMinimum() == realStartTime && range.getMaximum() == realEndTime) {
				rangeList.remove(range);
			}
			// 3.1.2、当区间开始时间和结束时间任意一端与加工时间重合：update
			else if (range.getMinimum() == realStartTime) {
				range.setMinimum(realEndTime);
			} else if (range.getMaximum() == realEndTime) {
				range.setMaximum(realStartTime);
			}
			// 3.1.3、当区间开始时间和结束时间与加工时间没有重合： add && update
			else {
				Range addRange = new Range(realEndTime, range.getMaximum()); // range修改前new，否则maximun已经改变
				range.setMaximum(realStartTime);
				rangeList.add(i + 1, addRange);
			}
			// 3.2、返回
			long[] useTime = { realStartTime, realEndTime };
			return useTime;
		}
		return null;
	}

	/**
	 * 从后往前更新设备负载：修改时间段
	 * 
	 * @param userdTime 加工时间
	 * @param endTime 时间段对象的结束时间
	 * @param start 设备产能大区间：开始
	 * @param end 设备产能大区间：结束
	 * @return long[] 占用的实际开始和结束区间
	 */
	public long[] updateRangeReverse(Long userdTime, Long endTime, Long start, Long end) {
		// 循环负载，按规则(正/反)寻找一个足够大的空档
		for (int i = 0; i < rangeList.size(); i++) {
			Range range = rangeList.get(i);
			// 1、根据大区间限制获取起止时间
			Long[] mums = this.getInterval(range, start, end);
			if (null == mums) {
				continue;
			}
			// 2、先行过滤：不能延后，可以提前（提前延后下道工序时间已经排定，延后是看设备实际状况）
			// 2.1、区间开始时间大于指定加工结束时间，太晚了
			if (mums[0] > endTime) {
				continue;
			}
			long realEndTime = endTime; // 实际结束时间
			// 2.2、区间结束时间小于指定加工结束时间，实际结束时间就是区间的结束时间
			if (mums[1] < endTime) {
				realEndTime = mums[1];
			}
			// 2.3、在指定区间内，时间小于加工时间，太短了
			if ((realEndTime - mums[0]) < userdTime) {
				continue;
			}
			long realStartTime = realEndTime - userdTime; // 实际开始时间

			// 3、满足条件，处理时间区间段，并返回。
			// 3.1、处理时间段
			// 3.1.1、当区间开始时间和结束时间与加工时间完全重合：remove
			if (range.getMinimum() == realStartTime && range.getMaximum() == realEndTime) {
				rangeList.remove(range);
			}
			// 3.1.2、当区间开始时间和结束时间任意一端与加工时间重合：update
			else if (range.getMinimum() == realStartTime) {
				range.setMinimum(realEndTime);
			} else if (range.getMaximum() == realEndTime) {
				range.setMaximum(realStartTime);
			}
			// 3.1.3、当区间开始时间和结束时间与加工时间没有重合： add && update
			else {
				Range addRange = new Range(realEndTime, range.getMaximum()); // range修改前new，否则maximun已经改变
				range.setMaximum(realStartTime);
				rangeList.add(i + 1, addRange);
			}
			// 3.2、返回
			long[] useTime = { realStartTime, realEndTime };
			return useTime;
		}
		return null;
	}

	/**
	 * 获取设备能力：所有
	 * */
	public long getLong() {
		return rangeList.getLong();
	}

	/**
	 * 获取设备能力：给定区间内
	 * */
	public long getLong(long start, long end) {
		return rangeList.getLong(start, end);
	}

}
