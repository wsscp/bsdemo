package cc.oit.bsmes.inv.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.oit.bsmes.common.mybatis.Sort;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.constants.LocationType;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.inv.model.Location;
import cc.oit.bsmes.inv.service.LocationService;
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
@RequestMapping("/inv/location")
public class LocationController {
	@Resource
	private LocationService locationService;
	@Resource
	private ProcessInformationService processInformationService;
	private static String warehouseId = "";

	@RequestMapping(produces = "text/html")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		warehouseId = request.getParameter("warehouseId");
		model.addAttribute("moduleName", "inv");
		model.addAttribute("submoduleName", "location");
		return "inv.location";
	}

	@RequestMapping
	@ResponseBody
	@SuppressWarnings("rawtypes")
	public TableView list(@ModelAttribute Location param, @RequestParam(required = false) Integer start,
			@RequestParam(required = false) Integer limit, @RequestParam(required = false) String sort) {
		param.setOrgCode(SessionUtils.getUser().getOrgCode());
		List list = locationService.find(param, start, limit, JSONArray.parseArray(sort, Sort.class));
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(locationService.count(param));
		return tableView;
	}

	@ResponseBody
	@RequestMapping(value = "insert", method = RequestMethod.POST)
	public void insert(HttpServletRequest request) {
		String data = request.getParameter("location");
		Location location = JSONObject.parseObject(data, Location.class);
		location.setType(LocationType.FIX);
		location.setWarehouseId(warehouseId);
		locationService.insert(location);
	}

	@ResponseBody
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public void update(HttpServletRequest request) {
		String data = request.getParameter("location");
		Location location = JSON.parseObject(data, Location.class);
		location.setOrgCode(SessionUtils.getUser().getOrgCode());
		location.setModifyUserCode(SessionUtils.getUser().getUserCode());
		locationService.update(location);
	}

	@ResponseBody
	@RequestMapping(value = "check", method = RequestMethod.GET)
	public String check(@RequestParam String processCode, @RequestParam String locationX, @RequestParam String locationY) {
		Location location = new Location();
		location.setLocationX(locationX);
		location.setLocationY(locationY);
		location.setProcessCode(processCode);
		location.setWarehouseId(warehouseId);
		Location local = locationService.checkLocationXY(location);
		String result = local == null ? "0" : "1";
		return result;
	}

	/**
	 * 根据仓库ID获取库存位置信息
	 * 
	 * @param warehouseId 仓库ID
	 * */
	@ResponseBody
	@RequestMapping(value = "getByWarehouseId", method = RequestMethod.GET)
	public List<Location> getByWarehouseId(@RequestParam String warehouseId) {
		Location location = new Location();
		location.setWarehouseId(warehouseId);
		return locationService.findByObj(location);
	}

}
