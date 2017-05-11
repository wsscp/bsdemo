package cc.oit.bsmes.wip.action;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.pla.model.Product;
import cc.oit.bsmes.pla.service.ProductService;
import cc.oit.bsmes.wip.service.ProcessBalanceChartService;

/**
 * 
 * @author leiwei
 * @date 2014-01-06 17:20:40
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/processBalanceChart")
public class ProcessBalanceChartController {

	@Resource 
	private ProductService productService;
	@Resource 
	private ProcessBalanceChartService processBalanceChartService;
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "processBalanceChart");
		return "wip.processBalanceChart";
    }
	
	@RequestMapping(value="product",method=RequestMethod.GET)	
	@ResponseBody
	public List<Product> product(@RequestParam String query){
		if(StringUtils.equals(query, "1_")){
			query=null;
		}
		List<Product> products=productService.getByProductCodeOrName(query);
		return products;
	}
	
	@RequestMapping(value="processChart",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object[]> processChart(@RequestParam String productCode,@RequestParam String startTime,@RequestParam String endTime){
		Date startDate=DateUtils.convert(startTime);
		Date endDate=DateUtils.convert(endTime);
		if(Integer.parseInt(DateUtils.convert(endDate,DateUtils.DATE_SHORT_FORMAT))>=Integer.parseInt(DateUtils.convert(new Date(),DateUtils.DATE_SHORT_FORMAT))){
			endDate=new Date();
		}else{
			endDate=DateUtils.convert(DateUtils.convert(endDate,DateUtils.DATE_FORMAT)+" "+DateUtils.DAYTIME_END, DateUtils.DATE_TIME_FORMAT);
		}
		return processBalanceChartService.getProcessChart(productCode,startDate,endDate);
	}
	
}
