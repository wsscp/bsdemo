package cc.oit.bsmes.inv.action;

import java.util.List;

import javax.annotation.Resource;

import cc.oit.bsmes.common.mybatis.Sort;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.constants.LocationType;
import cc.oit.bsmes.common.constants.WarehouseType;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.inv.model.Location;
import cc.oit.bsmes.inv.model.Warehouse;
import cc.oit.bsmes.inv.service.LocationService;
import cc.oit.bsmes.inv.service.WarehouseService;
import cc.oit.bsmes.pro.model.ProcessInformation;
import cc.oit.bsmes.pro.service.ProcessInformationService;

/**
 * 
 * 仓库
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author leiwei
 * @date 2014-9-17 下午3:48:51
 * @since
 * @version
 */
@Controller
@RequestMapping("/inv/warehouse")
public class WarehouseController {
	@Resource
	private WarehouseService warehouseService;
	@Resource
	private ProcessInformationService processInformationService;
	@Resource
	private LocationService locationService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "inv");
		model.addAttribute("submoduleName", "warehouse");
		return "inv.warehouse";
	}

	@RequestMapping
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public TableView list(@ModelAttribute Warehouse param, @RequestParam(required = false) String sort,
			@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer limit) {
		param.setOrgCode(warehouseService.getOrgCode());
		List list = warehouseService.find(param, start, limit, JSONArray.parseArray(sort, Sort.class));
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(warehouseService.count(param));
		return tableView;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public UpdateResult save(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		Warehouse warehouse = JSON.parseObject(jsonText, Warehouse.class);
		warehouseService.insert(warehouse);
		warehouse = warehouseService.getById(warehouse.getId());
		List<ProcessInformation> list = processInformationService.findByCodeOrName(null);
		for (ProcessInformation processInformation : list) {
			Location location = new Location();
			location.setProcessCode(processInformation.getCode());
			location.setLocationName("临时库位");
			location.setWarehouseId(warehouse.getId());
			location.setOrgCode(SessionUtils.getUser().getOrgCode());
			location.setType(LocationType.TEMP);
			locationService.insert(location);
		}
		Location local = new Location();
		local.setOrgCode(SessionUtils.getUser().getOrgCode());
		local.setType(LocationType.STORE);
		local.setProcessCode("-1");
		local.setWarehouseId(warehouse.getId());
		local.setLocationName("成品库");
		locationService.insert(local);
		updateResult.addResult(warehouse);
		return updateResult;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) throws ClassNotFoundException {
		UpdateResult updateResult = new UpdateResult();
		Warehouse warehouse = JSON.parseObject(jsonText, Warehouse.class);
		warehouse.setModifyUserCode(SessionUtils.getUser().getUserCode());
		warehouseService.update(warehouse);
		updateResult.addResult(warehouse);
		return updateResult;
	}

	@RequestMapping(value = "check/{warehouseCode}", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject checkUserCodeUnique(@PathVariable String warehouseCode) throws ClassNotFoundException {
		JSONObject object = new JSONObject();
		Warehouse warehouse = new Warehouse();
		warehouse.setWarehouseCode(warehouseCode);
		warehouse.setOrgCode(SessionUtils.getUser().getOrgCode());
		Warehouse house = warehouseService.checkExtist(warehouse);
		object.put("codeExtist", house == null);
		return object;
	}

	@RequestMapping(value = "checkType/{type}", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject checkType(@PathVariable String type) throws ClassNotFoundException {
		JSONObject object = new JSONObject();
		Warehouse warehouse = new Warehouse();
		warehouse.setType(WarehouseType.WIP);
		warehouse.setOrgCode(SessionUtils.getUser().getOrgCode());
		List<Warehouse> list = warehouseService.findByObj(warehouse);
		object.put("checkExtist", list.size() > 0);
		return object;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable String id) {
		locationService.deleteByWarehouseId(id);
		warehouseService.deleteById(id);
	}

	/**
	 * 获取企业下所有的仓库信息
	 * */
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	@ResponseBody
	public List<Warehouse> getAll() {
		Warehouse warehouse = new Warehouse();
		warehouse.setOrgCode(SessionUtils.getUser().getOrgCode());
		return warehouseService.getByObj(warehouse);
	}
}
