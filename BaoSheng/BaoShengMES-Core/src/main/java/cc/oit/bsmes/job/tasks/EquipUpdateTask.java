package cc.oit.bsmes.job.tasks;

import cc.oit.bsmes.interfacePLM.service.*;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 同步更新生产线、设备、工装、生产线与设备及设备与工装关系 的数据
 * Created by JinHy on 2014/10/8 0008.
 */
@Service
public class EquipUpdateTask extends BaseSimpleTask {

    public static final String SYNC_FROM = "PLM";
    @Resource
    private MachService machService;
    @Resource
    private EquipService equipService;
    @Resource
    private ScxService scxService;
    @Resource
    private MachObjofService machObjofService;
    @Resource
    private ScxObjofService scxObjofService;

    @Override
    @Transactional(rollbackFor = { Exception.class }, readOnly = false)
    public void process(JobParams parms) {
        /**
         * 生产线数据同步
         */
        scxService.syncScxData();

        /**
         * 设备数据同步
         */
        machService.syncMachData();
        /**
         * 工装数据同步
         */
        equipService.syncEquipData();
        /**
         * 设备生产线关系t同步
         */
        scxObjofService.syncScxMachData();
        /**
         * 设备与工装关系同步
         */
        machObjofService.syncMachEquipData();
    }
}
