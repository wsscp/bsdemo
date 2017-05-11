package cc.oit.bsmes.bas.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.bas.model.EquipEnergyMonitor;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.fac.model.EquipEnergyMonitor1;


public interface EquipEnergyMonitorService extends BaseService<EquipEnergyMonitor> {

	
	void insertEquipEnergyMonitorInfo()throws Exception ;
	
	public List<EquipEnergyMonitor> getEquipEnergyMonitor(Map<String, Object> findParams) ;
	
	public Map<String, Object> energyReceiptChart(String equipName, String startTime, String endTime);

	List<EquipEnergyMonitor> getEquipEnergyLoad(Map<String, Object> findParams);

	List<EquipEnergyMonitor> getEnergyQuantity(Map<String, Object> findParams);

//	void insertMonthEquipEnergyMonitorInfo();
//
//	List<EquipEnergyMonitor> getEquipEnergyMonthLoad(
//			Map<String, Object> findParams);
}
