package cc.oit.bsmes.ord.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.ord.model.SalesOrderItem;

import java.util.List;
import java.util.Map;

/**
 * SalesOrderItemDAO
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月19日 下午4:30:34
 * @since
 * @version
 */
public interface SalesOrderItemDAO extends BaseDAO<SalesOrderItem> {

	public SalesOrderItem getByCustomerOrderItemId(String customerOrderItemId);

	public List<SalesOrderItem> getByWorkOrder(String workOrderNo);

	public String getLastProcessCode(String salesOrderItemId);

	public Map<String,Object> getJJInfo(String id);

	public List<Map<String, String>> getFinishedGWData(Map<String, Object> param);

}
