package cc.oit.bsmes.job;

import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.job.tasks.EquipStatusProcessTask;
import cc.oit.bsmes.junit.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by JIN on 2015/5/11.
 */

public class EquipStatusProcessTaskTest extends BaseTest{

    @Resource
    private EquipStatusProcessTask equipStatusProcessTask;

    @Test
    public void syncEquipStatus(){
        JobParams params = new JobParams();
        params.setOrgCode("bstl01");
        equipStatusProcessTask.process(params);
    }
}
