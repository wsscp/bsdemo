package cc.oit.bsmes.pla.schedule.matcher;

import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.common.util.Range;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.pla.constants.PreciseMatchMode;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.schedule.AbstractEquipMatcher;
import cc.oit.bsmes.pla.schedule.ScheduleUtils;
import cc.oit.bsmes.pla.schedule.model.Capacity;
import cc.oit.bsmes.pla.schedule.model.IScheduleable;
import cc.oit.bsmes.pla.schedule.model.OrderListToSchedule;
import cc.oit.bsmes.pla.schedule.model.Strategy;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.wip.model.WorkOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 羽霓 on 2014/4/14.
 * 关于压缩：压缩会受到前后道工序的限制三天排程中前道工序压缩后道工序还是可能留空，
 * 尤其前后工序在同一设备上时设备压缩并不完整。但是考虑到合并生产可以允许空档（可能造成后续订单排程失败，但是继续压缩成功的可能性似乎也不大）
 * 三天后排程的压缩应依次压缩所有工序。再开始三天后订单的排程。
 */
public class PreciseEquipMatcher extends AbstractEquipMatcher {

    @Setter
    private PreciseMatchMode preciseMatchMode;
    @Setter
    private Map<CustomerOrderItemProDec, OrderListToSchedule> orderListToScheduleMap;
    @Getter
    private List<CustomerOrderItemProDec> ordersMatched; // 多次排程后结果
    private List<CustomerOrderItemProDec> ordersToSchedule; // 用于防止重排，当成功排程从此集合中删除，排程时订单不在集合中则跳过

    public PreciseEquipMatcher(ResourceCache resourceCache, String orgCode, Date endDate, List<CustomerOrderItemProDec> ordersToSchedule) {
        super(resourceCache, orgCode, endDate);
        this.ordersToSchedule = ordersToSchedule;

        ordersMatched = new ArrayList<CustomerOrderItemProDec>();

    }

    /**
     * 如成功排程则删除集合中订单并返回最后一个订单(入口)
     */
    @Override
    public CustomerOrderItemProDec match(IScheduleable scheduleable) {
        switch (preciseMatchMode) {
            case PRIORITY:
                strategy = new Strategy();
                strategy.setLatest(false);
                strategy.setAllowInsert(false);
                break;
            default:
                strategy = new Strategy();
                strategy.setLatest(false);// 已合并过，无需按最晚排程，也无须压缩
        }

        // 最空设备排程(允许延期)
        List<Range> ranges = match(scheduleable, true, false);
        if (ranges == null) {
            ranges = match(scheduleable, false, true);
        }

        if (ranges == null || ranges.size() == 0) {
            return null;
        }

        List<CustomerOrderItemProDec> orders = scheduleable.getOrders().subList(0, ranges.size());
        CustomerOrderItemProDec returnOrder = orders.get(orders.size() - 1);
        ordersToSchedule.removeAll(orders);
        if (ranges.size() != scheduleable.getOrders().size()) {
            // 未全部排完
            for (int i = 0; i < ranges.size(); i++) {
                scheduleable.getOrders().remove(0);
            }
        }
        return returnOrder;
    }

    protected List<Range> match(IScheduleable order, boolean limitEndDate, boolean emptyFirst) {
        strategy.setLimitEndDate(limitEndDate);
        List<EquipInfo> equipInfos = getAvailableEquipInfos(order, emptyFirst);
        for (EquipInfo equip : equipInfos) {
            List<Range> range = rootMatch(order, equip);
            if (range != null) {
                return range;
            }
        }
        return null;
    }

    private List<EquipInfo> getAvailableEquipInfos(IScheduleable order, boolean emptyFirst) {
        boolean needReverse = ScheduleUtils.needReverse(order, emptyFirst, strategy);
        if (order instanceof OrderListToSchedule) {
            return ((OrderListToSchedule) order).getAvailableEquipInfos(needReverse, equipInfoMap);
        } else if (order instanceof CustomerOrderItemProDec) {
            return ScheduleUtils.getAvailableEquipInfos((CustomerOrderItemProDec) order, needReverse, resourceCache, equipInfoMap);
        }
        return new ArrayList<EquipInfo>();
    }

    @Override
    public List<Range> rootMatch(IScheduleable order, EquipInfo equipInfo) {
        List<Range> ranges = super.rootMatch(order, equipInfo);
        if (ranges != null) {
            // 可能部分未排成功
            ordersMatched.addAll(order.getOrders().subList(0, ranges.size()));
        }
        return ranges;
    }

    public void match(WorkOrder pausedWorkOrder) {
        if (strategy == null) {
            strategy = new Strategy();
            strategy.setLatest(false);
        }
        EquipList equipList = resourceCache.getEquipList(pausedWorkOrder.getProductProcess().getId()
                + BusinessConstants.CAPACITY_KEY_SEPARATOR + pausedWorkOrder.getEquipCode());
        Capacity capacity = capacityLeft.get(pausedWorkOrder.getEquipCode());
        long time = ScheduleUtils.getProcessTime(equipList, pausedWorkOrder.getOrderLength(), strategy);
        Range occupied = capacity.occupy(time, null, null, strategy);
        pausedWorkOrder.setPreStartTime(new Date(occupied.getMinimum())); // real start 怎么办？
        pausedWorkOrder.setPreEndTime(new Date(occupied.getMaximum()));
    }

}
