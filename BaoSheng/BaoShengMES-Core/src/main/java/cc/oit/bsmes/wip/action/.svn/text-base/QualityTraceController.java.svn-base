package cc.oit.bsmes.wip.action;

import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.bas.service.EmployeeService;
import cc.oit.bsmes.common.constants.QADetectType;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.pro.model.ProcessQcValue;
import cc.oit.bsmes.pro.service.ProcessQcValueService;

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

@Controller
@RequestMapping("/wip/qualityTrace")
public class QualityTraceController {

	@Resource
	private ProcessQcValueService processQcValueService;
	@Resource
	private EmployeeService employeeService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "qualityTrace");
		return "wip.qualityTrace";
	}

	@ResponseBody
	@RequestMapping
	public TableView list(HttpServletRequest request, @ModelAttribute ProcessQcValue findParams,
			@RequestParam int page, @RequestParam int start, @RequestParam int limit,
			@RequestParam(required = false) String sort) {
		TableView tableView = new TableView();
		List<ProcessQcValue> rows = processQcValueService.find(findParams, start, limit,
				JSONArray.parseArray(sort, Sort.class));
		int total = processQcValueService.count(findParams);
		tableView.setRows(rows);
		tableView.setTotal(total);
		return tableView;
	}

	@RequestMapping(value = "/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName,
			@RequestParam String params, @RequestParam(required = false) String queryParams) throws IOException,
			WriteException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
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
		JSONObject queryFilter = JSONObject.parseObject(queryParams);
		processQcValueService.export(os, sheetName, columns, queryFilter);
		os.close();
	}

	@ResponseBody
	@RequestMapping(value = "user/{query}", method = RequestMethod.GET)
	public List<Employee> user(@PathVariable String query) {
		return employeeService.getEmployee(query);
	}
}
