package cc.oit.bsmes.fac.action;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cc.oit.bsmes.bas.model.EquipEnergyMonitor;
import cc.oit.bsmes.bas.model.EquipEnergyMonthMonitor;
import cc.oit.bsmes.bas.service.EquipEnergyMonitorService;
import cc.oit.bsmes.bas.service.EquipEnergyMonthMonitorService;
import cc.oit.bsmes.common.mybatis.Sort;
import cc.oit.bsmes.common.view.TableView;
import cc.oit.bsmes.wip.model.Report;
import cc.oit.bsmes.wip.service.ReportService;

import com.alibaba.fastjson.JSONArray;


@Controller
@RequestMapping("/fac/equipEnergyMonitor")
public class EquipEnergyMonitorController {
	
	 @Resource
	 private EquipEnergyMonitorService equipEnergyMonitorService;
	 
	 @Resource
	 private EquipEnergyMonthMonitorService equipEnergyMonthMonitorService;
	 @Resource
	 private ReportService reportService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
		model.addAttribute("moduleName", "fac");
        model.addAttribute("submoduleName", "equipEnergyMonitor");
        return "fac.equipEnergyMonitor";
    }
	
	@ResponseBody
	@RequestMapping
	public TableView list(HttpServletRequest request, @RequestParam String equipName,@RequestParam String demandTime,
			@RequestParam int page, @RequestParam int start,@RequestParam(required = false) String sort) throws ParseException {
		String name ="";
		if(equipName =="JY8"||"JY8".equals(equipName)||(equipName==""||"".equals(equipName))){
			name = "绝缘8#";
		}else if(equipName =="CL"||"CL".equals(equipName)){
			name = "4#成缆";
		}else if(equipName =="GXJ"||"GXJ".equals(equipName)){
			name = "115#硅橡胶";
		}else if(equipName =="HT4"||"HT4".equals(equipName)){
			name = "护套4#";
		}else if(equipName =="GD1"||"GD1".equals(equipName)){
			name = "高登1";
		}else if(equipName =="GD2"||"GD2".equals(equipName)){
			name = "高登2";
		}
		
		String mShift = demandTime + " " + "07:45:00";
		String aShift = demandTime + " " + "15:45:00";
		String eShift = demandTime + " " + "23:45:00";
		Calendar c = Calendar.getInstance(); 
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(eShift);
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, 1);
		String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + " " + "07:45:00";
		Map<String,Object> findParams = new HashMap<String,Object>();
		findParams.put("demandTime", demandTime);
		findParams.put("mShift", mShift);
		findParams.put("aShift", aShift);
		findParams.put("eShift", eShift);
		findParams.put("name", name);
		findParams.put("currentTime", currentTime);
		List<EquipEnergyMonitor> list = equipEnergyMonitorService.getEquipEnergyMonitor(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		return tableView;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/energyLoad", method = RequestMethod.GET)
	public TableView energyLoad(@RequestParam(required = false) String demandTime,
			@RequestParam(required = false) String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) throws ParseException {
		Map<String,Object> findParams = new HashMap<String,Object>();
		findParams.put("createDate", demandTime);
		List<EquipEnergyMonitor> list = equipEnergyMonitorService.getEquipEnergyLoad(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		return tableView;
	
	}
	
	@ResponseBody
	@RequestMapping(value = "/energyMonthLoad", method = RequestMethod.GET)
	public TableView energyMonthLoad(@RequestParam(required = false) String demandTime,
			@RequestParam(required = false) String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) throws ParseException {
		Map<String,Object> findParams = new HashMap<String,Object>();
		findParams.put("createDate", demandTime);
		List<EquipEnergyMonthMonitor> list = equipEnergyMonthMonitorService.getEquipEnergyMonthLoad(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		return tableView;
	
	}
	
	@ResponseBody
	@RequestMapping(value = "/energyQuantity", method = RequestMethod.GET)
	public TableView energyQuantity(@RequestParam(required = false) String demandTime,
			@RequestParam(required = false) String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) throws ParseException {
		Map<String,Object> findParams = new HashMap<String,Object>();
		findParams.put("createDate", demandTime);
		List<EquipEnergyMonitor> list = equipEnergyMonitorService.getEnergyQuantity(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		return tableView;
	
	}
	
	@ResponseBody
	@RequestMapping(value = "/energyMonthQuantity", method = RequestMethod.GET)
	public TableView energyMonthQuantity (@RequestParam(required = false) String demandTime,
			@RequestParam(required = false) String sort, @RequestParam int page, @RequestParam int start, @RequestParam int limit) throws ParseException {
		Map<String,Object> findParams = new HashMap<String,Object>();
		findParams.put("createDate", demandTime);
		List<EquipEnergyMonitor> list = equipEnergyMonthMonitorService.getEnergyMonthQuantity(findParams);
		TableView tableView = new TableView();
		tableView.setRows(list);
		return tableView;
	
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/equipEnergyInfo", method = RequestMethod.GET)
	public TableView equipEnergyInfo(@RequestParam(required = false) String equipCodes,@RequestParam(required = false) String energyModifyTime) throws ParseException {
		Report findParams = new Report();
		findParams.setEquipCode(equipCodes);
		String mShift = energyModifyTime + " " + "07:45:00";
		String aShift = energyModifyTime + " " + "15:45:00";
		String eShift = energyModifyTime + " " + "23:45:00";
		Calendar c = Calendar.getInstance(); 
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(eShift);
		c.setTime(d);
		c.add(Calendar.DAY_OF_MONTH, 1);
		String currentTime = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + " " + "07:45:00";
		findParams.setMorShift(mShift);
		findParams.setAftShift(aShift);
		findParams.setEveShift(eShift);
		findParams.setCurrentTime(currentTime);
		List<Report> list = reportService.getEquipEnergyInfo(findParams);
		for(Report li :list){
			String rMarks = reportService.getReMarks(li.getWorkOrderNo());
			li.setRMarks(rMarks);
		}
		TableView tableView = new TableView();
		tableView.setRows(list);
		tableView.setTotal(reportService.countFind(findParams));
		return tableView;
	}
	
	@RequestMapping(value = "realReceiptChart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> realChart(@RequestParam(required = false) String equipName,@RequestParam String startTime,@RequestParam String endTime) {
		String startTime1 = startTime.replaceAll("T"," ");
		String endTime1 = endTime.replaceAll("T"," ");
		Map<String, Object> result = equipEnergyMonitorService.energyReceiptChart(equipName,startTime1,endTime1);
		return result;
	}
}
