package cc.oit.bsmes.inv.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.LocationType;
import cc.oit.bsmes.inv.model.Warehouse;
import cc.oit.bsmes.inv.service.WarehouseService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.inv.dao.LocationDAO;
import cc.oit.bsmes.inv.model.Location;
import cc.oit.bsmes.inv.service.LocationService;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;

@Service
public class LocationServiceImpl extends BaseServiceImpl<Location> implements LocationService {
	@Resource
    private LocationDAO locationDAO;

    @Resource private WorkOrderService workOrderService;
    @Resource private ProductProcessService productProcessService;
    @Resource private WarehouseService warehouseService;

   
    /**
     * @Title:       getIdleLocation
     * @Description: TODO(根据工序编码和组织机构获取库位信息：正常库位&临时库位)
     * @param:       processCode 工序编码
     * @param:       orgCode 组织机构
     * @return:      Location   
     * @throws
     */
    @Override
    public Location getLocationByProcessCode(String processCode, String orgCode) {
        return this.addTLLocation(processCode);
    }
    
    /**
     * 添加特缆临时库位
     * @param processCode
     */
    @Override
    public Location addTLLocation(String processCode){
        Warehouse warehouse = warehouseService.getByCode(WebConstants.TL_WAREHOUSE_CODE);
        User user = SessionUtils.getUser();
        Location location = new Location();
        location.setWarehouseId(warehouse.getId());
        location.setProcessCode(processCode);
        location.setCreateUserCode(user.getUserCode());
        location.setModifyUserCode(user.getUserCode());
        location.setOrgCode(user.getOrgCode());
        location.setType(LocationType.TEMP);
        location.setId(UUID.randomUUID().toString());
        locationDAO.addLocation(location);
        return location;
    }

    /**
     * @Title:       getProductLocation
     * @Description: TODO(获取成品库位)
     * @param:       processCode 工序编码
     * @param:       orgCode 组织机构
     * @return:      Location   
     * @throws
     */
    @Override
    public Location getProductLocation(String processCode, String orgCode) {
        return locationDAO.getProductLocation(processCode, orgCode);
    }

    @Override
    public Location getBySerialNum(String serialNum) {
        return locationDAO.getBySerialNum(serialNum);
    }

    @Override
    public List<Location> getByWareHouse(String wareHouseId, String barCode) {
        WorkOrder workOrder = workOrderService.getByBarCode(barCode,barCode);
        if(workOrder == null)
            return new ArrayList<Location>();
       // Map<String,ProductProcess> processMap1 = StaticDataCache.getProcessMap();
        ProductProcess productProcess = StaticDataCache.getProcessByProcessId(workOrder.getProcessId())  ;
        if(productProcess == null)
            return new ArrayList<Location>();
        String processCode = "";
        ProductProcess nextProductProcess = null;
        if(WebConstants.ROOT_ID.equals(productProcess.getNextProcessId())){
            processCode = WebConstants.ROOT_ID;
        }else{
            nextProductProcess = StaticDataCache.getProcessByProcessId(productProcess.getNextProcessId());
        }
        if(StringUtils.isBlank(processCode) && nextProductProcess == null)
            return new ArrayList<Location>();

        if(StringUtils.isBlank(processCode)){
            processCode = nextProductProcess.getProcessCode();
        }
        return locationDAO.changeLocation(wareHouseId,processCode,SessionUtils.getUser().getOrgCode(),barCode);
    }

	@Override
	public void deleteByWarehouseId(String id) {
		locationDAO.deleteByWarehouseId(id);
	}

	@Override
	public Location checkLocationXY(Location location) {
		return locationDAO.checkLocationXY(location);	
	}

    @Override
    public Location getTempLocation(String processCode) {
        return locationDAO.getTempLocation(processCode,SessionUtils.getUser().getOrgCode());
    }

	/**
	 * 获取库位信息Combox
	 * 
	 * @param findParams 参数
	 * @return List<Location>
	 */
	public List<Location> findInvLocationCombox(Location findParams) {
		return locationDAO.findInvLocationCombox(findParams);
	}
}
