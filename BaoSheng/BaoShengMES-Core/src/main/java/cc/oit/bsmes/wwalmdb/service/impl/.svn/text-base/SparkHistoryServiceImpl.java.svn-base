package cc.oit.bsmes.wwalmdb.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.interfaceWWIs.dao.SparkHistoryDAO;
import cc.oit.bsmes.interfaceWWIs.model.SparkHistory;
import cc.oit.bsmes.wwalmdb.service.SparkHistoryService;

@Service
public class SparkHistoryServiceImpl
		implements SparkHistoryService {

 

	@Resource
	private SparkHistoryDAO sparkHistoryDAO;
	
 

	@Override
	public List<SparkHistory> findByEventStamp(Date lastExecuteDate,Integer batchSize) {
        Map<String,Object> findParams = new HashMap<String, Object>(); 
        findParams.put("lastExecuteDate",lastExecuteDate);          
        findParams.put("batchSize",batchSize);
		return sparkHistoryDAO.findByEventStamp(findParams);
	}
  
}
