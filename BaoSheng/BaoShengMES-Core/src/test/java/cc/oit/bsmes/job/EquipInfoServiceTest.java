package cc.oit.bsmes.job;

import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by jijy on 2014/11/5.
 */
public class EquipInfoServiceTest extends BaseTest {

    @Resource
    private EquipInfoService equipInfoService;

    @Test
    public void testCreateMaintainEvent() {
        equipInfoService.createMaintainEvent("1");
    }

}
