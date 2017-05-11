package cc.oit.bsmes.pla.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.pla.model.OrderStatistics;
import cc.oit.bsmes.pla.service.OrderStatisticsService;
/**
 * 
 * @author 吕桐生
 * 生产统计
 * 2017.1.3
 *
 */
@Controller
@RequestMapping("/pla/orderStatistics")
public class OrderStatisticsController {
	@Resource
	private OrderStatisticsService orderStatisticsService;
	
	@RequestMapping(produces = "text/html")
	public String index(Model model) {
		model.addAttribute("moduleName", "pla");
		model.addAttribute("submoduleName", "orderStatistics");
		return "pla.orderStatistics";
	}

	@RequestMapping
	@ResponseBody
	public TableView list(@ModelAttribute OrderStatistics param
			) {
		List<OrderStatistics> list = orderStatisticsService.getOrderStatisticsList(param);
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(list.size());
		return tableView;
		
	}


	@RequestMapping(value="/queryData",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject queryData(@ModelAttribute OrderStatistics param) {
		JSONObject jsonObject = new JSONObject();
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		String s= DateUtils.addDay(param.getEndTime(), 1);
       try {
		param.setEndTime(sim.parse(s));
	} catch (ParseException e) {
		e.printStackTrace();
	}
		List<OrderStatistics> list = orderStatisticsService.getOrderStatisticsList(param);
		for(OrderStatistics o : list){
			if(o.getSumLength() == null){
				o.setSumLength(0.0);
			}
		}
		jsonObject.put("list", list);
		jsonObject.put("section", param.getSection());
		jsonObject.put("sumLength", param.getSumLength());
		jsonObject.put("shiftName", param.getShiftName());
		return jsonObject;
}
	
	//获取工段
	@RequestMapping(value="/getSection",method = RequestMethod.GET)
	@ResponseBody
	public List<OrderStatistics> getSection(){
		return orderStatisticsService.getSection();
	}
	
	//获取工序
		@RequestMapping(value="/getName",method = RequestMethod.GET)
		@ResponseBody
		public List<OrderStatistics> getName(){
			return orderStatisticsService.getName();
		}
		
	/**
	 * <p>日期格式转换：String类型转为Date类型</p>
	 * 
	 * @date 
	 * @param binder
	 */
	@InitBinder   
    public void initBinder(WebDataBinder binder) {   
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   
        dateFormat.setLenient(true);   
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   
    }

}
