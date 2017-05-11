package cc.oit.bsmes.integration;

import cc.oit.bsmes.common.constants.InventoryDetailStatus;
import cc.oit.bsmes.common.util.ResourceCache;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.inv.model.InventoryDetail;
import cc.oit.bsmes.inv.service.InventoryDetailService;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.junit.BaseTest;
import cc.oit.bsmes.ord.service.SalesOrderService;
import cc.oit.bsmes.pla.service.CustomerOrderItemProDecService;
import cc.oit.bsmes.pla.service.CustomerOrderPlanService;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by 羽霓 on 2014/4/25.
 */
public class InvUseTest extends BaseTest {

    @Resource
    private org.springframework.core.io.Resource planData;

    @Resource
    private SalesOrderService salesOrderService;
    @Resource
    private InventoryService inventoryService;
    @Resource
    private InventoryDetailService inventoryDetailService;
    @Resource
    private CustomerOrderItemProDecService customerOrderItemProDecService;
    @Resource
    private EquipInfoService equipInfoService;
    @Resource
    private CustomerOrderPlanService customerOrderPlanService;
    @Resource
    private ResourceCache resourceCache;
    private String orgCode = "1";

    @Test
    @Rollback(false)
    public void insertInventory() {
        insertInventory("BPGGRPP-0.6/1(3*4+3*0.75)-SEM-9",500d,500d,300d);
    }

    @Test
    @Rollback(false)
    public void length1() throws InvocationTargetException, IllegalAccessException {
        insertInventory("T00002-2", 30d);
        insertInventory("T00001-2", 51d);
        insertInventory("T00001-2", 101d);
        insertInventory("T00001-2", 101d);
        analysis();
    }

    @Test
    @Rollback(false)
    public void length2() throws InvocationTargetException, IllegalAccessException {
        insertInventory("T00002-2", 60d, 60d);
        analysis();
    }

    @Test
    @Rollback(false)
    public void length3() throws InvocationTargetException, IllegalAccessException {
        insertInventory("T00002-2", 51d, 101d, 101d);
        analysis();
    }

    @Test
    @Rollback(false)
    public void length4() throws InvocationTargetException, IllegalAccessException {
        insertInventory("T00002-2", 160d);
        analysis();
    }

    @Test
    @Rollback(false)
    public void length5() throws InvocationTargetException, IllegalAccessException {
        insertInventory("T00002-2", 149d, 149d);
        analysis();
    }

    @Test
    @Rollback(false)
    public void sequence1() throws InvocationTargetException, IllegalAccessException {
        insertInventory("T00002-2", 101d, 101d);
        insertInventory("T00002-5", 51d);
        analysis();
    }

    @Test
    @Rollback(false)
    public void parallel1() throws InvocationTargetException, IllegalAccessException {
        insertInventory("D00001-7", 51d);
        insertInventory("D00001-3", 101d);
        insertInventory("D00001-4", 101d);
        insertInventory("D00002-5", 51d);
        insertInventory("D00002-2", 101d, 101d);
        analysis();
    }

    private void insertInventory(String materialCode, double... quantities) {
        double sum = 0;
        for (double quantity : quantities) {
            sum += quantity;
        }
        Inventory inventory = new Inventory();
        inventory.setQuantity(sum);
        inventory.setWarehouseId("1");
        inventory.setMaterialCode(materialCode);
        inventory.setMaterialName("tempdata");
        inventory.setOrgCode(orgCode);
        inventoryService.insert(inventory);

        for (int i = 0; i < quantities.length ; i++) {
            InventoryDetail inventoryDetail = new InventoryDetail();
            inventoryDetail.setInventoryId(inventory.getId());
            inventoryDetail.setSeq(i);
            inventoryDetail.setLength(quantities[i]);
            inventoryDetail.setStatus(InventoryDetailStatus.AVAILABLE);
            inventoryDetailService.insert(inventoryDetail);
        }
    }

    private void analysis() throws InvocationTargetException, IllegalAccessException {
        customerOrderItemProDecService.analysisOrderToProcess(resourceCache,
                orgCode);
        equipInfoService.initEquipLoad(orgCode);
        customerOrderPlanService.calculatorOA(resourceCache, orgCode);
    }

}
