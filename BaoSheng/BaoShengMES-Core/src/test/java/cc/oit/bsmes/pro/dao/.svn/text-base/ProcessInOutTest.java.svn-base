package cc.oit.bsmes.pro.dao;


import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.service.ProductCraftsService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class ProcessInOutTest extends BaseTest {

	@Resource
	private ProcessInOutDAO processInOutDAO;
    @Resource
    private ProductCraftsService productCraftsService;
	@Test
	public void test() {
        List<ProcessInOut> list = StaticDataCache.getByProcessId("0c401a32-a831-497b-8e45-230fe51d471e");
        for(ProcessInOut inOut:list){
            logger.info("{}:{}:{}",inOut.getMatCode(),inOut.getInOrOut().toString(),list.size());
        }
	}
}
