package cc.oit.bsmes.inv.service.impl;

import cc.oit.bsmes.common.constants.InventoryLogType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.inv.dao.InventoryLogDAO;
import cc.oit.bsmes.inv.model.InventoryLog;
import cc.oit.bsmes.inv.service.InventoryLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InventoryLogServiceImpl extends BaseServiceImpl<InventoryLog> implements
		InventoryLogService {
	
	@Resource
	private InventoryLogDAO inventoryLogDAO;
	
	@Override
	public List<InventoryLog> getByInventoryId(String inventoryId) {
		return inventoryLogDAO.getByInventoryId(inventoryId);
	}

    @Override
    public void log(String inventoryId, Double quantity, InventoryLogType type) {
        InventoryLog log = new InventoryLog();
        log.setQuantity(quantity);
        log.setInventoryId(inventoryId);
        log.setType(type);
        log.setOrgCode(SessionUtils.getUser().getOrgCode());
        inventoryLogDAO.insert(log);
    }
}
