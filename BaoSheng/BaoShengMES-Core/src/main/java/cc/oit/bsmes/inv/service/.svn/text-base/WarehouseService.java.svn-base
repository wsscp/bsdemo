package cc.oit.bsmes.inv.service;

import cc.oit.bsmes.common.service.BaseService;
import cc.oit.bsmes.inv.model.Warehouse;

import java.util.List;

/**
 * 
 * 仓库
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2014-9-12 下午2:08:34
 * @since
 * @version
 */
public interface WarehouseService  extends BaseService<Warehouse> {
	/**
	 * 
	 * <p>根据仓库号，orgCode判断该仓库是否存在</p> 
	 * @author leiwei
	 * @date 2014-9-18 上午11:09:48
	 * @param warehouse
	 * @return
	 * @see
	 */
	Warehouse checkExtist(Warehouse warehouse);

    List<Warehouse> getByBarCode(String barCode);

	Warehouse getByCode(String wareHouseCode);

}
