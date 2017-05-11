package cc.oit.bsmes.inv.action;

import cc.oit.bsmes.common.util.StaticDataCache;
import cc.oit.bsmes.inv.model.Location;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.model.Warehouse;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.inv.service.LocationService;
import cc.oit.bsmes.inv.service.WarehouseService;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.service.ReportService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by joker on 2014/9/18 0018.
 */
@Controller
@RequestMapping("/inv/halfProductIn")
public class HalfProductInController {

    @Resource private WarehouseService warehouseService;
    @Resource private LocationService locationService;
    @Resource private ReportService reportService;
    @Resource private InventoryService inventoryService;



    /**
     * @param barCode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "warehouseList/{barCode}",method = RequestMethod.GET)
    public List<Warehouse> getWarehouseList(@PathVariable String barCode){
        return warehouseService.getByBarCode(barCode);
    }

    @ResponseBody
    @RequestMapping(value="locations/{warehouseId}/{barCode}",method = RequestMethod.GET)
    public List<Location> getLocation(@PathVariable String warehouseId,@PathVariable String barCode){
        return locationService.getByWareHouse(warehouseId,barCode);
    }

    @ResponseBody
    @RequestMapping(value = "productDetail",method = RequestMethod.POST)
    public JSONObject productDetail(@RequestParam String barCode){
        Report report = reportService.getByBarCode(barCode);
        JSONObject result = new JSONObject();
        if(report != null){
            Map<String,Mat> matMap = StaticDataCache.getMatMap();
            Mat mat = matMap.get(report.getHalfProductCode());
            result.put("reportLength",report.getReportLength());
            result.put("materialCode",mat.getMatCode());
            result.put("materialName",mat.getMatName());
            result.put("success",true);
        }else{
            result.put("success",false);
            result.put("msg","条码输入有误，请确认后重新输入!");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value="inWarehouse",method = RequestMethod.POST)
    public JSONObject inWarehouse(@RequestParam String barCode,
                                  @RequestParam String warehouseId,
                                  @RequestParam String locationId){
        inventoryService.handInWarehouse(barCode);
        JSONObject result = new JSONObject();
        result.put("success",true);
        return result;
    }

    @ResponseBody
    @RequestMapping(value="printBar",method = RequestMethod.POST)
    public JSONObject printBar(@RequestParam String barCode){
         Report findParams = new Report();
         findParams.setSerialNum(barCode);
         List<Report> reports = reportService.getByObj(findParams);
         JSONObject result = new JSONObject();
         if(reports.size() == 0){
             result.put("success",false);
             result.put("msg","条码输入有误，请确认后重新输入!");
         }else{
             Report report = reports.get(0);
             result.put("success",true);
             result.put("report",reportService.createBarCode(report.getId()));
         }
         return result;
    }
}
