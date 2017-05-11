package cc.oit.bsmes.inv.service;

import java.util.List;
import java.util.Map;

import cc.oit.bsmes.common.constants.ProcessSection;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.WorkOrder;

/**
 * InventoryService
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author JinHanyun
 * @date 2013-12-24 下午5:24:48
 * @since
 * @version
 */
public interface InventoryService extends BaseService<Inventory> {

	/**
	 * 根据产品代码，及生产长度，查询所有的符合条件的半成品集合,按长度从小到大排序
	 * 
	 * @author JinHanyun
	 * @date 2013-12-25 上午9:57:48
	 * @param matCode 半成品代码
	 * @param proLength 需要生产的半成品长度
	 * @return
	 * @see
	 */
	public List<Inventory> findByMat(String matCode, Double proLength);

	/**
	 * 
	 * <p>
	 * 原材库存事物
	 * </p>
	 * 
	 * @author JinHanyun
	 * @date 2014-2-28 下午3:48:18
	 * @param materialCode
	 * @param locationName
	 * @return
	 * @see
	 */
	public List<Inventory> findByMatCodeOrLocationName(String materialCode, String locationName, int offset, int limit);

	/**
	 * 原材料库存：根据物料编码查询
	 * 
	 * @author DingXintao
	 * @param materialCode 物料编码
	 */
	public List<Inventory> findByMatCode(String materialCode);

	public int countByMatCodeOrLocationName(String materialCode, String locationName);

	
	/**
	 * @Title:       productInWarehouse
	 * @Description: TODO(入库根据工序信息查询空闲的库位)
	 * @param:       workOrder 生产单信息
	 * @param:       report 报工信息
	 * @param:       locationName 库位
	 * @return:      void   
	 * @throws
	 */
	public void productInWarehouse(WorkOrder workOrder, Report report, String locationName);

	/**
	 * <p>
	 * 库存管理块：获取当前工序的物料位置信息
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-9-15 11:16:48
	 * @param inventory 物料号和工序ID
	 * @return List<Inventory>
	 * @see
	 */
	public List<Inventory> getLocationByWorkOrderNo(Inventory inventory);

	/**
	 * 
	 * @param barCode
	 * @return
	 */
	public Inventory getInventoryInfoByBarCode(String barCode);

	public int deleteByBarCode(String barCode);

	public void handInWarehouse(String barCode);

	/**
	 * 查询库存列表
	 * 
	 * @param findParams 参数
	 * @param start 分页-开始
	 * @param limit 分页-数量
	 * @param sortList 排序
	 * @return List<Inventory>
	 */
	public List<Inventory> findInvenTory(Inventory findParams, Integer start, Integer limit, List<Sort> sortList);

	/**
	 * 库存列表数
	 * 
	 * @param findParams 参数
	 * @return int
	 */
	public int countInvenTory(Inventory findParams);

	public Inventory getByBarCode(String barCode);

	List<Inventory> findByBarCode(String barCode);

	/**
	 * 获取订单的原材料的物料需求
	 * 
	 * @author DingXintao
	 * @param workOrderNo 上道生产单号
	 * @param orderItemIds 订单id字符串，逗号分割
	 * @param section 工段
	 * @return List<Inventory>
	 */
	public List<Map<String, String>> getMaterialsInventory(String workOrderNo, String[] orderItemIds, ProcessSection section);

}
