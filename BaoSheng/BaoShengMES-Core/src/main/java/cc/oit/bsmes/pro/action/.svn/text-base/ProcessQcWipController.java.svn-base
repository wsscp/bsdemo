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

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.action.BaseController;
import cc.oit.bsmes.common.mybatis.complexQuery.CustomQueryParam;
import cc.oit.bsmes.common.mybatis.complexQuery.WithValueQueryParam;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.pla.service.OrderProcessPRService;
import cc.oit.bsmes.pro.model.ProcessQcWip;
import cc.oit.bsmes.pro.service.ProcessQcWipService;

/**
 * @ClassName:   ProcessQcWipController
 * @Description: TODO(订单工艺qc)
 * @author:      DingXintao
 * @date:        2016年5月20日 上午10:28:18
 */
@Controller
@RequestMapping("/pro/processWip/qc")
public class ProcessQcWipController {

	@Resource
	private ProcessQcWipService processQcWipService;
	@Resource
	private OrderProcessPRService orderProcessPRService;

	@ResponseBody
	@RequestMapping
	public TableView list(HttpServletRequest request, @RequestParam String processId, @RequestParam(required = false) String checkType1,
			@RequestParam String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) throws UnsupportedEncodingException,
			ParseException {
		TableView tableView = new TableView();
		// 设置findParams属性
		List<CustomQueryParam> queryParams = new ArrayList<CustomQueryParam>();
		queryParams.add(new WithValueQueryParam("processId", "=", processId));
		//queryParams.add(new WithValueQueryParam("dataStatus", "=", DataStatus.NORMAL));
		if (StringUtils.isNotBlank(checkType1)) {
			queryParams.add(new WithValueQueryParam(checkType1, "=", "1"));
		}

		// 根据filter设置findParams属性
		BaseController.addFilterQueryParams(request, queryParams);
		List<ProcessQcWip> rows = processQcWipService.query(queryParams);
				//, start, limit, JSONArray.parseArray(sort, Sort.class));
		int total = processQcWipService.countQuery(queryParams);
		tableView.setRows(rows);
		tableView.setTotal(total);
		return tableView;

	}
	
	/**
	 * @Title: insertQc
	 * @Description: TODO(修改qc参数)
	 * @param: qc qc请求参数
	 * @return: UpdateResult
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public UpdateResult insertQc(@RequestBody ProcessQcWip qc) {
		processQcWipService.insert(qc);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(qc);
		
		// 如果是特殊工艺的话，将要新增特殊工艺的变更记录
		if (StringUtils.isNotEmpty(qc.getSalesOrderItemId())) {
			orderProcessPRService.insertSpencial(qc.getSalesOrderItemId(), qc.getProcessId(), "质量参数", qc.getCheckItemCode(), qc.getCheckItemName(),
					qc.getItemTargetValue(), null, null, null);
		}
		
		return updateResult;
	}

	/**
	 * @Title: updateQc
	 * @Description: TODO(修改qc参数)
	 * @param: id qc主键
	 * @param: qc qc请求参数
	 * @param: salesOrderItemId 特殊工艺订单id
	 * @return: UpdateResult
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public UpdateResult update(@PathVariable String id, @RequestBody ProcessQcWip qc) {
		processQcWipService.update(qc);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(qc);

		// 如果是特殊工艺的话，将要新增特殊工艺的变更记录
		if (StringUtils.isNotEmpty(qc.getSalesOrderItemId())) {
			orderProcessPRService.insertSpencial(qc.getSalesOrderItemId(), qc.getProcessId(), "质量参数", qc.getCheckItemCode(), qc.getCheckItemName(),
					qc.getItemTargetValue(), null, null, null);
		}

		return updateResult;
	}

}
