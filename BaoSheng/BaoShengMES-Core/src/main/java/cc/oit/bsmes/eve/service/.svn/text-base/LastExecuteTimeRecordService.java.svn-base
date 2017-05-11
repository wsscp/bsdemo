package cc.oit.bsmes.eve.service;

import cc.oit.bsmes.common.constants.InterfaceDataType;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.eve.model.LastExecuteTimeRecord;

public interface LastExecuteTimeRecordService extends
		BaseService<LastExecuteTimeRecord> {
	
	public LastExecuteTimeRecord getOne(InterfaceDataType type);
	
	public LastExecuteTimeRecord getPrcvLastExecuteDate(String type);

    void saveRecord(LastExecuteTimeRecord record);

}
