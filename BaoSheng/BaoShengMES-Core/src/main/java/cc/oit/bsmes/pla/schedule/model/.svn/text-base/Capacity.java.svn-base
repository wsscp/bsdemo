package cc.oit.bsmes.pla.schedule.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cc.oit.bsmes.bas.model.EquipCalShift;
import cc.oit.bsmes.bas.model.EquipCalendar;
import cc.oit.bsmes.bas.service.EquipCalendarService;
import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.common.exception.InconsistentException;
import cc.oit.bsmes.common.util.Range;
import cc.oit.bsmes.common.util.RangeList;
import cc.oit.bsmes.common.util.RangeUtils;
import cc.oit.bsmes.fac.model.EquipInfo;

public class Capacity {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String FORMAT_STRING = "yyyyMMddHHmm";
    @Getter
    private RangeList timeRangesLeft;
    @Getter
    private RangeList timeRangesOccupied; // 集合中每个range一定对应一个ordertask
    private EquipInfo equipInfo;
    protected long endBoundary;
    private boolean endBoundaryNotUsed = true;
    private RangeList timeRangesLeftBeforeCompress;
    private RangeList timeRangesOccupiedBeforeCompress;
    private int loadMonthPower = 1;
    private long overworkTime = 0;

    /**
     * 初始化容量
     *
     * @param startDate 容量最早开始时间
     * @param endDate   容量结束时间
     * @throws java.text.ParseException
     */
    public Capacity(Date startDate, EquipInfo equipInfo, Date endDate, String orgCode) throws ParseException {
        init(equipInfo, orgCode);
        for (EquipCalendar equipCalendar : equipInfo.getEquipCalendar()) {
            DateFormat dateFormat = new SimpleDateFormat(FORMAT_STRING);
            List<EquipCalShift> shifts = equipCalendar.getEquipCalShift();
            if (shifts.size() == 0) {
                continue;
            }
            Date start = dateFormat.parse(equipCalendar.getDateOfWork()
                    + shifts.get(0).getShiftStartTime());
            if (endDate.getTime() < start.getTime()) {
                break;
            }
            loadCalendar(startDate, equipCalendar);
        }
    }

    /**
     * 初始化容量
     *
     * @throws java.text.ParseException
     * @author chanedi
     * @date 2014-2-28 下午1:36:11
     */
    public Capacity(EquipInfo equipInfo, String orgCode) throws ParseException {
        init(equipInfo, orgCode);
        loadCalendars(equipInfo.getEquipCalendar());
    }

    private void init(EquipInfo equipInfo, String orgCode) {
        timeRangesLeft = new RangeList();
        timeRangesOccupied = new RangeList();
        this.equipInfo = equipInfo;
    }

    private void loadCalendars(List<EquipCalendar> equipCalendars) throws ParseException {
        Date startDate = new Date();
        for (EquipCalendar equipCalendar : equipCalendars) {
            loadCalendar(startDate, equipCalendar);
        }
    }

    private void loadCalendar(Date startDate, EquipCalendar equipCalendar)
            throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(FORMAT_STRING);
        List<EquipCalShift> shifts = equipCalendar.getEquipCalShift();
        for (EquipCalShift equipCalShift : shifts) {
            Date start = dateFormat.parse(equipCalendar.getDateOfWork()
                    + equipCalShift.getShiftStartTime());
            Date end = dateFormat.parse(equipCalendar.getDateOfWork()
                    + equipCalShift.getShiftEndTime());
            
            // 如果班次中的结束时间小于了开始的时间，说明结束时间是第二天，故结束事件加一天
 			if (Integer.parseInt(equipCalShift.getShiftEndTime()) < Integer.parseInt(equipCalShift.getShiftStartTime())) {
 				Calendar calendar = Calendar.getInstance();
 				calendar.setTime(end);
 				calendar.add(Calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
 				end = calendar.getTime();
 			}
         			
            if (startDate != null && end.getTime() < startDate.getTime()) { // 不到开始时间
                continue;
            }
            if (startDate != null && startDate.getTime() > start.getTime()) { // 太早开始
                start = startDate;
            }

            timeRangesLeft.add(start, end);
            endBoundary = end.getTime();
        }
    }

    /**
     * 尝试占用并返回最早时间
     * @param timeToOccupy
     * @param earliestStartTime
     * @param latestStartTime
     * @param strategy
     * @return
     */
    public Long tryOccupy(long timeToOccupy, Long earliestStartTime, Long latestStartTime, Strategy strategy) {
        Range range = calculateRangeToOccupy(timeToOccupy, earliestStartTime, latestStartTime, strategy);
        if (range == null) {
            return null;
        }
        Long occupiedMin = calculateOccupiedMin(range, timeToOccupy, earliestStartTime, latestStartTime, strategy);
        checkTimeRangesLeft(strategy);
        return occupiedMin;
    }

