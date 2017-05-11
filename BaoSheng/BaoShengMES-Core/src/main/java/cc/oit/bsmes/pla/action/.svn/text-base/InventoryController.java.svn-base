package cc.oit.bsmes.pla.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.UnitType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.inv.model.Inventory;
import cc.oit.bsmes.inv.model.Location;
import cc.oit.bsmes.inv.model.Warehouse;
import cc.oit.bsmes.inv.service.InventoryService;
import cc.oit.bsmes.inv.service.LocationService;
import cc.oit.bsmes.inv.service.WarehouseService;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.service.ProcessInformationService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * 
 * 库存
 * 
 * @author DingXintao
 * @date 2014-09-12 下午6:10:04
 * @since
 * @version
 */
@Controller
@RequestMapping("/pla/inventory")
public class InventoryController {
	@Resource
	private InventoryService inventoryService;
	@Resource
	private LocationService locationService;
	@Resource
	private ProcessInformationService processInformationService;
	@Resource
	private WarehouseService wareHouseService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "pla");
		model.addAttribute("submoduleName", "inventory");
		return "pla.inventory";
	}

	@RequestMapping
	@ResponseBody
	public TableView indexList(HttpServletRequest request, @ModelAttribute Inventory findParams, @RequestParam String sort, @RequestParam int page,
			@RequestParam int start, @RequestParam int limit) {
		findParams.setOrgCode(inventoryService.getOrgCode());
		List<Inventory> list = inventoryService.findInvenTory(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
		Integer count = inventoryService.countInvenTory(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(count);
		return tableView;
	}

	/**
	 * <p>
	 * 获取仓库列表
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-9-17 14:16:48
	 * @param request 请求
	 * @param params 参数
	 * @return List<WareHouse>
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "wareHouseCombox", method = RequestMethod.GET)
	public List<Warehouse> wareHouseCombox(HttpServletRequest request, @ModelAttribute Warehouse params) {
		params.setOrgCode(SessionUtils.getUser().getOrgCode());
		return wareHouseService.findByObj(params);
	}

	/**
	 * <p>
	 * 获取库位信息Combox
	 * </p>
	 * 
	 * @author DingXintao
	 * @date 2014-9-17 14:16:48
	 * @param request 请求
	 * @param params 参数
	 * @return List<Location>
	 * @see
	 */
	@ResponseBody
	@RequestMapping(value = "invLocationCombox", method = RequestMethod.GET)
	public List<Location> invLocationCombox(HttpServletRequest request, @ModelAttribute Location params) {
		params.setOrgCode(SessionUtils.getUser().getOrgCode());
		return locationService.findInvLocationCombox(params);
	}

	/**
	 * 修改
	 * */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) {
		UpdateResult updateResult = new UpdateResult();
		Inventory inventory = JSON.parseObject(jsonText, Inventory.class);
		inventoryService.update(inventory);
		updateResult.addResult(inventory);
		return updateResult;
	}

	/**
	 * 新增
	 * */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) {
		UpdateResult updateResult = new UpdateResult();
		Inventory inventory = JSON.parseObject(jsonText, Inventory.class);
		if (UnitType.KM.name().equals(inventory.getUnit()) || UnitType.KG.name().equals(inventory.getUnit())
				|| UnitType.TON.name().equals(inventory.getUnit())) {
			inventory.setUnit(UnitType.KM.name().equals(inventory.getUnit()) ? UnitType.M.name() : UnitType.G.name());
			inventory.setQuantity(inventory.getQuantity() * 1000);
		}
		inventoryService.insert(inventory);
		updateResult.addResult(inventory);
		return updateResult;
	}

}
