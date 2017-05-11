package cc.oit.bsmes.inv.dao;

import cc.oit.bsmes.common.dao.BaseDAO;
import cc.oit.bsmes.inv.model.Warehouse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WarehouseDAO extends BaseDAO<Warehouse> {

	Warehouse checkExtist(Warehouse warehouse);

    /**
     * 查询工艺对应的仓库 非临时库
     * @param processCode
     * @param orgCode
     * @return
     */
    List<Warehouse> getByProcess(String processCode,String orgCode);

    Warehouse getByCode(@Param("wareHouseCode") String wareHouseCode);

}
