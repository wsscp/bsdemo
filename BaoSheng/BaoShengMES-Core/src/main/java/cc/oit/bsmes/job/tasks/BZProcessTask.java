package cc.oit.bsmes.job.tasks;

import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;
import cc.oit.bsmes.eve.service.LastExecuteTimeRecordService;
import cc.oit.bsmes.interfacePLM.model.BZProcess;
import cc.oit.bsmes.interfacePLM.service.BZProcessService;
import cc.oit.bsmes.job.base.parent.BaseSimpleTask;
import cc.oit.bsmes.job.base.vo.JobParams;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.service.ProcessInformationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by JinHy on 2014/9/26 0026.
 */
@Service
public class BZProcessTask extends BaseSimpleTask {

    private static final String SYNC_FROM = "PLM";
    @Resource
    private BZProcessService bzProcessService;
    @Resource
    private ProcessInformationService processInformationService;
    @Resource
    private LastExecuteTimeRecordService lastExecuteTimeRecordService;

    @Override
    @Transactional(rollbackFor = { Exception.class }, readOnly = false)
    public void process(JobParams parms) {
        LastExecuteTimeRecord letRecord = lastExecuteTimeRecordService.getOne(InterfaceDataType.BZPROCESS);
        if (letRecord == null) {
            letRecord = new LastExecuteTimeRecord();
            letRecord.setType(InterfaceDataType.BZPROCESS.name());
        }
        Date lastDate = letRecord == null ? null : letRecord.getLastExecuteTime();
        Map<String, Date> findParams = new HashMap<String, Date>();
        findParams.put("lastDate", lastDate);
        List<BZProcess> processes = bzProcessService.lastUpdateData(findParams);


        Date lastExecuteTime = null;

        for (BZProcess process : processes) {
            ProcessInformation processInformation = processInformationService.getByCode(process.getNo());
            if (processInformation == null) {
                processInformation = new ProcessInformation();
                processInformation.setCode(process.getNo());
                processInformation.setName(process.getName());
                processInformation.setSection(process.getPtype());
                processInformation.setCreateUserCode(SYNC_FROM);
                processInformation.setModifyUserCode(SYNC_FROM);
                processInformationService.insert(processInformation);
            } else {
                processInformation.setName(process.getName());
                processInformation.setSection(process.getPtype());
                processInformation.setModifyUserCode(SYNC_FROM);
                processInformationService.update(processInformation);
            }
            if(lastExecuteTime==null)
            {
            	lastExecuteTime=process.getCtime();
            }
            if(process.getCtime().after(lastExecuteTime))
            {
            	lastExecuteTime=process.getCtime();
            }
            if(process.getMtime().after(lastExecuteTime))
            {
            	lastExecuteTime=process.getMtime();
            } 
            
        }

        letRecord.setLastExecuteTime(lastExecuteTime);
        lastExecuteTimeRecordService.saveRecord(letRecord);
    }
}
