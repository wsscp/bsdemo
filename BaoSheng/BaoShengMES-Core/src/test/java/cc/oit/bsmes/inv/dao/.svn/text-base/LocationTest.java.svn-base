package cc.oit.bsmes.inv.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import cc.oit.bsmes.common.constants.LocationType;
import cc.oit.bsmes.inv.model.Location;
import cc.oit.bsmes.inv.model.Warehouse;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.service.ProcessInformationService;

/**
 * Created by Jinhy on 2015/1/10.
 */
public class LocationTest extends BaseTest{
    @Resource
    private LocationDAO locationDAO;

    @Resource
    private WarehouseDAO warehouseDAO;

    @Resource
    private ProcessInformationService processInformationService;


    @Test
    @Rollback(false)
    public void addLocatio(){
        Warehouse warehouse = warehouseDAO.getById("2c5b1494-ad57-47cf-9f1d-f9fa4c2575b0");
        List<ProcessInformation> list = processInformationService.findByCodeOrName(null);
        for(int j = 0;j<list.size();j++){
            ProcessInformation processInformation = list.get(j);
            for(int i=1;i<30;i++){
                Location location = new Location();
                location.setWarehouseId(warehouse.getId());
                location.setLocationName("E1001" + j + "" + i);
                location.setProcessCode(processInformation.getCode());
                location.setLocationX("X" + i);
                location.setLocationY("Y" + j);
                location.setType(LocationType.FIX);
                locationDAO.insert(location);
            }
        }
    }
}
