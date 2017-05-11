package cc.oit.bsmes.inv.dao;

import cc.oit.bsmes.common.constants.InventoryLogType;
import cc.oit.bsmes.inv.model.InventoryLog;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class InventoryLogDAOTest extends BaseTest{

	@Resource
	private InventoryLogDAO inventoryLogDAO;
	
	@Test
	public void testInsert() {
		InventoryLog log = new InventoryLog();
		log.setInventoryId("14e14b49-226c-463c-98c6-a8adc20ab481");
		log.setQuantity(500.0);
		log.setType(InventoryLogType.OUT);
		inventoryLogDAO.insert(log);
		
		log = new InventoryLog();
		log.setInventoryId("14e14b49-226c-463c-98c6-a8adc20ab481");
		log.setQuantity(1000.0);
		log.setType(InventoryLogType.IN);
		inventoryLogDAO.insert(log);
		
		log = new InventoryLog();
		log.setInventoryId("14e14b49-226c-463c-98c6-a8adc20ab481");
		log.setQuantity(3000.0);
		log.setType(InventoryLogType.OUT);
		inventoryLogDAO.insert(log);
		
		log = new InventoryLog();
		log.setInventoryId("14e14b49-226c-463c-98c6-a8adc20ab481");
		log.setQuantity(5000.0);
		log.setType(InventoryLogType.IN);
		inventoryLogDAO.insert(log);
	}
	
	@Test
	public void testFind(){
		InventoryLog findParams =  new InventoryLog();
		findParams.setInventoryId("14e14b49-226c-463c-98c6-a8adc20ab481");
		
	}

}
