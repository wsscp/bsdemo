package cc.oit.bsmes.pla.schedule;

import cc.oit.bsmes.common.constants.BusinessConstants;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.WorkOrderStatus;
import cc.oit.bsmes.common.exception.InconsistentException;
import cc.oit.bsmes.common.util.*;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.model.OrderTask;
import cc.oit.bsmes.pla.schedule.model.Capacity;
import cc.oit.bsmes.pla.schedule.model.IScheduleable;
import cc.oit.bsmes.pla.schedule.model.OrderListToSchedule;
import cc.oit.bsmes.pla.schedule.model.Strategy;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProductProcess;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author chanedi
 * @date 2014-2-28 下午1:26:01
 */
public abstract class AbstractEquipMatcher implements IEquipMatcher {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected Map<String, EquipInfo> equipInfoMap; // equipInfo.getCode(), equipInfo 注：不可移动此缓存到resourcecache，此设备中有ordertask
    protected Map<String, Capacity> capacityLeft; // equipInfo.getCode(), capacity
    protected ResourceCache resourceCache;
    protected String orgCode;
    protected Strategy strategy;
    protected long endDate; // for：如completeMatch中会申请新的容量

    public AbstractEquipMatcher(ResourceCache resourceCache, String orgCode) {
        equipInfoMap = new HashMap<String, EquipInfo>();
        capacityLeft = new HashMap<String, Capacity>();
        this.resourceCache = resourceCache;
        this.orgCode = orgCode;
    }

    public AbstractEquipMatcher(ResourceCache resourceCache, String orgCode, Date endDate) {
        this.resourceCache = resourceCache;
        capacityLeft = new HashMap<String, Capacity>();
        equipInfoMap = new HashMap<String, EquipInfo>();
        this.endDate = endDate.getTime();
        this.orgCode = orgCode;

        List<EquipInfo> equipInfos = resourceCache.getEquipInfoByOrgCode(orgCode);
        for (EquipInfo equipInfo : equipInfos) {
            if (equipInfo.getStatus() == EquipStatus.ERROR) { // 设备状态不正常
                continue;
            }
            equipInfoMap.put(equipInfo.getCode(), equipInfo);

            equipInfo.initOrderTasks();// OA等会变更内存

            Capacity capacity;
            try {
                OrderTask lastOrderTask = equipInfo.getLastOrderTask();
                Date workEndTime = lastOrderTask == null ? new Date() : lastOrderTask.getPlanFinishDate();
                capacity = new Capacity(workEndTime, equipInfo, endDate, orgCode);
            } catch (ParseException e) {
                throw new InconsistentException("fac.dateError");
            }

            capacityLeft.put(equipInfo.getCode(), capacity);
        }
    }

