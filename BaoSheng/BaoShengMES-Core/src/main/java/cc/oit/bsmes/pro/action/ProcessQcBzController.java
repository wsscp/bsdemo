package cc.oit.bsmes.pro.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import cc.oit.bsmes.common.mybatis.complexQuery.CustomQueryParam;
import cc.oit.bsmes.common.mybatis.complexQuery.WithValueQueryParam;
import cc.oit.bsmes.common.view.UpdateResult;
import cc.oit.bsmes.pro.model.ProcessQcBz;
import cc.oit.bsmes.pro.service.ProcessQcBzService;

/**
 * @author 陈翔
 */
@Controller
@RequestMapping("/pro/processBz/qc")
public class ProcessQcBzController {
	
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
	
	@ResponseBody
	@RequestMapping
	public List<ProcessQcBz> list(HttpServletRequest request, @RequestParam String processId, @RequestParam(required = false) String checkType1,
			@RequestParam String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) throws UnsupportedEncodingException, ParseException {
		// TableView tableView = new TableView();
		// 设置findParams属性
		List<CustomQueryParam> queryParams = new ArrayList<CustomQueryParam>();
		queryParams.add(new WithValueQueryParam("processId", "=", processId));
		// queryParams.add(new WithValueQueryParam("dataStatus", "=",
		// DataStatus.NORMAL));
		if (StringUtils.isNotBlank(checkType1)) {
			queryParams.add(new WithValueQueryParam(checkType1, "=", "1"));
		}

		// 根据filter设置findParams属性
		BaseController.addFilterQueryParams(request, queryParams);
		return processQcBzService.query(queryParams);
		// , start, limit, JSONArray.parseArray(sort, Sort.class));
		// int total = processQcService.countQuery(queryParams);
		// tableView.setRows(rows);
		// tableView.setTotal(total);
		// return tableView;
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
	public UpdateResult insertQc(@RequestBody ProcessQcBz qc) {
		processQcBzService.insert(qc);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(qc);

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
	public UpdateResult update(@PathVariable String id, @RequestBody ProcessQcBz qc) {
		processQcBzService.update(qc);
		UpdateResult updateResult = new UpdateResult();
		updateResult.addResult(qc);

		return updateResult;
	}
	
}