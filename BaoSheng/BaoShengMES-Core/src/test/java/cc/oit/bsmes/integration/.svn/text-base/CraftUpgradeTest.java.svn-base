package cc.oit.bsmes.integration;

import cc.oit.bsmes.common.constants.ProcessCode;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProductProcessService;
import jxl.read.biff.BiffException;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * Created by 羽霓 on 2014/5/4.
 */
public class CraftUpgradeTest extends BaseTest {

    @Resource
    private org.springframework.core.io.Resource planData;
    @Resource
    private CustomerOrderItemProDecService customerOrderItemProDecService;
    @Resource
    private ProductProcessService productProcessService;
    @Resource
    private ResourceCache resourceCache;
    private String orgCode = "1";

    @Test
    @Rollback(false)
    public void analysis1() throws BiffException, IOException {

        customerOrderItemProDecService.analysisOrderToProcess(resourceCache, orgCode);
    }

    private ProductProcess insertProcess(ProcessCode processCode, Integer seq, String craftsId, String lastPath) {
        ProductProcess process = new ProductProcess();
        process.setProcessCode(processCode.name());
        process.setProcessName(processCode.name());
        process.setSeq(seq);
        process.setProductCraftsId(craftsId);
        String[] strings = lastPath.split(";");
        process.setNextProcessId(strings[strings.length - 1]);
        process.setSameProductLine(false);
        process.setIsOption(false);
        process.setIsDefaultSkip(false);
        productProcessService.insert(process);
        process.setFullPath(lastPath + process.getId());
        productProcessService.update(process);
        return process;
    }

}
