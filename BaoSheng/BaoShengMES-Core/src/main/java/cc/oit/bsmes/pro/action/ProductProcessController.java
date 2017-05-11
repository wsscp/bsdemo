/*
 * Copyright by Orientech and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Orientech from
 *
 *      http://www.orientech.cc/
 *
 */
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
import cc.oit.bsmes.pla.service.OrderProcessPRService;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProcessInformationService;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessQcValueService;
import cc.oit.bsmes.pro.service.ProductProcessService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * TODO(描述类的职责)
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-3-11 下午5:03:51
 * @since
 * @version
 */
@Controller
@RequestMapping("/pro/process")
public class ProductProcessController {
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private ProcessInformationService processInformationService;
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private ProcessQcValueService processQcValueService;
	@Resource
	private OrderProcessPRService orderProcessPRService;

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
	public TableView list(HttpServletRequest request, ProductProcess findParams, @RequestParam String sort, @RequestParam int page,
			@RequestParam int start, @RequestParam int limit) {
		List<ProductProcess> result = productProcessService.findByCraftsIdAndParam(findParams, start, limit, JSONArray.parseArray(sort, Sort.class));
		int total = productProcessService.countByCraftsIdAndParam(findParams);

		TableView tableView = new TableView();
		tableView.setRows(result);
		tableView.setTotal(total);
		return tableView;
	}

	@ResponseBody
	@RequestMapping(value = "queryProcessQc")
	public TableView queryProcessQc(HttpServletRequest request, @RequestParam String processId, @RequestParam(required = false) String checkType1,
			@RequestParam String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) throws UnsupportedEncodingException,
			ParseException {
		TableView tableView = new TableView();
		// 设置findParams属性
		List<CustomQueryParam> queryParams = new ArrayList<CustomQueryParam>();
		queryParams.add(new WithValueQueryParam("processId", "=", processId));
		queryParams.add(new WithValueQueryParam("dataStatus", "=", DataStatus.NORMAL));
		if (StringUtils.isNotBlank(checkType1)) {
			queryParams.add(new WithValueQueryParam(checkType1, "=", "1"));
		}

		// 根据filter设置findParams属性
		BaseController.addFilterQueryParams(request, queryParams);
		List<ProcessQc> rows = processQcService.query(queryParams, start, limit, JSONArray.parseArray(sort, Sort.class));
		int total = processQcService.countQuery(queryParams);
		tableView.setRows(rows);
		tableView.setTotal(total);
		return tableView;

	}

	@ResponseBody
	@RequestMapping(value = "queryProcessQc", method = RequestMethod.POST)
	public UpdateResult insertQc(@RequestBody String jsonText) {
		ProcessQc qc = JSON.parseObject(jsonText, ProcessQc.class);
		processQcService.insert(qc);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(qc);
		return updateResult;
	}

	
	/**
	 * @Title:       updateQc
	 * @Description: TODO(修改qc参数)
	 * @param:       id qc主键
	 * @param:       qc qc请求参数
	 * @param:       salesOrderItemId 特殊工艺订单id
	 * @return:      UpdateResult   
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "queryProcessQc/{id}", method = RequestMethod.PUT)
	public UpdateResult updateQc(@PathVariable String id, @RequestBody ProcessQc qc) {
		processQcService.update(qc);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(qc);

		// 如果是特殊工艺的话，将要新增特殊工艺的变更记录
		if (StringUtils.isNotEmpty(qc.getSalesOrderItemId())) {
			orderProcessPRService.insertSpencial(qc.getSalesOrderItemId(), qc.getProcessId(), "质量参数", qc.getCheckItemCode(), qc.getCheckItemName(),
					qc.getItemTargetValue(), null, null, null);
		}

		return updateResult;
	}

	@RequestMapping(value = "/export/{fileName}", method = RequestMethod.POST)
	@ResponseBody
	public void export(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName, @RequestParam String params,
			@RequestParam(required = false) String queryParams) throws IOException, WriteException, InvocationTargetException,
			IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
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
		productProcessService.export(os, sheetName, columns, queryFilter);
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
	public List<ProductProcess> process(@RequestParam String craftsId) {
		List<ProductProcess> list = productProcessService.getByProductCraftsIdAsc(craftsId);
		return list;
	}

}
