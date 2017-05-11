package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.MesClient;
import cc.oit.bsmes.bas.model.MesClientManEqip;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

public class MesClientDAOTest extends BaseTest{

	@Resource
	private MesClientDAO mesClientDAO;
	@Resource
	private MesClientManEqipDAO mesClientManEqipDAO;
	@Test
	public void test() {
		MesClient mc = new MesClient();
		mc.setClientIp("192.168.1.106");
		mc.setClientMac("ac7c:2073:5912:b7d0");
		mc.setClientName("终端1");
		mc.setOrgCode("01");
		mesClientDAO.insert(mc);
	}
	
	@Test
	@Rollback(false)
	public void insert() {
		MesClient mc = new MesClient();
		mc.setClientIp("192.168.1.106");
		mc.setClientMac("ac7c:2073:5912:b7d0");
		mc.setClientName("终端1");
		mc.setOrgCode("01");
		mesClientDAO.insert(mc);
		
		MesClientManEqip eqip = new MesClientManEqip();
		eqip.setMesClientId(mc.getId());
		eqip.setEqipId("b23591c4-8f2c-42ec-bef2-99ff3f9f44e9");
		mesClientManEqipDAO.insert(eqip);
	}
}
