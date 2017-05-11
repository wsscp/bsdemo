package cc.oit.bsmes.pla.service;


import cc.oit.bsmes.junit.BaseTest;
import com.alibaba.fastjson.JSONArray;
import org.junit.Test;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

public class CustomerOrderItemServiceTest extends BaseTest {

	@Resource
	private CustomerOrderItemService customerOrderItemService;
	
	@Test
	public void test() {
		
		try {
			JSONArray array = customerOrderItemService.craftProcessHandle("P001");
			logger.info(array.toString());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
