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

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.constants.EquipType;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.pro.model.ProcessReceipt;
import cc.oit.bsmes.pro.service.ProcessReceiptService;
import cc.oit.bsmes.wip.service.WorkOrderService;

import com.alibaba.fastjson.JSON;

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
@RequestMapping("/pro/process/receipt")
public class ProcessReceiptController {

	@Resource
	private ProcessReceiptService processReceiptService;
	@Resource
	private EquipInfoService equipInfoService;
	@Resource
	private WorkOrderService workOrderService;

	@ResponseBody
	@RequestMapping(value = "queryProcessReceiptList", method = RequestMethod.GET)
	public TableView queryProcessReceiptList(@RequestParam String eqipListId, @RequestParam int page,
			@RequestParam int start, @RequestParam int limit) {
		TableView tableView = new TableView();
		ProcessReceipt findParams = new ProcessReceipt();
		findParams.setEqipListId(eqipListId);
		List<ProcessReceipt> rows = processReceiptService.getByEqipListId(eqipListId, start, limit);
		;
		int total = processReceiptService.count(findParams);
		tableView.setRows(rows);
		tableView.setTotal(total);
		return tableView;
	}

	@ResponseBody
	@RequestMapping(value = "updateProcessReceipt/{id}", method = RequestMethod.PUT)
	public UpdateResult updateProcessReceipt(@PathVariable String id, @RequestBody String jsonText) {
		ProcessReceipt receipt = JSON.parseObject(jsonText, ProcessReceipt.class);
		processReceiptService.update(receipt);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(receipt);
		return updateResult;
	}
}
