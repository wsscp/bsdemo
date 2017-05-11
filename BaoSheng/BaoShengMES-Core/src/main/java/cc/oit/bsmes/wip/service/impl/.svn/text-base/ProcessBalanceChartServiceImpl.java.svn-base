package cc.oit.bsmes.wip.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.util.DateUtils;
import cc.oit.bsmes.fac.dao.StatusHistoryDAO;
import cc.oit.bsmes.fac.model.StatusHistory;
import cc.oit.bsmes.fac.model.StatusHistoryChart;
import cc.oit.bsmes.pro.dao.ProductProcessDAO;
import cc.oit.bsmes.pro.model.ProductProcess;
import cc.oit.bsmes.wip.dao.WorkOrderDAO;
import cc.oit.bsmes.wip.model.WorkOrder;
import cc.oit.bsmes.wip.service.ProcessBalanceChartService;

/**
 * <p style="display:none">modifyRecord</p>
 * @author leiwei
 * @date 2015-01-06 下午3:44:31
 * @since
 * @version
 */

@Service
public class ProcessBalanceChartServiceImpl implements ProcessBalanceChartService {

	@Resource 
	private WorkOrderDAO  workOrderDAO;
	@Resource 
	private StatusHistoryDAO statusHistoryDAO;
	@Resource ProductProcessDAO productProcessDAO;
	@Override 
	public Map<String, Object[]> getProcessChart(String productCode,Date startTime, Date endTime) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("productCode", productCode);
		map.put("startTime", startTime);
		map.put("endTime",endTime );
		List<WorkOrder> workOrderList=workOrderDAO.getWorkOrderAndLimitTime(map);
		Map<String,Integer> processMap=new HashMap<String,Integer>();
		List<StatusHistoryChart> charts=new ArrayList<StatusHistoryChart>();
		if(workOrderList.size()>0){
			Date startDate=null;
			Date endDate=null;
			List<String> equipCodes=new ArrayList<String>();
			Map<String,String> equipMap=new HashMap<String,String>();
			for(WorkOrder workOrder:workOrderList){
				if(equipMap.get(workOrder.getEquipCode())==null){
					equipCodes.add(workOrder.getEquipCode());
					equipMap.put(workOrder.getEquipCode(), workOrder.getEquipCode());
				}
				if(startDate==null || startDate.after(workOrder.getRealStartTime())){
					startDate=workOrder.getRealStartTime();
				}
				if(endDate==null || endDate.before(workOrder.getRealEndTime())){
					endDate=workOrder.getRealEndTime();
				}
			}
			map.put("startTime", startDate);
			map.put("endTime",endDate );
			map.put("equipCodes", equipCodes);
			List<StatusHistory> statusHistoryList=statusHistoryDAO.getByLimitTime(map);
			for(int i=0;i<workOrderList.size();i++){
				WorkOrder workOrder=workOrderList.get(i);
				if(statusHistoryList.size()>0){
					if(processMap.get(workOrder.getProcessName())!=null){
						StatusHistoryChart pchart=charts.get(processMap.get(workOrder.getProcessName()));
						charts.get(processMap.get(workOrder.getProcessName())).setValue(pchart.getValue()+workOrder.getOrderLength());
					}
				}
				for(int j=0;j<statusHistoryList.size();j++){
					StatusHistory statusHistory=statusHistoryList.get(j);
					if(StringUtils.equals(workOrder.getEquipCode(),statusHistory.getEquipCode())){
						if(StringUtils.equals(statusHistory.getStatus().toString(), EquipStatus.IN_DEBUG.toString()) ||
								StringUtils.equals(statusHistory.getStatus().toString(), EquipStatus.IN_PROGRESS.toString())){
							if(workOrder.getRealStartTime().before(statusHistory.getEndTime()) && workOrder.getRealEndTime().after(statusHistory.getStartTime())){
								if(workOrder.getRealStartTime().getTime()>=statusHistory.getStartTime().getTime()){
									statusHistory.setStartTime(workOrder.getRealStartTime());
								}
								if(workOrder.getRealEndTime().getTime()<=statusHistory.getEndTime().getTime()){
									statusHistory.setEndTime(workOrder.getRealEndTime());
								}
								long betTime=DateUtils.getSecondsDiff(statusHistory.getStartTime(), statusHistory.getEndTime());
								StatusHistoryChart chart=null;
								if(processMap.get(workOrder.getProcessName())==null){
								   chart=new StatusHistoryChart();
									chart.setName(workOrder.getProcessName());
									if(StringUtils.equals(statusHistory.getStatus().toString(), EquipStatus.IN_DEBUG.toString())){
										chart.setDebugValue(betTime);
									}else{
										chart.setProcessValue(betTime);
									}
									chart.setValue(workOrder.getOrderLength());
									processMap.put(workOrder.getProcessName(), charts.size());
									charts.add(chart);
								}else{
									chart=charts.get(processMap.get(workOrder.getProcessName()));
									long total=0l;
									if(StringUtils.equals(statusHistory.getStatus().toString(), EquipStatus.IN_DEBUG.toString())){
										total+=(chart.getDebugValue()==null?0l:chart.getDebugValue())+betTime;
										charts.get(processMap.get(workOrder.getProcessName())).setDebugValue(total);
									}else{
										total+=(chart.getProcessValue()==null?0l:chart.getProcessValue())+betTime;
										charts.get(processMap.get(workOrder.getProcessName())).setProcessValue(total);
									}
//									charts.get(processMap.get(workOrder.getProcessName())).setValue(chart.getValue()+workOrder.getOrderLength());
								}
							}
						}
					}
				}
			}
			
			return sortChart(charts, productCode);
		}else{
			return null;
		}
	}
	private Map<String, Object[]> sortChart(List<StatusHistoryChart> charts,String productCode) {
		Map<String,Object[]> resultMap=new HashMap<String,Object[]>();
		List<ProductProcess> list=productProcessDAO.getByProductCode(productCode);
		Collections.reverse(list);
		List<ProductProcess> result=new ArrayList<ProductProcess>();
		Map<String,String> map=new HashMap<String,String>();
		for(ProductProcess productProcess:list){
			if(map.get(productProcess.getProcessCode())==null){
				result.add(productProcess);
				map.put(productProcess.getProcessCode(), productProcess.getProcessCode());
			}
		}
		String[] processName=new String[result.size()];
		Double[] processData=new Double[result.size()];
		Double[] debugData=new Double[result.size()];
		for(int i=0;i<result.size();i++){
			ProductProcess productProcess=result.get(i);
			processName[i]=productProcess.getProcessName();
			Double debug=null;
			Double process=null;
			for(StatusHistoryChart statusHistoryChart:charts){
				if(StringUtils.equals(productProcess.getProcessName(), statusHistoryChart.getName())){
					long debugTime=statusHistoryChart.getDebugValue()==null?0l:statusHistoryChart.getDebugValue();
					long processTime=statusHistoryChart.getProcessValue()==null?0l:statusHistoryChart.getProcessValue();
					process=Double.parseDouble(String.format("%.1f",processTime*100/(statusHistoryChart.getValue()*60.0)));
					debug=Double.parseDouble(String.format("%.1f",debugTime*100/(statusHistoryChart.getValue()*60.0)));
				}
			}
			if(debug==null || debug==0){
				debugData[i]=0.01;
			}else{
				debugData[i]=debug;
			}
			if(process==null || process==0 ){
				processData[i]=0.01;
			}else{
				processData[i]=process;
			}
		}
		resultMap.put("processName", processName);
		resultMap.put("processData", processData);
		resultMap.put("debugData", debugData);
		return resultMap;
	}

}
