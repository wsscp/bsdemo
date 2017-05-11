package cc.oit.bsmes.bas.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.bas.model.EquipEnergyMonitor;
import cc.oit.bsmes.bas.model.EquipEnergyMonthMonitor;
import cc.oit.bsmes.common.dao.BaseDAO;


public interface EquipEnergyMonitorDAO extends BaseDAO<EquipEnergyMonitor> {

	void updateMeterElectricDayData(EquipEnergyMonitor e);

	List<EquipEnergyMonitor> findEnergyHistory(EquipEnergyMonitor findparams);

	List<EquipEnergyMonitor> getEquipEnergyMonitor(
			Map<String, Object> findParams);

	List<EquipEnergyMonitor> getEquipEnergyLoad(Map<String, Object> findParams);

	List<EquipEnergyMonitor> getEnergyQuantity(Map<String, Object> findParams);

//	List<EquipEnergyMonthMonitor> getEquipEnergyMonthLoad(
//			Map<String, Object> findParams);
	
}
