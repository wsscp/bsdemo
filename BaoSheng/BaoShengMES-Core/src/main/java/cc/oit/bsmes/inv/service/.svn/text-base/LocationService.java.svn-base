package cc.oit.bsmes.inv.service;

import java.util.List;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.inv.model.Location;

/**
 * 
 * 库位
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-9-12 下午2:14:50
 * @since
 * @version
 */
public interface LocationService extends BaseService<Location> {

    /**
     * @Title:       getIdleLocation
     * @Description: TODO(根据工序编码和组织机构获取库位信息：正常库位&临时库位)
     * @param:       processCode 工序编码
     * @param:       orgCode 组织机构
     * @return:      Location   
     * @throws
     */
    public Location getLocationByProcessCode(String processCode, String orgCode);

    /**
     * @Title:       getProductLocation
     * @Description: TODO(获取成品库位)
     * @param:       processCode 工序编码
     * @param:       orgCode 组织机构
     * @return:      Location   
     * @throws
     */
    public Location getProductLocation(String processCode,String orgCode);

    Location getBySerialNum(String serialNum);

    List<Location> getByWareHouse(String wareHouseId,String barCode);
    /**
     * 
     * <p>根据仓库id删除库位</p> 
     * @author leiwei
     * @date 2014-9-22 下午5:42:20
     * @param id
     * @see
     */
	public void deleteByWarehouseId(String id);
	/**
	 * 
	 * 检验某一仓库下某工序的库位X,Y是否唯一 
	 * @author leiwei
	 * @date 2014-9-22 下午5:42:55
	 * @param location
	 * @see
	 */
	public Location checkLocationXY(Location location);


    Location getTempLocation(String processCode);

	/**
	 * 获取库位信息Combox
	 * 
	 * @param findParams 参数
	 * @return List<Location>
	 */
	public List<Location> findInvLocationCombox(Location findParams);

    public Location addTLLocation(String processCode);
}
