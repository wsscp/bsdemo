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

import cc.oit.bsmes.common.mybatis.Sort;
import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.pro.model.EquipList;
import cc.oit.bsmes.pro.service.EquipListService;

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
@RequestMapping("/pro/process/equipList")
public class ProcessEquipListController {

	@Resource
	private EquipListService equipListService;

	@ResponseBody
	@RequestMapping(value = "queryProcessEquipList", method = RequestMethod.GET)
	public TableView queryProcessEquipList(@RequestParam String processId, @RequestParam int page,
			@RequestParam(required = false) String sort, @RequestParam int start, @RequestParam int limit) {
		TableView tableView = new TableView();
		EquipList findParams = new EquipList();
		findParams.setProcessId(processId);
		List<EquipList> rows = equipListService.getByProcessId(processId, start, limit,
				JSONArray.parseArray(sort, Sort.class));
		;
		int total = equipListService.count(findParams);
		tableView.setRows(rows);
		tableView.setTotal(total);
		return tableView;
	}

	@ResponseBody
	@RequestMapping(value = "insertProcessEquipList", method = RequestMethod.POST)
	public UpdateResult insertProcessEquipList(@RequestBody EquipList equipList) {
		equipListService.insert(equipList);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(equipList);
		return updateResult;
	}

	@ResponseBody
	@RequestMapping(value = "updateProcessEquipList/{id}", method = RequestMethod.PUT)
	public UpdateResult updateProcessEquipList(@PathVariable String id ,@RequestBody EquipList equipList) {
		UpdateResult updateResult = new UpdateResult();
		equipListService.update(equipList);
		updateResult.addResult(equipList);
		return updateResult;
	}

}