    /**
     * 占据能力
     *
     * @param strategy 排程策略
     * @return null if没有占据能力，此时应排到其他能力上
     * @author chanedi
     * @date 2014年1月13日 下午2:14:55
     * @see
     */
    public Range occupy(long timeToOccupy, List<Range> lastRanges, Long latestStartTime, Strategy strategy) {
        Long earliestStartTime = lastRanges == null ? null : RangeUtils.getRangeMaximum(lastRanges);
        Long oriEarliestStartTime = earliestStartTime;
        if (!strategy.isNotCombine()) {
            earliestStartTime = strategy.getLastOccupied().getMaximum();
        }
        if (!strategy.isNewWorkerOrder()) {
            latestStartTime = earliestStartTime;
            if (oriEarliestStartTime != null && earliestStartTime < oriEarliestStartTime) {
                // 不能连续生产
                return null;
            }
        }

        Range range = calculateRangeToOccupy(timeToOccupy, earliestStartTime, latestStartTime, strategy);
        if (range == null) {
            return null;
        }
        Long occupiedMin = calculateOccupiedMin(range, timeToOccupy, earliestStartTime, latestStartTime, strategy);
        if (occupiedMin == null) {
            checkTimeRangesLeft(strategy);
            return null;
        }

        logger.debug("=================" + this.toString());
        String rangeToOccupy = range.toString();

        // 因为产生了一个最左分段，为了防止合并先缓存
        Range rangeLeft = new Range(range.getMinimum(), occupiedMin);
        long occupiedMax = occupiedMin + timeToOccupy;
        // 修正当前range的left值 ,此range为最右一段
        range.setMinimum(occupiedMax);

        Range rangeOccupied = new Range(occupiedMin, occupiedMax);
        rangeOccupied.setLastRanges(lastRanges);
        logger.debug("待排容量：timeToOccupy:{}, earliestStartTime:{}, latestStartTime:{}, rangeToOccupy:{}, rangeOccupied:{}, endBoundary:{}, equipCode:{}, strategy:{}", timeToOccupy, earliestStartTime, latestStartTime, rangeToOccupy, rangeOccupied, endBoundary, equipInfo.getCode(), strategy);
        if (rangeOccupied.contains(endBoundary)) {
            endBoundaryNotUsed = false;
        }
        timeRangesOccupied.add(rangeOccupied, false);

		/*
         *  修正timeRangesLeft
		 */
        checkTimeRangesLeft(strategy);
        if (range.getLong() <= 0) {
            timeRangesLeft.remove(range);
        }
        if (rangeLeft.getLong() > 0) {
            timeRangesLeft.add(rangeLeft);
        }

        return rangeOccupied;
    }

    public void occupy(Date startTime, Date endTime) {
        if (endTime.getTime() > endBoundary) {
            loadCapacity();
        }
        Range occupy = new Range(startTime.getTime(), endTime.getTime());
        timeRangesOccupied.add(occupy);
        for (int i = 0; i < timeRangesLeft.size(); i++) {
            Range range = timeRangesLeft.get(i);
            if (range.isBefore(occupy)) {
                continue;
            }

            long oriMax = range.getMaximum();
            // 有交集
            // 修正前段
            range.setMaximum(startTime.getTime());
            if (range.getLong() == 0) {
                timeRangesLeft.remove(i);
            }

            // 加后段
            timeRangesLeft.add(endTime.getTime(), oriMax);
            break;
        }
    }

