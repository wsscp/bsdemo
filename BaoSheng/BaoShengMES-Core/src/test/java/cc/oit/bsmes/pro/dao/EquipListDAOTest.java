package cc.oit.bsmes.pro.dao;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import org.junit.Test;

import javax.annotation.Resource;

public class EquipListDAOTest extends BaseTest{

	@Resource
	private EquipListDAO equipListDAO;

	@Resource
	private ProcessReceiptDAO processReceiptDAO;
	@Test
	public void testInsert() {
	/*	EquipList equip = new EquipList();
		equip.setProcessId("1");
		equip.setEquipCode("LINE-CL-0002");
		equipListDAO.insert(equip);*/
		
		ProcessReceipt t = new ProcessReceipt();
		t.setEqipListId("691b9c54-84f9-42ed-9671-03b8ebb10432");
		t.setReceiptCode("temperature");
		t.setReceiptName("温度");
		t.setSubReceiptName("test");
		t.setReceiptTargetValue("50");
		t.setReceiptMaxValue("60");
		t.setReceiptMinValue("40");
		processReceiptDAO.insert(t);
	}
	
	@Test
	public void test(){
        EquipList params = new EquipList();
        params.setEquipCode("fe");
        equipListDAO.query(null, null);
	}
}
