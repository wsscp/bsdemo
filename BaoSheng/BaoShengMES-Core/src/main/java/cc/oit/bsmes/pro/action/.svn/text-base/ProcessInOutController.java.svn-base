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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.inv.model.Mat;
import cc.oit.bsmes.inv.service.MatService;
import cc.oit.bsmes.pla.service.OrderProcessPRService;
import cc.oit.bsmes.pro.model.ProcessInOut;
import cc.oit.bsmes.pro.service.ProcessInOutService;

import com.alibaba.fastjson.JSONArray;

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
@RequestMapping("/pro/process/inOut")
public class ProcessInOutController {

	@Resource
	private ProcessInOutService processInOutService;
	@Resource
	private MatService matService;
	@Resource
	private OrderProcessPRService orderProcessPRService;

	/**
	 * @Title: list默认查询
	 * @Description: TODO(查询工序下面的投入产出)
	 * @param: processId 工序ID
	 * @return: List<ProcessInOut>
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<ProcessInOut> list(@RequestParam String processId, @RequestParam int page, @RequestParam(required = false) String sort,
			@RequestParam int start, @RequestParam int limit) {
		return processInOutService.getByProcessId(processId);
	}

	/**
	 * @Title: insertProcessInOut
	 * @Description: TODO(更新投入产出信息)
	 * @param: processInOut 投入产出更新参数: oldMatCode 旧物料编码, oldMatName 旧物料名称
	 * @return: UpdateResult
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public UpdateResult insertProcessInOut(@RequestBody ProcessInOut processInOut) {
		processInOutService.insert(processInOut);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(processInOut);
		return updateResult;
	}

	/**
	 * @Title: updateProcessInOut
	 * @Description: TODO(更新投入产出信息)
	 * @param: processInOut 投入产出更新参数: oldMatCode 旧物料编码, oldMatName 旧物料名称
	 * @return: UpdateResult
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public UpdateResult updateProcessInOut(@PathVariable String id, @RequestBody ProcessInOut processInOut) {
		processInOutService.update(processInOut);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(processInOut);
		return updateResult;
	}
	
}
