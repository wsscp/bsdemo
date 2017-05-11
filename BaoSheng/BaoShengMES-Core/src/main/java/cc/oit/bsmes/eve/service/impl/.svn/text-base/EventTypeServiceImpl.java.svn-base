package cc.oit.bsmes.eve.service.impl;

import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.eve.dao.EventTypeDAO;
import cc.oit.bsmes.eve.model.EventType;
import cc.oit.bsmes.eve.service.EventTypeService;
import cc.oit.bsmes.pro.model.ProductProcess;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-10 下午1:56:10
 * @since
 * @version
 */

@Service
public class EventTypeServiceImpl extends BaseServiceImpl<EventType> implements EventTypeService {
	@Resource private EventTypeDAO eventTypeDAO;


    @Override
    public EventType getByCode(String eventCode) {
        EventType findParams = new EventType();
        findParams.setCode(eventCode);
        List<EventType> list = eventTypeDAO.get(findParams);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


}
