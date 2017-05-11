package cc.oit.bsmes.wip.action;

import java.util.ArrayList;
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
import cc.oit.bsmes.fac.service.EquipInfoService;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.pro.service.ProductProcessService;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.WorkOrderService;

/**
 * 
 * @author leiwei
 * @date 2014-01-06 17:20:40
 * @since
 * @version
 */
@Controller
@RequestMapping("/wip/cycleStepChart")
public class CycleStepChartController {
	
	@Resource private EquipInfoService equipInfoService;
	@Resource private ProductProcessService productProcessService;
	@Resource private WorkOrderService workOrderService;
	
	@RequestMapping(produces="text/html")
    public String index(Model model) {
        model.addAttribute("moduleName", "wip");
		model.addAttribute("submoduleName", "cycleStepChart");
		return "wip.cycleStepChart";
    }
	
	@ResponseBody
	@RequestMapping(value="equip",method=RequestMethod.GET)		    
	public List<EquipInfo> equip(@RequestParam String query){
		if(StringUtils.equals(query, "1")){
			return null;
		}else{
			List<EquipInfo> result=equipInfoService.getMergeEquipByProcessCode(query,equipInfoService.getOrgCode());
			for(int i=0;i<result.size();i++){
	    		EquipInfo infos=result.get(i);
	    		result.get(i).setName("["+infos.getCode().replace("_EQUIP", "")+"]  "+infos.getName().replace("_设备", ""));
	    	}
		    return result;
		}
	}
	
	@ResponseBody
	@RequestMapping(value="process",method = RequestMethod.GET)
	public List<ProductProcess> process(@RequestParam String query){
		List<ProductProcess> list=productProcessService.getByProductCode(query);
		List<ProductProcess> result=new ArrayList<ProductProcess>();
		Map<String,String> map=new HashMap<String,String>();
		for(ProductProcess productProcess:list){
			if(map.get(productProcess.getProcessCode())==null){
				result.add(productProcess);
				map.put(productProcess.getProcessCode(), productProcess.getProcessCode());
			}
		}
	    return result;
	}
	
	  @ResponseBody
	    @RequestMapping(value = "/getCycleStep", method = RequestMethod.POST)
	    public Map<String,Object> getCycleStep(@RequestParam String productCode,@RequestParam String processCode,@RequestParam String equipCode,
	    		@RequestParam String startTime,@RequestParam String endTime){
		  Date startDate=DateUtils.convert(startTime);
		  Date endDate=DateUtils.convert(endTime);
		  if(Integer.parseInt(DateUtils.convert(endDate,DateUtils.DATE_SHORT_FORMAT))>=Integer.parseInt(DateUtils.convert(new Date(),DateUtils.DATE_SHORT_FORMAT))){
				endDate=new Date();
		  }else{
				endDate=DateUtils.convert(DateUtils.convert(endDate)+" "+DateUtils.DAYTIME_END, DateUtils.DATE_TIME_FORMAT);
		  }
		  WorkOrder workOrder=new WorkOrder();
		  workOrder.setProcessCode(processCode);
		  workOrder.setProductCode(productCode);
		  workOrder.setEquipCode(equipCode);
		  workOrder.setRealStartTime(startDate);
		  workOrder.setRealEndTime(endDate);
		  workOrder.setOrgCode(workOrderService.getOrgCode());
		  Map<String,Object> map=workOrderService.getProductsCoordinate(workOrder);
		  return map;
	    }
}
