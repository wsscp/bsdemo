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

import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.pro.model.ProcessInOutWip;
import cc.oit.bsmes.pro.service.ProcessInOutWipService;

/**
 * @ClassName:   ProcessInOutWipController
 * @Description: TODO(订单工艺投入产出)
 * @author:      DingXintao
 * @date:        2016年5月20日 上午10:27:50
 */
@Controller
@RequestMapping("/pro/processWip/inOut")
public class ProcessInOutWipController {

	@Resource
	private ProcessInOutWipService processInOutWipService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<ProcessInOutWip> list(@RequestParam String processId, @RequestParam int page, @RequestParam(required = false) String sort,
			@RequestParam int start, @RequestParam int limit) {
		return processInOutWipService.getByProcessId(processId);
	}

	/**
	 * @Title: insertProcessInOut
	 * @Description: TODO(更新投入产出信息)
	 * @param: processInOutWip 投入产出更新参数: salesOrderItemId 客户订单id, oldMatCode 旧物料编码, oldMatName 旧物料名称
	 * @return: UpdateResult
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public UpdateResult insertProcessInOut(@RequestBody ProcessInOutWip processInOutWip) {
		return processInOutWipService.insertProcessInOut(processInOutWip);
	}

	/**
	 * @Title: updateProcessInOut
	 * @Description: TODO(更新投入产出信息)
	 * @param: processInOutWip 投入产出更新参数: salesOrderItemId 客户订单id, oldMatCode 旧物料编码, oldMatName 旧物料名称
	 * @return: UpdateResult
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public UpdateResult updateProcessInOut(@PathVariable String id, @RequestBody ProcessInOutWip processInOutWip) {
		return processInOutWipService.updateProcessInOut(processInOutWip);
	}

}
