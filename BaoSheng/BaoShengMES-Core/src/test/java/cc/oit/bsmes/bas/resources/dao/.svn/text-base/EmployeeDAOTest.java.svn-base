package cc.oit.bsmes.bas.resources.dao;

import cc.oit.bsmes.bas.dao.ResourcesDAO;
import cc.oit.bsmes.bas.model.Resources;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.junit.BaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.util.List;

public class EmployeeDAOTest extends BaseTest {
	
	@javax.annotation.Resource
    private ResourcesDAO resourceDAO;

	@Test
	public void testGetAll() {
		System.out.println(resourceDAO.getAll().size());
	}
	
	@Test
	public void testInsert() {
		for(int i=0;i<10;i++){
			Resources res=new Resources();
			res.setName(RandomStringUtils.randomNumeric(10));
			res.setDescription(RandomStringUtils.randomNumeric(10));
			res.setParentId(WebConstants.ROOT_ID);
			res.setType(RandomStringUtils.randomNumeric(10));
			res.setUri("/res/"+i);
			resourceDAO.insert(res);
		}
	}
	@Test
	public void testGetByParentId(){
		List<Resources> list= resourceDAO.getByParentId(WebConstants.ROOT_ID);
		System.out.println(list.size());
	}
}
