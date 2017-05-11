package cc.oit.bsmes.inv.action;

import cc.oit.bsmes.common.exception.MESException;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.inv.service.InventoryDetailService;
import cc.oit.bsmes.inv.service.InventoryService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by jinhy on 2014/9/18 0018.
 * 半成品出库
 */

@Controller
@RequestMapping("/inv/halfProductOut")
public class HalfProductOutController {

    @Resource
    private InventoryService inventoryService;

    @Resource
    private InventoryDetailService inventoryDetailService;


    @ResponseBody
    @RequestMapping(value = "inventoryInfo",method = RequestMethod.POST)
    public Inventory inventoryInfo(@RequestParam String barCode){
        Inventory inventory = inventoryService.getInventoryInfoByBarCode(barCode);
        if(inventory == null){
            inventory = new Inventory();
        }
        return inventory;
    }

    @ResponseBody
    @RequestMapping(value ="productOut",method = RequestMethod.POST)
    public JSONObject productOut(@RequestParam String barCode){
        int deleteDetailInv = inventoryDetailService.deleteByBarCode(barCode);
        int invDeleteNum = inventoryService.deleteByBarCode(barCode);
        JSONObject result = new JSONObject();
        if(invDeleteNum == 1 && deleteDetailInv >=1){
            result.put("success",true);
        }else{
            throw new MESException("数据有误");
        }
        return result;
    }
}
