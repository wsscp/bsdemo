package cc.oit.bsmes.wip.action;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.SpecialCraftsTrace;
import cc.oit.bsmes.wip.service.SpecialCraftsTraceService;

import com.alibaba.fastjson.JSONArray;


/** 
* Title: 
* Description: 
* @author rongyidong
* @date 2016年4月7日 下午5:14:27
*/
@Controller
@RequestMapping("/wip/specialCraftsTrace")
public class SpecialCraftsTraceController {
	
	@Resource
	private SpecialCraftsTraceService specialCraftsTraceService;
	 

	@RequestMapping(produces = "text/html")
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "specialCraftsTrace");
		return "wip.specialCraftsTrace";
	}
	
	
	
	@ResponseBody
	@RequestMapping
	public TableView list(HttpServletRequest request, @RequestParam(required = false) Integer start,
			@RequestParam(required = false) Integer limit, @RequestParam(required = false) String sort){
		List<Sort> sortArray = JSONArray.parseArray(sort, Sort.class);
		Map<String, Object> findParams = new HashMap<String, Object>();
		Enumeration names=request.getParameterNames();
		while(names.hasMoreElements()){
			String name=(String) names.nextElement();
			if(name.equals("start")||name.equals("limit")){
				continue;
			}
			if(!request.getParameter(name).isEmpty()){
				List<String> value=Arrays.asList(request.getParameterValues(name));
				findParams.put(name, value);
			}					
		}
		findParams.put("start",start);
		findParams.put("end",start+limit);
		List<SpecialCraftsTrace> list = specialCraftsTraceService.getAllHistoryTrace(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(specialCraftsTraceService.countHistoryTrace(findParams));
		return tableView;
	} 
	
	@ResponseBody
	@RequestMapping(value="/searchContractNo/{value}")
	public List<SpecialCraftsTrace> searchContractNo(@PathVariable("value") String contractNo){
		return specialCraftsTraceService.searchContractNo(contractNo);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/searchProductType/{value}")
	public List<Map<String,String>> searchProductType(@PathVariable("value") String productType){
		System.out.println(specialCraftsTraceService.searchProductType(productType).size());
		return specialCraftsTraceService.searchProductType(productType);
	}
		
	@ResponseBody
	@RequestMapping(value="/searchProductSpec/{value}")
	public List<Map<String,String>> searchProductSpec(@PathVariable("value") String productSpec,
			@RequestParam(required=false) String productType){
		Map<String,String> param=new HashMap<String,String>();
		param.put("productSpec", productSpec);
		param.put("productType", "");
		if(!StringUtils.isEmpty(productType)){
			param.put("productType", productType);
		}
		return specialCraftsTraceService.searchProductSpec(param);
	}

}
