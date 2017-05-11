package cc.oit.bsmes.interfacePLM.service.impl;

import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;
import cc.oit.bsmes.eve.service.LastExecuteTimeRecordService;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfacePLM.dao.EquipDAO;
import cc.oit.bsmes.interfacePLM.model.Equip;
import cc.oit.bsmes.interfacePLM.service.EquipService;
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
public class EquipServiceImpl extends BaseServiceImpl<Equip> implements EquipService{

    @Resource private EquipDAO equipDAO;
    @Resource private EquipInfoService equipInfoService;
    @Resource private LastExecuteTimeRecordService lastExecuteTimeRecordService;

    @Override
    public void syncEquipData() {
        LastExecuteTimeRecord letRecord = lastExecuteTimeRecordService.getOne(InterfaceDataType.EQUIP);
        Map<String,Date> findParams = new HashMap<String, Date>();
        findParams.put("lastDate",letRecord.getLastExecuteTime());
        List<Equip> list = equipDAO.lastUpdateData(findParams);
        Date lastExecuteTime = null;

        for(Equip equip:list){
            EquipInfo equipInfo = equipInfoService.findForDataUpdate(equip.getNo(), EquipType.TOOLS.name());
            if(equipInfo == null){
                equipInfo = new EquipInfo();
                equipInfo.setId(equip.getId());
                equipInfo.setCode(equip.getNo());
                equipInfo.setName(equip.getName());
                equipInfo.setEname(equip.getEname());
                equipInfo.setSpecs(equip.getSpecs());
                equipInfo.setBnum(equip.getBnum());
                equipInfo.setType(EquipType.TOOLS);
                equipInfo.setStatus(EquipStatus.CLOSED);
                equipInfo.setCreateUserCode(EquipUpdateTask.SYNC_FROM);
                equipInfo.setModifyUserCode(EquipUpdateTask.SYNC_FROM);
                equipInfoService.insert(equipInfo);
            }else{
                equipInfo.setCode(equip.getNo());
                equipInfo.setName(equip.getName());
                equipInfo.setEname(equip.getEname());
                equipInfo.setSpecs(equip.getSpecs());
                equipInfo.setBnum(equip.getBnum());
                equipInfo.setType(EquipType.TOOLS);
                equipInfo.setModifyUserCode(EquipUpdateTask.SYNC_FROM);
                equipInfoService.update(equipInfo);
            }

            if(lastExecuteTime==null)
            {
            	lastExecuteTime=equip.getCtime();
            }
            if(equip.getCtime().after(lastExecuteTime))
            {
            	lastExecuteTime=equip.getCtime();
            }
            if(equip.getMtime().after(lastExecuteTime))
            {
            	lastExecuteTime=equip.getMtime();
            } 
        }

        letRecord.setLastExecuteTime(lastExecuteTime);
        lastExecuteTimeRecordService.saveRecord(letRecord);
    }
}
