package cc.oit.bsmes.pla.schedule.model;

import cc.oit.bsmes.common.util.Range;
import cc.oit.bsmes.common.util.RangeUtils;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.pla.model.CustomerOrder;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.schedule.ScheduleUtils;
import cc.oit.bsmes.pro.model.ProductProcess;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Data;

import java.util.*;

/**
 * Created by 羽霓 on 2014/5/21.
 */
@Data
public class OrderListToSchedule implements IScheduleable {

    private ResourceCache resourceCache;

    private List<CustomerOrderItemProDec> orders;

    private List<String> defaultEquipCodes;

    private List<String> optionalEquipCodes;

    private List<EquipInfo> defaultEquipInfos;

    private List<EquipInfo> optionalEquipInfos;

    private ProductProcess productProcess;

    private Double unFinishedLength;

    public OrderListToSchedule(ResourceCache resourceCache) {
        this.resourceCache = resourceCache;
    }

    @Override
    public ProductProcess getProductProcess() {
        if (productProcess == null) {
            productProcess = orders.iterator().next().getProductProcess();
        }
        return productProcess;
    }

    public List<EquipInfo> getAvailableEquipInfos(boolean needReverse, Map<String, EquipInfo> equipInfoMap) {
        if (defaultEquipInfos == null) {
            defaultEquipInfos = new ArrayList<EquipInfo>(defaultEquipCodes.size());
            for (String defaultEquipCode : defaultEquipCodes) {
                defaultEquipInfos.add(equipInfoMap.get(defaultEquipCode));
            }
            ResourceCache.sortEquipInfos(defaultEquipInfos, 1);
            if (optionalEquipCodes != null && optionalEquipCodes.size() > 0) {
                for (String optionalEquipCode : optionalEquipCodes) {
                    optionalEquipInfos.add(equipInfoMap.get(optionalEquipCode));
                }
                ResourceCache.sortEquipInfos(optionalEquipInfos, 1);
            } else {
                optionalEquipInfos = new ArrayList<EquipInfo>();
            }
        }

        List<EquipInfo> dEquipInfos = new ArrayList<EquipInfo>(defaultEquipInfos);
        List<EquipInfo> oEquipInfos = new ArrayList<EquipInfo>(optionalEquipInfos);
        if (needReverse) {
            Collections.reverse(dEquipInfos);
            Collections.reverse(oEquipInfos);
        }
        dEquipInfos.addAll(oEquipInfos);
        return dEquipInfos;
    }

    public Double getUnFinishedLength() {
        if (unFinishedLength == null) {
            unFinishedLength = 0d;
            for (CustomerOrderItemProDec order : orders) {
                unFinishedLength += order.getUnFinishedLength();
            }
        }
        return unFinishedLength;
    }

    public Long getLatestStartTime(long time) {
        Long startTime = null;
        for (CustomerOrderItemProDec order : orders) {
            Long curLatest = order.getLatestStartTime(time);
            if (curLatest == null) {
                continue;
            }

            startTime = startTime == null ? curLatest : Math.min(startTime, curLatest);
        }
        return startTime;
    }

    public Long getEarliestStartTime() {
        Long startTime = null;
        for (CustomerOrderItemProDec order : orders) {
            List<Range> lastRanges = ScheduleUtils.getLastRanges(order);
            if (lastRanges == null || lastRanges.size() == 0) {
                continue;
            }
            long curLatest = RangeUtils.getRangeMaximum(lastRanges);

            startTime = startTime == null ? curLatest : Math.max(startTime, curLatest);
        }
        return startTime;
    }

    public void setOrders(List<CustomerOrderItemProDec> orders) {
        this.orders = new ArrayList<CustomerOrderItemProDec>();

        List<String> keyList = new ArrayList<String>();
        Multimap<String, CustomerOrderItemProDec> orderMap = ArrayListMultimap.create();
        //调整相同合同
        for (CustomerOrderItemProDec order : orders) {
            String key = order.getContractNo();
            if (orderMap.get(key).size() == 0) {
                keyList.add(key);
            }
            orderMap.put(key, order);
        }
        for (String key : keyList) {
            this.orders.addAll(orderMap.get(key));
        }
    }

    @Override
    public List<CustomerOrderItemProDec> getOrders() {
        return orders;
    }

    public CustomerOrderItemProDec getLastOrder() {
        if (orders.size() == 0) {
            return null;
        }
        return orders.get(orders.size() - 1);
    }
}
