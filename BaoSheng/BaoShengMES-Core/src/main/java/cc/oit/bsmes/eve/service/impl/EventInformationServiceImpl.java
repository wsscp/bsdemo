package cc.oit.bsmes.eve.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.model.Role;
import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.EventTypeContent;
import cc.oit.bsmes.common.exception.DataCommitException;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.eve.dao.EventInformationDAO;
import cc.oit.bsmes.eve.dao.EventOwnerDAO;
import cc.oit.bsmes.eve.model.EventInformation;
import cc.oit.bsmes.eve.model.EventOwner;
import cc.oit.bsmes.eve.service.EventInformationService;
import cc.oit.bsmes.eve.service.EventProcesserService;
import cc.oit.bsmes.fac.dao.EquipRepairRecordDAO;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.EquipRepairRecord;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author leiwei
 * @date 2014-2-20 上午10:50:08
 * @since
 * @version
 */
@Service
public class EventInformationServiceImpl extends BaseServiceImpl<EventInformation> implements EventInformationService {
	@Resource
	private EventInformationDAO eventInformationDAO;
	@Resource
	private EventOwnerDAO eventOwnerDAO;
	@Resource
	private EventProcesserService eventProcesserService;
	@Resource
	private EquipRepairRecordDAO equipRepairRecordDAO;

	@Override
	public int getEventStatusByEventProcessId(String eventProcessId) {
		return eventInformationDAO.getEventStatusByEventProcessId(eventProcessId);
	}

	/**
	 * 
	 * @author leiwei
	 * @date 2014-3-6 上午9:45:11
	 * @param param
	 * @param start
	 * @param limit
	 * @param sortList
	 * @return
	 * @see cc.oit.bsmes.eve.service.EventInformationService#getInfo(cc.oit.bsmes.eve.model.EventInformation,
	 *      int, int, java.util.List)
	 */
	@Override
	public List<EventInformation> getInfo(EventInformation param, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		// admin超级管理员能够查看所有的事件
		List<Role> roleList = SessionUtils.getUser().getRoleList();
		for (Role role : roleList) {
			if ("1".equals(role.getId())) {
				param.setUserCode(null);
				break;
			}
		}
		return eventInformationDAO.getInfo(param);
	}

