package cc.oit.bsmes.pla.dao;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;

import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.model.OrderOA;
import cc.oit.bsmes.pla.model.ProductSOP;
import cc.oit.bsmes.pla.service.OrderOAService;

public class ProductSOPTest extends BaseTest {
	@Resource
	private ProductSOPDAO productSOPDAO;
	@Resource
	private OrderOAService orderOAService;
	
	@Test
	public void test(){
		ProductSOP pro=new ProductSOP();
		pro.setId(UUID.randomUUID().toString());
		pro.setProductCode("G00002");
		pro.setEarliestFinishDate(new Date());
		pro.setLastFinishDate(new Date());
		pro.setCreateTime(new Date());
		pro.setOrgCode("生产部");
		productSOPDAO.insert(pro);
		pro.setProductCode("G00001");
		productSOPDAO.insert(pro);
		
	}
	@Test
	public void ss(){
		List<OrderOA> list=orderOAService.getSubListByOrderItemId("1");
		System.out.println(list.size());
	}
}
