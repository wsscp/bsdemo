package cc.oit.bsmes.eve.service;


import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.eve.model.EventType;
/**
 * 
 * 异常事件信息
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-10 下午1:47:48
 * @since
 * @version
 */
public interface EventTypeService extends BaseService<EventType> {

    public EventType getByCode(String eventCode);
    


}
