package cc.oit.bsmes.eve.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.eve.dao.EventProcessDAO;
import cc.oit.bsmes.eve.model.EventProcess;
import cc.oit.bsmes.eve.service.EventProcessService;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-10 下午2:52:34
 * @since
 * @version
 */
@Service
public class EventProcessServiceImpl extends BaseServiceImpl<EventProcess> implements EventProcessService {

	@Resource private EventProcessDAO  eventProcessDAO;

	@Override
	public List<EventProcess> getByEventTypeId(String eventTypeId) {
		return eventProcessDAO.getByEventTypeId(eventTypeId);
	}

	@Override
	public void deleteEventProcessById(String id,String eventTypeId) {
		eventProcessDAO.deleteEventProcessById(id,eventTypeId);
	}

	@Override
	public Integer getProcesSeqByEventTypeId(String eventTypeId) {
		return eventProcessDAO.getProcesSeqByEventTypeId(eventTypeId);
	}

	@Override
	public Integer getProcessSeqById(String id) {
		return eventProcessDAO.getProcessSeqById(id);
	}

}
