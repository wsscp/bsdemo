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
package cc.oit.bsmes.wip.action;

import cc.oit.bsmes.common.constants.WebConstants;
import cc.oit.bsmes.common.exception.IllegalArgumentException;
import cc.oit.bsmes.common.util.CamelCaseUtils;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.interfaceWWAc.service.DataAcquisitionService;
import cc.oit.bsmes.pro.model.ProcessQc;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import cc.oit.bsmes.pro.service.ProcessQcService;
import cc.oit.bsmes.pro.service.ProcessReceiptService;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.model.Receipt;
import cc.oit.bsmes.wip.service.ReceiptService;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.ValueFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.*;

/**
 * <p style="display:none">
 * modifyRecord
 * </p>
 * 
 * @author QiuYangjun
 * @date 2014-2-24 下午5:48:15
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/processReceiptTrace")
public class ProcessReceiptTraceController {
	@Resource
	private ProcessReceiptService processReceiptService;
	@Resource
	private ProductProcessService productProcessService;
	@Resource
	private ProcessQcService processQcService;
	@Resource
	private DataAcquisitionService dataAcquisitionService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private ReceiptService receiptService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "processReceiptTrace");
		return "wip.processReceiptTrace";
	}

	/**
	 * 工艺参数报表： 获取设备的工艺参数实时数据
	 * 
	 * @param equipCode 设备编码
	 * @param processCode 工序编码
	 * */
	@RequestMapping
	@ResponseBody
	public List<ProcessReceipt> list(HttpServletRequest request, Model model, @RequestParam String equipCode,
			@RequestParam(required = false) String processCode) {
		if (StringUtils.isBlank(equipCode)) {
			throw new IllegalArgumentException();
		}
		// 查询
		List<ProcessReceipt> result = processReceiptService.getByEquipCodeAndProcessCode(equipCode, processCode);
		return result;
	}

	@RequestMapping(value = "/processQcTrace", method = RequestMethod.GET)
	@ResponseBody
	public List<ProcessQc> processQcTracelist(@RequestParam String equipCode, @RequestParam String processCode) {

		if (StringUtils.isBlank(equipCode)) {
			throw new IllegalArgumentException();
		}
		if (StringUtils.isBlank(processCode)) {
			throw new IllegalArgumentException();
		}
		// 查询
		List<ProcessQc> result = processQcService.traceByEquipCodeAndProcessCode(equipCode, processCode);
		return result;
	}

	@RequestMapping(value = "historyTrace", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<Object[]>> getHistoryTrace(@RequestParam String equipCode,
			@RequestParam String receiptCode, @RequestParam String startTime, @RequestParam String endTime) {
		Map<String, List<Object[]>> result = receiptService.getHistoryTrace(equipCode, receiptCode, startTime, endTime);
		return result;
	}

	@RequestMapping(value = "realReceiptChart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> realChart(@RequestParam String equipCode,
			@RequestParam(required = false) String processCode, @RequestParam String receiptCode,
			@RequestParam String type) {
		Map<String, Object> result = receiptService.realReceiptChart(equipCode, processCode, receiptCode, type);
		return result;
	}

	@RequestMapping(value = "realReceipt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> realReceipt(@RequestParam String equipCode, @RequestParam String receiptCode) {
		Receipt receipt = new Receipt();
		receipt.setEquipCode(equipCode);
		receipt.setReceiptCode(receiptCode);
		dataAcquisitionService.queryLiveReceiptByCodes(receipt);
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(receipt.getDaValue())) {
			// map.put("date", new Date());
			map.put("date", receipt.getCreateTime());
			map.put("realData", receipt.getDaValue());
			return map;
		} else {
			return null;
		}
	}
	
	@RequestMapping(value = "getServerTime", method = RequestMethod.POST)
	@ResponseBody
	public Date getServerTime(){
		return new Date();             
	}

	@RequestMapping(value = "/process/{equipCode}/{query}", method = RequestMethod.GET)
	@ResponseBody
	public List findProcessByEquipCode(@PathVariable String equipCode, @PathVariable String query) {
		if (StringUtils.equalsIgnoreCase(WebConstants.ROOT_ID, equipCode)) {
			equipCode = "";
		}
		if (StringUtils.equalsIgnoreCase(WebConstants.ROOT_ID, query)) {
			query = "";
		} else {
			query = "%" + query + "%";
		}
		List<Map<String, Object>> list = productProcessService.findByEquipCode(equipCode, query);

		NameFilter filter = new NameFilter() {
			@Override
			public String process(Object object, String name, Object value) {
				return CamelCaseUtils.toCamelCase(name);
			}
		};
		ValueFilter dateFilter = new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				if (value instanceof oracle.sql.TIMESTAMP) {

					try {
						return DateUtils
								.convert(((oracle.sql.TIMESTAMP) value).dateValue(), DateUtils.DATE_TIME_FORMAT);
					} catch (SQLException e) {

					}
				}
				return value;
			}

		};

		SerializeWriter out = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(out);
		serializer.getNameFilters().add(filter);
		serializer.getValueFilters().add(dateFilter);
		serializer.write(list);
		String text = out.toString();

		List result = JSONObject.parseArray(text);

		return result;
	}

	@RequestMapping(value = "/{equipCode}/{type}/{receiptCode}", produces = "text/html", method = RequestMethod.GET)
	public void single(HttpServletRequest request, HttpServletResponse response, @PathVariable String equipCode,
			@PathVariable String type, @PathVariable String receiptCode) {
		Date startTime = DateUtils.convert(request.getParameter("startTime"));
		Date endTime = DateUtils.convert(request.getParameter("endTime"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("equipCode", equipCode);
		map.put("processCode", " ");
		map.put("type", type);
		map.put("receiptCode", receiptCode);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		Receipt receipt = receiptService.getReceiptName(map);
		String sheetName = receipt.getReceiptName();
		try {
			String fileName = URLEncoder.encode(equipCode, "UTF8") + "_" + URLEncoder.encode(sheetName, "UTF8")
					+ ".xls";
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
			receiptService.exportToXls(os, sheetName, map);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
