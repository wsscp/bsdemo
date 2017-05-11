package cc.oit.bsmes.interfacePLM.service.impl;

import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;
import cc.oit.bsmes.eve.service.LastExecuteTimeRecordService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfacePLM.dao.MachDAO;
import cc.oit.bsmes.interfacePLM.model.Mach;
import cc.oit.bsmes.interfacePLM.service.MachService;
import cc.oit.bsmes.job.tasks.EquipUpdateTask;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JinHy on 2014/9/26 0026.
 */
@Service
public class MachServiceImpl extends BaseServiceImpl<Mach> implements MachService{

    @Resource private MachDAO machDAO;
    @Resource private EquipInfoService equipInfoService;
    @Resource private LastExecuteTimeRecordService lastExecuteTimeRecordService;

    @Override
    public void syncMachData() {
        LastExecuteTimeRecord letRecord = lastExecuteTimeRecordService.getOne(InterfaceDataType.MACH);
        Map<String,Date> findParams = new HashMap<String, Date>();
        findParams.put("lastDate",letRecord.getLastExecuteTime());
        List<Mach> list = machDAO.lastUpdateData(findParams);
        Date lastExecuteTime = null;

        for(Mach mach:list){
            EquipInfo equipInfo = equipInfoService.findForDataUpdate(mach.getNo(), EquipType.MAIN_EQUIPMENT.name());
            if(equipInfo == null){
                equipInfo = new EquipInfo();
                equipInfo.setCode(mach.getNo());
                equipInfo.setName(mach.getName());
                equipInfo.setEname(mach.getEname());
                equipInfo.setModel(mach.getModel());
                equipInfo.setSpecs(mach.getSpecs());
                equipInfo.setFactory(mach.getFactory());
                equipInfo.setCenter(mach.getCenter());
                equipInfo.setUseprices(mach.getUseprices());
                equipInfo.setType(EquipType.MAIN_EQUIPMENT);
                equipInfo.setStatus(EquipStatus.CLOSED);
                equipInfo.setOrgCode(SessionUtils.getUser().getOrgCode());
                equipInfo.setCreateUserCode(EquipUpdateTask.SYNC_FROM);
                equipInfo.setModifyUserCode(EquipUpdateTask.SYNC_FROM);
                equipInfoService.insert(equipInfo);
            }else{
                equipInfo.setCode(mach.getNo());
                equipInfo.setName(mach.getName());
                equipInfo.setEname(mach.getEname());
                equipInfo.setModel(mach.getModel());
                equipInfo.setSpecs(mach.getSpecs());
                equipInfo.setFactory(mach.getFactory());
                equipInfo.setCenter(mach.getCenter());
                equipInfo.setUseprices(mach.getUseprices());
                equipInfo.setModifyUserCode(EquipUpdateTask.SYNC_FROM);
                equipInfoService.update(equipInfo);
            }
            
            if(lastExecuteTime==null)
            {
            	lastExecuteTime=mach.getCtime();
            }
            if(mach.getCtime().after(lastExecuteTime))
            {
            	lastExecuteTime=mach.getCtime();
            }
            if(mach.getMtime().after(lastExecuteTime))
            {
            	lastExecuteTime=mach.getMtime();
            } 
             
        }
        letRecord.setLastExecuteTime(lastExecuteTime);
        lastExecuteTimeRecordService.saveRecord(letRecord);
    }
}
