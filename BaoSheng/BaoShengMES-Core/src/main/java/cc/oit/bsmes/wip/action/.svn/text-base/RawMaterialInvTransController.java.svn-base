package cc.oit.bsmes.wip.action;

import cc.oit.bsmes.common.constants.InventoryLogType;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.inv.model.InventoryLog;
import cc.oit.bsmes.inv.service.InventoryLogService;
import cc.oit.bsmes.inv.service.InventoryService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jxl.write.WriteException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 
 * 原材料库存事物
 * <p style="display:none">modifyRecord</p>
 * @author JinHanyun
 * @date 2014-2-27 下午4:19:47
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/rawMaterialInvTrans")
public class RawMaterialInvTransController {
	
	@Resource
	private InventoryService inventoryService;
	
	@Resource
	private InventoryLogService inventoryLogService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
        model.addAttribute("submoduleName", "rawMaterialInvTrans");
        return "wip.rawMaterialInvTrans"; 
    }
	
	
    @ResponseBody
    @RequestMapping
    public TableView list(	@RequestParam(required=false) String materialCode,
				    		@RequestParam(required=false) String locationName,
				    		@RequestParam int page, 
				    		@RequestParam int start, 
				    		@RequestParam int limit) {
    	TableView tableView = new TableView();
    	if(StringUtils.isNotBlank(materialCode)){
    		materialCode = "%"+materialCode+"%";
    	}
    	if(StringUtils.isNotBlank(locationName)){
    		locationName = "%"+locationName+"%";
    	}
    	List<Inventory> rows = inventoryService.findByMatCodeOrLocationName(materialCode, locationName,start,limit);
    	int total = inventoryService.countByMatCodeOrLocationName(materialCode, locationName);
    	tableView.setRows(rows);
    	tableView.setTotal(total);
    	return tableView;
    }
    
    @ResponseBody
    @RequestMapping(value="inventoryLogs/{inventoryId}/{quantity}")
    public TableView inventoryLogs(@PathVariable String inventoryId,
    								@PathVariable double quantity,
    								@RequestParam int page, 
				    				@RequestParam int start, 
				    				@RequestParam int limit){
    	TableView tableView = new TableView();
    	InventoryLog findParams = new InventoryLog();
    	findParams.setInventoryId(inventoryId);
    	List<InventoryLog> rows  = inventoryLogService.getByInventoryId(inventoryId);
    	calculateQuantity(quantity, rows);
    	tableView.setRows(rows);
    	return tableView;
    }
    
    private static void calculateQuantity(double quantity,List<InventoryLog> rows){
    	for(int i=rows.size();i>0;i--){
    		InventoryLog log = rows.get(i-1);
    		log.setAfterQuantity(quantity);
    		double inventoryQuantity = quantity;
    		if(log.getType() == InventoryLogType.OUT){
    			inventoryQuantity = quantity +  log.getQuantity();
    		}else if(log.getType() == InventoryLogType.IN){
    			inventoryQuantity = quantity -  log.getQuantity();
    		}
    		log.setBeforeQuantity(inventoryQuantity);
    		quantity = inventoryQuantity;
    	}
    }
    
    @RequestMapping(value="/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response,
                       @PathVariable String fileName,
                       @RequestParam String params,
                       @RequestParam(required = false) String queryParams) throws IOException, WriteException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        JSONArray columns = JSONArray.parseArray(params);
        String sheetName = fileName;
        fileName = URLEncoder.encode(fileName, "UTF8") + ".xls";
        String userAgent = request.getHeader("User-Agent").toLowerCase(); // 获取用户代理
        if (userAgent.indexOf("msie") != -1) { // IE浏览器
            fileName = "filename=\"" + fileName + "\"";
        } else if (userAgent.indexOf("mozilla") != -1) { // firefox浏览器
            fileName = "filename*=UTF-8''" + fileName;
        }

        response.reset();
        response.setContentType("application/ms-excel");
        response.setHeader("Content-Disposition", "attachment;" + fileName);
        OutputStream os = response.getOutputStream();
        JSONObject queryFilter  = JSONObject.parseObject(queryParams);
        inventoryService.export(os, sheetName, columns,queryFilter);
        os.close();
	}
}
