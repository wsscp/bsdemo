package cc.oit.bsmes.interfaceWebClient.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.model.User;
import cc.oit.bsmes.common.constants.EquipStatus;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.common.util.SessionUtils;
import cc.oit.bsmes.interfaceWebClient.dao.EquipSummaryDAO;
import cc.oit.bsmes.interfaceWebClient.model.EquipSummary;
import cc.oit.bsmes.interfaceWebClient.service.EquipSummaryService;

/**
 *  
 * <p style="display:none">modifyRecord</p>
 * @author zhangdongping
 * @date 2015-5-19 下午1:04:50
 * @since
 * @version
 */
@Service
public class EquipSummaryServiceImpl extends BaseServiceImpl<EquipSummary> implements EquipSummaryService{
	
	@Resource EquipSummaryDAO equipSummaryDAO;
	@Override
	public List<EquipSummary> getEquipSummary(EquipSummary parm) {
		User user = SessionUtils.getUser();		
		if(user!=null)
		parm.setOrgCode(user.getOrgCode());
		return  equipSummaryDAO.getEquipSummary(parm); 
	}
	@Override
	public List<EquipSummary> getEquipOEE(EquipSummary parm) {
		  
		User user = SessionUtils.getUser();		
		if(user!=null)
		parm.setOrgCode(user.getOrgCode());
		List<EquipSummary> resultList= new ArrayList<EquipSummary>();
		List<EquipSummary> alist = equipSummaryDAO.getEquipOEE(parm);
		if(alist==null)
		{
			return resultList;
		}
		Map<Object,EquipSummary>  temp=new HashMap<Object,EquipSummary>();
		
		
		
		for(int i=0;i<alist.size();i++){
			EquipSummary chart=alist.get(i);
			EquipSummary object = temp.get(chart.getReportDate());
			if(object==null)
			{
				object=chart;
				temp.put(chart.getReportDate(), object);
				resultList.add(object);
			}
			object.setTotal(object.getTotal()+chart.getTimes());
			if(chart.getType().equals(EquipStatus.IN_PROGRESS)||chart.getType().equals(EquipStatus.IN_DEBUG))
			{
				object.setUsed(object.getUsed()+chart.getTimes());
			} 
		}
		return resultList;
		
		 
	}
}
