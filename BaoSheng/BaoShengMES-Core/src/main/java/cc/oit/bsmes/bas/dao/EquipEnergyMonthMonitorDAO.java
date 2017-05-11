package cc.oit.bsmes.bas.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.bas.model.EquipEnergyMonitor;
import cc.oit.bsmes.bas.model.EquipEnergyMonthMonitor;
import cc.oit.bsmes.common.dao.BaseDAO;


public interface EquipEnergyMonthMonitorDAO extends BaseDAO<EquipEnergyMonthMonitor> {

	List<EquipEnergyMonthMonitor> getEquipEnergyMonthLoad(
			Map<String, Object> findParams);

	void updateMonthPowerData(EquipEnergyMonthMonitor e);

	List<EquipEnergyMonitor> getEnergyMonthQuantity(
			Map<String, Object> findParams);

}
