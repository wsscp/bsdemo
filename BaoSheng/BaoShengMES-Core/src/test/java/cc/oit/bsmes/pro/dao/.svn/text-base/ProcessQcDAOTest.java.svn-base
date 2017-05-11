package cc.oit.bsmes.pro.dao;

import cc.oit.bsmes.common.constants.InOrOut;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.model.ProcessInOut;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

public class ProcessQcDAOTest extends BaseTest{
	
	@Resource
	private ProcessQcDAO processQcDAO;

	@Test
	public void test() {
        List<ProcessInOut> inOuts = StaticDataCache.getByProcessId("54d82257-966a-4f6e-90c8-8369d12837c4");
        Iterator<ProcessInOut> it = inOuts.iterator();
        while(it.hasNext()){
            if(it.next().getInOrOut() == InOrOut.OUT){
                it.remove();
            }
        }
        for(ProcessInOut inOut:inOuts){
            logger.info(inOut.getMatCode());
        }


	}

}
