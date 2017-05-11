package cc.oit.bsmes.wip.action;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.SemiFinishedProducts;
import cc.oit.bsmes.wip.service.SemiFinishedProductsService;

import com.alibaba.fastjson.JSONArray;

/**
 * 半成品使用情况查询
 * @author 王国华
 * @date 2016-12-26 15:06:14
 *
 */
@Controller
@RequestMapping("/wip/SemiFinishedProductsUsing")
public class SemiFinishedProductsUsingController {

	@Resource
	private SemiFinishedProductsService semiFinishedProductsService;

	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "semiFinishedProductsUsing");
		return "wip.semiFinishedProductsUsing";
	}

	@RequestMapping
	@ResponseBody
	public TableView list(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Integer start, @RequestParam Integer limit, @RequestParam String sort) {
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		Map<String,Object> findParams=new HashMap<String,Object>();
		Enumeration em=request.getParameterNames();
		while(em.hasMoreElements()){
			String name=(String) em.nextElement();
			if(StringUtils.isNotEmpty(request.getParameter(name))){
				findParams.put(name, request.getParameter(name));
			}
		}
		findParams.put("start",start);
		findParams.put("end",start+limit);
		findParams.put("sort", sortArray);
		List<SemiFinishedProducts> lists=semiFinishedProductsService.useProcedureUsing(findParams);
		int count=semiFinishedProductsService.countListUsing(findParams);
		TableView tableView = new TableView();
		tableView.setRows(lists);
		tableView.setTotal(count);
		return tableView;
	}
	
	@ResponseBody
	@RequestMapping(value = "getMatQuan/{idArray}",method = RequestMethod.GET)
	public List<Map<String,String>> getMatQuan(@PathVariable String idArray){
		Map<String, Object> params = new HashMap<String, Object>();
		if(idArray.equals("-1")){
			return semiFinishedProductsService.getAllMatQuan();
		}else{
			params.put("idArr", Arrays.asList(idArray.split(",")));
			return semiFinishedProductsService.getMatQuan(params);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "insertAllMat")
	public void insertAllMat(HttpServletRequest request){
		List<String> orderArr = semiFinishedProductsService.getAllWorkOrder();
		for(int i=0;i<orderArr.size();i++){
			semiFinishedProductsService.calculateWorkOrderCost(orderArr.get(i));
		}
	}
	
	@RequestMapping(value = "/{processName}", produces = "text/html", method = RequestMethod.GET)
	public void exportToXls(HttpServletRequest request, HttpServletResponse response, @PathVariable String processName){
		String sheetName = "半成品盘点";
		Map<String, Object> map = new HashMap<String, Object>();
		if (processName != null && !processName.isEmpty()) {
			map.put("processName", processName);
		}
		try {
			String fileName = URLEncoder.encode("半成品盘点表", "UTF-8") + ".xls";
			if (processName != null && !processName.isEmpty()) {
				fileName = URLEncoder.encode("半成品盘点表", "UTF-8") + "_" + URLEncoder.encode(processName, "UTF-8") + ".xls";
			}
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
			semiFinishedProductsService.exportToXls(os, sheetName, map);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getProcessName", method = RequestMethod.GET)
	public List<SemiFinishedProducts> getProcessName(HttpServletRequest request){
		return semiFinishedProductsService.getProcessName();
	}
	
	@ResponseBody
	@RequestMapping(value="/searchProcessName")	
	public List<SemiFinishedProducts> searchProcessName(){
		return semiFinishedProductsService.searchProcessName();
	}
	
	@ResponseBody
	@RequestMapping(value="/searchContractNo/{value}")
	public List<SemiFinishedProducts> searchContractNo(@PathVariable("value") String contractNo){
		return semiFinishedProductsService.searchContractNo(contractNo);
	}
}
