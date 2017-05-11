package cc.oit.bsmes.inv.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.inv.model.Location;

import java.util.List;

public interface LocationDAO extends BaseDAO<Location> {

	/**
     * @Title:       getIdleLocation
     * @Description: TODO(根据工序编码和组织机构获取库位信息：正常库位&临时库位)
     * @param:       processCode 工序编码
     * @param:       orgCode 组织机构
     * @return:      Location   
     * @throws
     */
    public List<Location> getLocationByProcessCode(String processCode, String orgCode);
    
    Location getBySerialNum(String serialNum);
    Location getProductLocation(String processCode,String orgCode);
    List<Location> changeLocation(String warehouseId,String processCode,String orgCode,String barCode);
    public void deleteByWarehouseId(String id);
	public Location checkLocationXY(Location location);
    Location getTempLocation(String processCode,String orgCode);

	/**
	 * 获取库位信息Combox
	 * 
	 * @param findParams 参数
	 * @return List<Location>
	 */
	public List<Location> findInvLocationCombox(Location findParams);

    public void addLocation(Location location);
}
