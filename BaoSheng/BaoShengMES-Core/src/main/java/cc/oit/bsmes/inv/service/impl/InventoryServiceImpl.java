package cc.oit.bsmes.inv.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.InventoryLogType;
import cc.oit.bsmes.common.constants.ProcessSection;
import cc.oit.bsmes.common.constants.UnitType;
import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.interceptor.SqlInterceptor;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.inv.dao.InventoryDAO;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.inv.model.Location;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.InventoryDetailService;
import cc.oit.bsmes.inv.service.InventoryLogService;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.inv.service.LocationService;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.OrderTaskService;
import cc.oit.bsmes.pro.service.ProductProcessWipService;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ReportService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class InventoryServiceImpl extends BaseServiceImpl<Inventory> implements InventoryService {

	@Resource
	private InventoryDAO inventoryDAO;

	@Resource
	private InventoryLogService inventoryLogService;

	@Resource
	private InventoryDetailService inventoryDetailService;

	@Resource
	private LocationService locationService;

	@Resource
	private ReportService reportService;
	@Resource
	private WorkOrderService workOrderService;
	@Resource
	private OrderTaskService orderTaskService;
	@Resource
	private CustomerOrderItemProDecService customerOrderItemProDecService;
	@Resource
	private ProductProcessWipService productProcessWipService;
	@Resource
	private MatService matService;

	@Override
	public List<Inventory> findByMat(String matCode, Double proLength) {
		// TODO Auto-generated method stub暂时没用到，以后实现
		return null;
	}

	@Override
	public List<Inventory> findByMatCodeOrLocationName(String materialCode, String locationName, int offset, int limit) {
		SqlInterceptor.setRowBounds(new RowBounds(offset, limit));
		return inventoryDAO.findByMatCodeOrLocationName(materialCode, locationName);
	}

	/**
	 * 原材料库存：根据物料编码查询
	 * 
	 * @author DingXintao
	 * @param materialCode 物料编码
	 */
	@Override
	public List<Inventory> findByMatCode(String materialCode) {
		Inventory findParams = new Inventory();
		findParams.setMaterialCode(materialCode);
		return inventoryDAO.find(findParams);
	}

	@Override
	public int countByMatCodeOrLocationName(String materialCode, String locationName) {
		return inventoryDAO.countByMatCodeOrLocationName(materialCode, locationName);
	}

	@Override
	public List<Inventory> findForExport(JSONObject obj) throws InvocationTargetException, IllegalAccessException,
			NoSuchMethodException {
		Inventory findParams = JSON.toJavaObject(obj, Inventory.class);
		addLike(findParams, Inventory.class);
		return inventoryDAO.findByMatCodeOrLocationName(findParams.getMaterialCode(), findParams.getLocationName());
	}

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
	public List<Inventory> getLocationByWorkOrderNo(Inventory inventory) {
		return inventoryDAO.getLocationByWorkOrderNo(inventory);
	}

	/**
	 * @Title:       productInWarehouse
	 * @Description: TODO(入库根据工序信息查询空闲的库位)
	 * @param:       workOrder 生产单信息
	 * @param:       report 报工信息
	 * @param:       locationName 库位
	 * @return:      void   
	 * @throws
	 */
	@Override
	public void productInWarehouse(WorkOrder workOrder, Report report, String locationName) {
		Location location = null;
		// 1、库名没有
		if (StringUtils.isBlank(locationName)) {
			location = this.getLocation(workOrder);
		} else {
			// 2、获取库位信息：库名、工序编码
			Location findParams = new Location();
			findParams.setLocationName(locationName);
			findParams.setProcessCode(workOrder.getProcessCode());
			List<Location> locationList = locationService.getByObj(findParams);
			location = locationList.get(0);
		}
		if (null == location) { // 这样也取不到库位，返回，基本不可能
			return;
		}
		this.inWarehouse(workOrder, report, location);
	}

	/**
	 * @Title:       getLocation
	 * @Description: TODO(获取库位信息)
	 * @param:       @param productProcessWip
	 * @param:       @param location
	 * @param:       @return   
	 * @return:      Location   
	 * @throws
	 */
	private Location getLocation(WorkOrder workOrder) {
		Location location = null;
		// 1、最后一道生产单报工:成品库
		if (WebConstants.END_WO_ORDER.equals(workOrder.getNextSection())) {
			location = locationService.getProductLocation(WebConstants.ROOT_ID, this.getOrgCode());
		} else {
			// 2、半成品库位根据工序id寻找
			location = locationService.getLocationByProcessCode(workOrder.getProcessCode(), this.getOrgCode());
		}
		return location;
	}
	
	/**
	 * @Title:       inWarehouse
	 * @Description: TODO(新增库存信息)
	 * @param:       workOrder
	 * @param:       report
	 * @param:       locationId
	 * @param:       warehouseId   
	 * @throws
	 */
	private void inWarehouse(WorkOrder workOrder, Report report, Location location) {
		Inventory inventory = new Inventory();
		inventory.setLocationId(location.getId());
		inventory.setWarehouseId(location.getWarehouseId());
		inventory.setMaterialCode(workOrder.getHalfProductCode());
		Mat mat = matService.getByCode(workOrder.getHalfProductCode());
	    inventory.setMaterialName(null == mat ? "" : mat.getMatName());
		inventory.setBarCode(report.getSerialNum());
		inventory.setQuantity(report.getReportLength());
		inventory.setLockedQuantity(0.0);
		inventory.setOrgCode(this.getOrgCode());
		inventory.setUnit(UnitType.M.name());
		inventoryDAO.insert(inventory);
		// 记录入库日志
		inventoryLogService.log(inventory.getId(), inventory.getQuantity(), InventoryLogType.IN);
		// 记录明细
		// inventoryDetailService.recordInWareHouseDetail(inventory.getId(),report.getId());
	}

	@Override
	public void handInWarehouse(String barCode) {
		Report report = reportService.getByBarCode(barCode);
		WorkOrder workOrder = workOrderService.getByBarCode(barCode, getOrgCode());
		Location location = locationService.getLocationByProcessCode(workOrder.getProcessCode(), getOrgCode());
		inWarehouse(workOrder, report, location);
	}

	@Override
	public Inventory getInventoryInfoByBarCode(String barCode) {
		return inventoryDAO.getByBarCode(barCode);
	}

	@Override
	public int deleteByBarCode(String barCode) {
		Inventory inventory = inventoryDAO.getByBarCode(barCode);
		if (inventory == null) {
			return 0;
		}
		inventoryLogService.log(inventory.getId(), inventory.getQuantity(), InventoryLogType.OUT);
		inventoryDetailService.deleteByBarCode(barCode);
		return inventoryDAO.delete(inventory.getId());
	}

	/**
	 * 查询库存列表
	 * 
	 * @param findParams 参数
	 * @param start 分页-开始
	 * @param limit 分页-数量
	 * @param sortList 排序
	 * @return List<Inventory>
	 */
	public List<Inventory> findInvenTory(Inventory findParams, Integer start, Integer limit, List<Sort> sortList) {
		SqlInterceptor.setRowBounds(new RowBounds(start, limit));
		return inventoryDAO.findInvenTory(findParams);
	}

	/**
	 * 库存列表数
	 * 
	 * @param findParams 参数
	 * @return int
	 */
	public int countInvenTory(Inventory findParams) {
		return inventoryDAO.countInvenTory(findParams);
	}

	@Override
	public Inventory getByBarCode(String barCode) {
		return inventoryDAO.getByBarCode(barCode);
	}

	@Override
	public List<Inventory> findByBarCode(String barCode) {
		return inventoryDAO.findByBarCode(barCode);
	}

	/**
	 * 获取订单的原材料的物料需求
	 * 
	 * @author DingXintao
	 * @param workOrderNo 上道生产单号
	 * @param orderItemIds 订单id字符串，逗号分割
	 * @param section 工段
	 * @return List<Inventory>
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map<String, String>> getMaterialsInventory(String workOrderNo, String[] orderItemIds, ProcessSection section) {
		// 只查询上道生产单半成品，其他原材料目前不查，后期原材料库存添加了再查
		if(StringUtils.isEmpty(workOrderNo)){
			return new ArrayList<Map<String, String>>();
		}
		
		Map findParams = new HashMap();
		findParams.put("workOrderNo", workOrderNo);
		findParams.put("orderItemIds", new ArrayList<String>(Arrays.asList(orderItemIds)));
		findParams.put("section", section.getOrder());
		return inventoryDAO.getMaterialsInventory(findParams);
	}
}
