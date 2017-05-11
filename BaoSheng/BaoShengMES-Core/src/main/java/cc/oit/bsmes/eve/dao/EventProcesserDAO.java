package cc.oit.bsmes.eve.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.eve.model.EventProcesser;
/**
 * 
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-13 下午3:14:22
 * @since
 * @version
 */

public interface EventProcesserDAO extends BaseDAO<EventProcesser> {
	public List<EventProcesser> getByEventProcessId(String eventProcessId);
}
