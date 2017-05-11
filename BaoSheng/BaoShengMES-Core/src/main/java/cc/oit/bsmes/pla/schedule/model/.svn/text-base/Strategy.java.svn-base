package cc.oit.bsmes.pla.schedule.model;

import lombok.Data;
import cc.oit.bsmes.common.util.Range;

/**
 * Created by chanedi on 14-4-3.
 */
@Data
public class Strategy implements Cloneable {

    /**
     * 为false则即使超过加班时间限制或最晚开始时间也强制占据，默认为true
     */
    private boolean limitEndDate = true;

    /**
     * 为true则允许加班，默认为true
     * 注：暂时全部不允许加班
     */
    private boolean allowOvertime = false;

    public void setAllOvertime(boolean allowOvertime) {
        this.allowOvertime = false;
    }

    /**
     * 为true则从最晚时间开始排程，
     * 为false则从最早时间开始排程，默认为true
     */
    private boolean isLatest = true;

    /**
     * 为true则允许插入空档，默认为true
     */
    private boolean allowInsert = true;// TODO 未用到

    /**
     * 为true则需要计算SetUpTime，默认为true
     */
    private boolean includeSetUpTime = true;

    /**
     * 为true则需要计算ShutDownTime，默认为true
     */
    private boolean includeShutDownTime = true;

    /**
     * 为false则和上一张工单相同工单号，默认为true
     */
    private boolean newWorkerOrder = true;

    /**
     * 为false则表示是合并排程，默认为true
     */
    private boolean notCombine = true;

    /**
     * 为false则遇endBoundary时申请新的容量（for sop），默认为true
     */
    private boolean limitedCapacity = true;

    /**
     * 上一占用时间，用于合并排程
     */
    private Range lastOccupied = null;

//    public void setLastOccupied(CustomerOrderItemProDec lastOrder) {
//        OrderTask lastOrderTask = lastOrder.getOrderTask();
//        if (lastOrderTask != null) {
//            lastOccupied = lastOrderTask.getRange();
//        }
//    }

//    public void setLastOccupied(Range range) {
//        lastOccupied = range;
//    }

    @Override
    public Strategy clone() throws CloneNotSupportedException {
        return (Strategy) super.clone();
    }
}
