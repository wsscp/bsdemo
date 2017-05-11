package cc.oit.bsmes.eve.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.concurrent.NotThreadSafe;

import cc.oit.bsmes.common.context.ContextUtils;
import cc.oit.bsmes.fac.model.EquipMaintainState;
import cc.oit.bsmes.fac.service.EquipMaintainStateService;

/**
 * Created by jijy on 2014/10/23.
 * 应能保证每次只有一个线程操作
 */
@NotThreadSafe
public class MaintainCache {

    private static Map<String, Map<String, EquipMaintainState>> equipMaintainStates = new HashMap<String, Map<String, EquipMaintainState>>();
	private static Map<String, Object> resultMap = new HashMap<String, Object>();
	// 数据过期时间 暂定5分钟
	private static long ems_expired_milliseconds = 1000 * 60 * 5;
	private static final String TIME_KEY = "time";

	static {
		resultMap.put(TIME_KEY, System.currentTimeMillis());
	}

    public static void setEquipMaintainStates(List<EquipMaintainState> cacheList, String orgCode) {
        Map<String, EquipMaintainState> equipMaintainStateMap = new HashMap<String, EquipMaintainState>();
        for (EquipMaintainState eventInformation : cacheList) {
            equipMaintainStateMap.put(eventInformation.getEquipCode(), eventInformation);
        }

        equipMaintainStates.put(orgCode, equipMaintainStateMap);
    }

	/**
	 * 超过五分钟就执行更新，否则缓存中取
	 * */
    public static Map<String, EquipMaintainState> getEquipMaintainStates(String orgCode) {
		long oldtime = (Long) resultMap.get(TIME_KEY);
		long now = System.currentTimeMillis();
		if ((now - oldtime) > ems_expired_milliseconds||equipMaintainStates.get(orgCode)==null) {

        	EquipMaintainStateService equipMaintainStateService = (EquipMaintainStateService) ContextUtils.getBean(EquipMaintainStateService.class);            
            List<EquipMaintainState> equipMaintainStates = equipMaintainStateService.getUncompletedMaintainSates(orgCode);
            MaintainCache.setEquipMaintainStates(equipMaintainStates, orgCode);            
			// equipInfoService.createMaintainEvent(orgCode);

			resultMap.put(TIME_KEY, System.currentTimeMillis());
		}
        return equipMaintainStates.get(orgCode);
    }

}
