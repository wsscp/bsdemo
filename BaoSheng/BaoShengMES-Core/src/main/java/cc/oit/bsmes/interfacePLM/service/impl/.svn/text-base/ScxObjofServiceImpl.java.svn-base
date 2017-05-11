package cc.oit.bsmes.interfacePLM.service.impl;

import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;
import cc.oit.bsmes.eve.service.LastExecuteTimeRecordService;
import cc.oit.bsmes.fac.model.ProductEquip;
import cc.oit.bsmes.fac.service.ProductEquipService;
import cc.oit.bsmes.interfacePLM.dao.ScxObjofDAO;
import cc.oit.bsmes.interfacePLM.model.Mach;
import cc.oit.bsmes.interfacePLM.model.ScxObjof;
import cc.oit.bsmes.interfacePLM.service.MachService;
import cc.oit.bsmes.interfacePLM.service.ScxObjofService;
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
public class ScxObjofServiceImpl extends BaseServiceImpl<ScxObjof> implements ScxObjofService{

    @Resource private ScxObjofDAO scxObjofDAO;
    @Resource private LastExecuteTimeRecordService lastExecuteTimeRecordService;
    @Resource private ProductEquipService productEquipService;

    @Resource
    private MachService machService;

    private static final String MAIN_EQUIP_FLAG = "主机";

    @Override
    public void syncScxMachData() {
        LastExecuteTimeRecord letRecord = lastExecuteTimeRecordService.getOne(InterfaceDataType.SCX_OBJOF);
        Map<String,Date> findParams = new HashMap<String, Date>();
        findParams.put("lastDate",letRecord.getLastExecuteTime());
        List<ScxObjof> list = scxObjofDAO.lastUpdateData(findParams);
        Date lastExecuteTime = null;
        for(ScxObjof scxObjof:list){
            ProductEquip productEquip = productEquipService.getByLineAndProId(scxObjof.getItemid1(),scxObjof.getItemid2());
            if(productEquip == null){
                //需要验证scx equip 存在吗
                productEquip = new ProductEquip();

                Mach mach = machService.getById(scxObjof.getItemid2());
                if(mach == null){
                    continue;
                }
                if(MAIN_EQUIP_FLAG.equals(mach.getPtype())){
                    productEquip.setIsMain(true);
                }else{
                    productEquip.setIsMain(false);
                }
                productEquip.setProductLineId(scxObjof.getItemid1());
                productEquip.setEquipId(scxObjof.getItemid2());
                productEquip.setCreateUserCode(EquipUpdateTask.SYNC_FROM);
                productEquip.setModifyUserCode(EquipUpdateTask.SYNC_FROM);
                productEquipService.insert(productEquip);
            }

            if(lastExecuteTime==null)
            {
            	lastExecuteTime=scxObjof.getCtime();
            }
            if(scxObjof.getCtime().after(lastExecuteTime))
            {
            	lastExecuteTime=scxObjof.getCtime();
            }
            if(scxObjof.getMtime().after(lastExecuteTime))
            {
            	lastExecuteTime=scxObjof.getMtime();
            } 
        }

        letRecord.setLastExecuteTime(lastExecuteTime);
        lastExecuteTimeRecordService.saveRecord(letRecord);
    }
}
