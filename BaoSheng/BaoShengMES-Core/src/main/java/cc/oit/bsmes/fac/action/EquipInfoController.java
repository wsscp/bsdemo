package cc.oit.bsmes.fac.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;

import com.alibaba.fastjson.JSONArray;

@Controller
@RequestMapping("/fac/equipInfo")
public class EquipInfoController {

	@Resource
	private EquipInfoService equipInfoService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "fac");
		model.addAttribute("submoduleName", "equipInfo");
		return "fac.equipInfo";
	}

	@RequestMapping
	@ResponseBody
	public TableView list(HttpServletRequest request, @RequestParam(required = false) String sort,
			@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer limit,
			@RequestParam(required = false) String type, @RequestParam(required = false) String code) {
		EquipInfo params = new EquipInfo();
		String type1 = type.substring(0, type.length()-4);
		params.setType(type1);
		params.setCode(code);
		// 查询
		List<EquipInfo> list = equipInfoService.find(params, start, limit, JSONArray.parseArray(sort, Sort.class));
		Date now = new Date();
		for (EquipInfo equipInfo : list) {
			equipInfoService.fixNextMaintainDate(now, equipInfo);
		}
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(equipInfoService.count(params));
		return tableView;
	}

	/**
	 * 获取生产线
	 * */
	@ResponseBody
	@RequestMapping(value = "/getEquipLine", method = RequestMethod.GET)
	public List<EquipInfo> getEquipLine(HttpServletRequest request, @RequestParam(required = false) String status,
			@RequestParam(required = false) String codeOrName, @RequestParam(required = false) String processName) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(status)) {
			param.put("equipStatus", status.trim().split(","));
		}
		if (StringUtils.isNotEmpty(codeOrName)) {
			param.put("codeOrName", "%" + codeOrName + "%");
		}
		if (StringUtils.isNotEmpty(processName)) {
			param.put("processName", processName);
		}
		param.put("orgCode", equipInfoService.getOrgCode());
		return equipInfoService.getEquipLine(param);
	}

}
