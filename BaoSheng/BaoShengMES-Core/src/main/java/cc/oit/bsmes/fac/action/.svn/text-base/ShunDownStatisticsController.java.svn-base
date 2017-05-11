package cc.oit.bsmes.fac.action;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import cc.oit.bsmes.fac.model.EquipInfo;
import cc.oit.bsmes.fac.model.StatusHistory;
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.fac.service.StatusHistoryService;

/**
 * 停机原因统计
 *
 */
@Controller
@RequestMapping("/fac/shunDownStatistics")
public class ShunDownStatisticsController {

	@Resource
	private EquipInfoService equipInfoService;
	@Resource 
	private StatusHistoryService statusHistoryService;
	@RequestMapping(produces = "text/html")
	public String index(Model model) {
	    model.addAttribute("moduleName", "fac");
	    model.addAttribute("submoduleName", "shunDownStatistics");
	    return "fac.shunDownStatistics";
	}
	 
	@ResponseBody
	@RequestMapping(value="equip",method=RequestMethod.GET)		    
	public List<EquipInfo> equip(){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("orgCode", equipInfoService.getOrgCode());
		List<EquipInfo> result=equipInfoService.getEquipLine(param);
	    for(int i=0;i<result.size();i++){
	    	EquipInfo infos=result.get(i);
	    	result.get(i).setName("["+infos.getCode()+"]  "+infos.getName());
	    }
		return result;
	}
	
	@ResponseBody
    @RequestMapping(value = "/statistics", method = RequestMethod.POST)
    public Map<String,Object> statistics(@RequestParam(required = false) String equipCode,@RequestParam(required = false) String startTime
    		,@RequestParam(required = false) String endTime){
	  StatusHistory statusHistory=new StatusHistory();
	  SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  if(!StringUtils.isNotBlank(endTime)){
		  Calendar nowS = Calendar.getInstance();
		  nowS.set(Calendar.HOUR, 0);  
		  nowS.set(Calendar.MINUTE, 0);  
		  nowS.set(Calendar.SECOND, 0);  
		  nowS.set(Calendar.MILLISECOND, 0);
		  endTime = sdf.format(nowS.getTime());
	  }
	  if(!StringUtils.isNotBlank(startTime)){
		  Calendar nowE = Calendar.getInstance();
		  nowE.add(Calendar.MONTH, -1);
		  nowE.set(Calendar.HOUR, 0);  
		  nowE.set(Calendar.MINUTE, 0);  
		  nowE.set(Calendar.SECOND, 0);  
		  nowE.set(Calendar.MILLISECOND, 0);
		  startTime = sdf.format(nowE.getTime());
	  }
	  if(StringUtils.isNotBlank(equipCode)){
		  statusHistory.setEquipCode(equipCode);
	  }
	  if(StringUtils.isNotBlank(startTime)){
		  statusHistory.setStartTime(DateUtils.convert(startTime));
	  }
	  if(StringUtils.isNotBlank(endTime)){
		  Date endDate=DateUtils.convert(endTime);
		  if(Integer.parseInt(DateUtils.convert(endDate,DateUtils.DATE_SHORT_FORMAT))>=Integer.parseInt(DateUtils.convert(new Date(),DateUtils.DATE_SHORT_FORMAT))){
				endDate=new Date();
		  }else{
				endDate=DateUtils.getEndDatetime(endDate);
		  }
		  statusHistory.setEndTime(endDate);
	  }
	  statusHistory.setOrgCode(statusHistoryService.getOrgCode());
	  return statusHistoryService.getIdleDataByEquipAndLimitTime(statusHistory);
    }
}
