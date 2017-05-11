package cc.oit.bsmes.eve.dao;

import java.util.List;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.eve.model.EventType;
import cc.oit.bsmes.pro.model.ProductProcess;
/**
 * 
 * TODO(描述类的职责)
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-2-10 下午1:57:55
 * @since
 * @version
 */
public interface EventTypeDAO extends BaseDAO<EventType> {
	
	public List<ProductProcess> getProcess();

}
