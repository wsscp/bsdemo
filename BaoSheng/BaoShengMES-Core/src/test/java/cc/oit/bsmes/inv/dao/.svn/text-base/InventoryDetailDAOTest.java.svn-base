package cc.oit.bsmes.inv.dao;

import cc.oit.bsmes.inv.model.InventoryDetail;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class InventoryDetailDAOTest extends BaseTest{

	@Resource
	private InventoryDetailDAO inventoryDetailDAO;
	
	@Test
	public void test() {
		List<InventoryDetail> list = inventoryDetailDAO.findByMatCodeAndLen("a", 10.0);
		System.out.println(list.size());
	}

}
