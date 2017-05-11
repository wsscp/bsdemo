package cc.oit.bsmes.pla.service;

import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

public class CustomerOrderItemProDecServiceTest extends BaseTest{
	
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	
	@Resource
	private ResourceCache resourceCache;

	@Test
	public void testAnalysisOrderToProcess() {
		customerOrderItemProDecService.analysisOrderToProcess(resourceCache, "8770318921");
	}

}
