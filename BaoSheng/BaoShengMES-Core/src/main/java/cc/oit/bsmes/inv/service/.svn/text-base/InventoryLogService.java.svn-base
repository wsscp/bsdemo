package cc.oit.bsmes.inv.service;

import cc.oit.bsmes.common.constants.InventoryLogType;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.inv.model.InventoryLog;

import java.util.List;

public interface InventoryLogService extends BaseService<InventoryLog> {
	
	public List<InventoryLog> getByInventoryId(String inventoryId);

    public void log(String inventoryId,Double quantity,InventoryLogType type);

}
