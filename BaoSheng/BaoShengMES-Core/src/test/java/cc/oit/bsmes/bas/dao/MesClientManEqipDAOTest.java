package cc.oit.bsmes.bas.dao;

import cc.oit.bsmes.bas.model.MesClientManEqip;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.wip.dto.MesClientEqipInfo;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class MesClientManEqipDAOTest extends BaseTest{

	@Resource
	private MesClientManEqipDAO mesClientManEqipDAO;
	
	@Test
	public void test() {
		MesClientManEqip eqip = new MesClientManEqip();
		eqip.setMesClientId("123b4b55-6755-4fe0-9790-6e838d4c6091");
		eqip.setEqipId("df75a2ca-50fc-4b74-a601-ebdb3036e032");
		mesClientManEqipDAO.insert(eqip);
	}
	
	@Test
	public void findByMesClientMacTest(){
		String ip = "192.168.1.106";
		String mac = "ac7c:2073:5912:b7d0";
		List<MesClientEqipInfo> eqips = mesClientManEqipDAO.getInfoByMesClientMac(mac, ip);
	}
}
