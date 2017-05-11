package cc.oit.bsmes.inv.dao;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.inv.model.Inventory;

/**
 * InventoryDAO
 * <p style="display:none">modifyRecord</p>
 * @author chanedi
 * @date 2013年12月31日 下午1:59:12
 * @since
 * @version
 */
public interface InventoryDAO extends BaseDAO<Inventory> {
	
	public List<Inventory> findByMatCodeOrLocationName(String materialCode,
			String locationName);
	
	public int countByMatCodeOrLocationName(String materialCode,String locationName);

	/**
     * <p>库存管理块：获取当前工序的物料位置信息</p> 
     * @author DingXintao
     * @date 2014-9-15 11:16:48
     * @param inventory 物料号和工序ID
     * @return List<Inventory>
     * @see
     */
	public List<Inventory> getLocationByWorkOrderNo(Inventory inventory);

    public Inventory getByBarCode(String barCode);

	/**
	 * 查询库存列表
	 * 
	 * @param findParams 参数
	 * @param start 分页-开始
	 * @param limit 分页-数量
	 * @return List<Inventory>
	 */
	public List<Inventory> findInvenTory(Inventory findParams);

	/**
	 * 库存列表数
	 * 
	 * @param findParams 参数
	 * @return int
	 */
	public int countInvenTory(Inventory findParams);
	
	List<Inventory> findByBarCode(String barCode);
	
	/**
	 * 获取订单的原材料的物料需求
	 * 
	 * @author DingXintao 
	 * @param orderItemIds 订单id字符串，逗号分割
	 * @param section 工段
	 * @return List<Inventory>
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String, String>> getMaterialsInventory(Map findParams);
}
