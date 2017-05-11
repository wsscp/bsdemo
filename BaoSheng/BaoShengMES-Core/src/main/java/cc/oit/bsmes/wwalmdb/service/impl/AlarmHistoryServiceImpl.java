package cc.oit.bsmes.wwalmdb.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.model.EquipMESWWMapping;
import cc.oit.bsmes.bas.service.EquipMESWWMappingService;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.wwalmdb.dao.AlarmHistoryDAO;
import cc.oit.bsmes.wwalmdb.model.AlarmHistory;
import cc.oit.bsmes.wwalmdb.service.AlarmHistoryService;

@Service
public class AlarmHistoryServiceImpl implements AlarmHistoryService {

	private static String ALARM_LO = "Lo"; // 高报
	private static String ALARM_HI = "Hi"; // 低报
	private static String ALARM_NAN = "NaN"; // 无效变量值

	@Resource
	private AlarmHistoryDAO alarmHistoryDAO;

	@Resource
	private EquipMESWWMappingService equipMESWWMappingService;

	@Override
	public List<AlarmHistory> findByEventStamp(Date lastExecuteDate,
			Integer millisec, Integer batchSize) {
		Map<String, Object> findParams = new HashMap<String, Object>();
		if (lastExecuteDate != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(lastExecuteDate);
			calendar.add(Calendar.HOUR, -8);
			findParams.put("lastExecuteDate", calendar.getTime());
		}
		findParams.put("millisec", millisec);
		findParams.put("batchSize", batchSize);
		return alarmHistoryDAO.findByEventStamp(findParams);
	}

	public int count(AlarmHistory findParams) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("alarmHistory", findParams);
		return alarmHistoryDAO.count(params);
	}

	public List<AlarmHistory> find(AlarmHistory findParams, Integer start,
			Integer limit, List<Sort> sortList) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("limit", limit);
		params.put("alarmHistory", findParams);
		List<AlarmHistory> list = alarmHistoryDAO.find(params);
		if (null == list) {
			return new ArrayList<AlarmHistory>();
		}
		String tagName;
		String[] tag;
		String type;
		EquipMESWWMapping equipMESWWMapping;
		List<EquipMESWWMapping> equipMESWWMappingArray;
		List<AlarmHistory> reList = new ArrayList<AlarmHistory>();
		for (AlarmHistory alarmHistory : list) {
			tagName = alarmHistory.getTagName();
			if (StringUtils.isEmpty(tagName)) {
				continue;
			}
			tag = tagName.split("\\.");
			if (tag.length != 3) {
				continue;
			}

			type = alarmHistory.getType();
			if (!StringUtils.isEmpty(type)) {
				type = type.trim();
				if (ALARM_LO.equals(type)) {
					type = "低报";
				} else if (ALARM_HI.equals(type)) {
					type = "高报";
				}
				if (ALARM_NAN.equals(alarmHistory.getValue())) {
					alarmHistory.setValue(null);
				}
			}

			equipMESWWMapping = new EquipMESWWMapping();
			equipMESWWMapping.setParmCode(tag[1]);
//			equipMESWWMapping.setAcEquipCode(tag[0]);
			equipMESWWMappingArray = equipMESWWMappingService
					.findByObj(equipMESWWMapping);
			if (null != equipMESWWMappingArray
					&& equipMESWWMappingArray.size() > 0) {
				equipMESWWMapping = equipMESWWMappingArray.get(0);
			}
			
			alarmHistory.setEquipCode(equipMESWWMapping.getEquipCode());
			alarmHistory.setTagNameDec(equipMESWWMapping.getParmName());
			alarmHistory.setType(type);
			reList.add(alarmHistory);

		}
		return reList;
	}

}
