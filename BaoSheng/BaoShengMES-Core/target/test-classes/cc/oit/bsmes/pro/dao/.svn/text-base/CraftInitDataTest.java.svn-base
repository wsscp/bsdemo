package cc.oit.bsmes.pro.dao;

import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.constants.UnitType;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.model.ProductCrafts;
import cc.oit.bsmes.pro.model.ProductProcess;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Calendar;

public class CraftInitDataTest extends BaseTest{

	@Resource
	private ProductCraftsDAO productCraftsDAO; 
	
	@Resource
	private ProductProcessDAO productProcessDAO;
	@Resource
	private ProcessInOutDAO  processInOutDAO;
	
	@Test
	public void test() {
		Calendar cal = Calendar.getInstance();
		ProductCrafts craft = new ProductCrafts();
		craft.setCraftsCode("0101");
		craft.setCraftsName("A-01");
		
		cal.set(2013, 11, 22);
		craft.setStartDate(cal.getTime());

		cal.set(2014, 11, 22);
		craft.setEndDate(cal.getTime());
		
		craft.setCraftsVersion(1);
		craft.setProductCode("A-01-A");
		productCraftsDAO.insert(craft);
		
		ProductProcess process = new ProductProcess();
		process.setProcessCode("0101-01");
		process.setSeq(1);
		process.setProductCraftsId(craft.getId());
		process.setProcessTime(1d);
		process.setSetUpTime(1800);
		process.setShutDownTime(1800);
		process.setNextProcessId("-1");
		productProcessDAO.insert(process);
		
		ProcessInOut inout = new ProcessInOut();
		inout.setProductProcessId(process.getId());
		inout.setMatCode("0101-01-IN-A");
		inout.setInOrOut(InOrOut.IN);
		inout.setQuantity(10.0);
		inout.setQuantityFormula("待设置");
		inout.setUnit(UnitType.KM);
		inout.setUseMethod("Teset");
		processInOutDAO.insert(inout);
		
		inout = new ProcessInOut();
		inout.setProductProcessId(process.getId());
		inout.setMatCode("0101-01-IN-B");
		inout.setInOrOut(InOrOut.IN);
		inout.setQuantity(10.0);
		inout.setQuantityFormula("待设置");
		inout.setUnit(UnitType.KG);
		inout.setUseMethod("Teset");
		processInOutDAO.insert(inout);
		
		inout = new ProcessInOut();
		inout.setProductProcessId(process.getId());
		inout.setMatCode("0101-01-OUT-C");
		inout.setInOrOut(InOrOut.OUT);
		inout.setQuantity(10.0);
		inout.setQuantityFormula("待设置");
		inout.setUnit(UnitType.KM);
		inout.setUseMethod("Teset");
		processInOutDAO.insert(inout);
		
		process = new ProductProcess();
		process.setProcessCode("0101-02");
		process.setSeq(2);
		process.setProductCraftsId(craft.getId());
		process.setProcessTime(2d);
		process.setSetUpTime(1800);
		process.setShutDownTime(1800);
		process.setNextProcessId("-1");
		productProcessDAO.insert(process);
		
		
		inout = new ProcessInOut();
		inout.setProductProcessId(process.getId());
		inout.setMatCode("0101-01-OUT-C");
		inout.setInOrOut(InOrOut.IN);
		inout.setQuantity(10.0);
		inout.setQuantityFormula("待设置");
		inout.setUnit(UnitType.KM);
		inout.setUseMethod("Teset");
		processInOutDAO.insert(inout);
		
		inout = new ProcessInOut();
		inout.setProductProcessId(process.getId());
		inout.setMatCode("0101-02-IN-A");
		inout.setInOrOut(InOrOut.IN);
		inout.setQuantity(10.0);
		inout.setQuantityFormula("待设置");
		inout.setUnit(UnitType.KG);
		inout.setUseMethod("Teset");
		processInOutDAO.insert(inout);
		
		inout = new ProcessInOut();
		inout.setProductProcessId(process.getId());
		inout.setMatCode("0101-02-OUT-B");
		inout.setInOrOut(InOrOut.OUT);
		inout.setQuantity(10.0);
		inout.setQuantityFormula("待设置");
		inout.setUnit(UnitType.KM);
		inout.setUseMethod("Teset");
		processInOutDAO.insert(inout);
		
		process = new ProductProcess();
		process.setProcessCode("0101-03");
		process.setSeq(3);
		process.setProductCraftsId(craft.getId());
		process.setProcessTime(1.5);
		process.setSetUpTime(3600);
		process.setShutDownTime(3600);
		process.setNextProcessId("-1");
		productProcessDAO.insert(process);
		
		inout = new ProcessInOut();
		inout.setProductProcessId(process.getId());
		inout.setMatCode("0101-02-OUT-B");
		inout.setInOrOut(InOrOut.IN);
		inout.setQuantity(10.0);
		inout.setQuantityFormula("待设置");
		inout.setUnit(UnitType.KM);
		inout.setUseMethod("Teset");
		processInOutDAO.insert(inout);
		
		inout = new ProcessInOut();
		inout.setProductProcessId(process.getId());
		inout.setMatCode("0101-03-IN-A");
		inout.setInOrOut(InOrOut.IN);
		inout.setQuantity(10.0);
		inout.setQuantityFormula("待设置");
		inout.setUnit(UnitType.KG);
		inout.setUseMethod("Teset");
		processInOutDAO.insert(inout);
		
		inout = new ProcessInOut();
		inout.setProductProcessId(process.getId());
		inout.setMatCode("0101-03-OUT-B");
		inout.setInOrOut(InOrOut.OUT);
		inout.setQuantity(10.0);
		inout.setQuantityFormula("待设置");
		inout.setUnit(UnitType.KM);
		inout.setUseMethod("Teset");
		processInOutDAO.insert(inout);
	}

}
