package cc.oit.bsmes.pla.schedule.matcher;

import cc.oit.bsmes.common.exception.InconsistentException;
import cc.oit.bsmes.common.util.Range;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.WorkTask;
import cc.oit.bsmes.pla.model.CustomerOrderItemProDec;
import cc.oit.bsmes.pla.schedule.AbstractEquipMatcher;
import cc.oit.bsmes.pla.schedule.IEquipMatcher;
import cc.oit.bsmes.pla.schedule.model.Capacity;
import cc.oit.bsmes.pla.schedule.model.IScheduleable;
import cc.oit.bsmes.pla.schedule.model.Strategy;

import java.text.ParseException;
import java.util.List;

/**
 * Created by 羽霓 on 2014/4/14.
 */
public class SOPEquipMatcher extends AbstractEquipMatcher {

    public SOPEquipMatcher(ResourceCache resourceCache, String orgCode) {
        super(resourceCache, orgCode);

        List<EquipInfo> equipInfos = resourceCache.getEquipInfoByOrgCode(orgCode);
        for (EquipInfo equipInfo : equipInfos) {
            addEquip(equipInfo);
        }

        strategy = new Strategy();
        strategy.setLimitEndDate(false);
        strategy.setLatest(false);
        strategy.setLimitedCapacity(false);
    }

    @Override
    public CustomerOrderItemProDec match(IScheduleable order) {
        List<EquipInfo> equips = resourceCache.getDefaultEquips(order.getProductProcess().getId(), 1);
        if (equips.size() == 0) {
            return null;
        }

        EquipInfo equipInfo = equips.get(equips.size() - 1); // 找最空的排
        List<Range> ranges = super.rootMatch(order, equipInfo);
        if (ranges != null) {
            return (CustomerOrderItemProDec) order;
        }
        return null;
    }

    private void addEquip(EquipInfo equipInfo) {
        String code = equipInfo.getCode();
        if (equipInfoMap.get(code) != null) {
            return;
        }

        Capacity capacity;
        try {
            capacity = new Capacity(equipInfo, orgCode);
        } catch (ParseException e) {
            throw new InconsistentException("fac.dateError");
        }
        capacityLeft.put(code, capacity);
        equipInfoMap.put(code, equipInfo);

        for (WorkTask workTask : equipInfo.getWorkTasks()) {
            capacity.occupy(workTask.getWorkStartTime(), workTask.getWorkEndTime());
        }
    }

}
