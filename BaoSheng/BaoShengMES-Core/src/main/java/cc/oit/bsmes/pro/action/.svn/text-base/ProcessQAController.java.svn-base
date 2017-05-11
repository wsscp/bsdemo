package cc.oit.bsmes.pro.action;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.util.WebContextUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.pro.model.ProcessQcValue;
import cc.oit.bsmes.pro.service.ProcessQcValueService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jxl.write.WriteException;
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
@RequestMapping("/pro/processQA")
public class ProcessQAController {

	@Resource
	private ProcessQcValueService processQcValueService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "pro");
		model.addAttribute("submoduleName", "processQA");
		return "pro.processQA";
	}

	@RequestMapping
	@ResponseBody
	public TableView list(HttpServletRequest request, @ModelAttribute ProcessQcValue findParams,
			@RequestParam int page, @RequestParam int start, @RequestParam int limit,
			@RequestParam(required = false) String sort) {
		TableView tableView = new TableView();
//		List<ProcessQcValue> rows = processQcValueService.find(findParams, start, limit,
//				JSONArray.parseArray(sort, Sort.class));
		List<ProcessQcValue> rows = processQcValueService.getQaList(findParams, start, limit,
				JSONArray.parseArray(sort, Sort.class));
		int total = processQcValueService.countQaList(findParams);
		tableView.setRows(rows);
		tableView.setTotal(total);
		return tableView;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
	@ResponseBody
	public UpdateResult update(@RequestBody String jsonText) {
		UpdateResult updateResult = new UpdateResult();
		ProcessQcValue qcValue = JSON.parseObject(jsonText, ProcessQcValue.class);
		processQcValueService.update(qcValue);
		updateResult.addResult(qcValue);
		return updateResult;
	}

	@ResponseBody
	@RequestMapping(value = "createQAAlarm", method = RequestMethod.POST)
	public void createQAAlarm(@RequestParam String processQcValue) {
		ProcessQcValue qcValue = JSON.parseObject(processQcValue, ProcessQcValue.class);
		processQcValueService.generateQAAlarm(qcValue);
	}

	@RequestMapping(value = "/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject export(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName,
			@RequestParam String params, @RequestParam(required = false) String queryParams) throws IOException,
			WriteException, InvocationTargetException, IllegalAccessException, ClassNotFoundException,
			NoSuchMethodException {
		JSONObject queryFilter = JSONObject.parseObject(queryParams);
		int countExportRows = processQcValueService.countForExport(queryFilter);
		Integer maxExportLine = Integer.parseInt(WebContextUtils.getPropValue(WebConstants.MAX_EXPORT_LINE));
		if (countExportRows > maxExportLine) {
			JSONObject result = new JSONObject();
			result.put("msg", "export rows is than maxExportLine");
			return result;
		}
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
		processQcValueService.export(os, sheetName, columns, queryFilter);
		os.close();
		return null;
	}
}
