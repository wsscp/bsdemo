package cc.oit.bsmes.eve.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.eve.dao.LastExecuteTimeRecordDAO;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;
import cc.oit.bsmes.eve.service.LastExecuteTimeRecordService;

@Service
public class LastExecuteTimeRecordServiceImpl extends BaseServiceImpl<LastExecuteTimeRecord>
		implements LastExecuteTimeRecordService {

	@Resource
	private LastExecuteTimeRecordDAO lastExecuteTimeRecordDAO;
	
	@Override
	public LastExecuteTimeRecord getOne(InterfaceDataType typeParm) {		
		LastExecuteTimeRecord findParams=new LastExecuteTimeRecord();
		findParams.setType(typeParm.name());
        LastExecuteTimeRecord letRecord = lastExecuteTimeRecordDAO.getOne(findParams);
        if(letRecord == null){
            letRecord = new LastExecuteTimeRecord();
            letRecord.setType(typeParm.name());
            letRecord.setCreateTime(new Date());
            letRecord.setCreateUserCode("job");
        }
		return letRecord;
	}
	
    @Override
	public LastExecuteTimeRecord getPrcvLastExecuteDate(String type) {
    	LastExecuteTimeRecord findParams=new LastExecuteTimeRecord();
		findParams.setType(type);
        LastExecuteTimeRecord letRecord = lastExecuteTimeRecordDAO.getOne(findParams);
        if(letRecord == null){
            letRecord = new LastExecuteTimeRecord();
            letRecord.setType(type);
            letRecord.setCreateTime(new Date());
            letRecord.setCreateUserCode("job");
        }
		return letRecord;
    }

	@Override
    public void saveRecord(LastExecuteTimeRecord record) {
        if(StringUtils.isBlank(record.getId())){
            insert(record);
        }else{
            update(record);
        }
    }
}