	/**
	 * 
	 * @author leiwei
	 * @date 2014-3-6 上午9:45:22
	 * @param param
	 * @return
	 * @see cc.oit.bsmes.eve.service.EventInformationService#totalCount(cc.oit.bsmes.eve.model.EventInformation)
	 */
	@Override
	public Integer totalCount(EventInformation param) {
		// admin超级管理员能够查看所有的事件
		List<Role> roleList = SessionUtils.getUser().getRoleList();
		for (Role role : roleList) {
			if ("1".equals(role.getId())) {
				param.setUserCode(null);
				break;
			}
		}
		return eventInformationDAO.totalCount(param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EventInformation> findForExport(JSONObject queryFilter) throws InvocationTargetException, IllegalAccessException,
			NoSuchMethodException {
		EventInformation info = JSON.toJavaObject(queryFilter, EventInformation.class);
		return eventInformationDAO.getInfo(info);
	}

	@Override
	public void insertInfo(EventInformation param) {
		param.setId(UUID.randomUUID().toString());
		User user = SessionUtils.getUser();
		String userCode = "job";
		if (user != null) {
			userCode = user.getUserCode();
		}
		param.setCreateUserCode(userCode);
		param.setModifyUserCode(userCode);
		if (StringUtils.isBlank(param.getOrgCode())) {
			param.setOrgCode(SessionUtils.getUser().getOrgCode());
		}
		if (eventInformationDAO.insert(param) != 1) {
			throw new DataCommitException();
		}
		EventInformation eve = eventInformationDAO.getById(param.getId());
		Map<String, String> map = eventProcesserService.getUserCodeByEventProcessId(eve.getEventProcessId());
		if (map == null) {
			return;
		}
		Iterator<String> it = map.values().iterator();
		while (it.hasNext()) {
			String owner = it.next();
			EventOwner t = new EventOwner();
			t.setEventId(param.getId());
			t.setUserCode(owner);
			t.setCreateUserCode(userCode);
			t.setModifyUserCode(userCode);
			t.setEventProcessId(eve.getEventProcessId());
			eventOwnerDAO.insert(t);
		}

	}

	@Override
	public void insertInfo(List<EventInformation> param) {
		for (EventInformation t : param) {
			insertInfo(t);
		}
	}

	@Override
	public void insertInfo(EventInformation param, List<String> userList) {
		String p = param.getMaintainPeople();
		if (userList == null) {
			return;
		}
		param.setId(UUID.randomUUID().toString());
		
		User user = SessionUtils.getUser();
		String userCode = param.getCreateUserCode();
		if (StringUtils.isBlank(userCode)) {
			userCode = (user == null ? "job" : user.getUserCode());
			param.setCreateUserCode(userCode);
		}
		if (StringUtils.isBlank(param.getOrgCode())) {
			param.setOrgCode(user.getOrgCode());
		}
		if (eventInformationDAO.insert(param) != 1) {
			throw new DataCommitException();
		}
		EventInformation eve = eventInformationDAO.getById(param.getId());
		param.setMaintainPeople(p);
		eventInformationDAO.update(param);
		for (String ownerCode : userList) {
			EventOwner t = new EventOwner();
			t.setEventId(param.getId());
			t.setUserCode(ownerCode);
			t.setCreateUserCode(userCode);
			t.setModifyUserCode(userCode);
			t.setEventProcessId(eve.getEventProcessId());
			eventOwnerDAO.insert(t);
		}

	}

	/**
	 * 
	 * @author DingXintao
	 * @date 2014-6-18 下午4:52:30
	 * @param param
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	@Override
	public List<EventInformation> getAlarmInfo(Map<String, Object> param, int start, int limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return eventInformationDAO.getAlarmInfo(param);
	}

	/**
	 * 
	 * @author DingXintao
	 * @date 2014-6-18 下午4:52:30
	 * @param param
	 * @return
	 * @see
	 */
	@Override
	public Integer alarmTotalCount(Map<String, Object> param) {
		return eventInformationDAO.alarmTotalCount(param);
	}

	@Override
	public boolean isProIntercept(String productProcessId) {
		return eventInformationDAO.checkProIntercept(productProcessId) > 0;
	}

	@Override
	public List<EventInformation> findNeedToProcess(EventInformation findParams) {

		return eventInformationDAO.findNeedToProcess(findParams);
	}

	/**
	 * <p>
	 * 获取事件明细列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-10-22 上午9:45:11
	 * @param param
	 * @return List<EventInformation>
	 */
	@Override
	public List<EventInformation> getEventInfoList(EventInformation param) {
		return eventInformationDAO.getEventInfoList(param);
	}

	@Override
	public List<String> getUncompletedOrderLateEvents(String[] equipCodes, String orgCode) {
		return eventInformationDAO.getUncompletedOrderLateEvents(equipCodes, orgCode);
	}

	@Override
	public int countUncompletedByType(EventTypeContent type, String equipCode, String orgCode) {
		return eventInformationDAO.countUncompletedByType(type, equipCode, orgCode);
	}

	@Override
	public int findForWorkOrderExtensionAlarmTask(String bachNo) {
		return eventInformationDAO.findForWorkOrderExtensionAlarmTask(bachNo);
	}

	@Override
	public List<EventInformation> getDistinctTileByOrgCode(String orgCode) {
		return eventInformationDAO.getDistinctTileByOrgCode(orgCode);
	}

	@Override
	public List<String> getEquipCodes() {
		// TODO Auto-generated method stub
		return eventInformationDAO.getEquipCodes();
	}

	@Override
	public List<EventInformation> getWarnShow() {
		// TODO Auto-generated method stub
		return eventInformationDAO.getWarnShow();
	}

	@Override
	public List<EventInformation> getByEquipCode(String equipCode,String eventTypeCodeDesc) {
		// TODO Auto-generated method stub
		return eventInformationDAO.getByEquipCode(equipCode,eventTypeCodeDesc);
	}

	@Override
	public EventInformation getRegisterData(String id) {
		// TODO Auto-generated method stub
		return eventInformationDAO.getRegisterData(id);
	}

	@Override
	public List<EquipRepairRecord> getCheckData(String eventInfoId) {
		// TODO Auto-generated method stub
		return equipRepairRecordDAO.getCheckData(eventInfoId);
	}

	@Override
	public List<EventInformation> getPendingEvent(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return eventInformationDAO.getPendingEvent(param);
	}

	@Override
	public List<EventInformation> getEquipEventPending() {
		// TODO Auto-generated method stub
		return eventInformationDAO.getEquipEventPending();
	}

	@Override
	public List<EventInformation> getEquipEventInfo(EventInformation param,
			int start, int limit, List<Sort> parseArray) {
		// TODO Auto-generated method stub
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return eventInformationDAO.getInfo(param);
	}

	@Override
	public Integer totalEquipEventInfo(EventInformation param) {
		// TODO Auto-generated method stub
		return eventInformationDAO.totalCount(param);
	}
	
	/**
	 * @Title:       hasRecentEvent
	 * @Description: TODO(根据标题判断最近一天该类型的事件是否已经产生)
	 * @param:       param 参数
	 * @return:      Integer   
	 * @throws
	 */
	@Override
	public Integer hasRecentEvent(EventInformation param){
		return eventInformationDAO.hasRecentEvent(param);
	}
}
