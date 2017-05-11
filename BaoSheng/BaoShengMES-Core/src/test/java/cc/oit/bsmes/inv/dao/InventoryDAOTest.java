package cc.oit.bsmes.inv.dao;

import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class InventoryDAOTest extends  BaseTest{

	@Resource
	public InventoryDAO inventoryDAO;
	@Test
	public void testInsert() {
		Inventory inventory = new Inventory();
		inventory.setWarehouseId("1");
		inventory.setLocationId("1");
		inventory.setMaterialCode("H0002");
		inventory.setMaterialName("物料1");
		inventory.setBarCode("dfafd");
		inventory.setQuantity(10000.0);
		inventory.setLockedQuantity(1000.0);
		inventoryDAO.insert(inventory);
	}

	
	@Test
	public void  testFindByMatCodeOrLocationName(){
		List<Inventory> list = inventoryDAO.findByMatCodeOrLocationName("", "%cs%");
		//assertEquals(list.size(), 1);
	}
}
