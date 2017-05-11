package cc.oit.bsmes.pro.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WriteException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.action.BaseController;
import cc.oit.bsmes.common.constants.DataStatus;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.mybatis.complexQuery.CustomQueryParam;
import cc.oit.bsmes.common.mybatis.complexQuery.WithValueQueryParam;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.pro.model.ProcessQcBz;
import cc.oit.bsmes.pro.model.ProductProcessBz;
import cc.oit.bsmes.pro.service.ProcessQcBzService;
import cc.oit.bsmes.pro.service.ProductProcessBzService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author 陈翔
 */
@Controller
@RequestMapping("/pro/processBz")
public class ProductProcessBzController {
	
	@Resource
	private ProductProcessBzService productProcessBzService;
	@Resource
	private ProcessQcBzService processQcBzService;
	
	@RequestMapping(produces = "text/html")
	public String index(HttpServletRequest request, Model model) {
		String craftsId = request.getParameter("craftsId");
		model.addAttribute("moduleName", "pro");
		model.addAttribute("submoduleName", "process");
		model.addAttribute("craftsId", craftsId);
		return "pro.process";
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public TableView list(HttpServletRequest request, ProductProcessBz findParams, @RequestParam String sort,
			@RequestParam int page, @RequestParam int start, @RequestParam int limit) {
		List<Sort> sortList = JSONArray.parseArray(sort, Sort.class);
		for(Sort s :sortList){
			if("seq".equals(s.getProperty())){
				s.setProperty("to_char(seq)");
			}
		}
		List<ProductProcessBz> result = productProcessBzService.findByCraftsIdAndParam(findParams, start, limit,
				sortList);
		int total = productProcessBzService.countByCraftsIdAndParam(findParams);

		TableView tableView = new TableView();
		tableView.setRows(result);
		tableView.setTotal(total);
		return tableView;
	}
	
	@ResponseBody
	@RequestMapping(value = "queryProcessQc")
	public TableView queryProcessQc(HttpServletRequest request, @RequestParam String processId)
			throws UnsupportedEncodingException, ParseException {
		TableView tableView = new TableView();
		// 设置findParams属性
		List<CustomQueryParam> queryParams = new ArrayList<CustomQueryParam>();
		queryParams.add(new WithValueQueryParam("processId", "=", processId));
		queryParams.add(new WithValueQueryParam("dataStatus", "=", DataStatus.NORMAL));
		String checkType = request.getParameter("checkType");
		if (StringUtils.isNotBlank(checkType)) {
			queryParams.add(new WithValueQueryParam(checkType, "=", "1"));
		}

		// 根据filter设置findParams属性
		BaseController.addFilterQueryParams(request, queryParams);
		List<ProcessQcBz> rows = processQcBzService.query(queryParams);
		// int total = processQcService.countQuery(queryParams);
		tableView.setRows(rows);
		// tableView.setTotal(total);
		return tableView;
	}
	
	@ResponseBody
	@RequestMapping(value = "queryProcessQc/{id}", method = RequestMethod.PUT)
	public UpdateResult update(@PathVariable String id, @RequestBody String jsonText) {
		ProcessQcBz qc = JSON.parseObject(jsonText, ProcessQcBz.class);
		processQcBzService.update(qc);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(qc);
		return updateResult;
	}
	
	@RequestMapping(value = "/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName,
			@RequestParam String params, @RequestParam(required = false) String queryParams) throws IOException,
			WriteException, InvocationTargetException, IllegalAccessException, ClassNotFoundException,
			NoSuchMethodException {
		JSONObject queryFilter = JSONObject.parseObject(queryParams);
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
		productProcessBzService.export(os, sheetName, columns, queryFilter);
		os.close();
	}
	
	/**
	 * 工艺ID获取工序明细
	 * 
	 * @author DingXintao
	 * @param craftsId 工艺ID
	 * @return
	 * */
	@ResponseBody
	@RequestMapping(value = "getByCraftsId", method = RequestMethod.GET)
	public List<ProductProcessBz> process(@RequestParam String craftsId) {
		List<ProductProcessBz> list = productProcessBzService.getByProductCraftsIdAsc(craftsId);
		return list;
	}
}