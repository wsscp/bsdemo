package cc.oit.bsmes.wip.dao;

import cc.oit.bsmes.common.constants.UserOnOffType;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.wip.model.OnoffRecord;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

public class OnoffRecordDAOTest extends BaseTest{

	@Resource
	private OnoffRecordDAO onoffRecordDAO;
	
	@Test
	public void test(){
		OnoffRecord onoff = new OnoffRecord();
		onoff.setClientName("ac7c:2073:5912:b7d0");
		onoff.setExceptionType(UserOnOffType.ON_WORK.name());
		onoff.setUserCode("3285280657");
		onoff.setOrgCode("9426873737");
		onoff.setOnTime(new Date());
		onoffRecordDAO.insert(onoff);
	}

}
