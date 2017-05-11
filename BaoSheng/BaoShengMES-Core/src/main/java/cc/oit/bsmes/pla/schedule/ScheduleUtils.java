package cc.oit.bsmes.pla.schedule;

import cc.oit.bsmes.common.constants.ProcessCode;
import cc.oit.bsmes.common.util.Range;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.schedule.model.IScheduleable;
import cc.oit.bsmes.pla.schedule.model.Strategy;
import cc.oit.bsmes.pro.model.EquipList;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

/**
 * Created by 羽霓 on 2014/5/16.
 */
public class ScheduleUtils {

    /**
     * 计算花费的时间
     *
     * @return
     * @author chanedi
     * @date 2014-2-28 下午1:15:29
     * @see
     */
    public static long getProcessTime(EquipList equipList, double taskLength, Strategy strategy) {
        double equipCapacity = equipList.getEquipCapacity();
        long time = (long) (taskLength / equipCapacity * 1000);
        time = strategy.isIncludeSetUpTime() ? time + equipList.getSetUpTimeMilSec() : time;
        time = strategy.isIncludeShutDownTime() ? time + equipList.getShutDownTimeMilSec() : time;
        return time;
    }

    public static boolean needReverse(IScheduleable order, boolean emptyFirst, Strategy strategy) {
        // 复绕任务找最空设备排,强制找最空设备排
        return emptyFirst || (strategy.isLimitEndDate() && ProcessCode.RESPOOL.name().equals(order.getProductProcess().getProcessCode()));
    }

    public static List<EquipInfo> getPriorityEquipInfos(CustomerOrderItemProDec order, ResourceCache resourceCache, Map<String, EquipInfo> equipInfoMap) {
        String code = order.getEquipCode();
        if (code == null) {
            return null;
        } else {
            List<EquipInfo> equipInfos = new ArrayList<EquipInfo>();
            equipInfos.add(equipInfoMap.get(code));
            return equipInfos;
        }
    }

    public static List<String> getPriorityEquipCodes(CustomerOrderItemProDec order) {
        String code = order.getEquipCode();
        if (code == null) {
            return null;
        } else {
            List<String> equipCodes = new ArrayList<String>();
            equipCodes.add(code);
            return equipCodes;
        }
    }

    public static List<EquipInfo> getDefaultEquipInfos(CustomerOrderItemProDec order, boolean needReverse, ResourceCache resourceCache) {
        List<EquipInfo> equipInfos = resourceCache.getDefaultEquips(order.getProcessId(), 1);
        if (needReverse) {
            Collections.reverse(equipInfos);
        }
        return equipInfos;
    }

    public static List<String> getDefaultEquipCodes(CustomerOrderItemProDec order, ResourceCache resourceCache) {
        List<EquipInfo> equipInfos = resourceCache.getDefaultEquips(order.getProcessId());
        List<String> equipCodes = new ArrayList<String>(equipInfos.size());
        for (EquipInfo equipInfo : equipInfos) {
            equipCodes.add(equipInfo.getCode());
        }
        return equipCodes;
    }

    public static List<EquipInfo> getOptionalEquipInfos(CustomerOrderItemProDec order, boolean needReverse, ResourceCache resourceCache, Map<String, EquipInfo> equipInfoMap) {
        String[] optionalEquipCodes = order.getOptionalEquipCodes();
        List<EquipInfo> optionalEquips = new ArrayList<EquipInfo>(optionalEquipCodes.length);
        for (String optionalEquipCode : optionalEquipCodes) {
            optionalEquips.add(equipInfoMap.get(optionalEquipCode));
        }
        ResourceCache.sortEquipInfos(optionalEquips, 1);
        if (needReverse) {
            Collections.reverse(optionalEquips);
        }
        return optionalEquips;
    }

    public static List<String> getOptionalEquipCodes(CustomerOrderItemProDec order) {
        String[] optionalEquipCodes = order.getOptionalEquipCodes();
        return Arrays.asList(optionalEquipCodes);
    }

    public static List<EquipInfo> getAvailableEquipInfos(CustomerOrderItemProDec order, boolean needReverse, ResourceCache resourceCache, Map<String, EquipInfo> equipInfoMap) {
        List<EquipInfo> equipInfos = ScheduleUtils.getPriorityEquipInfos(order, resourceCache, equipInfoMap);
        if (equipInfos != null) {
            // 如果有固定设备直接选用固定设备
            return equipInfos;
        }

        equipInfos = ScheduleUtils.getDefaultEquipInfos(order, needReverse, resourceCache);
        equipInfos.addAll(ScheduleUtils.getOptionalEquipInfos(order, needReverse, resourceCache, equipInfoMap));
        return equipInfos;
    }

    public static List<String> getAvailableEquipCodes(CustomerOrderItemProDec order, ResourceCache resourceCache) {
        List<String> equipCodes = ScheduleUtils.getPriorityEquipCodes(order);
        if (equipCodes != null) {
            // 如果有固定设备直接选用固定设备
            return equipCodes;
        }

        equipCodes = ScheduleUtils.getDefaultEquipCodes(order, resourceCache);
        equipCodes.addAll(ScheduleUtils.getOptionalEquipCodes(order));
        return equipCodes;
    }

    public static List<Range> getLastRanges(CustomerOrderItemProDec order) {
        List<CustomerOrderItemProDec> lastOrders = order.getLastOrders();
        List<Range> lastRanges = new ArrayList<Range>();
        if (lastOrders != null) {
            for (CustomerOrderItemProDec lastOrder : lastOrders) {
                if (lastOrder==null || lastOrder.getOrderTask() == null) {
                    continue;
                }
                lastRanges.add(lastOrder.getOrderTask().getRange());
            }
        }
        return lastRanges;
    }

    public static boolean isVirtualProcess(String processCode) {
        return ProcessCode.JIANYAN.equals(processCode) || ProcessCode.KAIZHUANG_PT.equals(processCode);
    }


}