    /**
     * 压缩空档，超过maximum的不压缩
     *
     * @author chanedi
     * @date 2014-2-27 下午9:26:51
     * @param maximum
     */
    public void compress(Long maximum) {
//        logger.debug("容量压缩，压缩前容量：{}", this);
        if (timeRangesLeft.getLong() == 0) {
            return;
        }
        if (timeRangesLeft.getLong() == 0) {
            return;
        }
        timeRangesLeftBeforeCompress = (RangeList) timeRangesLeft.clone();
        timeRangesOccupiedBeforeCompress = (RangeList) timeRangesOccupied.clone();

        // 将前段的timeRangesLeft都消除
        ListIterator<Range> rangeOccupiedIterator = (ListIterator<Range>) timeRangesOccupied.iterator();
        while (rangeOccupiedIterator.hasNext()) {
            Range rangeOccupied = rangeOccupiedIterator.next();
            if (maximum != null && rangeOccupied.getMinimum() >= maximum) {
                break;
            }

            String characteristics = rangeOccupied.getCharacteristics();

            Iterator<Range> rangeLeftIterator = timeRangesLeft.iterator();
            while (rangeLeftIterator.hasNext()) {
                Range rangeLeft = rangeLeftIterator.next();
                if (rangeLeft.isAfter(rangeOccupied)) {
                    break;
                }
                if (!rangeLeft.isAdjacentBefore(rangeOccupied)) {
                    continue;
                }
                // 相邻且rangeLeft在前，可以前移

                List<Range> combinedRangeOccupied = new ArrayList<Range>();
                long combinedRangesLong = rangeOccupied.getLong();
                boolean nexted = false;
                while(rangeOccupiedIterator.hasNext()) {
                    nexted = true;
                    Range cRangeOccupied = rangeOccupiedIterator.next();
                    if (cRangeOccupied.getCharacteristics().equals(characteristics)) {
                        combinedRangeOccupied.add(cRangeOccupied);
                        combinedRangesLong += cRangeOccupied.getLong();
                    } else {
                        break;
                    }
                }
                if (nexted) {
                    rangeOccupiedIterator.previous();
                }

                long minBoundary = 0;
                int size = combinedRangeOccupied.size();
                if (size > 0) {
                    Range lastRange = combinedRangeOccupied.get(size - 1);// 最后一个可合并的
                    combinedRangesLong -= lastRange.getLong();
                    List<Range> lastRanges = lastRange.getLastRanges();
                    if (lastRanges != null) {
                        minBoundary = RangeUtils.getRangeMaximum(lastRanges) - combinedRangesLong;
                    }
                }

                long lastOriMax = rangeOccupied.getMaximum();
                if (!rangeOccupied.moveBefore(rangeLeft, minBoundary)) { // debug点
                    // 移动不成功
                    break;
                }

                long lastOccupiedMax = rangeOccupied.getMaximum();
                for (Range range : combinedRangeOccupied) {
                    lastOriMax = range.getMaximum();
                    range.moveBefore(lastOccupiedMax);
                    lastOccupiedMax = range.getMaximum();
                }


                if (rangeLeft.getLong() == 0) {
                    rangeLeftIterator.remove();
                }
                // 移动成功后的后置空档 debug点
                timeRangesLeft.add(lastOccupiedMax, lastOriMax);
                break;
            }
        }

//        logger.debug("容量压缩，压缩后容量：{}", this);
    }

    public void undoCompress() {
        if (timeRangesLeftBeforeCompress != null) {
            timeRangesLeft = timeRangesLeftBeforeCompress;
            timeRangesLeftBeforeCompress = null;
        }
        if (timeRangesOccupiedBeforeCompress != null) {
            for (int i = 0; i < timeRangesOccupied.size(); i++) {
                timeRangesOccupied.get(i).setRange(timeRangesOccupiedBeforeCompress.get(i)); // 不能改变引用地址
            }
            timeRangesOccupiedBeforeCompress = null;
        }
    }

    public long getLeftLong() {
        return timeRangesLeft.getLong();
    }

    public boolean isEmpty() {
        return timeRangesLeft.getLong() <= 0;
    }

    private Range calculateRangeToOccupy(long timeToOccupy, Long earliestStartTime, Long latestStartTime, Strategy strategy) {
        if (earliestStartTime != null && latestStartTime != null && earliestStartTime > latestStartTime && strategy.isLimitEndDate()) {
            return null;
        }
        int i = 0;
        if (earliestStartTime != null && earliestStartTime > endBoundary) {
            if (strategy.isLimitedCapacity()) {
                return null;
            } else {
                i = timeRangesLeft.size(); // 从新申请的容量开始找容量排程
                loadCapacity();
            }
        }

        checkTimeRangesLeft(strategy);
        boolean safeOccupy = false;
        Range range = null;
        if (!strategy.isAllowInsert()) {
            // 直接从最后开始
            i = timeRangesLeft.size() - 1;
        }
        for (; i < timeRangesLeft.size(); i++) {
            range = timeRangesLeft.get(i);
            if (!strategy.isLatest() && range.isBefore(earliestStartTime)) {
                continue;
            }
            if (strategy.isLatest() && range.isAfter(latestStartTime + timeToOccupy)) {
                continue;
            }

            // 可以开始排的位置
            Range nearestOccupiedRange = getNearestOccupiedRange(range);
            safeOccupy = isSafeOccupy(range, earliestStartTime, latestStartTime, timeToOccupy,
                    nearestOccupiedRange, strategy);
            if (safeOccupy) {
                break;
            } else if (!strategy.isNotCombine() && range.contains(endBoundary)) {
                // 合并生产必须从此开始排
                checkTimeRangesLeft(strategy);
                return null;
            }
        }
        if (range == null) {
            // 没有容量
            checkTimeRangesLeft(strategy);
            return null;
        }
        if (!safeOccupy) {
            if (strategy.isLimitEndDate()) {
                // 排程失败
                checkTimeRangesLeft(strategy);
                return null;
            }
        }

        return range;
    }

