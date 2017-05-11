package cc.oit.bsmes.eve.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.oit.bsmes.bas.dao.EmployeeDAO;
import cc.oit.bsmes.bas.dao.UserRoleDAO;
import cc.oit.bsmes.bas.model.Employee;
import cc.oit.bsmes.common.constants.EventProcesserType;
import cc.oit.bsmes.common.service.impl.BaseServiceImpl;
import cc.oit.bsmes.eve.dao.EventOwnerDAO;
import cc.oit.bsmes.eve.dao.EventProcesserDAO;
import cc.oit.bsmes.eve.model.EventOwner;
import cc.oit.bsmes.eve.model.EventProcesser;
import cc.oit.bsmes.eve.service.EventProcesserService;
@Service
public class EventProcesserServiceImpl extends BaseServiceImpl<EventProcesser>
		implements EventProcesserService {
	
	@Resource 
	private EventProcesserDAO eventProcesserDAO; 
	@Resource 
	private EmployeeDAO employeeDAO;
	@Resource 
	private UserRoleDAO userRoleDAO;
	@Resource 
	EventOwnerDAO eventOwnerDAO;
	@Override
	public List<EventProcesser> getByEventProcessId(String eventProcessId) {
		return eventProcesserDAO.getByEventProcessId(eventProcessId);
	}
	@Override
	public List<Employee> getEmployeeByEventId(String eventId,String eventProcessId) {
		
		EventOwner findParams=new EventOwner();
		findParams.setEventId(eventId);
		//findParams.setEventProcessId(eventProcessId);
		List<EventOwner> ownerlist = eventOwnerDAO.get(findParams);
		List<String> userCodeList =new ArrayList<String>(); 
		for (EventOwner owner : ownerlist)  
		{
			userCodeList.add(owner.getUserCode());
		} 
		List<Employee> tempList=new ArrayList<Employee>();
		if(userCodeList != null && userCodeList.size() != 0)
		{
			tempList = employeeDAO.getEmployeeByUserCodes(userCodeList);
		} 
		 
		return tempList;
	}
	
	
	@Override
	public Map<String,String > getUserCodeByEventProcessId(String eventProcessId) {
		List<EventProcesser> processerList=eventProcesserDAO.getByEventProcessId(eventProcessId);
		//List<String >
		Map<String,String> userCodeList =new HashMap<String,String>();
		List<Employee> allList =new ArrayList<Employee>();
		 
		for (EventProcesser eventProcesser : processerList)  
		{
			if(EventProcesserType.USER.name().equalsIgnoreCase(eventProcesser.getType().name()))
			{
				userCodeList.put(eventProcesser.getProcesser(), eventProcesser.getProcesser());
			}
			else
			{
				allList.addAll(employeeDAO.getEmployeeByRoleId(eventProcesser.getProcesser()));  				
			}			
		}
		
		for (Employee emp : allList)  
		{
			userCodeList.put(emp.getUserCode(),emp.getUserCode());
		} 
	  	
		return userCodeList;
	}

}