    /*
     * 最终match方法
     * @author chanedi
     * @date 2014-2-27 下午5:20:46
     * @return
     * @see
     */
    protected List<Range> rootMatch(IScheduleable scheduleable, EquipInfo equipInfo) {
        EquipList equipList = resourceCache.getEquipList(scheduleable.getProductProcess().getId()
                + BusinessConstants.CAPACITY_KEY_SEPARATOR + equipInfo.getCode());
        if (equipList == null) { // 不可使用此设备
            return null;
        }
        Capacity capacity = capacityLeft.get(equipInfo.getCode());
        List<Range>  ranges = null;
        Strategy strategy = null;
        try {
            strategy = this.strategy.clone();
            if (scheduleable instanceof CustomerOrderItemProDec) {
                Range range = rootMatch((CustomerOrderItemProDec) scheduleable, capacity, equipList, this.strategy);
                if (range != null) {
                    ranges = new ArrayList<Range>();
                    ranges.add(range);
                }
            } else if (scheduleable instanceof OrderListToSchedule) {
                ranges = rootMatch((OrderListToSchedule) scheduleable, capacity, equipList);
                strategy.setNotCombine(false);
                strategy.setNewWorkerOrder(scheduleable.getProductProcess().getSeq() != 1);
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace(); // 不可能的错误
        }
        if (ranges == null) {
            return null;
        }

        List<CustomerOrderItemProDec> orders = scheduleable.getOrders();
        List<OrderTask> orderTasks = equipInfo.getOrderTasks();
        CustomerOrderItemProDec lastOrder = null;
        for (int i = 0; i < ranges.size(); i++) {
            Range range = ranges.get(i);
            CustomerOrderItemProDec order = orders.get(i);

            OrderTask orderTask = new OrderTask(true);
            order.setOrderTask(orderTask);

            if (combineWorkOrder(strategy, lastOrder, order)) {
                orderTask.setWorkOrderNo(lastOrder.getOrderTask().getWorkOrderNo());
            }
            range.setCharacteristics(order.getHalfProductCode());
            orderTask.setRange(range);
            orderTask.setOrder(order);
            orderTask.setPlanStartDate(new Date(range.getMinimum()));
            orderTask.setPlanFinishDate(new Date(range.getMaximum()));
            orderTask.setEquipCode(equipInfo.getCode());
            orderTask.setOrderItemProDecId(order.getId());
            orderTask.setStatus(WorkOrderStatus.TO_AUDIT);
            orderTask.setProcessId(order.getProcessId());
            orderTask.setProcessPath(order.getProcessPath());
            orderTask.setIsDelayed(order.getLatestFinishDate().getTime() < range.getMaximum());
            orderTask.setOrgCode(order.getOrgCode());
            orderTask.setContractNo(order.getContractNo());
            orderTask.setProcessName(order.getProcessName());
            orderTask.setOperator(order.getOperator());
            orderTask.setProductCode(order.getProductCode());
            orderTask.setTaskLength(order.getUnFinishedLength());
            orderTask.setHalfProductCode(order.getHalfProductCode());
            orderTask.setIsDelayed(order.getLatestFinishDate().getTime() < range.getMaximum());
            orderTasks.add(orderTask);

            lastOrder = order;
        }

        return ranges;
    }

    private boolean combineWorkOrder(Strategy strategy, CustomerOrderItemProDec lastOrder, CustomerOrderItemProDec order) {
        if (lastOrder == null) {
            return false;
        }
       
        if(lastOrder.getContractNo().equals(order.getContractNo())&&lastOrder.getProductCode().equals(order.getProductCode()))
        {
        	return true;
        }
//        if (!strategy.isNewWorkerOrder()&&lastOrder.getContractNo().equals(order.getContractNo())&&lastOrder.getProductCode().equals(order.getProductCode())) {
//            return true;
//        }
      
        return false;
    }

    protected List<Range> rootMatch(OrderListToSchedule orderList, Capacity capacity, EquipList equipList) throws CloneNotSupportedException {
        // 预申请足够容量获取最早开始排程时间
        long time = ScheduleUtils.getProcessTime(equipList, orderList.getUnFinishedLength(), strategy);
        Long earliest = capacity.tryOccupy(time, orderList.getEarliestStartTime(), orderList.getLatestStartTime(time), strategy);
        if (earliest == null) {
            return null;
        }
        List<Range> ranges = new ArrayList();

        // 单个以最早策略排程
        Strategy strategy = this.strategy.clone();
        List<CustomerOrderItemProDec> orders = orderList.getOrders();
        strategy.setNotCombine(false);
        strategy.setNewWorkerOrder(false);
        strategy.setLastOccupied(new Range(earliest, earliest));
        for (CustomerOrderItemProDec orderItemProDec : orders) {
            Range range = rootMatch(orderItemProDec, capacity, equipList, strategy);
            if (range == null) {
                return ranges;
            }
            ranges.add(range);

            strategy.setLastOccupied(range);
            strategy.setIncludeSetUpTime(false);
            strategy.setIncludeShutDownTime(false);
        }

        return ranges;
    }

    protected Range rootMatch(CustomerOrderItemProDec order, Capacity capacity, EquipList equipList, Strategy strategy) {
        List<Range> lastRanges = ScheduleUtils.getLastRanges(order);
        ProductProcess productProcess = order.getProductProcess();
        if (productProcess.isSkippable()) {
            return new Range(RangeUtils.getRangeMaximum(lastRanges), RangeUtils.getRangeMaximum(lastRanges));
        }

        long time = ScheduleUtils.getProcessTime(equipList, order.getUnFinishedLength(), strategy);
        Range occupied = capacity.occupy(time, lastRanges, order.getLatestStartTime(time), strategy);
        if (occupied == null) {
//            logger.debug("-----------排程失败！订单{}，设备{}", order.getId(), equipList.getEquipCode());
            return null;
        }
        logger.debug("-----------排程成功！订单{}，设备{}，开始时间{}，结束时间{}，前置订单{}", order.getId(), equipList.getEquipCode(), new Date(occupied.getMinimum()), new Date(occupied.getMaximum()), order.printLastOrders());
        return occupied;
    }

    /**
     * 进行一次针对该设备的负载空隙压缩并排程
     *
     * @param order
     * @param equipInfo
     * @param maximum
     * @return
     */
    protected List<Range> compressMatch(IScheduleable order, EquipInfo equipInfo, Long maximum) {
        Capacity capacity = capacityLeft.get(equipInfo.getCode());
        capacity.compress(maximum);

        List<Range> ranges = rootMatch(order, equipInfo);
        if (ranges == null) {
            capacity.undoCompress();
        }
        return ranges;
    }

    public void compressAll() {
        Collection<EquipInfo> equipInfos = equipInfoMap.values();
        for (EquipInfo equipInfo : equipInfos) {
            compress(equipInfo);
        }
        for (EquipInfo equipInfo : equipInfos) {
            compress(equipInfo);
        }
    }

    /**
     * 进行一次针对该设备的负载空隙压缩
     *
     * @param equipInfo
     * @return
     */
    protected void compress(EquipInfo equipInfo) {
        Capacity capacity = capacityLeft.get(equipInfo.getCode());
        capacity.compress(null);
    }

    /**
     * 容量已空
     *
     * @param equipInfo
     * @return
     */
    protected boolean isEmptyCapacity(EquipInfo equipInfo) {
        Capacity capacity = capacityLeft.get(equipInfo.getCode());
        return capacity.getTimeRangesLeft().getLong() == 0;
    }

}
