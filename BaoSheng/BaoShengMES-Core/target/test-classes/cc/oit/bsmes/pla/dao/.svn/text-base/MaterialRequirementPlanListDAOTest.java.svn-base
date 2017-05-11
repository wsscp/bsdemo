package cc.oit.bsmes.pla.dao;

import cc.oit.bsmes.common.constants.MaterialStatus;
import cc.oit.bsmes.common.constants.UnitType;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.MaterialRequirementPlan;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MaterialRequirementPlanListDAOTest extends BaseTest{
	@Resource
	private MaterialRequirementPlanDAO materialRequirementPlanDAO;
	@Test
	public void test(){

		Calendar c = Calendar.getInstance();
		c.getTime();
		MaterialRequirementPlan mat=new MaterialRequirementPlan();
		for(int i=0;i<10;i++){
			mat.setId(UUID.randomUUID().toString());
			mat.setWorkOrderId("f5767d8f-0936-4771-ae1f-fcb5633ada13");
			mat.setMatCode("ss-1-"+i);
			mat.setQuantity(10.2+0.5+i);
			mat.setPlanDate(c.getTime());
			mat.setStatus(MaterialStatus.UNAUDITED);
			mat.setProcessCode("ss-1-"+i);
			mat.setOrgCode("1390208494617");
			mat.setUnit(UnitType.KM);
			materialRequirementPlanDAO.insert(mat);
			mat.setCreateUserCode("zs");
			mat.setCreateTime(new Date());
			mat.setModifyUserCode("zs");
			mat.setModifyTime(new Date());
		}
		
		
	}
}
