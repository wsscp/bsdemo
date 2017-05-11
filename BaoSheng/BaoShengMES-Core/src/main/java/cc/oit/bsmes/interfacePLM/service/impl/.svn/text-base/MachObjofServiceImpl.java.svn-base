package cc.oit.bsmes.interfacePLM.service.impl;

import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;
import cc.oit.bsmes.eve.service.LastExecuteTimeRecordService;
import cc.oit.bsmes.fac.model.ProductEquip;
import cc.oit.bsmes.fac.service.ProductEquipService;
import cc.oit.bsmes.interfacePLM.dao.MachObjofDAO;
import cc.oit.bsmes.interfacePLM.model.MachObjof;
import cc.oit.bsmes.interfacePLM.service.MachObjofService;
import cc.oit.bsmes.job.tasks.EquipUpdateTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JinHy on 2014/9/28 0028.
 */
@Service
public class MachObjofServiceImpl extends BaseServiceImpl<MachObjof> implements MachObjofService{

    @Resource private MachObjofDAO machObjofDAO;
    @Resource private LastExecuteTimeRecordService lastExecuteTimeRecordService;
    @Resource private ProductEquipService productEquipService;

    @Override
    public void syncMachEquipData() {
        LastExecuteTimeRecord letRecord = lastExecuteTimeRecordService.getOne(InterfaceDataType.MACH_OBJOF);
        Map<String,Date> findParams = new HashMap<String, Date>();
        findParams.put("lastDate",letRecord.getLastExecuteTime());
        List<MachObjof> list = machObjofDAO.lastUpdateData(findParams);
        Date lastExecuteTime = null;
        ProductEquip productEquip = null;
        for(MachObjof machObjof:list){
            productEquip = productEquipService.getByLineAndProId(machObjof.getItemid1(),machObjof.getItemid2());
            if(productEquip == null){
                productEquip = new ProductEquip();
                productEquip.setProductLineId(machObjof.getItemid1());
                productEquip.setEquipId(machObjof.getItemid2());
                productEquip.setIsMain(false);
                productEquip.setCreateUserCode(EquipUpdateTask.SYNC_FROM);
                productEquip.setModifyUserCode(EquipUpdateTask.SYNC_FROM);
                productEquipService.insert(productEquip);
            }

            if(lastExecuteTime==null)
            {
            	lastExecuteTime=machObjof.getCtime();
            }
            if(machObjof.getCtime().after(lastExecuteTime))
            {
            	lastExecuteTime=machObjof.getCtime();
            }
            if(machObjof.getMtime().after(lastExecuteTime))
            {
            	lastExecuteTime=machObjof.getMtime();
            } 
        }

        letRecord.setLastExecuteTime(lastExecuteTime);
        lastExecuteTimeRecordService.saveRecord(letRecord);
    }
}
