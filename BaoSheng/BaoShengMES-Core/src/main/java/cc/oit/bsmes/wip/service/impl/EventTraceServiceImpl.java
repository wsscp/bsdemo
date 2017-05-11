package cc.oit.bsmes.wip.service.impl;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.wip.dao.EventTraceDAO;
import cc.oit.bsmes.wip.model.EventTrace;
import cc.oit.bsmes.wip.service.EventTraceService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-3-4 下午3:39:53
 * @since
 * @version
 */
@Service
public class EventTraceServiceImpl extends BaseServiceImpl<EventTrace> implements EventTraceService {
	@Resource private EventTraceDAO eventTraceDAO;
	
	@Override
	public List<EventTrace> findForExport(JSONObject queryFilter) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
		EventTrace eventTrace=JSON.toJavaObject(queryFilter, EventTrace.class);
		return eventTraceDAO.find(eventTrace);
	}

	@Override
	public List<EventTrace> getProcess() {
		return eventTraceDAO.getProcess();
	}

	@Override
	public List<EventTrace> findAllEventTrace(Map<String, Object> findParams) {
		return eventTraceDAO.findAllEventTrace(findParams);
	}

	@Override
	public int countEventTrace(Map<String, Object> findParams) {
		return eventTraceDAO.countEventTrace(findParams);
	}
}