    /**
     * 最晚开始时反序。
     *
     * @author chanedi
     * @date 2014-2-27 下午5:37:50
     */
    private boolean checkTimeRangesLeft(Strategy strategy) {
        if (strategy != null && strategy.isLatest()) {
            Collections.reverse(timeRangesLeft);
            return true;
        }
        return false;
    }

    /*
     * 在指定range后的occupiedRange，业务上不可能出现两个range有交集的情况
     */
    private Range getNearestOccupiedRange(Range range) {
        for (Range occupiedRange : timeRangesOccupied) {
            if (occupiedRange.isBefore(range)) {
                continue;
            }

            return occupiedRange;
        }
        return null;
    }

    /*
     * 1.允许加班，但能力已经被占用了不能排 2.如果range包括endBoundary则一定可排
     */
    private boolean isSafeOccupy(Range range, Long earliestStartTime, Long latestStartTime,
                                 long timeToOccupy, Range nearestOccupiedRange, Strategy strategy) {
//        logger.debug("是否可安全排程：range:{}, earliestStartTime:{}, latestStartTime:{}, timeToOccupy:{}", range, earliestStartTime, latestStartTime, timeToOccupy);
        // 限制两边
        if (range.contains(earliestStartTime)) {
            range = new Range(earliestStartTime, range.getMaximum());
        }

        if (range.isBefore(earliestStartTime)) {
            return false;
        }

        if (range.isAfter(latestStartTime)) {
            return false;
        }

        if (range.contains(endBoundary) && endBoundaryNotUsed) { // range包括endBoundary
            if (!strategy.isLimitedCapacity()) {
                loadCapacity();
            } else {
                return true;
            }
        }
        if (range.getLong() >= timeToOccupy) { // range够大
            return true;
        }

        if (!strategy.isNewWorkerOrder()) { // 不能连续生产
            return false;
        }

        // LimitEndDate 且 不允许加班
        if (strategy.isLimitEndDate() && (!strategy.isAllowOvertime())) {
            return false;
        }

        // range 不够大 且 加班时间或强制想占用时间(NotLimitEndDate)已被占用
        if (nearestOccupiedRange != null && range.getMinimum() + timeToOccupy > nearestOccupiedRange
                .getMinimum()) {
            return false;
        }

        // 加班也不能完成
        if (range.getLong() + overworkTime < timeToOccupy) {
            return false;
        }

        logger.warn("未知的情况！");
        throw new RuntimeException("未知的情况！just for debug，如需要生产单数据请注释此行并return ture");
//        return true;
    }

    private Long calculateOccupiedMin(Range range, long timeToOccupy, Long earliestStartTime, Long latestStartTime, Strategy strategy) {
        if (strategy.isLatest() && strategy.isLimitEndDate()) {
            if (range.contains(latestStartTime + timeToOccupy)) {
                return latestStartTime;
            } else if (range.contains(endBoundary) && endBoundaryNotUsed) {
                return latestStartTime;
            } else {
                return range.getMaximum() - timeToOccupy;
            }
        } else {
            Long min = null;
            if (range.isBefore(earliestStartTime)) { // earliestStartTime超过三天
                return null;
            } else if (range.contains(earliestStartTime)) {
                min = earliestStartTime;
            } else {
                min = range.getMinimum();
            }
            if (range.contains(endBoundary)) {
                return min;
            } else if (min + timeToOccupy > range.getMaximum()) {
                // 最后一段也排不下，超过三天了
                return null;
            } else {
                return min;
            }
        }
    }

    /**
     * 申请新的容量
     */
    private void loadCapacity() {
        Date begin = new Date(this.endBoundary);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        calendar.add(Calendar.MONTH, (int) Math.pow(3, loadMonthPower++));
        Date end = calendar.getTime();
        EquipCalendarService equipCalendarService = (EquipCalendarService) ContextUtils.getBean(EquipCalendarService.class);
        List<EquipCalendar> addEquipCalendarList = equipCalendarService.getByEquipInfo(equipInfo, begin, end);
        equipInfo.getEquipCalendar().addAll(addEquipCalendarList);

        try {
            loadCalendars(addEquipCalendarList);
        } catch (ParseException e) {
            throw new InconsistentException("fac.dateError");
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("剩余容量：[");
        for (Range range : timeRangesLeft) {
            sb.append(range.toString());
        }
        sb.append("]");

        sb.append("已用容量：[");
        for (Range range : timeRangesOccupied) {
            sb.append(range.toString());
        }
        sb.append("]");
        return sb.toString();
    }

}
